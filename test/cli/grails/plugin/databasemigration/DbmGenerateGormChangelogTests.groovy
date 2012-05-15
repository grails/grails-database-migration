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
class DbmGenerateGormChangelogTests extends AbstractScriptTests {

	/**
	 * Test generating a changelog from a standard XML file.
	 */
	void testGenerateGormChangelog_XML() {

		initFile false

		executeAndCheck(['dbm-generate-gorm-changelog', file.name])

		assertTrue file.exists()
		String xml = file.text

		assertTrue xml.contains('<databaseChangeLog ')
		assertTrue xml.contains('<changeSet ')
		assertTrue xml.contains('<createTable ')

		assertTrue output.contains(
			'Starting dbm-generate-gorm-changelog for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}

	/**
	 * Test generating a changelog from a Groovy file generated from a standard XML file.
	 */
	void testGenerateGormChangelog_Groovy() {

		initFile true

		executeAndCheck(['dbm-generate-gorm-changelog', file.name])

		assertTrue file.exists()
		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(')

		assertTrue output.contains(
			'Starting dbm-generate-gorm-changelog for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}

	/**
	 * Test generating a changelog to STDOUT.
	 */
	void testGenerateGormChangelog_Stdout() {

		executeAndCheck 'dbm-generate-gorm-changelog'

		assertTrue output.contains('<databaseChangeLog')
		assertTrue output.contains('<changeSet')
		assertTrue output.contains('<createTable')

		assertTrue output.contains(
			'Starting dbm-generate-gorm-changelog for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}


    void testGenerateGormChangelogForSecondaryDataSource() {

        executeAndCheck (['dbm-generate-gorm-changelog', '--dataSource=secondary', '--stacktrace'])

        assertTrue output.contains('<databaseChangeLog')
        assertTrue output.contains('<changeSet')
        assertTrue output.contains('<createTable')

        assertTrue output.contains(
            'Starting dbm-generate-gorm-changelog for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary'
        )

        assertTrue output.contains('secondary_thing')

    }
}
