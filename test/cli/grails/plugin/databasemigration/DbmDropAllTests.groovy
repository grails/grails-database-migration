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
package grails.plugin.databasemigration

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmDropAllTests extends AbstractScriptTests {

	void testDropAll() {

		generateChangelog()
		assertTableCount 1

		// drop the one created by hbm2ddl
		executeUpdate 'drop table thing'
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
}
