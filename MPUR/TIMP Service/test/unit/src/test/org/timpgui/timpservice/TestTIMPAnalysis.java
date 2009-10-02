/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.org.timpgui.timpservice;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import junit.framework.TestCase;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRMatrix;
import org.rosuda.irconnect.ITwoWayConnection;
import org.rosuda.jri.JRIConnectionFactory;

/**
 *
 * @author jsg210
 */
public class TestTIMPAnalysis extends TestCase {

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

    public void test_requireTIMP() {
        connection.eval("require(TIMP)");
        assertEquals(true, connection.eval("exists(\"readData\")").asBool().isTRUE());
        assertEquals(true, connection.eval("exists(\"preProcess\")").asBool().isTRUE());
        assertEquals(true, connection.eval("exists(\"initModel\")").asBool().isTRUE());
        assertEquals(true, connection.eval("exists(\"fitModel\")").asBool().isTRUE());
    }
    
    public void test_setwd() {
        File resourceDir = getResourcesDir();
        String pathName = resourceDir.getAbsolutePath().toString();
        connection.eval("setwd(\""+ pathName +"\")");
        assertEquals(pathName, connection.eval("getwd()").asString());        
    }

    public void test_readData() {
        File resourceDir = getResourcesDir();
        String pathName = resourceDir.getAbsolutePath().toString();
        connection.eval("setwd(\""+ pathName +"\")");
        connection.eval("dataset1<-readData(\"cp432704.txt\")");
        assertEquals(true, connection.eval("exists(\"dataset1\")").asBool().isTRUE());
    }

    public void test_preProcess() {
        connection.eval("dataset1<-preProcess(data = dataset1, baselinelambda = c(1, 5, 1, 32))");
        assertEquals(true, connection.eval("exists(\"dataset1\")").asBool().isTRUE());
    }

    public void test_initModel() {
        connection.eval("model1<- initModel(mod_type = \"kin\", kinpar= c(1.0,  0.01, 0.005  ), irfpar=c(.003, .095), parmu = list(c(.073)), lambdac = 1670, seqmod=TRUE, positivepar = c(\"kinpar\"), weightpar = list(c(-16, -.1, NA, NA, .1)), makeps=\"MARI\", title=\"MARI\", cohspec = list( type = \"irf\"))");
        assertEquals(true, connection.eval("exists(\"model1\")").asBool().isTRUE());
    }

    public void test_fitModel() {
        connection.eval("result1<-fitModel(list(dataset1), list(model1), opt=kinopt(iter=5, linrange = .2, plot=FALSE))");
        assertEquals(true, connection.eval("exists(\"result1\")").asBool().isTRUE());
    }

    public void test_getResiduals() {
        assertEquals(true, connection.eval("is.matrix(getResiduals(result1))").asBool().isTRUE());
        IREXP testREXP = connection.eval("getResiduals(result1)");
        assertEquals("textREXP is not a matrix", IREXP.XT_MATRIX, testREXP.getType());
        final IRMatrix matrix = testREXP.asMatrix();
        assertNotNull("conversion asMatrx failed!", matrix);
    }

    //getCLP(NAME_OF_RESULT_OBJECT, datasetNumber);
        //getdim1(NAME_OF_RESULT_OBJECT));
        //getdim2(NAME_OF_RESULT_OBJECT));
        //getResiduals(NAME_OF_RESULT_OBJECT, datasetNumber));
        //getData(NAME_OF_RESULT_OBJECT, datasetNumber)); //if weighted=TRUE then use 3rd argument
        //getTraces(NAME_OF_RESULT_OBJECT, datasetNumber));
        //getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "kinpar"));
        //getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "specpar"));




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