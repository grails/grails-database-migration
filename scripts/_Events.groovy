eventPackageAppEnd = {
	try {
		def MigrationUtils = classLoader.loadClass('grails.plugin.databasemigration.MigrationUtils')
		MigrationUtils.scriptName = binding.variables.scriptName
	}
	catch (Throwable t) {
		println "\nERROR setting MigrationUtils.scriptName, auto-migrate not possible: $e.message\n"
	}
}
