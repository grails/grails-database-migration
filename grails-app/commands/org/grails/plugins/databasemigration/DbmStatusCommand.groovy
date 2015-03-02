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
import liquibase.Liquibase

@CompileStatic
class DbmStatusCommand implements ApplicationCommand, DatabaseMigrationCommand {

    final String description = 'Outputs count or list of unrun change sets to STDOUT or a file'

    @Override
    boolean handle(ExecutionContext executionContext) {
        def commandLine = executionContext.commandLine

        def filename = commandLine.remainingArgs[0]
        def verbose = commandLine.hasOption('verbose') ? Boolean.parseBoolean(commandLine.optionValue('verbose') as String) : true
        def contexts = commandLine.optionValue('contexts') as String
        def defaultSchema = commandLine.optionValue('defaultSchema') as String
        def dataSource = commandLine.optionValue('dataSource') as String

        withLiquibase(changeLogFile, defaultSchema, getDataSourceConfig(dataSource)) { Liquibase liquibase ->
            withFileOrSystemOutWriter(filename) { Writer writer ->
                liquibase.reportStatus((boolean) verbose, contexts, writer)
            }
        }

        true
    }
}
