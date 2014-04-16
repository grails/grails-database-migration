package grails.plugin.databasemigration

import java.sql.Connection
import java.sql.ResultSet
import groovy.sql.Sql

import liquibase.changelog.ChangeLogParameters
import liquibase.database.Database
import liquibase.parser.core.xml.XMLChangeLogSAXParser

class MiscTests extends GroovyTestCase {

	def dataSource

	// for GPDATABASEMIGRATION-132
	void testTableNames() {
		executeUpdate('DROP TABLE XDATABASECHANGELOGX')
		executeUpdate('DROP TABLE XDATABASECHANGELOGLOCKX')
		def names = findTableNames()
		assert !names.contains('DATABASECHANGELOG')
		assert !names.contains('DATABASECHANGELOGLOCK')
		assert !names.contains('XDATABASECHANGELOGX')
		assert !names.contains('XDATABASECHANGELOGLOCKX')

		def databaseChangeLog = new XMLChangeLogSAXParser().parse('test.changelist.xml',
					new ChangeLogParameters(), new ParserTestResourceAccessor())

		Database database = MigrationUtils.getDatabase()
		database.checkDatabaseChangeLogTable false, databaseChangeLog, null
		database.checkDatabaseChangeLogLockTable()

		names = findTableNames()
		assert !names.contains('DATABASECHANGELOG')
		assert !names.contains('DATABASECHANGELOGLOCK')
		assert names.contains('XDATABASECHANGELOGX')
		assert names.contains('XDATABASECHANGELOGLOCKX')
	}

	protected void executeUpdate(String sql, List values = null) {
		Sql gsql = new Sql(dataSource)
		if (values) {
			gsql.executeUpdate sql, values
		}
		else {
			gsql.executeUpdate sql
		}
		gsql.close()
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
