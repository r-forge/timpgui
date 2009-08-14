/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.resultsdisplayers;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.GrayPaintScalePlus;
import nl.wur.flim.jfreechartcustom.ImageUtilities;
import nl.wur.flim.jfreechartcustom.IntensImageDataset;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDataImageAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.LogAxis;
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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.TimpResultDataObject;
import static java.lang.Math.max;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
//import org.openide.util.Utilities;
/**
 * Top component which displays something.
 */
final class FlimResultsTopComponent extends TopComponent implements ChartMouseListener {

    private static FlimResultsTopComponent instance;
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
    private int numberOfComponents;
    private static DefaultListModel listOfResultObjectNames = new DefaultListModel();
    private static DefaultListModel listOfDatasets = new DefaultListModel();

    FlimResultsTopComponent(TimpResultDataObject dataObj) {
        initComponents();
        setToolTipText(NbBundle.getMessage(FlimResultsTopComponent.class, "HINT_FlimOutputTopComponent"));
        setName(dataObj.getName());
        res = dataObj.getTimpResultDataset();
//        res.calcRangeInt();
        numberOfComponents = res.getKineticParameters().length/2;
        double errTau;
        double tau;
//================tab 1=================
//fil in list of estimate lifetimes and errors
        Object[] lifetimes = new Object[res.getKineticParameters().length];
        for (int i = 0; i < numberOfComponents; i++) {
            tau = 1 / res.getKineticParameters()[i];
            lifetimes[2*i] = "Tau" + (i + 1) + "=" + String.format(String.valueOf(tau), "#.###") + "ns";
            errTau = max(abs(tau-(1 /(res.getKineticParameters()[i]+res.getKineticParameters()[i+numberOfComponents]))),
                          abs(tau-(1 /(res.getKineticParameters()[i]-res.getKineticParameters()[i+numberOfComponents]))));
            lifetimes[2*i+1] = "er_tau"+ (i + 1) + "=" + String.format(String.valueOf(errTau), "#.###");
        }

//find rectangle for selected image
        int firstLine = (int) floor(res.getX2()[0]/res.getOrwidth());
        int lastLine = (int) floor(res.getX2()[res.getX2().length-1]/res.getOrwidth());
        int firstCol = res.getX2().length-1;
        int lastCol = 0;
        int tempnum;
        for (int i = 0; i < res.getX2().length; i++){
            tempnum = (int)(res.getX2()[i] - floor(res.getX2()[res.getX2().length - 1] / res.getOrwidth()) * res.getOrwidth());
            if (tempnum<firstCol){
                firstCol = tempnum;
            }
            if (tempnum>lastCol){
                lastCol = tempnum;
            }
        }



//TODO create and plot intencity image with selected pixels
        IntensImageDataset intensutyImageDataset = new IntensImageDataset(res.getOrwidth(),res.getOrheigh(),res.getIntenceIm());
//        PaintScale ps = new RainbowPaintScale(res.getMinInt(), res.getMaxInt());
        PaintScale ps = new GrayPaintScalePlus(res.getMinInt(), res.getMaxInt(), -1);
        JFreeChart intIm = createScatChart(ImageUtilities.createColorCodedImage(intensutyImageDataset, ps), ps,res.getOrwidth(),res.getOrheigh());
        ChartPanel intImPanel = new ChartPanel(intIm);
        jPIntenceImage.add(intImPanel);

//        listToPlot = new Object[numberOfComponents + 1];
        aveLifetimes = MakeFlimImage(res.getKineticParameters(), res.getSpectra(), res.getX2().length);        
//        listToPlot[0] = "Average Lifetime";
//        jCBToPlot.addItem(listToPlot[0]);
//        for (int i = 0; i < res.getKineticParameters().length/2; i++) {
//            listToPlot[i + 1] = "Component " + (i + 1);
//            jCBToPlot.addItem(listToPlot[i + 1]);
//   //         lifetimes[i] = "Tau" + (i + 1) + "=" + String.format(String.valueOf(1 / res.getKineticParameters()[2*i]), "#.###") + "ns";
//        }

        jLEstimatedLifetimes.setListData(lifetimes);
        calculateSVDResiduals();
//        PlotFirstTrace();
//        MakeTracesChart();
    
    }

//    private ArrayList<ResultObject> resultObjects;

