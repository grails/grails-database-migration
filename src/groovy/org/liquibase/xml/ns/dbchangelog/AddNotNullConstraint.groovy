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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}addNotNullConstraintAttrib"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='addNotNullConstraint')
class AddNotNullConstraint {

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName

	@XmlAttribute(name='columnName', required=true)
	String columnName

	@XmlAttribute(name='defaultNullValue')
	String defaultNullValue

	@XmlAttribute(name='columnDataType')
	String columnDataType
}
