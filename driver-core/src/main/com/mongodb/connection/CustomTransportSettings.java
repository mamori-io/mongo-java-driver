package com.mongodb.connection;

import com.mongodb.internal.connection.StreamFactoryFactory;
import com.mongodb.spi.dns.InetAddressResolver;

public abstract class CustomTransportSettings extends TransportSettings {
    public abstract StreamFactoryFactory getStreamFactoryFactory(final InetAddressResolver inetAddressResolver);
}
