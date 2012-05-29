grails.project.groupId = appName
grails.mime.file.extensions = false
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

grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.views.default.codec = 'html'
grails.views.gsp.encoding = 'UTF-8'
grails.converters.encoding = 'UTF-8'
grails.views.gsp.sitemesh.preprocess = true
grails.scaffolding.templates.domainSuffix = ''
grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.spring.bean.packages = []
grails.web.disable.multipart=false
grails.exceptionresolver.params.exclude = ['password']
grails.hibernate.cache.queries = false

grails.dbconsole.enabled = true

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
	}
}

log4j = {
	error 'org.codehaus.groovy.grails',
	      'org.springframework',
	      'org.hibernate',
	      'net.sf.ehcache.hibernate'

	debug 'liquibase', 'grails.plugin.databasemigration'
}

grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

grails.plugin.databasemigration.reports.updateOnStart = true
grails.plugin.databasemigration.reports.updateOnStartFileNames = ['changelog-reports.groovy']
