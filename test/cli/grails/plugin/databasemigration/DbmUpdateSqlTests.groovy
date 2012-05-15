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
class DbmUpdateSqlTests extends AbstractScriptTests {

	void testUpdateSql() {

		assertTableCount 1

		copyTestChangelog()

		executeAndCheck 'dbm-update-sql'

		assertTableCount 1

		assertTrue output.contains(
			'Starting dbm-update-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')

		assertTrue output.contains('CREATE TABLE DATABASECHANGELOGLOCK')
		assertTrue output.contains('CREATE TABLE DATABASECHANGELOG')
		assertTrue output.contains('-- Changeset changelog.cli.test.groovy::test-1::burt::')
		assertTrue output.contains('-- Changeset changelog.cli.test.groovy::test-2::burt::')
		assertTrue output.contains('-- Changeset changelog.cli.test.groovy::test-3::burt::')
		assertTrue output.contains('INSERT INTO DATABASECHANGELOG')
		assertTrue output.contains('CREATE TABLE PERSON')
		assertTrue output.contains('ALTER TABLE PERSON ADD STREET1 VARCHAR(100) NOT NULL')
		assertTrue output.contains('ALTER TABLE PERSON ADD STREET2 VARCHAR(100)')
		assertTrue output.contains('ALTER TABLE PERSON ADD ZIPCODE VARCHAR(10) NOT NULL')
	}

    void testUpdateSqlForSecondaryDataSource() {

        def url = AbstractScriptTests.SECONDARY_URL

        assertTableCount 1, url

   		copyTestChangelog('test.changelog', SECONDARY_TEST_CHANGELOG)

   		executeAndCheck (['dbm-update-sql','--dataSource=secondary'])

   		assertTableCount 1, url

   		assertTrue output.contains(
   			'Starting dbm-update-sql for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')

   		assertTrue output.contains('CREATE TABLE DATABASECHANGELOGLOCK')
   		assertTrue output.contains('CREATE TABLE DATABASECHANGELOG')
   		assertTrue output.contains('-- Changeset changelog.cli.secondary-test.groovy::test-1::burt::')
   		assertTrue output.contains('-- Changeset changelog.cli.secondary-test.groovy::test-2::burt::')
   		assertTrue output.contains('-- Changeset changelog.cli.secondary-test.groovy::test-3::burt::')
   		assertTrue output.contains('INSERT INTO DATABASECHANGELOG')
   		assertTrue output.contains('CREATE TABLE PERSON')
   		assertTrue output.contains('ALTER TABLE PERSON ADD STREET1 VARCHAR(100) NOT NULL')
   		assertTrue output.contains('ALTER TABLE PERSON ADD STREET2 VARCHAR(100)')
   		assertTrue output.contains('ALTER TABLE PERSON ADD ZIPCODE VARCHAR(10) NOT NULL')
   	}

}
