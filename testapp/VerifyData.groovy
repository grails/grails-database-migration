includeTargets << grailsScript('_GrailsBootstrap')

target(verifyData: 'Verifies data') {
	depends(classpath, enableExpandoMetaClass, bootstrap, loadApp)

	def Product = grailsApp.getDomainClass('migrationtests.Product').clazz
	def Order = grailsApp.getDomainClass('migrationtests.Order').clazz

	Order.withTransaction { status ->
		assertEquals 3, Product.count()
		def products = Product.listOrderById()

		assertEquals 'p1', products[0].name
		assertEquals 'Electronics', products[0].category
		assertEquals 100, products[0].price

		assertEquals 'p2', products[1].name
		assertEquals 'Food', products[1].category
		assertEquals 5, products[1].price

		assertEquals 'p3', products[2].name
		assertEquals 'Automobiles', products[2].category
		assertEquals 30000, products[2].price

		assertEquals 3, Order.count()
        def orders = Order.listOrderById()

        orders.each { order ->
            order.refresh()
            order.customer.refresh()
            order.items.each{ item ->
                item.refresh()
                item.product.refresh()
            }
        }

        assertEquals 'c1', orders[0].customer.name
        assertEquals 1, orders[0].items.size()
        def items = orders[0].items.sort { it.product.name }
        assertEquals 'p1', items[0].product.name
        assertEquals 1, items[0].quantity

        assertEquals 'c1', orders[1].customer.name
        assertEquals 2, orders[1].items.size()
        items = orders[1].items.sort { it.product.name }
        assertEquals 'p2', items[0].product.name
        assertEquals 2, items[0].quantity
        assertEquals 'p3', items[1].product.name
        assertEquals 1, items[1].quantity

        assertEquals 'c2', orders[2].customer.name
        assertEquals 1, orders[2].items.size()
        items = orders[2].items.sort { it.product.name }
        assertEquals 'p1', items[0].product.name
        assertEquals 5, items[0].quantity
	}
}

private void assertEquals(expected, actual) {
	if (expected != actual) {
		throw new RuntimeException("expected:<" + expected + "> but was:<" + actual + ">")
	}
}

target(enableExpandoMetaClass: "Calls ExpandoMetaClass.enableGlobally()") {
	ExpandoMetaClass.enableGlobally()
}

setDefaultTarget verifyData
