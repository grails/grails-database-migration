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
class DbmRollbackSqlTests extends AbstractScriptTests {

	void testRollbackSql() {

		// run all the updates
		copyTestChangelog()
		executeAndCheck 'dbm-update'

		// manually tag the first update
		String tagName = 'THE_TAG'
		executeUpdate "update databasechangelog set tag=? where id='test-1'", [tagName]

		// test parameter check
		executeAndCheck(['dbm-rollback-sql'], false)
		assertTrue output.contains('ERROR: The dbm-rollback-sql script requires a tag')

		executeAndCheck(['dbm-rollback-sql', tagName])

		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN ZIPCODE')
		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN STREET1')
		assertTrue output.contains('ALTER TABLE PERSON DROP COLUMN STREET2')
		assertTrue output.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-2' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
		assertTrue output.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-3' AND AUTHOR='burt' AND FILENAME='changelog.cli.test.groovy'")
	}
}
