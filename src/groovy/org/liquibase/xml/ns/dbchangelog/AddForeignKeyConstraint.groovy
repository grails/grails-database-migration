package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

/**
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}addForeignKeyConstraintAttrib"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='addForeignKeyConstraint')
class AddForeignKeyConstraint {

	@XmlAttribute(name='baseTableSchemaName')
	String baseTableSchemaName

	@XmlAttribute(name='baseTableName', required=true)
	String baseTableName

	@XmlAttribute(name='baseColumnNames', required=true)
	String baseColumnNames

	@XmlAttribute(name='constraintName', required=true)
	String constraintName

	@XmlAttribute(name='referencedTableSchemaName')
	String referencedTableSchemaName

	@XmlAttribute(name='referencedTableName', required=true)
	String referencedTableName

	@XmlAttribute(name='referencedColumnNames', required=true)
	String referencedColumnNames

	@XmlAttribute(name='deferrable')
	String deferrable

	@XmlAttribute(name='initiallyDeferred')
	String initiallyDeferred

	@XmlAttribute(name='deleteCascade')
	String deleteCascade

	@XmlAttribute(name='onDelete')
	FkCascadeActionOptions onDelete

	@XmlAttribute(name='onUpdate')
	FkCascadeActionOptions onUpdate

	@XmlAttribute(name='referencesUniqueColumn')
	String referencesUniqueColumn
}
