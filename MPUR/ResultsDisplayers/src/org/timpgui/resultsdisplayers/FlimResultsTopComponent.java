/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.resultsdisplayers;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
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
    private double minAveLifetime;
    private double[] aveLifetimes;
    private Matrix normAmpl;
    private XYSeriesCollection tracesCollection,  residuals;
    private TimpResultDataset res;
    private Object[] listToPlot;
    private int selectedietm;
    private ColorCodedImageDataset dataset;
    private int numberOfComponents;
    private int selImWidth, selImHeight;
    private int[] selImInd;
    private IntensImageDataset intensutyImageDataset;

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
        jLEstimatedLifetimes.setListData(lifetimes);

//find rectangle for selected image
        int firstLine = (int) floor(res.getX2()[0]/res.getOrwidth());
        int lastLine = (int) floor(res.getX2()[res.getX2().length-1]/res.getOrwidth());
        int firstCol = res.getX2().length-1;
        int lastCol = 0;
        int tempnum;
        for (int i = 0; i < res.getX2().length; i++){
            tempnum = (int)(res.getX2()[i] - floor(res.getX2()[i] / res.getOrwidth()) * res.getOrwidth());
            if (tempnum<firstCol){
                firstCol = tempnum;
            }
            if (tempnum>lastCol){
                lastCol = tempnum;
            }
        }
        selImWidth = lastCol - firstCol+1;
        selImHeight = lastLine - firstLine+1;
        selImInd = new int[res.getX2().length];
        int indX, indY;
        for (int i = 0; i< res.getX2().length; i++){
            indY = (int) floor(res.getX2()[i]/res.getOrwidth())-firstLine;
            indX = (int)(res.getX2()[i] - floor(res.getX2()[i] / res.getOrwidth()) * res.getOrwidth())-firstCol;
            selImInd[i] = selImWidth*indY+indX;
        }

//create intence image with selected pixels
        intensutyImageDataset = new IntensImageDataset(res.getOrwidth(),res.getOrheigh(),res.getIntenceIm());
        for (int i = 0; i < res.getX2().length; i++){
            intensutyImageDataset.SetValue((int)res.getX2()[i], -1);
        }
        PaintScale ps = new GrayPaintScalePlus(res.getMinInt(), res.getMaxInt(), -1);
        JFreeChart intIm = createScatChart(ImageUtilities.createColorCodedImage(intensutyImageDataset, ps), ps,res.getOrwidth(),res.getOrheigh());
        ChartPanel intImPanel = new ChartPanel(intIm);
        intImPanel.setFillZoomRectangle(true);
        intImPanel.setMouseWheelEnabled(true);
        jPIntenceImage.add(intImPanel);
        jTFMinIntence.setText(String.valueOf(res.getMinInt()));
        jTFMaxIntence.setText(String.valueOf(res.getMaxInt()));

//create lifetime image
        aveLifetimes = MakeFlimImage(res.getKineticParameters(), res.getSpectra(), res.getX2().length);
        IntensImageDataset aveLifetimeDataset = new IntensImageDataset(selImHeight,selImWidth,new double[selImWidth*selImHeight]);
        for (int i = 0; i < res.getX2().length; i++){
            aveLifetimeDataset.SetValue(selImInd[i], aveLifetimes[i]);
        }
        ps = new RainbowPaintScale(0.0000001, maxAveLifetime);
        JFreeChart aveLifetimeChart = createScatChart(ImageUtilities.createColorCodedImage(aveLifetimeDataset, ps), ps,selImWidth,selImHeight);
        ChartPanel aveLifetimePanel = new ChartPanel(aveLifetimeChart);
        aveLifetimePanel.setFillZoomRectangle(true);
        aveLifetimePanel.setMouseWheelEnabled(true);
        jPImage.add(aveLifetimePanel);
        jTFMinLifetime.setText("0");
        jTFMaxLifetime.setText(String.valueOf(maxAveLifetime));

// create and plot histogram of average lifetimes
        jPHist.add(updateHistPanel(aveLifetimes, minAveLifetime, maxAveLifetime, 20));
        jTFChNumHist.setText("20");
