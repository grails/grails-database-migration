import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmListLocksCommand

description('Lists who currently has locks on the database changelog to STDOUT or a file') {
    usage 'grails [environment] dbm-list-locks [filename] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'filename', description: 'The path to the output file to write to. If not specified output is written to the console'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmListLocksCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
