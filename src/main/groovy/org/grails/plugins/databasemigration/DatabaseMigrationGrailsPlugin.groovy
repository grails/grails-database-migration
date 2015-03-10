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
import liquibase.configuration.GlobalConfiguration
import liquibase.configuration.LiquibaseConfiguration
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.servicelocator.ServiceLocator
import org.grails.plugins.databasemigration.liquibase.GormDatabase
import org.grails.plugins.databasemigration.liquibase.GrailsLiquibase
import org.grails.plugins.databasemigration.liquibase.GroovyChangeLogParser
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
                def isDefaultDetaSource = 'dataSource' == dataSourceName
                def migrationConfig = getMigrationConfig(dataSourceName)
                if (migrationConfig.get('updateOnStart')) {
                    "springLiquibase_${dataSourceName}"(GrailsLiquibase, applicationContext) {
                        dropFirst = migrationConfig.containsKey('dropOnStart') ? migrationConfig.get('dropOnStart') : false
                        dataSource = ref(dataSourceName)
                        if (migrationConfig.get('updateOnStartFileName')) {
                            changeLog = migrationConfig.get('updateOnStartFileName')
                        } else {
                            if (isDefaultDetaSource) {
                                changeLog = migrationConfig.get('updateOnStartFileName') ?: 'classpath:/changelog.groovy'
                            } else {
                                changeLog = migrationConfig.get('updateOnStartFileName') ?: "classpath:/changelog-${dataSourceName - 'dataSource_'}.groovy"
                            }
                        }
                        contexts = getContexts(migrationConfig)
                        labels = migrationConfig.get('updateOnStartLabels') ?: null
                        defaultSchema = migrationConfig.get('updateOnStartDefaultSchema') ?: null
                        databaseChangeLogTableName = migrationConfig.get('databaseChangeLogTableName') ?: null
                        databaseChangeLogLockTableName = migrationConfig.get('databaseChangeLogLockTableName') ?: null
                    }
                }
            }
        }
    }

    private void configureLiquibase() {
        if (!ServiceLocator.instance.packages.contains(CommonsLoggingLiquibaseLogger.package.name)) {
            ServiceLocator.instance.addPackageToScan(CommonsLoggingLiquibaseLogger.package.name)
        }
        if (!ServiceLocator.instance.packages.contains(GormDatabase.package.name)) {
            ServiceLocator.instance.addPackageToScan(GormDatabase.package.name)
        }
        def groovyChangeLogParser = ChangeLogParserFactory.instance.parsers.find { ChangeLogParser changeLogParser -> changeLogParser instanceof GroovyChangeLogParser } as GroovyChangeLogParser
        groovyChangeLogParser.applicationContext = applicationContext
        groovyChangeLogParser.config = config

        def databaseChangeLogTableName = migrationConfig.get('databaseChangeLogTableName')
        if (databaseChangeLogTableName) {
            LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration).databaseChangeLogTableName = databaseChangeLogTableName
        }
        def databaseChangeLogLockTableName = migrationConfig.get('databaseChangeLogLockTableName')
        if (databaseChangeLogLockTableName) {
            LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration).databaseChangeLogLockTableName = databaseChangeLogLockTableName
        }
    }

    private String getContexts(Map<String, Object> migrationConfig) {
        def contexts = migrationConfig.get('updateOnStartContexts')
        if (!contexts) {
            return null
        }
        if (contexts instanceof List) {
            return contexts.join(',')
        }
        return contexts
    }

    private Set<String> getDataSourceNames() {
        def dataSources = config.getProperty('dataSources', Map, [:])
        if (!dataSources) {
            return ['dataSource']
        }
        return dataSources.keySet()
    }

    private Map<String, Object> getMigrationConfig(String dataSourceName = 'dataSource') {
        if (dataSourceName == 'dataSource') {
            return config.grails.plugin.databasemigration
        }
        return config.grails.plugin.databasemigration."${dataSourceName - 'dataSource_'}"
    }
}
