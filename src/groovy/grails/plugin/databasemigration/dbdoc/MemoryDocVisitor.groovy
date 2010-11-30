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
package grails.plugin.databasemigration.dbdoc

import java.lang.reflect.Field
import java.lang.reflect.Method

import liquibase.change.Change
import liquibase.changelog.visitor.DBDocVisitor
import liquibase.database.Database
import liquibase.database.structure.Column
import liquibase.database.structure.DatabaseObject
import liquibase.database.structure.Table
import liquibase.exception.DatabaseHistoryException
import liquibase.resource.ResourceAccessor
import liquibase.snapshot.DatabaseSnapshot

import org.springframework.util.ReflectionUtils

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class MemoryDocVisitor extends DBDocVisitor {

	private static final int MAX_RECENT_CHANGE = 50
	private Database database
   private SortedSet changeLogs
   private Map<String, List<Change>> changesByAuthor
   private Map<DatabaseObject, List<Change>> changesByObject
   private Map<DatabaseObject, List<Change>> changesToRunByObject
   private Map<String, List<Change>> changesToRunByAuthor
   private List<Change> changesToRun
   private List<Change> recentChanges

   private String rootChangeLog

	MemoryDocVisitor(Database database) {
		super(database)
		this.database = database

		changeLogs = getFieldValue('changeLogs')
		changesByAuthor = getFieldValue('changesByAuthor')
		changesByObject = getFieldValue('changesByObject')
		changesToRunByObject = getFieldValue('changesToRunByObject')
		changesToRunByAuthor = getFieldValue('changesToRunByAuthor')
		changesToRun = getFieldValue('changesToRun')
		recentChanges = getFieldValue('recentChanges')
	}

	Map generateHTML(ResourceAccessor resourceAccessor) {

		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		new ChangeLogListWriter(files).writeHTML(changeLogs)
		new TableListWriter(files).writeHTML(new TreeSet<Object>(snapshot.getTables()))
		new AuthorListWriter(files).writeHTML(new TreeSet<Object>(changesByAuthor.keySet()))

		HTMLWriter authorWriter = new AuthorWriter(files, database)
		for (String author : changesByAuthor.keySet()) {
			authorWriter.writeHTML(author, changesByAuthor.get(author), changesToRunByAuthor.get(author), rootChangeLog)
		}

		HTMLWriter tableWriter = new TableWriter(files, database)
		for (Table table : snapshot.getTables()) {
			tableWriter.writeHTML(table, changesByObject.get(table), changesToRunByObject.get(table), rootChangeLog)
		}

		HTMLWriter columnWriter = new ColumnWriter(files, database)
		for (Column column : snapshot.getColumns()) {
			columnWriter.writeHTML(column, changesByObject.get(column), changesToRunByObject.get(column), rootChangeLog)
		}

		ChangeLogWriter changeLogWriter = new ChangeLogWriter(resourceAccessor, files)
		for (changeLog in changeLogs) {
			changeLogWriter.writeChangeLog(changeLog.logicalPath, changeLog.physicalPath)
		}

		HTMLWriter pendingChangesWriter = new PendingChangesWriter(files, database)
		pendingChangesWriter.writeHTML('index', null, changesToRun, rootChangeLog)

		HTMLWriter pendingSQLWriter = new PendingSQLWriter(files, database)
		pendingSQLWriter.writeHTML('sql', null, changesToRun, rootChangeLog)

		HTMLWriter recentChangesWriter = new RecentChangesWriter(files, database)
		if (recentChanges.size() > MAX_RECENT_CHANGE) {
			recentChanges = recentChanges.subList(0, MAX_RECENT_CHANGE)
		}
		recentChangesWriter.writeHTML('index', recentChanges, null, rootChangeLog)

//		Method copyFile = getClass().getSuperclass().getDeclaredMethod('copyFile', String.class, File.class)
//		copyFile.setAccessible(true)
//ReflectionUtils.invokeMethod(copyFile, this, 'liquibase/dbdoc/stylesheet.css', files)
//ReflectionUtils.invokeMethod(copyFile, this, 'liquibase/dbdoc/index.html', files)
//ReflectionUtils.invokeMethod(copyFile, this, 'liquibase/dbdoc/globalnav.html', files)
//ReflectionUtils.invokeMethod(copyFile, this, 'liquibase/dbdoc/overview-summary.html', files)
		files
	}

	Map writeChangelogs(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]
		new ChangeLogListWriter(files).writeHTML(changeLogs)
		files
	}

	Map writeTableList(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]
		new TableListWriter(files).writeHTML(new TreeSet<Object>(snapshot.getTables()))
		files
	}

	Map writeAuthorList(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]
		new AuthorListWriter(files).writeHTML(new TreeSet<Object>(changesByAuthor.keySet()))
		files
	}

	Map writeAuthors(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		HTMLWriter authorWriter = new AuthorWriter(files, database)
		for (String author : changesByAuthor.keySet()) {
			authorWriter.writeHTML(author, changesByAuthor.get(author), changesToRunByAuthor.get(author), rootChangeLog)
		}

		files
	}

	Map writeTables(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		HTMLWriter tableWriter = new TableWriter(files, database)
		for (Table table : snapshot.getTables()) {
			tableWriter.writeHTML(table, changesByObject.get(table), changesToRunByObject.get(table), rootChangeLog)
		}

		files
	}

	Map writeColumns(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		HTMLWriter columnWriter = new ColumnWriter(files, database)
		for (Column column : snapshot.getColumns()) {
			columnWriter.writeHTML(column, changesByObject.get(column), changesToRunByObject.get(column), rootChangeLog)
		}

		files
	}

	Map writeChangeLogs(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		ChangeLogWriter changeLogWriter = new ChangeLogWriter(resourceAccessor, files)
		for (changeLog in changeLogs) {
			changeLogWriter.writeChangeLog(changeLog.logicalPath, changeLog.physicalPath)
		}

		files
	}

	// TODO
	Map writePendingChanges(ResourceAccessor resourceAccessor) {
		DatabaseSnapshot snapshot = database.createDatabaseSnapshot(null, null)
		Map files = [:]

		HTMLWriter pendingChangesWriter = new PendingChangesWriter(files, database)
		pendingChangesWriter.writeHTML('index', null, changesToRun, rootChangeLog)

		HTMLWriter pendingSQLWriter = new PendingSQLWriter(files, database)
		pendingSQLWriter.writeHTML('sql', null, changesToRun, rootChangeLog)

		HTMLWriter recentChangesWriter = new RecentChangesWriter(files, database)
		if (recentChanges.size() > MAX_RECENT_CHANGE) {
			recentChanges = recentChanges.subList(0, MAX_RECENT_CHANGE)
		}
		recentChangesWriter.writeHTML('index', recentChanges, null, rootChangeLog)
	}

	private getFieldValue(String name) {
		Field field = ReflectionUtils.findField(getClass().superclass, name)
		field.accessible = true
		ReflectionUtils.getField field, this
	}
}
