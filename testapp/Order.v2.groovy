package migrationtests

class Order {
	Customer customer
	Date orderDate
	static hasMany = [items: OrderItem]
	static mapping = {
		table 'orders'
	}
}

