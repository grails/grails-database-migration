package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.XmlValue

/**
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="schemaName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="viewName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="replaceIfExists" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['value'])
@XmlRootElement(name='createView')
class CreateView {

	@XmlValue
	String value

	@XmlAttribute(name='schemaName')
	String schemaName

	@XmlAttribute(name='viewName', required=true)
	String viewName

	@XmlAttribute(name='replaceIfExists')
	String replaceIfExists
}
