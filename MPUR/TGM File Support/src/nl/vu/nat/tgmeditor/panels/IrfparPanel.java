package nl.vu.nat.tgmeditor.panels;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;


/*
 * KinIrfparPanel.java
 *
 * Created on August 3, 2008, 11:01 AM
 */
public class IrfparPanel extends SectionInnerPanel {

    private TgmDataObject dObj;
    private IrfparPanelModel irfparPanelModel;
    private IrfparTableModel model;
    private RowHeader rowHeader;
    private Object[] defRow;
    private Object[] newRow;
    private String[] rowNames;

    public IrfparPanel(SectionView view, TgmDataObject dObj, IrfparPanelModel irfparPanelModel) {
        super(view);
        this.dObj = dObj;
        this.irfparPanelModel = irfparPanelModel;
        initComponents();

        jSNumOfIrfParameters.setModel(new SpinnerNumberModel(irfparPanelModel.getIrf().size(), 0, 4, 2));

        rowHeader = new RowHeader(20,50);
        rowNames = new String[]{"Position", "Width", "Width2", "Relation"};

        defRow = new Object[]{new Double(0), new Boolean(false)};
        model = new IrfparTableModel(new Object[]{"Irf parameters", "Fixed"}, 0);
        for (int i = 0; i < irfparPanelModel.getIrf().size(); i++) {
            if (irfparPanelModel.getIrf().get(i) != null) {
                newRow = new Object[]{
                            irfparPanelModel.getIrf().get(i),
                            irfparPanelModel.getFixed().get(i)
                        };
                model.addRow(newRow);
                rowHeader.addRow(rowNames[i]);
            } else {
                model.addRow(defRow);
                rowHeader.addRow(rowNames[i]);
            }
        }

        jTIrfparTable.setModel(model);
        JScrollPane jscpane = (JScrollPane) jTIrfparTable.getParent().getParent();
        jscpane.setRowHeaderView(rowHeader);
        jscpane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeader.getTableHeader());

        
        if (irfparPanelModel.isDispmu()) {
            if (irfparPanelModel.getDispmufun().compareTo("poly") == 0) {
                jRDispmufun_poly.setSelected(true);
            }
            if (irfparPanelModel.getDispmufun().compareTo("discrete") == 0) {
                jRDispmufun_discrete.setSelected(true);
            }
        } else {
            jRDispmufun_no.setSelected(true);
        }
        jParmuTextfield.setText(irfparPanelModel.getParmu());

        if (irfparPanelModel.isDisptau()) {
            if (irfparPanelModel.getDisptaufun().compareTo("poly") == 0) {
                jRDisptaufun_poly.setSelected(true);
            }
            if (irfparPanelModel.getDisptaufun().compareTo("discrete") == 0) {
                jRDisptaufun_discrete.setSelected(true);
            }
        } else {
            jRDisptaufun_no.setSelected(true);
        }
        jPartauTextfield.setText(irfparPanelModel.getPartau());

        jTPolyDispersion.setText(String.valueOf(irfparPanelModel.getLamda()));

        jTFLaserPeriod.setText(String.valueOf(irfparPanelModel.getBacksweepPeriod()));

        jCBStreak.setSelected(irfparPanelModel.isBacksweepEnabled());

        jTFLaserPeriod.setEnabled(jCBStreak.isSelected());
        jLabel2.setEnabled(jCBStreak.isSelected());

        // Add listerners
        jTIrfparTable.getModel().addTableModelListener(model);
        //Radiobuttons
        addModifier(jRDispmufun_no);
        addModifier(jRDispmufun_poly);
        addModifier(jRDispmufun_discrete);
        addModifier(jRDisptaufun_no);
        addModifier(jRDisptaufun_poly);
        addModifier(jRDisptaufun_discrete);
        addModifier(jCBStreak);
        // Textfields:
        addModifier(jParmuTextfield);
        addModifier(jPartauTextfield);
        addModifier(jTPolyDispersion);
        addModifier(jTFLaserPeriod);

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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jSNumOfIrfParameters = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTIrfparTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jParmuTextfield = new javax.swing.JTextField();
        jRDispmufun_no = new javax.swing.JRadioButton();
        jRDispmufun_poly = new javax.swing.JRadioButton();
        jRDispmufun_discrete = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jRDisptaufun_no = new javax.swing.JRadioButton();
        jRDisptaufun_poly = new javax.swing.JRadioButton();
        jRDisptaufun_discrete = new javax.swing.JRadioButton();
        jPartauTextfield = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTPolyDispersion = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jCBStreak = new javax.swing.JCheckBox();
        jTFLaserPeriod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jLabel4.setText("Number of IRF parameters");

