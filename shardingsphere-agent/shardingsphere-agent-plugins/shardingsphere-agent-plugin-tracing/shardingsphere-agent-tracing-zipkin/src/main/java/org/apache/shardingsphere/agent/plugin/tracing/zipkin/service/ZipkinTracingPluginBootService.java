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

package org.apache.shardingsphere.agent.plugin.tracing.zipkin.service;

import brave.Tracing;
import brave.sampler.BoundarySampler;
import brave.sampler.RateLimitingSampler;
import brave.sampler.Sampler;
import org.apache.shardingsphere.agent.config.PluginConfiguration;
import org.apache.shardingsphere.agent.exception.PluginConfigurationException;
import org.apache.shardingsphere.agent.spi.boot.PluginBootService;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.Optional;
import java.util.Properties;

/**
 * Zipkin tracing plugin boot service.
 */
public final class ZipkinTracingPluginBootService implements PluginBootService {
    
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    
    private OkHttpSender sender;
    
    private Tracing tracing;
    
    @Override
    public void start(final PluginConfiguration pluginConfig) {
        if (!checkConfig(pluginConfig)) {
            throw new PluginConfigurationException("zipkin config error, host is null or port is %s", pluginConfig.getPort());
        }
        Properties props = pluginConfig.getProps();
        String urlVersion = Optional.ofNullable(props.getProperty("URL_VERSION")).orElse("/api/v2/spans");
        String serviceName = Optional.ofNullable(props.getProperty("SERVICE_NAME")).orElse("shardingsphere-agent");
        sender = OkHttpSender.create(String.format("http://%s:%s%s", pluginConfig.getHost(), pluginConfig.getPort(), urlVersion));
        Sampler sampler = createSampler(pluginConfig);
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        tracing = Tracing.newBuilder().localServiceName(serviceName).sampler(sampler).addSpanHandler(zipkinSpanHandler).build();
    }
    
    @Override
    public void close() {
        if (null != tracing) {
            tracing.close();
        }
        if (null != zipkinSpanHandler) {
            zipkinSpanHandler.close();
        }
        if (null != sender) {
            sender.close();
        }
    }
    
    @Override
    public String getType() {
        return "Zipkin";
    }
    
    private boolean checkConfig(final PluginConfiguration pluginConfiguration) {
        String host = pluginConfiguration.getHost();
        int port = pluginConfiguration.getPort();
        return null != host && !"".equalsIgnoreCase(host) && port > 0;
    }
    
    private Sampler createSampler(final PluginConfiguration pluginConfiguration) {
        String samplerType = Optional.ofNullable(pluginConfiguration.getProps().getProperty("SAMPLER_TYPE")).orElse("const");
        String samplerParam = Optional.ofNullable(pluginConfiguration.getProps().getProperty("SAMPLER_PARAM")).orElse("1");
        switch (samplerType) {
            case "const":
                if ("0".equals(samplerParam)) {
                    return Sampler.NEVER_SAMPLE;
                }
                return Sampler.ALWAYS_SAMPLE;
            case "counting":
                return Sampler.create(Float.parseFloat(samplerParam));
            case "ratelimiting":
                return RateLimitingSampler.create(Integer.parseInt(samplerParam));
            case "boundary":
                return BoundarySampler.create(Float.parseFloat(samplerParam));
            default:
                break;
        }
        return Sampler.ALWAYS_SAMPLE;
    }
}
