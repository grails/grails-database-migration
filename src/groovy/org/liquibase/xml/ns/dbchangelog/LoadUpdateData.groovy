package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElementRef
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
 *         &lt;element name="column" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="index" type="{http://www.liquibase.org/xml/ns/dbchangelog}integerExp" />
 *                 &lt;attribute name="header" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="defaultValueNumeric" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="defaultValueDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="defaultValueBoolean" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *                 &lt;attribute name="defaultValueComputed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="schemaName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tableName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}string" default="UTF-8" />
 *       &lt;attribute name="primaryKey" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="separator" type="{http://www.w3.org/2001/XMLSchema}string" default="," />
 *       &lt;attribute name="quotchar" type="{http://www.w3.org/2001/XMLSchema}string" default=""" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['content'])
@XmlRootElement(name='loadUpdateData')
class LoadUpdateData {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link String }
	 * {@link JAXBElement }{@code <}{@link LoadUpdateData.Column }{@code >}
	 */
	@XmlElementRef(name='column', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=JAXBElement, required=false)
	@XmlMixed
	List<Serializable> content = []

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='tableName', required=true)
	String tableName

	@XmlAttribute(name='file')
	String file

	@XmlAttribute(name='encoding')
	String encoding = 'UTF-8'

	@XmlAttribute(name='primaryKey', required=true)
	String primaryKey

	@XmlAttribute(name='separator')
	String separator = ','

	@XmlAttribute(name='quotchar')
	String quotchar = '"'

	/**
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="index" type="{http://www.liquibase.org/xml/ns/dbchangelog}integerExp" />
	 *       &lt;attribute name="header" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="defaultValueNumeric" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="defaultValueDate" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="defaultValueBoolean" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
	 *       &lt;attribute name="defaultValueComputed" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name='')
	static class Column {

		@XmlAttribute(name='index')
		String index

		@XmlAttribute(name='header')
		String header

		@XmlAttribute(name='name')
		String name

		@XmlAttribute(name='type')
		String type

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
}
