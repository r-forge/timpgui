package test.org.timpgui.timpservice;


import junit.framework.TestCase;
import org.timpgui.timpservice.TimpController;

public class TestTimpController extends TestCase {

    //static is alas necessary for this test to run on windows machine
    //since closing connection does not enable to connection(s)!
	private static TimpController controller;
    public static final double EPS = 0.000001;

    @Override
	protected void setUp() throws Exception {		
		super.setUp();
        if (controller==null) {
          controller = new TimpController();
     }
	}

	public void testConnection() {
		assertNotNull("not connected", controller);
	}

	public void testString() {
        final String testString = controller.getString(new StringBuffer().append(
		"try(").append("\"string\"").append(")").toString());
		assertNotNull(testString);
	}

    public void testDouble() {
		final double testDouble = controller.getDouble(new StringBuffer().append(
		"try(").append("1/4").append(")").toString());

        assertNotNull(testDouble);
		assertEquals(0.25, testDouble, EPS);
	}

}
