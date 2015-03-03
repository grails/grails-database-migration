import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmTagCommand

description('Adds a tag to mark the current database state') {
    usage 'grails [environment] dbm-tag [tagName] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'tagName', description: 'The name of the tag to use'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmTagCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
