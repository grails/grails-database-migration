databaseChangeLog = {

	changeSet(author: 'burt', id: 'product.prize') {
		comment { 'Rename prize to price' }
		renameColumn(tableName: 'product', oldColumnName: 'prize', newColumnName: 'price', columnDataType: 'float(19)')
	}

	changeSet(author: 'burt', id: 'product.name') {
		comment { 'narrow name to 100 chars' }
		modifyDataType(tableName: 'product', columnName: 'name', newDataType: 'varchar(100)')
	}
}
