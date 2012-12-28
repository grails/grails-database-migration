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
class DbmStatusTests extends AbstractScriptTests {

	void testStatusList() {
		String url = AbstractScriptTests.URL

		generateChangelog()

		executeAndCheck 'dbm-status'

		assertTrue output.contains(
			'1 change sets have not been applied to SA@jdbc:h2:tcp://localhost/./target/testdb/testdb')

		assertTrue output.contains(
			'changelog.cli.test.groovy::')

		executeUpdate url, 'drop table thing'

		// update one change
		executeAndCheck(['dbm-update-count', '1'])

		assertTrue output.contains('ChangeSet changelog.cli.test.groovy::')
		assertTrue output.contains('ran successfully in ')

		executeAndCheck 'dbm-status'
		assertTrue output.contains('SA@jdbc:h2:tcp://localhost/./target/testdb/testdb is up to date')
	}

	void testStatusCount() {

		generateChangelog()

		executeAndCheck(['dbm-status', '--verbose=false'])

		assertTrue output.contains(
			'1 change sets have not been applied to SA@jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}

	void testStatusListForSecondaryDataSource() {
		String url = AbstractScriptTests.SECONDARY_URL

		generateSecondaryChagelog()

		executeAndCheck (['dbm-status', '--dataSource=secondary'])

		assertTrue output.contains(
			'1 change sets have not been applied to SA@jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')

		assertTrue output.contains('changelog.cli.secondary-test.groovy::')

		executeUpdate url, 'drop table secondary_thing'

		// update one change
		executeAndCheck(['dbm-update-count', '1', '--dataSource=secondary'])

		assertTrue output.contains('ChangeSet changelog.cli.secondary-test.groovy::')
		assertTrue output.contains('ran successfully in ')

		executeAndCheck (['dbm-status', '--dataSource=secondary'])
		assertTrue output.contains('SA@jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary is up to date')
	}

	void testStatusCountForSecondaryDataSource() {

		generateSecondaryChagelog()

		executeAndCheck(['dbm-status', '--verbose=false', '--dataSource=secondary'])

		int index = 0
		def lines = output.readLines()
		lines.eachWithIndex { String line, int i ->
			if (line.trim() == '1 change sets have not been applied to SA@jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary') {
				index = i
			}
		}
		assertTrue index > 0

		assertTrue lines[index + 1].trim().startsWith('changelog.cli.secondary-test.groovy::')
	}
}
