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

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmListLocksTests extends AbstractScriptTests {

	void testListLocks() {

		generateChangelog()

		executeUpdate 'drop table thing'

		executeAndCheck(['dbm-update-count', '1'])

		executeAndCheck 'dbm-list-locks'

		assertTrue output.contains('Starting dbm-list-locks for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
		assertTrue output.contains('Database change log locks for SA@jdbc:h2:tcp://localhost/./target/testdb/testdb')
		assertTrue output.contains('No locks')

		executeUpdate('update databasechangeloglock set locked=?, lockgranted=?, lockedby=?',
		              [true, new java.sql.Timestamp(System.currentTimeMillis()), 'cli_test'])
		executeAndCheck 'dbm-list-locks'

		assertFalse output.contains('No locks')
		assertTrue output.contains('- cli_test at')
	}
}
