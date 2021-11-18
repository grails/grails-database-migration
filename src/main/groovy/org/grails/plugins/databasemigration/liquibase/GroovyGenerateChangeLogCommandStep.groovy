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
package org.grails.plugins.databasemigration.liquibase

import groovy.transform.CompileStatic
import liquibase.Scope
import liquibase.command.CommandResultsBuilder
import liquibase.command.CommandScope
import liquibase.command.core.InternalGenerateChangelogCommandStep
import liquibase.command.core.InternalSnapshotCommandStep
import liquibase.database.Database
import liquibase.database.ObjectQuotingStrategy
import liquibase.diff.DiffResult
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.util.StringUtil

@CompileStatic
class GroovyGenerateChangeLogCommandStep extends InternalGenerateChangelogCommandStep {

    public static final String[] COMMAND_NAME = new String[] {"groovyGenerateChangeLog"};

    private static final String INFO_MESSAGE =
            "When generating formatted SQL changelogs, it is important to decide if batched statements\n" +
                    "should be split or not.  For storedlogic objects, the default behavior is 'splitStatements:false'\n." +
                    "All other objects default to 'splitStatements:true'.  See https://docs.liquibase.org for additional information.";

    @Override
    void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();

        outputBestPracticeMessage();

        String changeLogFile = StringUtil.trimToNull(commandScope.getArgumentValue(CHANGELOG_FILE_ARG));
        if (changeLogFile != null && changeLogFile.toLowerCase().endsWith(".sql")) {
            Scope.getCurrentScope().getUI().sendMessage("\n" + INFO_MESSAGE + "\n");
            Scope.getCurrentScope().getLog(getClass()).info("\n" + INFO_MESSAGE + "\n");
        }

        final Database referenceDatabase = commandScope.getArgumentValue(REFERENCE_DATABASE_ARG);

        InternalSnapshotCommandStep.logUnsupportedDatabase(referenceDatabase, this.getClass());

        DiffResult diffResult = createDiffResult(commandScope);

        DiffToChangeLog changeLogWriter = new DiffToChangeLog(diffResult, commandScope.getArgumentValue(DIFF_OUTPUT_CONTROL_ARG));

        changeLogWriter.setChangeSetAuthor(commandScope.getArgumentValue(AUTHOR_ARG));
        changeLogWriter.setChangeSetContext(commandScope.getArgumentValue(CONTEXT_ARG));
        changeLogWriter.setChangeSetPath(changeLogFile);

        ObjectQuotingStrategy originalStrategy = referenceDatabase.getObjectQuotingStrategy();
        try {
            referenceDatabase.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
            def serializer = ChangeLogSerializerFactory.instance.getSerializer('groovy')
            if (StringUtil.trimToNull(changeLogFile) != null) {
                changeLogWriter.print(changeLogFile, serializer)
            } else {
                PrintStream outputStream = new PrintStream(resultsBuilder.getOutputStream());
                try {
                    changeLogWriter.print(outputStream, serializer);
                } finally {
                    outputStream.flush()
                }

            }
            if (StringUtil.trimToNull(changeLogFile) != null) {
                Scope.getCurrentScope().getUI().sendMessage("Generated changelog written to " + new File(changeLogFile).getAbsolutePath());
            }
        } finally {
            referenceDatabase.setObjectQuotingStrategy(originalStrategy);
        }
    }

    @Override
    String[][] defineCommandNames() {
        return new String[][] { COMMAND_NAME }
    }
}
