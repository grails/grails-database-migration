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

import spock.lang.AutoCleanup

class DbmUpdateSqlCommandSpec extends ScriptDatabaseMigrationCommandSpec {

    final Class<ScriptDatabaseMigrationCommand> commandClass = DbmUpdateSqlCommand

    @AutoCleanup('delete')
    File outputFile = File.createTempFile('update', 'sql')

    def "writes SQL to update database to STDOUT"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext())

        then:
            def output = outputCapture.toString()
            output.contains('CREATE TABLE PUBLIC.author (id BIGINT AUTO_INCREMENT, version BIGINT, name VARCHAR(255));')
            output.contains('CREATE TABLE PUBLIC.book (id BIGINT AUTO_INCREMENT, version BIGINT, author_id BIGINT, title VARCHAR(255));')
            output.contains('ALTER TABLE PUBLIC.book ADD CONSTRAINT FK_4sac2ubmnqva85r8bk8fxdvbf FOREIGN KEY (author_id) REFERENCES PUBLIC.author (id);')
            output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Mary\');')
            output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Amelia\');')
    }

    def "writes SQL to update database with contexts"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext('--contexts=test'))

        then:
            def output = outputCapture.toString()
            output.contains('CREATE TABLE PUBLIC.author (id BIGINT AUTO_INCREMENT, version BIGINT, name VARCHAR(255));')
            output.contains('CREATE TABLE PUBLIC.book (id BIGINT AUTO_INCREMENT, version BIGINT, author_id BIGINT, title VARCHAR(255));')
            output.contains('ALTER TABLE PUBLIC.book ADD CONSTRAINT FK_4sac2ubmnqva85r8bk8fxdvbf FOREIGN KEY (author_id) REFERENCES PUBLIC.author (id);')
            !output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Mary\');')
            output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Amelia\');')
    }

    def "writes SQL to update database to a file given as arguments"() {
        given:
            command.changeLogFile << CHANGE_LOG_CONTENT

        when:
            command.handle(getExecutionContext(outputFile.canonicalPath))

        then:
            def output = outputFile.text
            output.contains('CREATE TABLE PUBLIC.author (id BIGINT AUTO_INCREMENT, version BIGINT, name VARCHAR(255));')
            output.contains('CREATE TABLE PUBLIC.book (id BIGINT AUTO_INCREMENT, version BIGINT, author_id BIGINT, title VARCHAR(255));')
            output.contains('ALTER TABLE PUBLIC.book ADD CONSTRAINT FK_4sac2ubmnqva85r8bk8fxdvbf FOREIGN KEY (author_id) REFERENCES PUBLIC.author (id);')
            output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Mary\');')
            output.contains('INSERT INTO PUBLIC.author (name) VALUES (\'Amelia\');')
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
