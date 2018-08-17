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
import org.grails.config.PropertySourcesConfig
import org.grails.orm.hibernate.cfg.Settings
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.grails.plugins.databasemigration.liquibase.GroovyChangeLogParser
import org.grails.testing.GrailsUnitTest
import org.h2.Driver
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources
import org.springframework.core.env.PropertyResolver
import spock.lang.AutoCleanup

abstract class ApplicationContextDatabaseMigrationCommandSpec extends DatabaseMigrationCommandSpec implements GrailsUnitTest {

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
        applicationContext.beanFactory.registerSingleton('grailsApplication', grailsApplication)

        MutablePropertySources propertySources = new MutablePropertySources()
        propertySources.addFirst(new MapPropertySource("TestConfig", getConfiguration()))
        List<Class> domainClasses = getDomainClasses()
        config = new PropertySourcesConfig(propertySources)

        def datastoreInitializer = new HibernateDatastoreSpringInitializer((PropertyResolver) config, domainClasses)
        datastoreInitializer.configureForBeanDefinitionRegistry((GenericApplicationContext) applicationContext)

        applicationContext.refresh()

        def grailsApplication = applicationContext.getBean(GrailsApplication)
        grailsApplication.config = config

        def groovyChangeLogParser = ChangeLogParserFactory.instance.parsers.find { ChangeLogParser changeLogParser -> changeLogParser instanceof GroovyChangeLogParser } as GroovyChangeLogParser
        groovyChangeLogParser.applicationContext = applicationContext
        groovyChangeLogParser.config = config

        if (commandClass) {
            command = createCommand(commandClass)
        }
    }

    protected ApplicationCommand createCommand(Class<ApplicationCommand> applicationCommand) {
        def command = applicationCommand.newInstance()
        command.setApplicationContext(applicationContext)
        command.changeLogFile.parentFile.mkdirs()
        return command
    }

    protected Class[] getDomainClasses() {
        [] as Class[]
    }

    protected Class<ApplicationCommand> getCommandClass() {
        null
    }

    protected ExecutionContext getExecutionContext(Class<ApplicationCommand> clazz = commandClass, String... args) {
        def commandClassName = GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(clazz.name, 'Command'))
        new ExecutionContext(
                new CommandLineParser().parse(([commandClassName] + args.toList()) as String[])
        )
    }

    void cleanup() { }

    private Map getConfiguration() {
        [
                'grails.plugin.databasemigration.changelogLocation'                      : changeLogLocation.canonicalPath,
                (Settings.SETTING_DATASOURCE + '.dbCreate')                              : '',
                (Settings.SETTING_DATASOURCE + '.username')                              : 'sa',
                (Settings.SETTING_DATASOURCE + '.password')                              : '',
                (Settings.SETTING_DATASOURCE + '.driverClassName')                       : Driver.name,
                ('environments.test' + '.' + Settings.SETTING_DATASOURCE + '.url'): 'jdbc:h2:mem:testDb',
                ('environments.development' + '.' + Settings.SETTING_DATASOURCE + '.url'): 'jdbc:h2:mem:testDb',
                ('environments.other' + '.' + Settings.SETTING_DATASOURCE + '.url')      : 'jdbc:h2:mem:otherDb',
        ]
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
