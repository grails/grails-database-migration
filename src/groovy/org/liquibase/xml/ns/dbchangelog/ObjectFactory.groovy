package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlElementDecl
import javax.xml.bind.annotation.XmlRegistry
import javax.xml.namespace.QName

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.liquibase.xml.ns.dbchangelog package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
class ObjectFactory {

	private static final QName _Comment_QNAME = new QName('http://www.liquibase.org/xml/ns/dbchangelog', 'comment')
	private static final QName _DeleteWhere_QNAME = new QName('http://www.liquibase.org/xml/ns/dbchangelog', 'where')
	private static final QName _LoadDataColumn_QNAME = new QName('http://www.liquibase.org/xml/ns/dbchangelog', 'column')

	/**
	 * Create an instance of {@link CustomChange }
	 */
	CustomChange createCustomChange() {
		new CustomChange()
	}

	/**
	 * Create an instance of {@link CustomPrecondition }
	 */
	CustomPrecondition createCustomPrecondition() {
		new CustomPrecondition()
	}

	/**
	 * Create an instance of {@link LoadUpdateData }
	 */
	LoadUpdateData createLoadUpdateData() {
		new LoadUpdateData()
	}

	/**
	 * Create an instance of {@link LoadData }
	 */
	LoadData createLoadData() {
		new LoadData()
	}

	/**
	 * Create an instance of {@link ExecuteCommand }
	 */
	ExecuteCommand createExecuteCommand() {
		new ExecuteCommand()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog }
	 */
	DatabaseChangeLog createDatabaseChangeLog() {
		new DatabaseChangeLog()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet }
	 */
	DatabaseChangeLog.ChangeSet createDatabaseChangeLogChangeSet() {
		new DatabaseChangeLog.ChangeSet()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql }
	 */
	DatabaseChangeLog.ChangeSet.ModifySql createDatabaseChangeLogChangeSetModifySql() {
		new DatabaseChangeLog.ChangeSet.ModifySql()
	}

	/**
	 * Create an instance of {@link CustomChange.Param }
	 */
	CustomChange.Param createCustomChangeParam() {
		new CustomChange.Param()
	}

	/**
	 * Create an instance of {@link CreateView }
	 */
	CreateView createCreateView() {
		new CreateView()
	}

	/**
	 * Create an instance of {@link RenameTable }
	 */
	RenameTable createRenameTable() {
		new RenameTable()
	}

	/**
	 * Create an instance of {@link DropUniqueConstraint }
	 */
	DropUniqueConstraint createDropUniqueConstraint() {
		new DropUniqueConstraint()
	}

	/**
	 * Create an instance of {@link AddUniqueConstraint }
	 */
	AddUniqueConstraint createAddUniqueConstraint() {
		new AddUniqueConstraint()
	}

	/**
	 * Create an instance of {@link SqlCheck }
	 */
	SqlCheck createSqlCheck() {
		new SqlCheck()
	}

	/**
	 * Create an instance of {@link TagDatabase }
	 */
	TagDatabase createTagDatabase() {
		new TagDatabase()
	}

	/**
	 * Create an instance of {@link CreateProcedure }
	 */
	CreateProcedure createCreateProcedure() {
		new CreateProcedure()
	}

	/**
	 * Create an instance of {@link DropSequence }
	 */
	DropSequence createDropSequence() {
		new DropSequence()
	}

	/**
	 * Create an instance of {@link AddForeignKeyConstraint }
	 */
	AddForeignKeyConstraint createAddForeignKeyConstraint() {
		new AddForeignKeyConstraint()
	}

	/**
	 * Create an instance of {@link DropPrimaryKey }
	 */
	DropPrimaryKey createDropPrimaryKey() {
		new DropPrimaryKey()
	}

	/**
	 * Create an instance of {@link CustomPrecondition.Param }
	 */
	CustomPrecondition.Param createCustomPreconditionParam() {
		new CustomPrecondition.Param()
	}

	/**
	 * Create an instance of {@link AlterSequence }
	 */
	AlterSequence createAlterSequence() {
		new AlterSequence()
	}

	/**
	 * Create an instance of {@link DropView }
	 */
	DropView createDropView() {
		new DropView()
	}

	/**
	 * Create an instance of {@link Or }
	 */
	Or createOr() {
		new Or()
	}

	/**
	 * Create an instance of {@link And }
	 */
	And createAnd() {
		new And()
	}

	/**
	 * Create an instance of {@link Not }
	 */
	Not createNot() {
		new Not()
	}

	/**
	 * Create an instance of {@link Dbms }
	 */
	Dbms createDbms() {
		new Dbms()
	}

	/**
	 * Create an instance of {@link RunningAs }
	 */
	RunningAs createRunningAs() {
		new RunningAs()
	}

	/**
	 * Create an instance of {@link ChangeSetExecuted }
	 */
	ChangeSetExecuted createChangeSetExecuted() {
		new ChangeSetExecuted()
	}

