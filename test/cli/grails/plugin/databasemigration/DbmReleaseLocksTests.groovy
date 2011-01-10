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

import java.sql.Timestamp

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DbmReleaseLocksTests extends AbstractScriptTests {

	void testReleaseLocks() {

		generateChangelog()

		// auto-created by hbm2ddl
		executeUpdate 'drop table thing'

		executeAndCheck(['dbm-update-count', '1'])

		// force a lock
		executeUpdate('update databasechangeloglock set locked=?, lockgranted=?, lockedby=?',
		              [true, new Timestamp(System.currentTimeMillis()), 'cli_test'])
		executeAndCheck 'dbm-list-locks'

		assertFalse output.contains('No locks')

		executeAndCheck 'dbm-release-locks'

		assertTrue output.contains('Successfully released change log lock')
	}
}
