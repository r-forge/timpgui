package org.rosuda.jrclient;

import java.util.Vector;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRVector;

class JRClientVector extends Vector implements IRVector{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8310271150504477455L;
	private final Vector delegate;
	
	JRClientVector(final Vector vec) {
		if (vec == null)
			throw new IllegalArgumentException("missing required delegate");
		this.delegate = vec;		
	}

    @Override
	public synchronized Object get(final int idx) {
		final Object rObj = delegate.get(idx);
		return JRClientObjectWrapper.wrap(rObj);
	}

    @Override
	public synchronized Object elementAt(final int idx) {
		final Object rObj = delegate.elementAt(idx);
		return JRClientObjectWrapper.wrap(rObj);
	}

    public IREXP at(final int idx) {
		final Object rObj = delegate.elementAt(idx);
		return JRClientObjectWrapper.wrapAsIREXP(rObj);
	}

    @Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

    @Override
	public int size() {
		return delegate.size();
	}
}
