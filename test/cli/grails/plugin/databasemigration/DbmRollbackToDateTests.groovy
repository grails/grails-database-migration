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

import java.sql.SQLException
import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmRollbackToDateTests extends AbstractScriptTests {

	void testRollbackToDate() {

		String url = AbstractScriptTests.URL

		assertTableCount 1

		// should fail since the table isn't there yet
		String message = shouldFail(SQLException) {
			executeUpdate url, '''
				insert into person(version, name, street1, street2, zipcode)
				values (0, 'test.name', 'test.street1', 'test.street2', '12345')
			'''
		}
		assertTrue message.contains('Table "PERSON" not found')

		copyTestChangelog()

		executeAndCheck 'dbm-update'

		// original + 2 Liquibase + new person table
		assertTableCount 4

		// now we can insert into person
		executeUpdate url,'''
			insert into person(version, name, street1, street2, zipcode)
			values (0, 'test.name', 'test.street1', 'test.street2', '12345')
		'''

		// fake out the dates to be able to rollback to particular date
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-1'",
			[new Timestamp((new Date() - 30).time)]
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-2'",
			[new Timestamp((new Date() - 20).time)]
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-3'",
			[new Timestamp((new Date() - 10).time)]

		// test parameter check
		executeAndCheck(['dbm-rollback-to-date'], false)
		assertTrue output.contains('ERROR: Date must be specified')

		executeAndCheck(['dbm-rollback-to-date',
			new SimpleDateFormat('yyyy-MM-dd').format(new Date() - 15)])

		// still 4 tables
		assertTableCount 4

		// can't do full insert since the 3rd change was rolled back
		message = shouldFail(SQLException) {
			executeUpdate url, '''
				insert into person(version, name, street1, street2, zipcode)
				values (1, 'test.name2', 'test.street1.2', 'test.street2.2', '123456')
			'''
		}
		assertTrue message.contains('Column "ZIPCODE" not found')

		// can do partial insert
		executeUpdate url, '''
			insert into person(version, name, street1, street2)
			values (1, 'test.name2', 'test.street1.2', 'test.street2.2')
		'''
	}

	void testRollbackToDateForSecondaryDataSource() {

		String url = AbstractScriptTests.SECONDARY_URL

		assertTableCount 1, url

		// should fail since the table isn't there yet
		String message = shouldFail(SQLException) {
			executeUpdate url, '''
				insert into person(version, name, street1, street2, zipcode)
				values (0, 'test.name', 'test.street1', 'test.street2', '12345')
			'''
		}
		assertTrue message.contains('Table "PERSON" not found')

		copyTestChangelog('test.changelog', SECONDARY_TEST_CHANGELOG)

		executeAndCheck (['dbm-update', '--dataSource=secondary'])

		// original + 2 Liquibase + new person table
		assertTableCount 4, url

		// now we can insert into person
		executeUpdate url,'''
			insert into person(version, name, street1, street2, zipcode)
			values (0, 'test.name', 'test.street1', 'test.street2', '12345')
		'''

		// fake out the dates to be able to rollback to particular date
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-1'",
			[new Timestamp((new Date() - 30).time)]
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-2'",
			[new Timestamp((new Date() - 20).time)]
		executeUpdate url, "update databasechangelog set dateexecuted=? where id='test-3'",
			[new Timestamp((new Date() - 10).time)]

		// test parameter check
		executeAndCheck(['dbm-rollback-to-date', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: Date must be specified')

		executeAndCheck(['dbm-rollback-to-date',
			new SimpleDateFormat('yyyy-MM-dd').format(new Date() - 15), '--dataSource=secondary'])

		// still 4 tables
		assertTableCount 4, url

		// can't do full insert since the 3rd change was rolled back
		message = shouldFail(SQLException) {
			executeUpdate url, '''
				insert into person(version, name, street1, street2, zipcode)
				values (1, 'test.name2', 'test.street1.2', 'test.street2.2', '123456')
			'''
		}
		assertTrue message.contains('Column "ZIPCODE" not found')

		// can do partial insert
		executeUpdate url, '''
			insert into person(version, name, street1, street2)
			values (1, 'test.name2', 'test.street1.2', 'test.street2.2')
		'''
	}
}
