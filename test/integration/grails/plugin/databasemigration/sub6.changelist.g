databaseChangeLog = {
	logicalFilePath "site-autobase"

	changeSet(author: 'not_burt', id: 't3') {
		createTable(tableName: 't3') {
			column(autoIncrement: 'true', name: 'id', type: 'BIGINT') {
				constraints(nullable: 'false', primaryKey: 'true')
			}
			column(name: 'name', type: 'VARCHAR(255)') {
				constraints(nullable: 'false')
			}
		}
	}

	changeSet(author: 'not_burt', id: 't4') {
		addColumn(tableName: 't3') {
			column(name: 'type', type: 'VARCHAR(50)')
		}
	}
}
