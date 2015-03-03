import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmValidateCommand

description('Checks the changelog for errors') {
    usage 'grails [environment] dbm-validate --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmValidateCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
