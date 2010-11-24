// for testing only, not included in plugin zip

grails.plugin.databasemigration.changelogLocation = 'target'
grails.plugin.databasemigration.changelogFileName = 'changelog.cli.test.groovy'

grails.doc.authors = 'Burt Beckwith'
grails.doc.license = 'Apache License 2.0'
grails.doc.title = 'Database Migration Plugin'

log4j = {
	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			 'org.codehaus.groovy.grails.web.pages', //  GSP
			 'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			 'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			 'org.codehaus.groovy.grails.web.mapping', // URL mapping
			 'org.codehaus.groovy.grails.commons', // core / classloading
			 'org.codehaus.groovy.grails.plugins', // plugins
			 'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			 'org.springframework',
			 'org.hibernate',
			 'net.sf.ehcache.hibernate'

	warn   'org.mortbay.log'
	debug   'liquibase'
}
