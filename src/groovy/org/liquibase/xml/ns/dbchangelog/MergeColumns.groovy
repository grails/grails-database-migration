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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}tableNameAttribute"/>
 *       &lt;attribute name="column1Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="joinString" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column2Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="finalColumnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="finalColumnType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='mergeColumns')
class MergeColumns {

	@XmlAttribute(name='column1Name', required=true)
	String column1Name

	@XmlAttribute(name='joinString', required=true)
	String joinString

	@XmlAttribute(name='column2Name', required=true)
	String column2Name

	@XmlAttribute(name='finalColumnName', required=true)
	String finalColumnName

	@XmlAttribute(name='finalColumnType', required=true)
	String finalColumnType

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName
}
