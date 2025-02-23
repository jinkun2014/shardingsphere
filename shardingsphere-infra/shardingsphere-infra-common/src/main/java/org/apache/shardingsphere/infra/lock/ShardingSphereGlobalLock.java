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

package org.apache.shardingsphere.infra.lock;

/**
 * Global lock of ShardingSphere.
 */
public interface ShardingSphereGlobalLock extends ShardingSphereLock {
    
    /**
     * Ack locked.
     *
     * @param lockName lock name
     * @param instanceId instance id
     */
    void ackLock(String lockName, String instanceId);
    
    /**
     * Release ack lock.
     *
     * @param lockName lock name
     * @param instanceId instance id
     */
    void releaseAckLock(String lockName, String instanceId);
    
    /**
     * Add locked instance id.
     *
     * @param instanceId instance id
     */
    void addLockedInstance(String instanceId);
    
    /**
     * Remove locked instance id.
     *
     * @param instanceId instance id
     */
    void removeLockedInstance(String instanceId);
    
    /**
     * Release locked state.
     *
     * @param lockName lock name
     */
    void releaseLockedState(String lockName);
    
    /**
     * Refresh owner instance id.
     *
     * @param ownerInstanceId owner instance id
     */
    void refreshOwner(String ownerInstanceId);
}
