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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}indexName"/>
 *       &lt;attribute name="associatedWith" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='dropIndex')
class DropIndex {

	@XmlAttribute(name='associatedWith')
	String associatedWith

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName

	@XmlAttribute(name='indexName', required=true)
	String indexName
}
