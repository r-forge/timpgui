
package nl.vu.nat.tgmeditor.panels;

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
import nl.vu.nat.tgmodels.tgm.IntMatrix.Data;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;

/**
 *
 * @author  Sergey
 */
public class KMatrixPanel extends SectionInnerPanel implements TableModelListener {

    private TgmDataObject dObj;
    private KMatrixPanelModel kMatrixPanelModel;
    private NumberTableModel model1, model2, modelCLP0;
//    private JVectorTableModel jVec;
    private DefaultTableModel jVec;
    private RowHeader rowHeader1, rowHeader2, rowHeaderJVec, rowHeaderCLP0;

    private int matrixSize = 0;

    /** Creates new form KMatrixPanel */
    public KMatrixPanel(SectionView view, TgmDataObject dObj, KMatrixPanelModel kMatrixPanelModel) {
        super(view);
        this.dObj = dObj;
        this.kMatrixPanelModel = kMatrixPanelModel;
        rowHeader1 = new RowHeader(30, 30);
        rowHeader2 = new  RowHeader(30, 30);
        rowHeaderJVec = new  RowHeader(40, 30);
        rowHeaderCLP0 = new  RowHeader(20, 30);
        initComponents();

        matrixSize = kMatrixPanelModel.getJVector().getVector().size();
        jSNumOfComponents.setModel(new SpinnerNumberModel(matrixSize, 0, null, 1));

        model1 = new NumberTableModel(0, 0, Integer.class);
        model2 = new NumberTableModel(0, 0, Integer.class);
        jVec = new DefaultTableModel();
        jVec.addRow(new Vector());

        modelCLP0 = new NumberTableModel(0, 0, Double.class);

//initialisation from tgm file sizes of the matrices:
        for (int i = 0; i <matrixSize; i++){
            model1.addColumn(String.valueOf(i+1));
            model2.addColumn(String.valueOf(i+1));
            jVec.addColumn(String.valueOf(i+1));
            modelCLP0.addColumn(String.valueOf(i+1));

            jTKMatrix.getColumnModel().addColumn(new KMatrColumn(i, 30));
            jTBranches.getColumnModel().addColumn(new KMatrColumn(i, 30));
            jTJVector.getColumnModel().addColumn(new JVectorColumn(i, 30));
            jTClp0.getColumnModel().addColumn(new KMatrColumn(i, 30));
        }
//inicialisation of jVec
        for (int i=0; i < matrixSize; i++){
            jVec.setValueAt(new JVectorValueClass(kMatrixPanelModel.getJVector().getVector().get(i),
                                                  kMatrixPanelModel.getJVector().getFixed().get(i)) , 0, i);
        }

//fill in fixed to 0 spectra parameters
        
        modelCLP0.addRow(kMatrixPanelModel.getContrainsMatrix().getData().getMax().toArray().clone());
        modelCLP0.addRow(kMatrixPanelModel.getContrainsMatrix().getData().getMin().toArray().clone());
        
//inicialisation of kMatr
        for (int i = 0; i <matrixSize; i++){
            //model1.addRow(kMatrixPanelModel.getK1Matrix().getRow()[i].toArray().clone());
            model1.addRow(kMatrixPanelModel.getKMatrix().getData().get(i).getRow().toArray().clone());
            //TODO: fix this. add code for contrains matrix
            //model2.addRow(kMatrixPanelModel.getK2Matrix().getData().get(i).getRow().toArray().clone());
            rowHeader1.addRow(String.valueOf(i));
            rowHeader2.addRow(String.valueOf(i));
        }
        
//add listeners
        model1.addTableModelListener(this);
        model2.addTableModelListener(this);
        jVec.addTableModelListener(this);
        modelCLP0.addTableModelListener(this);

//add row names
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

        jTClp0.setModel(modelCLP0);
        JScrollPane jscpane4 = (JScrollPane) jTClp0.getParent().getParent();
        rowHeaderCLP0.addRow("low");
        rowHeaderCLP0.addRow("high");
        jscpane4.setRowHeaderView(rowHeaderCLP0);
        jscpane4.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderCLP0.getTableHeader());
        

//        model2.addTableModelListener(model2);
        // Add listerners
//        jTKMatrix.getModel().addTableModelListener(model1);

    }

    @Override
    public void setValue(JComponent source, Object value) {

        if (source ==jTKMatrix) {
            int val;
            kMatrixPanelModel.getKMatrix().getData().clear();
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
                kMatrixPanelModel.getKMatrix().getData().add(tempData);
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
//            int val;
//            kMatrixPanelModel.getK2Matrix().getData().clear();
//            Data tempData;
//            for (int j=0; j<matrixSize; j++){
//                tempData = new Data();
//                for (int i=0; i<matrixSize; i++){
//                    if (model2.getValueAt(j, i) == null)
//                        val = 0;
//                    else
//                        val =(Integer)model2.getValueAt(j, i);
//                   tempData.getRow().add(val);
//                }
//                kMatrixPanelModel.getK2Matrix().getData().add(tempData);
//            }
        }

        if (source ==jTJVector) {
            kMatrixPanelModel.getJVector().getFixed().clear();
            kMatrixPanelModel.getJVector().getVector().clear();
            JVectorValueClass val;
            for (int i=0; i<matrixSize; i++){
                val = (JVectorValueClass) jVec.getValueAt(0, i);
                kMatrixPanelModel.getJVector().getVector().add(val.getValue());
                kMatrixPanelModel.getJVector().getFixed().add(val.isFixed());
            }
        }

        if (source ==jTClp0) {
            kMatrixPanelModel.getContrainsMatrix().getData().getMax().clear();
            kMatrixPanelModel.getContrainsMatrix().getData().getMin().clear();

            for (int i=0; i<matrixSize; i++){
                kMatrixPanelModel.getContrainsMatrix().getData().getMax().add((Double) modelCLP0.getValueAt(0, i));
                kMatrixPanelModel.getContrainsMatrix().getData().getMin().add((Double) modelCLP0.getValueAt(1, i));
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

    @Override
    public void tableChanged(TableModelEvent event) {
         if (event.getSource().equals(model1)) {
                setValue(jTKMatrix, this);
         }
         else {
             if (event.getSource().equals(model2)) {
                 setValue(jTBranches, this);
             }
             else {
                 if (event.getSource().equals(jVec)) {
                     setValue(jTJVector, this);
                 }
                 else {
                     if (event.getSource().equals(modelCLP0)){
                         setValue(jTClp0, this);
                     }
                 }
             }
         }
    }

    class NumberTableModel extends DefaultTableModel{// implements TableModelListener {
        private Class cellClass;
        private NumberTableModel(Class var) {
            super();
            cellClass = var;
        }

        private NumberTableModel(int n, int m, Class var) {
            super(n, m);
            cellClass = var;
        }

        @Override
        public Class getColumnClass(int c) {
            return cellClass;
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
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTClp0 = new javax.swing.JTable();

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
        jTJVector.setRowHeight(40);
        jScrollPane3.setViewportView(jTJVector);

        jLabel2.setText("J Vector");

        jLabel3.setText("Kmatrix");

        jLabel4.setText("Relations");

        jLabel5.setText("Force spectra to 0");

        jTClp0.setAutoCreateColumnsFromModel(false);
        jTClp0.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTClp0.setRowHeight(20);
        jScrollPane4.setViewportView(jTClp0);

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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, jScrollPane2, jScrollPane3, jScrollPane4});

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane1, jScrollPane2});

    }// </editor-fold>//GEN-END:initComponents

