package migrationtests

class Order {
	String customer
	Date orderDate
	static hasMany = [items: OrderItem]
	static mapping = {
		table 'orders'
	}
}

