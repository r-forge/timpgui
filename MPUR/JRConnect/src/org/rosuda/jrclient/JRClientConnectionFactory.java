package org.rosuda.jrclient;

import org.rosuda.irconnect.AConnectionFactory;
import org.rosuda.irconnect.IRConnection;

public class JRClientConnectionFactory extends AConnectionFactory {

    protected IRConnection handleCreateConnection(final String host, final int port) {
        return new JRClientRconnection(host, port);
    }
}