//create and plot SVD of the residuals
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLEstimatedLifetimes = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPIntenceImage = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTFMaxIntence = new javax.swing.JTextField();
        jTFMinIntence = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPImage = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTFMaxLifetime = new javax.swing.JTextField();
        jTFMinLifetime = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPHist = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTFChNumHist = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPSelectedTrace = new javax.swing.JPanel();
        jBUpdate = new javax.swing.JButton();
        jTMin = new javax.swing.JTextField();
        jTMax = new javax.swing.JTextField();
        jCBToPlot = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jPanel2.setPreferredSize(new java.awt.Dimension(1200, 597));

        jScrollPane2.setViewportView(jLEstimatedLifetimes);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel4.text")); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel4, java.awt.BorderLayout.CENTER);

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
                .addComponent(jPRightSingVectors, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPRightSingVectors, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addComponent(jPLeftSingVectors, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addComponent(jPSingValues, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1034, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel5.text")); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel6.text")); // NOI18N

        jPIntenceImage.setBackground(new java.awt.Color(0, 0, 0));
        jPIntenceImage.setMaximumSize(new java.awt.Dimension(100, 100));
        jPIntenceImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPIntenceImage.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jButton1.text")); // NOI18N
        jButton1.setIconTextGap(2);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTFMaxIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMaxIntence.setText(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jTFMaxIntence.text")); // NOI18N

        jTFMinIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMinIntence.setText(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jTFMinIntence.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addComponent(jTFMinIntence, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFMaxIntence, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(2, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel2)
                .addComponent(jTFMinIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1)
                .addComponent(jTFMaxIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, 0, 313, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
            .addComponent(jPIntenceImage, javax.swing.GroupLayout.PREFERRED_SIZE, 313, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPIntenceImage, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPImage.setBackground(new java.awt.Color(0, 0, 0));
        jPImage.setMaximumSize(new java.awt.Dimension(450, 350));
        jPImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPImageMouseClicked(evt);
            }
        });
        jPImage.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jButton2.text")); // NOI18N
        jButton2.setIconTextGap(2);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTFMaxLifetime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMaxLifetime.setText(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jTFMaxLifetime.text")); // NOI18N

        jTFMinLifetime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMinLifetime.setText(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jTFMinLifetime.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(11, 11, 11)
                .addComponent(jTFMinLifetime, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFMaxLifetime, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel8)
                .addComponent(jTFMinLifetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton2)
                .addComponent(jTFMaxLifetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 302, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPHist.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPHist.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jLabel15.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jButton5.text")); // NOI18N
        jButton5.setIconTextGap(2);
        jButton5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTFChNumHist.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFChNumHist.setText(org.openide.util.NbBundle.getMessage(FlimResultsTopComponent.class, "FlimResultsTopComponent.jTFChNumHist.text")); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(jTFChNumHist, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel15)
                .addComponent(jTFChNumHist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton5))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(jPHist, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPHist, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(156, 156, 156))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jBUpdate)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(173, 173, 173))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jCBToPlot, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(616, 616, 616))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBToPlot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jBUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(82, 82, 82)
                .addComponent(jTMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
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
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please specify correct number of channels"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}//GEN-LAST:event_jBUpdateActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    int newNumChHish;
    try {
        newNumChHish = Integer.parseInt(jTFChNumHist.getText());
        jPHist.removeAll();
        ChartPanel chpanHist = updateHistPanel(aveLifetimes, minAveLifetime, maxAveLifetime, newNumChHish);
        chpanHist.setSize(jPHist.getSize());
        jPHist.add(chpanHist);
        jPHist.repaint();
    }catch(NumberFormatException ex) {
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please specify correct number of channels"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    double newMinAmpl, newMaxAmpl;
    try {
        newMinAmpl = Double.parseDouble(jTFMinIntence.getText());
        newMaxAmpl = Double.parseDouble(jTFMaxIntence.getText());
        PaintScale ps = new GrayPaintScalePlus(newMinAmpl, newMaxAmpl, -1);
        JFreeChart intIm = createScatChart(ImageUtilities.createColorCodedImage(intensutyImageDataset, ps), ps,res.getOrwidth(),res.getOrheigh());
        ChartPanel intImPanel = new ChartPanel(intIm);
        intImPanel.setFillZoomRectangle(true);
        intImPanel.setMouseWheelEnabled(true);
        jPIntenceImage.removeAll();
        intImPanel.setSize(jPIntenceImage.getSize());
        jPIntenceImage.add(intImPanel);
        jPIntenceImage.repaint();

    }catch(NumberFormatException ex) {
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please specify correct number of channels"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
double newMinLifetime, newMaxLifetime;
    try {
        newMinLifetime = Double.parseDouble(jTFMinLifetime.getText());
        newMaxLifetime = Double.parseDouble(jTFMaxLifetime.getText());

        IntensImageDataset aveLifetimeDataset = new IntensImageDataset(selImHeight,selImWidth,new double[selImWidth*selImHeight]);
        for (int i = 0; i < res.getX2().length; i++){
            aveLifetimeDataset.SetValue(selImInd[i], aveLifetimes[i]);
        }
        PaintScale ps = new RainbowPaintScale(newMinLifetime, newMaxLifetime);
        JFreeChart aveLifetimeChart = createScatChart(ImageUtilities.createColorCodedImage(aveLifetimeDataset, ps), ps,selImWidth,selImHeight);
        ChartPanel aveLifetimePanel = new ChartPanel(aveLifetimeChart);
        aveLifetimePanel.setFillZoomRectangle(true);
        aveLifetimePanel.setMouseWheelEnabled(true);
        jPImage.removeAll();
        aveLifetimePanel.setSize(jPImage.getSize());
        jPImage.add(aveLifetimePanel);
        jPImage.repaint();
    }catch(NumberFormatException ex) {
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please specify correct number of channels"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jCBToPlot;
    private javax.swing.JList jLEstimatedLifetimes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPHist;
    private javax.swing.JPanel jPImage;
    private javax.swing.JPanel jPIntenceImage;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTFChNumHist;
    private javax.swing.JTextField jTFMaxIntence;
    private javax.swing.JTextField jTFMaxLifetime;
    private javax.swing.JTextField jTFMinIntence;
    private javax.swing.JTextField jTFMinLifetime;
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

    private ChartPanel updateHistPanel(double[] data, double minVal, double maxVal, int numPockets) {
        HistogramDataset datasetHist = new HistogramDataset();
        datasetHist.addSeries("seria1", data, numPockets, minAveLifetime, maxAveLifetime);
        JFreeChart charthist = ChartFactory.createHistogram(
            null,
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


    private ChartPanel CreateHistPanel(int ind) {
       HistogramDataset datasetHist = new HistogramDataset();
       String name;
       double[] data;
       if (ind == 0){
           data = aveLifetimes;
           datasetHist.addSeries("Average lifetime", data, 20, minAveLifetime, maxAveLifetime);
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
//       plot.setForegroundAlpha(0.85f);
       XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
       renderer.setDrawBarOutline(false);
       return new ChartPanel(charthist,true); 
    }
    
    private double[] MakeFlimImage(double[] kinpar, Matrix amplitudes, int numOfSelPix){
        minAveLifetime = Double.MAX_VALUE;
        double[] aveLifeTimes = new double[numOfSelPix];
        normAmpl = new Matrix(numberOfComponents, numOfSelPix);
        double sumOfConc;
        for (int i=0; i<numOfSelPix; i++){
            aveLifeTimes[i] = 0;
            sumOfConc = 0;
            for (int k = 0; k < numberOfComponents; k++){
                sumOfConc=sumOfConc+amplitudes.get(k, i);
            }
            for (int j=0; j<numberOfComponents; j++){
                aveLifeTimes[i]=aveLifeTimes[i]+1/kinpar[j]*amplitudes.get(j, i)/sumOfConc; 
                normAmpl.set(j, i, amplitudes.get(j, i)/sumOfConc);
            }
            if (maxAveLifetime < aveLifeTimes[i])
                maxAveLifetime = aveLifeTimes[i];
            if (minAveLifetime > aveLifeTimes[i])
                minAveLifetime = aveLifeTimes[i];
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


//create images with first 2 RSV
 
        double[] tempRsingVec = null;
        double minVal = 0;
        double maxVal = 0;
        for (int j = 0; j < n; j++) {
//            seria = new XYSeries("RSV" + (j + 1));
            tempRsingVec = new double[selImWidth*selImHeight];
            minVal = result.getV().get(0, j);
            maxVal = result.getV().get(0, j);
            for (int i = 0; i < res.getX2().length; i++) {
                tempRsingVec[selImInd[i]] = result.getV().get(i, j);
                if (tempRsingVec[i] < minVal)
                    minVal = tempRsingVec[i];
                if (tempRsingVec[i] > maxVal)
                    maxVal = tempRsingVec[i];
            }

            IntensImageDataset rSingVec = new IntensImageDataset(selImHeight,selImWidth,tempRsingVec);
            PaintScale ps = new RainbowPaintScale(minVal, maxVal);
            JFreeChart rSingVect = createScatChart(ImageUtilities.createColorCodedImage(rSingVec, ps), ps,selImWidth,selImHeight);
            rSingVect.setTitle("R Singular vector "+String.valueOf(j+1));
            rSingVect.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
            ChartPanel rSingVectPanel = new ChartPanel(rSingVect);
            rSingVectPanel.setFillZoomRectangle(true);
            rSingVectPanel.setMouseWheelEnabled(true);
            jPRightSingVectors.add(rSingVectPanel);
        }
        
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
        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
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
        chart_temp.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
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
        legend.setStripWidth(10);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart_temp.getBackgroundPaint());
        chart_temp.addSubtitle(legend);

        return chart_temp;
    }

}
