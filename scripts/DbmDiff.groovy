/* Copyright 2006-2010 the original author or authors.
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

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */

import grails.util.Environment

import java.sql.DriverManager

import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.diff.Diff

includeTargets << new File("$databaseMigrationPluginDir/scripts/_DatabaseMigrationCommon.groovy")

target(dbmDiff: 'Writes description of differences to standard out') {
	depends dbmInit

	String otherEnv = argsList[0]
	if (!otherEnv) {
		errorAndDie 'You must specify the environment to diff against'
	}

	if (otherEnv == Environment.current.name) {
		errorAndDie 'You must specify a different environment than the one the script is running in'
	}

	def thisDatabase
	def otherDatabase
	try {
		ant.echo message: "Starting $hyphenatedScriptName against environment '$otherEnv'"

		thisDatabase = MigrationUtils.getDatabase(appCtx.dataSource.connection)

		otherDatabase = buildOtherDatabase(otherEnv)
		def diff = new Diff(thisDatabase, otherDatabase)
		diff.addStatusListener appCtx.diffStatusListener
		diff.compare().printChangeLog calculateDestination(1), otherDatabase

		ant.echo message: "Finished $hyphenatedScriptName"
	}
	catch (e) {
		printStackTrace e
		exit 1
	}
	finally {
		closeConnection thisDatabase?.connection
		closeConnection otherDatabase?.connection
	}
}

// TODO this will fail with JNDI or encryption codec
buildOtherDatabase = { String otherEnv ->
	def configSlurper = new ConfigSlurper(otherEnv)
	configSlurper.binding = binding.variables
	def otherDsConfig = configSlurper.parse(classLoader.loadClass('DataSource')).dataSource
	Class.forName otherDsConfig.driverClassName, true, classLoader

	def connection = DriverManager.getConnection(
		otherDsConfig.url ?: null, otherDsConfig.username ?: null, otherDsConfig.password ?: null)
	DatabaseFactory.instance.findCorrectDatabaseImplementation new JdbcConnection(connection)
}

setDefaultTarget dbmDiff
