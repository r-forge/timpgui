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
import org.glotaran.core.models.tgm.IrfparPanelModel;
import org.glotaran.core.models.tgm.KinPar;
import org.glotaran.core.models.tgm.WeightPar;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.CohSpecTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.IRFTypes;
import org.glotaran.core.ui.visualmodelling.nodes.CohSpecNode;
import org.glotaran.core.ui.visualmodelling.nodes.DispersionModelingNode;
import org.glotaran.core.ui.visualmodelling.nodes.IrfParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KineticParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KmatrixNode;
import org.glotaran.core.ui.visualmodelling.nodes.ParametersSubNode;
import org.glotaran.core.ui.visualmodelling.nodes.PropertiesAbstractNode;
import org.glotaran.core.ui.visualmodelling.nodes.WeightParametersNode;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.cookies.SaveCookie;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.Node;
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
        manager.setRootContext(new PropertiesAbstractNode("Model specification",container, lookup, this));//,ExplorerUtils.createLookup(manager, null)));
        manager.addPropertyChangeListener(this);
        modelSpecificationView.addPropertyChangeListener(this);
        model = object;
        ActionMap map = this.getActionMap ();
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");

        // following line tells the top component which lookup should be associated with it
        lookup = ExplorerUtils.createLookup (manager, map);
