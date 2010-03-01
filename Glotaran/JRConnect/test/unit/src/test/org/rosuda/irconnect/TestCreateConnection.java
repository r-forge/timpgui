package test.org.rosuda.irconnect;

import junit.framework.TestCase;
import org.rosuda.irconnect.IRConnection;
import org.rosuda.rengine.REngineConnectionFactory;

/**
 *
 * @author Ralf
 */
public class TestCreateConnection extends TestCase{

    public void testCreateREngineConnection() {
        final IRConnection irConnection = new REngineConnectionFactory().createRConnection(null);
        final IRConnection irConnection2 = new REngineConnectionFactory().createRConnection(null);
        final IRConnection irConnection3 = new REngineConnectionFactory().createRConnection(null);
        final IRConnection irConnection4 = new REngineConnectionFactory().createRConnection(null);
        assertNotNull(irConnection);
        assertNotNull(irConnection2);
        assertNotNull(irConnection3);
        assertNotNull(irConnection4);
        assertNotNull(irConnection.eval("rnorm(10^7)").toString());
        assertNotNull(irConnection2.eval("rnorm(10^7)").toString());
        assertNotNull(irConnection3.eval("rnorm(10^7)").toString());
        assertNotNull(irConnection4.eval("rnorm(10^7)").toString());        
        irConnection.close();
        irConnection2.close();
        irConnection3.close();
        irConnection4.close();
    }

}