    private FlimResultsTopComponent() {
        chart = null;
        maxAveLifetime = 0;
        res = new TimpResultDataset();
        normAmpl = null;
        initComponents();
        setName(NbBundle.getMessage(FlimResultsTopComponent.class, "CTL_FlimOutputTopComponent"));
        setToolTipText(NbBundle.getMessage(FlimResultsTopComponent.class, "HINT_FlimOutputTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPHist = new javax.swing.JPanel();
        jPImage = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLEstimatedLifetimes = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPIntenceImage = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPSelectedTrace = new javax.swing.JPanel();
        jBUpdate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTMin = new javax.swing.JTextField();
        jTMax = new javax.swing.JTextField();
        jCBToPlot = new javax.swing.JComboBox();

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jPHist.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPHist.setLayout(new java.awt.BorderLayout());

        jPImage.setBackground(new java.awt.Color(0, 0, 0));
        jPImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPImage.setMaximumSize(new java.awt.Dimension(450, 350));
        jPImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPImageMouseClicked(evt);
            }
        });
        jPImage.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(jLEstimatedLifetimes);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel3.text")); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel4.text")); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel5.add(jPanel9, gridBagConstraints);

        jPSingValues.setBackground(new java.awt.Color(255, 255, 255));
        jPSingValues.setLayout(new java.awt.BorderLayout());

        jPLeftSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectors.setLayout(new java.awt.BorderLayout());

        jPRightSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectors.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPLeftSingVectors, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSingValues, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPRightSingVectors, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(134, 134, 134))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPRightSingVectors, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(jPLeftSingVectors, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(jPSingValues, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jPanel8, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel5.text")); // NOI18N

        jPIntenceImage.setBackground(new java.awt.Color(0, 0, 0));
        jPIntenceImage.setMaximumSize(new java.awt.Dimension(100, 100));
        jPIntenceImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPIntenceImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPIntenceImageMouseClicked(evt);
            }
        });
        jPIntenceImage.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(jPIntenceImage, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jPHist, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1461, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPHist, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPIntenceImage, javax.swing.GroupLayout.PREFERRED_SIZE, 243, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))))
                .addGap(54, 54, 54)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPSelectedTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedTrace.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jBUpdate, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jBUpdate.text")); // NOI18N
        jBUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdateActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel2.text")); // NOI18N

        jCBToPlot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBToPlotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(43, Short.MAX_VALUE)
                        .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jBUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCBToPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(309, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(262, 262, 262)
                    .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(258, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jCBToPlot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jBUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(157, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(141, 141, 141)
                    .addComponent(jPSelectedTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addGap(141, 141, 141)))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void jPImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPImageMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jPImageMouseClicked

private void jCBToPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBToPlotActionPerformed
//combobox to plot selection
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

    MakeImageChart(MakeCCDataset(64, 64, res.getX2(), selectedietm), minVal, maxVal, true);
    ChartPanel chpanImage = new ChartPanel(chart,true);
    jPImage.removeAll();
//    chpanImage.setSize(jPImage.getSize());
    chpanImage.addChartMouseListener(this);
    jPImage.add(chpanImage);
//    jPImage.repaint();
    jPHist.removeAll();
    ChartPanel chpanHist = CreateHistPanel(selectedietm);
//    chpanHist.setSize(jPHist.getSize());
    jPHist.add(chpanHist);
//    jPHist.repaint();

//    System.out.println(jCBToPlot.getModel().getSelectedItem());
}//GEN-LAST:event_jCBToPlotActionPerformed

private void jBUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdateActionPerformed
    double minVal,  maxVal;
    try {

        minVal = Float.parseFloat(jTMin.getText());
        maxVal = Float.parseFloat(jTMax.getText());
        MakeImageChart(MakeCCDataset(64, 64, res.getX2(), selectedietm), minVal, maxVal, true);
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

private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
// TODO add your handling code here:
    System.out.println("test");
}//GEN-LAST:event_formFocusGained

private void jPIntenceImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPIntenceImageMouseClicked
    // TODO add your handling code here:
}//GEN-LAST:event_jPIntenceImageMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBUpdate;
    private javax.swing.JComboBox jCBToPlot;
    private javax.swing.JList jLEstimatedLifetimes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPHist;
    private javax.swing.JPanel jPImage;
    private javax.swing.JPanel jPIntenceImage;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTMax;
    private javax.swing.JTextField jTMin;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized FlimResultsTopComponent getDefault() {
        if (instance == null) {
            instance = new FlimResultsTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the FlimOutputTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized FlimResultsTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(FlimResultsTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof FlimResultsTopComponent) {
            return (FlimResultsTopComponent) win;
        }
        Logger.getLogger(FlimResultsTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
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
            return FlimResultsTopComponent.getDefault();
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
            data = new double[res.getX2().length];
            for (int i = 0; i < res.getX2().length; i++)
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
        normAmpl = new Matrix(kinpar.length/2, numOfSelPix);
        double sumOfConc;
        for (int i=0; i<numOfSelPix; i++){
            aveLifeTimes[i] = 0;
            sumOfConc = 0;
            for (int k = 0; k < kinpar.length/2; k++){
                sumOfConc=sumOfConc+amplitudes.get(k, i);
            }
            for (int j=0; j<kinpar.length/2; j++){
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
        for (int i = 0; i < res.getX2().length; i++)
            if (res.getX2()[i]==pixnum){
                item = i;
            }
        
        if (item>-1){
            tracesCollection.getSeries(0).clear();
            tracesCollection.getSeries(1).clear();
            residuals.getSeries(0).clear();
            for (int j=0; j<res.getX().length; j++){
                tracesCollection.getSeries(0).add(res.getX()[j], res.getTraces().get(j, item));
                tracesCollection.getSeries(1).add(res.getX()[j], res.getFittedTraces().get(j, item));
                residuals.getSeries(0).add(res.getX()[j], res.getResiduals().get(j, item));
            }
        }
    }
    private void PlotFirstTrace(){
            tracesCollection = new XYSeriesCollection();
            residuals = new XYSeriesCollection();
            XYSeries seriaData = new XYSeries("Trace");
            XYSeries seriaFit = new XYSeries("Fittedtrace");
            XYSeries resid = new XYSeries("Residuals");
            for (int j=0; j<res.getX().length; j++){
                seriaData.add(res.getX()[j], res.getTraces().get(j, 0));
                seriaFit.add(res.getX()[j], res.getFittedTraces().get(j, 0));
                resid.add(res.getX()[j],res.getResiduals().get(j, 0));
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
        xAxis.setRange(res.getX()[0], res.getX()[res.getX().length-1]);
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
            lifeTimeImage = new double[res.getX2().length];
            for (int i = 0; i < res.getX2().length; i++){
                lifeTimeImage[i] = normAmpl.get(ind-1, i);
            }
        } 
        
        double[] image = new double[origWidth*origHeigth];
        for (int i = 0; i<selPixels.length; i++){
             image[(int)selPixels[i]] = lifeTimeImage[i];
        }
        dataset = new ColorCodedImageDataset(origWidth, origHeigth, image, res.getX2(), res.getX() ,false);
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
    
//    public void refreshLists() {
//
//        int selected = jListResultObjects.getSelectedIndex();
//
//        listOfResultObjectNames.removeAllElements();
//        resultObjects = null;//Current.GetresultNames();
//        for (int i = 0; i < resultObjects.size(); i++) {
//        String x = resultObjects.get(i).getNameOfResultsObjects();
//        listOfResultObjectNames.addElement(x);
//        }
//        jListResultObjects.setModel(listOfResultObjectNames);
//
//        jListResultObjects.setSelectedIndex(selected);
//
//    }
//

    private void calculateSVDResiduals(){
        int n = 2;
//do SVD
        SingularValueDecomposition result;
        result = res.getResiduals().svd();

//creare collection with first 2 LSV
        XYSeriesCollection lSVCollection = new XYSeriesCollection();
        XYSeries seria;
        for (int j = 0; j < n; j++) {
            seria = new XYSeries("LSV" + (j + 1));
            for (int i = 0; i < res.getX().length; i++) {
                seria.add(res.getX()[i], result.getU().get(i, j));
            }
            lSVCollection.addSeries(seria);
        }
//creare chart for 2 LSV
        JFreeChart tracechart = ChartFactory.createXYLineChart(
                    "Left singular vectors",
                    "Time (ns)",
                    null,
                    lSVCollection,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);
        tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
        ChartPanel chpan = new ChartPanel(tracechart);
//add chart with 2 LSV to JPannel
        jPLeftSingVectors.removeAll();
        jPLeftSingVectors.add(chpan);



//create collection with first 2 RSV
        XYSeriesCollection rSVCollection = new XYSeriesCollection();
        for (int j = 0; j < n; j++) {
            seria = new XYSeries("RSV" + (j + 1));
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], result.getV().get(i, j));
            }
            rSVCollection.addSeries(seria);
        }

//creare charts for RSV
        tracechart = ChartFactory.createXYLineChart(
                    "Right singular vectors",
                    "Wavelength (nm)",
                    null,
                    rSVCollection,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
        chpan = new ChartPanel(tracechart);
//add chart with 2 RSV to JPannel
        jPRightSingVectors.removeAll();
        jPRightSingVectors.add(chpan);

//creare collection with singular values
        XYSeriesCollection sVCollection = new XYSeriesCollection();
        seria = new XYSeries("SV");
        for (int i = 0; i < result.getSingularValues().length; i++) {
            seria.add(i+1, result.getSingularValues()[i]);
        }
        sVCollection.addSeries(seria);


//creare chart for singular values
        tracechart = ChartFactory.createXYLineChart(
                    "Screeplot",
                    "Number of factors (#)",
                    null,
                    sVCollection,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getXYPlot().setRangeAxis(new LogAxis("Log(SV)"));
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) tracechart.getXYPlot().getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.white);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));

        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);

        chpan = new ChartPanel(tracechart);
//add chart with singularvalues to JPannel
        jPSingValues.removeAll();
        jPSingValues.add(chpan);
    }

    private JFreeChart createScatChart(BufferedImage image, PaintScale ps, int plotWidth, int plotHeigh){
        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, new XYSeriesCollection(), PlotOrientation.VERTICAL, false, false,
                false);
        XYDataImageAnnotation ann = new XYDataImageAnnotation(image, 0,0,
                plotWidth, plotHeigh, true);
        XYPlot plot = (XYPlot) chart_temp.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.getRenderer().addAnnotation(ann, Layer.BACKGROUND);
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        xAxis.setVisible(false);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        yAxis.setVisible(false);

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(ps.getLowerBound(),ps.getUpperBound());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(ps,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
    //        legend.setAxisOffset(5.0);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
    //        legend.setFrame(new BlockBorder(Color.red));
    //        legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart_temp.getBackgroundPaint());
        chart_temp.addSubtitle(legend);

        return chart_temp;
    }

}
