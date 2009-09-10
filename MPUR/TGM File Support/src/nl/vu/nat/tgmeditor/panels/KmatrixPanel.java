/*
 * KinparPanel.java
 *
 * Created on аўторак, 5, жніўня 2008, 19.49
 */
package nl.vu.nat.tgmeditor.panels;

import javax.swing.JComponent;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import nl.vu.nat.tgmodels.tgm.KinPar;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;



/**
 *
 * @author  Sergey
 */
public class KmatrixPanel extends SectionInnerPanel {

    private TgmDataObject dObj;
    private KinparPanelModel kinparPanelModel;
    private KinModTableModel model;

    private Object[] defRow;
    private Object[] colNames;
    private Object[] newRow;

    /** Creates new form KinparPanel */
    public KmatrixPanel(SectionView view, TgmDataObject dObj, KinparPanelModel kinparPanelModel) {
        super(view);
        this.dObj = dObj;
        this.kinparPanelModel = kinparPanelModel;
        initComponents();
        
        jSNumOfComponents.setModel(new SpinnerNumberModel(kinparPanelModel.getKinpar().size(), 1, null, 1));
                    
        defRow = new Object[]{new Double(0), new Boolean(false), new Boolean(false), new Boolean(false), new Double(0), new Double(0)};
        colNames = new Object[]{"Starting value", "Fixed", "FreeBetwDatasets", "Constrained", "Min", "Max"};
        model = new KinModTableModel(colNames, 0);
        
         for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
            newRow = new Object[]{
                kinparPanelModel.getKinpar().get(i).getStart(),
                kinparPanelModel.getKinpar().get(i).isFixed(),
                kinparPanelModel.getKinpar().get(i).isModeldiffsFree(),
                kinparPanelModel.getKinpar().get(i).isConstrained(),
                kinparPanelModel.getKinpar().get(i).getMin(),
                kinparPanelModel.getKinpar().get(i).getMax()
            };
            model.addRow(newRow);
            
        }
        jTKMatrix.setModel(model);
        
        // Add listerners
        jTKMatrix.getModel().addTableModelListener(model);
    }

    @Override
    public void setValue(JComponent source, Object value) {
        if (source ==jTKMatrix) {
        
            if (model.getRowCount()>kinparPanelModel.getKinpar().size()) {
                KinPar kp = new KinPar();
                kp.setStart((Double)model.getValueAt((model.getRowCount()-1),0));
                kp.setFixed((Boolean)model.getValueAt((model.getRowCount()-1),1));
                kp.setModeldiffsFree((Boolean)model.getValueAt((model.getRowCount()-1),2));
                kp.setConstrained((Boolean)model.getValueAt((model.getRowCount()-1),3));
                kp.setMin((Double)model.getValueAt((model.getRowCount()-1),4));
                kp.setMax((Double)model.getValueAt((model.getRowCount()-1),5));
                kinparPanelModel.getKinpar().add(kp);
            } else if (model.getRowCount()<kinparPanelModel.getKinpar().size()) {
                kinparPanelModel.getKinpar().remove(kinparPanelModel.getKinpar().size()-1);
            }
         
        for (int i = 0; i < model.getRowCount(); i++) {
                kinparPanelModel.getKinpar().get(i).setStart((Double)model.getValueAt(i,0));
                kinparPanelModel.getKinpar().get(i).setFixed((Boolean)model.getValueAt(i,1));
                kinparPanelModel.getKinpar().get(i).setModeldiffsFree((Boolean)model.getValueAt(i,2));
                kinparPanelModel.getKinpar().get(i).setConstrained((Boolean)model.getValueAt(i,3));
                kinparPanelModel.getKinpar().get(i).setMin((Double)model.getValueAt(i,4));
                kinparPanelModel.getKinpar().get(i).setMax((Double)model.getValueAt(i,5));
            }
        }
        endUIChange();
    }
    
    @Override
    protected void endUIChange() {// signalUIChange() is deprecated{
        dObj.modelUpdatedFromUI();
    }

    public void linkButtonPressed(Object arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JComponent getErrorComponent(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    class KinModTableModel extends DefaultTableModel implements TableModelListener {

        private Class[] types = new Class[]{Double.class, Boolean.class, Boolean.class, Boolean.class,Double.class, Double.class};

        private KinModTableModel() {
            super();
        }

        private KinModTableModel(Object[] ColNames, int i) {
            super(ColNames, i);
        }

        @Override
        public Class getColumnClass(int c) {
            return types[c];
        }

        public void tableChanged(TableModelEvent event) {
            //if (jTKinParamTable.isValid()) {
            setValue(jTKMatrix, this);
            endUIChange();
           // }
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
        jSNumOfComponents = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTKMatrix = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTBranches = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTJVector = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("Size of Kmatrix");

        jSNumOfComponents.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSNumOfComponentsStateChanged(evt);
            }
        });

        jScrollPane1.setViewportView(jTKMatrix);

        jScrollPane2.setViewportView(jTBranches);

        jScrollPane3.setViewportView(jTJVector);

        jLabel2.setText("J Vector");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, jScrollPane2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, jScrollPane2});

    }// </editor-fold>//GEN-END:initComponents

private void jSNumOfComponentsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSNumOfComponentsStateChanged
// TODO add your handling code here:]
//    jSNumOfComponents.getPreviousValue().
//    for (int i = 0; i < Math.abs((Integer)jSNumOfComponents.getValue()-(Integer)jSNumOfComponents.getPreviousValue()); i++) {
    if ((Integer) jSNumOfComponents.getValue() > model.getRowCount()) {
        model.addRow(defRow);
    } else {
        model.removeRow(model.getRowCount() - 1);
        
//    DefaultTableModel model = (DefaultTableModel) jTKinParamTable.getModel();
//    model.setRowCount((Integer)jSNumOfComponents.getValue());
//    model.addRow(new Object[]{"v1", "v2","v3"});
    //   jTKinParamTable.
//    }
    
    }
    endUIChange();
}//GEN-LAST:event_jSNumOfComponentsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSNumOfComponents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTBranches;
    private javax.swing.JTable jTJVector;
    private javax.swing.JTable jTKMatrix;
    // End of variables declaration//GEN-END:variables
}