        jSNumOfIrfParameters.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSNumOfIrfParametersStateChanged(evt);
            }
        });

        jTIrfparTable.setRowHeight(20);
        jScrollPane3.setViewportView(jTIrfparTable);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters to model IRF dispersion"));

        jLabel3.setText("model dispersion of IRF location?");

        jParmuTextfield.setColumns(10);
        jParmuTextfield.setToolTipText("parmu: *** Object of class \"list\" of starting values for the dispersion model for the IRF location\n[Enter as R string]");

        buttonGroup1.add(jRDispmufun_no);
        jRDispmufun_no.setSelected(true);
        jRDispmufun_no.setText("no");
        jRDispmufun_no.setToolTipText("disptaufun: *** Object of class \"character\" describing the functional form of the disper-\n    sion of the IRF width parameter; if equal to \"discrete\" then the IRF width is parame-\n    terized per element of x2 and partau should have the same length as x2. defaults to a\n    polynomial description\n");

        buttonGroup1.add(jRDispmufun_poly);
        jRDispmufun_poly.setText("\"poly\" (as polynomial function)");
        jRDispmufun_poly.setToolTipText("disptaufun: *** Object of class \"character\" describing the functional form of the disper-\n    sion of the IRF width parameter; if equal to \"discrete\" then the IRF width is parame-\n    terized per element of x2 and partau should have the same length as x2. defaults to a\n    polynomial description\n");

        buttonGroup1.add(jRDispmufun_discrete);
        jRDispmufun_discrete.setText("\"discrete\" (with one parameter per-wavelength)");
        jRDispmufun_discrete.setToolTipText("disptaufun: *** Object of class \"character\" describing the functional form of the disper-\n    sion of the IRF width parameter; if equal to \"discrete\" then the IRF width is parame-\n    terized per element of x2 and partau should have the same length as x2. defaults to a\n    polynomial description\n");

        jLabel5.setText("parameters for dispersion of IRF location");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jParmuTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                    .addComponent(jRDispmufun_poly)
                    .addComponent(jRDispmufun_discrete)
                    .addComponent(jLabel5)
                    .addComponent(jRDispmufun_no)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDispmufun_no)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDispmufun_poly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDispmufun_discrete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jParmuTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("model dispersion of IRF width?");

        buttonGroup2.add(jRDisptaufun_no);
        jRDisptaufun_no.setSelected(true);
        jRDisptaufun_no.setText("no");

        buttonGroup2.add(jRDisptaufun_poly);
        jRDisptaufun_poly.setText("\"poly\" (as polynomial function)");

        buttonGroup2.add(jRDisptaufun_discrete);
        jRDisptaufun_discrete.setText("\"discrete\" (with one parameter per-wavelength)");

        jPartauTextfield.setColumns(10);
        jPartauTextfield.setToolTipText("partau: *** Object of class \"vector\" of starting values for the dispersion model for the IRF FWHM\n[Enter as R string]\n");

        jLabel7.setText("parameters for dispersion of IRF width");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRDisptaufun_poly)
                    .addComponent(jRDisptaufun_no)
                    .addComponent(jLabel6)
                    .addComponent(jRDisptaufun_discrete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(jPartauTextfield))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDisptaufun_no)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDisptaufun_poly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRDisptaufun_discrete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPartauTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Center wavelength for polynomial dispersion");

        jTPolyDispersion.setPreferredSize(new java.awt.Dimension(4, 18));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jTPolyDispersion, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTPolyDispersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters for a streak images analysis"));

        jCBStreak.setText("Include backsweep into model");
        jCBStreak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBStreakActionPerformed(evt);
            }
        });

        jTFLaserPeriod.setEnabled(false);

        jLabel2.setText("Period of the laser pulses:");
        jLabel2.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBStreak)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFLaserPeriod, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCBStreak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFLaserPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(jSNumOfIrfParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSNumOfIrfParameters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jSNumOfIrfParametersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSNumOfIrfParametersStateChanged
// TODO add your handling code here:]
    if ((Integer) jSNumOfIrfParameters.getValue() > model.getRowCount()) {
        model.addRow(defRow);
        model.addRow(defRow);
        rowHeader.addRow(rowNames[(Integer)jSNumOfIrfParameters.getValue()-2]);
        rowHeader.addRow(rowNames[(Integer)jSNumOfIrfParameters.getValue()-1]);
    } else {
        model.removeRow(model.getRowCount() - 1);
        model.removeRow(model.getRowCount() - 1);
        rowHeader.removeRow(rowHeader.getRowCount() - 1);
        rowHeader.removeRow(rowHeader.getRowCount() - 1);

    }
//    jTIrfparTable.setModel(model);
    setValue(jTIrfparTable,this);    
}//GEN-LAST:event_jSNumOfIrfParametersStateChanged

