/*
 * TgmPanel.java
 *
 * Created on July 20, 2008, 11:19 PM
 */

package org.glotaran.tgmeditor.panels;

import javax.swing.JComponent;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;

/**
 *
 * @author  joris
 */
public class TgmPanel extends SectionInnerPanel {
    private TgmDataObject dObj;
    private Tgm tgm;

    /** Creates new form TgmPanel */
    public TgmPanel(SectionView view, TgmDataObject dObj, Tgm tgm) {
        super(view);
        this.dObj=dObj;
        this.tgm=tgm;
        initComponents();
        // Here is where we initialize the gui's components.
        if (tgm.getDat().getModelName()==null) {
            tgm.getDat().setModelName("defaultModelName"); endUIChange();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(TgmPanel.class, "TgmPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setValue(JComponent source, Object value) {
      
    }
    
    @Override
    protected void endUIChange() { //signalUIChange() {
        dObj.modelUpdatedFromUI();
    }

    public void linkButtonPressed(Object arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JComponent getErrorComponent(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
