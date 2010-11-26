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
package grails.plugin.databasemigration

import java.sql.Connection

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.diff.Diff

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class MigrationUtils {

	private MigrationUtils() {
		// static only
	}

	/**
	 * Set at startup.
	 */
	static GrailsApplication application

	static Database getDatabase(Connection connection, String defaultSchema) {
		def database = DatabaseFactory.instance.findCorrectDatabaseImplementation(
			new JdbcConnection(connection))
		if (defaultSchema) {
			database.defaultSchemaName = defaultSchema
		}
//		database.defaultSchemaName = connection.catalog // TODO
		database
	}

	static Database getDatabase(String defaultSchema = null) {
		getDatabase application.mainContext.dataSource.connection, defaultSchema
	}

	static Liquibase getLiquibase(Database database) {
		getLiquibase database, getChangelogFileName()
	}

	static Liquibase getLiquibase(Database database, String changelogFileName) {
		def resourceAccessor = application.mainContext.migrationResourceAccessor
		new Liquibase(changelogFileName, resourceAccessor, database)
	}

	static ConfigObject getConfig() {
		application.config.grails.plugin.databasemigration
	}

	static String getDbDocLocation() {
		getConfig().dbDocLocation ?: 'target/dbdoc'
	}

	static String getChangelogFileName() {
		getConfig().changelogFileName ?: 'changelog.groovy'
	}

	static String getChangelogLocation() {
		getConfig().changelogLocation ?: 'grails-app/conf/migrations'
	}

	static ConfigObject getChangelogProperties() {
		getConfig().changelogProperties
	}
}
