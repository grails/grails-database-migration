databaseChangeLog = {

	property(name: 'tableName', value: 'property_based')
	property(name: 'pk', value: 'table_id')
	property(file: 'columns.properties')

	changeSet(author: '${changeset_author}', id: '${changeset_id}') {
		createTable(tableName: '${tableName}') {
			column(autoIncrement: 'true', name: '${pk}', type: 'BIGINT') {
				constraints(nullable: 'false', primaryKey: 'true')
			}
			column(name: '${col1Name}', type: '${col1Type}') {
				constraints(nullable: '$col1Nullable')
			}
			column(name: '${col2Name}', type: '${col2Type}') {
				constraints(nullable: '${col2Nullable}')
			}
		}
	}

	include(file: 'test.property.changelist.xml')
}
