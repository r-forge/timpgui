/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rosuda.jri;

import java.util.Map.Entry;
import java.util.Properties;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;
import org.rosuda.irconnect.IConnectionFactory;
import org.rosuda.irconnect.IRConnection;

/**
 *
 * @author Ralf
 */
public class JRIConnectionFactory implements IConnectionFactory{

   private static IConnectionFactory instance = new JRIConnectionFactory();

    public static final String RUNMAINLOOL = "runMainLoop";

    private final RMainLoopCallbacks defaultCallback;

    public static IConnectionFactory getInstance() {
        return instance;
    }

    protected JRIConnectionFactory() {
        instance = this;
        defaultCallback = new RMainLoopCallbacks() {

            public void rWriteConsole(final Rengine engine, final String text, int arg2) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void rBusy(final Rengine engine, final int which)  {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String rReadConsole(final Rengine engine, final String prompt, final int addToHistory)  {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void rShowMessage(final Rengine engine, final String message) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String rChooseFile(final Rengine engine, final int newFile) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void rFlushConsole(final Rengine engine) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void rSaveHistory(final Rengine engine, final String filename) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void rLoadHistory(final Rengine engine, final String filename) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    @Override
    public IRConnection createConnection(final Properties configuration) {
        String[] rargs = null;
        boolean runMainLoop = true;
        RMainLoopCallbacks callback = null;
        if (configuration != null) {
            rargs = new String[configuration.size()];
            int count = 0;
            for (final Entry<Object,Object> entry: configuration.entrySet()) {
                if (entry.getValue() instanceof String) {
                    rargs[count++] = (String) entry.getValue();
                }
                if (RUNMAINLOOL.equals(entry.getKey())) {
                    runMainLoop = Boolean.parseBoolean(entry.getValue().toString());
                    if (callback == null)
                        callback = defaultCallback;
                }
                if (entry.getValue() instanceof RMainLoopCallbacks) {
                    callback = (RMainLoopCallbacks) entry.getValue();
                }
            }
        }
        return new JRIConnection(new Rengine(rargs, runMainLoop, callback));
    }

}
