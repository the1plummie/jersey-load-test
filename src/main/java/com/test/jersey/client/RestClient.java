package com.test.jersey.client;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadFactory;


/**
 * Created by shouguoli on 4/13/16.
 */
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public static Client newClient() {
        return newClient(null);
    }

    public static Client newClient(ClientConfig clientConfig) {
        if (clientConfig == null) {
            clientConfig = createClientConfig();
        }
        return ClientBuilder.newClient(clientConfig);
    }

    public static ClientConfig createClientConfig() {
        return createClientConfig(32);
    }

    public static ClientConfig createClientConfig(int maxConns) {
        return createClientConfig(maxConns, 10000);
    }

    public static ClientConfig createClientConfig(int maxConns, int timeout) {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, timeout)
                .property(ClientProperties.CONNECT_TIMEOUT, timeout);

        if (maxConns > 0) {
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.setMaxTotal(maxConns);
            connectionManager.setDefaultMaxPerRoute(maxConns);
            clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager)
                    .connectorProvider(new ApacheConnectorProvider());
        }

        return clientConfig;
    }

}
