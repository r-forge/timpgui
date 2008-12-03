package org.rosuda.rengine;

import org.rosuda.irconnect.AConnectionFactory;
import org.rosuda.irconnect.IRConnection;

public class REngineConnectionFactory extends AConnectionFactory {

    @Override
    protected IRConnection handleCreateConnection(final String host, final int port) {
        return new REngineRConnection(host, port);
    }

}
