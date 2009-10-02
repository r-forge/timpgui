
package nl.vu.nat.tgmeditor.panels;

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
import nl.vu.nat.tgmodels.tgm.Double2BoolMatrix;
import nl.vu.nat.tgmodels.tgm.Double2Matrix;
import nl.vu.nat.tgmodels.tgm.IntMatrix;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;

/**
 *
 * @author  Sergey
 */
public class KMatrixPanel extends SectionInnerPanel implements TableModelListener {

    private TgmDataObject dObj;
    private KMatrixPanelModel kMatrixPanelModel;
    private NumberTableModel model1, modelCLP0, modelClpEq;
    private DefaultTableModel jVec, relationsModel;
    private RowHeader rowHeader1, rowHeaderRelations, rowHeaderJVec, rowHeaderCLP0, rowHeaderClpEq;

    private int matrixSize = 0;

    /** Creates new form KMatrixPanel */
    public KMatrixPanel(SectionView view, TgmDataObject dObj, KMatrixPanelModel kMatrixPanelModel) {
        super(view);
        this.dObj = dObj;
        this.kMatrixPanelModel = kMatrixPanelModel;
        rowHeader1 = new RowHeader(30, 30);
        rowHeaderRelations = new  RowHeader(30, 30);
        rowHeaderJVec = new  RowHeader(40, 30);
        rowHeaderClpEq = new  RowHeader(40, 30);
        rowHeaderCLP0 = new  RowHeader(20, 30);

        initComponents();

        matrixSize = kMatrixPanelModel.getJVector().getVector().size();

        jSNumOfComponents.setModel(new SpinnerNumberModel(matrixSize, 0, null, 1));
        
        relationsModel = new DefaultTableModel();
        jVec = new DefaultTableModel();
        jVec.addRow(new Vector());
        
        model1 = new NumberTableModel(0, 0, Integer.class);
        modelCLP0 = new NumberTableModel(0, 0, Double.class);
        modelClpEq = new NumberTableModel(0, 0, Double.class);

//initialization from tgm file sizes of the matrices:
        for (int i = 0; i <matrixSize; i++){
            model1.addColumn(String.valueOf(i+1));
            relationsModel.addColumn(String.valueOf(i+1));
            jVec.addColumn(String.valueOf(i+1));
            modelCLP0.addColumn(String.valueOf(i+1));
            modelClpEq.addColumn(String.valueOf(i+1));

            jTKMatrix.getColumnModel().addColumn(new KMatrColumn(i, 30));
            jTClp0.getColumnModel().addColumn(new KMatrColumn(i, 30));
            jTClpEq.getColumnModel().addColumn(new KMatrColumn(i, 30));

            jTRelations.getColumnModel().addColumn(new RelationColumn(i));
            jTJVector.getColumnModel().addColumn(new JVectorColumn(i));
        }
//initialization of jVec
        for (int i=0; i < matrixSize; i++){
            jVec.setValueAt(new JVectorValueClass(kMatrixPanelModel.getJVector().getVector().get(i),
                                                  kMatrixPanelModel.getJVector().getFixed().get(i)) , 0, i);
        }

//fill in fixed to 0 spectra parameters
        modelCLP0.addRow(kMatrixPanelModel.getSpectralContraints().getMin().toArray().clone());
        modelCLP0.addRow(kMatrixPanelModel.getSpectralContraints().getMax().toArray().clone());
        
//initialization of kMatr
        for (int i = 0; i <matrixSize; i++){
            model1.addRow(kMatrixPanelModel.getKMatrix().getData().get(i).getRow().toArray().clone());
            rowHeader1.addRow(String.valueOf(i+1));
        }

//initialization of clpeq
        for (int i = 0; i <matrixSize; i++){
            modelClpEq.addRow(kMatrixPanelModel.getContrainsMatrix().getData().get(i).getMin().toArray().clone());
            modelClpEq.addRow(kMatrixPanelModel.getContrainsMatrix().getData().get(i).getMax().toArray().clone());
            rowHeaderClpEq.addRow(String.valueOf(i+1));
        }

//initialization of relations
        for (int i = 0; i <matrixSize; i++){
            relationsModel.addRow(new Vector(matrixSize));
            for (int j = 0; j <matrixSize; j++){
                if (kMatrixPanelModel.getRelationsMatrix().getData().size()==matrixSize){
                    relationsModel.setValueAt(new RelationValueClass(
                            kMatrixPanelModel.getRelationsMatrix().getData().get(i).getC0().get(j),
                            kMatrixPanelModel.getRelationsMatrix().getData().get(i).getC1().get(j),
                            kMatrixPanelModel.getRelationsMatrix().getData().get(i).getC0Fixed().get(j),
                            kMatrixPanelModel.getRelationsMatrix().getData().get(i).getC1Fixed().get(j)),
                            i, j);
                }
            }
            rowHeaderRelations.addRow(String.valueOf(i+1));
        }

//add listeners
        model1.addTableModelListener(this);
        relationsModel.addTableModelListener(this);
        jVec.addTableModelListener(this);
        modelCLP0.addTableModelListener(this);
        modelClpEq.addTableModelListener(this);

//add row names
        jTKMatrix.setModel(model1);
        JScrollPane jscpane = (JScrollPane) jTKMatrix.getParent().getParent();
        jscpane.setRowHeaderView(rowHeader1);
        jscpane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeader1.getTableHeader());
        
