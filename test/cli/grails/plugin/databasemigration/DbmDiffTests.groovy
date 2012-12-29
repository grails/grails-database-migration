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

import groovy.sql.Sql

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmDiffTests extends AbstractScriptTests {

	@Override
	protected void setUp() {
		super.setUp()
		new File('target/dbdiff').deleteDir()
	}

	@Override
	protected void tearDown() {
		super.tearDown()
		new File('target/dbdiff').deleteDir()
	}

	void testBadParams() {
		// test missing parameter check
		executeAndCheck(['dbm-diff'], false)
		assertTrue output.contains('ERROR: You must specify the environment to diff against')

		// test same env parameter check
		executeAndCheck(['dbm-diff', 'dev'], false)
		assertTrue output.contains('ERROR: You must specify a different environment than the one the script is running in')

		// test same env parameter check
		executeAndCheck(['dbm-diff', 'development'], false)
		assertTrue output.contains('ERROR: You must specify a different environment than the one the script is running in')
	}

	void testDbmDiff_STDOUT() {

		createTables()

		executeAndCheck(['dbm-diff', 'dbdiff'])

		assertTrue output.contains('<changeSet ')
		assertTrue output.contains('<createTable tableName="NEW_TABLE">')
		assertTrue output.contains('<addColumn tableName="THING">')
		assertTrue output.contains('<dropColumn columnName="NEW_COL" tableName="THING"/>')
		assertTrue output.contains('<dropColumn columnName="NEW_INT_COL" tableName="THING"/>')
	}

	void testDbmDiff_XML() {

		createTables()

		def file = new File(CHANGELOG_DIR, '/cl.xml')
		assertFalse file.exists()

		executeAndCheck(['dbm-diff', 'dbdiff', 'cl.xml'])

		assertTrue file.exists()
		file.deleteOnExit()

		String xml = file.text

		assertTrue xml.contains('<changeSet ')
		assertTrue xml.contains('<createTable tableName="NEW_TABLE">')
		assertTrue xml.contains('<addColumn tableName="THING">')
		assertTrue xml.contains('<dropColumn columnName="NEW_COL" tableName="THING"/>')
		assertTrue xml.contains('<dropColumn columnName="NEW_INT_COL" tableName="THING"/>')
	}

	void testDbmDiff_Groovy() {

		createTables()

		def file = new File(CHANGELOG_DIR, '/cl.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-diff', 'dbdiff', 'cl.groovy'])

		assertTrue file.exists()
		file.deleteOnExit()

		String groovy = file.text

		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(tableName: "NEW_TABLE") {')
		assertTrue groovy.contains('addColumn(tableName: "THING") {')
		assertTrue groovy.contains('dropColumn(columnName: "NEW_COL", tableName: "THING")')
		assertTrue groovy.contains('dropColumn(columnName: "NEW_INT_COL", tableName: "THING")')
	}

	void testBadParamsForSecondaryDataSource() {
		// test missing parameter check
		executeAndCheck(['dbm-diff', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: You must specify the environment to diff against')

		// test same env parameter check
		executeAndCheck(['dbm-diff', 'dev', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: You must specify a different environment than the one the script is running in')

		// test same env parameter check
		executeAndCheck(['dbm-diff', 'development', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: You must specify a different environment than the one the script is running in')
	}

	void testDbmDiffForSecondaryDataSource_STDOUT() {

		createSecondaryTables()

		executeAndCheck(['dbm-diff', 'dbdiff', '--dataSource=secondary'])

		assertTrue output.contains('<changeSet ')
		assertTrue output.contains('<createTable tableName="NEW_TABLE">')
		assertTrue output.contains('<addColumn tableName="SECONDARY_THING">')
		assertTrue output.contains('<dropColumn columnName="NEW_COL" tableName="SECONDARY_THING"/>')
		assertTrue output.contains('<dropColumn columnName="NEW_INT_COL" tableName="SECONDARY_THING"/>')
	}

	void testDbmDiffForSecondaryDataSource_XML() {

		createSecondaryTables()

		def file = new File(CHANGELOG_DIR, '/cl.xml')
		assertFalse file.exists()

		executeAndCheck(['dbm-diff', 'dbdiff', 'cl.xml', '--dataSource=secondary'])

		assertTrue file.exists()
		file.deleteOnExit()

		String xml = file.text

		assertTrue xml.contains('<changeSet ')
		assertTrue xml.contains('<createTable tableName="NEW_TABLE">')
		assertTrue xml.contains('<addColumn tableName="SECONDARY_THING">')
		assertTrue xml.contains('<dropColumn columnName="NEW_COL" tableName="SECONDARY_THING"/>')
		assertTrue xml.contains('<dropColumn columnName="NEW_INT_COL" tableName="SECONDARY_THING"/>')
	}

	void testDbmDiffForSecondaryDataSource_Groovy() {

		createSecondaryTables()

		def file = new File(CHANGELOG_DIR, '/cl.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-diff', 'dbdiff', 'cl.groovy', '--dataSource=secondary'])

		assertTrue file.exists()
		file.deleteOnExit()

		String groovy = file.text

		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(tableName: "NEW_TABLE") {')
		assertTrue groovy.contains('addColumn(tableName: "SECONDARY_THING") {')
		assertTrue groovy.contains('dropColumn(columnName: "NEW_COL", tableName: "SECONDARY_THING")')
		assertTrue groovy.contains('dropColumn(columnName: "NEW_INT_COL", tableName: "SECONDARY_THING")')
	}

	private void createTables(String url = AbstractScriptTests.URL, String tableName = 'thing' ) {

		// create a new test table that's not in the comparison db
		executeUpdate url, '''
			create table new_table (
				id bigint generated by default as identity (start with 1),
				version bigint not null,
				name varchar(255),
				primary key (id)
			)'''

		def diffSql = Sql.newInstance('jdbc:h2:file:target/dbdiff/dbdiff', 'sa', '', 'org.h2.Driver')

		// create a modified 'thing' table in the comparison db
		def createTableSql = """
			create table ${tableName} (
				id bigint generated by default as identity (start with 1),
				version bigint not null,
				new_col varchar(100),
				new_int_col bigint,
				primary key (id)
			)"""

		diffSql.executeUpdate createTableSql.toString()

		diffSql.executeUpdate 'SHUTDOWN'
	}

	private void createSecondaryTables() {
		createTables(AbstractScriptTests.SECONDARY_URL, '   secondary_thing')
	}
}
