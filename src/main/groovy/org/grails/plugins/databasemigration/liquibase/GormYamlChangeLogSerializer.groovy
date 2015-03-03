/*
 * Copyright 2015 original authors
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
package org.grails.plugins.databasemigration.liquibase

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import liquibase.change.ConstraintsConfig
import liquibase.changelog.ChangeSet
import liquibase.serializer.LiquibaseSerializable
import liquibase.serializer.core.yaml.YamlChangeLogSerializer
import liquibase.util.StringUtils

@CompileStatic
class GormYamlChangeLogSerializer extends YamlChangeLogSerializer {

    @Override
    String[] getValidFileExtensions() {
        ['yaml', 'yml'] as String[]
    }

    @Override
    public void write(List<ChangeSet> changeSets, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))
        writer.write("databaseChangeLog:\n")
        for (ChangeSet changeSet : changeSets) {
            String serialized = serialize(changeSet, true)
            writer.write(StringUtils.indent(serialized, 2).replaceFirst(/^  /, /- /).replaceFirst(/\s*$/, ''))
            writer.write("\n")
        }
        writer.flush()
    }

    @CompileDynamic
    protected Map<String, Object> toMap(LiquibaseSerializable object) {
        Comparator<String> comparator;
        if (object instanceof ChangeSet) {
            comparator = new ChangeSetComparator();
        } else {
            comparator = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            };
        }
        Map<String, Object> objectMap = new TreeMap<String, Object>(comparator);

        for (String field : object.getSerializableFields()) {
            Object value = object.getSerializableFieldValue(field);
            if (value != null) {
                if (value instanceof ConstraintsConfig) {
                    value = toMap((LiquibaseSerializable) value)?.constraints ?: [:]
                }
                if (value instanceof LiquibaseSerializable) {
                    value = toMap((LiquibaseSerializable) value);
                }
                if (value instanceof Collection) {
                    List valueAsList = new ArrayList((Collection) value);
                    if (valueAsList.size() == 0) {
                        continue;
                    }
                    for (int i = 0; i < valueAsList.size(); i++) {
                        if (valueAsList.get(i) instanceof LiquibaseSerializable) {
                            valueAsList.set(i, toMap((LiquibaseSerializable) valueAsList.get(i)));
                        }
                    }
                    value = valueAsList;

                }
                objectMap.put(field, value);
            }
        }

        Map<String, Object> containerMap = new HashMap<String, Object>();
        containerMap.put(object.getSerializedObjectName(), objectMap);
        return containerMap;
    }

    private static class ChangeSetComparator implements Comparator<String> {
        private static final Map<String, Integer> order = new HashMap();

        private ChangeSetComparator() {
        }

        public int compare(String o1, String o2) {
            Integer o1Order = (Integer) order.get(o1);
            if (o1Order == null) {
                o1Order = Integer.valueOf(10);
            }

            Integer o2Order = (Integer) order.get(o2);
            if (o2Order == null) {
                o2Order = Integer.valueOf(10);
            }

            int orderCompare = o1Order.compareTo(o2Order);
            return orderCompare == 0 ? o1.compareTo(o2) : orderCompare;
        }

        static {
            order.put("id", Integer.valueOf(1));
            order.put("author", Integer.valueOf(2));
            order.put("changes", Integer.valueOf(2147483647));
        }
    }
}
