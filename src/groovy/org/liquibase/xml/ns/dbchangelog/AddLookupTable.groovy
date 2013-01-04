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
 *       &lt;attribute name="existingTableSchemaName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="existingTableName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="existingColumnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="newTableSchemaName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="newTableName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="newColumnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="newColumnDataType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="constraintName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='addLookupTable')
class AddLookupTable {

	@XmlAttribute(name='existingTableSchemaName')
	String existingTableSchemaName

	@XmlAttribute(name='existingTableName', required=true)
	String existingTableName

	@XmlAttribute(name='existingColumnName', required=true)
	String existingColumnName

	@XmlAttribute(name='newTableSchemaName')
	String newTableSchemaName

	@XmlAttribute(name='newTableName', required=true)
	String newTableName

	@XmlAttribute(name='newColumnName', required=true)
	String newColumnName

	@XmlAttribute(name='newColumnDataType')
	String newColumnDataType

	@XmlAttribute(name='constraintName')
	String constraintName
}
