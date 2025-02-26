/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.internal.connection;

import com.mongodb.MongoClientException;
import com.mongodb.connection.CustomTransportSettings;
import com.mongodb.connection.NettyTransportSettings;
import com.mongodb.connection.TransportSettings;
import com.mongodb.internal.connection.netty.NettyStreamFactoryFactory;
import com.mongodb.spi.dns.InetAddressResolver;

/**
 * <p>This class is not part of the public API and may be removed or changed at any time</p>
 */
public final class StreamFactoryHelper {
    public static StreamFactoryFactory getStreamFactoryFactoryFromSettings(final TransportSettings transportSettings,
            final InetAddressResolver inetAddressResolver) {
        if (transportSettings instanceof NettyTransportSettings) {
            return NettyStreamFactoryFactory.builder().applySettings((NettyTransportSettings) transportSettings)
                    .inetAddressResolver(inetAddressResolver)
                    .build();
        } else if(transportSettings instanceof CustomTransportSettings) {
            return ((CustomTransportSettings) transportSettings).getStreamFactoryFactory(inetAddressResolver);
        } else {
            throw new MongoClientException("Unsupported transport settings: " + transportSettings.getClass().getName());
        }
    }

    private StreamFactoryHelper() {
    }
}
