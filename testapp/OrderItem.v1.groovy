package migrationtests

class OrderItem {
	Product product
	Integer quantity
	static belongsTo = Order
}

