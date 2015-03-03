import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmReleaseLocksCommand

description('Releases all locks on the database changelog') {
    usage 'grails [environment] dbm-release-locks --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmReleaseLocksCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
