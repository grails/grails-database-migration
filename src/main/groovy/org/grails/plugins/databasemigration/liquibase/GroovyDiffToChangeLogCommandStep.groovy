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
import liquibase.command.CommandBuilder
import liquibase.command.CommandDefinition
import liquibase.command.CommandResult
import liquibase.command.CommandResultsBuilder
import liquibase.command.CommandScope
import liquibase.command.core.DiffToChangeLogCommand
import liquibase.command.core.InternalDiffChangelogCommandStep
import liquibase.command.core.InternalSnapshotCommandStep
import liquibase.database.Database
import liquibase.database.ObjectQuotingStrategy
import liquibase.diff.DiffGeneratorFactory
import liquibase.diff.DiffResult
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.DiffOutputControl
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.exception.DatabaseException
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.snapshot.DatabaseSnapshot
import liquibase.snapshot.InvalidExampleException
import liquibase.util.StringUtil

@CompileStatic
class GroovyDiffToChangeLogCommandStep extends InternalDiffChangelogCommandStep {

    public static final String[] COMMAND_NAME = new String[] {"groovyDiffChangelog"}

    @Override
    void run(CommandResultsBuilder resultsBuilder) {
        CommandScope commandScope = resultsBuilder.getCommandScope()
        Database referenceDatabase = commandScope.getArgumentValue(REFERENCE_DATABASE_ARG);
        String changeLogFile = commandScope.getArgumentValue(CHANGELOG_FILE_ARG);

        InternalSnapshotCommandStep.logUnsupportedDatabase(referenceDatabase, this.getClass());

        DiffResult diffResult = createDiffResult(commandScope);

        PrintStream outputStream = new PrintStream(resultsBuilder.getOutputStream());

        outputBestPracticeMessage();

        ObjectQuotingStrategy originalStrategy = referenceDatabase.getObjectQuotingStrategy();
        try {
            referenceDatabase.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
            def serializer = ChangeLogSerializerFactory.instance.getSerializer('groovy')
            if (StringUtil.trimToNull(changeLogFile) == null) {
                createDiffToChangeLogObject(diffResult, commandScope).print(outputStream, serializer)
            } else {
                createDiffToChangeLogObject(diffResult, commandScope).print(changeLogFile, serializer)
            }
        }
        finally {
            referenceDatabase.setObjectQuotingStrategy(originalStrategy);
            outputStream.flush();
        }
        resultsBuilder.addResult("statusCode", 0);

    }

    @Override
    String[][] defineCommandNames() {
        return new String[][] { COMMAND_NAME }
    }

}
