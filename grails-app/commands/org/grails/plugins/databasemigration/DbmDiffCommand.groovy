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
import grails.util.Environment
import groovy.transform.CompileStatic
import groovy.util.logging.Commons
import liquibase.database.Database

@Commons
@CompileStatic
class DbmDiffCommand implements ApplicationCommand, DatabaseMigrationCommand {

    final String description = 'Compares two databases and creates a changelog that will make the changes required to bring them into sync'

    @Override
    boolean handle(ExecutionContext executionContext) {
        def commandLine = executionContext.commandLine

        def otherEnv = commandLine.remainingArgs[0]
        if (!otherEnv) {
            log.error 'You must specify the environment to diff against'
            return false
        }
        if (Environment.getEnvironment(otherEnv) == Environment.current || otherEnv == Environment.current.name) {
            log.error 'You must specify a different environment than the one the command is running in'
            return false
        }

        def filename = commandLine.remainingArgs[1]
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

        def defaultSchema = commandLine.optionValue('defaultSchema') as String
        def dataSource = commandLine.optionValue('dataSource') as String

        withDatabase(defaultSchema, getDataSourceConfig(dataSource)) { Database referenceDatabase ->
            withDatabase(defaultSchema, getDataSourceConfig(dataSource, otherEnv)) { Database targetDatabase ->
                doDiffToChangeLog(changeLogFile, referenceDatabase, targetDatabase)
            }
        }

        if (filename && commandLine.hasOption('add')) {
            appendToChangeLog(changeLogFile)
        }

        return true
    }
}
