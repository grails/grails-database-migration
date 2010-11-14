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

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */

import grails.util.GrailsNameUtils
import grails.util.GrailsUtil

import java.text.SimpleDateFormat

import org.apache.log4j.Logger
import org.springframework.util.StringUtils

includeTargets << grailsScript('_GrailsBootstrap')

DAY_DATE_FORMAT = 'yyyy-MM-dd'
FULL_DATE_FORMAT = DAY_DATE_FORMAT + ' HH:mm:ss'

target(dbmInit: 'General initialization, also creates a Liquibase instance') {
	depends(classpath, checkVersion, configureProxy, bootstrap, loadApp)

	try {
		hyphenatedScriptName = GrailsNameUtils.getScriptName(scriptName)
		log = Logger.getLogger('grails.plugin.databasemigration.Scripts')
		MigrationUtils = classLoader.loadClass('grails.plugin.databasemigration.MigrationUtils')
		argsList = StringUtils.tokenizeToStringArray(args, '\n', true, true) as List
	}
	catch (e) {
		printStackTrace e
		throw e
	}
}

printStackTrace = { e ->
	GrailsUtil.deepSanitize e
	e.printStackTrace()
}

calculateDestination = { int argIndex = 0 ->
	argsList[argIndex] ? new PrintStream(argsList[argIndex]) : System.out
}
newPrintWriter = { -> new PrintWriter(calculateDestination()) }
newOutputStreamWriter = { -> new OutputStreamWriter(calculateDestination()) }

doAndClose = { Closure c ->
	try {
		database = MigrationUtils.getDatabase(appCtx.dataSource.connection)
		liquibase = MigrationUtils.getLiquibase(database)

		def dsConfig = config.dataSource
		String dbDesc = dsConfig.jndiName ? "JNDI $dsConfig.jndiName" : "$dsConfig.username @ $dsConfig.url"
		ant.echo message: "Starting $hyphenatedScriptName for database $dbDesc"
		c()
		ant.echo message: "Finished $hyphenatedScriptName"
	}
	catch (e) {
		printStackTrace e
		exit 1
	}
	finally {
		closeConnection database?.connection
	}
}

closeConnection = { try { it?.close() } catch (ignored) { } }

errorAndDie = { String message ->
	ant.echo "\nERROR: $message"
	exit 1
}

calculateDate = { ->

	def dateFormat
	String dateString
	if (argsMap.day instanceof CharSequence) {
		if (argsMap.time instanceof CharSequence) {
			dateString = argsMap.day.trim() + ' ' + argsMap.time.trim()
			dateFormat = new SimpleDateFormat(FULL_DATE_FORMAT)
		}
		else {
			dateString = argsMap.day.trim()
			dateFormat = new SimpleDateFormat(DAY_DATE_FORMAT)
		}
	}
	else {
		dateString = argsList.join(' ')
		dateFormat = new SimpleDateFormat(dateString.contains(' ') ? FULL_DATE_FORMAT : DAY_DATE_FORMAT)
	}

	if (dateString) {
		try {
			return dateFormat.parse(dateString)
		}
		catch (e) {
			errorAndDie "Problem parsing '$dateString' as a Date: $e.message"
		}
	}

	errorAndDie 'Date must be specified either with --day and --time parameters or as\n' +
	     '       two strings, and the combined format must be yyyy-MM-dd HH:mm:ss'
}
