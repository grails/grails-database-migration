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

import grails.plugins.Plugin
import liquibase.integration.spring.SpringLiquibase
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.ChangeLogSerializerFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.plugins.databasemigration.liquibase.GormYamlChangeLogParser
import org.grails.plugins.databasemigration.liquibase.GormYamlChangeLogSerializer
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger

class DatabaseMigrationGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.0.BUILD-SNAPSHOT > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails Database Migration Plugin" // Headline display name of the plugin
    def author = "Kazuki YAMAMOTO"
    def authorEmail = "yam.kazuki@gmail.com"
    def description = 'Grails Database Migration Plugin'
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/database-migration"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Online location of the plugin's browseable source code.
    //def scm = [url: "http://svn.codehaus.org/grails-plugins/"]

    @Override
    Closure doWithSpring() {
        configureLiquibase()

        return { ->
            def dataSourceNames = getDataSourceNames()
            dataSourceNames.each { String dataSourceName ->
                def migrationConfig = getMigrationConfig(dataSourceName)
                if (migrationConfig.get('updateOnStart')) {
                    "springLiquibase_${dataSourceName}"(SpringLiquibase) {
                        dropFirst = migrationConfig.containsKey('dropOnStart') ? migrationConfig.get('dropOnStart') : false
                        dataSource = ref(dataSourceName)
                        changeLog = migrationConfig.get('updateOnStartFileName') ?: 'classpath:/changelog.yml'
                        contexts = migrationConfig.get('updateOnStartContexts') ?: null
                        labels = migrationConfig.get('updateOnStartLabels') ?: null
                        defaultSchema = migrationConfig.get('updateOnStartDefaultSchema') ?: null
                    }
                }
            }
        }
    }

    private static void configureLiquibase() {
        if (!ServiceLocator.instance.packages.contains(CommonsLoggingLiquibaseLogger.package.name)) {
            ServiceLocator.instance.addPackageToScan(CommonsLoggingLiquibaseLogger.package.name)
        }
        if (!ChangeLogSerializerFactory.instance.serializers.any { String name, ChangeLogSerializer serializer -> serializer instanceof GormYamlChangeLogSerializer }) {
            ChangeLogSerializerFactory.instance.register(new GormYamlChangeLogSerializer())
        }
        if (!ChangeLogParserFactory.instance.parsers.any { ChangeLogParser parser -> parser instanceof GormYamlChangeLogParser }) {
            ChangeLogParserFactory.instance.register(new GormYamlChangeLogParser())
        }
    }

    private Set<String> getDataSourceNames() {
        def dataSources = config.getProperty('dataSources', Map, [:])
        if (!dataSources) {
            return ['dataSource']
        }
        return dataSources.keySet()
    }

    private Map<String, Object> getMigrationConfig(String dataSourceName) {
        if (dataSourceName == 'dataSource') {
            return config.grails.plugin.databasemigration
        }
        return config.grails.plugin.databasemigration."${dataSourceName.substring('dataSource_'.size())}"
    }
}
