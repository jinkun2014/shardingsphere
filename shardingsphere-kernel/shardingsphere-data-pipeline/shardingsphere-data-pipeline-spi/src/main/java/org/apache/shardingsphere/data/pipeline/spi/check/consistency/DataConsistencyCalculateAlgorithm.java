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

package org.apache.shardingsphere.data.pipeline.spi.check.consistency;

import org.apache.shardingsphere.data.pipeline.api.check.consistency.DataCalculateParameter;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithm;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmPostProcessor;
import org.apache.shardingsphere.spi.type.typed.TypedSPIMetadataAware;

/**
 * Data consistency calculate algorithm.
 */
public interface DataConsistencyCalculateAlgorithm extends ShardingSphereAlgorithm, TypedSPIMetadataAware, ShardingSphereAlgorithmPostProcessor {
    
    /**
     * Calculate data for consistency check.
     *
     * @param dataCalculateParameter data calculate parameter
     * @return calculated result
     */
    Iterable<Object> calculate(DataCalculateParameter dataCalculateParameter);
}
