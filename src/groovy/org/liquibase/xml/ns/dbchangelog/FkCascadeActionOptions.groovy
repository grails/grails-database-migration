package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlEnumValue
import javax.xml.bind.annotation.XmlType

/**
 * <p>Class for fkCascadeActionOptions.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="fkCascadeActionOptions">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CASCADE"/>
 *     &lt;enumeration value="SET NULL"/>
 *     &lt;enumeration value="SET DEFAULT"/>
 *     &lt;enumeration value="RESTRICT"/>
 *     &lt;enumeration value="NO ACTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = 'fkCascadeActionOptions')
@XmlEnum
enum FkCascadeActionOptions {

	CASCADE('CASCADE'),

	@XmlEnumValue('SET NULL')
	SET_NULL('SET NULL'),

	@XmlEnumValue('SET DEFAULT')
	SET_DEFAULT('SET DEFAULT'),

	RESTRICT('RESTRICT'),
	@XmlEnumValue('NO ACTION')

	NO_ACTION('NO ACTION')

	final String value

	private FkCascadeActionOptions(String v) {
		value = v
	}

	static FkCascadeActionOptions fromValue(String v) {
		for (FkCascadeActionOptions c : values()) {
			if (c.value.equals(v)) {
				return c
			}
		}
		throw new IllegalArgumentException(v)
	}
}
