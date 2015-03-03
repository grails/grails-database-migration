import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmStatusCommand

description('Outputs count or list of unrun change sets to STDOUT or a file') {
    usage 'grails [environment] dbm-status [filename] --verbose=[verbose] --contexts=[contexts] --defaultSchema=[defaultSchema] --dataSource=[dataSource]'
    flag name: 'filename', description: 'The path to the output file to write to. If not specified output is written to the console'
    flag name: 'verbose', description: 'If true (the default) the changesets are listed; if false only the count is displayed'
    flag name: 'contexts', description: 'A comma-delimited list of context names. If specified, only changesets tagged with one of the context names will be included'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
}

try {
    new DbmStatusCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
