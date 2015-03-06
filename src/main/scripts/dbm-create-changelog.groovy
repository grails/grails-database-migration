import org.grails.plugins.databasemigration.DatabaseMigrationException
import org.grails.plugins.databasemigration.command.DbmCreateChangelog

description('Creates an empty changelog file') {
    usage 'grails [environment] dbm-create-changelog [filename]'
    flag name: 'filename', description: 'The path to the output file to write to'
}

try {
    new DbmCreateChangelog().handle(executionContext)
} catch (DatabaseMigrationException e) {
    error e.message, e
}
