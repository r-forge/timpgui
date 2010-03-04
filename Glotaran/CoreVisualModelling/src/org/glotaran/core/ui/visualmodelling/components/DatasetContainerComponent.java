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
import java.io.IOException;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.models.gta.GtaChangesModel;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDiffContainer;
import org.glotaran.core.models.gta.GtaModelDiffDO;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.core.ui.visualmodelling.common.VisualCommonFunctions;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetComponentNode;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetsRootNode;
import org.glotaran.core.ui.visualmodelling.nodes.ModelDiffsChangeNode;
import org.glotaran.core.ui.visualmodelling.nodes.ModelDiffsNode;
import org.glotaran.core.ui.visualmodelling.nodes.PropertiesAbstractNode;
import org.glotaran.core.ui.visualmodelling.widgets.DatasetContainerWidget;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
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
    private static final long serialVersionUID = 1;
    private ExplorerManager manager = new ExplorerManager();
    private DatasetSpecificationView datasetView  = new DatasetSpecificationView();
    private Lookup lookup;
    private GtaDatasetContainer datasetContainer;
    private Tgm connectedModel = null;
    private GtaModelDifferences modelDifferences = null;
    private FileObject schemaFolder;

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
        manager.setRootContext(new DatasetsRootNode(new Index.ArrayChildren(), this));
        manager.addPropertyChangeListener(this);
    }

    public DatasetContainerComponent(GtaDatasetContainer myNode, PropertyChangeListener listn) {
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
        addPropertyChangeListener(listn);
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

    public Tgm getConnectedModel() {
        return connectedModel;
    }

    public boolean isConnected(){
        return connectedModel == null ? false : true;
    }

    public void setConnectedModel(Tgm connectedModel) {
        this.connectedModel = connectedModel;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager &&
                ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            WindowManager.getDefault().getRegistry().getActivated().setActivatedNodes(manager.getSelectedNodes());
            return;
        }

        if (evt.getSource().getClass().equals(DatasetsRootNode.class)){
            if (evt.getPropertyName().equals("datasetAdded")){
                datasetContainer.getDatasets().add((GtaDataset)evt.getNewValue());
                if (isConnected()){
                    modelDifferences.getDifferences().add(new GtaModelDiffContainer());
                }
            }
            firePropertyChange("modelChanged", null, null);
            return;
        }

        if (evt.getSource().getClass().equals(DatasetComponentNode.class)){
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                datasetContainer.getDatasets().remove((GtaDataset) evt.getNewValue());
                if ((evt.getNewValue() != null)&&(isConnected())) {
                    int ind = ((DatasetComponentNode) evt.getSource()).getdatasetIndex();
                    if (modelDifferences.getDifferences().get(ind-1).getChanges()!=null){
                        try {
                            schemaFolder.getFileObject(modelDifferences.getDifferences().get(ind - 1).getChanges().getFilename()).delete();
                        } catch (IOException ex) {
                            CoreErrorMessages.fileDeleteException(modelDifferences.getDifferences().get(ind - 1).getChanges().getFilename());
                        }
                    }
                    modelDifferences.getDifferences().remove(ind-1);
                    for (int i = ind-1; i < modelDifferences.getDifferences().size(); i++){
                        if (!modelDifferences.getDifferences().get(i).getAdd().isEmpty()){
                            for (int j = 0; j < modelDifferences.getDifferences().get(i).getAdd().size(); j++){
                                modelDifferences.getDifferences().get(i).getAdd().get(j).setDataset(i+1);
                            }
                        }
                        if (!modelDifferences.getDifferences().get(i).getFree().isEmpty()){
                            for (int j = 0; j < modelDifferences.getDifferences().get(i).getFree().size(); j++){
                                modelDifferences.getDifferences().get(i).getFree().get(j).setDataset(i+1);
                            }
                        }
                        if (!modelDifferences.getDifferences().get(i).getRemove().isEmpty()){
                            for (int j = 0; j < modelDifferences.getDifferences().get(i).getRemove().size(); j++){
                                modelDifferences.getDifferences().get(i).getRemove().get(j).setDataset(i+1);
                            }
                        }
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("ChangeParamAdded")){
                try {
                    String newTgmFileName = datasetContainer.getId()+"mdChange"+String.valueOf(System.currentTimeMillis()+".xml");
                    FileObject newTgmFile = schemaFolder.createData(newTgmFileName);
                    VisualCommonFunctions.createNewTgmFile(FileUtil.toFile(newTgmFile));
                    GtaChangesModel gtaChanges = new GtaChangesModel();
                    gtaChanges.setPath(schemaFolder.getPath());
                    gtaChanges.setFilename(newTgmFileName);
                    modelDifferences.getDifferences().get((Integer)evt.getOldValue()-1).setChanges(gtaChanges);
                } catch (IOException ex) {
                    CoreErrorMessages.fileSaveError("newtgmfile");
                }

            }
            firePropertyChange("modelChanged", null, null);
            return;
        }

        if (evt.getSource().getClass().equals(DatasetContainerWidget.class)){
            if (evt.getPropertyName().equalsIgnoreCase("connectionChange")){
                schemaFolder = ((DatasetContainerWidget)evt.getSource()).getSchemaPath();
                schemaFolder = schemaFolder.getParent();
                connectedModel = (Tgm)evt.getOldValue();
                if (evt.getNewValue() != null) {
                    modelDifferences = (GtaModelDifferences) evt.getNewValue();
                    int diffNum = modelDifferences.getDifferences().size();
                    updateModelDiffsNodes(modelDifferences);
                    if (datasetContainer.getDatasets().size() > diffNum) {
                        for (int i = 0; i < datasetContainer.getDatasets().size() - diffNum; i++) {
                            modelDifferences.getDifferences().add(new GtaModelDiffContainer());
                        }
                    }
                }
            }
            firePropertyChange("modelChanged", null, null);
            return;
        }

        if (evt.getSource().getClass().equals(ModelDiffsNode.class)){
            ModelDiffsNode sourceNode = (ModelDiffsNode)evt.getSource();
            int datasetIndex = ((DatasetComponentNode)sourceNode.getParentNode()).getdatasetIndex()-1;
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                if (evt.getOldValue().equals("FreeParameter")){
                    if (modelDifferences.getDifferences().get(datasetIndex).getFree()!=null){
                        modelDifferences.getDifferences().get(datasetIndex).getFree().clear();
                    }
                }
                if (evt.getOldValue().equals("AddParameter")) {
                    if (modelDifferences.getDifferences().get(datasetIndex).getAdd()!=null){
                        modelDifferences.getDifferences().get(datasetIndex).getAdd().clear();
                    }
                }
                if (evt.getOldValue().equals("RemoveParameter")) {
                    if (modelDifferences.getDifferences().get(datasetIndex).getRemove()!=null){
                        modelDifferences.getDifferences().get(datasetIndex).getRemove().clear();
                    }
                }
            }

            if (evt.getPropertyName().equalsIgnoreCase("Number of components")){
                if ((Integer)evt.getNewValue()>(Integer)evt.getOldValue()){
                    for (int i = 0; i < (Integer)evt.getNewValue()-(Integer)evt.getOldValue(); i++){
                        GtaModelDiffDO newModelDiffs = new GtaModelDiffDO();
                        newModelDiffs.setDataset(sourceNode.getDatasetIndex());
                        if (sourceNode.getType().equals("FreeParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getFree().add(newModelDiffs);
                        }
                        if (sourceNode.getType().equals("AddParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getAdd().add(newModelDiffs);
                        }
                        if (sourceNode.getType().equals("RemoveParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getRemove().add(newModelDiffs);
                        }
                    }
                } else {
                    for (int i = 0; i < (Integer) evt.getOldValue() - (Integer) evt.getNewValue(); i++) {
                        if (sourceNode.getType().equals("FreeParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getFree().remove(modelDifferences.getDifferences().get(datasetIndex).getFree().size()-1);
                        }
                        if (sourceNode.getType().equals("AddParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getAdd().remove(modelDifferences.getDifferences().get(datasetIndex).getAdd().size()-1);
                        }
                        if (sourceNode.getType().equals("RemoveParameter")) {
                            modelDifferences.getDifferences().get(datasetIndex).getRemove().remove(modelDifferences.getDifferences().get(datasetIndex).getRemove().size()-1);
                        }
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("start")){
                if (sourceNode.getType().equals("FreeParameter")) {
                    modelDifferences.getDifferences().get(datasetIndex).getFree().get((Integer)evt.getOldValue()).setStart((Double)evt.getNewValue());
                }
            }

            if (evt.getPropertyName().equalsIgnoreCase("index")){
                if (sourceNode.getType().equals("FreeParameter")) {
                    modelDifferences.getDifferences().get(datasetIndex).getFree().get((Integer)evt.getOldValue()).setIndex((Integer)evt.getNewValue()+1);
                }
            }

            if (evt.getPropertyName().equalsIgnoreCase("what")){
                if (sourceNode.getType().equals("FreeParameter")) {
                    modelDifferences.getDifferences().get(datasetIndex).getFree().get((Integer)evt.getOldValue()).setWhat((String)evt.getNewValue());
                }
            }

            if (evt.getPropertyName().equalsIgnoreCase("delete")){
                int index = (Integer)evt.getOldValue();
                if (sourceNode.getType().equals("FreeParameter")) {
                    modelDifferences.getDifferences().get(datasetIndex).getFree().remove(index);
                }
            }
            firePropertyChange("modelChanged", null, null);
            return;
        }

        if (evt.getSource().getClass().equals(ModelDiffsChangeNode.class)){
            ModelDiffsChangeNode sourceNode = (ModelDiffsChangeNode)evt.getSource();
            int datasetIndex = ((DatasetComponentNode)sourceNode.getParentNode()).getdatasetIndex()-1;
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                try {
                    schemaFolder.getFileObject(modelDifferences.getDifferences().get(datasetIndex).getChanges().getFilename()).delete();
                } catch (IOException ex) {
                    CoreErrorMessages.fileDeleteException(modelDifferences.getDifferences().get(datasetIndex).getChanges().getFilename());
                }
                modelDifferences.getDifferences().get(datasetIndex).setChanges(null);
            }
            firePropertyChange("modelChanged", null, null);
            return;
        }

        if (evt.getSource().getClass().getSuperclass().equals(PropertiesAbstractNode.class)){
            if (((PropertiesAbstractNode)evt.getSource()).getParentNode().getClass().equals(ModelDiffsChangeNode.class)){
                ModelDiffsChangeNode changeNode = (ModelDiffsChangeNode)((PropertiesAbstractNode)evt.getSource()).getParentNode();
                int datasetIndex = ((DatasetComponentNode)changeNode.getParentNode()).getdatasetIndex()-1;
                FileObject tgmFile = schemaFolder.getFileObject(modelDifferences.getDifferences().get(datasetIndex).getChanges().getFilename());
                try {
                    TgmDataObject tgmDO = (TgmDataObject)TgmDataObject.find(tgmFile);
                    boolean state = VisualCommonFunctions.modelParametersChange(tgmDO.getTgm().getDat(), evt);
                    tgmDO.setModified(state);
                } catch (DataObjectNotFoundException ex) {
                    CoreErrorMessages.fileLoadException("tgm changes");
                }
            }
        }
        
    }

    private void updateModelDiffsNodes(GtaModelDifferences modelDifferences) {
        int number = manager.getRootContext().getChildren().getNodesCount() > modelDifferences.getDifferences().size() ?
            modelDifferences.getDifferences().size() : manager.getRootContext().getChildren().getNodesCount();
        Node[] datasets = manager.getRootContext().getChildren().getNodes();
        for (int i = 0; i< number; i++){
            if (!modelDifferences.getDifferences().get(i).getFree().isEmpty()){    
                datasets[i].getChildren().add(new Node[]{new ModelDiffsNode("FreeParameter", i, modelDifferences.getDifferences().get(i).getFree(), this)});
            }
            if (!modelDifferences.getDifferences().get(i).getAdd().isEmpty()){
                datasets[i].getChildren().add(new Node[]{new ModelDiffsNode("AddParameter", i, modelDifferences.getDifferences().get(i).getAdd(), this)});
            }
            if (!modelDifferences.getDifferences().get(i).getRemove().isEmpty()){
                datasets[i].getChildren().add(new Node[]{new ModelDiffsNode("RemoveParameter", i, modelDifferences.getDifferences().get(i).getRemove(), this)});
            }
            if (modelDifferences.getDifferences().get(i).getChanges()!=null){
                datasets[i].getChildren().add(new Node[]{new ModelDiffsChangeNode("ChangeParameter", i, modelDifferences.getDifferences().get(i).getChanges(), this)});
            }
        }
       
    }
}
