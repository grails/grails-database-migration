package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElementRef
import javax.xml.bind.annotation.XmlElementRefs
import javax.xml.bind.annotation.XmlMixed
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
 *         &lt;element name="where" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}tableNameAttribute"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['content'])
@XmlRootElement(name='update')
class Update {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link Column }
	 * {@link String }
	 * {@link JAXBElement }{@code <}{@link Object }{@code >}
	 */
	@XmlElementRefs([
		@XmlElementRef(name='column', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Column),
		@XmlElementRef(name='where', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=JAXBElement)
	])
	@XmlMixed
	List<Object> content = []

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName
}
