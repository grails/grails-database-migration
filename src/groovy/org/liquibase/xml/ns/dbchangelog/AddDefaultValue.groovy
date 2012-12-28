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
 *       &lt;attribute name="schemaName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tableName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnDataType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValueNumeric" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValueDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValueBoolean" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultValueComputed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='addDefaultValue')
class AddDefaultValue {

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName

	@XmlAttribute(name='columnName', required=true)
	String columnName

	@XmlAttribute(name='columnDataType')
	String columnDataType

	@XmlAttribute(name='defaultValue')
	String defaultValue

	@XmlAttribute(name='defaultValueNumeric')
	String defaultValueNumeric

	@XmlAttribute(name='defaultValueDate')
	String defaultValueDate

	@XmlAttribute(name='defaultValueBoolean')
	String defaultValueBoolean

	@XmlAttribute(name='defaultValueComputed')
	String defaultValueComputed
}