	/**
	 * Create an instance of {@link TableExists }
	 */
	TableExists createTableExists() {
		new TableExists()
	}

	/**
	 * Create an instance of {@link ColumnExists }
	 */
	ColumnExists createColumnExists() {
		new ColumnExists()
	}

	/**
	 * Create an instance of {@link SequenceExists }
	 */
	SequenceExists createSequenceExists() {
		new SequenceExists()
	}

	/**
	 * Create an instance of {@link ForeignKeyConstraintExists }
	 */
	ForeignKeyConstraintExists createForeignKeyConstraintExists() {
		new ForeignKeyConstraintExists()
	}

	/**
	 * Create an instance of {@link IndexExists }
	 */
	IndexExists createIndexExists() {
		new IndexExists()
	}

	/**
	 * Create an instance of {@link PrimaryKeyExists }
	 */
	PrimaryKeyExists createPrimaryKeyExists() {
		new PrimaryKeyExists()
	}

	/**
	 * Create an instance of {@link ViewExists }
	 */
	ViewExists createViewExists() {
		new ViewExists()
	}

	/**
	 * Create an instance of {@link ChangeLogPropertyDefined }
	 */
	ChangeLogPropertyDefined createChangeLogPropertyDefined() {
		new ChangeLogPropertyDefined()
	}

	/**
	 * Create an instance of {@link Constraints }
	 */
	Constraints createConstraints() {
		new Constraints()
	}

	/**
	 * Create an instance of {@link DropDefaultValue }
	 */
	DropDefaultValue createDropDefaultValue() {
		new DropDefaultValue()
	}

	/**
	 * Create an instance of {@link DropTable }
	 */
	DropTable createDropTable() {
		new DropTable()
	}

	/**
	 * Create an instance of {@link AddColumn }
	 */
	AddColumn createAddColumn() {
		new AddColumn()
	}

	/**
	 * Create an instance of {@link org.liquibase.xml.ns.dbchangelog.Column }
	 */
	org.liquibase.xml.ns.dbchangelog.Column createColumn() {
		new org.liquibase.xml.ns.dbchangelog.Column()
	}

	/**
	 * Create an instance of {@link MergeColumns }
	 */
	MergeColumns createMergeColumns() {
		new MergeColumns()
	}

	/**
	 * Create an instance of {@link AddNotNullConstraint }
	 */
	AddNotNullConstraint createAddNotNullConstraint() {
		new AddNotNullConstraint()
	}

	/**
	 * Create an instance of {@link LoadUpdateData.Column }
	 */
	LoadUpdateData.Column createLoadUpdateDataColumn() {
		new LoadUpdateData.Column()
	}

	/**
	 * Create an instance of {@link LoadData.Column }
	 */
	LoadData.Column createLoadDataColumn() {
		new LoadData.Column()
	}

	/**
	 * Create an instance of {@link DropNotNullConstraint }
	 */
	DropNotNullConstraint createDropNotNullConstraint() {
		new DropNotNullConstraint()
	}

	/**
	 * Create an instance of {@link Update }
	 */
	Update createUpdate() {
		new Update()
	}

	/**
	 * Create an instance of {@link Stop }
	 */
	Stop createStop() {
		new Stop()
	}

	/**
	 * Create an instance of {@link CreateTable }
	 */
	CreateTable createCreateTable() {
		new CreateTable()
	}

	/**
	 * Create an instance of {@link AddDefaultValue }
	 */
	AddDefaultValue createAddDefaultValue() {
		new AddDefaultValue()
	}

	/**
	 * Create an instance of {@link DropIndex }
	 */
	DropIndex createDropIndex() {
		new DropIndex()
	}

	/**
	 * Create an instance of {@link Rollback }
	 */
	Rollback createRollback() {
		new Rollback()
	}

	/**
	 * Create an instance of {@link RenameView }
	 */
	RenameView createRenameView() {
		new RenameView()
	}

	/**
	 * Create an instance of {@link Insert }
	 */
	Insert createInsert() {
		new Insert()
	}

	/**
	 * Create an instance of {@link Sql }
	 */
	Sql createSql() {
		new Sql()
	}

	/**
	 * Create an instance of {@link SqlFile }
	 */
	SqlFile createSqlFile() {
		new SqlFile()
	}

	/**
	 * Create an instance of {@link RenameColumn }
	 */
	RenameColumn createRenameColumn() {
		new RenameColumn()
	}

	/**
	 * Create an instance of {@link DropColumn }
	 */
	DropColumn createDropColumn() {
		new DropColumn()
	}

	/**
	 * Create an instance of {@link ModifyDataType }
	 */
	ModifyDataType createModifyDataType() {
		new ModifyDataType()
	}

	/**
	 * Create an instance of {@link CreateSequence }
	 */
	CreateSequence createCreateSequence() {
		new CreateSequence()
	}

	/**
	 * Create an instance of {@link CreateIndex }
	 */
	CreateIndex createCreateIndex() {
		new CreateIndex()
	}

