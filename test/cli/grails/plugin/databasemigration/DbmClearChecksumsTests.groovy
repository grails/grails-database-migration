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
class DbmClearChecksumsTests extends AbstractScriptTests {

	void testClearChecksums() {

		generateChangelog()

		executeUpdate 'drop table thing'

		executeAndCheck(['dbm-update-count', '1'])

		// should have checksums after running update
		newSql().eachRow('select * from databasechangelog') {
			assertNotNull it.md5sum
			assertEquals 34, it.md5sum.length()
			assertEquals 'changelog.cli.test.groovy', it.filename
			assertEquals 'EXECUTED', it.exectype
			assertEquals 'Create Table', it.description
			assertNull it.tag
		}

		executeAndCheck 'dbm-clear-checksums'

		// should have null checksums but otherwise unchanged
		newSql().eachRow('select * from databasechangelog') {
			assertNull it.md5sum
			assertEquals 'changelog.cli.test.groovy', it.filename
			assertEquals 'EXECUTED', it.exectype
			assertEquals 'Create Table', it.description
			assertNull it.tag
		}

		assertTrue output.contains(
			'Starting dbm-clear-checksums for database sa @ jdbc:h2:tcp://localhost/./target/testdb/testdb')
	}
}
