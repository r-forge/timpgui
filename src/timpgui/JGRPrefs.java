package timpgui;

//JGR - Java Gui for R, see http://www.rosuda.org/JGR/
//Copyright (C) 2003 - 2005 Markus Helbig
//--- for licensing information see LICENSE file in the original JGR distribution ---

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

// import org.rosuda.JGR.JGR;
// import org.rosuda.JGR.JGRPackageManager;
// import org.rosuda.JGR.RController;

/**
 * JGRPrefs - preferences like fonts colors ....
 * 
 * @author Markus Helbig
 * 
 * RoSuDa 2003 - 2005
 */

public class JGRPrefs {

	/** Preference version */
	public static final int prefsVersion = 0x0102; // version 1.2

	/** Debuglevel */
	public static final int DEBUG = 0;

	/** Is JGR running on a Mac? */
	public static boolean isMac = false;
	
	public static boolean isWindows = false;

	/** DefaultFontName */
	public static String FontName = "Monospaced";

	/** DefaultFontStyle */
	public static int FontStyle = Font.PLAIN;

	/** DefaultFontSize */
	public static int FontSize = 12;

	/** MinimalFontSize */
	public static final int MINFONTSIZE = 18;

	/** DefaultFont */
	public static Font DefaultFont;

	/** DefaultHighLightColor */
	public static Color HighLightColor = Color.green;

	/** DefaultCommandColor */
	public static Color CMDColor = Color.red;

	/** DefaultResultColor */
	public static Color RESULTColor = Color.blue;

	/** DefaultErrorColor */
	public static Color ERRORColor = Color.red;

	/** DefaultBracketHighLightColor */
	public static Color BRACKETHighLight = new Color(200, 255, 255);

	/** DefaultFontSet */
	public static MutableAttributeSet DEFAULTFONT = new SimpleAttributeSet();

	/** DefaultSizeSet */
	public static MutableAttributeSet SIZE = new SimpleAttributeSet();

	/** DefaultCMDSet */
	public static MutableAttributeSet CMD = new SimpleAttributeSet();

	/** DefaultResultSet */
	public static MutableAttributeSet RESULT = new SimpleAttributeSet();

	/** DefaultSet */
	public static MutableAttributeSet NORMAL = new SimpleAttributeSet();

	/** DefaultNumberSet */
	public static MutableAttributeSet NUMBER = new SimpleAttributeSet();

	/** DefaultNumberColor */
	public static Color NUMBERColor = Color.red;

	/** DefaultKEYWORDSet */
	public static MutableAttributeSet KEYWORD = new SimpleAttributeSet();

	/** DefaultKeyWordColor */
	public static Color KEYWORDColor = new Color(0, 0, 140);

	/** DefaultKEYWORDOBJECTSet */
	public static MutableAttributeSet OBJECT = new SimpleAttributeSet();

	/** DefaultKeyWordObjectColor */
	public static Color OBJECTColor = new Color(50, 0, 140);

	/** DefaultCommentSet */
	public static MutableAttributeSet COMMENT = new SimpleAttributeSet();

	/** DefaultCommentColor */
	public static Color COMMENTColor = new Color(0, 120, 0);

	/** DefaultQuoteSet */
	public static MutableAttributeSet QUOTE = new SimpleAttributeSet();

	/** DefaultQuoteColor */
	public static Color QUOTEColor = Color.blue;

	/** MaximalHelpTabs */
	public static int maxHelpTabs = 10;

	/** UseHelpAgent */
	public static boolean useHelpAgent = true;

	/** UseHelpAgent in editor */
	public static boolean useHelpAgentEditor = true;

	/** UseHelpAgent in console */
	public static boolean useHelpAgentConsole = true;

	/** UseEmacsKeyBindings */
	public static boolean useEmacsKeyBindings = false;

	/** ShowHiddenFiles */
	public static boolean showHiddenFiles = false;

	/** Packages which were installed when JGR was running the last time */
	public static String previousPackages = null;

	/** Initial working directory */
	public static String workingDirectory = System.getProperty("user.home");

	/** Tab width */
	public static int tabWidth = 4;

	/**
     * Apply current settings to TIMPGUI.
     */
	public static void apply() {
		JGRPrefs.refresh();
	}

	/**
	 * Initialize settings from .JGRPrefsrc.
	 */
	public static void initialize() {
		readPrefs();
	}

	/**
	 * Refresh current settings.
	 */
	public static void refresh() {
	
	}

