package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

/**
 * <p>Class for onChangeLogPreconditionErrorOrFail.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="onChangeLogPreconditionErrorOrFail">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HALT"/>
 *     &lt;enumeration value="WARN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name='onChangeLogPreconditionErrorOrFail')
@XmlEnum
enum OnChangeLogPreconditionErrorOrFail {

	HALT,
	WARN

	String value() { name() }

	static OnChangeLogPreconditionErrorOrFail fromValue(String v) {
		valueOf(v)
	}
}