private void jCBStreakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBStreakActionPerformed
        jTFLaserPeriod.setEnabled(jCBStreak.isSelected());
        jLabel2.setEnabled(jCBStreak.isSelected());
    
}//GEN-LAST:event_jCBStreakActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox jCBStreak;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jParmuTextfield;
    private javax.swing.JTextField jPartauTextfield;
    private javax.swing.JRadioButton jRDispmufun_discrete;
    private javax.swing.JRadioButton jRDispmufun_no;
    private javax.swing.JRadioButton jRDispmufun_poly;
    private javax.swing.JRadioButton jRDisptaufun_discrete;
    private javax.swing.JRadioButton jRDisptaufun_no;
    private javax.swing.JRadioButton jRDisptaufun_poly;
    private javax.swing.JSpinner jSNumOfIrfParameters;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTFLaserPeriod;
    private javax.swing.JTable jTIrfparTable;
    private javax.swing.JTextField jTPolyDispersion;
    // End of variables declaration//GEN-END:variables
    @Override
    public void setValue(JComponent source, Object value) {
        if (source == jTIrfparTable) {
            irfparPanelModel.getIrf().clear();
            irfparPanelModel.getFixed().clear();

            for (int i = 0; i < model.getRowCount(); i++) {
                irfparPanelModel.getIrf().add((Double) model.getValueAt(i, 0));
                irfparPanelModel.getFixed().add((Boolean) model.getValueAt(i, 1));
            }
        }

        if (source == jRDispmufun_no) {
            irfparPanelModel.setDispmu(false);
        }
        if (source == jRDisptaufun_no) {
            irfparPanelModel.setDisptau(false);
        }
        if (source == jRDispmufun_poly) {
            irfparPanelModel.setDispmufun("poly");
            irfparPanelModel.setDispmu(true);
        }
        if (source == jRDisptaufun_poly) {
            irfparPanelModel.setDisptaufun("poly");
            irfparPanelModel.setDisptau(true);
        }
        if (source == jRDispmufun_discrete) {
            irfparPanelModel.setDispmufun("discrete");
            irfparPanelModel.setDispmu(true);
        }
        if (source == jRDisptaufun_discrete) {
            irfparPanelModel.setDisptaufun("discrete");
            irfparPanelModel.setDisptau(true);
        }
        if (source == jParmuTextfield) {
            irfparPanelModel.setParmu((String) value);
        }
        if (source == jPartauTextfield) {
            irfparPanelModel.setPartau((String) value);
        }
        if (source == jTPolyDispersion) {
            String test = (String)value;
            if (!test.isEmpty()) {
            irfparPanelModel.setLamda(Double.valueOf((String) value));
            } else {
                irfparPanelModel.setLamda(Double.NaN);
                jTPolyDispersion.setText(String.valueOf(Double.NaN));
            }
        }

        if (source == jCBStreak) {
            irfparPanelModel.setBacksweepEnabled(jCBStreak.isSelected());
        }

        if (source == jTFLaserPeriod) {
            irfparPanelModel.setBacksweepPeriod(Double.valueOf(jTFLaserPeriod.getText()));
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

    class IrfparTableModel extends DefaultTableModel implements TableModelListener {

        private Class[] types = new Class[]{Double.class, Boolean.class};

        private IrfparTableModel() {
            super();
        }

        private IrfparTableModel(Object[] ColNames, int i) {
            super(ColNames, i);
        }

        @Override
        public Class getColumnClass(int c) {
            return types[c];
        }

        @Override
        public void tableChanged(TableModelEvent event) {
            setValue(jTIrfparTable, this);
            jTIrfparTable.repaint();
            endUIChange();
        }
    }
}
