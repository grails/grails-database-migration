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
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="stripComments" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *       &lt;attribute name="splitStatements" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *       &lt;attribute name="encoding" type="{http://www.w3.org/2001/XMLSchema}string" default="UTF-8" />
 *       &lt;attribute name="endDelimiter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="relativeToChangelogFile" type="{http://www.liquibase.org/xml/ns/dbchangelog}booleanExp" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name='')
@XmlRootElement(name='sqlFile')
class SqlFile {

	@XmlAttribute(name='path', required=true)
	String path

	@XmlAttribute(name='stripComments')
	String stripComments

	@XmlAttribute(name='splitStatements')
	String splitStatements

	@XmlAttribute(name='encoding')
	String encoding = 'UTF-8'

	@XmlAttribute(name='endDelimiter')
	String endDelimiter

	@XmlAttribute(name='relativeToChangelogFile')
	String relativeToChangelogFile
}
