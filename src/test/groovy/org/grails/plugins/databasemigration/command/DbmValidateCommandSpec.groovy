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

import liquibase.exception.ChangeLogParseException
import org.grails.plugins.databasemigration.ScriptDatabaseMigrationCommand

class DbmValidateCommandSpec extends ScriptDatabaseMigrationCommandSpec {

    final Class<ScriptDatabaseMigrationCommand> commandClass = DbmValidateCommand

    def "checks the valid changelog"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext())

        then:
            noExceptionThrown()
    }

    def "checks the invalid changelog"() {
        given:
            command.changeLogFile << 'xxx'

        when:
            command.handle(getExecutionContext())

        then:
            thrown(ChangeLogParseException)
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
