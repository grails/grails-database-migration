includeTargets << grailsScript('_GrailsBootstrap')

target(populateData: 'Insert data using GORM') {
	depends(classpath, enableExpandoMetaClass, bootstrap, loadApp)

	def Product = grailsApp.getDomainClass('migrationtests.Product').clazz
	def Order = grailsApp.getDomainClass('migrationtests.Order').clazz
	def Report = grailsApp.getDomainClass('migrationtests.Report').clazz

	Product.withTransaction { status ->
		def p1 = Product.newInstance(name: 'p1', category: 'Electronics', prize: 100).save(failOnError: true)
		def p2 = Product.newInstance(name: 'p2', category: 'Food', prize: 5).save(failOnError: true)
		def p3 = Product.newInstance(name: 'p3', category: 'Automobiles', prize: 30000).save(failOnError: true)

		def o1 = Order.newInstance(customer: 'c1', orderDate: new Date())
		o1.addToItems(product: p1, quantity: 1)
		o1.save(failOnError: true)

		def o2 = Order.newInstance(customer: 'c1', orderDate: new Date() - 1)
		o2.addToItems(product: p2, quantity: 2)
		o2.addToItems(product: p3, quantity: 1)
		o2.save(failOnError: true)

		def o3 = Order.newInstance(customer: 'c2', orderDate: new Date() - 10)
		o3.addToItems(product: p1, quantity: 5)
		o3.save(failOnError: true)

		Report.newInstance(name: 'r1', value: 'Foo').save(failOnError: true)
		Report.newInstance(name: 'r2', value: 'Bar').save(failOnError: true)
		Report.newInstance(name: 'r3', value: 'Baz').save(failOnError: true)
	}
}

target(enableExpandoMetaClass: "Calls ExpandoMetaClass.enableGlobally()") {
	ExpandoMetaClass.enableGlobally()
}

setDefaultTarget populateData
