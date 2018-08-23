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
import liquibase.resource.ResourceAccessor
import liquibase.util.file.FilenameUtils
import org.grails.build.parsing.CommandLine
import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.liquibase.GroovyDiffToChangeLogCommand
import org.grails.plugins.databasemigration.liquibase.GroovyGenerateChangeLogCommand

import java.nio.file.Path
import java.text.ParseException

import static org.grails.plugins.databasemigration.DatabaseMigrationGrailsPlugin.getDataSourceName
import static org.grails.plugins.databasemigration.DatabaseMigrationGrailsPlugin.isDefaultDataSource
import static org.grails.plugins.databasemigration.PluginConstants.DATA_SOURCE_NAME_KEY
import static org.grails.plugins.databasemigration.PluginConstants.DEFAULT_CHANGE_LOG_LOCATION

@CompileStatic
trait DatabaseMigrationCommand {

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
        return config.getProperty("${configPrefix}.contexts".toString(), List)?.join(',')
    }

    List<String> getArgs() {
        commandLine.remainingArgs
    }

    File getChangeLogLocation() {
        new File(config.getProperty("${configPrefix}.changelogLocation".toString(), String) ?: DEFAULT_CHANGE_LOG_LOCATION)
    }

    File getChangeLogFile() {
        new File(changeLogLocation, changeLogFileName)
    }

    String getChangeLogFileName() {
        def changelogFileName = config.getProperty("${configPrefix}.changelogFileName".toString(), String)
        if (changelogFileName) {
            return changelogFileName
        }
        return isDefaultDataSource(dataSource) ? 'changelog.groovy' : "changelog-${dataSource}.groovy"
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
        def dataSourceName = dataSource ?: 'dataSource'

        if (dataSourceName == 'dataSource' && config.containsKey(dataSourceName)) {
            return (Map<String, String>) (config.getProperty(dataSourceName, Map) ?: [:])
        }

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
        if (outputFile.parentFile && !outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }
        outputFile.withWriter { BufferedWriter writer ->
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
        def resourceAccessor = createResourceAccessor()

        Path changeLogLocationPath = changeLogLocation.toPath()
        Path changeLogFilePath = changeLogFile.toPath()
        String relativePath = changeLogLocationPath.relativize(changeLogFilePath).toString()

        withDatabase { Database database ->
            Liquibase liquibase = new Liquibase(relativePath, resourceAccessor, database)
            liquibase.changeLogParameters.set(DATA_SOURCE_NAME_KEY, getDataSourceName(dataSource))
            closure.call(liquibase)
        }
    }

    ResourceAccessor createResourceAccessor() {
        new FileSystemResourceAccessor(changeLogLocation.path)
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

    @CompileDynamic
    Database createDatabase(String defaultSchema, String dataSource, Map<String, String> dataSourceConfig) {
        String password = dataSourceConfig.password ?: null

        if (password && dataSourceConfig.passwordEncryptionCodec) {
            def clazz = Class.forName(dataSourceConfig.passwordEncryptionCodec)
            password = clazz.decode(password)
        }

        Database database = DatabaseFactory.getInstance().openDatabase(
                dataSourceConfig.url,
                dataSourceConfig.username ?: null,
                password,
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
        database.defaultSchemaName = defaultSchema
        if (!database.supportsSchemas() && defaultSchema) {
            database.defaultCatalogName = defaultSchema
        }
        database.databaseChangeLogTableName = config.getProperty("${configPrefix}.databaseChangeLogTableName".toString(), String)
        database.databaseChangeLogLockTableName = config.getProperty("${configPrefix}.databaseChangeLogLockTableName".toString(), String)
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

        def command = new GroovyDiffToChangeLogCommand(config.getProperty("${configPrefix}.diffCallback", Closure))
        command.setReferenceDatabase(referenceDatabase).setTargetDatabase(targetDatabase).setCompareControl(compareControl).setOutputStream(System.out)
        command.setChangeLogFile(changeLogFilePath).setDiffOutputControl(createDiffOutputControl())

        try {
            command.execute()
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e)
        }
    }

    private DiffOutputControl createDiffOutputControl() {
        def diffOutputControl = new DiffOutputControl(false, false, false)

        String excludeObjects = config.getProperty("${configPrefix}.excludeObjects".toString(), String)
        String includeObjects = config.getProperty("${configPrefix}.includeObjects".toString(), String)
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

    String getConfigPrefix() {
        return isDefaultDataSource(dataSource) ?
                'grails.plugin.databasemigration' : "grails.plugin.databasemigration.${dataSource}"
    }
}