	/**
	 * Read prefs form .JGRPrefsrc in user's home directory.
	 */
	public static void readPrefs() {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(System
					.getProperty("user.home")
					+ File.separator + ".JGRprefsrc"));
		} catch (FileNotFoundException e) {
		}

		try {
			if (is != null) {
				Preferences prefs = Preferences
						.userNodeForPackage(TIMPGUI.class);
				try {
					prefs.clear();
				} catch (Exception x) {
				}
				prefs = null;
				Preferences.importPreferences(is);
			}
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
		}

		if (is == null)
			return;

		Preferences prefs = Preferences
				.userNodeForPackage(TIMPGUI.class);
		FontName = prefs.get("FontName", FontName);
		FontSize = prefs.getInt("FontSize", FontSize);
		maxHelpTabs = prefs.getInt("MaxHelpTabs", maxHelpTabs);
		useHelpAgent = prefs.getBoolean("UseHelpAgent", true);
		useHelpAgentConsole = prefs.getBoolean("UseHelpAgentConsole",
				useHelpAgentConsole);
		useHelpAgentEditor = prefs.getBoolean("UseHelpAgentEditor",
				useHelpAgentEditor);
		// it is safe to use emacs bindings on Macs since that's the default in
		// Coca widgets. on win/unix it's not safe since ctrl may be the sc
		// modifier
		useEmacsKeyBindings = prefs.getBoolean("UseEmacsKeyBindings",
				org.rosuda.util.Platform.isMac);
		previousPackages = prefs.get("PreviousPackages", null);
		showHiddenFiles = prefs.getBoolean("ShowHiddenFiles", false);
		workingDirectory = prefs.get("WorkingDirectory", System
				.getProperty("user.home"));
		tabWidth = prefs.getInt("tabWidth", 4);
	}

	/**
	 * Save preferences to .JGRPrefsrc.
	 * 
	 * @param writeLibs
	 *            R_LIBS should only be saved when new packages where installed.
	 */
	public static void writePrefs(boolean writeLibs) {
		Preferences prefs = Preferences
				.userNodeForPackage(TIMPGUI.class);

		try {
			prefs.clear();
		} catch (Exception x) {
		}
		prefs.putInt("PrefsVersion", prefsVersion);
		prefs.put("FontName", FontName); // String
		prefs.putInt("FontSize", FontSize); // int
		prefs.putInt("MaxHelpTabs", maxHelpTabs);
		prefs.putBoolean("UseHelpAgent", useHelpAgent);
		prefs.putBoolean("UseHelpAgentConsole", useHelpAgentConsole);
		prefs.putBoolean("UseHelpAgentEditor", useHelpAgentEditor);
		prefs.putBoolean("UseEmacsKeyBindings", useEmacsKeyBindings);
		prefs.putBoolean("ShowHiddenFiles", showHiddenFiles);
		prefs.put("PreviousPackages", RController.getCurrentPackages()
				+ (JGRPackageManager.remindPackages == null ? ""
						: ("," + JGRPackageManager.remindPackages)));
		prefs.put("WorkingDirectory", workingDirectory);
		prefs.putInt("tabWidth", tabWidth);
		if (JGRPackageManager.defaultPackages != null
				&& JGRPackageManager.defaultPackages.length > 0) {
			String packages = JGRPackageManager.defaultPackages[JGRPackageManager.defaultPackages.length - 1]
					.toString();
			for (int i = JGRPackageManager.defaultPackages.length - 2; i >= 0; i--)
				packages += ", " + JGRPackageManager.defaultPackages[i];
			prefs.put("DefaultPackages", packages);
		}
		if (TIMPGUI.RLIBS != null && TIMPGUI.RLIBS.length > 1) {
			String libpaths = TIMPGUI.RLIBS[0].toString();
			for (int i = 1; i < TIMPGUI.RLIBS.length - 1; i++)
				libpaths += (isWindows ? ";" : ":") + TIMPGUI.RLIBS[i];
			prefs.put("InitialRLibraryPath", libpaths);
		}
		try {
			prefs.exportNode(new FileOutputStream(System
					.getProperty("user.home")
					+ File.separator + ".JGRprefsrc"));
		} catch (IOException e) {
		} catch (BackingStoreException e) {
		}
	}

	/**
	 * Save missing packages if the user likes to be reminded.
	 */
	public static void writeCurrentPackagesWhenExit() {
		readPrefs();
		writePrefs(false);
	}
}
