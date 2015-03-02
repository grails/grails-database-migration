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
package org.grails.plugins.databasemigration

import grails.config.Config
import grails.util.Environment
import grails.util.Holders
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import liquibase.CatalogAndSchema
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.diff.output.DiffOutputControl
import liquibase.integration.commandline.CommandLineResourceAccessor
import liquibase.integration.commandline.CommandLineUtils
import liquibase.resource.CompositeResourceAccessor
import liquibase.resource.FileSystemResourceAccessor
import org.grails.config.PropertySourcesConfig
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.hibernate.cfg.Configuration
import org.hibernate.dialect.Dialect
import org.springframework.context.ConfigurableApplicationContext

import java.text.ParseException

@CompileStatic
trait DatabaseMigrationCommand {

    static final String CONFIG_PREFIX = 'grails.plugin.databasemigration'

    static final String DEFAULT_CHANGE_LOG_LOCATION = 'grails-app/conf'
    static final String DEFAULT_CHANGE_LOG_FILE = 'db/changelog/db.changelog-master.yml'

    File getChangeLogLocation() {
        new File(Holders.config.getProperty("${CONFIG_PREFIX}.changelogLocation", DEFAULT_CHANGE_LOG_LOCATION))
    }

    File getChangeLogFile() {
        new File(getChangeLogLocation(), Holders.config.getProperty("${CONFIG_PREFIX}.changelogFileName", DEFAULT_CHANGE_LOG_FILE))
    }

    File resolveChangeLogFile(String filename) {
        if (!filename) {
            return null
        }
        new File(getChangeLogLocation(), filename)
    }

    Map<String, String> getDataSourceConfig(String dataSource, String environment = Environment.current.name) {
        def dataSourceName = dataSource ? "dataSource_$dataSource" : 'dataSource'
        def config = getConfig(environment)
        def dataSources = config.getProperty('dataSources', Map, [:])
        if (!dataSources) {
            def defaultDataSource = config.getProperty('dataSource', Map)
            if (defaultDataSource) {
                dataSources['dataSource'] = defaultDataSource
            }
        }
        return (Map<String, String>) dataSources.get(dataSourceName)
    }

    private Config getConfig(String environment = null) {
        if (!environment || Environment.currentEnvironment.name == environment) {
            return Holders.config
        }

        return (Config) environmentWith(environment) {
            new PropertySourcesConfig(((PropertySourcesConfig) Holders.config).getPropertySources())
        }
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

    private Object environmentWith(String environment, Closure closure) {
        def originalEnvironment = Environment.currentEnvironment
        System.setProperty(Environment.KEY, environment)
        try {
            return closure.call()
        } finally {
            System.setProperty(Environment.KEY, originalEnvironment.name)
        }
    }

    boolean isTimeFormat(String time) {
        time ==~ /\d{2}:\d{2}:\d{2}/
    }

    Date parseDateTime(String date, String time) throws ParseException {
        time = time ?: '00:00:00'
        Date.parse('yyyy-MM-dd HH:mm:ss', "$date $time")
    }

    void withLiquibase(File changeLogFile, String defaultSchema, Map<String, String> dataSourceConfig, @ClosureParams(value = SimpleType, options = 'liquibase.Liquibase') Closure closure) {
        def fsOpener = new FileSystemResourceAccessor()
        def clOpener = new CommandLineResourceAccessor(Thread.currentThread().contextClassLoader)
        def fileOpener = new CompositeResourceAccessor(fsOpener, clOpener)

        withDatabase(defaultSchema, dataSourceConfig) { Database database ->
            def liquibase = new Liquibase(changeLogFile.path, fileOpener, database)
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

    void withGormDatabase(ConfigurableApplicationContext applicationContext, String dataSource, @ClosureParams(value = SimpleType, options = 'liquibase.database.Database') Closure closure) {
        def database = null
        try {
            database = createGormDatabase(applicationContext, dataSource)
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

    @CompileDynamic
    private Database createGormDatabase(ConfigurableApplicationContext applicationContext, String dataSource = null) {
        String sessionFactoryName = dataSource ? '&sessionFactory_' + dataSource : '&sessionFactory'

        def sessionFactory = applicationContext.getBean(sessionFactoryName)
        def configuration = (Configuration) sessionFactory.configuration
        def dialect = (Dialect) applicationContext.classLoader.loadClass((String) configuration.getProperty('hibernate.dialect')).newInstance()

        new GormDatabase(configuration, dialect)
    }

    void doGenerateChangeLog(File changeLogFile, Database originalDatabase) {
        DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false)
        CommandLineUtils.doGenerateChangeLog(
            changeLogFile?.path,
            originalDatabase,
            [] as CatalogAndSchema[],
            null,
            null,
            null,
            null,
            diffOutputControl,
        )
    }

    void doDiffToChangeLog(File changeLogFile, Database referenceDatabase, Database targetDatabase) {
        DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false)
        CommandLineUtils.doDiffToChangeLog(
            changeLogFile?.path,
            referenceDatabase,
            targetDatabase,
            diffOutputControl,
            null
        )
    }
}
