package org.liquibase.xml.ns.dbchangelog

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
 *         &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}constraints" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}column"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['content'])
@XmlRootElement(name='column')
class Column {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link String }
	 * {@link Constraints }
	 */
	@XmlElementRef(name='constraints', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=Constraints, required=false)
	@XmlMixed
	List<Object> content = []

	@XmlAttribute(name='name', required=true)
	String name

	@XmlAttribute(name='type')
	String type

	@XmlAttribute(name='value')
	String value

	@XmlAttribute(name='valueNumeric')
	String valueNumeric

	@XmlAttribute(name='valueBoolean')
	String valueBoolean

	@XmlAttribute(name='valueDate')
	String valueDate

	@XmlAttribute(name='valueComputed')
	String valueComputed

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

	@XmlAttribute(name='autoIncrement')
	String autoIncrement

	@XmlAttribute(name='startWith')
	Long startWith

	@XmlAttribute(name='incrementBy')
	Long incrementBy

	@XmlAttribute(name='remarks')
	String remarks
}
