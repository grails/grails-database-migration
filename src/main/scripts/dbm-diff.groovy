import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmDiffCommand

description('Compares two databases and creates a changelog that will make the changes required to bring them into sync') {
    usage 'grails [environment] dbm-diff [otherEnv] [filename] --defaultSchema=[defaultSchema] --dataSource=[dataSource] --add'
    flag name: 'otherEnv', description: 'The name of the environment to compare to'
    flag name: 'filename', description: 'The path to the output file to write to. If not specified output is written to the console'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
    flag name: 'add', description: 'if provided will run the script for the specified dataSource. Not needed for the default dataSource.'
}

try {
    new DbmDiffCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
