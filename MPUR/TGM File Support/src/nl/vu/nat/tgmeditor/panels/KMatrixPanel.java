
package nl.vu.nat.tgmeditor.panels;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import nl.vu.nat.tgmodels.tgm.DoubleMatrix;
import nl.vu.nat.tgmodels.tgm.DoubleMatrix.Data;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;
import nl.vu.nat.tgmodels.tgm.KinPar;

/**
 *
 * @author  Sergey
 */
public class KMatrixPanel extends SectionInnerPanel {//implements TableModelListener {

    private TgmDataObject dObj;
    private KMatrixPanelModel kMatrixPanelModel;
    private KMatrixTableModel model1, model2, jVec;
    private RowHeader rowHeader1, rowHeader2, rowHeaderJVec;

    private int matrixSize = 0;

    /** Creates new form KMatrixPanel */
    public KMatrixPanel(SectionView view, TgmDataObject dObj, KMatrixPanelModel kMatrixPanelModel) {
        super(view);
        this.dObj = dObj;
        this.kMatrixPanelModel = kMatrixPanelModel;
        rowHeader1 = new RowHeader(30, 30);
        rowHeader2 = new  RowHeader(30, 30);
        rowHeaderJVec = new  RowHeader(30, 30);
        initComponents();

        matrixSize = kMatrixPanelModel.getJVector().size();
        jSNumOfComponents.setModel(new SpinnerNumberModel(matrixSize, 0, null, 1));

        model1 = new KMatrixTableModel(0, 0);
        model2 = new KMatrixTableModel(0, 0);
        jVec = new KMatrixTableModel(0, 0);


            for (int i = 0; i <matrixSize; i++){
                model1.addColumn(String.valueOf(i+1));
                model2.addColumn(String.valueOf(i+1));
                jVec.addColumn(String.valueOf(i+1));
                jTKMatrix.getColumnModel().addColumn(new KMatrColumn(i, 30));
                jTBranches.getColumnModel().addColumn(new KMatrColumn(i, 30));
                jTJVector.getColumnModel().addColumn(new KMatrColumn(i, 30));

            }
            jVec.addRow(kMatrixPanelModel.getJVector().toArray().clone());
            for (int i = 0; i <matrixSize; i++){
                //model1.addRow(kMatrixPanelModel.getK1Matrix().getRow()[i].toArray().clone());
                model1.addRow(kMatrixPanelModel.getK1Matrix().getData().get(i).getRow().toArray().clone());
                model2.addRow(kMatrixPanelModel.getK2Matrix().getData().get(i).getRow().toArray().clone());
                rowHeader1.addRow(String.valueOf(i));
                rowHeader2.addRow(String.valueOf(i));
            }
        

        model1.addTableModelListener(model1);
        model2.addTableModelListener(model2);
        jVec.addTableModelListener(jVec);

//         for (int i = 0; i < kMatrixPanelModel.getKinpar().size(); i++) {
//            newRow = new Object[]{
//                kMatrixPanelModel.getKinpar().get(i).getStart(),
//                kMatrixPanelModel.getKinpar().get(i).isFixed(),
//                kMatrixPanelModel.getKinpar().get(i).isModeldiffsFree(),
//                kMatrixPanelModel.getKinpar().get(i).isConstrained(),
//                kMatrixPanelModel.getKinpar().get(i).getMin(),
//                kMatrixPanelModel.getKinpar().get(i).getMax()
//            };
//            model.addRow(newRow);
//
//        }

        jTKMatrix.setModel(model1);
        JScrollPane jscpane = (JScrollPane) jTKMatrix.getParent().getParent();
        jscpane.setRowHeaderView(rowHeader1);
        jscpane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeader1.getTableHeader());
        
        jTBranches.setModel(model2);
        JScrollPane jscpane2 = (JScrollPane) jTBranches.getParent().getParent();
        jscpane2.setRowHeaderView(rowHeader2);
        jscpane2.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeader2.getTableHeader());

        jTJVector.setModel(jVec);
        JScrollPane jscpane3 = (JScrollPane) jTJVector.getParent().getParent();
        rowHeaderJVec.addRow("1");
        jscpane3.setRowHeaderView(rowHeaderJVec);
        jscpane3.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderJVec.getTableHeader());


        

//        model2.addTableModelListener(model2);
        // Add listerners
