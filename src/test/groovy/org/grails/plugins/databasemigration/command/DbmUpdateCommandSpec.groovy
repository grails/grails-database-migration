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

class DbmUpdateCommandSpec extends ScriptDatabaseMigrationCommandSpec {

    final Class<ScriptDatabaseMigrationCommand> commandClass = DbmUpdateCommand

    def "updates database to current version"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext())

        then:
            def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
            tables as Set == ['book', 'author', 'databasechangeloglock', 'databasechangelog'] as Set

        and:
            def authors = sql.rows('SELECT name FROM author').collect { it.name }
            authors as Set == ['Mary', 'Amelia'] as Set
    }

    def "updates database to current version with contexts"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext('--contexts=test'))

        then:
            def tables = sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'').collect { it.table_name.toLowerCase() }
            tables as Set == ['book', 'author', 'databasechangeloglock', 'databasechangelog'] as Set

        and:
            def authors = sql.rows('SELECT name FROM author').collect { it.name }
            authors as Set == ['Amelia'] as Set
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
- changeSet:
    id: 3
    author: John Smith
    changes:
    - addForeignKeyConstraint:
        baseColumnNames: author_id
        baseTableName: book
        constraintName: FK_4sac2ubmnqva85r8bk8fxdvbf
        deferrable: false
        initiallyDeferred: false
        referencedColumnNames: id
        referencedTableName: author
- changeSet:
    id: 4
    author: John Smith
    context: development
    changes:
    - insert:
        tableName: author
        columns:
        - column:
            name: name
            value: Mary
- changeSet:
    id: 5
    author: John Smith
    context: test
    changes:
    - insert:
        tableName: author
        columns:
        - column:
            name: name
            value: Amelia
'''
}
