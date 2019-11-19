/*
 * Copyright 2015 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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
package org.grails.plugins.databasemigration.liquibase

import groovy.transform.CompileStatic
import liquibase.command.CommandResult
import liquibase.command.core.DiffToChangeLogCommand
import liquibase.diff.DiffResult
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.serializer.ChangeLogSerializerFactory

@CompileStatic
class GroovyDiffToChangeLogCommand extends DiffToChangeLogCommand {

    @Override
    protected CommandResult run() throws Exception {
        DiffResult diffResult = createDiffResult()

        if (!outputStream) {
            outputStream = System.out
        }

        if (!changeLogFile) {
            def serializer = ChangeLogSerializerFactory.instance.getSerializer('groovy')
            new DiffToChangeLog(diffResult, diffOutputControl).print(outputStream, serializer)
        } else {
            new DiffToChangeLog(diffResult, diffOutputControl).print(changeLogFile)
        }

        return new CommandResult("OK")
    }
}
