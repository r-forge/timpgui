/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui.plots;

import Jama.Matrix;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.timpgui.timpinterface.ResultObject;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.TimpResultDataset;
//import org.openide.util.Utilities;
/**
 * Top component which displays something.
 */
final class FlimOutputTopComponent extends TopComponent implements ChartMouseListener {

    private static FlimOutputTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "FlimOutputTopComponent";
    private JFreeChart chart;
    private double maxAveLifetime;
    private double[] aveLifetimes;
    private Matrix normAmpl;
    private XYSeriesCollection tracesCollection,  residuals;
    private TimpResultDataset res;
    private Object[] listToPlot;
    private int selectedietm;
    private ColorCodedImageDataset dataset;
    private static DefaultListModel listOfResultObjectNames = new DefaultListModel();
    private static DefaultListModel listOfDatasets = new DefaultListModel();
    private ArrayList<ResultObject> resultObjects;

    private FlimOutputTopComponent() {
        chart = null;
        maxAveLifetime = 0;
        res = new TimpResultDataset();
        normAmpl = null;
        initComponents();
        setName(NbBundle.getMessage(FlimOutputTopComponent.class, "CTL_FlimOutputTopComponent"));
        setToolTipText(NbBundle.getMessage(FlimOutputTopComponent.class, "HINT_FlimOutputTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPImage = new javax.swing.JPanel();
        jPSelectedTrace = new javax.swing.JPanel();
        jPHist = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListResultObjects = new javax.swing.JList();
        jCBToPlot = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLEstimatedLifetimes = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTMin = new javax.swing.JTextField();
        jTMax = new javax.swing.JTextField();
        jBUpdate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListDatasets = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jPImage.setBackground(new java.awt.Color(0, 0, 0));
        jPImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPImage.setMaximumSize(new java.awt.Dimension(450, 350));
        jPImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPImageLayout = new javax.swing.GroupLayout(jPImage);
        jPImage.setLayout(jPImageLayout);
        jPImageLayout.setHorizontalGroup(
            jPImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );
        jPImageLayout.setVerticalGroup(
            jPImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );

        jPSelectedTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSelectedTraceLayout = new javax.swing.GroupLayout(jPSelectedTrace);
        jPSelectedTrace.setLayout(jPSelectedTraceLayout);
        jPSelectedTraceLayout.setHorizontalGroup(
            jPSelectedTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        jPSelectedTraceLayout.setVerticalGroup(
            jPSelectedTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );

        jPHist.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPHistLayout = new javax.swing.GroupLayout(jPHist);
        jPHist.setLayout(jPHistLayout);
        jPHistLayout.setHorizontalGroup(
            jPHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );
        jPHistLayout.setVerticalGroup(
            jPHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        jListResultObjects.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jListResultObjects.border.title"))); // NOI18N
        jListResultObjects.setModel(listOfDatasets);
        jListResultObjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListResultObjects.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListResultObjectsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListResultObjects);
        jListResultObjects.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jListResultObjects.AccessibleContext.accessibleName")); // NOI18N

        jCBToPlot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBToPlotActionPerformed(evt);
            }
        });

        jLEstimatedLifetimes.setBorder(javax.swing.BorderFactory.createTitledBorder("Estimated Lifetimes"));
        jScrollPane2.setViewportView(jLEstimatedLifetimes);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBUpdate, org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jBUpdate.text")); // NOI18N
        jBUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdateActionPerformed(evt);
            }
        });

        jListDatasets.setBorder(javax.swing.BorderFactory.createTitledBorder("ListOfDatasets"));
        jListDatasets.setModel(listOfDatasets);
        jListDatasets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListDatasets.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListDatasetsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListDatasets);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(FlimOutputTopComponent.class, "FlimOutputTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jCBToPlot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jBUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                                            .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(0, 0, 0)
                        .addComponent(jPHist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPSelectedTrace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jBUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCBToPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                    .addComponent(jPHist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPSelectedTrace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jPImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPImageMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jPImageMouseClicked

private void jCBToPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBToPlotActionPerformed
// TODO add your handling code here:
    double minVal;
    double maxVal;
    selectedietm = 0;
    for (int i = 0; i < listToPlot.length; i++) {
        if (jCBToPlot.getModel().getSelectedItem() == listToPlot[i]) {
            selectedietm = i;
        }
    }
    if (selectedietm == 0) {
        minVal = 0;
        maxVal = maxAveLifetime;
    } else {
        minVal = 0;
        maxVal = 1;
    }

    MakeImageChart(MakeCCDataset(64, 64, res.GetX2(), selectedietm), minVal, maxVal, true);
    ChartPanel chpanImage = new ChartPanel(chart,true);
    jPImage.removeAll();
    chpanImage.setSize(jPImage.getSize());
    chpanImage.addChartMouseListener(this);
    jPImage.add(chpanImage);
    jPImage.repaint();
    jPHist.removeAll();
    ChartPanel chpanHist = CreateHistPanel(selectedietm);
    chpanHist.setSize(jPHist.getSize());
    jPHist.add(chpanHist);
    jPHist.repaint();

//    System.out.println(jCBToPlot.getModel().getSelectedItem());
}//GEN-LAST:event_jCBToPlotActionPerformed

private void jBUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdateActionPerformed
// TODO add your handling code here:
    double minVal,  maxVal;
    try {

        minVal = Float.parseFloat(jTMin.getText());
        maxVal = Float.parseFloat(jTMax.getText());
        MakeImageChart(MakeCCDataset(64, 64, res.GetX2(), selectedietm), minVal, maxVal, true);
        ChartPanel chpanImage = new ChartPanel(chart,true);
        jPImage.removeAll();
        chpanImage.setSize(jPImage.getSize());
        chpanImage.addChartMouseListener(this);
        jPImage.add(chpanImage);
        jPImage.repaint();
        jPHist.removeAll();
        ChartPanel chpanHist = CreateHistPanel(selectedietm);
        chpanHist.setSize(jPHist.getSize());
        jPHist.add(chpanHist);
        jPHist.repaint();
    } catch (NumberFormatException ex) {
        System.out.println("Wrong number format");
    }
}//GEN-LAST:event_jBUpdateActionPerformed

private void jListDatasetsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListDatasetsValueChanged
// TODO add your handling code here:
    if (jListDatasets.getSelectedIndex()!=-1) {
    //res = tc.setTimpResultDataset((String)jListDatasets.getSelectedValue(), jListDatasets.getSelectedIndex(), Current.GetresultNames().get(jListResultObjects.getSelectedIndex()));
  //  res.CalcRangeInt();
    
    jCBToPlot.removeAllItems();
//    jCBToPlot.addItem("Average Lifetimes");
    Object[] lifetimes = new Object[res.GetKineticParameters().length];
    listToPlot = new Object[res.GetKineticParameters().length + 1];
    aveLifetimes = MakeFlimImage(res.GetKineticParameters(), res.GetSpectras(), res.GetX2().length);
    listToPlot[0] = "Average Lifetimes";
    jCBToPlot.addItem(listToPlot[0]);
    for (int i = 0; i < res.GetKineticParameters().length; i++) {
        listToPlot[i + 1] = "Component " + (i + 1);
        jCBToPlot.addItem(listToPlot[i + 1]);
        lifetimes[i] = "Tau" + (i + 1) + "=" + 1 / res.GetKineticParameters()[i] + "ns";
    }
    jLEstimatedLifetimes.setListData(lifetimes);


    PlotFirstTrace();
    MakeTracesChart();
    }
    
}//GEN-LAST:event_jListDatasetsValueChanged

