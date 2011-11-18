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
class DbmCreateChangelogTests extends AbstractScriptTests {

	void testCreateChangelogDefault() {

		def file = new File(CHANGELOG_DIR, '/changelog.cli.test.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-create-changelog'])

		verifyFile file
	}

	void testCreateChangelog() {

		def file = new File(CHANGELOG_DIR, '/newChangeLog.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-create-changelog', 'newChangeLog'])

		verifyFile file
	}

	void testCreateChangelogInSubdirectory() {

		def file = new File(CHANGELOG_DIR, '/foo/bar/otherChangeLog.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-create-changelog', 'foo/bar/otherChangeLog'])

		verifyFile file
	}

	private void verifyFile(file) {
		assertTrue file.exists()
		file.deleteOnExit()

		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(author: ')
		assertTrue groovy.contains(", id: \"${file.name - '.groovy'}\") {")
	}
}
