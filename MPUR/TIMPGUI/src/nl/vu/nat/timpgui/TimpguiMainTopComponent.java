/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.text.DefaultEditorKit;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.Utilities;


/**
 * Top component which displays something.
 */
final class TimpguiMainTopComponent extends TopComponent implements ExplorerManager.Provider {

    private static TimpguiMainTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "nl/vu/nat/timpgui/TIMPGUI_APP_LOGO.PNG";
    private static final String PREFERRED_ID = "TimpguiMainTopComponent";
    private final ExplorerManager manager = new ExplorerManager();

    private TimpguiMainTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(TimpguiMainTopComponent.class, "CTL_TimpguiMainTopComponent"));
        setToolTipText(NbBundle.getMessage(TimpguiMainTopComponent.class, "HINT_TimpguiMainTopComponent"));
        setIcon(Utilities.loadImage(ICON_PATH, true));
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        associateLookup(ExplorerUtils.createLookup(manager, map));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(600, 400));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized TimpguiMainTopComponent getDefault() {
        if (instance == null) {
            instance = new TimpguiMainTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the TimpguiMainTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TimpguiMainTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(TimpguiMainTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof TimpguiMainTopComponent) {
            return (TimpguiMainTopComponent) win;
        }
        Logger.getLogger(TimpguiMainTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
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

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return TimpguiMainTopComponent.getDefault();
        }
    }

}