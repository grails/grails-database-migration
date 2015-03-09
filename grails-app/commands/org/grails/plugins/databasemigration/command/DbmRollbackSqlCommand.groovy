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

@Commons
@CompileStatic
class DbmRollbackSqlCommand implements ApplicationCommand, ApplicationContextDatabaseMigrationCommand {

    final String description = 'Writes SQL to roll back the database to the state it was in when the tag was applied to STDOUT or a file'

    @Override
    boolean handle(ExecutionContext executionContext) {
        commandLine = executionContext.commandLine

        def tagName = args[0]
        if (!tagName) {
            throw new DatabaseMigrationException("The $name command requires a tag")
        }

        def filename = args[1]
        def contexts = optionValue('contexts')
        def defaultSchema = optionValue('defaultSchema')
        def dataSource = optionValue('dataSource')

        withLiquibase(defaultSchema, dataSource) { Liquibase liquibase ->
            withFileOrSystemOutWriter(filename) { Writer writer ->
                liquibase.rollback(tagName, contexts, writer)
            }
        }

        return true
    }
}
