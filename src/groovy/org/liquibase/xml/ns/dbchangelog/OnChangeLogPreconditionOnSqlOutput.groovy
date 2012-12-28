package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

/**
 * <p>Class for onChangeLogPreconditionOnSqlOutput.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="onChangeLogPreconditionOnSqlOutput">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TEST"/>
 *     &lt;enumeration value="FAIL"/>
 *     &lt;enumeration value="IGNORE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name='onChangeLogPreconditionOnSqlOutput')
@XmlEnum
enum OnChangeLogPreconditionOnSqlOutput {

	TEST,
	FAIL,
	IGNORE

	String value() { name() }

	static OnChangeLogPreconditionOnSqlOutput fromValue(String v) {
		valueOf(v)
	}
}
