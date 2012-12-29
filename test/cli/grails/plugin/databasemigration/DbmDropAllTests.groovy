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
class DbmDropAllTests extends AbstractScriptTests {

	void testDropAll() {

		generateChangelog()
		assertTableCount 1

		// drop the one created by hbm2ddl
		executeUpdate AbstractScriptTests.URL, 'drop table thing'
		assertTableCount 0

		executeAndCheck(['dbm-update-count', '1'])
		// 2 Liquibase + person
		assertTableCount 3

		executeAndCheck 'dbm-drop-all'
		// now just 2 Liquibase
		assertTableCount 2

		assertTrue output.contains(
			'Starting dbm-drop-all for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}

	void testDropAllForSecondaryDataSource() {
		String secondary_url = AbstractScriptTests.SECONDARY_URL

		generateSecondaryChagelog()
		assertTableCount 1, secondary_url

		// drop the one created by hbm2ddl
		executeUpdate secondary_url, 'drop table secondary_thing'
		assertTableCount 0, secondary_url

		executeAndCheck(['dbm-update-count', '1', '--dataSource=secondary'])
		// 2 Liquibase + person
		assertTableCount 3, secondary_url

		executeAndCheck (['dbm-drop-all', '--dataSource=secondary'])
		// now just 2 Liquibase
		assertTableCount 2, secondary_url

		assertTrue output.contains(
			'Starting dbm-drop-all for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')
	}
}
