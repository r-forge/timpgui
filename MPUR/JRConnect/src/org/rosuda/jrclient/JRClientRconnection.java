package org.rosuda.jrclient;

import org.rosuda.REngine.Rserve.RserveException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.irconnect.ARConnection;
import org.rosuda.irconnect.IRConnection;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.RServerException;
import org.rosuda.rengine.REngineREXP;


class JRClientRconnection extends ARConnection implements IRConnection {

	final RConnection delegate;
	
	JRClientRconnection(final String host, final int port) {
		try {
			this.delegate = new RConnection(host,port);
		} catch (final RserveException re) {
			throw new RServerException(this, re.getRequestErrorDescription(), re.getMessage());
		}
		if (this.delegate == null)
			throw new IllegalArgumentException("missing required delegate");
	}
	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#voidEval(java.lang.String)
	 */
	public void voidEval(final String expression) {
		try {
			delegate.voidEval(expression);
		} catch (RserveException e) {
			throw new RServerException(this, e.getRequestErrorDescription(), e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#eval(java.lang.String)
	 */
	public IREXP eval(final String expression) {
		try {
			return new REngineREXP(delegate.eval(expression));
			//"old"
			//return new JRClientREXP(delegate.eval(expression));
		} catch (final RserveException e) {
			throw new RServerException(this, e.getRequestErrorDescription(), e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#close()
	 */
	public void close() {
		delegate.close();
	}

	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#isConnected()
	 */
	public boolean isConnected() {
		return delegate.isConnected();
	}

	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#getLastError()
	 */
	public String getLastError() {
		return delegate.getLastError();
	}

	/* (non-Javadoc)
	 * @see org.rosuda.JRclient.IRConnection#shutdown()
	 */
	public void shutdown() {
		try {
			delegate.shutdown();
		} catch (final RserveException e) {
			throw new RServerException(this, e.getRequestErrorDescription(), e.getMessage());
		}
	}

    @Override
    protected void login(final String userName, final String userPassword) {
       try {
            delegate.login(userName, userName);
       } catch (final RserveException e) {
            throw new RServerException(this, e.getRequestErrorDescription(), e.getMessage());
       }
    }
		
	

}
