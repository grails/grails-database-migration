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

import grails.config.ConfigMap
import grails.util.Environment
import grails.util.GrailsNameUtils
import groovy.transform.CompileStatic
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.build.parsing.CommandLine
import org.grails.cli.profile.ExecutionContext
import org.grails.config.CodeGenConfig
import org.grails.plugins.databasemigration.EnvironmentAwareCodeGenConfig
import org.grails.plugins.databasemigration.liquibase.GormYamlChangeLogParser
import org.grails.plugins.databasemigration.liquibase.GormYamlChangeLogSerializer
import org.grails.plugins.databasemigration.liquibase.log.GrailsConsoleLogger

@CompileStatic
trait ScriptDatabaseMigrationCommand implements DatabaseMigrationCommand {

    ConfigMap config
    ConfigMap sourceConfig
    ExecutionContext executionContext
    CommandLine commandLine

    void handle(ExecutionContext executionContext) {
        this.executionContext = executionContext
        setCommandLine(executionContext.commandLine)
        setConfig(executionContext.config)

        configureLiquibase()
        handle()
    }

    void configureLiquibase() {
        if (!ServiceLocator.instance.packages.contains(GrailsConsoleLogger.package.name)) {
            ServiceLocator.instance.addPackageToScan(GrailsConsoleLogger.package.name)
        }
        if (!ChangeLogSerializerFactory.instance.serializers.any { String name, ChangeLogSerializer serializer -> serializer instanceof GormYamlChangeLogSerializer }) {
            ChangeLogSerializerFactory.instance.register(new GormYamlChangeLogSerializer())
        }
        if (!ChangeLogParserFactory.instance.parsers.any { ChangeLogParser parser -> parser instanceof GormYamlChangeLogParser }) {
            ChangeLogParserFactory.instance.register(new GormYamlChangeLogParser())
        }
    }

    abstract void handle()

    String getName() {
        return GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(getClass().getName(), "Command"))
    }

    void setConfig(ConfigMap config) {
        this.sourceConfig = config
        this.config = new EnvironmentAwareCodeGenConfig(sourceConfig as CodeGenConfig, Environment.current.name)
    }

    String optionValue(String name) {
        commandLine.optionValue(name)?.toString()
    }

    boolean hasOption(String name) {
        commandLine.hasOption(name)
    }

    List<String> getArgs() {
        executionContext.commandLine.remainingArgs
    }

    ConfigMap getEnvironmentConfig(String environment = Environment.current.name) {
        new EnvironmentAwareCodeGenConfig(sourceConfig as CodeGenConfig, environment)
    }
}
