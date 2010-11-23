/* Copyright 2006-2010 the original author or authors.
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

import grails.plugin.databasemigration.GormDatabaseSnapshotGenerator
import grails.plugin.databasemigration.GrailsChange
import grails.plugin.databasemigration.GrailsChangeLogParser
import grails.plugin.databasemigration.GrailsClassLoaderResourceAccessor
import grails.plugin.databasemigration.GrailsDiffStatusListener
import grails.plugin.databasemigration.GrailsPrecondition
import grails.plugin.databasemigration.MigrationRunner
import grails.plugin.databasemigration.MigrationUtils

import liquibase.change.ChangeFactory
import liquibase.parser.ChangeLogParserFactory
import liquibase.precondition.PreconditionFactory
import liquibase.resource.FileSystemResourceAccessor
import liquibase.snapshot.DatabaseSnapshotGeneratorFactory

class DatabaseMigrationGrailsPlugin {

	String version = '0.1'
	String grailsVersion = '1.3.0 > *'
	String author = 'Burt Beckwith'
	String authorEmail = 'beckwithb@vmware.com'
	String title = 'Grails Database Migration Plugin'
	String description = 'Grails Database Migration Plugin'
	String documentation = 'http://grails.org/plugin/database-migration'

	List pluginExcludes = ['grails-app/domain/**']

	def doWithSpring = {

		MigrationUtils.application = application

		if (application.warDeployed) {
			migrationResourceAccessor(GrailsClassLoaderResourceAccessor, application.classLoader)
		}
		else {
			String changelogLocation = MigrationUtils.changelogLocation
			String changelogLocationPath = new File(changelogLocation).path
			migrationResourceAccessor(FileSystemResourceAccessor, changelogLocationPath)
		}

		diffStatusListener(GrailsDiffStatusListener)
	}

	def doWithApplicationContext = { ctx ->
		ChangeLogParserFactory.instance.register new GrailsChangeLogParser(ctx)
		DatabaseSnapshotGeneratorFactory.instance.register new GormDatabaseSnapshotGenerator()
		ChangeFactory.instance.register GrailsChange
		PreconditionFactory.instance.register GrailsPrecondition

		MigrationRunner.autoRun()
	}
}
