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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}constraintsAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='constraints')
class Constraints {

	@XmlAttribute(name='nullable')
	String nullable

	@XmlAttribute(name='primaryKey')
	String primaryKey

	@XmlAttribute(name='primaryKeyName')
	String primaryKeyName

	@XmlAttribute(name='primaryKeyTablespace')
	String primaryKeyTablespace

	@XmlAttribute(name='unique')
	String unique

	@XmlAttribute(name='uniqueConstraintName')
	String uniqueConstraintName

	@XmlAttribute(name='references')
	String references

	@XmlAttribute(name='foreignKeyName')
	String foreignKeyName

	@XmlAttribute(name='deleteCascade')
	String deleteCascade

	@XmlAttribute(name='deferrable')
	String deferrable

	@XmlAttribute(name='initiallyDeferred')
	String initiallyDeferred
}
