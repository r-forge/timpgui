/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import nl.vu.nat.timpinterface.Current;
import nl.vu.nat.timpinterface.ResultObject;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final class TimpguiMainTopComponent extends TopComponent {

    private static TimpguiMainTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "nl/vu/nat/timpgui/TIMPGUI_APP_LOGO.PNG";
    private static final String PREFERRED_ID = "TimpguiMainTopComponent";
    private static DefaultListModel datasetsListModel = new DefaultListModel();
    private static DefaultListModel modelsListModel = new DefaultListModel();
    private static DefaultListModel optionsListModel = new DefaultListModel();
    private static DefaultListModel resultsListModel = new DefaultListModel();

    private TimpguiMainTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(TimpguiMainTopComponent.class, "CTL_TimpguiMainTopComponent"));
        setToolTipText(NbBundle.getMessage(TimpguiMainTopComponent.class, "HINT_TimpguiMainTopComponent"));
        setIcon(Utilities.loadImage(ICON_PATH, true));
        refreshLists();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel4 = new javax.swing.JPanel();
        jButtonRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTResultsNameTextfield = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jBShowResultsButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        removeDataset = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JListDatasets = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListModels = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListOptions = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListResults = new javax.swing.JList();

        setPreferredSize(new java.awt.Dimension(800, 400));

        org.openide.awt.Mnemonics.setLocalizedText(jButtonRefresh, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jButtonRefresh.text")); // NOI18N
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTResultsNameTextfield.setText(org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jTResultsNameTextfield.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jTResultsNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButtonRefresh)
                    .addComponent(jButton2)
                    .addComponent(jTResultsNameTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.openide.awt.Mnemonics.setLocalizedText(jBShowResultsButton, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jBShowResultsButton.text")); // NOI18N
        jBShowResultsButton.setEnabled(false);
        jBShowResultsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBShowResultsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBShowResultsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jBShowResultsButton)
                .addContainerGap())
        );

        org.openide.awt.Mnemonics.setLocalizedText(removeDataset, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.removeDataset.text")); // NOI18N
        removeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDatasetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeDataset)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeDataset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setMaximumSize(new java.awt.Dimension(1000, 400));
        jPanel8.setMinimumSize(new java.awt.Dimension(400, 100));
        jPanel8.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabel1, gridBagConstraints);

        JListDatasets.setModel(modelsListModel);
        JListDatasets.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                JListDatasetsValueChanged(evt);
            }
        });
        JListDatasets.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                JListDatasetsComponentShown(evt);
            }
        });
        jScrollPane4.setViewportView(JListDatasets);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane4, gridBagConstraints);

        jPanel8.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jLabel2, gridBagConstraints);

        jListModels.setModel(modelsListModel);
        jListModels.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListModels.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListModelsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListModels);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jPanel8.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jLabel3, gridBagConstraints);

        jListOptions.setModel(optionsListModel);
        jListOptions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListOptions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListOptionsValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jListOptions);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane6, gridBagConstraints);

        jPanel8.add(jPanel3);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TimpguiMainTopComponent.class, "TimpguiMainTopComponent.jLabel4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel5.add(jLabel4, gridBagConstraints);

        jListResults.setModel(optionsListModel);
        jListResults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListResults.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListResultsValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(jListResults);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jScrollPane7, gridBagConstraints);

        jPanel8.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jListOptionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListOptionsValueChanged
// TODO add your handling code here:
}//GEN-LAST:event_jListOptionsValueChanged

private void jListModelsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListModelsValueChanged
// TODO add your handling code here:
}//GEN-LAST:event_jListModelsValueChanged

private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
// TODO add your handling code here:
    refreshLists();
    
}//GEN-LAST:event_jButtonRefreshActionPerformed

private void JListDatasetsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_JListDatasetsValueChanged
// TODO add your handling code here:
}//GEN-LAST:event_JListDatasetsValueChanged

