package org.grails.plugins.databasemigration.liquibase

import groovy.transform.CompileStatic
import liquibase.ContextExpression
import liquibase.Labels
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.ChangeLogParseException
import liquibase.parser.core.ParsedNode
import liquibase.parser.core.yaml.YamlChangeLogParser
import liquibase.resource.ResourceAccessor
import liquibase.util.StreamUtil
import org.yaml.snakeyaml.Yaml

@CompileStatic
class GrailsYamlChangeLogParser extends YamlChangeLogParser {

    final int priority = 10

    @Override
    DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        Yaml yaml = new Yaml()

        try {
            InputStream changeLogStream = StreamUtil.singleInputStream(physicalChangeLogLocation, resourceAccessor)
            if (changeLogStream == null) {
                throw new ChangeLogParseException(physicalChangeLogLocation + " does not exist")
            }

            Map parsedYaml
            try {
                parsedYaml = yaml.loadAs(changeLogStream, Map.class)
            } catch (Exception e) {
                throw new ChangeLogParseException("Syntax error in " + getSupportedFileExtensions()[0] + ": " + e.getMessage(), e)
            }

            if (!parsedYaml.containsKey("databaseChangeLog")) {
                throw new ChangeLogParseException("Could not find databaseChangeLog node")
            }
            List rootList = (List) parsedYaml.get("databaseChangeLog") ?: []
            for (Object obj : rootList) {
                if (obj instanceof Map && ((Map) obj).containsKey("property")) {
                    Map property = (Map) ((Map) obj).get("property")
                    ContextExpression context = new ContextExpression((String) property.get("context"))
                    Labels labels = new Labels((String) property.get("labels"))
                    if (property.containsKey("name")) {
                        Object value = property.get("value")
                        if (value != null) {
                            value = value.toString()
                        }
                        changeLogParameters.set((String) property.get("name"), (String) value, context, labels, (String) property.get("dbms"))
                    } else if (property.containsKey("file")) {
                        Properties props = new Properties()
                        InputStream propertiesStream = StreamUtil.singleInputStream((String) property.get("file"), resourceAccessor)
                        if (propertiesStream == null) {
                            log.info("Could not open properties file " + property.get("file"))
                        } else {
                            props.load(propertiesStream)

                            for (Map.Entry entry : props.entrySet()) {
                                changeLogParameters.set(entry.getKey().toString(), entry.getValue().toString(), context, labels, (String) property.get("dbms"))
                            }
                        }
                    }
                }
            }

            replaceParameters(parsedYaml, changeLogParameters)

            DatabaseChangeLog changeLog = new DatabaseChangeLog(physicalChangeLogLocation)
            changeLog.setChangeLogParameters(changeLogParameters)
            ParsedNode databaseChangeLogNode = new ParsedNode(null, "databaseChangeLog")
            databaseChangeLogNode.setValue(rootList)

            changeLog.load(databaseChangeLogNode, resourceAccessor)

            return changeLog
        } catch (Throwable e) {
            if (e instanceof ChangeLogParseException) {
                throw (ChangeLogParseException) e
            }
            throw new ChangeLogParseException(e)
        }
    }
}
