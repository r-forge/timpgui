/*
 *  Puzzle-GIS - OpenSource mapping program
 *  http://docs.codehaus.org/display/PUZZLEGIS
 *  Copyright (C) 2007 Puzzle-GIS
 *  
 *  GPLv3 + Classpath exception
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.vu.nat.tgmprojectsupport;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

//import org.geotools.map.MapContext;
//import org.geotools.map.MapLayer;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

//import org.puzzle.core.project.source.GISLayerSource;
//import org.puzzle.core.project.filetype.GISContextDataObject;
//import org.puzzle.core.project.filetype.GISSourceDataObject;
//import org.puzzle.core.project.source.GISSource;
//import org.puzzle.core.project.source.GISSourceInfo;
//import org.puzzle.core.view.MapView;
//import org.puzzle.core.view.ViewService;
/**
 * This class is the project. the project allow to manage the
 * datas. These datas are not necessary georeferenced datas.
 * It provides support for managing :
 * <ul>
 *  <li>maps (saving {@code MapContext}s...),</li>
 *  <li>sources (adding, removing, organizing),</li>
 *  <li>documents (reports...).</li>
 * </ul>
 * 
 * @author  Johann Sorel (Puzzle-GIS)
 * @author  Thomas Bonavia (comments)
 * 
 * @see     org.netbeans.api.project.Project
 */
public class TGProject implements Project {

    /** The name of the folder containing datasets files. */
    public static final String DATASETS_DIR = "datasets";
    /** The name of the folder containing preprocessing files. */
    public static final String PREPROCESSING_DIR = "preprocessing";
    /** The name of the folder containing models files. */
    public static final String MODELS_DIR = "models";
    /** The name of the folder containing fitting options files. */
    public static final String OPTIONS_DIR = "options";
    /** The name of the folder containing output files. */
    public static final String RESULTS_DIR = "results";
    private final FileObject projectDir;
    private final ProjectState state;
    private final LogicalViewProvider logicalView = new TGLogicalView(this);
    private final InstanceContent lookUpContent = new InstanceContent();
    private final Lookup lookUp = new AbstractLookup(lookUpContent);

    /**
     * Constructor.
     * This constructor initializes the project by adding to its {@code Lookup}
     * <ul>
     *  <li>The project ({@code this}),</li>
     *  <li>The project's state,</li>
     *  <li>The project personal {@code ActionProvider},</li>
     *  <li>The project properties,</li>
     *  <li>The project personal {@code ProjectInformation},</li>
     *  <li>The {@link TGLogicalView} associated to the project.</li>
     * </ul>
     * @param root  The folder containing the project.
     * @param state The state of the project. This state describes if the
     *              the project needs or not to be saved.
     */
    public TGProject(FileObject root, ProjectState state) {
        this.projectDir = root;
        this.state = state;

        lookUpContent.add(this);
        lookUpContent.add(state);
        lookUpContent.add(new ActionProviderImpl());
        lookUpContent.add(loadProperties());
        lookUpContent.add(new Info());
        lookUpContent.add(logicalView);

    }

    /**
     * Get the project folder.
     * @return  A {@code FileObject} representing the project folder.
     */
    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    /**
     * Get the {@code Lookup} of the project.
     * @return  The {@code Lookup} of the project.
     */
    @Override
    public Lookup getLookup() {
        return lookUp;
    }

    /**
     * Get the datasets folder.
     * @param   create    Create the folder if does not exists ?
     * @return  The {@code FileObject} representing the sources folder.
     */
    public FileObject getDatasetsFolder(boolean create) {
        FileObject result = projectDir.getFileObject(DATASETS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(DATASETS_DIR);
            } catch (IOException ioe) {
                Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                        "Unable to create folder " + DATASETS_DIR, ioe);
            }
        }
        return result;
    }

    /**
     * Get the sources folder.
     * @param   create    Create the folder if does not exists ?
     * @return  The {@code FileObject} representing the sources folder.
     */
    public FileObject getPreprocessingFolder(boolean create) {
        FileObject result = projectDir.getFileObject(PREPROCESSING_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(PREPROCESSING_DIR);
            } catch (IOException ioe) {
                Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                        "Unable to create folder " + PREPROCESSING_DIR, ioe);
            }
        }
        return result;
    }

    /**
     * Get the models folder.
     * @param   create    Create the folder if does not exists ?
     * @return  The {@code FileObject} representing the maps folder.
     */
    public FileObject getModelsFolder(boolean create) {
        FileObject result = projectDir.getFileObject(MODELS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(MODELS_DIR);
            } catch (IOException ioe) {
                Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                        "Unable to create folder " + MODELS_DIR, ioe);
            }
        }
        return result;
    }

        /**
     * Get the fittings options folder.
     * @param   create    Create the folder if does not exists ?
     * @return  The {@code FileObject} representing the sources folder.
     */
    public FileObject getOptionsFolder(boolean create) {
        FileObject result = projectDir.getFileObject(OPTIONS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(OPTIONS_DIR);
            } catch (IOException ioe) {
                Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                        "Unable to create folder " + OPTIONS_DIR, ioe);
            }
        }
        return result;
    }

    /**
     * Get the documents folder.
     * @param   create    Create the folder if does not exists ?
     * @return  The {@code FileObject} representing the documents folder.
     */
    public FileObject getResultsFolder(boolean create) {
        FileObject result = projectDir.getFileObject(RESULTS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(RESULTS_DIR);
            } catch (IOException ioe) {
                Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                        "Unable to create folder " + RESULTS_DIR, ioe);
            }
        }
        return result;
    }

    /**
     * Load project properties.
     * @return  A {@code Properties} containing project properties.
     */
    private Properties loadProperties() {
        FileObject fob = projectDir.getFileObject(TGProjectFactory.PROJECT_DIR +
                "/" + TGProjectFactory.PROJECT_PROPFILE);
        Properties properties = new NotifyProperties(state);
        if (fob != null) {
            try {
                properties.load(fob.getInputStream());
            } catch (Exception e) {
                Logger.getLogger(TGProject.class.getName()).log(Level.WARNING,
                        "Unable to load project properties", e);
            }
        }
        return properties;
    }

    ////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASSES /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private static class NotifyProperties extends Properties {

        private final ProjectState state;

        NotifyProperties(ProjectState state) {
            this.state = state;
        }

        @Override
        public Object put(Object key, Object val) {
            Object result = super.put(key, val);
            if (((result == null) != (val == null)) || (result != null &&
                    val != null && !val.equals(result))) {
                state.markModified();
            }
            return result;
        }
    }

    private final class ActionProviderImpl implements ActionProvider {

        @Override
        public String[] getSupportedActions() {
            return new String[0];
        }

        @Override
        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            //do nothing
        }

        @Override
        public boolean isActionEnabled(String string, Lookup lookup) throws IllegalArgumentException {
            return false;
        }
    }

    /* Implementation of project system's ProjectInformation class */
    private final class Info implements ProjectInformation {

        private final String ICON_PATH = "nl/vu/nat/tgmprojectsupport/boussole.png";
        private final ImageIcon ICON = new ImageIcon(ImageUtilities.loadImage(ICON_PATH, true));
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);

        @Override
        public String getName() {
            return projectDir.getName();
        }

        @Override
        public String getDisplayName() {
            return getProjectDirectory().getName();
        }

        public ImageIcon getImageIcon() {
            return ICON;
        }

        @Override
        public Icon getIcon() {
            return ICON;
        }

        @Override
        public Project getProject() {
            return TGProject.this;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            support.removePropertyChangeListener(listener);
        }
    }
}
