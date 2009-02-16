package org.rosuda.jrclient;

import org.rosuda.irconnect.AConnectionFactory;
import org.rosuda.irconnect.ARConnection;
import org.rosuda.irconnect.IJava2RConnection;
import org.rosuda.irconnect.IRConnection;

public class JRClientConnectionFactory extends AConnectionFactory {

    protected ARConnection handleCreateConnection(final String host, final int port) {
        return new JRClientRconnection(host, port);
    }

    @Override
    protected IJava2RConnection handleCreateTransfer(final IRConnection con) {
        if (!(con instanceof JRClientRconnection))
            throw new IllegalArgumentException("Unsupported type:"+con);
        return new JRJava2RConnection((JRClientRconnection)con);
    }

}
