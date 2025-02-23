/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.mode.metadata.persist.node;

import org.apache.shardingsphere.infra.database.DefaultSchema;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DatabaseMetaDataNodeTest {
    
    @Test
    public void assertGetRulePath() {
        assertThat(DatabaseMetaDataNode.getRulePath(DefaultSchema.LOGIC_NAME, "0"), is("/metadata/logic_db/versions/0/rules"));
    }
    
    @Test
    public void assertGetDatabaseName() {
        Optional<String> actualSchemaName = DatabaseMetaDataNode.getDatabaseName("/metadata/logic_db/logic_schema/rules");
        assertTrue(actualSchemaName.isPresent());
        assertThat(actualSchemaName.get(), is("logic_db"));
    }
    
    @Test
    public void assertGetDatabaseNameWithLine() {
        Optional<String> actualSchemaName = DatabaseMetaDataNode.getDatabaseName("/metadata/logic-db-test/logic-db-schema/rules");
        assertTrue(actualSchemaName.isPresent());
        assertThat(actualSchemaName.get(), is("logic-db-test"));
    }
    
    @Test
    public void assertGetDatabaseNamePath() {
        assertThat(DatabaseMetaDataNode.getDatabaseNamePath("sharding_db"), is("/metadata/sharding_db"));
    }
    
    @Test
    public void assertGetMetaDataTablesPath() {
        assertThat(DatabaseMetaDataNode.getMetaDataTablesPath("sharding_db", "sharding_schema"), is("/metadata/sharding_db/schemas/sharding_schema/tables"));
    }
    
    @Test
    public void assertGetDatabaseNameByDatabasePath() {
        Optional<String> actualSchemaName = DatabaseMetaDataNode.getDatabaseNameByDatabasePath("/metadata/logic_db");
        assertTrue(actualSchemaName.isPresent());
        assertThat(actualSchemaName.get(), is("logic_db"));
    }
    
    @Test
    public void assertGetTableName() {
        Optional<String> actualTableName = DatabaseMetaDataNode.getTableName("/metadata/logic_db/schemas/logic_schema/tables/t_order");
        assertTrue(actualTableName.isPresent());
        assertThat(actualTableName.get(), is("t_order"));
    }
    
    @Test
    public void assertGetVersionBySchemaPath() {
        Optional<String> actualVersion = DatabaseMetaDataNode.getVersionByDataSourcesPath("/metadata/logic_db/versions/0/dataSources");
        assertTrue(actualVersion.isPresent());
        assertThat(actualVersion.get(), is("0"));
    }
    
    @Test
    public void assertGetActiveVersionPath() {
        assertThat(DatabaseMetaDataNode.getActiveVersionPath("logic_db"), is("/metadata/logic_db/active_version"));
    }
    
    @Test
    public void assertGetVersionByRulesPath() {
        Optional<String> actualVersion = DatabaseMetaDataNode.getVersionByRulesPath("/metadata/logic_db/versions/0/rules");
        assertTrue(actualVersion.isPresent());
        assertThat(actualVersion.get(), is("0"));
    }
    
    @Test
    public void assertGetDatabaseVersionPath() {
        assertThat(DatabaseMetaDataNode.getDatabaseVersionPath("logic_db", "0"), is("/metadata/logic_db/versions/0"));
    }
    
    @Test
    public void assertGetTableMetaDataPath() {
        assertThat(DatabaseMetaDataNode.getTableMetaDataPath("logic_db", "logic_schema", "order"), is("/metadata/logic_db/schemas/logic_schema/tables/order"));
    }
    
    @Test
    public void assertGetMetaDataNodePath() {
        assertThat(DatabaseMetaDataNode.getMetaDataNodePath(), is("/metadata"));
    }
    
    @Test
    public void assertGetMetaDataDataSourcePath() {
        assertThat(DatabaseMetaDataNode.getMetaDataDataSourcePath(DefaultSchema.LOGIC_NAME, "0"), is("/metadata/logic_db/versions/0/dataSources"));
    }
}
