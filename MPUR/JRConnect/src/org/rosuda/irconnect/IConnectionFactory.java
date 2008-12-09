/**
 * thats the entry towards the wrapped IRConnection
 * use <strong>host</strong> for host property
 * or <strong>PORT</strong> for port (which must be an integer number!)
 * other implementing interfaces like JRI are able to use any type of
 * RMainLoopCallbacks (for YOUR gui callback interface) and support the
 * parameter <strong>runMainLoop</strong>
 * see the documentatino for <strong>org.rosuda.JRI.RMainLoopCallbacks</strong>
 * for further advice on its usefulness
 */
package org.rosuda.irconnect;

import java.util.Properties;

public interface IConnectionFactory {

    public static final String HOST = "192.168.0.100";
    public static final String PORT = "6311";
    public static final String default_host="192.168.0.100";
    public static final int default_port=6311;

    /**
     * implement this method if you'd like another type of wrapped normalized
     * R connection available.
     * @param configuration
     * @return
     */
	public IRConnection createConnection(final Properties configuration);
}
