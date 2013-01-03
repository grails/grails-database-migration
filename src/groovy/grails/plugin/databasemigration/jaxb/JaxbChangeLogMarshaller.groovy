/* Copyright 2010-2013 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package grails.plugin.databasemigration.jaxb

import grails.plugin.databasemigration.ChangelogXml2Groovy

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

import org.liquibase.xml.ns.dbchangelog.DatabaseChangeLog

/**
 * Based on http://www.liquibase.org/generate-changelog-with-jaxb-and-groovy
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class JaxbChangeLogMarshaller {

	static String changelogToXml(DatabaseChangeLog changeLog) {

		Marshaller marshaller = JAXBContext.newInstance('org.liquibase.xml.ns.dbchangelog').createMarshaller()
		marshaller.setProperty 'jaxb.formatted.output', true

		def sw = new StringWriter()
		marshaller.marshal changeLog, sw
		sw.toString()
	}

	static String changelogToGroovy(DatabaseChangeLog changeLog) {
		ChangelogXml2Groovy.convert changelogToXml(changeLog)
	}
}
