/* Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.databasemigration

import java.sql.Connection

import groovy.sql.Sql

import liquibase.change.AbstractChange
import liquibase.change.ChangeMetaData
import liquibase.change.ChangeProperty
import liquibase.change.CheckSum
import liquibase.change.custom.CustomSqlRollback
import liquibase.change.custom.CustomTaskRollback
import liquibase.database.Database
import liquibase.database.DatabaseConnection;
import liquibase.exception.CustomChangeException
import liquibase.exception.RollbackImpossibleException
import liquibase.exception.SetupException;
import liquibase.exception.UnsupportedChangeException
import liquibase.exception.ValidationErrors
import liquibase.exception.Warnings
import liquibase.statement.SqlStatement

/**
 * Custom Groovy-based change.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class GrailsChange extends AbstractChange {

	@ChangeProperty(includeInSerialization = false)
	private boolean validateClosureCalled

	@ChangeProperty(includeInSerialization = false)
	private ValidationErrors validationErrors = new ValidationErrors()

	@ChangeProperty(includeInSerialization = false)
	private Warnings warnings = new Warnings()

	@ChangeProperty(includeInSerialization = false)
	private Database database

	@ChangeProperty(includeInSerialization = false)
	private List<SqlStatement> statements = []

	@ChangeProperty(includeInSerialization = false)
	private Sql sql

	@ChangeProperty(includeInSerialization = false)
	Closure initClosure

	@ChangeProperty(includeInSerialization = false)
	Closure validateClosure

	@ChangeProperty(includeInSerialization = false)
	Closure changeClosure

	@ChangeProperty(includeInSerialization = false)
	Closure rollbackClosure

	/**
	 * @see liquibase.change.Change#getConfirmationMessage()
	 */
	@ChangeProperty(includeInSerialization = false)
	String confirmationMessage = 'Executed GrailsChange'

	@ChangeProperty(includeInSerialization = false)
	String checksumString

	/**
	 * Constructor.
	 */
	GrailsChange() {
		super('grailsChange', 'Grails Change', ChangeMetaData.PRIORITY_DEFAULT)
	}

	@Override
	void init() throws SetupException {
		if (!initClosure) {
			return
		}

		initClosure.delegate = this
		try {
			initClosure()
		}
		catch (e) {
			throw new SetupException(e)
		}
	}

	@Override
	Warnings warn(Database database) {
		this.database = database
		callValidateClosure()
		warnings
	}

	@Override
	ValidationErrors validate(Database database) {
		this.database = database
		callValidateClosure()
		validationErrors
	}

	/**
	 * Called by the validate closure.
	 *
	 * @param message the error message
	 */
	void error(String message) {
		validationErrors.addError message
	}

	/**
	 * Called by the validate closure.
	 *
	 * @param warning the warning message
	 */
	void warn(String warning) {
		warnings.addWarning warning
	}

	/**
	 * {@inheritDoc}
	 * @see liquibase.change.Change#generateStatements(liquibase.database.Database)
	 */
	@Override
	SqlStatement[] generateStatements(Database database) {
		this.database = database

		if (changeClosure) {
			changeClosure.delegate = this
			changeClosure()
		}

		statements as SqlStatement[]
	}

	@Override
	SqlStatement[] generateRollbackStatements(Database database) {
		this.database = database

		if (rollbackClosure) {
			rollbackClosure.delegate = this
			rollbackClosure()
		}

		statements as SqlStatement[]
	}

	/**
	 * Called by the change or rollback closure.
	 *
	 * @param statement the statement
	 */
	void sqlStatement(SqlStatement statement) {
		if (statement) statements << statement
	}

	/**
	 * Called by the change or rollback closure.
	 *
	 * @param statement the statement
	 */
	void sqlStatements(statements) {
		if (statements) statements.addAll statements as List
	}

	/**
	 * Called by the change or rollback closure.
	 *
	 * @param message the confirmation message
	 */
	void confirm(String message) { confirmationMessage = message }

	@Override
	boolean supportsRollback(Database database) { true }

	@Override
	CheckSum generateCheckSum() {
		checksumString ? CheckSum.compute(checksumString) : super.generateCheckSum()
	}

	// called from change or rollback closure
	Sql getSql() {
		if (!connection) return null

		if (!sql) {
			sql = new Sql(connection) {
				protected void closeResources(Connection c) {
					// do nothing, let Liquibase close the connection
				}
			}
		}

		sql
	}

	// called from change or rollback closure
	DatabaseConnection getDatabaseConnection() { database?.connection }

	// called from change or rollback closure
	Connection getConnection() { database?.connection?.wrappedConnection }

	// warn is called then validate, but both are handled by
	// the 'warnings' closure, so we only want to run it once
	private void callValidateClosure() {
		if (!validateClosure || validateClosureCalled) {
			return
		}

		validateClosure.delegate = this
		validateClosure()
		validationErrors
	}
}
