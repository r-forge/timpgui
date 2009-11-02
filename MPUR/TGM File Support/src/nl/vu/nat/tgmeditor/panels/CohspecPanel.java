package nl.vu.nat.tgmeditor.panels;

import javax.swing.JComponent;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
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
    public CohspecPanel(SectionView view, TgmDataObject dObj, CohspecPanelModel cohspecPanelModel){
        super(view);    
        this.dObj=dObj;
        this.cohspecPanelModel=cohspecPanelModel;
        initComponents();
        if (cohspecPanelModel.getCohspec().isSet()) {
           if (cohspecPanelModel.getCohspec().getType().compareTo("irf")==0) {
               cohspec_irf.setSelected(true);}
           if (cohspecPanelModel.getCohspec().getType().compareTo("freeirfdisp")==0) {
               cohspec_freeirfdisp.setSelected(true);}
           if (cohspecPanelModel.getCohspec().getType().compareTo("irfmulti")==0) {
               cohspec_irfmulti.setSelected(true);}
           if (cohspecPanelModel.getCohspec().getType().compareTo("seq")==0) {
               cohspec_seq.setSelected(true);}
           if (cohspecPanelModel.getCohspec().getType().compareTo("mix")==0) {
               cohspec_mix.setSelected(true);}
        } else {
            cohspec_no.setSelected(true);
        }
        cohspec_start.setText(cohspecPanelModel.getCoh()); //comma separated list of doubles, f.i.: "1,2,3,80,99"

        if (cohspecPanelModel.isClp0Enabled()!=null){
            jCBCohSpec0.setSelected(cohspecPanelModel.isClp0Enabled());
            updateEnabled(cohspecPanelModel.isClp0Enabled());
            jTFCohSpec0From.setText(String.valueOf(cohspecPanelModel.getClp0Min()));
            jTFCohSpec0To.setText(String.valueOf(cohspecPanelModel.getClp0Max()));
        }

        addModifier(cohspec_start);
        addModifier(cohspec_no);
        addModifier(cohspec_irf);
        addModifier(cohspec_freeirfdisp);
        addModifier(cohspec_irfmulti);
        addModifier(cohspec_seq);
        addModifier(cohspec_mix);
        addModifier(jCBCohSpec0);
        addModifier(jTFCohSpec0From);
        addModifier(jTFCohSpec0To);
        
        //endUIChange();
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
        cohspec_no = new javax.swing.JRadioButton();
        cohspec_irf = new javax.swing.JRadioButton();
        cohspec_freeirfdisp = new javax.swing.JRadioButton();
        cohspec_irfmulti = new javax.swing.JRadioButton();
        cohspec_seq = new javax.swing.JRadioButton();
        cohspec_mix = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        cohspec_start = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCBCohSpec0 = new javax.swing.JCheckBox();
        jTFCohSpec0From = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFCohSpec0To = new javax.swing.JTextField();

        jLabel1.setText("model coherent artifact/scatter?");

        cohspec_buttonGroup.add(cohspec_no);
        cohspec_no.setSelected(true);
        cohspec_no.setText("no");
        cohspec_no.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

        cohspec_buttonGroup.add(cohspec_irf);
        cohspec_irf.setText("irf");
        cohspec_irf.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

        cohspec_buttonGroup.add(cohspec_freeirfdisp);
        cohspec_freeirfdisp.setText("freeirfdisp");
        cohspec_freeirfdisp.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

        cohspec_buttonGroup.add(cohspec_irfmulti);
        cohspec_irfmulti.setText("irfmulti");
        cohspec_irfmulti.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

        cohspec_buttonGroup.add(cohspec_seq);
        cohspec_seq.setText("seq");
        cohspec_seq.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

        cohspec_buttonGroup.add(cohspec_mix);
        cohspec_mix.setText("mix");
        cohspec_mix.setToolTipText("cohspec: *** Object of class \"list\" describing the model for coherent artifact/scatter com-\n    ponent(s) containing the element type and optionally the element numdatasets. The\n    element type can be set as follows:\n\n\"irf\": if type=\"irf\", the coherent artifact/scatter has the time proﬁle of the IRF.\n        \n\"freeirfdisp\": if type=\"freeirfdisp\", the coherent artifact/scatter has a Gaus-\n        sian time proﬁle whose location and width are parameterized in the vector coh.\n\n\"irfmulti\": if type=\"irfmulti\" the time proﬁle of the IRF is used for the coherent\n          artifact/scatter model, but the IRF parameters are taken per dataset (for the multidataset\n          case), and the integer argument numdatasets must be equal to the number of datasets\n          modeled.\n\n\"seq\": if type=\"seq\" a sequential exponential decay model is applied, whose starting\n          value are contained in an additional list element start. This often models oscillating\n          behavior well, where the number of oscillations is the number of parameter starting values\n          given in start. The starting values after optimization will be found in the slot coh of\n          the object of class theta corresponding to each dataset modeled.\n\n \"mix\": if type=\"mix\" if type=\"mix\" a sequential exponential decay model is applied\n          along with a model that follows the time proﬁle of the IRF; the coherent artifact/scatter\n          is then a linear superposition of these two models; see the above description of seq for\n          how to supply the starting values.");

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
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCohSpec0From, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCohSpec0To, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCBCohSpec0)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cohspec_no)
                            .addComponent(cohspec_irf))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cohspec_freeirfdisp)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cohspec_irfmulti)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cohspec_mix)
                            .addComponent(cohspec_seq)))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cohspec_start, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cohspec_no)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cohspec_irf))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cohspec_freeirfdisp)
                            .addComponent(cohspec_seq))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cohspec_mix)
                            .addComponent(cohspec_irfmulti))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cohspec_start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCBCohSpec0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFCohSpec0From, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTFCohSpec0To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCBCohSpec0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCohSpec0ActionPerformed
        updateEnabled(jCBCohSpec0.isSelected());
    }//GEN-LAST:event_jCBCohSpec0ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup cohspec_buttonGroup;
    private javax.swing.JRadioButton cohspec_freeirfdisp;
    private javax.swing.JRadioButton cohspec_irf;
    private javax.swing.JRadioButton cohspec_irfmulti;
    private javax.swing.JRadioButton cohspec_mix;
    private javax.swing.JRadioButton cohspec_no;
    private javax.swing.JRadioButton cohspec_seq;
    private javax.swing.JTextField cohspec_start;
    private javax.swing.JCheckBox jCBCohSpec0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTFCohSpec0From;
    private javax.swing.JTextField jTFCohSpec0To;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setValue(JComponent source, Object value) {
        if (source==cohspec_no) {
            cohspecPanelModel.getCohspec().setSet(!(Boolean)value);
        }
        if (source==cohspec_irf) {
            cohspecPanelModel.getCohspec().setType("irf");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source==cohspec_freeirfdisp) {
            cohspecPanelModel.getCohspec().setType("freeirfdisp");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source==cohspec_irfmulti) {
            cohspecPanelModel.getCohspec().setType("irfmulti");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source==cohspec_seq) {
            cohspecPanelModel.getCohspec().setType("seq");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source==cohspec_mix) {
            cohspecPanelModel.getCohspec().setType("mix");
            cohspecPanelModel.getCohspec().setSet(true);
        }
        if (source==cohspec_start) {
            cohspecPanelModel.setCoh((String)value);
        }

        if (source==jCBCohSpec0) {
            cohspecPanelModel.setClp0Enabled(jCBCohSpec0.isSelected());
        }

        if (source==jTFCohSpec0From) {
            cohspecPanelModel.setClp0Min(Double.valueOf((String)value));
        }
        if (source==jTFCohSpec0To) {
            cohspecPanelModel.setClp0Max(Double.valueOf((String)value));
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

    private void updateEnabled(boolean setEnabl){
        jLabel4.setEnabled(setEnabl);
        jLabel5.setEnabled(setEnabl);
        jTFCohSpec0From.setEnabled(setEnabl);
        jTFCohSpec0To.setEnabled(setEnabl);
    }
}
