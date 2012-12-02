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

    public static MySQLCompatibleChangeLogSerializer create() {
        new MySQLCompatibleChangeLogSerializer()
    }

    @Override
    void write(List<ChangeSet> changeSets, OutputStream out) throws IOException {
        super.write(reorderForeignKeysToEnd(changeSets), out)
    }

    private static List<ChangeSet> reorderForeignKeysToEnd(List<ChangeSet> changeSets) {
        List<ChangeSet> foreignKeyChangeSets = new LinkedList<>()
        List<ChangeSet> newChangeSets = new LinkedList<>()
        changeSets.each { changeSet ->
            if (hasForeignKeyConstraintChange(changeSet)) {
                foreignKeyChangeSets.add(changeSet)
            }
            else {
                newChangeSets.add(changeSet)
            }
        }
        newChangeSets.addAll(foreignKeyChangeSets)
        newChangeSets
    }

    private static Boolean hasForeignKeyConstraintChange(ChangeSet changeSet) {
        for (Change change : changeSet.changes) {
            if (change instanceof AddForeignKeyConstraintChange) {
                return true
            }
        }
        return false
    }
}