	/**
	 * Create an instance of {@link DropForeignKeyConstraint }
	 */
	DropForeignKeyConstraint createDropForeignKeyConstraint() {
		new DropForeignKeyConstraint()
	}

	/**
	 * Create an instance of {@link DropAllForeignKeyConstraints }
	 */
	DropAllForeignKeyConstraints createDropAllForeignKeyConstraints() {
		new DropAllForeignKeyConstraints()
	}

	/**
	 * Create an instance of {@link AddPrimaryKey }
	 */
	AddPrimaryKey createAddPrimaryKey() {
		new AddPrimaryKey()
	}

	/**
	 * Create an instance of {@link AddLookupTable }
	 */
	AddLookupTable createAddLookupTable() {
		new AddLookupTable()
	}

	/**
	 * Create an instance of {@link AddAutoIncrement }
	 */
	AddAutoIncrement createAddAutoIncrement() {
		new AddAutoIncrement()
	}

	/**
	 * Create an instance of {@link Delete }
	 */
	Delete createDelete() {
		new Delete()
	}

	/**
	 * Create an instance of {@link ExecuteCommand.Arg }
	 */
	ExecuteCommand.Arg createExecuteCommandArg() {
		new ExecuteCommand.Arg()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.Property }
	 */
	DatabaseChangeLog.Property createDatabaseChangeLogProperty() {
		new DatabaseChangeLog.Property()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.PreConditions }
	 */
	DatabaseChangeLog.PreConditions createDatabaseChangeLogPreConditions() {
		new DatabaseChangeLog.PreConditions()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.Include }
	 */
	DatabaseChangeLog.Include createDatabaseChangeLogInclude() {
		new DatabaseChangeLog.Include()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.IncludeAll }
	 */
	DatabaseChangeLog.IncludeAll createDatabaseChangeLogIncludeAll() {
		new DatabaseChangeLog.IncludeAll()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ValidCheckSum }
	 */
	DatabaseChangeLog.ChangeSet.ValidCheckSum createDatabaseChangeLogChangeSetValidCheckSum() {
		new DatabaseChangeLog.ChangeSet.ValidCheckSum()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.PreConditions }
	 */
	DatabaseChangeLog.ChangeSet.PreConditions createDatabaseChangeLogChangeSetPreConditions() {
		new DatabaseChangeLog.ChangeSet.PreConditions()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Replace }
	 */
	DatabaseChangeLog.ChangeSet.ModifySql.Replace createDatabaseChangeLogChangeSetModifySqlReplace() {
		new DatabaseChangeLog.ChangeSet.ModifySql.Replace()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace }
	 */
	DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace createDatabaseChangeLogChangeSetModifySqlRegExpReplace() {
		new DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Prepend }
	 */
	DatabaseChangeLog.ChangeSet.ModifySql.Prepend createDatabaseChangeLogChangeSetModifySqlPrepend() {
		new DatabaseChangeLog.ChangeSet.ModifySql.Prepend()
	}

	/**
	 * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Append }
	 */
	DatabaseChangeLog.ChangeSet.ModifySql.Append createDatabaseChangeLogChangeSetModifySqlAppend() {
		new DatabaseChangeLog.ChangeSet.ModifySql.Append()
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
	 */
	@XmlElementDecl(namespace='http://www.liquibase.org/xml/ns/dbchangelog', name='comment')
	JAXBElement<String> createComment(String value) {
		new JAXBElement<String>(_Comment_QNAME, String, null, value)
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
	 */
	@XmlElementDecl(namespace='http://www.liquibase.org/xml/ns/dbchangelog', name='where', scope=Delete)
	JAXBElement<Object> createDeleteWhere(Object value) {
		new JAXBElement<Object>(_DeleteWhere_QNAME, Object, Delete, value)
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
	 */
	@XmlElementDecl(namespace='http://www.liquibase.org/xml/ns/dbchangelog', name='where', scope=Update)
	JAXBElement<Object> createUpdateWhere(Object value) {
		new JAXBElement<Object>(_DeleteWhere_QNAME, Object, Update, value)
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link LoadData.Column }{@code >}}
	 */
	@XmlElementDecl(namespace='http://www.liquibase.org/xml/ns/dbchangelog', name='column', scope=LoadData)
	JAXBElement<LoadData.Column> createLoadDataColumn(LoadData.Column value) {
		new JAXBElement<LoadData.Column>(_LoadDataColumn_QNAME, LoadData.Column, LoadData, value)
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link LoadUpdateData.Column }{@code >}}
	 */
	@XmlElementDecl(namespace='http://www.liquibase.org/xml/ns/dbchangelog', name='column', scope=LoadUpdateData)
	JAXBElement<LoadUpdateData.Column> createLoadUpdateDataColumn(LoadUpdateData.Column value) {
		new JAXBElement<LoadUpdateData.Column>(_LoadDataColumn_QNAME, LoadUpdateData.Column, LoadUpdateData, value)
	}
}
