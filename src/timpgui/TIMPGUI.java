/*
 * TIMPGUI.java
 *
 * Created on March 14, 2007, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package timpgui;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import Jama.Matrix;

// Import JRI packages
import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.REXP;

//Import JGR Pacakges
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.ibase.SVar;
import org.rosuda.util.Global;

/**
 *
 * @author Joris
 */


/**
 * TIMPGUI Main Class
 */
public class TIMPGUI  {
    
    //TIMPGUI_Version 0.1 aplha Sep 2007
    
    /** Version number of TIMPGUI */
    public static final String VERSION = "0.1a";
    
    /** Title (used in the about box) */
    public static final String TITLE = "TIMPGUI";
    
    /** Subtitle (used in the about box) */
    public static final String SUBTITLE = "Java GUI for TIMP";
    
    /** Develtime (used in the about box) */
    public static final String DEVELTIME = "2007";
    
    /** Organization (used in the about box) */
    public static final String INSTITUTION = "Computational Biophysics, Vrije Universiteit van Amsterdam";
    
    /** First author TIMP (used in the about box) */
    public static final String AUTHOR1 = "Joris Snellenburg";
    
    /** Second author TIMP (used in the about box) */
    public static final String AUTHOR2 = "Kate Mullen";
    
    /** Website of organization (used in the about box) */
    public static final String WEBSITE = "http://www.nat.vu.nl";
    
    /** GUI Interface for TIMP */
    public static TIMPGUIFrame TIMPInterface = null;
    
    /**
     * The history of current session. If there was a .Rhistory file, it will be
     * loaded into this vector
     */
    public static Vector RHISTORY = null;
    
    /** RHOME path of current used R */
    public static String RHOME = null;
    
    /** RLIB pathes, where we can find the user's R-packages */
    public static String[] RLIBS = null;
    
    /** Rengine {@link org.rosuda.JRI.Rengine}, needed for executing R-commands */
    public static Rengine R = null;
    
    /** ConsoleSnyc {@link org.rosuda.JRG.toolkit.ConsoleSync} */
    public static ConsoleSync rSync = new ConsoleSync();
    
    /** Current data-sets (data.frames, matrix, ...) */
    public static Vector DATA = new Vector();
    
    /** Current models */
    public static Vector MODELS = new Vector();
    
    /** Current data not in DATA {@link DATA} */
    public static Vector OTHERS = new Vector();
    
    /** Current functions */
    public static Vector FUNCTIONS = new Vector();
    
    /** Current objects in workspace */
    public static Vector OBJECTS = new Vector();
    
    /** Keywords for syntaxhighlighting */
    public static HashMap KEYWORDS = new HashMap();
    
    /** Keywords (objects) for syntaxhighlighting */
    public static HashMap KEYWORDS_OBJECTS = new HashMap();
    
    /** Indicates wether the Rengine is up or not */
    public static boolean STARTED = false;
    
    /**
     * JGRListener, is uses for listening to java-commands coming from JGR's
     * R-process
     */
    //private static JGRListener jgrlistener = null;
    
    /** arguments for Rengine */
    private static String[] rargs = { "--save" };
    
    /**
     * Set to <code>true</code> when TIMP is running as the main program and
     * <code>false</code> if the classes are loaded, but not run via main.
     */
    private static boolean TIMPmain = false;
    
    /** Contains various methods to deal with TIMP datasets */
    public static TDatasets datasets = null;
    