//        jTKMatrix.getModel().addTableModelListener(model1);

    }

    @Override
    public void setValue(JComponent source, Object value) {

        if (source ==jTKMatrix) {
            int val;
            kMatrixPanelModel.getK1Matrix().getData().clear();
            Data tempData;
            for (int j=0; j<matrixSize; j++){
                tempData = new Data();
                for (int i=0; i<matrixSize; i++){
                    if (model1.getValueAt(j, i) == null)
                        val = 0;
                    else
                        val =(Integer)model1.getValueAt(j, i);
                   tempData.getRow().add(val);
                }
                kMatrixPanelModel.getK1Matrix().getData().add(tempData);
            }
        
        
//            if (model.getRowCount()>kMatrixPanelModel.getKinpar().size()) {
//                KinPar kp = new KinPar();
//                kp.setStart((Double)model.getValueAt((model.getRowCount()-1),0));
//                kp.setFixed((Boolean)model.getValueAt((model.getRowCount()-1),1));
//                kp.setModeldiffsFree((Boolean)model.getValueAt((model.getRowCount()-1),2));
//                kp.setConstrained((Boolean)model.getValueAt((model.getRowCount()-1),3));
//                kp.setMin((Double)model.getValueAt((model.getRowCount()-1),4));
//                kp.setMax((Double)model.getValueAt((model.getRowCount()-1),5));
//                kMatrixPanelModel.getKinpar().add(kp);
//            } else if (model.getRowCount()<kMatrixPanelModel.getKinpar().size()) {
//                kMatrixPanelModel.getKinpar().remove(kMatrixPanelModel.getKinpar().size()-1);
//            }
         
//        for (int i = 0; i < model.getRowCount(); i++) {
//                kMatrixPanelModel.getKinpar().get(i).setStart((Double)model.getValueAt(i,0));
//                kMatrixPanelModel.getKinpar().get(i).setFixed((Boolean)model.getValueAt(i,1));
//                kMatrixPanelModel.getKinpar().get(i).setModeldiffsFree((Boolean)model.getValueAt(i,2));
//                kMatrixPanelModel.getKinpar().get(i).setConstrained((Boolean)model.getValueAt(i,3));
//                kMatrixPanelModel.getKinpar().get(i).setMin((Double)model.getValueAt(i,4));
//                kMatrixPanelModel.getKinpar().get(i).setMax((Double)model.getValueAt(i,5));
//            }
        }
        if (source ==jTBranches) {
            int val;
            kMatrixPanelModel.getK2Matrix().getData().clear();
            Data tempData;
            for (int j=0; j<matrixSize; j++){
                tempData = new Data();
                for (int i=0; i<matrixSize; i++){
                    if (model2.getValueAt(j, i) == null)
                        val = 0;
                    else
                        val =(Integer)model2.getValueAt(j, i);
                   tempData.getRow().add(val);
                }
                kMatrixPanelModel.getK2Matrix().getData().add(tempData);
            }
        }

        if (source ==jTJVector) {
            kMatrixPanelModel.getJVector().clear();
            int val;
            for (int i=0; i<matrixSize; i++){
                if (jVec.getValueAt(0, i) == null)
                    val = 0;
                else
                    val =(Integer)jVec.getValueAt(0, i);
                kMatrixPanelModel.getJVector().add(val);
            }
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

//    @Override
//    public void tableChanged(TableModelEvent e) {
//
//         setValue(jTKMatrix, this);
//    }

    class KMatrixTableModel extends DefaultTableModel implements TableModelListener {

        private KMatrixTableModel() {
            super();
        }

        private KMatrixTableModel(int n, int m) {
            super(n, m);
        }

        @Override
        public Class getColumnClass(int c) {
            return Integer.class;
        }

        @Override
        public void tableChanged(TableModelEvent event) {
            if (event.getSource().equals(model1)) {
                setValue(jTKMatrix, this);
            }
            else
                if (event.getSource().equals(model2)) {
                    setValue(jTBranches, this);
                }
                else
                    if (event.getSource().equals(jVec)) {
                    setValue(jTJVector, this);
                }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("Size of Kmatrix");

        jSNumOfComponents.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSNumOfComponentsStateChanged(evt);
            }
        });

        jTKMatrix.setAutoCreateColumnsFromModel(false);
        jTKMatrix.setToolTipText("Specification of the K-Matrix");
        jTKMatrix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTKMatrix.setRowHeight(30);
        jTKMatrix.getTableHeader().setResizingAllowed(false);
        jTKMatrix.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTKMatrix);

        jTBranches.setAutoCreateColumnsFromModel(false);
        jTBranches.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTBranches.setRowHeight(30);
        jScrollPane2.setViewportView(jTBranches);

        jTJVector.setAutoCreateColumnsFromModel(false);
        jTJVector.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTJVector.setRowHeight(30);
        jScrollPane3.setViewportView(jTJVector);

        jLabel2.setText("J Vector");

        jLabel3.setText("Kmatrix");

        jLabel4.setText("Branches");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, jScrollPane2, jScrollPane3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, jScrollPane2});

    }// </editor-fold>//GEN-END:initComponents

private void jSNumOfComponentsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSNumOfComponentsStateChanged

    model1.removeTableModelListener(model1);
    model2.removeTableModelListener(model2);
    jVec.removeTableModelListener(jVec);

    if ((Integer) jSNumOfComponents.getValue() > matrixSize) {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.addColumn(String.valueOf(matrixSize));
        model2.addColumn(String.valueOf(matrixSize));
        jVec.addColumn(String.valueOf(matrixSize));
        jTKMatrix.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTBranches.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTJVector.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        model1.addRow(new Vector(matrixSize));
        model2.addRow(new Vector(matrixSize));
        rowHeader1.addRow(String.valueOf(matrixSize));
        rowHeader2.addRow(String.valueOf(matrixSize));
 
    } else {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.removeRow(matrixSize);
        model2.removeRow(matrixSize);
        rowHeader1.removeRow(matrixSize);
        rowHeader2.removeRow(matrixSize);
        jTKMatrix.getColumnModel().removeColumn(jTKMatrix.getColumnModel().getColumn(matrixSize));
        jTBranches.getColumnModel().removeColumn(jTBranches.getColumnModel().getColumn(matrixSize));
        jTJVector.getColumnModel().removeColumn(jTJVector.getColumnModel().getColumn(matrixSize));
        model1.setColumnCount(matrixSize);
        model2.setColumnCount(matrixSize);
        jVec.setColumnCount(matrixSize);
    
    }
    model1.addTableModelListener(model1);
    model2.addTableModelListener(model2);
    jVec.addTableModelListener(jVec);
    model1.fireTableStructureChanged();
    model2.fireTableStructureChanged();
    jVec.fireTableStructureChanged();
//    endUIChange();
}//GEN-LAST:event_jSNumOfComponentsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSpinner jSNumOfComponents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTBranches;
    private javax.swing.JTable jTJVector;
    private javax.swing.JTable jTKMatrix;
    // End of variables declaration//GEN-END:variables
}
