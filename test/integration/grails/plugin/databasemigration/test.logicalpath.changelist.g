databaseChangeLog = {
	logicalFilePath "main"

	changeSet(author: 'burt', id: 't1') {
		createTable(tableName: 't1') {
			column(autoIncrement: 'true', name: 'id', type: 'BIGINT') {
				constraints(nullable: 'false', primaryKey: 'true')
			}
			column(name: 'version', type: 'BIGINT') {
				constraints(nullable: 'false')
			}
			column(name: 'name', type: 'VARCHAR(255)') {
				constraints(nullable: 'false')
			}
		}
	}

	include(file: 'sub6.changelist.groovy')
	include(file: 'sub7.changelist.groovy')
}
