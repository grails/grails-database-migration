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
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import grails.util.Environment
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import liquibase.database.Database
import org.grails.config.PropertySourcesConfig
import org.grails.datastore.mapping.config.Entity
import org.grails.datastore.mapping.core.connections.ConnectionSource
import org.grails.orm.hibernate.HibernateDatastore
import org.grails.plugins.databasemigration.DatabaseMigrationTransactionManager
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.spi.JdbcServices
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.springframework.context.ConfigurableApplicationContext

@CompileStatic
trait ApplicationContextDatabaseMigrationCommand implements DatabaseMigrationCommand {

    ConfigurableApplicationContext applicationContext

    Boolean skipBootstrap = true

    boolean handle(ExecutionContext executionContext) {
        this.executionContext = executionContext
        handle()
        return true
    }

    void setExecutionContext(ExecutionContext executionContext) {
        commandLine = executionContext.commandLine
        contexts = optionValue('contexts')
        defaultSchema = optionValue('defaultSchema')
        dataSource = optionValue('dataSource')
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

    private Database createGormDatabase(ConfigurableApplicationContext applicationContext, String dataSource) {
        final String defaultDataSource = "DEFAULT"
        String dataSourceName = dataSource ? dataSource : defaultDataSource

        boolean isDefault = dataSourceName == defaultDataSource
        String sessionFactoryName = "sessionFactory"
        if (!isDefault) {
            sessionFactoryName = sessionFactoryName + '_' + dataSourceName
        }

        def serviceRegistry = applicationContext.getBean(sessionFactoryName, SessionFactoryImplementor).serviceRegistry.parentServiceRegistry

        Dialect dialect = serviceRegistry.getService(JdbcServices.class).dialect

        Database database = new GormDatabase(dialect, serviceRegistry)
        configureDatabase(database)

        return database
    }

    ConfigMap getEnvironmentConfig(String environment) {
        return (ConfigMap) environmentWith(environment) {
            new PropertySourcesConfig(((PropertySourcesConfig) config).getPropertySources())
        }
    }

    private Object environmentWith(String environment, Closure closure) {
        def originalEnvironment = Environment.current
        System.setProperty(Environment.KEY, environment)
        try {
            return closure.call()
        } finally {
            System.setProperty(Environment.KEY, originalEnvironment.name)
        }
    }

    void withTransaction(Closure callable) {
        new DatabaseMigrationTransactionManager(applicationContext, dataSource).withTransaction(callable)
    }
}
