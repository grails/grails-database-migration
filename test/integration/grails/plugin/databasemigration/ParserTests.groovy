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

import org.springframework.beans.PropertyAccessorFactory

import liquibase.changelog.ChangeLogParameters
import liquibase.resource.FileSystemResourceAccessor
import liquibase.resource.ResourceAccessor
import liquibase.parser.core.xml.XMLChangeLogSAXParser

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class ParserTests extends GroovyTestCase {

	private static final List<Class> SIMPLE_CLASSES = [Class, Date, String, Boolean]

	private static final List<String> IGNORE_NAMES = [
		'class', 'cachedClass', 'metaClass',
		'changeSet', // backref
		'filePath', 'physicalFilePath', 'resourceAccessor'] // won't match, different source names

	@Override
	protected void tearDown() {
		super.tearDown()
		new File('target/includestest').deleteDir()
	}

	/**
	 * Parses an XML changelist and converts it to the Groovy DSL and parses it,
	 * and compares both field by field.
	 */
	void testParse() {

		String filename = 'test.changelist.xml'

		def xmlChangeLog = new XMLChangeLogSAXParser().parse(filename,
			new ChangeLogParameters(), new ParserTestResourceAccessor())

		// build the groovy version of the XML and create a ChangeLog from that
		def groovyChangeLog = new GrailsChangeLogParser().parse(filename + '.groovy',
			new ChangeLogParameters(), new ParserTestGroovyResourceAccessor(filename))

		checkLists xmlChangeLog.preconditions.nestedPreconditions,
					groovyChangeLog.preconditions.nestedPreconditions, 'Precondition'

		checkLists xmlChangeLog.changeSets, groovyChangeLog.changeSets, 'Changeset'
	}

	void testParseWithIncludes() {

		// test.including.changelist.groovy
		//    sub1/sub1.changelist.xml
		//    sub1/sub2.changelist.groovy
		//       sub2/sub3.changelist.xml
		//       sub_all/sub4.changelist.groovy
		//       sub_all/sub5.changelist.xml
		copyFile 'test.including.changelist.g', '', 'test.including.changelist.groovy'
		copyFile 'sub1.changelist.xml', 'sub1'
		copyFile 'sub2.changelist.g', 'sub1', 'sub2.changelist.groovy'
		copyFile 'sub3.changelist.xml', 'sub1/sub2'
		copyFile 'sub4.changelist.g', 'sub1/sub_all', 'sub4.changelist.groovy'
		copyFile 'sub5.changelist.xml', 'sub1/sub_all'

		def resourceAccessor = new FileSystemResourceAccessor('target/includestest')

		def changeLog = new GrailsChangeLogParser().parse('test.including.changelist.groovy',
			new ChangeLogParameters(), resourceAccessor)

		assertEquals 6, changeLog.changeSets.size()

		assertEquals 't1', changeLog.changeSets[0].id
		assertEquals 'burt', changeLog.changeSets[0].author
		assertEquals 'test.including.changelist.groovy', changeLog.changeSets[0].filePath

		assertEquals 't2', changeLog.changeSets[1].id
		assertEquals 'burt', changeLog.changeSets[1].author
		assertEquals 'sub1/sub1.changelist.xml', changeLog.changeSets[1].filePath

		assertEquals 't3', changeLog.changeSets[2].id
		assertEquals 'not_burt', changeLog.changeSets[2].author
		assertEquals 'sub1/sub2.changelist.groovy', changeLog.changeSets[2].filePath

		assertEquals 't4', changeLog.changeSets[3].id
		assertEquals 'burt', changeLog.changeSets[3].author
		assertEquals 'sub1/sub2/sub3.changelist.xml', changeLog.changeSets[3].filePath

		assertEquals 't5', changeLog.changeSets[4].id
		assertEquals 'burt', changeLog.changeSets[4].author
		assertEquals 'sub1/sub_all/sub4.changelist.groovy', changeLog.changeSets[4].filePath

		assertEquals 't6', changeLog.changeSets[5].id
		assertEquals 'burt', changeLog.changeSets[5].author
		assertEquals 'sub1/sub_all/sub5.changelist.xml', changeLog.changeSets[5].filePath
	}

	private void copyFile(String filename, String relativeDir, String rename = filename) {
		def file = new File("target/includestest/$relativeDir", rename)
		file.parentFile.mkdirs()
		file.withWriter { it.write getClass().getResourceAsStream(filename).text }
	}

	void testParseWithProperties() {
		// TODO
		fail 'implement me'
	}

	private void checkLists(List xmlList, List groovyList, String type) {
		assertEquals "Should have the same number of $type", xmlList.size(), groovyList.size()

		xmlList.size().times { i -> checkEqual(xmlList[i], groovyList[i], i, type) }
	}

	private void checkEqual(xmlObject, groovyObject, int order, String type) {
		if (xmlObject == null && groovyObject == null) {
			return
		}

		assertNotNull "Groovy object $groovyObject isn't null but XML is", xmlObject
		assertNotNull "XML object $xmlObject isn't null but Groovy is", groovyObject

		assertEquals "$type should have the same class", xmlObject.getClass(), groovyObject.getClass()

		def xmlWrapper = PropertyAccessorFactory.forBeanPropertyAccess(xmlObject)
		def xmlDescriptors = xmlWrapper.propertyDescriptors

		def groovyWrapper = PropertyAccessorFactory.forBeanPropertyAccess(groovyObject)
		def groovyDescriptors = groovyWrapper.propertyDescriptors

		assertEquals "$type $order should have the same number of properties",
				xmlDescriptors.length, groovyDescriptors.length

		xmlDescriptors.eachWithIndex { descriptor, int i ->
			String name = descriptor.name
			if (IGNORE_NAMES.contains(name)) return

			def readMethod = descriptor.readMethod
			if (!readMethod || readMethod.parameterTypes.length) return

			def xmlValue = readMethod.invoke(xmlObject)
			def groovyValue = groovyDescriptors[i].readMethod.invoke(groovyObject)

			if ('rollBackChanges'.equals(name) || 'changes'.equals(name)) {
				checkLists xmlValue as List, groovyValue as List, 'Change'
			}
			else if ('sqlVisitors'.equals(name)) {
				checkLists xmlValue, groovyValue, 'SqlVisitor'
			}
			else if ('columns'.equals(name)) {
				checkLists xmlValue, groovyValue, 'ColumnConfig'
			}
			else if (isObject(descriptor.propertyType)) {
				checkEqual xmlValue, groovyValue, 0, descriptor.propertyType.simpleName
			}
			else {
				assertEquals "$type $order property '$name' values should be the same",
						xmlValue, groovyValue
			}
		}
	}

	// limited to the types that are used in Liquibase
	private boolean isObject(Class<?> type) {
		!type.isPrimitive() && !Number.isAssignableFrom(type) && !(type in SIMPLE_CLASSES)
	}
}

class ParserTestResourceAccessor implements ResourceAccessor {
	InputStream getResourceAsStream(String file) {
		getClass().getResourceAsStream(file)
	}

	Enumeration<URL> getResources(String packageName) { null }

	ClassLoader toClassLoader() { getClass().classLoader }
}

class ParserTestGroovyResourceAccessor extends ParserTestResourceAccessor {

	String filename

	ParserTestGroovyResourceAccessor(String filename) {
		this.filename = filename
	}

	InputStream getResourceAsStream(String file) {

		String groovyChangeLogText = ChangelogXml2Groovy.convert(
			super.getResourceAsStream(filename).text)

		new ByteArrayInputStream(groovyChangeLogText.bytes)
	}
}
