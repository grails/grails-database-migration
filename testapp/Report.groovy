package migrationtests

class Report {
	String name
	String value
	Date dateCreated

	static mapping = {
		datasource 'reports'
	}
}
