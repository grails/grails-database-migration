import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmClearChecksumsCommand

description('Removes current checksums from database. On next run checksums will be recomputed') {
    usage 'grails [environment] dbm-clear-checksums --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmClearChecksumsCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
