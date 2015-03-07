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
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.DiffOutputControl
import liquibase.exception.LiquibaseException
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.FileSystemResourceAccessor
import liquibase.util.file.FilenameUtils
import org.grails.plugins.databasemigration.liquibase.GroovyDiffToChangeLogCommand
import org.grails.plugins.databasemigration.liquibase.GroovyGenerateChangeLogCommand

import java.text.ParseException

@CompileStatic
trait DatabaseMigrationCommand {

    static final String CONFIG_PREFIX = 'grails.plugin.databasemigration'

    static final String DEFAULT_CHANGE_LOG_LOCATION = 'grails-app/migrations'
    static final String DEFAULT_CHANGE_LOG_FILE = 'changelog.groovy'

    abstract ConfigMap getConfig()

    File getChangeLogLocation() {
        new File(config.getProperty("${CONFIG_PREFIX}.changelogLocation", String) ?: DEFAULT_CHANGE_LOG_LOCATION)
    }

    File getChangeLogFile() {
        new File(changeLogLocation, config.getProperty("${CONFIG_PREFIX}.changelogFileName", String) ?: DEFAULT_CHANGE_LOG_FILE)
    }

    File resolveChangeLogFile(String filename) {
        if (!filename) {
            return null
        }
        new File(changeLogLocation, filename)
    }

    Map<String, String> getDataSourceConfig(String dataSource) {
        getDataSourceConfig(dataSource, config)
    }

    Map<String, String> getDataSourceConfig(String dataSource, ConfigMap config) {
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

    @CompileDynamic
    void withLiquibase(String defaultSchema, String dataSource, @ClosureParams(value = SimpleType, options = 'liquibase.Liquibase') Closure closure) {
        def fileSystemResourceAccessor = new FileSystemResourceAccessor(changeLogLocation.path)

        withDatabase(defaultSchema, getDataSourceConfig(dataSource)) { Database database ->
            def liquibase = new Liquibase(changeLogLocation.toPath().relativize(changeLogFile.toPath()).toString(), fileSystemResourceAccessor, database)
            closure.call(liquibase)
        }
    }

    void withDatabase(String defaultSchema, Map<String, String> dataSourceConfig, @ClosureParams(value = SimpleType, options = 'liquibase.database.Database') Closure closure) {
        def database = null
        try {
            database = createDatabase(defaultSchema, dataSourceConfig)
            closure.call(database)
        } finally {
            database?.close()
        }
    }

    private Database createDatabase(String defaultSchema, Map<String, String> dataSourceConfig) {
        return CommandLineUtils.createDatabaseObject(
            Thread.currentThread().contextClassLoader,
            dataSourceConfig.url,
            dataSourceConfig.username ?: null,
            dataSourceConfig.password ?: null,
            dataSourceConfig.driverClassName,
            defaultSchema,
            null,
            true,
            true,
            null,
            null,
            null,
            null,
            null,
        )
    }

    void doGenerateChangeLog(File changeLogFile, Database originalDatabase) {
        def changeLogFilePath = changeLogFile?.path
        def compareControl = new CompareControl([] as CompareControl.SchemaComparison[], null as String)
        def diffOutputControl = new DiffOutputControl(false, false, false)

        def command = new GroovyGenerateChangeLogCommand()
        command.setReferenceDatabase(originalDatabase)
            .setOutputStream(System.out)
            .setCompareControl(compareControl)
        command.setChangeLogFile(changeLogFilePath)
            .setDiffOutputControl(diffOutputControl)

        try {
            command.execute()
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e)
        }
    }

    void doDiffToChangeLog(File changeLogFile, Database referenceDatabase, Database targetDatabase) {
        def changeLogFilePath = changeLogFile?.path
        def compareControl = new CompareControl([] as CompareControl.SchemaComparison[], null as String)
        def diffOutputControl = new DiffOutputControl(false, false, false)

        def command = new GroovyDiffToChangeLogCommand()
        command.setReferenceDatabase(referenceDatabase)
            .setTargetDatabase(targetDatabase)
            .setCompareControl(compareControl)
            .setOutputStream(System.out)
        command.setChangeLogFile(changeLogFilePath)
            .setDiffOutputControl(diffOutputControl)

        try {
            command.execute();
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e)
        }
    }

    void appendToChangeLog(File destChangeLogFile) {
        def srcChangeLogFile = changeLogFile
        if (!changeLogFile.exists() || srcChangeLogFile == destChangeLogFile) {
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
}
