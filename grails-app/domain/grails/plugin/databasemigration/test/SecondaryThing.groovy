package grails.plugin.databasemigration.test

class SecondaryThing {

	String name

	static mapping = {
		datasource 'secondary'
	}
}
