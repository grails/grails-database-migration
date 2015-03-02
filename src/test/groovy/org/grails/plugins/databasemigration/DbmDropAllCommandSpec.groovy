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

class DbmDropAllCommandSpec extends DatabaseMigrationCommandSpec {

    final Class<ApplicationCommand> commandClass = DbmDropAllCommand

    def "drops all database objects"() {
        given:
            sql.executeUpdate 'CREATE TABLE book (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, CONSTRAINT PK_BOOK PRIMARY KEY (id))'
            assert sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'')

        when:
            command.handle(getExecutionContext())

        then:
            !sql.rows('SELECT table_name FROM information_schema.tables WHERE table_type = \'TABLE\'')
    }
}
