package org.grails.plugins.databasemigration.command

import grails.dev.commands.ApplicationCommand
import org.grails.plugins.databasemigration.DatabaseMigrationException
import spock.lang.AutoCleanup

class DbmPreviousChangesetSqlCommandSpec extends ApplicationContextDatabaseMigrationCommandSpec {

    @Override
    protected Class<ApplicationCommand> getCommandClass() {
        return DbmPreviousChangesetSqlCommand
    }

    @AutoCleanup('delete')
    File outputFile = File.createTempFile('previous', 'sql')


    def setup() {
        command.changeLogFile << CHANGE_LOG_CONTENT

        new DbmUpdateCommand(applicationContext: applicationContext).handle(getExecutionContext())

        def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
        assert tables as Set == ['book', 'author', 'databasechangeloglock', 'databasechangelog'] as Set
    }


    void "The last SQL change sets to STDOUT"() {
        when:
        command.handle(getExecutionContext('1'))

        then:
        def output = output.toString()
        output.contains('CREATE TABLE PUBLIC.book (id BIGINT AUTO_INCREMENT NOT NULL, version BIGINT NOT NULL, author_id BIGINT NOT NULL, title VARCHAR(255) NOT NULL, CONSTRAINT bookPK PRIMARY KEY (id));')
    }

    void "The last SQL change sets to a file given as arguments"() {
        when:
        command.handle(getExecutionContext('1', outputFile.canonicalPath))

        then:
        def output = outputFile.text
        output.contains('CREATE TABLE PUBLIC.book (id BIGINT AUTO_INCREMENT NOT NULL, version BIGINT NOT NULL, author_id BIGINT NOT NULL, title VARCHAR(255) NOT NULL, CONSTRAINT bookPK PRIMARY KEY (id));')

    }

    void "The second last SQL change sets to a file given as arguments"() {
        when:
        command.handle(getExecutionContext('1', outputFile.canonicalPath, "--skip=1"))

        then:
        def output = outputFile.text
        output.contains('CREATE TABLE PUBLIC.author (id BIGINT AUTO_INCREMENT NOT NULL, version BIGINT NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT authorPK PRIMARY KEY (id));')

    }

    void "an error occurs if the count parameter is not specified"() {
        when:
        command.handle(getExecutionContext())

        then:
        def e = thrown(DatabaseMigrationException)
        e.message == "The ${command.name} command requires a change set number argument"
    }

    void "an error occurs if the count parameter is not number"() {
        when:
        command.handle(getExecutionContext('one'))

        then:
        def e = thrown(DatabaseMigrationException)
        e.message == 'The change set number argument \'one\' isn\'t a number'
    }


    static final String CHANGE_LOG_CONTENT = '''
databaseChangeLog = {

    changeSet(author: "John Smith", id: "1") {
        createTable(tableName: "author") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "authorPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "John Smith", id: "2") {
        createTable(tableName: "book") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "bookPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "author_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
'''
}
