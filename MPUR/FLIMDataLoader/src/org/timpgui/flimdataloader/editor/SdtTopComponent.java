/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader.editor;

import java.io.Serializable;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final public class SdtTopComponent extends CloneableTopComponent  {

    private static SdtTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SdtTopComponent";

    public SdtTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SdtTopComponent.class, "CTL_SdtTopComponent"));
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized SdtTopComponent getDefault() {
        if (instance == null) {
            instance = new SdtTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the SdtTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized SdtTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SdtTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SdtTopComponent) {
            return (SdtTopComponent) win;
        }
        Logger.getLogger(SdtTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return SdtTopComponent.getDefault();
        }
    }
}
