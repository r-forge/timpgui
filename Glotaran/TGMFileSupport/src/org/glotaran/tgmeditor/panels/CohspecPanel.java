package org.glotaran.tgmeditor.panels;

import javax.swing.JComponent;
import org.glotaran.core.models.tgm.CohspecPanelModel;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;

/*
 * CohspecPanel.java
 *
 * Created on August 3, 2008, 11:22 AM
 */
import org.netbeans.modules.xml.multiview.ui.SectionView;

/**
 *
 * @author  kate
 */
public class CohspecPanel extends SectionInnerPanel {

    private TgmDataObject dObj;
    private CohspecPanelModel cohspecPanelModel;

    /** Creates new form CohspecPanel */
    public CohspecPanel(SectionView view, TgmDataObject dObj, CohspecPanelModel cohspecPanelModel) {
        super(view);
        this.dObj = dObj;
        this.cohspecPanelModel = cohspecPanelModel;
        initComponents();
        if (cohspecPanelModel.getCohspec().isSet()) {
            if (cohspecPanelModel.getCohspec().getType().compareTo("irf") == 0) {
                jRBCohspecIrf.setSelected(true);
            }
            if (cohspecPanelModel.getCohspec().getType().compareTo("freeirfdisp") == 0) {
                jRBCohspecFreeirfdisp.setSelected(true);
            }
            if (cohspecPanelModel.getCohspec().getType().compareTo("irfmulti") == 0) {
                cohspec_irfmulti.setSelected(true);
            }
            if (cohspecPanelModel.getCohspec().getType().compareTo("seq") == 0) {
                jRBCohspecSeq.setSelected(true);
            }
            if (cohspecPanelModel.getCohspec().getType().compareTo("mix") == 0) {
                cohspec_mix.setSelected(true);
            }
        } else {
            jRBCohspecNo.setSelected(true);
        }
        cohspec_start.setText(cohspecPanelModel.getCoh()); //comma separated list of doubles, f.i.: "1,2,3,80,99"

        if (cohspecPanelModel.isClp0Enabled() != null) {
            jCBCohSpec0.setSelected(cohspecPanelModel.isClp0Enabled());
            updateEnabled(cohspecPanelModel.isClp0Enabled());
            jTFCohSpec0From.setText(String.valueOf(cohspecPanelModel.getClp0Min()));
            jTFCohSpec0To.setText(String.valueOf(cohspecPanelModel.getClp0Max()));
        }

        addModifier(cohspec_start);
        addModifier(jRBCohspecNo);
        addModifier(jRBCohspecIrf);
        addModifier(jRBCohspecFreeirfdisp);
        addModifier(cohspec_irfmulti);
        addModifier(jRBCohspecSeq);
        addModifier(cohspec_mix);
        addModifier(jCBCohSpec0);
        addModifier(jTFCohSpec0From);
        addModifier(jTFCohSpec0To);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cohspec_buttonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRBCohspecNo = new javax.swing.JRadioButton();
        jRBCohspecIrf = new javax.swing.JRadioButton();
        jRBCohspecFreeirfdisp = new javax.swing.JRadioButton();
        cohspec_irfmulti = new javax.swing.JRadioButton();
        jRBCohspecSeq = new javax.swing.JRadioButton();
        cohspec_mix = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        cohspec_start = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCBCohSpec0 = new javax.swing.JCheckBox();
        jTFCohSpec0From = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFCohSpec0To = new javax.swing.JTextField();

        jLabel1.setText("Type of model for coherent artifact/scatter component(s):");

        cohspec_buttonGroup.add(jRBCohspecNo);
        jRBCohspecNo.setSelected(true);
        jRBCohspecNo.setText("None");
        jRBCohspecNo.setToolTipText("<html>\n<b>TIMP function</b>: cohspec<br>\n<b>Description</b>: if this option is selected then no coherent artifact will be modelled (<i>default</i>)\n</html>");
        jRBCohspecNo.addChangeListener(new javax.swing.event.ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRBCohspecNoStateChanged(evt);
            }
        });
        jRBCohspecNo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBCohspecNoActionPerformed(evt);
            }
        });

        cohspec_buttonGroup.add(jRBCohspecIrf);
        jRBCohspecIrf.setText("IRF");
        jRBCohspecIrf.setToolTipText("<html>\n<b>TIMP function</b>: cohspec<br>\n<b>Argument</b>: type=\"irf\"<br>\n<b>Description</b>: if this option is selected then, the coherent artifact/scatter<br>\n has the time proﬁle of the Instrument Response Function.\n</html>");

        cohspec_buttonGroup.add(jRBCohspecFreeirfdisp);
        jRBCohspecFreeirfdisp.setText("Free IRF Disp");
        jRBCohspecFreeirfdisp.setToolTipText("<html>\n<b>TIMP function</b>: cohspec<br>\n<b>Argument</b>: type=\"freeirfdisp\"<br>\n<b>Description</b>: if this option is used then, the coherent artifact/scatter has a <br>\n Gaussian time profile whose location and width are parameterized in the vector coh.\n</html>");

        cohspec_buttonGroup.add(cohspec_irfmulti);
        cohspec_irfmulti.setText("IRF Multi");
        cohspec_irfmulti.setToolTipText("<html>\n<b>TIMP function</b>: cohspec<br>\n<b>Argument</b>: type=\"irfmulti\"<br>\n<b>Description</b>: if this option is selected then, the time profile of the IRF <br>\n is used for the coherent artifact/scatter model, but the IRF parameters are taken<br>\n per dataset (for the multidataset case), and the integer argument <i>numdatasets</i> must<br>\n be equal to the number of datasets modeled.<br>\n<b>Comments</b>: requires additional argument <i>numdatasets</i> to be specified.\n</html>");

        cohspec_buttonGroup.add(jRBCohspecSeq);
        jRBCohspecSeq.setText("Sequential");
        jRBCohspecSeq.setToolTipText("<html>\n<b>TIMP function</b>: \"cohspec\"<br>\n<b>Argument</b>: type=\"seq\"<br>\n<b>Description</b>: if this options is used, then a sequential exponential decay model <br>\nis applied, whose starting value are contained in an additional list element start. This often <br>\nmodels oscillating behavior well, where the number of oscillations is the number of <br>\nparameter starting values given in start. The starting values after optimization will be<br>\nfound in the slot coh of the object of class theta corresponding to each dataset modeled.\n<b>Comments</b>: required additional argument <i>start</i> to be set.\n</html>");
        jRBCohspecSeq.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBCohspecSeqActionPerformed(evt);
            }
        });

        cohspec_buttonGroup.add(cohspec_mix);
        cohspec_mix.setText("Mixed Sequential/IRF");
        cohspec_mix.setToolTipText("<html>\n<b>TIMP function</b>: cohspec<br>\n<b>Argument</b>: type=\"mix\"<br>\n<b>Description</b>: if this options is used, then a sequential exponential decay model is applied <br>\n along with a model that follows the time profile of the IRF; the coherent artifact/scatter<br>\n is then a linear superposition of these two models. This starting value for the sequential model<br>\n are contained in an additional list element <i>start</i>.\n<b>Comments</b>: requires additional argument <i>start</i> to be set.\n</html>");

        jLabel2.setText("Start parameters (comma seperated list of numbers):");

        cohspec_start.setColumns(10);
        cohspec_start.setEnabled(false);

        jLabel4.setText("from:");
        jLabel4.setEnabled(false);

        jCBCohSpec0.setText("set amplitude of the coherent artifact/scatter to 0");
        jCBCohSpec0.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCohSpec0ActionPerformed(evt);
            }
        });

        jTFCohSpec0From.setEnabled(false);

        jLabel5.setText("to:");
        jLabel5.setEnabled(false);

        jTFCohSpec0To.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTFCohSpec0From, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28).addComponent(jLabel5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTFCohSpec0To, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(jCBCohSpec0).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jRBCohspecNo).addComponent(jRBCohspecIrf)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jRBCohspecFreeirfdisp).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addComponent(cohspec_irfmulti))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cohspec_mix).addComponent(jRBCohspecSeq))).addComponent(jLabel1).addComponent(jLabel2).addComponent(cohspec_start, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jRBCohspecNo).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jRBCohspecIrf)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jRBCohspecFreeirfdisp).addComponent(jRBCohspecSeq)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cohspec_mix).addComponent(cohspec_irfmulti)))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cohspec_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCBCohSpec0).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jTFCohSpec0From, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(jTFCohSpec0To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(14, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void jCBCohSpec0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCohSpec0ActionPerformed
        updateEnabled(jCBCohSpec0.isSelected());
        setValue(jCBCohSpec0, jCBCohSpec0.isSelected());
    }//GEN-LAST:event_jCBCohSpec0ActionPerformed

    private void jRBCohspecNoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRBCohspecNoStateChanged
    }//GEN-LAST:event_jRBCohspecNoStateChanged

    private void jRBCohspecNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBCohspecNoActionPerformed
    }//GEN-LAST:event_jRBCohspecNoActionPerformed

    private void jRBCohspecSeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBCohspecSeqActionPerformed
    }//GEN-LAST:event_jRBCohspecSeqActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup cohspec_buttonGroup;
    private javax.swing.JRadioButton cohspec_irfmulti;
    private javax.swing.JRadioButton cohspec_mix;
    private javax.swing.JTextField cohspec_start;
    private javax.swing.JCheckBox jCBCohSpec0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton jRBCohspecFreeirfdisp;
    private javax.swing.JRadioButton jRBCohspecIrf;
    private javax.swing.JRadioButton jRBCohspecNo;
    private javax.swing.JRadioButton jRBCohspecSeq;
    private javax.swing.JTextField jTFCohSpec0From;
    private javax.swing.JTextField jTFCohSpec0To;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setValue(JComponent source, Object value) {
        if (source == jRBCohspecNo) {
            cohspecPanelModel.getCohspec().setSet(!(Boolean) value);
        }
        if (source == jRBCohspecIrf) {
            cohspecPanelModel.getCohspec().setType("irf");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source == jRBCohspecFreeirfdisp) {
            cohspecPanelModel.getCohspec().setType("freeirfdisp");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source == cohspec_irfmulti) {
            cohspecPanelModel.getCohspec().setType("irfmulti");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source == jRBCohspecSeq) {
            cohspecPanelModel.getCohspec().setType("seq");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source == cohspec_mix) {
            cohspecPanelModel.getCohspec().setType("mix");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source == cohspec_start) {
            cohspecPanelModel.setCoh((String) value);
        }

        if (source == jCBCohSpec0) {
            cohspecPanelModel.setClp0Enabled(jCBCohSpec0.isSelected());
        }

        if (source == jTFCohSpec0From) {
            cohspecPanelModel.setClp0Min(Double.valueOf((String) value));
        }
        if (source == jTFCohSpec0To) {
            cohspecPanelModel.setClp0Max(Double.valueOf((String) value));
        }


        endUIChange();
    }

    @Override
    protected void endUIChange() {// signalUIChange() is deprecated{
        dObj.modelUpdatedFromUI();
    }

    @Override
    public void linkButtonPressed(Object arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JComponent getErrorComponent(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateEnabled(boolean setEnabl) {
        jLabel4.setEnabled(setEnabl);
        jLabel5.setEnabled(setEnabl);
        jTFCohSpec0From.setEnabled(setEnabl);
        jTFCohSpec0To.setEnabled(setEnabl);
    }
}
