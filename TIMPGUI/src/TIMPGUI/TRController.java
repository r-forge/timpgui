package TIMPGUI;

import org.rosuda.JRI.REXP;

public class TRController {
    
    	/**
	 * dummy object.
	 */
	public static Object dummy = new Object();

	/**
	 * Get R_HOME.
	 * 
	 * @return R_HOME path
	 */
	public static String getRHome() {
		REXP x = TIMPGUI.R.eval("R.home()");
		if (x != null && x.asStringArray() != null)
			return x.asStringArray()[0];
		return "";
	}

	/**
	 * Get R_LIBS.
	 * 
	 * @return R_LIBS paths
	 */
	public static String[] getRLibs() {
		REXP x = TIMPGUI.R.eval(".libPaths()");
		if (x != null && x.asStringArray() != null)
			return x.asStringArray();
		return null;
	}

	/**
	 * Get R prompt
	 * 
	 * @return prompt
	 */
	public static String getRPrompt() {
		REXP x = TIMPGUI.R.eval("try(as.character(options('prompt')),silent=TRUE)");
		if (x != null && x.asStringArray() != null)
			return x.asStringArray() == null ? "> " : x.asStringArray()[0];
		return "> ";
	}

	/**
	 * Get R continue
	 * 
	 * @return continue
	 */
	public static String getRContinue() {
		REXP x = TIMPGUI.R
				.eval("try(as.character(options('continue')),silent=TRUE)");
		if (x != null && x.asStringArray() != null)
			return x.asStringArray() == null ? "> " : x.asStringArray()[0];
		return "> ";
	}

	/**
	 * Get R_DEFAULT_PACKAGES.
	 * 
	 * @return default packages
	 */
	public static String getCurrentPackages() {
		REXP x = TIMPGUI.R.eval(".packages(TRUE)"); //.allpkg <- (.packages(all=T));.p <- NULL;for (i in 1:length(.allpkg)) .p <- paste(.p,as.character(.allpkg[i]),sep=\",\");substring(.p,2)");
		
		if (x != null && x.asStringArray() != null) {
			String p = "";
			for (int i = 0; i < x.asStringArray().length - 1; i++)
				p += x.asStringArray()[i] + ",";
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
		REXP x = TIMPGUI.R.eval("getOption(\"defaultPackages\")");
		if (x != null && x.asStringArray() != null)
			return x.asStringArray();
		return new String[] {};
	}
}
