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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}sequenceAttributes"/>
 *       &lt;attribute name="cycle" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='createSequence')
class CreateSequence {

	@XmlAttribute(name='cycle')
	String cycle

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='sequenceName', required=true)
	String sequenceName

	@XmlAttribute(name='startValue')
	String startValue

	@XmlAttribute(name='incrementBy')
	String incrementBy

	@XmlAttribute(name='maxValue')
	String maxValue

	@XmlAttribute(name='minValue')
	String minValue

	@XmlAttribute(name='ordered')
	String ordered
}
