/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DatasetContainerComponent.java
 *
 * Created on Jul 15, 2009, 10:18:18 AM
 */

package org.glotaran.core.ui.visualmodelling.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetsRootNode;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Index;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 *
 * @author slk230
 */
public class DatasetContainerComponent
        extends JPanel
        implements ExplorerManager.Provider, Lookup.Provider, PropertyChangeListener {

/** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "DatasetsListView";

    private ExplorerManager manager = new ExplorerManager();
    private DatasetSpecificationView datasetView  = new DatasetSpecificationView();
//    private DatasetNodeContainer container = new DatasetNodeContainer();
    private Lookup lookup;
    private GtaDatasetContainer datasetContainer;

    /** Creates new form DatasetContainerComponent */
    public DatasetContainerComponent() {
        initComponents();
        datasetContainer = null;
        jPDatasetsPanel.add(datasetView);
        datasetView.setPreferredSize(jPDatasetsPanel.getSize());
        //datasetView.setPreferredSize(new Dimension(150, 150));
//        ExplorerUtils.createLookup(manager, null)));
//        new ProxyLookup(arg0)
        ActionMap map = this.getActionMap ();
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");
        lookup = ExplorerUtils.createLookup(manager, map);
        manager.setRootContext(new DatasetsRootNode(new Index.ArrayChildren()));
        manager.addPropertyChangeListener(this);

    }

    public DatasetContainerComponent(GtaDatasetContainer myNode) {
        initComponents();
        jPDatasetsPanel.add(datasetView);
        datasetView.setPreferredSize(jPDatasetsPanel.getSize());
        datasetContainer = myNode;
        ActionMap map = this.getActionMap ();
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");
        lookup = ExplorerUtils.createLookup(manager, map);
        manager.setRootContext(new DatasetsRootNode(new Index.ArrayChildren(), this));
        manager.addPropertyChangeListener(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jBRefreshModel = new javax.swing.JButton();
        jBSaveModel = new javax.swing.JButton();
        jBRemove = new javax.swing.JButton();
        jPDatasetsPanel = new javax.swing.JPanel();
        modelDiffsPanel = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(180, 240));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(180, 28));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jBRefreshModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/glotaran/core/main/resources/Refresh.png"))); // NOI18N
        jBRefreshModel.setText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBRefreshModel.text")); // NOI18N
        jBRefreshModel.setToolTipText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBRefreshModel.toolTipText")); // NOI18N
        jBRefreshModel.setFocusable(false);
        jBRefreshModel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBRefreshModel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBRefreshModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRefreshModelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel1.add(jBRefreshModel, gridBagConstraints);

        jBSaveModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/glotaran/core/main/resources/Save.png"))); // NOI18N
        jBSaveModel.setText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBSaveModel.text")); // NOI18N
        jBSaveModel.setToolTipText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBSaveModel.toolTipText")); // NOI18N
        jBSaveModel.setFocusable(false);
        jBSaveModel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBSaveModel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBSaveModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSaveModelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel1.add(jBSaveModel, gridBagConstraints);

        jBRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/glotaran/core/main/resources/Delete-icon-16.png"))); // NOI18N
        jBRemove.setText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBRemove.text")); // NOI18N
        jBRemove.setToolTipText(org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jBRemove.toolTipText")); // NOI18N
        jBRemove.setFocusable(false);
        jBRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel1.add(jBRemove, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel1, gridBagConstraints);

        jPDatasetsPanel.setBackground(new java.awt.Color(255, 255, 236));
        jPDatasetsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.jPDatasetsPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
        jPDatasetsPanel.setPreferredSize(new java.awt.Dimension(180, 126));
        jPDatasetsPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.7;
        add(jPDatasetsPanel, gridBagConstraints);

        modelDiffsPanel.setBackground(new java.awt.Color(236, 255, 255));
        modelDiffsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(DatasetContainerComponent.class, "DatasetContainerComponent.modelDiffsPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
        modelDiffsPanel.setPreferredSize(new java.awt.Dimension(180, 86));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        add(modelDiffsPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jBRefreshModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRefreshModelActionPerformed
        //manager.getRootContext().removePropertyChangeListener(this);
        //manager.getRootContext().getChildren().remove(manager.getRootContext().getChildren().getNodes());
        //manager.getRootContext().addPropertyChangeListener(this);
//        fillInModel();
//        model.setModified(false);
}//GEN-LAST:event_jBRefreshModelActionPerformed

    private void jBSaveModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSaveModelActionPerformed
//        if (model.getCookie(SaveCookie.class)!=null){
//            try {
//                model.getCookie(SaveCookie.class).save();
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
}//GEN-LAST:event_jBSaveModelActionPerformed

    private void jBRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoveActionPerformed
//        container.remove(manager.getSelectedNodes());
//        model.setModified(true);
}//GEN-LAST:event_jBRemoveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBRefreshModel;
    private javax.swing.JButton jBRemove;
    private javax.swing.JButton jBSaveModel;
    private javax.swing.JPanel jPDatasetsPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel modelDiffsPanel;
    // End of variables declaration//GEN-END:variables

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager &&
                ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            WindowManager.getDefault().getRegistry().getActivated().setActivatedNodes(manager.getSelectedNodes());
        }
        if (evt.getPropertyName().equals("datasetAdded")){
            datasetContainer.getDatasets().add((GtaDataset)evt.getNewValue());
        }
    }
}