private void jListResultObjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListResultObjectsValueChanged
// TODO add your handling code here:
    if (jListResultObjects.getSelectedIndex()!=-1) {
        resultObjects = null; //Current.GetresultNames();
        ResultObject results = resultObjects.get(jListResultObjects.getSelectedIndex());    
        
        listOfDatasets.removeAllElements();
        ArrayList<String> x = results.getNamesOfDatasets();
        for (int i = 0; i < x.size(); i++) {
        String e = x.get(i);
        listOfDatasets.addElement(e);
        }
        jListDatasets.removeAll();
        jListDatasets.setModel(listOfDatasets);
    }
}//GEN-LAST:event_jListResultObjectsValueChanged

private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
// TODO add your handling code here:
    System.out.println("test");
}//GEN-LAST:event_formFocusGained

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    refreshLists();
}//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBUpdate;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jCBToPlot;
    private javax.swing.JList jLEstimatedLifetimes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jListDatasets;
    private javax.swing.JList jListResultObjects;
    private javax.swing.JPanel jPHist;
    private javax.swing.JPanel jPImage;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTMax;
    private javax.swing.JTextField jTMin;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized FlimOutputTopComponent getDefault() {
        if (instance == null) {
            instance = new FlimOutputTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the FlimOutputTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized FlimOutputTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(FlimOutputTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof FlimOutputTopComponent) {
            return (FlimOutputTopComponent) win;
        }
        Logger.getLogger(FlimOutputTopComponent.class.getName()).warning(
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
//        resultObjects = Current.GetresultNames();
//        for (int i = 0; i < resultObjects.size(); i++) {
//        String x = resultObjects.get(i).getNameOfResultsObjects();
//        listOfResultObjectNames.addElement(x);
//        }
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
            return FlimOutputTopComponent.getDefault();
        }
    }

    public void chartMouseClicked(ChartMouseEvent event) {

        ChartEntity entity = event.getEntity();
        XYItemEntity cei = (XYItemEntity) entity;
        int item = 0;

        try {
            item = cei.getItem();

            /*            
            if (dataset.getZValue(1,item)==-1){
            dataset.SetValue(item,flimImage.getIntMap()[item]);
            numSelPix--;
            jLNumSelPix.setText(Integer.toString(numSelPix));
            }else {
            dataset.SetValue(item, -1);
            numSelPix++;
            jLNumSelPix.setText(Integer.toString(numSelPix));
            }
            //            chpanSelectedTrace.getChart().getXYPlot().getRenderer().setSeriesVisible(item, true);
             */
            System.out.println(item);
            UpdateSelectedTrace(item);




        } catch (Exception ex) {
            System.out.println("Error pixel out of chart");
        }

    /*
     * old code
     * 
    int mouseX = event.getTrigger().getX();
    int mouseY = event.getTrigger().getY();
    Point2D p = this.chpan.translateScreenToJava2D(new Point(mouseX, mouseY));
    XYPlot plot = (XYPlot) this.chart.getPlot();
    ChartRenderingInfo info = this.chpan.getChartRenderingInfo();
    Rectangle2D dataArea = info.getPlotInfo().getDataArea();
    
    ValueAxis domainAxis = plot.getDomainAxis();
    RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
    ValueAxis rangeAxis = plot.getRangeAxis();
    RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge();
    
    int chartX =  (int) round(domainAxis.java2DToValue(p.getX(), dataArea, domainAxisEdge));
    int chartY =  (int) round(rangeAxis.java2DToValue(p.getY(), dataArea, rangeAxisEdge));
    
    if ((chartX<=flimImage.getX())&&(chartY<=flimImage.getY())){
    if (dataset.getZValue(1, (int)chartY*flimImage.getX()+(int)chartX)==-1){
    dataset.SetValue(chartY*flimImage.getX()+chartX,flimImage.getIntMap()[chartY*flimImage.getX()+chartX]);
    numSelPix--;
    jLNumSelPix.setText(Integer.toString(numSelPix));
    }else {
    dataset.SetValue((int)chartX, (int)chartY, -1);
    numSelPix++;
    jLNumSelPix.setText(Integer.toString(numSelPix));
    }
    }
     */
    }

    public void chartMouseMoved(ChartMouseEvent event) {
//         System.out.println("ChartMouseMoved");
    }
    
     private ChartPanel CreateHistPanel(int ind) {
        HistogramDataset datasetHist = new HistogramDataset();
        String name;
        double[] data;
        if (ind == 0){
            data = aveLifetimes;
            datasetHist.addSeries("Average lifetime", data, 20, 0.0, maxAveLifetime);
            name = "Average lifetime";
        }
        else {
            name = "Componet "+(ind);
            data = new double[res.GetX2().length];
            for (int i = 0; i < res.GetX2().length; i++)
                data[i] = normAmpl.get(ind-1, i);
            datasetHist.addSeries(name, data, 20, 0.0, 1.0);
        }
        JFreeChart charthist = ChartFactory.createHistogram(
            name,
            null,
            null,
            datasetHist,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );
        XYPlot plot = (XYPlot) charthist.getPlot();
        plot.setForegroundAlpha(0.85f);
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        return new ChartPanel(charthist,true); 
    }
    
    private double[] MakeFlimImage(double[] kinpar, Matrix amplitudes, int numOfSelPix){
        double[] aveLifeTimes = new double[numOfSelPix];
        normAmpl = new Matrix(kinpar.length, numOfSelPix);
        double sumOfConc;
        for (int i=0; i<numOfSelPix; i++){
            aveLifeTimes[i] = 0;
            sumOfConc = 0;
            for (int k = 0; k < kinpar.length; k++){
                sumOfConc=sumOfConc+amplitudes.get(k, i);
            }
            for (int j=0; j<kinpar.length; j++){
                aveLifeTimes[i]=aveLifeTimes[i]+1/kinpar[j]*amplitudes.get(j, i)/sumOfConc; 
                normAmpl.set(j, i, amplitudes.get(j, i)/sumOfConc);
                if (maxAveLifetime < aveLifeTimes[i])
                    maxAveLifetime = aveLifeTimes[i];
            }
        }
        return aveLifeTimes;
    }
    private void UpdateSelectedTrace(int pixnum){  
    
        int item = -1;
        for (int i = 0; i < res.GetX2().length; i++)
            if (res.GetX2()[i]==pixnum){
                item = i;
            }
        
        if (item>-1){
            tracesCollection.getSeries(0).clear();
            tracesCollection.getSeries(1).clear();
            residuals.getSeries(0).clear();
            for (int j=0; j<res.GetX().length; j++){
                tracesCollection.getSeries(0).add(res.GetX()[j], res.GetTraces().get(j, item));
                tracesCollection.getSeries(1).add(res.GetX()[j], res.GetFittedTraces().get(j, item));
                residuals.getSeries(0).add(res.GetX()[j], res.GetResiduals().get(j, item));
            }
        }
    }
    private void PlotFirstTrace(){
            tracesCollection = new XYSeriesCollection();
            residuals = new XYSeriesCollection();
            XYSeries seriaData = new XYSeries("Trace");
            XYSeries seriaFit = new XYSeries("Fittedtrace");
            XYSeries resid = new XYSeries("Residuals");
            for (int j=0; j<res.GetX().length; j++){
                seriaData.add(res.GetX()[j], res.GetTraces().get(j, 0));
                seriaFit.add(res.GetX()[j], res.GetFittedTraces().get(j, 0));
                resid.add(res.GetX()[j],res.GetResiduals().get(j, 0));
            }
           tracesCollection.addSeries(seriaData);
           tracesCollection.addSeries(seriaFit);
           residuals.addSeries(resid);
    }    
    private void MakeTracesChart(){ 
        XYItemRenderer renderer1 = new StandardXYItemRenderer();
        NumberAxis rangeAxis1 = new NumberAxis("Number of counts");
        XYPlot subplot1 = new XYPlot(tracesCollection, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
     
        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        NumberAxis rangeAxis2 = new NumberAxis("Resid");
        rangeAxis2.setAutoRangeIncludesZero(false);
        XYPlot subplot2 = new XYPlot(residuals, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        
        NumberAxis xAxis  = new NumberAxis("Time (ns)");
        xAxis.setRange(res.GetX()[0], res.GetX()[res.GetX().length-1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);
        
        plot.add(subplot1, 3);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        
        JFreeChart tracechart =  new JFreeChart("Selected trace", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        
        ChartPanel chpanSelectedTrace = new ChartPanel(tracechart,true);
        chpanSelectedTrace.setSize(jPSelectedTrace.getSize());
        jPSelectedTrace.removeAll();
        jPSelectedTrace.add(chpanSelectedTrace);
        jPSelectedTrace.repaint(); 
    }
    private ColorCodedImageDataset MakeCCDataset(int origWidth, int origHeigth, double[] selPixels, int ind){
   
        double[] lifeTimeImage = null;
        if (ind == 0){ 
           lifeTimeImage = aveLifetimes;
        } 
        else {
            lifeTimeImage = new double[res.GetX2().length]; 
            for (int i = 0; i < res.GetX2().length; i++){
                lifeTimeImage[i] = normAmpl.get(ind-1, i);
            }
        } 
        
        double[] image = new double[origWidth*origHeigth];
        for (int i = 0; i<selPixels.length; i++){
             image[(int)selPixels[i]] = lifeTimeImage[i];
        }
        dataset = new ColorCodedImageDataset(origWidth, origHeigth, image, false); 
        return dataset;
    }
    private void MakeImageChart(XYZDataset dataset, double min, double max, boolean mode){
        NumberAxis xAxis = new NumberAxis("Wavelengths (nm)");
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
//        xAxis.setRange(data.GetX2()[0],data.GetX2()[data.GetX2().length-1]);      
        xAxis.setVisible(false);
        NumberAxis yAxis = new NumberAxis("Time (ps)");
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setInverted(true);
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
 //       yAxis.setRange(data.GetX()[0],data.GetX()[data.GetX().length-1]);
        yAxis.setVisible(false);
        XYBlockRenderer renderer = new XYBlockRenderer();
        PaintScale scale = new RainbowPaintScale(min, max, true, mode);

        renderer.setPaintScale(scale);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);        
   
        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);

        chart = new JFreeChart(plot);
        chart.removeLegend();   
  
        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        
        scaleAxis.setRange(min,max);
        
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(scale,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
    //        legend.setAxisOffset(5.0);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
    //        legend.setFrame(new BlockBorder(Color.red));
    //        legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart.getBackgroundPaint());
        chart.addSubtitle(legend); 
    }
    
    public void refreshLists() {
        
        int selected = jListResultObjects.getSelectedIndex();
        
        listOfResultObjectNames.removeAllElements();
        resultObjects = null;//Current.GetresultNames();
        for (int i = 0; i < resultObjects.size(); i++) {
        String x = resultObjects.get(i).getNameOfResultsObjects();
        listOfResultObjectNames.addElement(x);
        }
        jListResultObjects.setModel(listOfResultObjectNames);
        
        jListResultObjects.setSelectedIndex(selected);

    }
    
    
}
