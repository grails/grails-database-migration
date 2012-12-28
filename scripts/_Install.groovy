createConfig()
def conf = config.grails.plugin.databasemigration
String changelogLocation = conf.changelogLocation ?: 'grails-app/migrations'
ant.mkdir dir: "$basedir/$changelogLocation"
