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

package org.apache.shardingsphere.infra.datasource.pool.destroyer.detector;

import org.apache.shardingsphere.spi.ShardingSphereServiceLoader;
import org.apache.shardingsphere.spi.type.required.RequiredSPIRegistry;
import org.apache.shardingsphere.spi.type.typed.TypedSPIRegistry;

/**
 * Data source pool active detector factory.
 */
public final class DataSourcePoolActiveDetectorFactory {
    
    static {
        ShardingSphereServiceLoader.register(DataSourcePoolActiveDetector.class);
    }
    
    /**
     * Create new instance of data source pool active detector.
     * 
     * @param dataSourceClassName data source class name
     * @return new instance of data source pool active detector
     */
    public static DataSourcePoolActiveDetector newInstance(final String dataSourceClassName) {
        return TypedSPIRegistry.findRegisteredService(DataSourcePoolActiveDetector.class, dataSourceClassName).orElse(RequiredSPIRegistry.getRegisteredService(DataSourcePoolActiveDetector.class));
    }
}
