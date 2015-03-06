package org.grails.plugins.databasemigration.command

import grails.core.GrailsApplication
import grails.dev.commands.ApplicationCommand
import grails.dev.commands.ExecutionContext
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import grails.persistence.Entity
import grails.util.GrailsNameUtils
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.build.parsing.CommandLineParser
import org.grails.config.PropertySourcesConfig
import org.grails.plugins.databasemigration.liquibase.GrailsYamlChangeLogSerializer
import org.h2.Driver
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources
import spock.lang.AutoCleanup

abstract class ApplicationContextDatabaseMigrationCommandSpec extends DatabaseMigrationCommandSpec {

    @AutoCleanup
    GenericApplicationContext applicationContext

    ApplicationCommand command

    def setup() {
        if (!ServiceLocator.instance.packages.contains(CommonsLoggingLiquibaseLogger)) {
            ServiceLocator.instance.addPackageToScan(CommonsLoggingLiquibaseLogger.package.name)
        }
        if (!ChangeLogSerializerFactory.instance.serializers.any { String name, ChangeLogSerializer serializer -> serializer instanceof GrailsYamlChangeLogSerializer }) {
            ChangeLogSerializerFactory.instance.register(new GrailsYamlChangeLogSerializer())
        }

        applicationContext = new GenericApplicationContext()

        applicationContext.beanFactory.registerSingleton('dataSource', dataSource)

        def mutablePropertySources = new MutablePropertySources()
        mutablePropertySources.addFirst(new MapPropertySource('TestConfig', [
            'grails.plugin.databasemigration.changelogLocation': changeLogLocation.canonicalPath,
            'dataSource.dbCreate'                              : '',
            'dataSource.url'                                   : 'jdbc:h2:mem:testDb',
            'dataSource.username'                              : 'sa',
            'dataSource.password'                              : '',
            'dataSource.driverClassName'                       : Driver.name,
            'environments.other.dataSource.url'                : 'jdbc:h2:mem:otherDb',
        ]))
        def config = new PropertySourcesConfig(mutablePropertySources)

        def datastoreInitializer = new HibernateDatastoreSpringInitializer(domainClasses)
        datastoreInitializer.configuration = config
        datastoreInitializer.configureForBeanDefinitionRegistry(applicationContext)

        applicationContext.refresh()

        def grailsApplication = applicationContext.getBean(GrailsApplication)
        grailsApplication.config = config

        command = commandClass.newInstance()
        command.applicationContext = applicationContext
        command.changeLogFile.parentFile.mkdirs()
    }

    protected Class[] getDomainClasses() {
        [] as Class[]
    }

    abstract protected Class<ApplicationCommand> getCommandClass()

    protected ExecutionContext getExecutionContext(String... args) {
        new ExecutionContext(
            new CommandLineParser().parse(([GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(commandClass.name, 'Command'))] + args.toList()) as String[])
        )
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
