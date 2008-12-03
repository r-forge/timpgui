package org.rosuda.jrclient;

import org.rosuda.JRclient.RFactor;
import org.rosuda.irconnect.IRFactor;


public class JRClientRFactor implements IRFactor {

	private final RFactor delegate; 
	
	JRClientRFactor(final RFactor delegate) {
		if (delegate == null)
			throw new IllegalArgumentException("missing required delegate");
		this.delegate = delegate;
	}

	public void add(String v) {
		delegate.add(v);
	}

	public String at(int i) {
		return delegate.at(i);
	}

	public int size() {
		return delegate.size();
	}
}
