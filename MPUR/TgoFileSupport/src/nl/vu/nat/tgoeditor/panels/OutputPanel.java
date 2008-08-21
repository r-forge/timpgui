/*
 * OutputPanel.java
 *
 * Created on August 3, 2008, 1:46 PM
 */
package nl.vu.nat.tgoeditor.panels;

import nl.vu.nat.tgofilesupport.TgoDataObject;
import javax.swing.JComponent;

import nl.vu.nat.tgmodels.tgo.OutputPanelElements;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;

/**
 *
 * @author  kate
 */
public class OutputPanel extends SectionInnerPanel {

    private TgoDataObject dObj;
    private OutputPanelElements outputData;

    /** Creates new form OutputPanel */
    public OutputPanel(SectionView view, TgoDataObject dObj, OutputPanelElements outputData) {
        super(view);
        this.dObj = dObj;
        this.outputData = outputData;
        initComponents();
        jCheckBox1.setSelected(outputData.isWritecon());
        jCheckBox2.setSelected(outputData.isWritespec());
        jCheckBox3.setSelected(outputData.isWritenormspec());
        jCheckBox4.setSelected(outputData.isWritefit());
        jCheckBox5.setSelected(outputData.isWritefitivo());
        jCheckBox6.setSelected(outputData.isWritedata());
        jCheckBox7.setSelected(outputData.isWriteclperr());
        jTOutputFilenameTextfield.setText(outputData.getTitle());
        if (outputData.getOutput().getFormat().compareTo("ps") == 0) {
            jRadioButton3.setSelected(true);
        } else if (outputData.getOutput().getFormat().compareTo("pdf") == 0) {
            jRadioButton2.setSelected(true);
        } else {
            jRadioButton1.setSelected(true);
        }
        addModifier(jCheckBox1);
        addModifier(jCheckBox2);
        addModifier(jCheckBox3);
        addModifier(jCheckBox4);
        addModifier(jCheckBox5);
        addModifier(jCheckBox6);
        addModifier(jCheckBox7);
        addModifier(jRadioButton1);
        addModifier(jRadioButton2);
        addModifier(jRadioButton3);
        addModifier(jTOutputFilenameTextfield);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTOutputFilenameTextfield = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("Check ASCII results to write:");

        jCheckBox1.setText("writecon");

        jCheckBox2.setText("writespec");

        jCheckBox3.setText("writenormspec");

        jCheckBox4.setText("writefit");

        jCheckBox5.setText("writefitivo");

        jCheckBox6.setText("writedata");

        jCheckBox7.setText("writeclperr");

        jLabel2.setText("Output plot files?");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("No");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Yes, as pdf");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Yes, as postscript");

        jTOutputFilenameTextfield.setColumns(10);

        jLabel3.setText("Plot filenames:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton3)
                                    .addComponent(jLabel2)
                                    .addComponent(jRadioButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTOutputFilenameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox4))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jTOutputFilenameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton3))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JTextField jTOutputFilenameTextfield;
    // End of variables declaration//GEN-END:variables
    @Override
    public void setValue(JComponent source, Object value) {
        if (source == jCheckBox1) {
            outputData.setWritecon(((Boolean) value));
        } if (source == jCheckBox2) {
            outputData.setWritespec(((Boolean) value));
        } if (source == jCheckBox3) {
           outputData.setWritenormspec(((Boolean) value));
        } if (source == jCheckBox4) {
            outputData.setWritefit(((Boolean) value));
        } if (source == jCheckBox5) {
            outputData.setWritefitivo(((Boolean) value));
        } if (source == jCheckBox6) {
            outputData.setWritedata(((Boolean) value));
        } if (source == jCheckBox7) {
            outputData.setWriteclperr(((Boolean) value));
        }
        if (source == jRadioButton1) {
            outputData.getOutput().setEnabled(false);
            outputData.getOutput().setFormat("");
        } else if (source == jRadioButton2) {
            outputData.getOutput().setEnabled(true);
            outputData.getOutput().setFormat("pdf");
        } else if (source == jRadioButton3) {
            outputData.getOutput().setEnabled(true);
            outputData.getOutput().setFormat("ps");
        }
        if (source == jTOutputFilenameTextfield) {
        outputData.setTitle((String)value);
        }
        endUIChange();
    }

    public void linkButtonPressed(Object arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JComponent getErrorComponent(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void endUIChange() { //signalUIChange() {
        dObj.modelUpdatedFromUI();
    }
}
