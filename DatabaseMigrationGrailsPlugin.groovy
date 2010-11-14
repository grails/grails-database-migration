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

import grails.plugin.databasemigration.GrailsDiffStatusListener
import grails.plugin.databasemigration.MigrationUtils

import grails.util.BuildSettingsHolder
import grails.util.Environment

import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.resource.FileSystemResourceAccessor

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH

class DatabaseMigrationGrailsPlugin {

	String version = '0.1'
	String grailsVersion = '1.3.0 > *'
	String author = 'Burt Beckwith'
	String authorEmail = 'beckwithb@vmware.com'
	String title = 'Grails Database Migration Plugin'
	String description = 'Grails Database Migration Plugin'
	String documentation = 'http://grails.org/plugin/database-migration'

	String pluginExcludes = [
		'src/docs'
	]

	def doWithSpring = {

		if (Environment.current == Environment.DEVELOPMENT) {
			String changelogLocation = MigrationUtils.config.changelogLocation ?: 'grails-app/migrations'
			String changelogLocationPath = new File(BuildSettingsHolder.settings.baseDir, changelogLocation).path
			migrationResourceAccessor(FileSystemResourceAccessor, changelogLocationPath)
		}
		else {
			migrationResourceAccessor(ClassLoaderResourceAccessor, AH.application.classLoader)
		}

		diffStatusListener(GrailsDiffStatusListener)
	}
}
