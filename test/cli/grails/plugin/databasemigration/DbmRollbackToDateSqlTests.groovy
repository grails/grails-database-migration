/* Copyright 2010-2012 SpringSource.
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
package grails.plugin.databasemigration

import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmRollbackToDateSqlTests extends AbstractScriptTests {

	void testRollbackToDateSql_stdout() {

		assertTableCount 1

		copyTestChangelog()
		executeAndCheck 'dbm-update'
		assertTableCount 4

		// fake out the dates to be able to rollback to particular date
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-1'",
			[new Timestamp((new Date() - 30).time)]
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-2'",
			[new Timestamp((new Date() - 20).time)]
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-3'",
			[new Timestamp((new Date() - 10).time)]

		// test parameter check
		executeAndCheck(['dbm-rollback-to-date-sql'], false)
		assertTrue output.contains('ERROR: Date must be specified')

		executeAndCheck(['dbm-rollback-to-date-sql',
			new SimpleDateFormat('yyyy-MM-dd').format(new Date() - 25)])

		// no db changes
		assertTableCount 4

		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN STREET1')
		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN STREET2')
		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN ZIPCODE')
		assertTrue output.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-2' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
		assertTrue output.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-3' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
	}

	void testRollbackToDateSql_file() {

		assertTableCount 1

		copyTestChangelog()
		executeAndCheck 'dbm-update'
		assertTableCount 4

		// fake out the dates to be able to rollback to particular date
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-1'",
			[new Timestamp((new Date() - 30).time)]
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-2'",
			[new Timestamp((new Date() - 20).time)]
		executeUpdate "update databasechangelog set dateexecuted=? where id='test-3'",
			[new Timestamp((new Date() - 10).time)]

		// test parameter check
		executeAndCheck(['dbm-rollback-to-date-sql'], false)
		assertTrue output.contains('ERROR: Date must be specified')

		def file = new File(CHANGELOG_DIR, 'testRollbackToDateSql_file.sql')
		file.deleteOnExit()
		assertFalse file.exists()

		executeAndCheck(['dbm-rollback-to-date-sql',
			new SimpleDateFormat('yyyy-MM-dd').format(new Date() - 25),
			CHANGELOG_DIR + '/testRollbackToDateSql_file.sql'])

		assertTrue file.exists()

		String fileContents = file.text

		// no db changes
		assertTableCount 4

		assertTrue fileContents.contains('ALTER TABLE PERSON DROP COLUMN STREET1')
		assertTrue fileContents.contains('ALTER TABLE PERSON DROP COLUMN STREET2')
		assertTrue fileContents.contains('ALTER TABLE PERSON DROP COLUMN ZIPCODE')
		assertTrue fileContents.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-2' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
		assertTrue fileContents.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-3' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
	}
}
