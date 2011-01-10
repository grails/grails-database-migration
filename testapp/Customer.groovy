package migrationtests

class Customer {

	String name
	String username

	static constraints = {
		username unique: true
	}
}

