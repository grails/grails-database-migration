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

import grails.dev.commands.ApplicationCommand
import grails.dev.commands.ExecutionContext
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import grails.persistence.Entity
import grails.util.GrailsNameUtils
import grails.util.Holders
import groovy.sql.Sql
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.build.parsing.CommandLineParser
import org.grails.config.PropertySourcesConfig
import org.grails.plugins.databasemigration.liquibase.GormYamlChangeLogSerializer
import org.h2.Driver
import org.junit.Rule
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger
import org.springframework.boot.test.OutputCapture
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources
import org.springframework.jdbc.datasource.DriverManagerDataSource
import spock.lang.AutoCleanup
import spock.lang.Specification

import java.sql.Connection

abstract class DatabaseMigrationCommandSpec extends Specification {

    @Rule
    OutputCapture outputCapture = new OutputCapture()

    @AutoCleanup
    GenericApplicationContext applicationContext

    @AutoCleanup
    Connection connection

    @AutoCleanup
    Sql sql

    @AutoCleanup('deleteDir')
    File changeLogLocation

    ApplicationCommand command

    def setup() {
        def serviceLocator = ServiceLocator.instance
        serviceLocator.addPackageToScan(CommonsLoggingLiquibaseLogger.package.name)

        ChangeLogSerializerFactory.instance.register(new GormYamlChangeLogSerializer())

        applicationContext = new GenericApplicationContext()

        def dataSource = new DriverManagerDataSource('jdbc:h2:mem:testDb', 'sa', '')
        dataSource.driverClassName = Driver.name
        applicationContext.beanFactory.registerSingleton('dataSource', dataSource)
        connection = dataSource.connection
        sql = new Sql(connection)

        def datastoreInitializer = new HibernateDatastoreSpringInitializer(domainClasses)
        datastoreInitializer.configureForBeanDefinitionRegistry(applicationContext)

        applicationContext.refresh()

        changeLogLocation = File.createTempDir()

        def mutablePropertySources = new MutablePropertySources()
        mutablePropertySources.addFirst(new MapPropertySource('TestConfig', [
            'grails.plugin.databasemigration.changelogLocation': changeLogLocation.canonicalPath,
            'dataSource.url'                                   : 'jdbc:h2:mem:testDb',
            'dataSource.username'                              : 'sa',
            'dataSource.password'                              : '',
            'dataSource.driverClassName'                       : Driver.name,
            'environments.other.dataSource.url'                : 'jdbc:h2:mem:otherDb',
        ]))
        Holders.config = new PropertySourcesConfig(mutablePropertySources)

        command = commandClass.newInstance()
        command.applicationContext = applicationContext
        command.changeLogFile.parentFile.mkdirs()
    }

    protected Class[] getDomainClasses() {
        [] as Class[]
    }

    protected File getChangeLogFile(String filename) {
        new File(changeLogLocation, filename)
    }

    abstract protected Class<ApplicationCommand> getCommandClass()

    protected ExecutionContext getExecutionContext(String... args) {
        new ExecutionContext(
            new CommandLineParser().parse(([GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(commandClass.name, 'Command'))] + args.toList()) as String[])
        )
    }

    def cleanup() {
        Holders.clear()
    }
}


@Entity
class Book {
    String title
    Author author
}

@Entity
class Author {
    String name
    static hasMany = [books: Book]
}
