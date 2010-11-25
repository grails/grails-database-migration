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
package grails.plugin.databasemigration.test

import java.sql.Connection
import java.sql.SQLException

import org.apache.commons.dbcp.BasicDataSource
import org.springframework.jdbc.datasource.DelegatingDataSource

/**
 * Only used for testing; see http://burtbeckwith.com/blog/?p=312
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DelayedDataSource extends DelegatingDataSource {

	private boolean _initialized

	ConfigObject dataSourceConfig

	@Override
	Connection getConnection() throws SQLException {
		initialize()
		super.getConnection()
	}

	@Override
	void afterPropertiesSet() {
		// override to not check for targetDataSource since it's lazily created
	}

	private synchronized void initialize() {
		if (_initialized) {
			return
		}

		setTargetDataSource new BasicDataSource(
				driverClassName: dataSourceConfig.driverClassName, password: dataSourceConfig.password,
				username: dataSourceConfig.username, url: dataSourceConfig.url)

		_initialized = true
	}
}
