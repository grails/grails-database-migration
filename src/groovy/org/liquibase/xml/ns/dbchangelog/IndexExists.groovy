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
 *       &lt;attribute name="indexName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tableName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnNames" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='indexExists')
class IndexExists {

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='indexName')
	String indexName

	@XmlAttribute(name='tableName')
	String tableName

	@XmlAttribute(name='columnNames')
	String columnNames
}
