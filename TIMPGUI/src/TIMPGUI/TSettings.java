package TIMPGUI;

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

/**
 * TSettings - working directroy etc ....
 */

public class TSettings {
    
        public static TRController rc;

	/** Are we on a Mac? */
	public static boolean isMac = false;
        /** Or Windows? Else we assume GNU/Linux*/
	public static boolean isWindows = false;

	/** Initial working directory */
	public static String workingDirectory = System.getProperty("user.home");


	/**
	 * Load settings from a file ".TIMPsettings" in the user home directory
	 */
	public static void readPrefs() {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(System
					.getProperty("user.home")
					+ File.separator + ".TIMPprefsrc"));
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
		workingDirectory = prefs.get("WorkingDirectory", System
				.getProperty("user.home"));
	}

	/**
	 * Store settings in a file ".TIMPsettings" in the user home directory
	 */
	public static void writePrefs() {
		Preferences prefs = Preferences
				.userNodeForPackage(TIMPGUI.class);

		try {
			prefs.clear();
		} catch (Exception x) {
		}
		prefs.put("WorkingDirectory", workingDirectory);
		try {
			prefs.exportNode(new FileOutputStream(System
					.getProperty("user.home")
					+ File.separator + ".TIMPsettings"));
		} catch (IOException e) { System.out.print("Error: File not found");
		} catch (BackingStoreException e) {System.out.print("Error: File access");
		}
	}

	/**
	 * Save missing packages if the user likes to be reminded.
	 */
	public static void writeCurrentPackagesWhenExit() {
		readPrefs();
		writePrefs();
	}
}
