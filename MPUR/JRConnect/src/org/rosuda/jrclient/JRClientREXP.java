package org.rosuda.jrclient;


import org.rosuda.JRclient.REXP;
import org.rosuda.irconnect.IRBool;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRFactor;
import org.rosuda.irconnect.IRList;
import org.rosuda.irconnect.IRMap;
import org.rosuda.irconnect.IRMatrix;
import org.rosuda.irconnect.IRVector;

@Deprecated
public class JRClientREXP implements IREXP {

	private final REXP delegate;
	
	JRClientREXP(final REXP rexp) {
		if (rexp == null)
			throw new IllegalArgumentException("missing required delegate");
		this.delegate = rexp;
	}

	

	public IRList asList() {
		return new JRClientRList(delegate.asList());
	}

	public IRVector asVector() {
		return new JRClientVector(delegate.asVector());
	}

	public String asString() {
		return delegate.asString();
	}

	public IRBool asBool() {
		return new JRClientRBool(delegate.asBool());
	}

	public int getType() {
	/* activate for IRMatrix */
		final int[] dim = dim();
		if (dim!=null&&dim.length==2)
			return IREXP.XT_MATRIX;			
		return delegate.getType();
	}

	public int asInt() {
		return delegate.asInt();
	}

	public int[] asIntArray() {
		return delegate.asIntArray();
	}

	public double asDouble() {
		return delegate.asDouble();
	}

	public double[] asDoubleArray() {
		return delegate.asDoubleArray();
	}

	public IRFactor asFactor() {
		return new JRClientRFactor(delegate.asFactor());
	}
    @Deprecated
	public IREXP getAttribute() {
        return null;/*
		if (delegate.getAttribute()==null)
			return null;
		return new JRClientREXP(delegate.getAttribute());*/
	}

	public Object getContent() {
		final Object content = delegate.getContent();
		return JRClientObjectWrapper.wrap(content);
	}

	public IRMap asMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] asStringArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] dim() {
        /*
		if (delegate.getAttribute()!=null) {
			final REXP attribute = delegate.getAttribute();
			if (attribute.getType()==IREXP.XT_LIST) {
				final IRList tagList = new JRClientRList(attribute.asList());
				final IREXP tag = tagList.getTag();
				if (tag!=null&&tag.getType()==IREXP.XT_SYM&&tag.asSymbol()!=null) {
					final IREXP sym = tag.asSymbol();	
					if (sym.toString().equalsIgnoreCase("[SYMBOL dim]")) {
						if (tagList.getHead()!=null)
							if (tagList.getHead().getType()==IREXP.XT_ARRAY_INT)
								return tagList.getHead().asIntArray();
							else if (tagList.getHead().getType()==IREXP.XT_INT)
								return new int[]{tagList.getHead().asInt()};
					}					
				}
			}			
		}*/
		return null;
	}

	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IREXP asSymbol() {
		if (delegate==null)
			return null;
		return new JRClientREXP(delegate);
	}

	public IRBool[] asBoolArray() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
	public String toString() {
		return delegate.toString();
	}

	public IRMatrix asMatrix() {
		final IREXP irExp = new JRClientREXP(delegate);
		return new JRClientMatrix(irExp.dim(), delegate);
	}



	public boolean hasAttribute(final String attrName) {		
		return false;
	}

	public IREXP getAttribute(final String attrName) {
		return null;
	}
}
