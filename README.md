grails-database-migration
=========================

Added support for multi schema to the original plugin.

Configuration:

grails.plugin.databasemigration.multiSchema = false

grails.plugin.databasemigration.multiSchemaPattern = 'test_.*'

grails.plugin.databasemigration.multiSchemaList = ['test_1', 'test_2', 'test_3']


If enabled it gets the list of schemas from Connection.getMetaData().getSchemas() and matches that to the pattern and list.

Working with PostgresSql.
