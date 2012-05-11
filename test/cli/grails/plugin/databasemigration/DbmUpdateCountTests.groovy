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

import java.sql.SQLException

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmUpdateCountTests extends AbstractScriptTests {

	void testUpdateCount() {

		assertTableCount 1

		// should fail since the table isn't there yet
		String message = shouldFail(SQLException) {
			executeUpdate '''
				insert into person(version, name, street1, street2, zipcode)
				values (0, 'test.name', 'test.street1', 'test.street2', '12345')
			'''
		}
		assertTrue message.contains('Table "PERSON" not found')

		copyTestChangelog()

		// test parameter check
		executeAndCheck(['dbm-update-count'], false)
		assertTrue output.contains('ERROR: The dbm-update-count script requires a change set count argument')

		executeAndCheck(['dbm-update-count', '2'])

		// original + 2 Liquibase + new person table
		assertTableCount 4

		assertTrue output.contains(
			'Starting dbm-update-count for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')

		// can't do full insert since the 3rd change wasn't applied
		message = shouldFail(SQLException) {
			executeUpdate '''
				insert into person(version, name, street1, street2, zipcode)
				values (0, 'test.name', 'test.street1', 'test.street2', '12345')
			'''
		}

		assertTrue message.contains('Column "ZIPCODE" not found')

		// can do partial insert
		executeUpdate '''
			insert into person(version, name, street1, street2)
			values (0, 'test.name', 'test.street1', 'test.street2')
		'''
	}
}
