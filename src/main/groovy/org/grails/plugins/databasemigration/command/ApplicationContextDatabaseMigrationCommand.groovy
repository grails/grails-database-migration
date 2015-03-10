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
import grails.core.GrailsApplication
import grails.dev.commands.ExecutionContext
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import liquibase.database.Database
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.hibernate.cfg.Configuration
import org.hibernate.dialect.Dialect
import org.springframework.context.ConfigurableApplicationContext

@CompileStatic
trait ApplicationContextDatabaseMigrationCommand implements DatabaseMigrationCommand {

    ConfigurableApplicationContext applicationContext

    boolean handle(ExecutionContext executionContext) {
        commandLine = executionContext.commandLine
        contexts = optionValue('contexts')
        defaultSchema = optionValue('defaultSchema')
        dataSource = optionValue('dataSource')

        handle()

        return true
    }

    abstract void handle()

    @Override
    ConfigMap getConfig() {
        applicationContext.getBean(GrailsApplication).config
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

    @CompileDynamic
    private Database createGormDatabase(ConfigurableApplicationContext applicationContext, String dataSource = null) {
        String sessionFactoryName = dataSource ? '&sessionFactory_' + dataSource : '&sessionFactory'

        def sessionFactory = applicationContext.getBean(sessionFactoryName)
        def configuration = (Configuration) sessionFactory.configuration
        def dialect = (Dialect) applicationContext.classLoader.loadClass((String) configuration.getProperty('hibernate.dialect')).newInstance()

        Database database = new GormDatabase(configuration, dialect)
        configureDatabase(database)

        return database
    }
}
