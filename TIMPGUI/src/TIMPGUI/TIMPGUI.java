/*
 * TIMPGUI.java
 * Joris Snellenburg
 */

package TIMPGUI;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JOptionPane;
import Jama.Matrix;

// Import JRI packages
import org.rosuda.JRI.Rengine;

/**
 * TIMPGUI Main Class
 * @revision February 19th, 2008.
 */
public class TIMPGUI  {
    
    /** TIMPGUI program constants */  
    public static final String VERSION = "0.1a";
    public static final String TITLE = "TIMPGUI";
    public static final String SUBTITLE = "Java GUI for TIMP";
    
    /** GUI Interface for TIMP and R synchronization*/
    public static TIMPGUIFrame TIMPInterface = null;
    public static Rengine R = null; //used to execute R commands
    public static ConsoleSync rSync = new ConsoleSync();
    
    /** File location R uses */
    public static Vector<String> RHISTORY = new Vector<String>(); //RHISTORY = null; // Stores the location .Rhistroy file
    public static String RHOME = null; //RHOME path of current used R
    public static String[] RLIBS = null;
    
    /** Bookkeeping */
    public static boolean STARTED = false;
    private static String[] rargs = { "--save" };
    private static int DEBUG = 0;

    /**
     * Starts  TIMPGUI Application
     */
    public TIMPGUI() {

        TSettings.readPrefs();
        readHistory();
        TIMPInterface = new TIMPGUIFrame();
        TIMPInterface.setVisible(true);
        if (System.getProperty("os.name").startsWith("Window")) {
        TSettings.isWindows = true;
        }
        if (System.getProperty("os.name").startsWith("Mac")) {
        TSettings.isMac = true;
        }
        
        // preemptively load JRI - if it doesn't work work in non R mode
        // TODO: make a non-R mode in which other functions are still accessible
        try {
            System.loadLibrary("jri");
        } catch (UnsatisfiedLinkError e) {
            String errStr = "Not all environment variables (PATH, LD_LIBRARY_PATH, etc.) are setup properly (see supplied script)";
            String libName = "libjri.so";
            
            System.out.printf("Cannot find Java/R Interface (JRI) library %s \n", libName);
            System.err.printf("%s \n", errStr);
            e.printStackTrace();
            System.exit(1);
        }
        
        if (!Rengine.versionCheck()) {
            JOptionPane
                    .showMessageDialog(
                    null,
                    "Java/R Interface (JRI) library doesn't match the latest version.\nPlease update your rJava libary to the latest version.",
                    "Version Mismatch", JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
        R = new Rengine(rargs, true, TIMPInterface);
        if (DEBUG > 0) {
            System.out.println("Rengine created, waiting for R");}
        if (!R.waitForR()) {
            System.out.println("Cannot load R");
            System.exit(1);
        }
        R.eval("try(setwd(\""+TSettings.workingDirectory+"\"),silent=T)");
        R.eval("try(require(TIMP),silent=T)");
        
        STARTED = true;
        TIMPInterface.end = TIMPInterface.output.getText().length();
        if (TIMPGUI.R != null && TIMPGUI.STARTED)
            TIMPGUI.R
                    .eval("options(width=" + TIMPGUI.TIMPInterface.getFontWidth()
                    + ")");
        TIMPInterface.input.requestFocus();
    }
    
    /**
     * Exits TIMPGUI, but not before asked the user if he wants to save his
     * workspace.
     *
     * @return users's answer (yes/no/cancel)
     */
    public static String exit() {
        int exit = JOptionPane.showConfirmDialog(null, "Save workspace?",
                "Close TIMPGUI", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (exit == 0) {
            writeHistory();
            TSettings.writeCurrentPackagesWhenExit();
            return "y\n";
        } else if (exit == 1) {
            TSettings.writeCurrentPackagesWhenExit();
            return "n\n";
        } else
            return "c\n";
    }
    
    public static Matrix getRMatrix(String datasetLabel) {
        return new Matrix(R.eval(datasetLabel+"@psi.df").asDoubleMatrix());
    }
    
    public static double[] getRDouble(String datasetLabel, String xLabel) {
        double[] x= R.eval(datasetLabel+"@"+xLabel).asDoubleArray();
        return x;
    }
    
    public static double[] getRLinLogLabels(String datasetLabel, String xLabel) {
        double[] x= R.eval("linloglines("+datasetLabel+"@"+xLabel+",0,10)").asDoubleArray();
        return x;
    }
    
    public static Matrix getRXList(String name) {
        Matrix x = new Matrix(R.eval("getXList("+name+")[[1]]").asDoubleMatrix());
        return x;
    }

    public static Matrix getRCLPList(String name) {
        Matrix x = new Matrix(R.eval("getCLPList("+name+")[[1]]").asDoubleMatrix());
        return x;
    }
    
    public static double[] getRDim1List(String name) {
        double[] x= R.eval("getdim1List("+name+")[[1]]").asDoubleArray();
        return x;
    }

    public static double[] getRDim1Labels(String name, double mu, double alpha) {
        double[] x= R.eval("linloglines(getdim1List("+name+")[[1]],"+mu+","+alpha+")").asDoubleArray();
        return x;
    }
  
    static double[] getRDim2Labels(String name, double mu, double alpha) {
        double[] x= R.eval("linloglines(getdim2List("+name+")[[1]],"+mu+","+alpha+")").asDoubleArray();
        return x;
    }

    static double[] getRDim2List(String name) {
        double[] x= R.eval("getdim2List("+name+")[[1]]").asDoubleArray();
        return x;
    }
    
    /**
     * Set R_HOME (in java app).
     *
     * @param rhome
     *            RHOME path
     */
    public static void setRHome(String rhome) {
        RHOME = rhome;
    }
    
    /**
     * Set R_LIBS (in java app).
     *
     * @param lib
     *            Library path
     */
    public static void setRLibs(String lib) {
        setRLibs(new String[] { lib });
    }
    
    /**
     * Set R_LIBS (in java app).
     *
     * @param libs
     *            Library pathes
     */
    public static void setRLibs(String[] libs) {
        RLIBS = libs;
        for (int i = 0; i < RLIBS.length; i++)
            if (RLIBS[i].startsWith("~"))
                RLIBS[i] = RLIBS[i].replaceFirst("~", System
                        .getProperty("user.home"));
    }
    

    

    
    
    /**
     * If there is a file named .Rhistory in the user's home path we load his
     * commands to current history.
     */
    public static void readHistory() {
        File hist = null;
        try {
            if ((hist = new File(System.getProperty("user.home")
            + File.separator + ".TIMPhistory")).exists()) { //note
                
                BufferedReader reader = new BufferedReader(new FileReader(hist));
                Vector<String> RHISTORY = new Vector<String>();
                String cmd = null;
                while (reader.ready()) {
                    cmd = (cmd == null ? "" : cmd + "\n") + reader.readLine();
                    if (cmd.endsWith("#")) {
                        RHISTORY.add(cmd.substring(0, cmd.length() - 1));
                        cmd = null;
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Write the commands of current session to .Rhistory.
     */
    public static void writeHistory() {
        File hist = null;
        try {
            hist = new File(System.getProperty("user.home") + File.separator
                    + ".TIMPhistory");
            BufferedWriter writer = new BufferedWriter(new FileWriter(hist));
            Enumeration e = TIMPGUI.RHISTORY.elements();
            while (e.hasMoreElements()) {
                writer.write(e.nextElement().toString() + "#\n");
                writer.flush();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    public static void main(String[] args) {

        if (args.length > 0) {
            Vector<String> args2 = new Vector<String>();
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--debug")) {
                    DEBUG = 1;
                    System.out.println("TIMP version " + VERSION);
                } else
                    args2.add(args[i]);
                if (args[i].equals("--version")) {
                    System.out.println("TIMP version " + VERSION);
                    System.exit(0);
                }
                if (args[i].equals("--help") || args[i].equals("-h")) {
                    System.out.println("TIMP version " + VERSION);
                    System.out.println("\nOptions:");
                    System.out
                            .println("\n\t-h, --help\t Print short helpmessage and exit");
                    System.out.println("\t--version\t Print version end exit");
                    System.out
                            .println("\t--debug\t Print debug information");
                    System.out
                            .println("\nMost other R options are supported too");
                    System.exit(0);
                }
            }
            Object[] arguments = args2.toArray();
            if (arguments.length > 0) {
                rargs = new String[arguments.length + 1];
                for (int i = 0; i < rargs.length - 1; i++)
                    rargs[i] = arguments[i].toString();
                rargs[rargs.length - 1] = "--save";
            }
        }
        
        if (DEBUG > 0)
            for (int i = 0; i < rargs.length; i++)
                System.out.println(rargs[i]);
        
        try {
            new TIMPGUI();
        } catch (Exception e) {
        }
    }
}