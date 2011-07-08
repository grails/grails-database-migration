databaseChangeLog = {
	changeSet(author: 'burt', id: 't5') {
		createTable(tableName: 't5') {
			column(autoIncrement: 'true', name: 'id', type: 'BIGINT') {
				constraints(nullable: 'false', primaryKey: 'true')
			}
			column(name: 'name', type: 'VARCHAR(255)') {
				constraints(nullable: 'false')
			}
		}
	}
}
