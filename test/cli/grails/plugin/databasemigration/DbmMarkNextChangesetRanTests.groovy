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
class DbmMarkNextChangesetRanTests extends AbstractScriptTests {

	void testMarkNextChangesetRan() {
		assertTableCount 1
		copyTestChangelog()
		executeAndCheck(['dbm-update-count', '2'])
		assertTableCount 4

		assertTrue output.contains('ChangeSet changelog.cli.test.groovy::test-1::burt ran successfully')
		assertTrue output.contains('ChangeSet changelog.cli.test.groovy::test-2::burt ran successfully')

		executeAndCheck 'dbm-mark-next-changeset-ran'
		assertTrue output.contains('Starting dbm-mark-next-changeset-ran')

		executeAndCheck 'dbm-update'
println output
//		assertTrue output.contains("DELETE FROM DATABASECHANGELOG  WHERE ID='test-3'")
	}
}
