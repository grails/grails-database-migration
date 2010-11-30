databaseChangeLog = {

	changeSet(author: 'burt', id: 'product.prize') {
		comment { 'Rename prize to price' }
		renameColumn(tableName: 'product', oldColumnName: 'prize', newColumnName: 'price', columnDataType: 'float(19)')
	}
}

