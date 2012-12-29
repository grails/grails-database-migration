/* Copyright 2010-2012 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

import grails.util.GrailsUtil
import liquibase.database.Database

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Based on the class of the same name from Mike Hugo's liquibase-runner plugin.
 *
 * @author Mike Hugo
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class MigrationRunner {

	private static Logger LOG = LoggerFactory.getLogger(this)

	static void autoRun() {

		for (dsNameConfig in MigrationUtils.dataSourceConfigs) {
			String dsConfigName = dsNameConfig.key
			ConfigObject configObject = dsNameConfig.value

			if (!MigrationUtils.canAutoMigrate(dsConfigName)) {
				LOG.warn "cannot auto migrate for ${dsConfigName}"
				continue
			}

			def config = MigrationUtils.getConfig(dsConfigName)

			if (!config.updateOnStart) {
				LOG.info "updateOnStart disabled for ${dsConfigName}; not running migrations"
				continue
			}

			try {
				MigrationUtils.executeInSession(dsConfigName) {
					def database = MigrationUtils.getDatabase(MigrationUtils.getConfig(dsConfigName).updateOnStartDefaultSchema ?: null, dsConfigName)
					if (config.dropOnStart) {
						LOG.warn "Dropping tables..."
						MigrationUtils.getLiquibase(database).dropAll()
					}
					List updateOnStartFileNames = config.updateOnStartFileNames
					for (String name in updateOnStartFileNames) {
						LOG.info "Running script '$name'"
						MigrationUtils.getLiquibase(database, name).update config.updateOnStartContexts ?: config.contexts ?: null
					}
				}
			}
			catch (e) {
				GrailsUtil.deepSanitize e
				throw e
			}
		}
	}
}
