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

import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.ScriptDatabaseMigrationCommand

class DbmRollbackCountCommandSpec extends ScriptDatabaseMigrationCommandSpec {

    final Class<ScriptDatabaseMigrationCommand> commandClass = DbmRollbackCountCommand

    def setup() {
        command.changeLogFile << CHANGE_LOG_CONTENT

        new DbmUpdateCommand().handle(getExecutionContext())

        def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
        assert tables as Set == ['book', 'author', 'databasechangeloglock', 'databasechangelog'] as Set
    }

    def "rolls back the specified number of change sets"() {
        when:
            command.handle(getExecutionContext('1'))

        then:
            def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
            tables as Set == ['author', 'databasechangeloglock', 'databasechangelog'] as Set
    }

    def "an error occurs if the count parameter is not specified"() {
        when:
            command.handle(getExecutionContext())

        then:
            def e = thrown(DatabaseMigrationException)
            e.message == "The ${command.name} command requires a change set number argument"
    }

    def "an error occurs if the count parameter is not number"() {
        when:
            command.handle(getExecutionContext('one'))

        then:
            def e = thrown(DatabaseMigrationException)
            e.message == 'The change set number argument \'one\' isn\'t a number'
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
