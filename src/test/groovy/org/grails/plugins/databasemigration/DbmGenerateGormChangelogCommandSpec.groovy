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
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.parser.core.yaml.YamlChangeLogParser
import liquibase.resource.FileSystemResourceAccessor

class DbmGenerateGormChangelogCommandSpec extends DatabaseMigrationCommandSpec {

    final Class<ApplicationCommand> commandClass = DbmGenerateGormChangelogCommand

    def "writes Change Log to copy the current state of the database to STDOUT"() {
        when:
            def result = command.handle(getExecutionContext())

        then:
            result

        and:
            outputCapture.toString() =~ /createTable tableName="book"/
    }

    def "writes Change Log to copy the current state of the database to a file given as arguments"() {
        given:
            def filename = 'changelog.yml'

        when:
            def result = command.handle(getExecutionContext(filename))

        then:
            result

        and:
            def changeLog = toDatabaseChangeLog(new File(changeLogLocation, filename))
            changeLog.changeSets.size() == 3
            changeLog.changeSets[0].changes.size() == 1
            changeLog.changeSets[0].changes[0].getSerializableFieldValue('tableName') == 'author'
    }

    def "an error occurs if changeLogFile already exists"() {
        given:
            def filename = 'changelog.yml'
            def changeLogFile = new File(changeLogLocation, filename)
            assert changeLogFile.createNewFile()

        when:
            def result = command.handle(getExecutionContext(filename))

        then:
            !result
            outputCapture.toString().contains("ChangeLogFile ${changeLogFile.canonicalPath} already exists!")

    }

    @Override
    protected Class[] getDomainClasses() {
        [Book, Author] as Class[]
    }

    private static DatabaseChangeLog toDatabaseChangeLog(File file) {
        new YamlChangeLogParser().parse(file.canonicalPath, new ChangeLogParameters(), new FileSystemResourceAccessor())
    }
}
