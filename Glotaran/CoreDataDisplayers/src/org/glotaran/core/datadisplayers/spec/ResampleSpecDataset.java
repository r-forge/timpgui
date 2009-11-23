package org.glotaran.core.datadisplayers.spec;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 *
 * @author lsp
 */
public class ResampleSpecDataset extends java.awt.Panel {
    private int numXCh, numYCh;

    /** Creates new form ResampleSpecDataset */
    public ResampleSpecDataset() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCbAplyToSelArea = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTYnum = new javax.swing.JTextField();
        jTXnum = new javax.swing.JTextField();
        jCbResampleY = new javax.swing.JCheckBox();
        jCbResampleX = new javax.swing.JCheckBox();
        jCCreatNewDataset = new javax.swing.JCheckBox();

        jCbAplyToSelArea.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jCbAplyToSelArea.text")); // NOI18N
        jCbAplyToSelArea.setEnabled(false);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jLabel1.text")); // NOI18N
        jLabel1.setEnabled(false);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jLabel2.text")); // NOI18N
        jLabel2.setEnabled(false);

        jTYnum.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jTYnum.text")); // NOI18N
        jTYnum.setEnabled(false);
        jTYnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTYnumKeyReleased(evt);
            }
        });

        jTXnum.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jTXnum.text")); // NOI18N
        jTXnum.setEnabled(false);
        jTXnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTXnumKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTXnumKeyTyped(evt);
            }
        });

        jCbResampleY.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jCbResampleY.text")); // NOI18N
        jCbResampleY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCbResampleYActionPerformed(evt);
            }
        });

        jCbResampleX.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jCbResampleX.text")); // NOI18N
        jCbResampleX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCbResampleXActionPerformed(evt);
            }
        });

        jCCreatNewDataset.setText(org.openide.util.NbBundle.getMessage(ResampleSpecDataset.class, "ResampleSpecDataset.jCCreatNewDataset.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTYnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTXnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCbResampleX)
                            .addComponent(jCbResampleY))
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCbAplyToSelArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jCCreatNewDataset)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCbResampleX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTXnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCbResampleY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTYnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCbAplyToSelArea)
                    .addComponent(jCCreatNewDataset))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCbResampleXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCbResampleXActionPerformed
        jTXnum.setEnabled(jCbResampleX.isSelected());
        jLabel2.setEnabled(jCbResampleX.isSelected());
    }//GEN-LAST:event_jCbResampleXActionPerformed

    private void jCbResampleYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCbResampleYActionPerformed
        jTYnum.setEnabled(jCbResampleY.isSelected());
        jLabel1.setEnabled(jCbResampleY.isSelected());
    }//GEN-LAST:event_jCbResampleYActionPerformed

    private void jTXnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTXnumKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTXnumKeyTyped

    private void jTXnumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTXnumKeyReleased
        // TODO add your handling code here:
        if (Integer.parseInt(jTXnum.getText())>numXCh){
            NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("set_correct_chanNum") +
                    NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("setLess")+ String.valueOf(numXCh)));
            DialogDisplayer.getDefault().notify(errorMessage);
            jTXnum.setText(String.valueOf(numXCh));
        }
    }//GEN-LAST:event_jTXnumKeyReleased

    private void jTYnumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTYnumKeyReleased
        // TODO add your handling code here:
        if (Integer.parseInt(jTYnum.getText())>numYCh){
            NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("set_correct_chanNum") +
                    NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("setLess") + String.valueOf(numYCh)));
            DialogDisplayer.getDefault().notify(errorMessage);
            jTYnum.setText(String.valueOf(numYCh));
        }

    }//GEN-LAST:event_jTYnumKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCCreatNewDataset;
    private javax.swing.JCheckBox jCbAplyToSelArea;
    private javax.swing.JCheckBox jCbResampleX;
    private javax.swing.JCheckBox jCbResampleY;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTXnum;
    private javax.swing.JTextField jTYnum;
    // End of variables declaration//GEN-END:variables


    public void setInitialNumbers(int x, int y){
        jTXnum.setText(Integer.toString(x));
        jTYnum.setText(Integer.toString(y));
        numXCh = x;
        numYCh = y;
    }

    public boolean getResampleXState(){
        return jCbResampleX.isSelected();
    }

    public boolean getResampleYState(){
        return jCbResampleY.isSelected();
    }

    public boolean getAplyToState(){
        return jCbAplyToSelArea.isSelected();
    }

    public int getResampleXNum(){
        return Integer.parseInt(jTXnum.getText());
    }

    public int getResampleYNum(){
        return Integer.parseInt(jTYnum.getText());
    }

    public boolean getNewDatasetState(){
        return jCCreatNewDataset.isSelected();
    }

}
