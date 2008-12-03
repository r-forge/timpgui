package org.rosuda.jrclient;

import java.util.Vector;

import org.rosuda.JRclient.RBool;
import org.rosuda.REngine.REXP;
import org.rosuda.JRclient.RFactor;
import org.rosuda.JRclient.RList;
import org.rosuda.irconnect.AREXP;
import org.rosuda.irconnect.IREXP;
import org.rosuda.rengine.REngineREXP;

public class JRClientObjectWrapper {

	static Object wrap(final Object obj) {
		if (obj == null)
			return null;
		else if (obj instanceof RList) {
			return new JRClientRList((RList) obj);
		} else if (obj instanceof REXP) {
			return new REngineREXP((REXP) obj);
			// --old
			//return new JRClientREXP((REXP) obj);
		} else if (obj instanceof RBool) {
			return new JRClientRBool((RBool) obj);
		} else if (obj instanceof RFactor) {
			return new JRClientRFactor((RFactor)obj);
		} else if (obj instanceof Vector) {
			return new JRClientVector((Vector) obj);
		}
		return obj;
	}

    static IREXP wrapAsIREXP(final Object obj) {
        if (obj == null) {
			return new AREXP() {
                @Override
                public int getType() {return XT_NULL;}

            };
		} else if (obj instanceof REXP) {
			return new REngineREXP((REXP) obj);
		}
        throw new IllegalArgumentException("cannot wrap "+obj+" into IREXP");
    }
}
