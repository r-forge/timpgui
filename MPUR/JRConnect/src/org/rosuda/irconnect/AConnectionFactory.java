/*
 * This is a conveniance implementation that extracts
 * <strong>host</strong> and <strong>port</strong> from the properties file
 *
 */

package org.rosuda.irconnect;

import java.util.Properties;

/**
 *
 * @author Ralf
 */
public abstract class AConnectionFactory implements IConnectionFactory{

    private static IConnectionFactory instance;

    public static IConnectionFactory getInstance() {
        return instance;
    }
    
    protected AConnectionFactory() {
        instance = this;
    }

    @Override
    public IRConnection createConnection(final Properties configuration) {
        if (configuration == null)
            return handleCreateConnection(default_host, default_port);
        String host = default_host;
        int port = default_port;
        if (configuration.containsKey(IConnectionFactory.HOST)) {
            host = configuration.getProperty(IConnectionFactory.HOST);
        }
        if (configuration.containsKey(IConnectionFactory.PORT)) {
            port = Integer.parseInt(configuration.getProperty(IConnectionFactory.PORT));
        }
        return handleCreateConnection(host, port);
    }

    protected abstract IRConnection handleCreateConnection(final String host, final int port);
}
