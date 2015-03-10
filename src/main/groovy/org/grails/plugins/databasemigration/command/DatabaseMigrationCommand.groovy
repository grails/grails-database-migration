/*
 * Copyright 2015 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.plugins.databasemigration.command

import grails.config.ConfigMap
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import liquibase.Liquibase
import liquibase.command.CommandExecutionException
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.DiffOutputControl
import liquibase.diff.output.StandardObjectChangeFilter
import liquibase.exception.LiquibaseException
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.resource.FileSystemResourceAccessor
import liquibase.util.file.FilenameUtils
import org.grails.build.parsing.CommandLine
import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.liquibase.GroovyDiffToChangeLogCommand
import org.grails.plugins.databasemigration.liquibase.GroovyGenerateChangeLogCommand

import java.nio.file.Path
import java.text.ParseException

@CompileStatic
trait DatabaseMigrationCommand {

    static final String DEFAULT_CHANGE_LOG_LOCATION = 'grails-app/migrations'

    CommandLine commandLine

    String defaultSchema
    String dataSource
    String contexts

    abstract ConfigMap getConfig()

    String optionValue(String name) {
        commandLine.optionValue(name)?.toString()
    }

    boolean hasOption(String name) {
        commandLine.hasOption(name)
    }

    String getContexts() {
        if (contexts) {
            return contexts
        }
        def contexts = migrationConfig.get('contexts')
        if (!contexts) {
            return null
        }
        if (contexts instanceof List) {
            return contexts.join(',')
        }
        return contexts
    }

    List<String> getArgs() {
        commandLine.remainingArgs
    }

    File getChangeLogLocation() {
        new File(migrationConfig.get('changelogLocation', DEFAULT_CHANGE_LOG_LOCATION) as String)
    }

    File getChangeLogFile() {
        def migrationConfig = getMigrationConfig(dataSource)

        boolean isDefault = (!dataSource || dataSource == 'dataSource')
        new File(changeLogLocation, migrationConfig.get('changelogFileName', isDefault ? 'changelog.groovy' : "changelog-${dataSource}.groovy") as String)
    }

    File resolveChangeLogFile(String filename) {
        if (!filename) {
            return null
        }
        if (FilenameUtils.getExtension(filename)) {
            return new File(changeLogLocation, filename)
        }
        if (dataSource) {
            return new File(changeLogLocation, "${filename}-${dataSource}.groovy")
        }
        return new File(changeLogLocation, "${filename}.groovy")
    }

    Map<String, String> getDataSourceConfig(ConfigMap config = this.config) {
        def dataSourceName = dataSource ? "dataSource_$dataSource" : 'dataSource'
        def dataSources = config.getProperty('dataSources', Map) ?: [:]
        if (!dataSources) {
            def defaultDataSource = config.getProperty('dataSource', Map)
            if (defaultDataSource) {
                dataSources['dataSource'] = defaultDataSource
            }
        }
        return (Map<String, String>) dataSources.get(dataSourceName)
    }

    void withFileOrSystemOutWriter(String filename, @ClosureParams(value = SimpleType, options = "java.io.Writer") Closure closure) {
        if (!filename) {
            closure.call(new PrintWriter(System.out))
            return
        }

        def outputFile = new File(filename)
        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }
        outputFile.withWriter { Writer writer ->
            closure.call(writer)
        }
    }

    boolean isTimeFormat(String time) {
        time ==~ /\d{2}:\d{2}:\d{2}/
    }

    Date parseDateTime(String date, String time) throws ParseException {
        time = time ?: '00:00:00'
        Date.parse('yyyy-MM-dd HH:mm:ss', "$date $time")
    }

    void withLiquibase(@ClosureParams(value = SimpleType, options = 'liquibase.Liquibase') Closure closure) {
        def fileSystemResourceAccessor = new FileSystemResourceAccessor(changeLogLocation.path)

        Path changeLogLocationPath = changeLogLocation.toPath()
        Path changeLogFilePath = changeLogFile.toPath()
        String relativePath = changeLogLocationPath.relativize(changeLogFilePath).toString()

        withDatabase { Database database ->
            def liquibase = new Liquibase(relativePath, fileSystemResourceAccessor, database)
            closure.call(liquibase)
        }
    }

    void withDatabase(Map<String, String> dataSourceConfig = null, @ClosureParams(value = SimpleType, options = 'liquibase.database.Database') Closure closure) {
        def database = null
        try {
            database = createDatabase(defaultSchema, dataSource, dataSourceConfig ?: getDataSourceConfig())
            closure.call(database)
        } finally {
            database?.close()
        }
    }

    Database createDatabase(String defaultSchema, String dataSource, Map<String, String> dataSourceConfig) {
        Database database = DatabaseFactory.getInstance().openDatabase(
            dataSourceConfig.url,
            dataSourceConfig.username ?: null,
            dataSourceConfig.password ?: null,
            dataSourceConfig.driverClassName,
            null,
            null,
            null,
            new ClassLoaderResourceAccessor(Thread.currentThread().contextClassLoader)
        )
        configureDatabase(database)
        return database
    }

    void configureDatabase(Database database) {
        def migrationConfig = getMigrationConfig(dataSource)

        database.defaultSchemaName = defaultSchema
        if (!database.supportsSchemas() && defaultSchema) {
            database.defaultCatalogName = defaultSchema
        }
        if (migrationConfig.containsKey('databaseChangeLogTableName')) {
            database.databaseChangeLogTableName = migrationConfig.get('databaseChangeLogTableName') as String
        }
        if (migrationConfig.containsKey('databaseChangeLogLockTableName')) {
            database.databaseChangeLogLockTableName = migrationConfig.get('databaseChangeLogLockTableName') as String
        }
    }

    void doGenerateChangeLog(File changeLogFile, Database originalDatabase) {
        def changeLogFilePath = changeLogFile?.path
        def compareControl = new CompareControl([] as CompareControl.SchemaComparison[], null as String)

        def command = new GroovyGenerateChangeLogCommand()
        command.setReferenceDatabase(originalDatabase).setOutputStream(System.out).setCompareControl(compareControl)
        command.setChangeLogFile(changeLogFilePath).setDiffOutputControl(createDiffOutputControl())

        try {
            command.execute()
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e)
        }
    }

    void doDiffToChangeLog(File changeLogFile, Database referenceDatabase, Database targetDatabase) {
        def changeLogFilePath = changeLogFile?.path
        def compareControl = new CompareControl([] as CompareControl.SchemaComparison[], null as String)

        def command = new GroovyDiffToChangeLogCommand()
        command.setReferenceDatabase(referenceDatabase).setTargetDatabase(targetDatabase).setCompareControl(compareControl).setOutputStream(System.out)
        command.setChangeLogFile(changeLogFilePath).setDiffOutputControl(createDiffOutputControl())

        try {
            command.execute();
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e)
        }
    }

    private DiffOutputControl createDiffOutputControl() {
        def diffOutputControl = new DiffOutputControl(false, false, false)

        def migrationConfig = getMigrationConfig(dataSource)

        String excludeObjects = migrationConfig.get('excludeObjects')
        String includeObjects = migrationConfig.get('includeObjects')
        if (excludeObjects && includeObjects) {
            throw new DatabaseMigrationException("Cannot specify both excludeObjects and includeObjects")
        }
        if (excludeObjects) {
            diffOutputControl.objectChangeFilter = new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.EXCLUDE, excludeObjects)
        }
        if (includeObjects) {
            diffOutputControl.objectChangeFilter = new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, includeObjects)
        }

        diffOutputControl
    }

    void appendToChangeLog(File srcChangeLogFile, File destChangeLogFile) {
        if (!srcChangeLogFile.exists() || srcChangeLogFile == destChangeLogFile) {
            return
        }

        def relativePath = changeLogLocation.toPath().relativize(destChangeLogFile.toPath()).toString()
        def extension = FilenameUtils.getExtension(srcChangeLogFile.name)?.toLowerCase()

        switch (extension) {
            case ['yaml', 'yml']:
                srcChangeLogFile << """
                |- include:
                |    file: ${relativePath}
                """.stripMargin().trim()
                break
            case ['xml']:
                def text = srcChangeLogFile.text
                if (text =~ '<databaseChangeLog[^>]*/>') {
                    srcChangeLogFile.write(text.replaceFirst('(<databaseChangeLog[^>]*)/>', "\$1>\n    <include file=\"$relativePath\"/>\n</databaseChangeLog>"))
                } else {
                    srcChangeLogFile.write(text.replaceFirst('</databaseChangeLog>', "    <include file=\"$relativePath\"/>\n\$0"))
                }
                break
            case ['groovy']:
                def text = srcChangeLogFile.text
                srcChangeLogFile.write(text.replaceFirst('}.*$', "    include file: '$relativePath'\n\$0"))
                break
        }
    }

    @CompileDynamic
    Map<String, Object> getMigrationConfig(String dataSourceName = null) {
        def isDefault = (!dataSourceName || dataSourceName == 'dataSource')
        if (isDefault) {
            return config.grails.plugin.databasemigration
        }
        return config.grails.plugin.databasemigration."${dataSourceName - 'dataSource_'}"
    }
}
