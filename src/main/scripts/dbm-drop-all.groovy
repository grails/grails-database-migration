import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmDropAllCommand

description('Drops all database objects owned by the user') {
    usage 'grails [environment] dbm-drop-all [schemaNames] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'schemaNames', description: 'A comma-delimited list of schema names to use'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmDropAllCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