//==============filling up parameters ============
        if (!model.getTgm().getDat().getKMatrixPanel().getJVector().getVector().isEmpty()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new KmatrixNode(model.getTgm().getDat().getKMatrixPanel(), this)});

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
                    new Node[]{new IrfParametersNode(model.getTgm().getDat().getIrfparPanel(), this)});
        }

        if (model.getTgm().getDat().getIrfparPanel().getParmu().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel(), EnumTypes.DispersionTypes.PARMU, this)});
        }

        if (model.getTgm().getDat().getIrfparPanel().getPartau().length()!=0){
            manager.getRootContext().getChildren().add(
                    new Node[]{new DispersionModelingNode(model.getTgm().getDat().getIrfparPanel(), EnumTypes.DispersionTypes.PARTAU, this)});
        }

        if (!model.getTgm().getDat().getWeightParPanel().getWeightpar().isEmpty()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new WeightParametersNode(model.getTgm().getDat().getWeightParPanel().getWeightpar(), this)});
        }

        if (model.getTgm().getDat().getCohspecPanel().getCohspec().isSet()){
            manager.getRootContext().getChildren().add(
                    new Node[]{new CohSpecNode(model.getTgm().getDat().getCohspecPanel(), this)});
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
            return;
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
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                model.getTgm().getDat().getKinparPanel().getKinpar().clear();
                model.getTgm().getDat().getKinparPanel().setPositivepar(false);
                model.getTgm().getDat().getKinparPanel().setSeqmod(false);
            }


            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }
            return;

        }
        if (evt.getSource().getClass().equals(IrfParametersNode.class)){
            if (evt.getPropertyName().equalsIgnoreCase("SetBackSweep")){
                model.getTgm().getDat().getIrfparPanel().setBacksweepEnabled((Boolean)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("SetBackSweepPeriod")){
                model.getTgm().getDat().getIrfparPanel().setBacksweepPeriod((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                model.getTgm().getDat().getIrfparPanel().getIrf().clear();
                model.getTgm().getDat().getIrfparPanel().getFixed().clear();
                model.getTgm().getDat().getIrfparPanel().setParmufixed(Boolean.FALSE);
                model.getTgm().getDat().getIrfparPanel().setPartaufixed(Boolean.FALSE);
                model.getTgm().getDat().getIrfparPanel().setBacksweepEnabled(Boolean.FALSE);
                model.getTgm().getDat().getIrfparPanel().setMirf(Boolean.FALSE);
            }
            if (evt.getPropertyName().equalsIgnoreCase("SetIRFType")){
                EnumTypes.IRFTypes irfType = (IRFTypes)evt.getNewValue();
                switch (irfType) {
                    case GAUSSIAN: {
                        model.getTgm().getDat().getIrfparPanel().setMirf(Boolean.FALSE);
                        if (model.getTgm().getDat().getIrfparPanel().getIrf()!=null){
                            model.getTgm().getDat().getIrfparPanel().getIrf().clear();
                            model.getTgm().getDat().getIrfparPanel().getFixed().clear();
                        }
                        for (int i = 0; i < 2; i++){
                            model.getTgm().getDat().getIrfparPanel().getIrf().add(
                                    ((ParametersSubNode)((IrfParametersNode)evt.getSource()).getChildren().getNodes()[i]).getDataObj().getStart());
                            model.getTgm().getDat().getIrfparPanel().getFixed().add(
                                    ((ParametersSubNode)((IrfParametersNode)evt.getSource()).getChildren().getNodes()[i]).getDataObj().isFixed());
                        }
                        break;
                    }
                    case DOUBLE_GAUSSIAN: {
                        model.getTgm().getDat().getIrfparPanel().setMirf(Boolean.FALSE);
                        if (model.getTgm().getDat().getIrfparPanel().getIrf()!=null){
                            model.getTgm().getDat().getIrfparPanel().getIrf().clear();
                            model.getTgm().getDat().getIrfparPanel().getFixed().clear();
                        }
                        for (int i = 0; i < 4; i++){
                            model.getTgm().getDat().getIrfparPanel().getIrf().add(
                                    ((ParametersSubNode)((IrfParametersNode)evt.getSource()).getChildren().getNodes()[i]).getDataObj().getStart());
                            model.getTgm().getDat().getIrfparPanel().getFixed().add(
                                    ((ParametersSubNode)((IrfParametersNode)evt.getSource()).getChildren().getNodes()[i]).getDataObj().isFixed());
                        }
                        break;
                    }
                    case MEASURED_IRF: {
                        model.getTgm().getDat().getIrfparPanel().setMirf(Boolean.TRUE);
                        //todo finish measured IRF
                        break;
                    }
                }               
            }

            if (evt.getPropertyName().equalsIgnoreCase("start")){
                model.getTgm().getDat().getIrfparPanel().getIrf().set((Integer)evt.getOldValue(), (Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")){
                model.getTgm().getDat().getIrfparPanel().getFixed().set((Integer)evt.getOldValue(), (Boolean)evt.getNewValue());
            }

            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }
            return;
        }
        if (evt.getSource().getClass().equals(CohSpecNode.class)){
            if (evt.getPropertyName().equalsIgnoreCase("setClpMax")){
                model.getTgm().getDat().getCohspecPanel().setClp0Max((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setClpMin")){
                model.getTgm().getDat().getCohspecPanel().setClp0Min((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setClpZero")){
                model.getTgm().getDat().getCohspecPanel().setClp0Enabled((Boolean)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setCohType")){
                EnumTypes.CohSpecTypes cohType = (CohSpecTypes)evt.getNewValue();
                switch (cohType) {
                    case IRF: {
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setType("irf");
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case FREE_IRF: {
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setType("freeirfdisp");
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case IRF_MULTY: {
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setType("irfmulti");
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case MIXED: {
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setType("mix");
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case SEQ: {
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setType("seq");
                        model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                model.getTgm().getDat().getCohspecPanel().setClp0Enabled(null);
                model.getTgm().getDat().getCohspecPanel().setClp0Max(null);
                model.getTgm().getDat().getCohspecPanel().setClp0Min(null);
                model.getTgm().getDat().getCohspecPanel().setCoh("");
                model.getTgm().getDat().getCohspecPanel().getCohspec().setSet(false);
                model.getTgm().getDat().getCohspecPanel().getCohspec().setType("");
            }
            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }
            return;

        }
        if (evt.getSource().getClass().equals(DispersionModelingNode.class)){
            boolean parMu = evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU);
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                for (int i = 0; i < getExplorerManager().getRootContext().getChildren().getNodesCount(); i++){
                    if (getExplorerManager().getRootContext().getChildren().getNodes()[i] instanceof DispersionModelingNode){
                        ((DispersionModelingNode)getExplorerManager().getRootContext().getChildren().getNodes()[i]).setSingle(true);
                        ((DispersionModelingNode)getExplorerManager().getRootContext().getChildren().getNodes()[i]).recreateSheet();
                    }
                }
                if (parMu){
                    model.getTgm().getDat().getIrfparPanel().setParmufixed(false);
                    model.getTgm().getDat().getIrfparPanel().setDispmufun("");
                    model.getTgm().getDat().getIrfparPanel().setParmu("");
                }
                else {
                    model.getTgm().getDat().getIrfparPanel().setPartaufixed(false);
                    model.getTgm().getDat().getIrfparPanel().setDisptaufun("");
                    model.getTgm().getDat().getIrfparPanel().setPartau("");
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("setCentralWave")){
                model.getTgm().getDat().getIrfparPanel().setLamda((Double)evt.getNewValue());
            }

            if (evt.getPropertyName().equalsIgnoreCase("setDisptype")){
                IrfparPanelModel irfModel = model.getTgm().getDat().getIrfparPanel();
                if ((evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU))&
                        (evt.getNewValue().equals(EnumTypes.DispersionTypes.PARTAU))){
                    
                    irfModel.setPartaufixed(irfModel.isParmufixed());
                    irfModel.setDisptaufun(irfModel.getDispmufun());
                    irfModel.setPartau(irfModel.getParmu());
                    model.getTgm().getDat().getIrfparPanel().setParmufixed(false);
                    model.getTgm().getDat().getIrfparPanel().setDispmufun("");
                    model.getTgm().getDat().getIrfparPanel().setParmu("");
                }
                else {
                    if ((evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU))&
                        (evt.getNewValue().equals(EnumTypes.DispersionTypes.PARMU))){

                        irfModel.setPartaufixed(irfModel.isParmufixed());
                        irfModel.setDisptaufun(irfModel.getDispmufun());
                        irfModel.setPartau(irfModel.getParmu());
                        model.getTgm().getDat().getIrfparPanel().setParmufixed(false);
                        model.getTgm().getDat().getIrfparPanel().setDispmufun("");
                        model.getTgm().getDat().getIrfparPanel().setParmu("");
                    }
                }
            }
            if ((evt.getPropertyName().equalsIgnoreCase("start"))||(evt.getPropertyName().equalsIgnoreCase("delete"))){
                String paramString = null;
                ParametersSubNode paramSubNode = (ParametersSubNode)((DispersionModelingNode)evt.getSource()).getChildren().getNodeAt(0);
                paramString = String.valueOf(paramSubNode.getDataObj().getStart());
                for (int i = 1; i < ((DispersionModelingNode)evt.getSource()).getChildren().getNodesCount(); i++){
                    paramSubNode = (ParametersSubNode)((DispersionModelingNode)evt.getSource()).getChildren().getNodeAt(i);
                    paramString = paramString+ ", " + String.valueOf(paramSubNode.getDataObj().getStart());
                }
                if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU)){
                    model.getTgm().getDat().getIrfparPanel().setDispmufun("poly");
                    model.getTgm().getDat().getIrfparPanel().setParmu(paramString);
                } 
                else {
                    if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU)){
                        model.getTgm().getDat().getIrfparPanel().setDisptaufun("poly");
                        model.getTgm().getDat().getIrfparPanel().setPartau(paramString);
                    }                    
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")){
                if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU)){
                    model.getTgm().getDat().getIrfparPanel().setParmufixed((Boolean)evt.getNewValue());
                }
                else {
                    if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU)){
                        model.getTgm().getDat().getIrfparPanel().setPartaufixed((Boolean)evt.getNewValue());

                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")){

            }


            

            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }
            return;

        }
        if (evt.getSource().getClass().equals(WeightParametersNode.class)){
            if (evt.getPropertyName().equalsIgnoreCase("Number of components")){
                if ((Integer)evt.getNewValue()>(Integer)evt.getOldValue()){
                    for (int i = 0; i < (Integer)evt.getNewValue()-(Integer)evt.getOldValue(); i++){
                        model.getTgm().getDat().getWeightParPanel().getWeightpar().add(new WeightPar());
                    }
                } else {
                        for (int i = 0; i < (Integer)evt.getOldValue()-(Integer)evt.getNewValue(); i++){
                            model.getTgm().getDat().getWeightParPanel().getWeightpar().remove(
                                    model.getTgm().getDat().getWeightParPanel().getWeightpar().size()-1);
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().clear();
            }
            if (evt.getPropertyName().equalsIgnoreCase("weight")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().get((Integer)evt.getOldValue()).setWeight((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmin1")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().get((Integer)evt.getOldValue()).setMin1((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmin2")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().get((Integer)evt.getOldValue()).setMin2((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmax1")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().get((Integer)evt.getOldValue()).setMax1((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmax2")){
                model.getTgm().getDat().getWeightParPanel().getWeightpar().get((Integer)evt.getOldValue()).setMax2((Double)evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")){
                int index = (Integer)evt.getNewValue();
                model.getTgm().getDat().getWeightParPanel().getWeightpar().remove(index);
            }
            try {
                model.setModified(true);
                model.getCookie(SaveCookie.class).save();
                model.setModified(false);
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(model.getNodeDelegate().getName());
            }
            return;

        }
    }
}
