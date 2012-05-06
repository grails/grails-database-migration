package grails.plugin.databasemigration.test

class SecondaryThing {

    String name

    static mapping = {
        datasources(['secondary'])
    }

}
