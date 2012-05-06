includeTargets << grailsScript('_GrailsBootstrap')

target(populateData: 'Insert data using GORM') {
	depends(classpath, enableExpandoMetaClass, bootstrap, loadApp)

	def Product = grailsApp.getDomainClass('migrationtests.Product').clazz
	def Order = grailsApp.getDomainClass('migrationtests.Order').clazz
	def Report = grailsApp.getDomainClass('migrationtests.Report').clazz

	Product.withTransaction { status ->
		def p1 = Product.newInstance(name: 'p1', category: 'Electronics', prize: 100).save()
		def p2 = Product.newInstance(name: 'p2', category: 'Food', prize: 5).save()
		def p3 = Product.newInstance(name: 'p3', category: 'Automobiles', prize: 30000).save()

		def o1 = Order.newInstance(customer: 'c1', orderDate: new Date())
		o1.addToItems(product: p1, quantity: 1)
		o1.save()

		def o2 = Order.newInstance(customer: 'c1', orderDate: new Date() - 1)
		o2.addToItems(product: p2, quantity: 2)
		o2.addToItems(product: p3, quantity: 1)
		o2.save()

		def o3 = Order.newInstance(customer: 'c2', orderDate: new Date() - 10)
		o3.addToItems(product: p1, quantity: 5)
		o3.save()

        def r1 = Report.newInstance(name:'r1', value:'Foo').save()
        def r2 = Report.newInstance(name:'r2', value:'Bar').save()
        def r3 = Report.newInstance(name:'r3', value:'Baz').save()
	}
}

target(enableExpandoMetaClass: "Calls ExpandoMetaClass.enableGlobally()") {
	ExpandoMetaClass.enableGlobally()
}

setDefaultTarget(populateData)
