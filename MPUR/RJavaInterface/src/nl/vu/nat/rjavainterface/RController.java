package nl.vu.nat.rjavainterface;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import nl.wur.flimdataloader.flimpac.DatasetTimp;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

import org.rosuda.JRI.*;

public class RController implements RMainLoopCallbacks {

    public static Rengine re = null;
    public static ConsoleSync rSync = new ConsoleSync();


    /** arguments for Rengine */
    private String[] rargs = {"--save"};
    
    public static boolean STARTED = false;
    public static Vector<String> RHISTORY = new Vector<String>(); //RHISTORY = null; // Stores the location .Rhistroy file
    public static String RHOME = null; //RHOME path of current used R
    public static String[] RLIBS = null;
    
    private String wspace = null;
    private int currentHistPosition;
    private boolean DEBUG = true;
    
    private JFileChooser chooser;
    private StringBuffer console = new StringBuffer();
    private static InputOutput io = IOProvider.getDefault().getIO("R Output", false);
    private static OutputWriter output;
    private static OutputWriter error;

    public RController() {
        init();
        io.select();
    }

    private void init() {
        // just making sure we have the right version of everything
        if (!Rengine.versionCheck()) {
        }
        if (DEBUG) {
            System.out.println("Creating Rengine (with arguments)");
        }
        // 1) we pass the arguments from the command line
        // 2) we won't use the main loop at first, we'll start it later
        //    (that's the "false" as second argument)
        // 3) the callbacks are implemented by the TextConsole class above
        re = new Rengine(rargs, true, this);
        //re.startMainLoop();
        if (DEBUG) {
            System.out.println("Rengine created, waiting for R");
        }
        // the engine creates R is a new thread, so we should wait until it's ready
        if (!re.waitForR()) {
            error.println("Cannot load R");
            if (DEBUG) {
                System.out.println("Cannot load R");
            }
        } else {
            if (DEBUG) {
                System.out.println("R was loaded sucesfully");
            }
            RHOME = getRHome();
            RLIBS = getRLibs();
            STARTED = true;
            re.eval("require(TIMP)");
        }
    }

    public static boolean isSupported(String cmd) {
        cmd = cmd.trim();
        if (cmd.startsWith("fix(") || cmd.startsWith("edit(") || cmd.startsWith("edit.data.frame(")) {
            error.println("> " + cmd);
            error.println("Warning: error funtionality is not yet supported.");
            return false;
        }
        return true;
    }

    /**
     * Parse command if it is a helpcommand.
     *
     * @param cmd
     *            command which should be executed
     * @return true if help should be started, false if not
     */
    // later i hope it will be possible let R do this
    public boolean isHelpCMD(String cmd) {
        cmd = cmd.trim();
        if (cmd.startsWith("help(") || cmd.startsWith("?") || cmd.startsWith("help.start(") || cmd.startsWith("help.search(")) {
            error.println("> " + cmd);
            error.println("Warning: help funtionality is not yet supported.");
            return true;
        }
        return false;
    }

    /**
     * Write output from R into console (old R callback).
     *
     * @param re
     *            used Rengine
     * @param text
     *            output
     * @param addToHist
     *            seems to be added in versions 1.5 (no documentation)
     */
    public void rWriteConsole(Rengine re, String text, int addToHist) {
        output = io.getOut();
        console.append(text);
        if (console.length() > 100) {
            output.append(console.toString());
            console.delete(0, console.length());
        }
        output.close();
    }

    /**
     * Invoke the busy cursor (R callback).
     *
     * @param re
     *            used Rengine
     * @param which
     *            busy (1) or not (0)
     */
    public void rBusy(Rengine re, int which) {
        output = io.getOut();
        if (which == 0) {
            if (output != null) {
                output.append(console.toString()); // color?
                console.delete(0, console.length());
            }

        }
    }

    /**
     * Read the commands from input area (R callback).
     *
     * @param re
     *            used Rengine
     * @param prompt
     *            prompt from R
     * @param addToHistory
     *            is it an command which to add to the history
     */
    public String rReadConsole(Rengine re, String prompt, int addToHistory) {
        output = io.getOut();
        if (prompt.indexOf("Save workspace") > -1) {
            String retVal = exit();
            if (wspace != null && retVal.indexOf('y') >= 0) {
                RController.re.eval("save.image(\"" + wspace.replace('\\', '/') + "\")");
                return "n\n";
            } else {
                return retVal;
            }
        } else {
            output.append(prompt);
            String s = rSync.waitForNotification();
            try {
                output.append(s + "\n");
            } catch (Exception e) {
            }
            return (s == null || s.length() == 0) ? "\n" : s + "\n";
        }
    }

