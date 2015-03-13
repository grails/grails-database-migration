package org.grails.plugins.databasemigration

import grails.core.GrailsApplication
import grails.dev.commands.ApplicationCommand
import liquibase.exception.ValidationFailedException
import org.grails.plugins.databasemigration.command.ApplicationContextDatabaseMigrationCommandSpec
import org.grails.plugins.databasemigration.command.DbmUpdateCommand

class GroovyChangeLogSpec extends ApplicationContextDatabaseMigrationCommandSpec {

    final Class<ApplicationCommand> commandClass = DbmUpdateCommand

    static List<String> called

    def setup() {
        called = []
    }

    def "checks execution order"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext())

        then:
            called == ['init', 'validate', 'change']
    }

    def "validate with warn message"() {
        given:
            command.changeLogFile << """
databaseChangeLog = {
    changeSet(author: "John Smith", id: "1") {
        grailsChange {
            validate {
                ${GroovyChangeLogSpec.name}.called << 'validate'
                warn('warn message')
            }
            change {
                ${GroovyChangeLogSpec.name}.called << 'change'
            }
        }
    }
}
"""
        when:
            command.handle(getExecutionContext())

        then:
            outputCapture.toString().contains('warn message')
            called == ['validate', 'change']
    }

    def "validate with error message"() {
        given:
            command.changeLogFile << """
databaseChangeLog = {
    changeSet(author: "John Smith", id: "1") {
        grailsChange {
            validate {
                ${GroovyChangeLogSpec.name}.called << 'validate'
                error('error message')
            }
            change {
                ${GroovyChangeLogSpec.name}.called << 'change'
            }
        }
    }
}
"""
        when:
            command.handle(getExecutionContext())

        then:
            def e = thrown(ValidationFailedException)
            e.message.contains('1 changes have validation failures')
            e.message.contains('error message, changelog.groovy::1::John Smith')
            called == ['validate']
    }

    def "checks binding variables"() {
        given:
            command.changeLogFile << """
databaseChangeLog = {
    changeSet(author: "John Smith", id: "1") {
        grailsChange {
            change {
                assert changeSet.id == '1'
                assert resourceAccessor.toString() == 'liquibase.resource.FileSystemResourceAccessor(${changeLogLocation.canonicalPath})'
                assert ctx.hashCode() == ${applicationContext.hashCode()}
                assert application.hashCode() == ${applicationContext.getBean(GrailsApplication).hashCode()}
                ${GroovyChangeLogSpec.name}.called << 'change'
            }
        }
    }
}
"""
        when:
            command.handle(getExecutionContext())

        then:
            called == ['change']
    }

    def "executes sql statements"() {
        given:
            command.changeLogFile << """
import groovy.sql.Sql
import liquibase.statement.core.InsertStatement

databaseChangeLog = {
    changeSet(author: "John Smith", id: "1") {
        grailsChange {
            change {
                new Sql(database.connection.underlyingConnection).executeUpdate('CREATE TABLE book (id INT)')
                new Sql(databaseConnection.underlyingConnection).executeUpdate('INSERT INTO book (id) VALUES (1)')
                new Sql(connection).executeUpdate('INSERT INTO book (id) VALUES (2)')
                sqlStatement(new InsertStatement(null, null, 'book').addColumnValue('id', 3))
                sqlStatements([new InsertStatement(null, null, 'book').addColumnValue('id', 4), new InsertStatement(null, null, 'book').addColumnValue('id', 5)])
            }
        }
    }
}
"""

        when:
            command.handle(getExecutionContext())

        then:
            sql.rows('SELECT id FROM book').collect { it.id } as Set == [1, 2, 3, 4, 5] as Set
    }

    static final String CHANGE_LOG_CONTENT = """
databaseChangeLog = {
    changeSet(author: "John Smith", id: "1") {
        grailsChange {
            init { ${GroovyChangeLogSpec.name}.called << 'init' }
            validate { ${GroovyChangeLogSpec.name}.called << 'validate' }
            change { ${GroovyChangeLogSpec.name}.called << 'change' }
            rollback { ${GroovyChangeLogSpec.name}.called << 'rollback' }
        }
    }
}
"""
}
