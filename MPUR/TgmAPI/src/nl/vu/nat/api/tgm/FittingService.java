/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.api.tgm;

import java.util.Properties;
import org.openide.filesystems.FileObject;


/**
 *
 * @author joris
 */
public abstract class FittingService {
    
    public static final String PROJECT_FITTING_KEY_PREFIX = "fitting.";
    public static final String PRODCUTION_FITTING_SETTINGS_NAME = "production";
    public abstract FileObject fit(FileObject model, String propertiesName);
    public abstract FileObject fit (FileObject model, Properties fittingSettings);
    public abstract FileObject fit (FileObject model);
    public abstract FileObject fit();
    public abstract String[] getAvailableFittingSettingsNames();
    public abstract Properties getFittingSettings (String name);
    public abstract String getPreferredFittingSettingsNames();
    public abstract String getDisplayName (String settingsName);
}
