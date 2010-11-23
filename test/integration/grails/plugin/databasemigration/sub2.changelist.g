databaseChangeLog = {
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

	include(file: 'sub1/sub2/sub3.changelist.xml')

	includeAll(path: 'sub1/sub_all')
}
