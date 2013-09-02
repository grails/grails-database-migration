/* Copyright 2010-2013 SpringSource.
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
class DbmTagTests extends AbstractScriptTests {

	void testTag() {

		String url = AbstractScriptTests.URL

		generateChangelog()

		executeUpdate url, 'drop table thing'

		executeAndCheck(['dbm-update-count', '1'])

		newSql(url).eachRow('select * from xdatabasechangelogx') { assertNull it.TAG }

		// test parameter check
		executeAndCheck(['dbm-tag'], false)
		assertTrue output.contains('ERROR: The dbm-tag script requires a tag')

		executeAndCheck(['dbm-tag', 'tag123'])

		newSql(url).eachRow('select * from xdatabasechangelogx') { assertEquals 'tag123', it.TAG }
	}

	void testTagForSecondaryDataSource() {

		String url = AbstractScriptTests.SECONDARY_URL

		generateSecondaryChagelog()

		executeUpdate url, 'drop table secondary_thing'

		executeAndCheck(['dbm-update-count', '1', '--dataSource=secondary'])

		newSql(url).eachRow('select * from xdatabasechangelogx') { assertNull it.TAG }

		// test parameter check
		executeAndCheck(['dbm-tag', '--dataSource=secondary'], false)
		assertTrue output.contains('ERROR: The dbm-tag script requires a tag')

		executeAndCheck(['dbm-tag', 'tag123', '--dataSource=secondary'])

		newSql(url).eachRow('select * from xdatabasechangelogx') { assertEquals 'tag123', it.TAG }
	}
}
