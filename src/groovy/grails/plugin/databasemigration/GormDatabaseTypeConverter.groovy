/* Copyright 2011 the original author or authors.
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

import liquibase.database.Database
import liquibase.database.structure.Column
import liquibase.database.typeconversion.core.DefaultTypeConverter

/**
 * Fixes changelog errors generated from the GORM scripts.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class GormDatabaseTypeConverter extends DefaultTypeConverter {

	@Override
	String convertToDatabaseTypeString(Column referenceColumn, Database database) {

		if (referenceColumn.typeName.startsWith('longtext')) {
			return 'longtext'
		}

		if (referenceColumn.typeName.startsWith('mediumtext')) {
			return 'longtext'
		}

		if (referenceColumn.typeName.startsWith('text')) {
			return 'text'
		}

		super.convertToDatabaseTypeString referenceColumn, database
	}

	@Override
	int getPriority() { PRIORITY_DATABASE }

	@Override
	boolean supports(Database database) { database instanceof GormDatabase }
}
