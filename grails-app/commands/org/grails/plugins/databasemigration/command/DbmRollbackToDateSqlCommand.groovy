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

import grails.dev.commands.ApplicationCommand
import grails.dev.commands.ExecutionContext
import groovy.transform.CompileStatic
import groovy.util.logging.Commons
import liquibase.Liquibase
import org.grails.plugins.databasemigration.DatabaseMigrationException

import java.text.ParseException

@Commons
@CompileStatic
class DbmRollbackToDateSqlCommand implements ApplicationCommand, ApplicationContextDatabaseMigrationCommand {

    final String description = 'Writes SQL to roll back the database to the state it was in at the given date/time to STDOUT or a file'

    @Override
    boolean handle(ExecutionContext executionContext) {
        def commandLine = executionContext.commandLine

        def dateStr = commandLine.remainingArgs[0]
        if (!dateStr) {
            throw new DatabaseMigrationException('Date must be specified as two strings with the format "yyyy-MM-dd HH:mm:ss" or as one strings with the format "yyyy-MM-dd"')
        }

        String timeStr = null
        String filename = null
        if (commandLine.remainingArgs[1]) {
            if (commandLine.remainingArgs.size() > 2 || isTimeFormat(commandLine.remainingArgs[1])) {
                timeStr = commandLine.remainingArgs[1]
            } else {
                filename = commandLine.remainingArgs[1]
            }
        }

        def date = null
        try {
            date = parseDateTime(dateStr, timeStr)
        } catch (ParseException e) {
            throw new DatabaseMigrationException("Problem parsing '$dateStr${timeStr ? " $timeStr" : ''}' as a Date: $e.message")
        }

        filename = filename ?: commandLine.remainingArgs[2]
        def contexts = commandLine.optionValue('contexts') as String
        def defaultSchema = commandLine.optionValue('defaultSchema') as String
        def dataSource = commandLine.optionValue('dataSource') as String

        withLiquibase(defaultSchema, dataSource) { Liquibase liquibase ->
            withFileOrSystemOutWriter(filename) { Writer writer ->
                liquibase.rollback(date, contexts, writer)
            }
        }

        return true
    }
}
