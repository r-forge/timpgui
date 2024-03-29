package org.timpgui.gui.scheme;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.gui.scheme.palette.PaletteSupport;


/**
 * Top component which displays something.
 */
final class TGSceneTopComponent extends TopComponent {
    
    private JComponent myView;
    
    private static TGSceneTopComponent instance;
    /** path to the icon used by the component and its open action */
    //    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    
    private static final String PREFERRED_ID = "TGSceneTopComponent";
    
    private TGSceneTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(TGSceneTopComponent.class, "CTL_TGSceneTopComponent"));
        setToolTipText(NbBundle.getMessage(TGSceneTopComponent.class, "HINT_TGSceneTopComponent"));
        //        setIcon(Utilities.loadImage(ICON_PATH, true));
        
        GraphSceneImpl scene = new GraphSceneImpl();
        myView = scene.createView();        
        shapePane.setViewportView(myView);
        add(scene.createSatelliteView(), BorderLayout.WEST);

        setFocusable (true);
        setFocusTraversalKeysEnabled (false);
        
        associateLookup( Lookups.fixed( new Object[] { PaletteSupport.createPalette() } ) );
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shapePane = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());
        add(shapePane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane shapePane;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized TGSceneTopComponent getDefault() {
        if (instance == null) {
            instance = new TGSceneTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the TGSceneTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TGSceneTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(TGSceneTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof TGSceneTopComponent) {
            return (TGSceneTopComponent)win;
        }
        Logger.getLogger(TGSceneTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    public void componentOpened() {
        // TODO add custom code on component opening
    }
    
    public void componentClosed() {
        // TODO add custom code on component closing
    }
    
    /** replaces this in object stream */
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return TGSceneTopComponent.getDefault();
        }
    }
    
}
