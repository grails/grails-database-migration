package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
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
 *         &lt;element ref="{http://www.liquibase.org/xml/ns/dbchangelog}comment" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='', propOrder=['content'])
@XmlRootElement(name='createProcedure')
class CreateProcedure {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link String }
	 * {@link JAXBElement }{@code <}{@link String }{@code >}
	 */
	@XmlElementRef(name='comment', namespace='http://www.liquibase.org/xml/ns/dbchangelog', type=JAXBElement, required=false)
	@XmlMixed
	List<Serializable> content = []
}