private void jSNumOfComponentsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSNumOfComponentsStateChanged

    model1.removeTableModelListener(this);
    model2.removeTableModelListener(this);
    jVec.removeTableModelListener(this);
    modelCLP0.removeTableModelListener(this);

    if ((Integer) jSNumOfComponents.getValue() > matrixSize) {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.addColumn(String.valueOf(matrixSize));
        model2.addColumn(String.valueOf(matrixSize));
        jVec.addColumn(String.valueOf(matrixSize));
        modelCLP0.addColumn(String.valueOf(matrixSize));

        jTKMatrix.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTBranches.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTJVector.getColumnModel().addColumn(new JVectorColumn(matrixSize-1, 30));
        jTClp0.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));

        model1.addRow(new Vector(matrixSize));
        model2.addRow(new Vector(matrixSize));
        rowHeader1.addRow(String.valueOf(matrixSize));
        rowHeader2.addRow(String.valueOf(matrixSize));
        jVec.setValueAt(new JVectorValueClass(), 0, matrixSize-1);

 
    } else {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.removeRow(matrixSize);
        model2.removeRow(matrixSize);
        rowHeader1.removeRow(matrixSize);
        rowHeader2.removeRow(matrixSize);
        jTKMatrix.getColumnModel().removeColumn(jTKMatrix.getColumnModel().getColumn(matrixSize));
        jTBranches.getColumnModel().removeColumn(jTBranches.getColumnModel().getColumn(matrixSize));
        jTJVector.getColumnModel().removeColumn(jTJVector.getColumnModel().getColumn(matrixSize));
        jTClp0.getColumnModel().removeColumn(jTClp0.getColumnModel().getColumn(matrixSize));
        model1.setColumnCount(matrixSize);
        model2.setColumnCount(matrixSize);
        jVec.setColumnCount(matrixSize);
        modelCLP0.setColumnCount(matrixSize);
    
    }
    model1.addTableModelListener(this);
    model2.addTableModelListener(this);
    jVec.addTableModelListener(this);
    modelCLP0.addTableModelListener(this);
    model1.fireTableStructureChanged();
    model2.fireTableStructureChanged();
    jVec.fireTableStructureChanged();
    modelCLP0.fireTableStructureChanged();

//    endUIChange();
}//GEN-LAST:event_jSNumOfComponentsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSpinner jSNumOfComponents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTBranches;
    private javax.swing.JTable jTClp0;
    private javax.swing.JTable jTJVector;
    private javax.swing.JTable jTKMatrix;
    // End of variables declaration//GEN-END:variables

}
