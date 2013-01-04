package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

/**
 * <p>Class for onChangeSetValidationFail.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="onChangeSetValidationFail">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HALT"/>
 *     &lt;enumeration value="MARK_RAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name='onChangeSetValidationFail')
@XmlEnum
enum OnChangeSetValidationFail {

	HALT,
	MARK_RAN

	String value() { name() }

	static OnChangeSetValidationFail fromValue(String v) {
		valueOf(v)
	}
}
