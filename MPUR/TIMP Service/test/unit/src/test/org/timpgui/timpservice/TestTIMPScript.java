/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.org.timpgui.timpservice;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import junit.framework.TestCase;
import org.netbeans.api.java.classpath.ClassPath;
import org.openide.filesystems.FileObject;
import org.rosuda.irconnect.ITwoWayConnection;
import org.rosuda.jri.JRIConnectionFactory;

/**
 *
 * @author jsg210
 */
public class TestTIMPScript extends TestCase {

    //static is alas necessary for this test to run on windows machine
    //since closing connection does not enable to connection(s)!
    private static ITwoWayConnection connection;
    private static final String RESOURCES = "test.org.timpgui.timpservice.resources";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if (connection == null) {
            connection = JRIConnectionFactory.getInstance().createTwoWayConnection(null);
        }
    }

    public void testConnection() {
        assertNotNull("not connected", connection);
    }

    public void testScript() {
        File resourceDir = getResourcesDir();
        String pathName = resourceDir.getAbsolutePath().toString();
        connection.eval("setwd(\""+ pathName +"\")");
        connection.eval("source(\"kin_simple_ir_script.R\")");
        assertEquals(true,connection.eval("exists(\"denRes\")").asBool().isTRUE());
        assertEquals(true,connection.eval("exists(\"mdDat\")").asBool().isTRUE());
        assertEquals(true,connection.eval("exists(\"model1\")").asBool().isTRUE());
    }

    private File getResourcesDir() {

            String className = getClass().getName();
            URL url = this.getClass().getResource(className.substring(className.lastIndexOf('.')+1)+".class"); // NOI18N
            File dir;
            try {
                dir = new File(url.toURI());
            } catch(URISyntaxException e) {
                dir = new File(url.getPath());
            }
            File dataDir = dir.getParentFile();
            int index = 0;
            while((index = className.indexOf('.', index)+1) > 0) {
                dataDir = dataDir.getParentFile();
            }
            dataDir = new File(dataDir, RESOURCES.replace(".", "/")); //NOI18N
            return dataDir;

    }
}