    /**
     * Starting the JGR Application (javaside)
     */
    public TIMPGUI() {
        SVar.int_NA = -2147483648;
        
        Object dummy = new Object();
        JGRPackageManager.neededPackages.put("base", dummy);
        JGRPackageManager.neededPackages.put("graphics", dummy);
        JGRPackageManager.neededPackages.put("utils", dummy);
        JGRPackageManager.neededPackages.put("methods", dummy);
        JGRPackageManager.neededPackages.put("stats", dummy);
        JGRPackageManager.neededPackages.put("datasets", dummy);
        
        JGRPackageManager.neededPackages.put("JGR", dummy);
        JGRPackageManager.neededPackages.put("rJava", dummy);
        JGRPackageManager.neededPackages.put("javaGD", dummy);
        JGRPackageManager.neededPackages.put("TIMP", dummy);
        
        org.rosuda.util.Platform.initPlatform("org.rosuda.JGR.toolkit.");
        
        JGRPrefs.initialize();
        readHistory();
        TIMPInterface = new TIMPGUIFrame();
        TIMPInterface.setVisible(true);
        
        datasets = new TDatasets();
        
        // let's preemptively load JRI - if we do it here, we can show an error
        // message
        try {
            System.loadLibrary("jri");
        } catch (UnsatisfiedLinkError e) {
            String errStr = "all environment variables (PATH, LD_LIBRARY_PATH, etc.) are setup properly (see supplied script)";
            String libName = "libjri.so";
            if (System.getProperty("os.name").startsWith("Window")) {
                errStr = "you start JGR by double-clicking the JGR.exe program";
                libName = "jri.dll";
            }
            if (System.getProperty("os.name").startsWith("Mac")) {
                errStr = "you start JGR by double-clicking the JGR application";
                libName = "libjri.jnilib";
            }
            System.out.println("Cannot find Java/R Interface (JRI) library");
            
            System.err.println("Cannot find JRI native library!\n");
            e.printStackTrace();
            System.exit(1);
        }
        
        if (!Rengine.versionCheck()) {
            JOptionPane
                    .showMessageDialog(
                    null,
                    "Java/R Interface (JRI) library doesn't match this JGR version.\nPlease update JGR and JRI to the latest version.",
                    "Version Mismatch", JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
        R = new Rengine(rargs, true, TIMPInterface);
        if (org.rosuda.util.Global.DEBUG > 0) {
            System.out.println("Rengine created, waiting for R");}
        if (!R.waitForR()) {
            System.out.println("Cannot load R");
            System.exit(1);
        }
        JGRPackageManager.defaultPackages = RController.getDefaultPackages();
        
        R.eval("try(setwd(\""+JGRPrefs.workingDirectory+"\"),silent=T)");
        
        STARTED = true;
        TIMPInterface.end = TIMPInterface.output.getText().length();
        if (TIMPGUI.R != null && TIMPGUI.STARTED)
            TIMPGUI.R
                    .eval("options(width=" + TIMPGUI.TIMPInterface.getFontWidth()
                    + ")");
        TIMPInterface.input.requestFocus();
        new Refresher().run();
    }
    
    /**
     * Exits JGR, but not before asked the user if he wants to save his
     * workspace.
     *
     * @return users's answer (yes/no/cancel)
     */
    public static String exit() {
        int exit = JOptionPane.showConfirmDialog(null, "Save workspace?",
                "Close JGR", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (exit == 0) {
            writeHistory();
            JGRPrefs.writeCurrentPackagesWhenExit();
            return "y\n";
        } else if (exit == 1) {
            JGRPrefs.writeCurrentPackagesWhenExit();
            return "n\n";
        } else
            return "c\n";
    }
    
    public static Matrix getRMatrix(String datasetLabel) {
                // getDataFromR
        Matrix psidf=new Matrix(R.eval(datasetLabel+"@psi.df").asDoubleMatrix());
        return psidf;
    }
    
    public static double[] getRDouble(String datasetLabel, String xLabel) {
        // psidf.get(1,1);
        double[] x= R.eval(datasetLabel+"@"+xLabel).asDoubleArray();
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
     * Set keywords for highlighting.
     *
     * @param word
     *            This word will be highlighted
     */
    public static void setKeyWords(String word) {
        setKeyWords(new String[] { word });
    }
    
    /**
     * Set keywords for highlighting.
     *
     * @param words
     *            These words will be highlighted
     */
    public static void setKeyWords(String[] words) {
        KEYWORDS.clear();
        Object dummy = new Object();
        for (int i = 0; i < words.length; i++)
            KEYWORDS.put(words[i], dummy);
    }
    
    /**
     * Set objects for hightlighting.
     *
     * @param object
     *            This object will be highlighted
     */
    public static void setObjects(String object) {
        setObjects(new String[] { object });
    }
    
    /**
     * Set objects for hightlighting.
     *
     * @param objects
     *            These words will be highlighted
     */
    public static void setObjects(String[] objects) {
        OBJECTS.clear();
        KEYWORDS_OBJECTS.clear();
        Object dummy = new Object();
        for (int i = 0; i < objects.length; i++) {
            KEYWORDS_OBJECTS.put(objects[i], dummy);
            OBJECTS.add(objects[i]);
        }
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
                RHISTORY = new Vector();
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
            new ErrorMsg(e);
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
            new ErrorMsg(e);
        }
    }
    
    /** return the value of the {@link #JGRmain} flag */
    public static boolean isTIMPmain() {
        return TIMPmain;
    }
    
    private void checkForMissingPkg() {
        try {
            String previous = JGRPrefs.previousPackages;
            
            //System.out.println("previous "+previous);
            
            if (previous == null)
                return;
            String current = RController.getCurrentPackages();
            
            //System.out.println("current: "+current);
            
            if (current == null)
                return;
            
            Vector missing = new Vector();
            
            Vector currentPkg = new Vector();
            Vector previousPkg = new Vector();
            
            StringTokenizer st = new StringTokenizer(current,",");
            while (st.hasMoreTokens())
                currentPkg.add(st.nextToken().toString().replaceFirst(",",""));
            
            st = new StringTokenizer(previous,",");
            while (st.hasMoreTokens())
                previousPkg.add(st.nextToken().toString().replaceFirst(",",""));
            
            for (int i = 0; i < currentPkg.size(); i++)
                previousPkg.remove(currentPkg.elementAt(i));
            
            if (previousPkg.size() > 0)
                new JGRPackageManager(previousPkg);
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) {
        TIMPmain = true;
        if (args.length > 0) {
            Vector args2 = new Vector();
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--debug")) {
                    org.rosuda.util.Global.DEBUG = 1;
                    org.rosuda.JRI.Rengine.DEBUG = 1;
                    System.out.println("JGR version " + VERSION);
                } else
                    args2.add(args[i]);
                if (args[i].equals("--version")) {
                    System.out.println("JGR version " + VERSION);
                    System.exit(0);
                }
                if (args[i].equals("--help") || args[i].equals("-h")) {
                    System.out.println("JGR version " + VERSION);
                    System.out.println("\nOptions:");
                    System.out
                            .println("\n\t-h, --help\t Print short helpmessage and exit");
                    System.out.println("\t--version\t Print version end exit");
                    System.out
                            .println("\t--debug\t Print more information about JGR's process");
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
        
        if (Global.DEBUG > 0)
            for (int i = 0; i < rargs.length; i++)
                System.out.println(rargs[i]);
        
        try {
            new TIMPGUI();
        } catch (Exception e) {
            new ErrorMsg(e);
        }
    }
    
    class Refresher implements Runnable {
        
        public Refresher() {
            checkForMissingPkg();
        }
        
        public void run() {
            while (true)
                try {
                    Thread.sleep(60000);
                    REXP x = R.idleEval("try(.refreshKeyWords(),silent=TRUE)");
                    String[] r = null;
                    if (x != null && (r = x.asStringArray()) != null)
                        setKeyWords(r);
                    x = R.idleEval("try(.refreshObjects(),silent=TRUE)");
                    r = null;
                    if (x != null && (r = x.asStringArray()) != null)
                        setObjects(r);
                } catch (Exception e) {
                    new ErrorMsg(e);
                }
        }
    }
}