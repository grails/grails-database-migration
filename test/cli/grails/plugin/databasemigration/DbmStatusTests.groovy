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
class DbmStatusTests extends AbstractScriptTests {

	void testStatus() {

		generateChangelog()

		executeAndCheck 'dbm-status'

		assertTrue output.contains(
			'1 change sets have not been applied to SA@jdbc:h2:tcp://localhost/./target/testdb/testdb')

		executeUpdate 'drop table thing'

		// update one change
		executeAndCheck(['dbm-update-count', '1'])

		assertTrue output.contains('ChangeSet changelog.cli.test.groovy::')
		assertTrue output.contains('ran successfully in ')

		executeAndCheck 'dbm-status'
		assertTrue output.contains('SA@jdbc:h2:tcp://localhost/./target/testdb/testdb is up to date')
	}
}
