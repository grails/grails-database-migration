package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAnyElement
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElementRef
import javax.xml.bind.annotation.XmlElementRefs
import javax.xml.bind.annotation.XmlMixed
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType
import org.w3c.dom.Element

/**
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeSetChildren" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="changeSetPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="changeSetAuthor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="changeSetId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['content'])
@XmlRootElement(name='rollback')
class Rollback {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link DropView }
	 * {@link CustomChange }
	 * {@link DropPrimaryKey }
	 * {@link AddAutoIncrement }
	 * {@link AddPrimaryKey }
	 * {@link AddLookupTable }
	 * {@link Stop }
	 * {@link CreateTable }
	 * {@link CreateView }
	 * {@link RenameView }
	 * {@link Update }
	 * {@link ExecuteCommand }
	 * {@link LoadData }
	 * {@link Element }
	 * {@link DropColumn }
	 * {@link String }
	 * {@link JAXBElement }{@code <}{@link String }{@code >}
	 * {@link MergeColumns }
	 * {@link DropSequence }
	 * {@link DropForeignKeyConstraint }
	 * {@link CreateSequence }
	 * {@link Object }
	 * {@link Rollback }
	 * {@link AddForeignKeyConstraint }
	 * {@link AddUniqueConstraint }
	 * {@link DropDefaultValue }
	 * {@link Sql }
	 * {@link CreateIndex }
	 * {@link ModifyDataType }
	 * {@link CreateProcedure }
	 * {@link Delete }
	 * {@link RenameColumn }
	 * {@link DropUniqueConstraint }
	 * {@link AddDefaultValue }
	 * {@link SqlFile }
	 * {@link DropAllForeignKeyConstraints }
	 * {@link DropTable }
	 * {@link RenameTable }
	 * {@link DropIndex }
	 * {@link Insert }
	 * {@link AddNotNullConstraint }
	 * {@link AlterSequence }
	 * {@link DropNotNullConstraint }
	 * {@link AddColumn }
	 * {@link LoadUpdateData }
	 */
	@XmlElementRefs([
		@XmlElementRef(name='customChange', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CustomChange, required=false),
		@XmlElementRef(name='dropView', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropView, required=false),
		@XmlElementRef(name='dropPrimaryKey', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropPrimaryKey, required=false),
		@XmlElementRef(name='addAutoIncrement', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddAutoIncrement, required=false),
		@XmlElementRef(name='stop', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Stop, required=false),
		@XmlElementRef(name='addLookupTable', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddLookupTable, required=false),
		@XmlElementRef(name='addPrimaryKey', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddPrimaryKey, required=false),
		@XmlElementRef(name='renameView', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=RenameView, required=false),
		@XmlElementRef(name='createView', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CreateView, required=false),
		@XmlElementRef(name='createTable', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CreateTable, required=false),
		@XmlElementRef(name='executeCommand', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=ExecuteCommand, required=false),
		@XmlElementRef(name='update', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Update, required=false),
		@XmlElementRef(name='loadData', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=LoadData, required=false),
		@XmlElementRef(name='dropColumn', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropColumn, required=false),
		@XmlElementRef(name='comment', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=JAXBElement, required=false),
		@XmlElementRef(name='mergeColumns', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=MergeColumns, required=false),
		@XmlElementRef(name='dropForeignKeyConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropForeignKeyConstraint, required=false),
		@XmlElementRef(name='dropSequence', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropSequence, required=false),
		@XmlElementRef(name='createSequence', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CreateSequence, required=false),
		@XmlElementRef(name='rollback', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Rollback, required=false),
		@XmlElementRef(name='addForeignKeyConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddForeignKeyConstraint, required=false),
		@XmlElementRef(name='addUniqueConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddUniqueConstraint, required=false),
		@XmlElementRef(name='dropDefaultValue', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropDefaultValue, required=false),
		@XmlElementRef(name='sql', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Sql, required=false),
		@XmlElementRef(name='createIndex', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CreateIndex, required=false),
		@XmlElementRef(name='modifyDataType', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=ModifyDataType, required=false),
		@XmlElementRef(name='createProcedure', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=CreateProcedure, required=false),
		@XmlElementRef(name='delete', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Delete, required=false),
		@XmlElementRef(name='renameColumn', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=RenameColumn, required=false),
		@XmlElementRef(name='dropUniqueConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropUniqueConstraint, required=false),
		@XmlElementRef(name='addDefaultValue', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddDefaultValue, required=false),
		@XmlElementRef(name='dropAllForeignKeyConstraints', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropAllForeignKeyConstraints, required=false),
		@XmlElementRef(name='sqlFile', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=SqlFile, required=false),
		@XmlElementRef(name='renameTable', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=RenameTable, required=false),
		@XmlElementRef(name='dropTable', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropTable, required=false),
		@XmlElementRef(name='dropIndex', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropIndex, required=false),
		@XmlElementRef(name='insert', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Insert, required=false),
		@XmlElementRef(name='alterSequence', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AlterSequence, required=false),
		@XmlElementRef(name='addNotNullConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddNotNullConstraint, required=false),
		@XmlElementRef(name='dropNotNullConstraint', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=DropNotNullConstraint, required=false),
		@XmlElementRef(name='loadUpdateData', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=LoadUpdateData, required=false),
		@XmlElementRef(name='addColumn', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=AddColumn, required=false)
	])
	@XmlMixed
	@XmlAnyElement(lax=true)
	List<Object> content = []

	@XmlAttribute(name='changeSetPath')
	String changeSetPath

	@XmlAttribute(name='changeSetAuthor')
	String changeSetAuthor

	@XmlAttribute(name='changeSetId')
	String changeSetId
}