    /**
     * Showing a message from the rengine (R callback).
     *
     * @param re
     *            used Rengine
     * @param message
     *            message from R
     */
    public void rShowMessage(Rengine re, String message) {
        JOptionPane.showMessageDialog(null, message,"R Message",1);
	}

    /**
     * Choose a file invoked be file.choose() (R callback).
     *
     * @param re
     *            used Rengine
     * @param newFile
     *            if it's a new file
     */
    public String rChooseFile(Rengine re, int newFile) {
        String res = null;
        String directory = null;
        if (directory != null) {
            chooser = new JFileChooser(directory);
        } else {
            chooser = new JFileChooser();

        }
        int result = chooser.showOpenDialog(chooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            res = chooser.getCurrentDirectory().getAbsolutePath() + File.separator;
        }
        return res;
    }

    /**
     * Flush the console (R callback). !! not implemented yet !!
     *
     * @param re
     *            used Rengine
     */
    public void rFlushConsole(Rengine re) {
    }

    /**
     * Load history from a file (R callback).
     *
     * @param re
     *            used Rengine
     * @param filename
     *            history file
     */
    public void rLoadHistory(Rengine re, String filename) {
        File hist = null;
        try {
            if ((hist = new File(filename)).exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(hist));
                if (RHISTORY == null) {
                    RHISTORY = new Vector<String>();
                }
                while (reader.ready()) {
                    RHISTORY.add(reader.readLine() + "\n");
                }
                reader.close();
            }
        } catch (Exception e) {
        // new Error(e);
        }
    }

    /**
     * Save history to a file (R callback).
     *
     * @param re
     *            used Rengine
     * @param filename
     *            history file
     */
    public void rSaveHistory(Rengine re, String filename) {
        try {
            System.out.println("Save History");
            File hist = new File(filename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(hist));
            Enumeration e = RHISTORY.elements();
            int i = 0;
            while (e.hasMoreElements()) {
                writer.write(e.nextElement().toString() + "#\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
        // new ErrorMsg(e);
        }
    }

    /**
     * Get R_HOME.
     * 
     * @return R_HOME path
     */
    public static String getRHome() {
        REXP x = re.eval("R.home()");
        if (x != null && x.asStringArray() != null) {
            return x.asStringArray()[0];
        }
        return "";
    }

    /**
     * Get R_LIBS.
     * 
     * @return R_LIBS paths
     */
    public static String[] getRLibs() {
        REXP x = re.eval(".libPaths()");
        if (x != null && x.asStringArray() != null) {
            return x.asStringArray();
        }
        return null;
    }

    /**
     * Get R prompt
     * 
     * @return prompt
     */
    public static String getRPrompt() {
        REXP x = re.eval("try(as.character(options('prompt')),silent=TRUE)");
        if (x != null && x.asStringArray() != null) {
            return x.asStringArray() == null ? "> " : x.asStringArray()[0];
        }
        return "> ";
    }

    /**
     * Get R continue
     * 
     * @return continue
     */
    public static String getRContinue() {
        REXP x = re.eval("try(as.character(options('continue')),silent=TRUE)");
        if (x != null && x.asStringArray() != null) {
            return x.asStringArray() == null ? "> " : x.asStringArray()[0];
        }
        return "> ";
    }

    /**
     * Get R_DEFAULT_PACKAGES.
     * 
     * @return default packages
     */
    public static String getCurrentPackages() {
        REXP x = re.eval(".packages(TRUE)");
        if (x != null && x.asStringArray() != null) {
            String p = "";
            for (int i = 0; i < x.asStringArray().length - 1; i++) {
                p += x.asStringArray()[i] + ",";
            }
            return p += x.asStringArray()[x.asStringArray().length - 1];
        }
        return null;
    }

    /**
     * Get current installed packages
     * 
     * @return installed packages
     */
    public static String[] getDefaultPackages() {
        REXP x = re.eval("getOption(\"defaultPackages\")");
        if (x != null && x.asStringArray() != null) {
            return x.asStringArray();
        }
        return new String[]{};
    }

    /**
     * Exits TIMPGUI, but not before asked the user if he wants to save his
     * workspace.
     *
     * @return users's answer (yes/no/cancel)
     */
    public String exit() {
        int exit = JOptionPane.showConfirmDialog(null, "Save workspace?",
                "Close TIMPGUI", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (exit == 0) {
            writeHistory();

            return "y\n";
        } else if (exit == 1) {

            return "n\n";
        } else {
            return "c\n";
        }
    }

    /**
     * Write the commands of current session to .Rhistory.
     */
    public void writeHistory() {
        File hist = null;
        try {
            hist = new File(System.getProperty("user.home") + File.separator + ".TIMPhistory");
            BufferedWriter writer = new BufferedWriter(new FileWriter(hist));
            Enumeration e = RHISTORY.elements();
            while (e.hasMoreElements()) {
                writer.write(e.nextElement().toString() + "#\n");
                writer.flush();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    /**
	 * Execute a command and add it into history.
	 * 
	 * @param cmd
	 *            command for execution
	 * @param addToHist
	 *            indicates wether the command should be added to history or not
	 */
	public static void execute(String cmd, boolean addToHist) {
		if (!RController.STARTED)
			return;
		if (addToHist && RController.RHISTORY.size() == 0)
			RController.RHISTORY.add(cmd);
		else if (addToHist && cmd.trim().length() > 0
				&& RController.RHISTORY.size() > 0
				&& !RController.RHISTORY.lastElement().equals(cmd.trim()))
			RController.RHISTORY.add(cmd);

		String[] cmdArray = cmd.split("\n");

		String c = null;
		for (int i = 0; i < cmdArray.length; i++) {
			c = cmdArray[i];
                    if (RController.isSupported(c)) {
				RController.rSync.triggerNotification(c.trim());
                    }
		}
	}
        
        public static void execute(String cmd) {
            execute(cmd,false);
        }
        
        //Start of method to get data from R.
    
    public static double getDouble(String cmd) {
        return re.eval(cmd).asDouble();
    }

    public static double[] getDoubleArray(String cmd) {
        return re.eval(cmd).asDoubleArray();
    }

    public static double[][] getDoubleMatrix(String cmd) {
        return re.eval(cmd).asDoubleMatrix();
    }
    
    public static int getInt(String cmd) {
        return re.eval(cmd).asInt();
    }
    
    public static int[] getIntArray(String cmd) {
        return re.eval(cmd).asIntArray();
    }
    
    public static List<?> getRList(String cmd) {
        // This function is not yet working.
        List ls = null;
        RList rl = re.eval(cmd).asList();
        String[] keys = rl.keys();
        //int length = keys.length;
        // These lines produces warning
        //ls.add(keys);
        //ls.add(rl.body.getType());
        //ls.add(rl.head.getType());
        return ls;
    }
    
    public static RBool getRBool(String cmd) {
        return re.eval(cmd).asBool();
    }
    
    public static String getRTestBool(String cmd) {
        return re.eval(cmd).asString();
    }
    
    public static Boolean getBoolean(String cmd) {
        Boolean value = null;
        RBool rb = getRBool(cmd);
        if (rb.isTRUE()) {
            value = true;
        } else if (rb.isFALSE()) {
            value = false;
        } else if (rb.isNA()) {
            //Do nothing return null
        }
        return value;
    }
   
    
    public static RVector getRVector(String cmd) {
        return re.eval(cmd).asVector();
    }
    
    public static void setDouble(String cmd) {
    }

    public static void setDoubleArray(double[] xx, String n) {
       long inr = re.rniPutDoubleArray(xx);
        re.rniAssign(n, inr, 0);
    }

    public static void setDoubleMatrix(String cmd) {
        
    }
    public static void sendDatasetTimp(DatasetTimp dd) {
     
        long psisim = re.rniPutDoubleArray(dd.GetPsisim());
        re.rniAssign("psisim", psisim, 0);
        REXP xx;
        
            
        xx = re.eval("psisim");
        
        
        long x = re.rniPutDoubleArray(dd.GetX());
        re.rniAssign("x", x, 0);
        xx = re.eval("x");
        
        
        long x2 = re.rniPutDoubleArray(dd.GetX2());
        re.rniAssign("x2", x2, 0);
        xx = re.eval("x2");
        
        long nl = re.rniPutIntArray(dd.GetNl());
        re.rniAssign("nl", nl, 0);
        xx = re.eval("nl");
        
        long nt = re.rniPutIntArray(dd.GetNt());
        re.rniAssign("nt", nt, 0);
        xx = re.eval("nt");
        double[] in;
        
        long intenceIm = re.rniPutDoubleArray(dd.GetIntenceIm());
        re.rniAssign("intenceIm", intenceIm, 0);
       
        re.eval("psisim <- as.matrix(psisim)");
        re.eval("if(is.null(intenceIm))  intenceIm <- matrix(1,1,1)");
        re.eval("intenceIm <- as.matrix(intenceIm)");
       
        
        re.eval("dim(psisim) <- c(nt, nl)");
        
        execute(dd.GetDatasetName() + "<- dat(psi.df = psisim, x = x, nt = nt, x2 = x2, nl = nl, " +
                "inten = intenceIm)");
        
    }
    
     public static void getDatasetTimp(String ddname) {
        
         long x = re.rniParse("slot(" + ddname + ",'x' )", 1);
	 double[] jx = re.rniGetDoubleArray(x);
        
         long x2 = re.rniParse("slot(" + ddname + ",'x2' )", 1);
	 double[] jx2 = re.rniGetDoubleArray(x2);
         
         long nt = re.rniParse("slot(" + ddname + ",'nt' )", 1);
	 int[] jnt = re.rniGetIntArray(nt);
         
         long nl = re.rniParse("slot(" + ddname + ",'nl' )", 1);
	 int[] jnl = re.rniGetIntArray(nl);
       
         long psisim = re.rniParse("slot(" + ddname + ",'psisim' )", 1);
	 double[] jpsisim = re.rniGetDoubleArray(psisim);
       
         long intenceim = re.rniParse("slot(" + ddname + ",'intenceim' )", 1);
	 double[] jintenceim = re.rniGetDoubleArray(intenceim);
         
         long datasetname = re.rniParse("slot(" + ddname + ",'datasetname' )", 1);
	 String jdatasetname = re.rniGetString(datasetname);
         
         DatasetTimp dd = new DatasetTimp(jx, jx2, jnt, jnl, jpsisim, jintenceim, jdatasetname);
         
         test1(ddname);
        
    }
     public static void test1(String nn) {
        
        output.append("Test if dataset " + nn + " exists in R ...");
        RController.execute("exists(\""+nn+"\")");
        output.append("\n end test for dataset.");
        io.select();
     }
     
    public static void test() {
        InputOutput io1 = IOProvider.getDefault().getIO("Test Output", false);
        OutputWriter testOutput = io1.getOut();
        io1.select(); //ensures it's visible to the user
        
        /* High-level API - do not use RNI methods unless there is no other way
        to accomplish what you want */
        try {
            REXP x;
            re.eval("data(iris)", false);
            
            testOutput.println(x = re.eval("iris"));
            // generic vectors are RVector to accomodate names
            RVector v = x.asVector();
            if (v.getNames() != null) {
                testOutput.println("has names:");
                for (Enumeration e = v.getNames().elements(); e.hasMoreElements();) {
                    testOutput.println(e.nextElement());
                }
            }
            // for compatibility with Rserve we allow casting of vectors to lists
            RList vl = x.asList();
            String[] k = vl.keys();
            if (k != null) {
                testOutput.println("and once again from the list:");
                int i = 0;
                while (i < k.length) {
                    testOutput.println(k[i++]);
                }
            }

            // get boolean array
            testOutput.println(x = re.eval("iris[[1]]>mean(iris[[1]])"));
            // R knows about TRUE/FALSE/NA, so we cannot use boolean[] this way
            // instead, we use int[] which is more convenient (and what R uses internally anyway)
            int[] bi = x.asIntArray();
            {
                int i = 0;
                while (i < bi.length) {
                    testOutput.print(bi[i] == 0 ? "F " : (bi[i] == 1 ? "T " : "NA "));
                    i++;
                }
                testOutput.println("");                                             
            }

            // push a boolean array
            boolean by[] = {true, false, false};
            re.assign("bool", by);
            testOutput.println(x = re.eval("bool"));
            // asBool returns the first element of the array as RBool
            // (mostly useful for boolean arrays of the length 1). is should return true
            testOutput.println("isTRUE? " + x.asBool().isTRUE());

            // now for a real dotted-pair list:
            testOutput.println(x = re.eval("pairlist(a=1,b='foo',c=1:5)"));
            RList l = x.asList();
            if (l != null) {
                int i = 0;
                String[] a = l.keys();
                testOutput.println("Keys:");
                while (i < a.length) {
                    testOutput.println(a[i++]);
                }
                testOutput.println("Contents:");
                i = 0;
                while (i < a.length) {
                    testOutput.println(l.at(i++));
                }
            }
            testOutput.println(re.eval("sqrt(36)"));
        } catch (Exception e) {
            testOutput.println("EX:" + e);
            e.printStackTrace();
        }

        // Part 2 - low-level API - for illustration purposes only!
        //System.exit(0);

        // simple assignment like a<-"hello" (env=0 means use R_GlobalEnv)
        long xp1 = re.rniPutString("hello");
        re.rniAssign("a", xp1, 0);

        // Example: how to create a named list or data.frame
        double da[] = {1.2, 2.3, 4.5};
        double db[] = {1.4, 2.6, 4.2};
        long xp3 = re.rniPutDoubleArray(da);
        long xp4 = re.rniPutDoubleArray(db);

        // now build a list (generic vector is how that's called in R)
        long la[] = {xp3, xp4};
        long xp5 = re.rniPutVector(la);

        // now let's add names
        String sa[] = {"a", "b"};
        long xp2 = re.rniPutStringArray(sa);
        re.rniSetAttr(xp5, "names", xp2);

        // ok, we have a proper list now
        // we could use assign and then eval "b<-data.frame(b)", but for now let's build it by hand:       
        String rn[] = {"1", "2", "3"};
        long xp7 = re.rniPutStringArray(rn);
        re.rniSetAttr(xp5, "row.names", xp7);

        long xp6 = re.rniPutString("data.frame");
        re.rniSetAttr(xp5, "class", xp6);

        // assign the whole thing to the "b" variable
        re.rniAssign("b", xp5, 0);

        {
            testOutput.println("Parsing");
            long e = re.rniParse("data(iris)", 1);
            testOutput.println("Result = " + e + ", running eval");
            long r = re.rniEval(e, 0);
            testOutput.println("Result = " + r + ", building REXP");
            REXP x = new REXP(re, r);
            testOutput.println("REXP result = " + x);
        }
        {
            testOutput.println("Parsing");
            long e = re.rniParse("iris", 1);
            testOutput.println("Result = " + e + ", running eval");
            long r = re.rniEval(e, 0);
            testOutput.println("Result = " + r + ", building REXP");
            REXP x = new REXP(re, r);
            testOutput.println("REXP result = " + x);
        }
        {
            testOutput.println("Parsing");
            long e = re.rniParse("names(iris)", 1);
            testOutput.println("Result = " + e + ", running eval");
            long r = re.rniEval(e, 0);
            testOutput.println("Result = " + r + ", building REXP");
            REXP x = new REXP(re, r);
            testOutput.println("REXP result = " + x);
            String s[] = x.asStringArray();
            if (s != null) {
                int i = 0;
                while (i < s.length) {
                    testOutput.println("[" + i + "] \"" + s[i] + "\"");
                    i++;
                }
            }
        }
        {
            testOutput.println("Parsing");
            long e = re.rniParse("rnorm(10)", 1);
            testOutput.println("Result = " + e + ", running eval");
            long r = re.rniEval(e, 0);
            testOutput.println("Result = " + r + ", building REXP");
            REXP x = new REXP(re, r);
            testOutput.println("REXP result = " + x);
            double d[] = x.asDoubleArray();
            if (d != null) {
                int i = 0;
                while (i < d.length) {
                    testOutput.print(((i == 0) ? "" : ", ") + d[i]);
                    i++;
                }
                testOutput.println("");
            }
            testOutput.println("");
        }
        {
            REXP x = re.eval("1:10");
            testOutput.println("REXP result = " + x);
            int d[] = x.asIntArray();
            if (d != null) {
                int i = 0;
                while (i < d.length) {
                    testOutput.print(((i == 0) ? "" : ", ") + d[i]);
                    i++;
                }
                testOutput.println("");
            }
        }

        re.eval("print(1:10/3)");
        testOutput.println(re.eval("testVar<-6"));
        testOutput.println(re.eval("unlist(ls())").asString());
    }

}
