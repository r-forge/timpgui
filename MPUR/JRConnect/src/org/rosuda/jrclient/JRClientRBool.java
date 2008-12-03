package org.rosuda.jrclient;

import org.rosuda.JRclient.RBool;
import org.rosuda.irconnect.IRBool;


public class JRClientRBool implements IRBool {

	private final RBool delegate;
	
	JRClientRBool(final RBool delegate) {
		if (delegate == null)
			throw new IllegalArgumentException("missing required delegate");
		this.delegate =  delegate;
		
	}
	public boolean isFALSE() {
		return delegate.isFALSE();
	}

	public boolean isNA() {
		return delegate.isNA();
	}

	public boolean isTRUE() {
		return delegate.isTRUE();
	}
}
