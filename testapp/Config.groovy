grails.project.groupId = appName
grails.mime.file.extensions = true
grails.mime.use.accept.header = false
grails.mime.types = [
	html: ['text/html','application/xhtml+xml'],
	xml: ['text/xml', 'application/xml'],
	text: 'text/plain',
	js: 'text/javascript',
	rss: 'application/rss+xml',
	atom: 'application/atom+xml',
	css: 'text/css',
	csv: 'text/csv',
	all: '*/*',
	json: ['application/json','text/json'],
	form: 'application/x-www-form-urlencoded',
	multipartForm: 'multipart/form-data'
]

grails.views.default.codec = 'none'
grails.views.gsp.encoding = 'UTF-8'
grails.converters.encoding = 'UTF-8'
grails.views.gsp.sitemesh.preprocess = true
grails.scaffolding.templates.domainSuffix = 'Instance'

grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.logging.jul.usebridge = true
grails.spring.bean.packages = []

environments {
	production {
		grails.serverURL = 'http://www.changeme.com'
	}
	development {
		grails.serverURL = "http://localhost:8080/${appName}"
	}
	test {
		grails.serverURL = "http://localhost:8080/${appName}"
	}
}

log4j = {
	error  'org.codehaus.groovy.grails',
	       'org.springframework',
	       'org.hibernate',
	       'net.sf.ehcache.hibernate'

	debug   'liquibase', 'grails.plugin.databasemigration'
}

grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

