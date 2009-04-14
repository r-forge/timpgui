/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.flimdataloader.components;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final class MeasuredIrfTopComponent extends TopComponent {
    
        private JFileChooser fc;
    private int length;
    private int from, till;
    private float maxInt;
    private float[] refArray;
    private ChartPanel chpan;
    private XYSeriesCollection refSerColl;
    private JFreeChart chart;
    private IntervalMarker marker;

    private static MeasuredIrfTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "MeasuredIrfTopComponent";

    private MeasuredIrfTopComponent() {
                fc = new JFileChooser();
        chpan=null;
        chart=null;
        marker=null;
        maxInt = 0;
        refSerColl = new XYSeriesCollection();
        initComponents();
        setName(NbBundle.getMessage(MeasuredIrfTopComponent.class, "CTL_MeasuredIrfTopComponent"));
        setToolTipText(NbBundle.getMessage(MeasuredIrfTopComponent.class, "HINT_MeasuredIrfTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        tFRefFilename = new javax.swing.JTextField();
        Bloadref = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCBEstimateBG = new javax.swing.JCheckBox();
        jSFrom = new javax.swing.JSpinner();
        jSTill = new javax.swing.JSpinner();
        jTFBGvalue = new javax.swing.JTextField();
        jBSubtrBG = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jBCalculateBG = new javax.swing.JButton();
        jCBNegToZer = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        tTFIrfVariableName = new javax.swing.JTextField();
        jBMakeMeasIrfVariable = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jLabel2.text")); // NOI18N

        tFRefFilename.setEditable(false);
        tFRefFilename.setText(org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.tFRefFilename.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(Bloadref, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.Bloadref.text")); // NOI18N
        Bloadref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloadrefActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel2ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(jCBEstimateBG, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jCBEstimateBG.text")); // NOI18N
        jCBEstimateBG.setEnabled(false);
        jCBEstimateBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBEstimateBGActionPerformed(evt);
            }
        });

        jSFrom.setEnabled(false);
        jSFrom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSFromStateChanged(evt);
            }
        });

        jSTill.setEnabled(false);
        jSTill.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSTillStateChanged(evt);
            }
        });
        jSTill.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSTillPropertyChange(evt);
            }
        });

        jTFBGvalue.setText(org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jTFBGvalue.text")); // NOI18N
        jTFBGvalue.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jBSubtrBG, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jBSubtrBG.text")); // NOI18N
        jBSubtrBG.setEnabled(false);
        jBSubtrBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubtrBGActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jLabel1.text")); // NOI18N
        jLabel1.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jLabel3.text")); // NOI18N
        jLabel3.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jLabel4.text")); // NOI18N
        jLabel4.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jBCalculateBG, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jBCalculateBG.text")); // NOI18N
        jBCalculateBG.setEnabled(false);
        jBCalculateBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCalculateBGActionPerformed(evt);
            }
        });

        jCBNegToZer.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCBNegToZer, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jCBNegToZer.text")); // NOI18N
        jCBNegToZer.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jLabel5.text")); // NOI18N

        tTFIrfVariableName.setText(org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.tTFIrfVariableName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeMeasIrfVariable, org.openide.util.NbBundle.getMessage(MeasuredIrfTopComponent.class, "MeasuredIrfTopComponent.jBMakeMeasIrfVariable.text")); // NOI18N
        jBMakeMeasIrfVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeMeasIrfVariableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCBEstimateBG, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSTill, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(jBCalculateBG)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFBGvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(tFRefFilename, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Bloadref, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBMakeMeasIrfVariable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCBNegToZer, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBSubtrBG))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(10, 10, 10)
                                .addComponent(tTFIrfVariableName, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tFRefFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tTFIrfVariableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bloadref)
                    .addComponent(jBSubtrBG)
                    .addComponent(jCBNegToZer)
                    .addComponent(jBMakeMeasIrfVariable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFBGvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBCalculateBG)
                    .addComponent(jLabel1)
                    .addComponent(jSFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jSTill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBEstimateBG))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void BloadrefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloadrefActionPerformed
// TODO add your handling code here:
        int returnVal = fc.showOpenDialog(this);
        Vector refVector = new Vector();
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            tFRefFilename.setText(fc.getName(file));
        
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    refVector.addElement(sc.nextFloat());
                }
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }      
            int i=0;
            float num;
            XYSeries refSeria = new XYSeries("Reference");
            length = refVector.size();
            refArray = new float[length];
            for (Enumeration e = refVector.elements(); e.hasMoreElements();){
                Float temp = (Float)e.nextElement();
                num = (float)temp;    
                refSeria.add(i,num);
                if (num>maxInt)
                    maxInt = num;
                refArray[i]=num;
                i++;                 
            }

            refSerColl.addSeries(refSeria);
            MakeChart(refSerColl);
            from = 0;
            till = length;
            marker = new IntervalMarker(from,till);
            marker.setPaint(new Color(222, 222, 255, 128));

            jBSubtrBG.setEnabled(true);
            jLabel4.setEnabled(true);
            jCBEstimateBG.setEnabled(true);
            jTFBGvalue.setEnabled(true);
            jCBNegToZer.setEnabled(true);

            jSFrom.setModel(new SpinnerNumberModel(from,0,length,1));  
            jSTill.setModel(new SpinnerNumberModel(till,0,length,1));
           //        System.out.println(refVector);
        }
}//GEN-LAST:event_BloadrefActionPerformed

