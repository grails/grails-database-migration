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

import groovy.transform.CompileStatic
import liquibase.changelog.ChangeSet
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
}
