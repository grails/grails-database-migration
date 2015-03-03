import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmDbDocCommand

description('Generates Javadoc-like documentation based on current database and change log') {
    usage 'grails [environment] dbm-db-doc [destination] --contexts=[contexts] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'destination', description: 'The path to write to'
    flag name: 'contexts', description: 'A comma-delimited list of context names. If specified, only changesets tagged with one of the context names will be included'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmDbDocCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
