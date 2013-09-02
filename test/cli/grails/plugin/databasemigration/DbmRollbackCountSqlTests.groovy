/* Copyright 2010-2013 SpringSource.
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
class DbmRollbackCountSqlTests extends AbstractScriptTests {

	void testRollbackCountSql() {

		assertTableCount 1

		copyTestChangelog()
		executeAndCheck 'dbm-update'
		assertTableCount 4

		// test parameter check
		executeAndCheck(['dbm-rollback-count-sql'], false)
		assertTrue output.contains('ERROR: The dbm-rollback-count-sql script requires a change set count argument')

		executeAndCheck(['dbm-rollback-count-sql', '1'])

		// no db changes
		assertTableCount 4

		assertTrue output.contains(
			'Starting dbm-rollback-count-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')

		assertTrue output.contains(
			'ALTER TABLE PERSON DROP COLUMN ZIPCODE')
		assertTrue output.contains(
			"DELETE FROM xdatabasechangelogx  WHERE ID='test-3' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy';")
	}

	void testRollbackCountSqlForSecondaryDataSource() {

		assertTableCount 1, SECONDARY_URL

		copyTestChangelog('test.changelog', SECONDARY_TEST_CHANGELOG)

		executeAndCheck (['dbm-update', '--dataSource=secondary'])
		assertTableCount 4, SECONDARY_URL

		// test parameter check
		executeAndCheck(['dbm-rollback-count-sql', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: The dbm-rollback-count-sql script requires a change set count argument')

		executeAndCheck(['dbm-rollback-count-sql', '1', '--dataSource=secondary'])

		// no db changes
		assertTableCount 4, SECONDARY_URL

		assertTrue output.contains(
			'Starting dbm-rollback-count-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')

		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN ZIPCODE')

		assertTrue output.contains(
			"DELETE FROM xdatabasechangelogx  WHERE ID='test-3' AND AUTHOR='burt' AND FILENAME='changelog.cli.secondary-test.groovy';")
	}
}
