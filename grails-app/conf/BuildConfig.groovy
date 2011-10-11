grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.docs.output.dir = 'docs/manual' // for backwards-compatibility, the docs are checked into gh-pages branch

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenLocal()
		mavenCentral()
	}

	dependencies {

		compile('org.liquibase:liquibase-core:2.0.1') {
			transitive = false
		}

		compile('com.h2database:h2:1.2.144') {
			transitive = false
			export = false
		}
	}

	plugins {
		build(':release:1.0.0.RC3') {
			export = false
		}
	}
}
