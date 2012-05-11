grails.servlet.version = '2.5'
grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.source.level = 1.6
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'
	checksums

	repositories {
		inherits true

		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenLocal()
		mavenCentral()
	}

	dependencies {
		runtime 'mysql:mysql-connector-java:5.1.16'
	}

	plugins {
		compile ":hibernate:$grailsVersion"
		compile ":jquery:1.6.1.1"
		compile ":resources:1.1.1"

		build ":tomcat:$grailsVersion"

		runtime ":database-migration:1.0"
	}
}
