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
import spock.lang.AutoCleanup

class DbmRollbackSqlCommandSpec extends DatabaseMigrationCommandSpec {

    final Class<ApplicationCommand> commandClass = DbmRollbackSqlCommand

    @AutoCleanup('delete')
    File outputFile = File.createTempFile('rollback', 'sql')

    def setup() {
        command.changeLogFile << CHANGE_LOG_CONTENT

        new DbmUpdateCountCommand(applicationContext: applicationContext).handle(getExecutionContext('1'))
        new DbmTagCommand(applicationContext: applicationContext).handle(getExecutionContext('test-tag'))
        new DbmUpdateCommand(applicationContext: applicationContext).handle(getExecutionContext())

        def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
        assert tables as Set == ['book', 'author', 'databasechangeloglock', 'databasechangelog'] as Set
    }

    def "writes SQL to roll back the database to the state it was in when the tag was applied to STDOUT"() {
        when:
            command.handle(getExecutionContext('test-tag'))

        then:
            def output = outputCapture.toString()
            output.contains('DROP TABLE PUBLIC.book;')
            !output.contains('DROP TABLE PUBLIC.author;')
    }

    def "writes SQL to roll back the database to the state it was in when the tag was applied to a file given as arguments"() {
        when:
            command.handle(getExecutionContext('test-tag', outputFile.canonicalPath))

        then:
            def output = outputFile.text
            output.contains('DROP TABLE PUBLIC.book;')
            !output.contains('DROP TABLE PUBLIC.author;')

    }

    def "an error occurs if tagName parameter is not specified"() {
        when:
            command.handle(getExecutionContext())

        then:
            outputCapture.toString().contains("The ${command.name} command requires a tag")
    }

    static final String CHANGE_LOG_CONTENT = '''
databaseChangeLog:
- changeSet:
    id: 1
    author: John Smith
    changes:
    - createTable:
        columns:
        - column:
            autoIncrement: true
            constraints:
              constraints:
                primaryKey: true
                primaryKeyName: authorPK
            name: id
            type: BIGINT
        - column:
            constraints:
              constraints:
                nullable: false
            name: version
            type: BIGINT
        - column:
            constraints:
              constraints:
                nullable: false
            name: name
            type: VARCHAR(255)
        tableName: author
- changeSet:
    id: 2
    author: John Smith
    changes:
    - createTable:
        columns:
        - column:
            autoIncrement: true
            constraints:
              constraints:
                primaryKey: true
                primaryKeyName: bookPK
            name: id
            type: BIGINT
        - column:
            constraints:
              constraints:
                nullable: false
            name: version
            type: BIGINT
        - column:
            constraints:
              constraints:
                nullable: false
            name: author_id
            type: BIGINT
        - column:
            constraints:
              constraints:
                nullable: false
            name: title
            type: VARCHAR(255)
        tableName: book
'''
}
