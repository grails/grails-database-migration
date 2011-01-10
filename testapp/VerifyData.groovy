includeTargets << grailsScript('_GrailsBootstrap')

target(populateData: 'Insert data using GORM') {
	depends(classpath, bootstrap, loadApp)

	def Product = grailsApp.getDomainClass('migrationtests.Product').clazz
	def Order = grailsApp.getDomainClass('migrationtests.Order').clazz

	Order.withTransaction { status ->
		assert 3 == Product.count()
		def products = Product.listOrderById()

		assert 'p1' == products[0].name
		assert 'Electronics' == products[0].category
		assert 100 == products[0].price

		assert 'p2' == products[1].name
		assert 'Food' == products[1].category
		assert 5 == products[1].price

		assert 'p3' == products[2].name
		assert 'Automobiles' == products[2].category
		assert 30000 == products[2].price

		assert 3 == Order.count()
		def orders = Order.listOrderById()

		assert 'c1' == orders[0].customer.name
		assert 1 == orders[0].items.size()
		def items = orders[0].items.sort { it.product.name }
		assert 'p1' == items[0].product.name
		assert 1 == items[0].quantity

		assert 'c1' == orders[1].customer.name
		assert 2 == orders[1].items.size()
		items = orders[1].items.sort { it.product.name }
		assert 'p2' == items[0].product.name
		assert 2 == items[0].quantity
		assert 'p3' == items[1].product.name
		assert 1 == items[1].quantity

		assert 'c2' == orders[2].customer.name
		assert 1 == orders[2].items.size()
		items = orders[2].items.sort { it.product.name }
		assert 'p1' == items[0].product.name
		assert 5 == items[0].quantity
	}
}

setDefaultTarget(populateData)

