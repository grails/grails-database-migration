package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAnyElement
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementRef
import javax.xml.bind.annotation.XmlElementRefs
import javax.xml.bind.annotation.XmlElements
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
 *       &lt;sequence>
 *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="dbms" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="preConditions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="onFailMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="onErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="onFail" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionErrorOrFail" />
 *                 &lt;attribute name="onError" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionErrorOrFail" />
 *                 &lt;attribute name="onSqlOutput" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionOnSqlOutput" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="changeSet" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="validCheckSum" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}comment" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="preConditions" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;choice>
 *                               &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
 *                             &lt;/choice>
 *                             &lt;attribute name="onFailMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                             &lt;attribute name="onErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                             &lt;attribute name="onFail" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
 *                             &lt;attribute name="onError" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
 *                             &lt;attribute name="onSqlOutput" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionOnSqlOutput" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;choice>
 *                       &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}tagDatabase"/>
 *                       &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeSetChildren" maxOccurs="unbounded" minOccurs="0"/>
 *                     &lt;/choice>
 *                     &lt;element name="modifySql" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;choice>
 *                               &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}modifySqlChildren" maxOccurs="unbounded"/>
 *                             &lt;/choice>
 *                             &lt;attribute name="dbms" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                             &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                             &lt;attribute name="applyToRollback" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                   &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeSetAttributes"/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="include" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="file" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="relativeToChangelogFile" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="includeAll" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="relativeToChangelogFile" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeLogAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['property', 'preConditions', 'changeSetOrIncludeOrIncludeAll'])
@XmlRootElement(name='databaseChangeLog')
class DatabaseChangeLog {

	List<DatabaseChangeLog.Property> property = []

