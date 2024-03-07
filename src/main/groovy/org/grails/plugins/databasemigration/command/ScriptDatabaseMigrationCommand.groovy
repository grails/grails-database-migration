/*
 * Copyright 2015-2024 original authors
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
import org.grails.config.CodeGenConfig
import org.grails.plugins.databasemigration.EnvironmentAwareCodeGenConfig

@CompileStatic
trait ScriptDatabaseMigrationCommand implements DatabaseMigrationCommand {

    ConfigMap config
    ConfigMap sourceConfig

    abstract void handle()

    String getName() {
        return GrailsNameUtils.getScriptName(GrailsNameUtils.getLogicalName(getClass().getName(), "Command"))
    }

    void setConfig(ConfigMap config) {
        this.sourceConfig = config
        this.config = new EnvironmentAwareCodeGenConfig(sourceConfig as CodeGenConfig, Environment.current.name)
    }
}
