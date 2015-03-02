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
import grails.dev.commands.ExecutionContext
import groovy.transform.CompileStatic
import groovy.util.logging.Commons
import liquibase.database.Database

@Commons
@CompileStatic
class DbmGenerateGormChangelogCommand implements ApplicationCommand, DatabaseMigrationCommand {

    final String description = 'Generates an initial changelog XML or YAML file from current GORM classes'

    @Override
    boolean handle(ExecutionContext executionContext) {
        def commandLine = executionContext.commandLine

        def filename = commandLine.remainingArgs[0]
        def dataSource = commandLine.optionValue('dataSource') as String

        def changeLogFile = resolveChangeLogFile(filename)
        if (changeLogFile) {
            if (changeLogFile.exists()) {
                log.error "ChangeLogFile ${changeLogFile} already exists!"
                return false
            }
            if (!changeLogFile.parentFile.exists()) {
                changeLogFile.parentFile.mkdirs()
            }
        }

        withGormDatabase(applicationContext, dataSource) { Database database ->
            doGenerateChangeLog(changeLogFile, database)
        }

        return true
    }
}