private void JListDatasetsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_JListDatasetsComponentShown
// TODO add your handling code here:
    JListDatasets.setModel(datasetsListModel);
    JListDatasets.repaint();
}//GEN-LAST:event_JListDatasetsComponentShown

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    //Current.setSelectedDatasetNames(JListDatasets.getSelectedIndices());
    //Current.setSelectedModelName(jListModels.getSelectedIndex());
    //Current.setSelectedOptionsName(jListOptions.getSelectedIndex());
    //Current.addResults(new ResultObject(Current.getSelectedDatasetNames(JListDatasets.getSelectedIndices()), jTResultsNameTextfield.getText()));
    //Call_fitModel.fitModel(JListDatasets.getSelectedIndices(), jListModels.getSelectedIndex(), jListOptions.getSelectedIndex(), jTResultsNameTextfield.getText());
}//GEN-LAST:event_jButton1ActionPerformed

private void removeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDatasetActionPerformed
// TODO add your handling code here:
        TopComponent win = RunRScriptsTopComponent.findInstance();
        win.open();
        win.requestActive();
}//GEN-LAST:event_removeDatasetActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    
    //Make the call to fitmodel based on the selection
   // Call_fitModel.fitModel(tgo, tgm);
    //Wait for the results
    
    //Get the results
  //  String resultvariable = "res"+datasetName;
  //  TimpController.getdim1(ICON_PATH)
  //  DatasetTimp.
            
//                    psisim = null;
//        x = null;
//        x2 = null;
//        intenceIm = null;
//        nt = new int [1];
//        nt[0] = 1;
//        nl = new int [1];
//        nl[0] = 1;
//        orheigh = new int [1];
//        orheigh[0] = 1;
//        orwidth = new int [1];
//	orwidth[0] = 1;s
//        datasetName = "dataset1";
//        maxInt = 0;
}//GEN-LAST:event_jButton2ActionPerformed

private void jListResultsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListResultsValueChanged
// TODO add your handling code here:
    
    if (jListResults.getModel().getSize() >0) {
        jBShowResultsButton.setEnabled(true);
    } else {
        jBShowResultsButton.setEnabled(false);
    }
}//GEN-LAST:event_jListResultsValueChanged

private void jBShowResultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBShowResultsButtonActionPerformed
// TODO add your handling code here:
    //HAVE TO FIX
    TopComponent win = WindowManager.getDefault().findTopComponent("StreakOutTopComponent");
    win.open();
    win.requestActive();
            
}//GEN-LAST:event_jBShowResultsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList JListDatasets;
    private javax.swing.JButton jBShowResultsButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jListModels;
    private javax.swing.JList jListOptions;
    private javax.swing.JList jListResults;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTResultsNameTextfield;
    private javax.swing.JButton removeDataset;
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
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        refreshLists();
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
            return TimpguiMainTopComponent.getDefault();
        }
    }

    public void refreshLists() {
        int[] selectedDatasets = JListDatasets.getSelectedIndices();
        int selectedModel =jListModels.getSelectedIndex();
        int selectedOptions = jListOptions.getSelectedIndex();
        
        ArrayList<String> datasetNames = Current.getDatasetNames();
        datasetsListModel.removeAllElements();
        for (int i = 0; i<datasetNames.size(); i++) {
            datasetsListModel.addElement(datasetNames.get(i));
        }
        JListDatasets.setModel(datasetsListModel);
        
        ArrayList<String> modelNames = Current.getModelNames();
        modelsListModel.removeAllElements();
        for (int i = 0; i < modelNames.size(); i++) {
            modelsListModel.addElement(modelNames.get(i));
        }
        jListOptions.setModel(modelsListModel);
        
        ArrayList<String> optionsNames = Current.getOptNames();
        optionsListModel.removeAllElements();
        for (int i = 0; i < optionsNames.size(); i++) {
            optionsListModel.addElement(optionsNames.get(i));
        }
        jListOptions.setModel(optionsListModel);
        
        ArrayList<ResultObject> resultObjects = Current.GetresultNames();
        resultsListModel.removeAllElements();
        for (int i = 0; i < resultObjects.size(); i++) {
            resultsListModel.addElement(resultObjects.get(i).getNameOfResultsObjects());
        }
        jListResults.setModel(resultsListModel);
        
        JListDatasets.setSelectedIndices(selectedDatasets);
        jListModels.setSelectedIndex(selectedModel);
        jListOptions.setSelectedIndex(selectedOptions);

    }
}