        jTRelations.setModel(relationsModel);
        JScrollPane jscpane2 = (JScrollPane) jTRelations.getParent().getParent();
        jscpane2.setRowHeaderView(rowHeaderRelations);
        jscpane2.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderRelations.getTableHeader());

        jTClpEq.setModel(modelClpEq);
        JScrollPane jscpane5 = (JScrollPane) jTClpEq.getParent().getParent();
        jscpane5.setRowHeaderView(rowHeaderClpEq);
        jscpane5.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderClpEq.getTableHeader());

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

    }

    @Override
    public void setValue(JComponent source, Object value) {

        if (source ==jTKMatrix) {
            int val;
            kMatrixPanelModel.getKMatrix().getData().clear();
            IntMatrix.Data tempData;
            for (int j=0; j<matrixSize; j++){
                tempData = new IntMatrix.Data();
                for (int i=0; i<matrixSize; i++){
                    if (model1.getValueAt(j, i) == null)
                        val = 0;
                    else
                        val =(Integer)model1.getValueAt(j, i);
                   tempData.getRow().add(val);
                }
                kMatrixPanelModel.getKMatrix().getData().add(tempData);
            }

        }

        if (source ==jTRelations) {
//            int val;
            kMatrixPanelModel.getRelationsMatrix().getData().clear();
            Double2BoolMatrix.Data tempData;
            RelationValueClass val;
            for (int i=0; i<matrixSize; i++){
                tempData = new Double2BoolMatrix.Data();
                for (int j=0; j<matrixSize; j++){
                    val = (RelationValueClass) relationsModel.getValueAt(i, j);
                    if (val==null) {
                        val = new RelationValueClass();
                    }
                        tempData.getC0().add(val.getC0());
                        tempData.getC1().add(val.getC1());
                        tempData.getC0Fixed().add(val.isFixedC0());
                        tempData.getC1Fixed().add(val.isFixedC1());
                    
                }
                kMatrixPanelModel.getRelationsMatrix().getData().add(tempData);
            }
        }


        if (source ==jTClpEq) {
            Double2Matrix.Data tempData;
            kMatrixPanelModel.getContrainsMatrix().getData().clear();
            for (int i=0; i<matrixSize; i++){
                tempData = new Double2Matrix.Data();
                for (int j=0; j<matrixSize; j++){
                    tempData.getMin().add((Double) modelClpEq.getValueAt(2*i, j));
                    tempData.getMax().add((Double) modelClpEq.getValueAt(2*i+1, j));
                }
                kMatrixPanelModel.getContrainsMatrix().getData().add(tempData);
            }
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
            kMatrixPanelModel.getSpectralContraints().getMax().clear();
            kMatrixPanelModel.getSpectralContraints().getMin().clear();

            for (int i=0; i<matrixSize; i++){
                kMatrixPanelModel.getSpectralContraints().getMax().add((Double) modelCLP0.getValueAt(0, i));
                kMatrixPanelModel.getSpectralContraints().getMin().add((Double) modelCLP0.getValueAt(1, i));
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
             if (event.getSource().equals(relationsModel)) {
                 setValue(jTRelations, this);
             }
             else {
                 if (event.getSource().equals(jVec)) {
                     setValue(jTJVector, this);
                 }
                 else {
                     if (event.getSource().equals(modelCLP0)){
                         setValue(jTClp0, this);
                     }
                     else {
                         if (event.getSource().equals(modelClpEq)){
                             setValue(jTClpEq, this);
                         }
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
        jTRelations = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTJVector = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTClp0 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTClpEq = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

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

        jTRelations.setAutoCreateColumnsFromModel(false);
        jTRelations.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTRelations.setRowHeight(30);
        jScrollPane2.setViewportView(jTRelations);

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

        jTClpEq.setAutoCreateColumnsFromModel(false);
        jTClpEq.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTClpEq.setRowHeight(20);
        jScrollPane5.setViewportView(jTClpEq);

        jLabel7.setText("Spectra equality");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel7))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, jScrollPane2, jScrollPane3, jScrollPane4});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jSNumOfComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, 0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
    relationsModel.removeTableModelListener(this);
    jVec.removeTableModelListener(this);
    modelCLP0.removeTableModelListener(this);
    modelClpEq.removeTableModelListener(this);

    if ((Integer) jSNumOfComponents.getValue() > matrixSize) {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.addColumn(String.valueOf(matrixSize));
        relationsModel.addColumn(String.valueOf(matrixSize));
        jVec.addColumn(String.valueOf(matrixSize));
        modelCLP0.addColumn(String.valueOf(matrixSize));
        modelClpEq.addColumn(String.valueOf(matrixSize));

        jTKMatrix.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTRelations.getColumnModel().addColumn(new RelationColumn(matrixSize-1));
        jTJVector.getColumnModel().addColumn(new JVectorColumn(matrixSize-1, 30));
        jTClp0.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));
        jTClpEq.getColumnModel().addColumn(new KMatrColumn(matrixSize-1, 30));

        model1.addRow(new Vector(matrixSize));
        modelClpEq.addRow(new Vector(matrixSize));
        modelClpEq.addRow(new Vector(matrixSize));

        relationsModel.addRow(new Vector(matrixSize));
        for (int i = 0; i<matrixSize; i++){
            relationsModel.setValueAt(new RelationValueClass(), matrixSize-1, i);
            relationsModel.setValueAt(new RelationValueClass(), i, matrixSize-1);
        }
        rowHeader1.addRow(String.valueOf(matrixSize));
        rowHeaderRelations.addRow(String.valueOf(matrixSize));
        rowHeaderClpEq.addRow(String.valueOf(matrixSize));
        jVec.setValueAt(new JVectorValueClass(), 0, matrixSize-1);

 
    } else {

        matrixSize = (Integer)jSNumOfComponents.getValue();
        model1.removeRow(matrixSize);
        modelClpEq.removeRow(modelClpEq.getRowCount()-1);
        modelClpEq.removeRow(modelClpEq.getRowCount()-1);
        relationsModel.removeRow(matrixSize);

        rowHeader1.removeRow(matrixSize);
        rowHeaderRelations.removeRow(matrixSize);
        rowHeaderClpEq.removeRow(matrixSize);

        jTKMatrix.getColumnModel().removeColumn(jTKMatrix.getColumnModel().getColumn(matrixSize));
        jTRelations.getColumnModel().removeColumn(jTRelations.getColumnModel().getColumn(matrixSize));
        jTJVector.getColumnModel().removeColumn(jTJVector.getColumnModel().getColumn(matrixSize));
        jTClp0.getColumnModel().removeColumn(jTClp0.getColumnModel().getColumn(matrixSize));
        jTClpEq.getColumnModel().removeColumn(jTClpEq.getColumnModel().getColumn(matrixSize));

        model1.setColumnCount(matrixSize);
        relationsModel.setColumnCount(matrixSize);
        jVec.setColumnCount(matrixSize);
        modelCLP0.setColumnCount(matrixSize);
        modelClpEq.setColumnCount(matrixSize);
    
    }
    model1.addTableModelListener(this);
    relationsModel.addTableModelListener(this);
    jVec.addTableModelListener(this);
    modelCLP0.addTableModelListener(this);
    modelClpEq.addTableModelListener(this);
    model1.fireTableStructureChanged();
    relationsModel.fireTableStructureChanged();
    jVec.fireTableStructureChanged();
    modelCLP0.fireTableStructureChanged();
    modelClpEq.fireTableStructureChanged();

//    endUIChange();
}//GEN-LAST:event_jSNumOfComponentsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner jSNumOfComponents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTClp0;
    private javax.swing.JTable jTClpEq;
    private javax.swing.JTable jTJVector;
    private javax.swing.JTable jTKMatrix;
    private javax.swing.JTable jTRelations;
    // End of variables declaration//GEN-END:variables

}
