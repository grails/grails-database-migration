package org.liquibase.xml.ns.dbchangelog

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElements
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

/**
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;group ref="{http://www.liquibase.org/xml/ns/dbchangelog}PreConditionChildren" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = '', propOrder = ['preConditionChildren'])
@XmlRootElement(name = 'or')
class Or {

	/**
	 * Objects of the following type(s) are allowed in the list
	 * {@link And }
	 * {@link Or }
	 * {@link Not }
	 * {@link Dbms }
	 * {@link RunningAs }
	 * {@link ChangeSetExecuted }
	 * {@link TableExists }
	 * {@link ColumnExists }
	 * {@link SequenceExists }
	 * {@link ForeignKeyConstraintExists }
	 * {@link IndexExists }
	 * {@link PrimaryKeyExists }
	 * {@link ViewExists }
	 * {@link SqlCheck }
	 * {@link ChangeLogPropertyDefined }
	 * {@link CustomPrecondition }
	 */
	@XmlElements([
		@XmlElement(name = 'and', type = And),
		@XmlElement(name = 'or', type = Or),
		@XmlElement(name = 'not', type = Not),
		@XmlElement(name = 'dbms', type = Dbms),
		@XmlElement(name = 'runningAs', type = RunningAs),
		@XmlElement(name = 'changeSetExecuted', type = ChangeSetExecuted),
		@XmlElement(name = 'tableExists', type = TableExists),
		@XmlElement(name = 'columnExists', type = ColumnExists),
		@XmlElement(name = 'sequenceExists', type = SequenceExists),
		@XmlElement(name = 'foreignKeyConstraintExists', type = ForeignKeyConstraintExists),
		@XmlElement(name = 'indexExists', type = IndexExists),
		@XmlElement(name = 'primaryKeyExists', type = PrimaryKeyExists),
		@XmlElement(name = 'viewExists', type = ViewExists),
		@XmlElement(name = 'sqlCheck', type = SqlCheck),
		@XmlElement(name = 'changeLogPropertyDefined', type = ChangeLogPropertyDefined),
		@XmlElement(name = 'customPrecondition', type = CustomPrecondition)
	])
	List<Object> preConditionChildren = []
}
