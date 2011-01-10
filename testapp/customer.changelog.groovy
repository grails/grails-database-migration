import liquibase.statement.core.InsertStatement
import liquibase.statement.core.UpdateStatement

databaseChangeLog = {

	changeSet(author: "burt", id: "customer_table_1") {
		comment { 'Create the customer table' }
		createTable(tableName: "customer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "burt", id: "customer_table_2") {
		comment { 'Add the customer_id column, FK and index' }
		addColumn(tableName: "orders") {
			column(name: "customer_id", type: "bigint") {
				constraints(nullable: "true") // will fix below
			}
		}

		createIndex(indexName: "FKC3DF62E5BE5C611A", tableName: "orders") {
			column(name: "customer_id")
		}

		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "orders", constraintName: "FKC3DF62E5BE5C611A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "burt", id: "customer_table_3") {
		comment { 'Migrate data' }

		grailsChange {
			change {
				def names = []
				sql.eachRow 'select distinct customer from orders', { row -> names << row.customer }
				names.eachWithIndex { String name, int id ->
					sqlStatement new InsertStatement(null, 'customer')
						.addColumnValue('id', id + 1)
						.addColumnValue('version', 0)
						.addColumnValue('name', name)
						.addColumnValue('username', name)

					sqlStatement new UpdateStatement(null, 'orders')
						.addNewColumnValue('customer_id', id + 1)
						.setWhereClause('customer=?')
						.addWhereParameter(name)
				}

				confirm 'Migrated order.customer data to customer table'
			}
		}
	}

	changeSet(author: "burt", id: "customer_table_4") {
		comment { 'make customer_id not null' }
		addNotNullConstraint(tableName: 'orders', columnName: 'customer_id', columnDataType: 'bigint')
	}

	changeSet(author: "burt", id: "customer_table_5") {
		comment { 'Drop the customer column' }
		dropColumn(columnName: "customer", tableName: "orders")
	}
}

