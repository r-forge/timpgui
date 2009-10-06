/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JVectorCell.java
 *
 * Created on Sep 25, 2009, 11:47:08 AM
 */

package nl.vu.nat.tgmeditor.panels;

/**
 *
 * @author slapten
 */
public class JVectorCellPanel extends javax.swing.JPanel{

    /** Creates new form JVectorCell */
    public JVectorCellPanel() {
        initComponents();
    }

    public JVectorCellPanel(JVectorValueClass jVecVal) {
        initComponents();
        jCheckBox1.setEnabled(jVecVal.isFixed());
        jTextField1.setText(String.valueOf(jVecVal.getValue()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(40, 40));

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setName("jTextField1"); // NOI18N

        jCheckBox1.setName("jCheckBox1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addComponent(jCheckBox1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    public boolean isValueFixed() {
        return jCheckBox1.isSelected();
    }

    public void setValueFixed(boolean fixedVal) {
        jCheckBox1.setSelected(fixedVal);
    }

    public double getValueNumber() {
        return Double.parseDouble(jTextField1.getText());
    }

    public void setValueNumber(double val) {
        jTextField1.setText(String.valueOf(val));
    }
}