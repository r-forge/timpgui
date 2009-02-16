package test.org.rosuda.irconnect;

import junit.framework.TestCase;
import org.rosuda.irconnect.IRConnection;
import org.rosuda.jrclient.JRClientConnectionFactory;
import org.rosuda.rengine.REngineConnectionFactory;

/**
 *
 * @author Ralf
 */
public class TestCreateConnection extends TestCase{

    public void testCreateREngineConnection() {
        final IRConnection irConnection = new REngineConnectionFactory().createRConnection(null);
        assertNotNull(irConnection);
        irConnection.close();
    }

    public void testCreateRServeConnection() {
        final IRConnection irConnection = new JRClientConnectionFactory().createRConnection(null);
        assertNotNull(irConnection);
        irConnection.close();
    }

}
