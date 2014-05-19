// for testing only, not included in plugin zip

grails.plugin.databasemigration.changelogLocation = 'target/changelogs'
grails.plugin.databasemigration.changelogFileName = 'changelog.cli.test.groovy'
grails.plugin.databasemigration.changelogProperties = [
	changeset_author: 'Hunter S. Thompson',
	changeset_id: 'Fear and Loathing in Las Vegas']

grails.plugin.databasemigration.secondary.changelogLocation = 'target/changelogs'
grails.plugin.databasemigration.secondary.changelogFileName = 'changelog.cli.secondary-test.groovy'

grails.plugin.databasemigration.databaseChangeLogTableName = 'xdatabasechangelogx'
grails.plugin.databasemigration.databaseChangeLogLockTableName = 'xdatabasechangeloglockx'

grails.doc.authors = 'Burt Beckwith'
grails.doc.license = 'Apache License 2.0'
grails.doc.title = 'Database Migration Plugin'

log4j = {
	error 'org.codehaus.groovy.grails',
	      'org.springframework',
	      'org.hibernate',
	      'net.sf.ehcache.hibernate'

	debug 'liquibase'
}

grails.plugin.databasemigration.multiSchema = false
grails.plugin.databasemigration.multiSchemaPattern = 'test_.*'
grails.plugin.databasemigration.meta.multiSchemaQuery = false
grails.plugin.databasemigration.multiSchemaList = ['test_1', 'test_2', 'test_3']