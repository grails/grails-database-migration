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
class DbmChangelogToGroovyTests extends AbstractScriptTests {

	void testChangelogToGroovy() {

		def file = new File('target/changelog.xml')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-generate-changelog', 'target/changelog.xml'])

		assertTrue file.exists()
		file.deleteOnExit()

		// test parameter check
		executeAndCheck(['dbm-changelog-to-groovy'], false)
		assertTrue output.contains('ERROR: Must specify the source XML file path')

		file = new File('target/changelog.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-changelog-to-groovy', 'target/changelog.xml'])

		assertTrue file.exists()
		file.deleteOnExit()

		assertTrue output.contains('Converting target/changelog.xml to target/changelog.groovy')

		String groovy = file.text

		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(')

		file = new File('target/cl.groovy')
		file.delete()
		assertFalse file.exists()

		executeAndCheck(['dbm-changelog-to-groovy', 'target/changelog.xml', 'target/cl.groovy'])

		assertTrue file.exists()
		file.deleteOnExit()

		assertTrue output.contains('Converting target/changelog.xml to target/cl.groovy')

		groovy = file.text
		assertTrue groovy.contains('databaseChangeLog = {')
		assertTrue groovy.contains('changeSet(')
		assertTrue groovy.contains('createTable(')
	}
}
