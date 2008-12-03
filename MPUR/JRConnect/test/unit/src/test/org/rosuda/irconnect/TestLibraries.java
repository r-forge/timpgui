package test.org.rosuda.irconnect;

import junit.framework.TestCase;

import org.rosuda.JRclient.RSrvException;
import org.rosuda.JRclient.Rconnection;

public class TestLibraries extends TestCase {

	Rconnection connection;
	
	protected void setUp() throws Exception {		
		super.setUp();
		connection = new Rconnection();				
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		connection.close();
		try {
			connection.shutdown();
		} catch (final RSrvException rse) {			
		}
	}

	public void testConnection() {
		assertNotNull("not connected", connection);
	}
	
	public void testLibraries() throws RSrvException {
		/*
		final REXP libraryREXP =				
			connection.eval(
				new StringBuffer()
					.append("try(")
					.append("library()")
					.append(")")
					.toString()
					);
		
		
			assertEquals("libraryREXP is not a List", REXP.XT_LIST, libraryREXP.getType());
			final RList elementList = libraryREXP.asList();
			assertEquals("elementList.getHead is not a Vector", REXP.XT_VECTOR, elementList.getHead().getType());
			assertEquals("elementList.getBody is not a Vector", REXP.XT_VECTOR, elementList.getBody().getType());
			assertNull("elementList.getTag is not a null", elementList.getTag());
			final Vector listHead = elementList.getHead().asVector();
			assertEquals("head has more than 3 element", 3, listHead.size());
			assertEquals("elementList.getHead #0 is not a String", REXP.XT_STR, ((REXP)listHead.get(0)).getType());
			assertEquals("elementList.getHead #1 is not a String", REXP.XT_STR, ((REXP)listHead.get(1)).getType());
			assertEquals("elementList.getHead #2 is not a String", REXP.XT_STR, ((REXP)listHead.get(2)).getType());
			assertEquals("elementList.getHead #0 is not header", "header", ((REXP)listHead.get(0)).asString());
			assertEquals("elementList.getHead #1 is not results", "results", ((REXP)listHead.get(1)).asString());
			assertEquals("elementList.getHead #2 is not footer", "footer", ((REXP)listHead.get(2)).asString());
			final Vector listBody = elementList.getBody().asVector();
			assertEquals("body has more than 3 element",3, listBody.size());
			
			assertEquals("listBody not null first element", REXP.XT_NULL, ((REXP)listBody.get(0)).getType());
			assertEquals("listBody second element is not a list", REXP.XT_LIST, ((REXP)listBody.get(1)).getType());
			assertEquals("listBody not null third element", REXP.XT_NULL, ((REXP)listBody.get(2)).getType());
			
			final RList rList = ((REXP)listBody.get(1)).asList();
			assertEquals("rList not Int* Head", REXP.XT_ARRAY_INT, rList.getHead().getType());
			assertEquals("rList not Vector Body", REXP.XT_VECTOR, rList.getBody().getType());
			assertNull("rList not null Tag", rList.getTag());
			
			int[] dim = rList.getHead().asIntArray();
			assertEquals("not 3 columns", 3, dim[1]);
			final int rows = dim[0];
			final Vector rListBodyVec = rList.getBody().asVector();
			assertEquals("vector does not correspond to dimension (rows="+rows+" * columns=3)", rows * 3, rListBodyVec.size());
			*/						
	}
	
}
