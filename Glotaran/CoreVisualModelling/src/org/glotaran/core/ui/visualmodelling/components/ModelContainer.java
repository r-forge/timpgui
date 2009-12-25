/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DatasetContainer.java
 *
 * Created on Jul 15, 2009, 10:18:18 AM
 */

package org.glotaran.core.ui.visualmodelling.components;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.nodes.DispersionModelingNode;
import org.glotaran.core.ui.visualmodelling.nodes.IrfParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KineticParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.PropertiesAbstractNode;
import org.glotaran.core.ui.visualmodelling.nodes.WeightParametersNode;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 *
 * @author slk230
 */
public class ModelContainer extends javax.swing.JPanel implements ExplorerManager.Provider, Lookup.Provider, PropertyChangeListener {


/** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "ModelSpecificationView";

    private ExplorerManager manager = new ExplorerManager();
    private ModelSpecificationView    modelSpecificationView  = new ModelSpecificationView();
    private ModelSpecificationNodeContainer   container = new ModelSpecificationNodeContainer();
    private Lookup lookup;
    private TgmDataObject model;
    /** Creates new form DatasetContainer */
    public ModelContainer() {
        initComponents();
        manager.setRootContext(new PropertiesAbstractNode("Model specification",container));//,ExplorerUtils.createLookup(manager, null)));
        manager.addPropertyChangeListener(this);
        ActionMap map = this.getActionMap ();
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false

        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");

        // following line tells the top component which lookup should be associated with it
        lookup = ExplorerUtils.createLookup (manager, map);
        //new ProxyLookup(arg0)
    }

    public ModelContainer(TgmDataObject object) {
        initComponents();
        manager.setRootContext(new PropertiesAbstractNode("Model specification",container));//,ExplorerUtils.createLookup(manager, null)));
        manager.addPropertyChangeListener(this);
        model = object;
        ActionMap map = this.getActionMap ();
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");

        // following line tells the top component which lookup should be associated with it
        lookup = ExplorerUtils.createLookup (manager, map);
//==============filling up parameters ============
        if (!model.getTgm().getDat().getKMatrixPanel().getJVector().getVector().isEmpty()){
//============== TODO implement Kmatrix case ==============

        }
        else {
            if (!model.getTgm().getDat().getKinparPanel().getKinpar().isEmpty()){
                manager.getRootContext().getChildren().add(
                        new Node[]{new KineticParametersNode(model.getTgm().getDat().getKinparPanel())});
            }
        }

        if (!model.getTgm().getDat().getIrfparPanel().getIrf().isEmpty()&&
                (!model.getTgm().getDat().getIrfparPanel().isMirf())){
            manager.getRootContext().getChildren().add(
                    new Node[]{new IrfParametersNode(model.getTgm().getDat().getIrfparPanel())});
        }

        if (model.getTgm().getDat().getIrfparPanel().getParmu().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel().getParmu(), EnumTypes.DispersionTypes.PARMU)});
        }

        if (model.getTgm().getDat().getIrfparPanel().getPartau().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel().getParmu(), EnumTypes.DispersionTypes.PARTAU)});
        }

        if (!model.getTgm().getDat().getWeightParPanel().getWeightpar().isEmpty()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new WeightParametersNode(model.getTgm().getDat().getWeightParPanel().getWeightpar())});
        }




    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        remove = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();

        setMaximumSize(new java.awt.Dimension(160, 250));
        setMinimumSize(new java.awt.Dimension(160, 250));
        setPreferredSize(new java.awt.Dimension(160, 250));

        panel.setMaximumSize(new java.awt.Dimension(150, 200));
        panel.setMinimumSize(new java.awt.Dimension(150, 200));
        panel.setPreferredSize(new java.awt.Dimension(150, 200));
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(modelSpecificationView, BorderLayout.CENTER);

        remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/glotaran/core/main/resources/Delete-icon-16.png"))); // NOI18N
        remove.setText(org.openide.util.NbBundle.getMessage(ModelContainer.class, "ModelContainer.remove.text")); // NOI18N
        remove.setToolTipText(org.openide.util.NbBundle.getMessage(ModelContainer.class, "ModelContainer.remove.toolTipText")); // NOI18N
        remove.setFocusable(false);
        remove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        remove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(ModelContainer.class, "ModelContainer.jCheckBox1.text")); // NOI18N

        jCheckBox2.setText(org.openide.util.NbBundle.getMessage(ModelContainer.class, "ModelContainer.jCheckBox2.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2))
                    .addComponent(remove))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        container.remove(manager.getSelectedNodes());
}//GEN-LAST:event_removeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JPanel panel;
    private javax.swing.JButton remove;
    // End of variables declaration//GEN-END:variables

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    public Lookup getLookup() {
        return lookup;
    }



    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager &&
                ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            WindowManager.getDefault().getRegistry().getActivated().setActivatedNodes(manager.getSelectedNodes());
        }
    }
}
