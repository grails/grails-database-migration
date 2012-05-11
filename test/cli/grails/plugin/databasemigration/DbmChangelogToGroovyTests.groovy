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
class DbmChangelogToGroovyTests extends AbstractScriptTests {

	void testChangelogToGroovy() {

		def file = new File(CHANGELOG_DIR, '/changelog.xml')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-generate-changelog', 'changelog.xml'])

		assertTrue file.exists()
		file.deleteOnExit()

		// test parameter check
		executeAndCheck(['dbm-changelog-to-groovy'], false)
		assertTrue output.contains('ERROR: Must specify the source XML file path')

		file = new File(CHANGELOG_DIR, '/changelog.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-changelog-to-groovy', CHANGELOG_DIR + '/changelog.xml'])

		assertTrue file.exists()
		file.deleteOnExit()

		assertTrue output.contains("Converting $CHANGELOG_DIR/changelog.xml to $CHANGELOG_DIR/changelog.groovy".toString())

		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(')

		file = new File(CHANGELOG_DIR, '/cl.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-changelog-to-groovy',
			CHANGELOG_DIR + '/changelog.xml',
			CHANGELOG_DIR + '/cl.groovy'])

		assertTrue file.exists()
		file.deleteOnExit()

		assertTrue output.contains("Converting $CHANGELOG_DIR/changelog.xml to $CHANGELOG_DIR/cl.groovy".toString())

		groovy = file.text
		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(')
	}
}
