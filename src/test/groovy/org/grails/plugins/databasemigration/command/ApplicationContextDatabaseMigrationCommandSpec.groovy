package org.grails.plugins.databasemigration.command

import grails.config.Config
import grails.core.GrailsApplication
import grails.dev.commands.ApplicationCommand
import grails.dev.commands.ExecutionContext
import grails.orm.bootstrap.HibernateDatastoreSpringInitializer
import grails.persistence.Entity
import grails.util.GrailsNameUtils
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.build.parsing.CommandLineParser
import org.grails.cli.GrailsCli
import org.grails.config.CodeGenConfig
import org.grails.config.PropertySourcesConfig
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.grails.plugins.databasemigration.liquibase.GroovyChangeLogParser
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

    Config config

    def setup() {
        if (!ServiceLocator.instance.packages.contains(CommonsLoggingLiquibaseLogger.package.name)) {
            ServiceLocator.instance.addPackageToScan(CommonsLoggingLiquibaseLogger.package.name)
        }
        if (!ServiceLocator.instance.packages.contains(GormDatabase.package.name)) {
            ServiceLocator.instance.addPackageToScan(GormDatabase.package.name)
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
        config = new PropertySourcesConfig(mutablePropertySources)

        def datastoreInitializer = new HibernateDatastoreSpringInitializer(domainClasses)
        datastoreInitializer.configuration = config
        datastoreInitializer.configureForBeanDefinitionRegistry(applicationContext)

        applicationContext.refresh()

        def grailsApplication = applicationContext.getBean(GrailsApplication)
        grailsApplication.config = config

        def groovyChangeLogParser = ChangeLogParserFactory.instance.parsers.find { ChangeLogParser changeLogParser -> changeLogParser instanceof GroovyChangeLogParser } as GroovyChangeLogParser
        groovyChangeLogParser.applicationContext = applicationContext
        groovyChangeLogParser.config = config

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

    protected org.grails.cli.profile.ExecutionContext getScriptExecutionContext(String... args) {
        def codeGenConfig = new CodeGenConfig()
        codeGenConfig.mergeMap(config.flatten())
        codeGenConfig.mergeMap(config.flatten(), true)
        def executionContext = new GrailsCli.ExecutionContextImpl(codeGenConfig)
        executionContext.commandLine = new CommandLineParser().parse(([GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(commandClass.name, 'Command'))] + args.toList()) as String[])
        executionContext
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
