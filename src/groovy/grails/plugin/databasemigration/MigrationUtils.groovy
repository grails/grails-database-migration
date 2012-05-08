/* Copyright 2010-2011 SpringSource.
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

import java.sql.Connection

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.database.structure.Index
import liquibase.database.structure.UniqueConstraint
import liquibase.diff.DiffResult

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.hibernate.FlushMode
import org.hibernate.Session
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class MigrationUtils {

	private MigrationUtils() {
		// static only
	}

	/** Set at startup. */
	static GrailsApplication application

	/** Set from _Events.groovy in eventPackageAppEnd. */
	static String scriptName

	static Database getDatabase(Connection connection, String defaultSchema, String dialectName) {
		def database = DatabaseFactory.instance.findCorrectDatabaseImplementation(
			new JdbcConnection(connection))

		if (defaultSchema) {
			database.defaultSchemaName = defaultSchema
		}
//		database.defaultSchemaName = connection.catalog // TODO

		if (dialectName) {
			// add in a per-instance getDialect() method, mostly needed in
			// MysqlAwareCreateTableGenerator for MySQL InnoDB
			def dialect = createInstance(dialectName)
			def emc = new ExpandoMetaClass(database.getClass(), false)
			emc.getDialect = { -> dialect }
			emc.initialize()
			database.metaClass = emc
		}

        println "getDatabase database: ${database}"

		database
	}

    static Map<String, Database> getDatabases() {
        Map<String, Database> databaseMap = [:]

        databaseMap["dataSource"] = getDatabase(config.updateOnStartDefaultSchema ?: null)

        getDataSourceConfigs().each{ dsName, dsConfig ->
            String dataSourceSuffix = extractSuffix(dsName)
            def connection = findSessionFactory(dataSourceSuffix).currentSession.connection()

            def dialect = application.config."dataSource${dataSourceSuffix}".dialect
            if (dialect && dialect instanceof Class) {
                dialect = dialect.name
            } else {
                dialect = application.mainContext.dialectDetector
            }

            def defaultSchema = config."${dsName}".updateOnStartDefaultSchema ?: null
            databaseMap[dsName] == getDatabase( connection, defaultSchema, dialect.toString() )
        }

        databaseMap
    }

    static Map<String, ConfigObject> getDataSourceConfigs() {
        def dsConfigs = [:]
        application.config.each { String name, value ->
            if(name.startsWith('dataSource_') && value instanceof ConfigObject) {
                dsConfigs[name] = value
            }
        }

        dsConfigs
    }


	static Database getDatabase(String defaultSchema = null, String dsName = 'dataSource') {
        def dataSourceSuffix = extractSuffix(dsName)
		def connection = findSessionFactory(dataSourceSuffix).currentSession.connection()

		def dialect = application.config."$dsName".dialect
		if (dialect) {
			if (dialect instanceof Class) {
				dialect = dialect.name
			}
		}
		else {
			dialect = application.mainContext.dialectDetector
		}

		getDatabase connection, defaultSchema, dialect.toString()
	}

	static Liquibase getLiquibase(Database database) {
		getLiquibase database, getChangelogFileName()
	}

	static Liquibase getLiquibase(Database database, String changelogFileName) {
		def resourceAccessor = application.mainContext.migrationResourceAccessor
		new Liquibase(changelogFileName, resourceAccessor, database)
	}

	static void executeInSession(String dsName = 'dataSource', Closure c) {
        String dataSourceSuffix = extractSuffix(dsName)
		boolean participate = initSession(dataSourceSuffix)
		try {
			c()
		}
		finally {
			if (!participate) {
				flushAndClose(dataSourceSuffix)
			}
		}
	}

	private static boolean initSession(String dataSourceSuffix) {
		def sessionFactory = findSessionFactory(dataSourceSuffix)
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			return true
		}

		Session session = SessionFactoryUtils.getSession(sessionFactory, true)
		session.flushMode = FlushMode.AUTO
		TransactionSynchronizationManager.bindResource sessionFactory, new SessionHolder(session)
		false
	}

	private static void flushAndClose(String dataSourceSuffix) {
		def sessionFactory = findSessionFactory(dataSourceSuffix)
		def session = TransactionSynchronizationManager.unbindResource(sessionFactory).session
		if (!FlushMode.MANUAL == session.flushMode) {
			session.flush()
		}
		SessionFactoryUtils.closeSession session
	}

	private static findSessionFactory(String dataSourceSuffix = '') {

        def testDataSourceSuffix = dataSourceSuffix
		def factoryBean = application.mainContext.getBean("&sessionFactory${testDataSourceSuffix}")
		if (factoryBean.getClass().simpleName == 'DelayedSessionFactoryBean') {
			// get the un-proxied version since at this point it's ok to get a connection;
			// only an issue during plugin tests
			return factoryBean.realSessionFactory
		}

		application.mainContext."sessionFactory${dataSourceSuffix}"
	}

    private static extractSuffix(dataSourceName) {
        boolean isDefault = dataSourceName == "dataSource"
        String suffix = isDefault ? '' : dataSourceName[10..-1]
        suffix
    }

    private static extractSuffixWithOutUnderbar(dataSourceName) {
        boolean isDefault = dataSourceName == "dataSource"
        String suffix = isDefault ? '' : dataSourceName[11..-1]
        suffix
    }

    static String dataSourceNameWithSuffix( dataSourceSuffix = '') {
        String dsName = 'dataSource'
        if (dataSourceSuffix?.length() > 0) {
            dsName += "_${dataSourceSuffix}"
        }
        dsName
    }

	static boolean canAutoMigrate() {

		// in a war
		if (application.warDeployed) {
			return true
		}

		// in run-app
		if (autoMigrateScripts.contains(scriptName)) {
			return true
		}

		false
	}

	static createInstance(String className) {
		application.classLoader.loadClass(className).newInstance()
	}

	static ConfigObject getConfig() {
		application.config.grails.plugin.databasemigration
	}

	static String getDbDocLocation() {
		getConfig().dbDocLocation ?: 'target/dbdoc'
	}

	static String getAutoMigrateScripts() {
		getConfig().autoMigrateScripts ?: ['RunApp']
	}

	static String getChangelogFileName(String dsName = 'dataSource') {
        boolean isDefault = dsName == "dataSource"
        if( !isDefault ) {
            String dataSourceSuffix = extractSuffixWithOutUnderbar(dsName)
            println "dataSourceSuffix: $dataSourceSuffix"
            return getConfig()."${dataSourceSuffix}".changelogFileName ?: "changelog-${dataSourceSuffix}.groovy"
        }
        return getConfig().changelogFileName ?: 'changelog.groovy'
	}

	static String getChangelogLocation() {
		getConfig().changelogLocation ?: 'grails-app/migrations'
	}

	static ConfigObject getChangelogProperties() {
		getConfig().changelogProperties ?: [:]
	}

	static DiffResult fixDiffResult(DiffResult diffResult) {
		removeRedundantUnexpectedUnique diffResult
		removeEquivalentIndexes diffResult

		for (Iterator iter = diffResult.unexpectedIndexes.iterator(); iter.hasNext(); ) {
			Index index = iter.next()
			if (index.associatedWith.contains(Index.MARK_PRIMARY_KEY) ||
					index.associatedWith.contains(Index.MARK_FOREIGN_KEY) ||
					index.associatedWith.contains(Index.MARK_UNIQUE_CONSTRAINT)) {
				continue
			}

			for (Index targetIndex in diffResult.referenceSnapshot.indexes) {
				if (index.columns.size() == targetIndex.columns.size() &&
						index.columns.containsAll(targetIndex.columns) &&
						index.table.name.equalsIgnoreCase(targetIndex.table.name) &&
						index.unique == targetIndex.unique) {
					iter.remove()
					break
				}
			}
		}

		diffResult
	}

	static void removeRedundantUnexpectedUnique(DiffResult diffResult) {
		for (Iterator iter = diffResult.unexpectedUniqueConstraints.iterator(); iter.hasNext(); ) {
			UniqueConstraint uniqueConstraint = iter.next()
			List<String> constraintColumnNames = uniqueConstraint.columns*.toLowerCase()
			for (Index index in diffResult.targetSnapshot.indexes) {
				List<String> indexColumnNames = index.columns*.toLowerCase()
				if (index.unique &&
						indexColumnNames.size() == constraintColumnNames.size() &&
						indexColumnNames.containsAll(constraintColumnNames) &&
						index.table.name.equalsIgnoreCase(uniqueConstraint.table.name)) {
					iter.remove()
					break
				}
			}
		}
	}

	static void removeEquivalentIndexes(DiffResult diffResult) {
		for (Iterator iter = diffResult.missingIndexes.iterator(); iter.hasNext(); ) {
			Index index = iter.next()
			List<String> indexColumnNames = index.columns*.toLowerCase()
			for (Iterator targetIter = diffResult.targetSnapshot.indexes.iterator(); targetIter.hasNext(); ) {
				Index targetIndex = targetIter.next()
				List<String> targetIndexColumnNames = targetIndex.columns*.toLowerCase()
				if (indexColumnNames.size() == targetIndexColumnNames.size() &&
						indexColumnNames.containsAll(targetIndexColumnNames) &&
						index.table.name.equalsIgnoreCase(targetIndex.table.name)) {
					iter.remove()
					targetIter.remove()
					diffResult.unexpectedIndexes.remove targetIndex
					break
				}
			}
		}
	}
}
