package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

/**
 * <p>Class for onChangeSetPreconditionErrorOrFail.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="onChangeSetPreconditionErrorOrFail">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HALT"/>
 *     &lt;enumeration value="WARN"/>
 *     &lt;enumeration value="CONTINUE"/>
 *     &lt;enumeration value="MARK_RAN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name='onChangeSetPreconditionErrorOrFail')
@XmlEnum
enum OnChangeSetPreconditionErrorOrFail {

	HALT,
	WARN,
	CONTINUE,
	MARK_RAN

	String value() { name() }

	static OnChangeSetPreconditionErrorOrFail fromValue(String v) {
		valueOf(v)
	}
}
