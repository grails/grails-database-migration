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
class DbmCreateChangelogTests extends AbstractScriptTests {

	void testBadParams() {
		executeAndCheck(['dbm-create-changelog'], false)
		assertTrue output.contains('ERROR: You must specify the migration name')
	}

	void testCreateChangelog() {

		def file = new File('target/newChangeLog.groovy')
		assertFalse file.exists()

		executeAndCheck(['dbm-create-changelog', 'newChangeLog'])

		assertTrue file.exists()
		file.deleteOnExit()

		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(author: ')
		assertTrue groovy.contains(', id: "newChangeLog") {')
	}

	void testCreateChangelogInSubdirectory() {

		def file = new File('target/foo/bar/newChangeLog.groovy')
		assertFalse file.exists()

		executeAndCheck(['dbm-create-changelog', 'foo/bar/otherChangeLog'])

		assertTrue file.exists()
		file.deleteOnExit()

		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(author: ')
		assertTrue groovy.contains(', id: "otherChangeLog") {')
	}
}
