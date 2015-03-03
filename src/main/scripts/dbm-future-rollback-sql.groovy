import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmFutureRollbackSqlCommand

description('Writes SQL to roll back the database to the current state after the changes in the changeslog have been applied to STDOUT or a file') {
    usage 'grails [environment] dbm-future-rollback-sql [filename] --contexts=[contexts] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'filename', description: 'The path to the output file to write to. If not specified output is written to the console'
    flag name: 'contexts', description: 'A comma-delimited list of context names. If specified, only changesets tagged with one of the context names will be included'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmFutureRollbackSqlCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
