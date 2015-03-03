import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmGenerateChangelogCommand

description('Generates an initial changelog XML or Groovy DSL file from the database') {
    usage 'grails [environment] dbm-generate-changelog [filename] --diffTypes=[diffTypes] --defaultSchema=[defaultSchema] --dataSource=[dataSource] --add'
    flag name: 'filename', description: 'The path to the output file to write to. If not specified output is written to the console'
    flag name: 'defaultSchema', description: 'The default schema name to use'
    flag name: 'dataSource', description: 'If provided will run the script for the specified dataSource. Not needed for the default dataSource'
    flag name: 'add', description: 'if specified add an include in the root changelog file referencing the new file'
}

try {
    new DbmGenerateChangelogCommand().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
