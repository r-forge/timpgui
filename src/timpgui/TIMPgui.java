/*
 * TIMPgui.java
 *
 * Created on March 14, 2007, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package timpgui;

import Jama.Matrix;
import java.io.*;
import java.awt.Frame;
import java.awt.FileDialog;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYZDataset;
import org.rosuda.JGR.util.ErrorMsg;

// Import JRI packages
import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.REXP;
// import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.RList; // not needed yet
import org.rosuda.JRI.RVector; // not needed yet

//Import JGR Pacakges
import org.rosuda.ibase.SVar;
import org.rosuda.util.Global;

// Import JFreeChart packages
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Joris
 */


/**
 * TIMPgui Main Class
 */
public class TIMPgui  {
    
	//JGR_VERSION 1.4-16
	
	/** Version number of JGR */
	public static final String VERSION = "1.4-16";

	/** Title (used in the about box) */
	public static final String TITLE = "TIMPGUI";

	/** Subtitle (used in the about box) */
	public static final String SUBTITLE = "Java GUI for TIMP";

	/** Develtime (used in the about box) */
	public static final String DEVELTIME = "2007";

	/** Organization (used in the about box) */
	public static final String INSTITUTION = "Computational Biophysics, Physics and Astronomy, Vrije Universiteit";

	/** Author JGR (used in the about box) */
	public static final String AUTHOR1 = "Joris Snellenburg";

	/** Author JRI, rJava and JavaGD (used in the about box) */
	public static final String AUTHOR2 = "Kate Mullen";

	/** Website of organization (used in the about box) */
	public static final String WEBSITE = "http://www.nat.vu.nl";

	/** Filename of the splash-image (used in the about box) */
	//public static final String SPLASH = "jgrsplash.jpg";

	/** Main-console window */
	public static TIMPguiFrame TIMPInterface = null; //public static TIMPcommand MAINRCONSOLE = null;

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

	/** Splashscreen */
	//public static org.rosuda.JGR.toolkit.SplashScreen splash;

	/** arguments for Rengine */
	private static String[] rargs = { "--save" };

	/**
	 * Set to <code>true</code> when TIMP is running as the main program and
	 * <code>false</code> if the classes are loaded, but not run via main.
	 */
	private static boolean TIMPmain = false;
        
        public Matrix m;

    private static double[][] test;

	/**
	 * Starting the JGR Application (javaside)
	 */
    public TIMPgui() {
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
		TIMPInterface = new TIMPguiFrame();
                TIMPInterface.setVisible(true);

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
                if (TIMPgui.R != null && TIMPgui.STARTED)
			TIMPgui.R
					.eval("options(width=" + TIMPgui.TIMPInterface.getFontWidth()
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
			Enumeration e = TIMPgui.RHISTORY.elements();
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
        
    public static void outputGraphics() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Joris", 75);
        dataset.setValue("Kate", 20);
        dataset.setValue("Ivo", 5);
        
        // create a chart...
        JFreeChart chart = ChartFactory.createPieChart("TIMPGUI Development Pie Chart",
                dataset, true, // legend?
                true, // tooltips?
                false // URLs?
                );
        // create and display a frame...
        ChartFrame frame = new ChartFrame("Test", chart);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void outputGraphics2() {
        
        // getDataFromR
        Matrix psidf=new Matrix(R.eval("psi_1@psi.df").asDoubleMatrix());
        // psidf.get(1,1);
        double[] x= R.eval("psi_1@x").asDoubleArray();
        double[] x2=R.eval("psi_1@x2").asDoubleArray();
//            // System.out.println(m.get(1,1));
//        XYBlockChartDemo1 demo = new XYBlockChartDemo1("XY Area Chart Demo", matrixToData(m));
//        demo.showJGraph();

        // create a new frame and display the chart in it ...
        XYBlockChartDemo1 demo2 = new XYBlockChartDemo1("JFreeChart: XYBlockChartDemo1", createDatasetXYZ(psidf,x,x2));
        demo2.showGraph();
    }

private static XYZDataset createDatasetBlock() {
        return new XYZDataset() {
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                return 10000;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return item / 100 - 50;
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return item - (item / 100) * 100 - 50;
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                double x = getXValue(series, item);
                double y = getYValue(series, item);
                return Math.sin(Math.sqrt(x * x + y * y) / 5.0);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "sin(sqrt(x + y))";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }        
        };
    }
 
private static XYZDataset createDatasetXYZ(final Matrix psidf, final double[] x, final double[] x2) {
        return new XYZDataset() {
            
            public int getSeriesCount() {
                // System.out.println( psidf.getRowDimension());
                return psidf.getRowDimension();
            }
            public int getItemCount(int series) {
                // return 10000;
                return psidf.getColumnDimension();
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return x[series];
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return x2[item];
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                //double x = getXValue(series, item);
                //double y = getYValue(series, item);
                return psidf.get(series,item);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "sin(sqrt(x + y))";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }        
        };
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
			new TIMPgui();
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