	DatabaseChangeLog.PreConditions preConditions

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link DatabaseChangeLog.ChangeSet }
	 * {@link DatabaseChangeLog.Include }
	 * {@link DatabaseChangeLog.IncludeAll }
	 */
	@XmlElements([
		@XmlElement(name='changeSet', type=DatabaseChangeLog.ChangeSet),
		@XmlElement(name='include', type=DatabaseChangeLog.Include),
		@XmlElement(name='includeAll', type=DatabaseChangeLog.IncludeAll)
	])
	List<Object> changeSetOrIncludeOrIncludeAll = []

	@XmlAttribute(name='logicalFilePath')
	String logicalFilePath

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *    	 &lt;element name="validCheckSum" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}comment" minOccurs="0"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *         &lt;element name="preConditions" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;choice>
	 *                   &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
	 *                 &lt;/choice>
	 *                 &lt;attribute name="onFailMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="onErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="onFail" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
	 *                 &lt;attribute name="onError" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
	 *                 &lt;attribute name="onSqlOutput" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionOnSqlOutput" />
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *         &lt;choice>
	 *           &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}tagDatabase"/>
	 *           &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeSetChildren" maxOccurs="unbounded" minOccurs="0"/>
	 *         &lt;/choice>
	 *         &lt;element name="modifySql" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;choice>
	 *                   &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}modifySqlChildren" maxOccurs="unbounded"/>
	 *                 &lt;/choice>
	 *                 &lt;attribute name="dbms" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="applyToRollback" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}changeSetAttributes"/>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='', propOrder=['validCheckSum', 'preConditions', 'tagDatabase', 'changeSetChildren', 'modifySql'])
	static class ChangeSet {

		List<DatabaseChangeLog.ChangeSet.ValidCheckSum> validCheckSum = []

		DatabaseChangeLog.ChangeSet.PreConditions preConditions

		TagDatabase tagDatabase

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
		@XmlAnyElement(lax=true)
		List<Object> changeSetChildren = []

		List<DatabaseChangeLog.ChangeSet.ModifySql> modifySql = []

		@XmlAttribute(name='id', required=true)
		String id

		@XmlAttribute(name='author', required=true)
		String author

		@XmlAttribute(name='context')
		String context

		@XmlAttribute(name='dbms')
		String dbms

		@XmlAttribute(name='runOnChange')
		String runOnChange

		@XmlAttribute(name='runAlways')
		String runAlways

		@XmlAttribute(name='failOnError')
		String failOnError

		@XmlAttribute(name='onValidationFail')
		OnChangeSetValidationFail onValidationFail

		@XmlAttribute(name='runInTransaction')
		String runInTransaction = 'true'

		@XmlAttribute(name='logicalFilePath')
		String logicalFilePath

		/**
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 *
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;choice>
		 *         &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}modifySqlChildren" maxOccurs="unbounded"/>
		 *       &lt;/choice>
		 *       &lt;attribute name="dbms" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *       &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *       &lt;attribute name="applyToRollback" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name='', propOrder=['modifySqlChildren'])
		static class ModifySql {

			/**
			 * Objects of the following type(s) are allowed in the list
			 * {@link DatabaseChangeLog.ChangeSet.ModifySql.Replace }
			 * {@link DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace }
			 * {@link DatabaseChangeLog.ChangeSet.ModifySql.Prepend }
			 * {@link DatabaseChangeLog.ChangeSet.ModifySql.Append }
			 *
			 */
			@XmlElements([
				@XmlElement(name='replace', type=DatabaseChangeLog.ChangeSet.ModifySql.Replace),
				@XmlElement(name='regExpReplace', type=DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace),
				@XmlElement(name='prepend', type=DatabaseChangeLog.ChangeSet.ModifySql.Prepend),
				@XmlElement(name='append', type=DatabaseChangeLog.ChangeSet.ModifySql.Append)
			])
			List<Object> modifySqlChildren = []

			@XmlAttribute(name='dbms')
			String dbms

			@XmlAttribute(name='context')
			String context

			@XmlAttribute(name='applyToRollback')
			String applyToRollback

			/**
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 *
			 * <pre>
			 *  &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name='')
			static class Append {

				@XmlAttribute(name='value', required=true)
				String value
			}

			/**
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 *
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name='')
			static class Prepend {

				@XmlAttribute(name='value', required=true)
				String value
			}

			/**
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 *
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;attribute name="replace" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *       &lt;attribute name="with" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name='')
			static class RegExpReplace {

				@XmlAttribute(name='replace', required=true)
				String replace

				@XmlAttribute(name='with', required=true)
				String with
			}

			/**
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 *
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;attribute name="replace" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *       &lt;attribute name="with" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name='')
			static class Replace {

				@XmlAttribute(name='replace', required=true)
				String replace

				@XmlAttribute(name='with', required=true)
				String with
			}
		}

		/**
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 *
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;choice>
		 *         &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
		 *       &lt;/choice>
		 *       &lt;attribute name="onFailMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *       &lt;attribute name="onErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *       &lt;attribute name="onFail" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
		 *       &lt;attribute name="onError" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeSetPreconditionErrorOrFail" />
		 *       &lt;attribute name="onSqlOutput" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionOnSqlOutput" />
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name='', propOrder=['preConditionChildren'])
		static class PreConditions {

			/**
			 * Objects of the following type(s) are allowed in the list
			 * {@link And }
			 * {@link Or }
			 * {@link Not }
			 * {@link Dbms }
			 * {@link RunningAs }
			 * {@link ChangeSetExecuted }
			 * {@link TableExists }
			 * {@link ColumnExists }
			 * {@link SequenceExists }
			 * {@link ForeignKeyConstraintExists }
			 * {@link IndexExists }
			 * {@link PrimaryKeyExists }
			 * {@link ViewExists }
			 * {@link SqlCheck }
			 * {@link ChangeLogPropertyDefined }
			 * {@link CustomPrecondition }
			 */
			@XmlElements([
				@XmlElement(name='and', type=And),
				@XmlElement(name='or', type=Or),
				@XmlElement(name='not', type=Not),
				@XmlElement(name='dbms', type=Dbms),
				@XmlElement(name='runningAs', type=RunningAs),
				@XmlElement(name='changeSetExecuted', type=ChangeSetExecuted),
				@XmlElement(name='tableExists', type=TableExists),
				@XmlElement(name='columnExists', type=ColumnExists),
				@XmlElement(name='sequenceExists', type=SequenceExists),
				@XmlElement(name='foreignKeyConstraintExists', type=ForeignKeyConstraintExists),
				@XmlElement(name='indexExists', type=IndexExists),
				@XmlElement(name='primaryKeyExists', type=PrimaryKeyExists),
				@XmlElement(name='viewExists', type=ViewExists),
				@XmlElement(name='sqlCheck', type=SqlCheck),
				@XmlElement(name='changeLogPropertyDefined', type=ChangeLogPropertyDefined),
				@XmlElement(name='customPrecondition', type=CustomPrecondition)
			])
			List<Object> preConditionChildren = []

			@XmlAttribute(name='onFailMessage')
			String onFailMessage

			@XmlAttribute(name='onErrorMessage')
			String onErrorMessage

			@XmlAttribute(name='onFail')
			OnChangeSetPreconditionErrorOrFail onFail

			@XmlAttribute(name='onError')
			OnChangeSetPreconditionErrorOrFail onError

			@XmlAttribute(name='onSqlOutput')
			OnChangeLogPreconditionOnSqlOutput onSqlOutput
		}

		/**
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 *
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}comment" minOccurs="0"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name='', propOrder=['content'])
		static class ValidCheckSum {

			/**
			 * Objects of the following type(s) are allowed in the list
			 * {@link String }
			 * {@link JAXBElement }{@code <}{@link String }{@code >}
			 */
			@XmlElementRef(name='comment', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=JAXBElement, required=false)
			@XmlMixed
			List<Serializable> content = []
		}
	}

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="file" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="relativeToChangelogFile" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='')
	static class Include {

		@XmlAttribute(name='file', required=true)
		String file

		@XmlAttribute(name='relativeToChangelogFile')
		String relativeToChangelogFile
	}

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="relativeToChangelogFile" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='')
	static class IncludeAll {

		@XmlAttribute(name='path', required=true)
		String path

		@XmlAttribute(name='relativeToChangelogFile')
		String relativeToChangelogFile
	}

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;choice>
	 *         &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
	 *       &lt;/choice>
	 *       &lt;attribute name="onFailMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="onErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="onFail" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionErrorOrFail" />
	 *       &lt;attribute name="onError" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionErrorOrFail" />
	 *       &lt;attribute name="onSqlOutput" type="{http://www.liquibase.org/xml/ns/dbchangelog}onChangeLogPreconditionOnSqlOutput" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='', propOrder=['preConditionChildren'])
	static class PreConditions {

		/**
		 * Objects of the following type(s) are allowed in the list
		 * {@link And }
		 * {@link Or }
		 * {@link Not }
		 * {@link Dbms }
		 * {@link RunningAs }
		 * {@link ChangeSetExecuted }
		 * {@link TableExists }
		 * {@link ColumnExists }
		 * {@link SequenceExists }
		 * {@link ForeignKeyConstraintExists }
		 * {@link IndexExists }
		 * {@link PrimaryKeyExists }
		 * {@link ViewExists }
		 * {@link SqlCheck }
		 * {@link ChangeLogPropertyDefined }
		 * {@link CustomPrecondition }
		 */
		@XmlElements([
			@XmlElement(name='and', type=And),
			@XmlElement(name='or', type=Or),
			@XmlElement(name='not', type=Not),
			@XmlElement(name='dbms', type=Dbms),
			@XmlElement(name='runningAs', type=RunningAs),
			@XmlElement(name='changeSetExecuted', type=ChangeSetExecuted),
			@XmlElement(name='tableExists', type=TableExists),
			@XmlElement(name='columnExists', type=ColumnExists),
			@XmlElement(name='sequenceExists', type=SequenceExists),
			@XmlElement(name='foreignKeyConstraintExists', type=ForeignKeyConstraintExists),
			@XmlElement(name='indexExists', type=IndexExists),
			@XmlElement(name='primaryKeyExists', type=PrimaryKeyExists),
			@XmlElement(name='viewExists', type=ViewExists),
			@XmlElement(name='sqlCheck', type=SqlCheck),
			@XmlElement(name='changeLogPropertyDefined', type=ChangeLogPropertyDefined),
			@XmlElement(name='customPrecondition', type=CustomPrecondition)
		])
		List<Object> preConditionChildren = []

		@XmlAttribute(name='onFailMessage')
		String onFailMessage

		@XmlAttribute(name='onErrorMessage')
		String onErrorMessage

		@XmlAttribute(name='onFail')
		OnChangeLogPreconditionErrorOrFail onFail

		@XmlAttribute(name='onError')
		OnChangeLogPreconditionErrorOrFail onError

		@XmlAttribute(name='onSqlOutput')
		OnChangeLogPreconditionOnSqlOutput onSqlOutput
	}

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="dbms" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='')
	static class Property {

		@XmlAttribute(name='file')
		String file

		@XmlAttribute(name='name')
		String name

		@XmlAttribute(name='value')
		String value

		@XmlAttribute(name='dbms')
		String dbms

		@XmlAttribute(name='context')
		String context
	}
}
