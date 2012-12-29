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
class DbmDbDocTests extends AbstractScriptTests {

	void testDbDoc() {

		generateChangelog()

		executeUpdate AbstractScriptTests.URL, 'drop table thing'

		executeAndCheck(['dbm-update-count', '1'])

		executeAndCheck 'dbm-db-doc'

		['authors', 'changelogs', 'columns', 'pending', 'recent', 'tables'].each {
			assertTrue new File('target/dbdoc', it).exists()
			assertTrue new File('target/dbdoc', it).isDirectory()
		}

		assertTrue new File('target/dbdoc/changelogs/changelog.cli.test.groovy.xml').exists()

		assertTrue new File('target/dbdoc/tables/thing.html').exists()

		assertTrue new File('target/dbdoc/columns/thing.id.html').exists()
		assertTrue new File('target/dbdoc/columns/thing.name.html').exists()
		assertTrue new File('target/dbdoc/columns/thing.version.html').exists()

		assertTrue output.contains(
			'Starting dbm-db-doc for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}

	void testDbDocForSecondaryDatasource() {

		generateSecondaryChagelog()

		executeUpdate AbstractScriptTests.SECONDARY_URL, 'drop table secondary_thing'

		executeAndCheck(['dbm-update-count', '1', '--dataSource=secondary'])

		executeAndCheck (['dbm-db-doc', '--dataSource=secondary'])

		for (String name in ['authors', 'changelogs', 'columns', 'pending', 'recent', 'tables']) {
			assertTrue new File('target/dbdoc', name).exists()
			assertTrue new File('target/dbdoc', name).isDirectory()
		}

		assertTrue new File('target/dbdoc/changelogs/changelog.cli.secondary-test.groovy.xml').exists()

		assertTrue new File('target/dbdoc/tables/secondary_thing.html').exists()

		assertTrue new File('target/dbdoc/columns/secondary_thing.id.html').exists()
		assertTrue new File('target/dbdoc/columns/secondary_thing.name.html').exists()
		assertTrue new File('target/dbdoc/columns/secondary_thing.version.html').exists()

		assertTrue output.contains(
			'Starting dbm-db-doc for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb-secondary')
	}

	protected void tearDown() {
		super.tearDown()
		new File('target/dbdoc').deleteDir()
	}
}
