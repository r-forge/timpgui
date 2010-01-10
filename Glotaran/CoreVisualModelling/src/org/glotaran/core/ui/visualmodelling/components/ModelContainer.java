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
import java.io.IOException;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.models.tgm.KinPar;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.nodes.CohSpecNode;
import org.glotaran.core.ui.visualmodelling.nodes.DispersionModelingNode;
import org.glotaran.core.ui.visualmodelling.nodes.IrfParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KineticParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.PropertiesAbstractNode;
import org.glotaran.core.ui.visualmodelling.nodes.WeightParametersNode;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.cookies.SaveCookie;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 *
 * @author slk230
 */
public class ModelContainer 
        extends javax.swing.JPanel
        implements ExplorerManager.Provider, Lookup.Provider, PropertyChangeListener {


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
                        new Node[]{new KineticParametersNode(model.getTgm().getDat().getKinparPanel(), this)});
            }
        }

        if (!model.getTgm().getDat().getIrfparPanel().getIrf().isEmpty()&&
                (!model.getTgm().getDat().getIrfparPanel().isMirf())){
            manager.getRootContext().getChildren().add(
                    new Node[]{new IrfParametersNode(model.getTgm().getDat().getIrfparPanel())});
        }

        if (model.getTgm().getDat().getIrfparPanel().getParmu().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel(), EnumTypes.DispersionTypes.PARMU)});
        }

        if (model.getTgm().getDat().getIrfparPanel().getPartau().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel(), EnumTypes.DispersionTypes.PARTAU)});
        }

        if (!model.getTgm().getDat().getWeightParPanel().getWeightpar().isEmpty()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new WeightParametersNode(model.getTgm().getDat().getWeightParPanel().getWeightpar())});
        }

        if (model.getTgm().getDat().getCohspecPanel().getCohspec().isSet()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new CohSpecNode(model.getTgm().getDat().getCohspecPanel())});
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remove))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        container.remove(manager.getSelectedNodes());
}//GEN-LAST:event_removeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    private javax.swing.JButton remove;
    // End of variables declaration//GEN-END:variables

    public ExplorerManager getExplorerManager() {
        return manager;
    }

    public Lookup getLookup() {
        return lookup;
    }



    @SuppressWarnings("element-type-mismatch")
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager &&
                ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            WindowManager.getDefault().getRegistry().getActivated().setActivatedNodes(manager.getSelectedNodes());
        }
        if (evt.getSource().getClass().equals(KineticParametersNode.class)){
            if (evt.getPropertyName().equalsIgnoreCase("Number of components")){
                if ((Integer)evt.getNewValue()>(Integer)evt.getOldValue()){
                    for (int i = 0; i < (Integer)evt.getNewValue()-(Integer)evt.getOldValue(); i++){
                        model.getTgm().getDat().getKinparPanel().getKinpar().add(new KinPar());
                    }
                } else {
                        for (int i = 0; i < (Integer)evt.getOldValue()-(Integer)evt.getNewValue(); i++){
                            model.getTgm().getDat().getKinparPanel().getKinpar().remove(
                                    model.getTgm().getDat().getKinparPanel().getKinpar().size()-1);
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("Positise rates")){
                model.getTgm().getDat().getKinparPanel().setPositivepar((Boolean)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("Sequential model")){
                model.getTgm().getDat().getKinparPanel().setSeqmod((Boolean)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("start")){
                model.getTgm().getDat().getKinparPanel().getKinpar().get((Integer)evt.getOldValue()).setStart((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")){
                model.getTgm().getDat().getKinparPanel().getKinpar().get((Integer)evt.getOldValue()).setFixed((Boolean)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")){
                int index = (Integer)evt.getNewValue();
                model.getTgm().getDat().getKinparPanel().getKinpar().remove(index);
            }
            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }

        }

        
//        model.modelUpdatedFromUI();
    }
}
