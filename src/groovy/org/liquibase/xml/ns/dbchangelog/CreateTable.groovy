package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

/**
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}column" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}tableNameAttribute"/>
 *       &lt;attribute name="tablespace" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['column'])
@XmlRootElement(name='createTable')
class CreateTable {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link Column }
	 */
	@XmlElement(required=true)
	List<Column> column = []

	@XmlAttribute(name='tablespace')
	String tablespace

	@XmlAttribute(name='remarks')
	String remarks

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName
}
