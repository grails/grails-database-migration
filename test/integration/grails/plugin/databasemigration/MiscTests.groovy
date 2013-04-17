package grails.plugin.databasemigration

import java.sql.Connection
import java.sql.ResultSet

import liquibase.changelog.ChangeLogParameters
import liquibase.database.Database
import liquibase.parser.core.xml.XMLChangeLogSAXParser

class MiscTests {

	def dataSource

	// for GPDATABASEMIGRATION-132
	void testTableNames() {

		def names = findTableNames()
		assert !names.contains('DATABASECHANGELOG')
		assert !names.contains('DATABASECHANGELOGLOCK')
		assert !names.contains('FOO')
		assert !names.contains('BAR')

		def databaseChangeLog = new XMLChangeLogSAXParser().parse('test.changelist.xml',
					new ChangeLogParameters(), new ParserTestResourceAccessor())

		Database database = MigrationUtils.getDatabase()
		database.checkDatabaseChangeLogTable false, databaseChangeLog, null
		database.checkDatabaseChangeLogLockTable()

		names = findTableNames()
		assert !names.contains('DATABASECHANGELOG')
		assert !names.contains('DATABASECHANGELOGLOCK')
		assert names.contains('FOO')
		assert names.contains('BAR')
	}

	private List<String> findTableNames() {
		Connection c = dataSource.connection
		ResultSet rs = c.metaData.getTables(null, null, '%', ['TABLE'] as String[])
		def names = []
		while (rs.next()) {
			names << rs.getString('TABLE_NAME').toUpperCase()
		}
		rs.close()
		c.close()
		names
	}
}
