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
import groovy.sql.Sql
import org.h2.Driver
import org.springframework.jdbc.datasource.DriverManagerDataSource
import spock.lang.AutoCleanup

import java.sql.Connection

class DbmDiffCommandSpec extends DatabaseMigrationCommandSpec {

    final Class<ApplicationCommand> commandClass = DbmDiffCommand

    @AutoCleanup
    Connection otherDbConnection

    @AutoCleanup
    Sql otherDbSql

    def setup() {
        def otherDbDataSource = new DriverManagerDataSource('jdbc:h2:mem:otherDb', 'sa', '')
        otherDbDataSource.driverClassName = Driver.name
        otherDbConnection = otherDbDataSource.connection
        otherDbSql = new Sql(otherDbConnection)
        otherDbSql.executeUpdate '''
CREATE TABLE book (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, CONSTRAINT PK_BOOK PRIMARY KEY (id))
'''
        sql.executeUpdate '''
CREATE TABLE book (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, price INT NOT NULL, CONSTRAINT PK_BOOK PRIMARY KEY (id));
CREATE TABLE author (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT PK_AUTHOR PRIMARY KEY (id));
'''
    }

    def "writes Change Log to update the database to STDOUT"() {
        when:
            command.handle(getExecutionContext('other'))

        then:
            outputCapture.toString() =~ '''
<\\?xml version="1\\.0" encoding="UTF-8" standalone="no"\\?>
<databaseChangeLog .*?>
    <changeSet author=".*?" id=".*?">
        <createTable tableName="AUTHOR">
            <column autoIncrement="true" name="ID" type="INT\\(10\\)">
                <constraints primaryKey="true" primaryKeyName="PK_AUTHOR"/>
            </column>
            <column name="NAME" type="VARCHAR\\(255\\)">
                <constraints nullable="false"/>
            </column>
        </createTable>
    </changeSet>
    <changeSet author=".*?" id=".*?">
        <addColumn tableName="BOOK">
            <column name="PRICE" type="INTEGER\\(10\\)">
                <constraints nullable="false"/>
            </column>
        </addColumn>
    </changeSet>
</databaseChangeLog>
'''.trim()
    }

    def "writes Change Log to update the database to a file given as arguments"() {
        given:
            def outputChangeLog = new File(changeLogLocation, 'diff.yml')

        when:
            command.handle(getExecutionContext('other', outputChangeLog.name))

        then:
            outputChangeLog.text =~ '''
databaseChangeLog:
- changeSet:
    id: .*?
    author: .*?
    changes:
    - createTable:
        columns:
        - column:
            autoIncrement: true
            constraints:
              constraints:
                primaryKey: true
                primaryKeyName: PK_AUTHOR
            name: ID
            type: INT\\(10\\)
        - column:
            constraints:
              constraints:
                nullable: false
            name: NAME
            type: VARCHAR\\(255\\)
        tableName: AUTHOR
- changeSet:
    id: .*?
    author: .*?
    changes:
    - addColumn:
        columns:
        - column:
            constraints:
              constraints:
                nullable: false
            name: PRICE
            type: INTEGER\\(10\\)
        tableName: BOOK
'''.trim()
    }

    def "an error occurs if the otherEnv parameter is not specified"() {
        when:
            def result = command.handle(getExecutionContext())

        then:
            !result
            outputCapture.toString().contains('You must specify the environment to diff against')
    }

    def "an error occurs if other environment and current environment is same"() {
        when:
            def result = command.handle(getExecutionContext('development'))

        then:
            !result
            outputCapture.toString().contains('You must specify a different environment than the one the command is running in')
    }
}