private void jPanel2ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel2ComponentResized
// TODO add your handling code here:
        if (chpan!=null)
            chpan.setSize(jPanel2.getSize());
}//GEN-LAST:event_jPanel2ComponentResized

private void jCBEstimateBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBEstimateBGActionPerformed
// TODO add your handling code here:
        jTFBGvalue.setEditable(!jTFBGvalue.isEditable());
        jLabel1.setEnabled(!jLabel1.isEnabled());
        jLabel3.setEnabled(!jLabel3.isEnabled());
        jSFrom.setEnabled(!jSFrom.isEnabled());
        jSTill.setEnabled(!jSTill.isEnabled());
        jBCalculateBG.setEnabled(!jBCalculateBG.isEnabled());
        if (jCBEstimateBG.isSelected())
            chart.getXYPlot().addDomainMarker(marker);
        else 
            chart.getXYPlot().clearDomainMarkers(0);
}//GEN-LAST:event_jCBEstimateBGActionPerformed

private void jSFromStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSFromStateChanged
// TODO add your handling code here:

        Integer fr = (Integer)jSFrom.getValue();
        Integer tl = (Integer)jSTill.getValue();
        from = (int)fr;
        till = (int)tl;
        if (from>=till){
            jSTill.setValue(jSFrom.getValue());
            till = from;
        }
        marker.setStartValue(from);
        marker.setEndValue(till);
}//GEN-LAST:event_jSFromStateChanged

private void jSTillStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSTillStateChanged
// TODO add your handling code here:
        Integer fr = (Integer)jSFrom.getValue();
        Integer tl = (Integer)jSTill.getValue();
        from = (int)fr;
        till = (int)tl;
        
        if (from>=till){
            jSFrom.setValue(jSTill.getValue());
            from = till;
        }
        marker.setStartValue(from);
        marker.setEndValue(till);
}//GEN-LAST:event_jSTillStateChanged

private void jSTillPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSTillPropertyChange
// TODO add your handling code here:
}//GEN-LAST:event_jSTillPropertyChange

private void jBSubtrBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubtrBGActionPerformed
// TODO add your handling code here:
        float val = 0;
        try{
                val = Float.parseFloat(jTFBGvalue.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Wrong number format");            
        }
        refSerColl.getSeries(0).clear();
        for (int i = 0; i < length; i++){
            refArray[i]-=val;
            if (jCBNegToZer.isSelected()&&(refArray[i]<0)){
                    refArray[i]=0;
            }
            refSerColl.getSeries(0).add(i,refArray[i]);
        }
}//GEN-LAST:event_jBSubtrBGActionPerformed

private void jBCalculateBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCalculateBGActionPerformed
// TODO add your handling code here:
       float sum = 0;  
       for(int i = from; i<till; i++){
           sum += refArray[i];
       } 
       Float val = sum/(till-from); 
       jTFBGvalue.setText(val.toString());
}//GEN-LAST:event_jBCalculateBGActionPerformed

private void jBMakeMeasIrfVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeMeasIrfVariableActionPerformed
// TODO add your handling code here:
    //Current.SetcurrMIRF(tTFIrfVariableName.getText());
   // Register.RegisterMeasuredIRF(tTFIrfVariableName.getText(), refArray);
}//GEN-LAST:event_jBMakeMeasIrfVariableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bloadref;
    private javax.swing.JButton jBCalculateBG;
    private javax.swing.JButton jBMakeMeasIrfVariable;
    private javax.swing.JButton jBSubtrBG;
    private javax.swing.JCheckBox jCBEstimateBG;
    private javax.swing.JCheckBox jCBNegToZer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSFrom;
    private javax.swing.JSpinner jSTill;
    private javax.swing.JTextField jTFBGvalue;
    private javax.swing.JTextField tFRefFilename;
    private javax.swing.JTextField tTFIrfVariableName;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized MeasuredIrfTopComponent getDefault() {
        if (instance == null) {
            instance = new MeasuredIrfTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the MeasuredIrfTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized MeasuredIrfTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(MeasuredIrfTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof MeasuredIrfTopComponent) {
            return (MeasuredIrfTopComponent) win;
        }
        Logger.getLogger(MeasuredIrfTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return MeasuredIrfTopComponent.getDefault();
        }
    }
    
     private void MakeChart(XYDataset dat){
        chart = ChartFactory.createXYLineChart(
            "Reference compound", 
            "Chanel number",
            "Number of counts", 
            dat, 
            PlotOrientation.VERTICAL, 
            false, 
            false, 
            false
        );
        chart.getXYPlot().getDomainAxis().setUpperBound(length);
        chart.getXYPlot().setDomainZeroBaselineVisible(true);
        chpan = new ChartPanel(chart,true);
        chpan.setSize(jPanel2.getSize());
        jPanel2.removeAll();
        jPanel2.add(chpan);
        jPanel2.repaint(); 
    }
}
