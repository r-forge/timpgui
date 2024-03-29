/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling;

import java.awt.BorderLayout;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaLayout;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.gtafilesupport.GtaDataObject;
import org.glotaran.core.ui.visualmodelling.palette.PaletteSupport;
import org.glotaran.core.ui.visualmodelling.view.GlotaranGraphScene;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectState;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.netbeans.api.visual.model.ObjectSceneEventType;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.openide.explorer.propertysheet.PropertyPanel;
import org.openide.nodes.Node;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.windows.CloneableTopComponent;

import org.openide.nodes.NodeOperation;

/**
 * Top component which displays something.
 */
final public class VisualModellingTopComponent extends CloneableTopComponent implements ObjectSceneListener {

    private static VisualModellingTopComponent instance;
    private static final long serialVersionUID = 1;
    private InstanceContent content;
    private static final int DEFAULT_COMPONENT_HEIGHT = 240;
    private static final int DEFAULT_COMPONENT_WIDTH = 180;
    private static final String PREFERRED_ID = "VisualModellingTopComponent";
    private GtaDataObject dobj;

    public VisualModellingTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(VisualModellingTopComponent.class, "CTL_VisualModellingTopComponent"));
        setToolTipText(NbBundle.getMessage(VisualModellingTopComponent.class, "HINT_VisualModellingTopComponent"));
        // setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        GlotaranGraphScene scene = new GlotaranGraphScene();
        JComponent myView = scene.createView();
        visualDesignScrollPane.setViewportView(myView);
        add(scene.createSatelliteView(), BorderLayout.WEST);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public VisualModellingTopComponent(GtaDataObject dobj) {
        initComponents();
        this.dobj = dobj;
        setName(NbBundle.getMessage(VisualModellingTopComponent.class, "CTL_VisualModellingTopComponent"));
        setToolTipText(NbBundle.getMessage(VisualModellingTopComponent.class, "HINT_VisualModellingTopComponent"));
        setIcon(dobj.getNodeDelegate().getIcon(0));
        GlotaranGraphScene scene = new GlotaranGraphScene(dobj);
        JComponent myView = scene.createView();
        visualDesignScrollPane.setViewportView(myView);
        add(scene.createSatelliteView(), BorderLayout.WEST);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        scene.addObjectSceneListener(this,
                ObjectSceneEventType.OBJECT_ADDED,
                ObjectSceneEventType.OBJECT_REMOVED);
        associateLookup(Lookups.fixed(new Object[]{PaletteSupport.createPalette()}));
        TopComponent t = WindowManager.getDefault().findTopComponent("properties"); // NOI18N
        if (null != t) {
            t.requestVisible();
            t.open();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualDesignScrollPane = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());
        add(visualDesignScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane visualDesignScrollPane;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     * @return
     */
    public static synchronized VisualModellingTopComponent getDefault() {
        if (instance == null) {
            instance = new VisualModellingTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the VisualModellingTopComponent instance. Never call {@link #getDefault} directly!
     * @return
     */
    public static synchronized VisualModellingTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(VisualModellingTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof VisualModellingTopComponent) {
            return (VisualModellingTopComponent) win;
        }
        Logger.getLogger(VisualModellingTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        // TODO changed to PERSISTENCE_ONLY_OPENED once componentOpened() and
        // componentClosed() are implemented.
        return TopComponent.PERSISTENCE_NEVER;//  PERSISTENCE_ONLY_OPENED;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        VisualModellingTopComponent singleton = VisualModellingTopComponent.getDefault();
        singleton.readPropertiesImpl(p);
        return singleton;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public void objectAdded(ObjectSceneEvent event, Object addedObject) {
        if (addedObject instanceof GtaDatasetContainer) {
            GtaDatasetContainer container = (GtaDatasetContainer) addedObject;
            if (container.getLayout() == null) {
                container.setLayout(new GtaLayout());
                container.getLayout().setHeight(DEFAULT_COMPONENT_HEIGHT);
                container.getLayout().setWidth(DEFAULT_COMPONENT_WIDTH);
            }

            if (dobj.getProgectScheme().getDatasetContainer() != null) {
                dobj.getProgectScheme().getDatasetContainer().add(container);
            } else {
                CoreErrorMessages.somethingStrange();
            }
        }
        if (addedObject instanceof GtaModelReference) {
            GtaModelReference container = (GtaModelReference) addedObject;
            if (container.getLayout() == null) {
                container.setLayout(new GtaLayout());
                container.getLayout().setHeight(DEFAULT_COMPONENT_HEIGHT);
                container.getLayout().setWidth(DEFAULT_COMPONENT_WIDTH);
            }

            if (dobj.getProgectScheme().getDatasetContainer() != null) {
                dobj.getProgectScheme().getModel().add(container);
            } else {
                CoreErrorMessages.somethingStrange();
            }
        }
        if (addedObject instanceof GtaOutput) {
            GtaOutput container = (GtaOutput) addedObject;
            if (container.getLayout() == null) {
                container.setLayout(new GtaLayout());
                container.getLayout().setHeight(DEFAULT_COMPONENT_HEIGHT);
                container.getLayout().setWidth(DEFAULT_COMPONENT_WIDTH);
            }
            if (dobj.getProgectScheme().getOutput() != null) {
                dobj.getProgectScheme().getOutput().add(container);
            } else {
                CoreErrorMessages.somethingStrange();
            }
        }
        if (addedObject instanceof GtaConnection) {
            if (!dobj.getProgectScheme().getConnection().contains((GtaConnection) addedObject)) {
                dobj.getProgectScheme().getConnection().add((GtaConnection) addedObject);
            }
        }
        dobj.getProgectScheme().setNodeCounter(String.valueOf(((GlotaranGraphScene) event.getObjectScene()).getNodeCount()));
        dobj.getProgectScheme().setEdgeCounter(String.valueOf(((GlotaranGraphScene) event.getObjectScene()).getEdgeCount()));
        dobj.setModified(true);
    }

    public void objectRemoved(ObjectSceneEvent event, Object removedObject) {
        if (removedObject instanceof GtaDatasetContainer) {
            GtaDatasetContainer container = (GtaDatasetContainer) removedObject;
            dobj.getProgectScheme().getDatasetContainer().remove(container);
            for (int i = 0; i < dobj.getProgectScheme().getConnection().size(); i++) {
                if (dobj.getProgectScheme().getConnection().get(i).getTargetID().equalsIgnoreCase(container.getId())) {
                    dobj.getProgectScheme().getConnection().remove(i);
                    i--;
                }
            }
        }
        if (removedObject instanceof GtaModelReference) {
            GtaModelReference container = (GtaModelReference) removedObject;
            dobj.getProgectScheme().getModel().remove(container);
            for (int i = 0; i < dobj.getProgectScheme().getConnection().size(); i++) {
                if (dobj.getProgectScheme().getConnection().get(i).getTargetID().equalsIgnoreCase(container.getId())) {
                    dobj.getProgectScheme().getConnection().remove(i);
                    i--;
                }
            }
        }
        if (removedObject instanceof GtaOutput) {
            GtaOutput output = (GtaOutput) removedObject;
            dobj.getProgectScheme().getOutput().remove(output);
        }
        if (removedObject instanceof GtaConnection) {
        }
        dobj.setModified(true);
    }

    public void objectStateChanged(ObjectSceneEvent event, Object changedObject, ObjectState previousState, ObjectState newState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void selectionChanged(ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void highlightingChanged(ObjectSceneEvent event, Set<Object> previousHighlighting, Set<Object> newHighlighting) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void hoverChanged(ObjectSceneEvent event, Object previousHoveredObject, Object newHoveredObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void focusChanged(ObjectSceneEvent event, Object previousFocusedObject, Object newFocusedObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
