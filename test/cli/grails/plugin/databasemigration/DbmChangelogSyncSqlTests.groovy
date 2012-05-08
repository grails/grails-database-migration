/* Copyright 2010-2011 SpringSource.
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
class DbmChangelogSyncSqlTests extends AbstractScriptTests {

	void testChangelogSyncSql() {

		generateChangelog()

		executeAndCheck 'dbm-changelog-sync-sql'

		assertTrue output.contains('CREATE TABLE DATABASECHANGELOGLOCK')
		assertTrue output.contains('INSERT INTO DATABASECHANGELOGLOCK')

		assertTrue output.contains('CREATE TABLE DATABASECHANGELOG')
		assertTrue output.contains('INSERT INTO DATABASECHANGELOG')

		assertTrue output.contains(
			'Starting dbm-changelog-sync-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}


    void testChangelogSyncSqlForSecondaryDataSource() {

        executeAndCheck(['dbm-generate-changelog', AbstractScriptTests.SECONDARY_TEST_CHANGELOG, '--dataSource=secondary'])

   		executeAndCheck(['dbm-changelog-sync-sql', '--dataSource=secondary'])

   		assertTrue output.contains('CREATE TABLE DATABASECHANGELOGLOCK')
   		assertTrue output.contains('INSERT INTO DATABASECHANGELOGLOCK')

   		assertTrue output.contains('CREATE TABLE DATABASECHANGELOG')
   		assertTrue output.contains('INSERT INTO DATABASECHANGELOG')

   		assertTrue output.contains(
   			'Starting dbm-changelog-sync-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')
   	}
}
