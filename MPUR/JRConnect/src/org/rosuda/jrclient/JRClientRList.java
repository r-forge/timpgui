package org.rosuda.jrclient;

import org.rosuda.JRclient.RList;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRList;

@Deprecated
public class JRClientRList implements IRList {

	private final RList delegate;
	
	JRClientRList(final RList list) {
		if (list == null)
			throw new IllegalArgumentException("missing required delegate");
		this.delegate = list;
	}
	
	public IREXP getBody() {
        return null;/*
		return new JRClientREXP(delegate.getBody());
                     */
	}

	public IREXP getHead() {
        return null;/*
		return new JRClientREXP(delegate.getHead());
                     */
	}

	public IREXP getTag() {
        return null;/*
		return new JRClientREXP(delegate.getTag());
                     */
	}

	public String[] keys() {
		return delegate.keys();
	}

	public IREXP at(final String key) {
		return new JRClientREXP(delegate.at(key));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
