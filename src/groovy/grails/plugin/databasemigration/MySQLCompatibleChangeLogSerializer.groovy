package grails.plugin.databasemigration

import liquibase.change.Change
import liquibase.change.core.AddForeignKeyConstraintChange
import liquibase.changelog.ChangeSet
import liquibase.serializer.core.xml.XMLChangeLogSerializer

/**
 * Reorder ChangeSets with Foreign Key changes to the end of changes.<p/>
 *
 * MySQL need the Indexes created before a Foreign Key with the same name, elsewhere it throws an ERROR 1280 (42000): Incorrect index name '*INDEXNAME*'
 * @see <a href="http://bugs.mysql.com/bug.php?id=55465">http://bugs.mysql.com/bug.php?id=55465</a>
 */
class MySQLCompatibleChangeLogSerializer extends XMLChangeLogSerializer {

	@Override
	void write(List<ChangeSet> changeSets, OutputStream out) throws IOException {
		super.write reorderForeignKeysToEnd(changeSets), out
	}

	protected static List<ChangeSet> reorderForeignKeysToEnd(List<ChangeSet> changeSets) {
		List<ChangeSet> foreignKeyChangeSets = []
		List<ChangeSet> newChangeSets = []

		for (ChangeSet changeSet in changeSets) {
			if (hasForeignKeyConstraintChange(changeSet)) {
				foreignKeyChangeSets << changeSet
			}
			else {
				newChangeSets << changeSet
			}
		}

		newChangeSets + foreignKeyChangeSets
	}

	protected static boolean hasForeignKeyConstraintChange(ChangeSet changeSet) {
		changeSet.changes.find { Change change -> change instanceof AddForeignKeyConstraintChange }
	}
}
