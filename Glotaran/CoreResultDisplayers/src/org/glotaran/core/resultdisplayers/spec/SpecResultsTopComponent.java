package org.glotaran.core.resultdisplayers.spec;

import Jama.Matrix;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.glotaran.core.interfaces.TimpControllerInterface;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.main.nodes.dataobjects.TimpResultDataObject;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.resultdisplayers.common.panels.CommonTools;
import org.glotaran.core.resultdisplayers.common.panels.SelectTracesForPlot;
import org.glotaran.jfreechartcustom.ColorCodedImageDataset;
import org.glotaran.jfreechartcustom.GraphPanel;
import org.glotaran.jfreechartcustom.ImageCrosshairLabelGenerator;
import org.glotaran.jfreechartcustom.ImageUtilities;
import org.glotaran.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDataImageAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
/**
 * Top component which displays something.
 */
public final class SpecResultsTopComponent extends TopComponent implements ChartChangeListener, ChartMouseListener {

    private static SpecResultsTopComponent instance;
    private final static long serialVersionUID = 1L;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "StreakOutTopComponent";
    private TimpResultDataset res;
    private ChartPanel chpanImage;
    private JFreeChart subchartTimeTrace;
    private JFreeChart subchartResidualsTime;
    private JFreeChart subchartWaveTrace;
    private JFreeChart subchartResidualsWave;
    ArrayList<Integer> selectedTimeTraces = new ArrayList<Integer>();
    ArrayList<Integer> selectedWaveTraces = new ArrayList<Integer>();
    private Range lastXRange;
    private Range lastYRange;
    private Range wholeXRange;
    private Range wholeYRange;
    private Crosshair crosshair1;
    private Crosshair crosshair2;
    private JFreeChart chartMain;
    private ColorCodedImageDataset dataset;
    private int numberOfComponents;
    private Matrix leftSingVec;
    private Matrix leftSingVecPart;
    private double[] timePart;
    private double[] t0Curve;
    private TimpResultDataObject dataObject;


    public SpecResultsTopComponent(TimpResultDataObject dataObj) {
        initComponents();
        setToolTipText(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
        setName(dataObj.getName());
        this.dataObject = dataObj;
        res = dataObject.getTimpResultDataset();
        res.calcRangeInt();
        if (res.getJvec()!=null) {
            numberOfComponents = res.getJvec().length / 2;
        }
        else {
            numberOfComponents = res.getKineticParameters().length / 2;
        }
        Object[] rates = new Object[res.getKineticParameters().length];

        DecimalFormat paramFormat = new DecimalFormat("##0.0000");
        DecimalFormat errFormat = new DecimalFormat("##0.0000");

        for (int i = 0; i < numberOfComponents; i++) {
            rates[i] = "k" + (i+1) + "=" +  new Formatter().format("%g",res.getKineticParameters()[i]);
        }
        jLKineticParameters.setListData(rates);

        Object[] irfpar = new Object[res.getIrfpar().length/2 + 2];
        for (int i = 0; i < irfpar.length ; i++) {
            irfpar[i] = "irf" + (i+1) + "=" + new Formatter().format("%g",res.getIrfpar()[i]);
        }

        irfpar[res.getIrfpar().length/2] = "-----";
        irfpar[res.getIrfpar().length/2+1] = "RMS =" + (new Formatter().format("%g",res.getRms())).toString();
        
                jLSpectralParameters.setListData(irfpar);
                
//first tab
        double centrWave;
        double[] dispParam;
        double timeZero;
        centrWave = res.getLamdac();
        if (res.getParmu()!=null) {
            dispParam = res.getParmu();
        }
        else {
            dispParam = new double[]{0};
        }
        if (res.getIrfpar()!=null) {
            timeZero = res.getIrfpar()[0];
        }
        else {
            timeZero = 0;
        }

        calculateDispersionCurve(centrWave, dispParam, timeZero, dispParam.length/2);
        if (res.getSpectra().getRowDimension()>numberOfComponents){
            jTBShowChohSpec.setEnabled(true);
        }
        plotSpectrTrace();
        GraphPanel conc = createLinTimePlot(res.getConcentrations(), res.getX());
        jPConcentrations.removeAll();
        jPConcentrations.add(conc);

        leftSingVec = calculateSVDresid(res.getResiduals(),res.getX(),res.getX2(),jPRightSingVectors, jPLeftSingVectors, jPSingValues);

//=====================second tab (can be done in BG)
//calculate dispersion curve

        makeImageChart();
        MakeTracesChart();
        jSColum.setMaximum(dataset.GetImageWidth()-1);
        jSColum.setMinimum(0);
        jSColum.setValue(0);
        jSRow.setMaximum(dataset.GetImageHeigth()-1);
        jSRow.setMinimum(0);
        jSRow.setValue(0);

        jTFCentrWave.setText(String.valueOf(res.getLamdac()));
        String parmuStr = "";
        for (int i = 0; i < res.getParmu().length/2; i++){
            if (i > 0) {
                parmuStr = parmuStr + ",";
            }
            parmuStr += paramFormat.format(res.getParmu()[i]);
        }
        jTFCurvParam.setText(parmuStr);
    }

    private SpecResultsTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
    }

    public TimpResultDataObject getDataObject() {
        return dataObject;
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
        jPanel14 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jTBLinLog = new javax.swing.JToggleButton();
        jTFLinPart = new javax.swing.JTextField();
        jBUpdLinLog = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jTBShowChohSpec = new javax.swing.JToggleButton();
        jTBNormToMax = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPSAS = new javax.swing.JPanel();
        jPDAS = new javax.swing.JPanel();
        jPSASnorm = new javax.swing.JPanel();
        jPDASnorm = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLKineticParameters = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLSpectralParameters = new javax.swing.JList();
        jPConcentrations = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jTBLinLogTraces = new javax.swing.JToggleButton();
        jTFLinPartTraces = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPSelectedWaveTrace = new javax.swing.JPanel();
        jPSelectedTimeTrace = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPSpecImage = new javax.swing.JPanel();
        jSColum = new javax.swing.JSlider();
        jSRow = new javax.swing.JSlider();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCBDispCurveShow = new javax.swing.JCheckBox();
        jTFCentrWave = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFCurvParam = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPSingValuesPart = new javax.swing.JPanel();
        jPLeftSingVectorsPart = new javax.swing.JPanel();
        jPRightSingVectorsPart = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPSelTimeTrCollection = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPSelWavTrCollection = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1000, 1200));

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1500, 1500));
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 1000));

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jTBLinLog, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTBLinLog.text")); // NOI18N
        jTBLinLog.setFocusable(false);
        jTBLinLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBLinLog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBLinLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBLinLogActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBLinLog);

        jTFLinPart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFLinPart.setText(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTFLinPart.text")); // NOI18N
        jTFLinPart.setMaximumSize(new java.awt.Dimension(70, 19));
        jToolBar1.add(jTFLinPart);

        org.openide.awt.Mnemonics.setLocalizedText(jBUpdLinLog, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jBUpdLinLog.text")); // NOI18N
        jBUpdLinLog.setEnabled(false);
        jBUpdLinLog.setFocusable(false);
        jBUpdLinLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBUpdLinLog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBUpdLinLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdLinLogActionPerformed(evt);
            }
        });
        jToolBar1.add(jBUpdLinLog);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jTBShowChohSpec, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTBShowChohSpec.text")); // NOI18N
        jTBShowChohSpec.setEnabled(false);
        jTBShowChohSpec.setFocusable(false);
        jTBShowChohSpec.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBShowChohSpec.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBShowChohSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBShowChohSpecActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBShowChohSpec);

        org.openide.awt.Mnemonics.setLocalizedText(jTBNormToMax, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTBNormToMax.text")); // NOI18N
        jTBNormToMax.setFocusable(false);
        jTBNormToMax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBNormToMax.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBNormToMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBNormToMaxActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBNormToMax);

        jScrollPane3.setBorder(null);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel6.setPreferredSize(new java.awt.Dimension(900, 1000));

        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jPSAS.setBackground(new java.awt.Color(255, 255, 255));
        jPSAS.setPreferredSize(new java.awt.Dimension(230, 110));
        jPSAS.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPSAS);

        jPDAS.setBackground(new java.awt.Color(255, 255, 255));
        jPDAS.setPreferredSize(new java.awt.Dimension(230, 110));
        jPDAS.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPDAS);

        jPSASnorm.setBackground(new java.awt.Color(255, 255, 255));
        jPSASnorm.setPreferredSize(new java.awt.Dimension(230, 110));
        jPSASnorm.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPSASnorm);

        jPDASnorm.setBackground(new java.awt.Color(255, 255, 255));
        jPDASnorm.setPreferredSize(new java.awt.Dimension(230, 110));
        jPDASnorm.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPDASnorm);

        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        jLKineticParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("KinParameters"));
        jScrollPane1.setViewportView(jLKineticParameters);

        jPanel7.add(jScrollPane1);

        jLSpectralParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("SpecParameters"));
        jScrollPane2.setViewportView(jLSpectralParameters);

        jPanel7.add(jScrollPane2);

        jPConcentrations.setBackground(new java.awt.Color(255, 255, 255));
        jPConcentrations.setPreferredSize(new java.awt.Dimension(230, 110));
        jPConcentrations.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jLabel1.text")); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel5.add(jPanel9, gridBagConstraints);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPSingValues.setBackground(new java.awt.Color(255, 255, 255));
        jPSingValues.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.26;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPSingValues, gridBagConstraints);

        jPLeftSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectors.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.37;
        jPanel8.add(jPLeftSingVectors, gridBagConstraints);

        jPRightSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectors.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.37;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPRightSingVectors, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jPanel8, gridBagConstraints);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, 0, 0, Short.MAX_VALUE)
                            .addComponent(jPConcentrations, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPConcentrations, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(413, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel6);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel14.TabConstraints.tabTitle"), jPanel14); // NOI18N

        jToolBar2.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);
        jToolBar2.add(jSeparator2);

        org.openide.awt.Mnemonics.setLocalizedText(jTBLinLogTraces, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTBLinLogTraces.text")); // NOI18N
        jTBLinLogTraces.setFocusable(false);
        jTBLinLogTraces.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBLinLogTraces.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBLinLogTraces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBLinLogTracesActionPerformed(evt);
            }
        });
        jToolBar2.add(jTBLinLogTraces);

        jTFLinPartTraces.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFLinPartTraces.setText(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTFLinPartTraces.text")); // NOI18N
        jTFLinPartTraces.setMaximumSize(new java.awt.Dimension(70, 19));
        jToolBar2.add(jTFLinPartTraces);
        jToolBar2.add(jSeparator3);

        org.openide.awt.Mnemonics.setLocalizedText(jButton8, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton8.text")); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);
        jToolBar2.add(jSeparator4);

        jScrollPane6.setBorder(null);
        jScrollPane6.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel3.setPreferredSize(new java.awt.Dimension(900, 1000));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel1.border.title"))); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPSelectedWaveTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedWaveTrace.setMaximumSize(new java.awt.Dimension(450, 150));
        jPSelectedWaveTrace.setMinimumSize(new java.awt.Dimension(450, 150));
        jPSelectedWaveTrace.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPSelectedWaveTrace);

        jPSelectedTimeTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTimeTrace.setMaximumSize(new java.awt.Dimension(450, 350));
        jPSelectedTimeTrace.setMinimumSize(new java.awt.Dimension(450, 350));
        jPSelectedTimeTrace.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPSelectedTimeTrace);

        jPSpecImage.setBackground(new java.awt.Color(0, 0, 0));
        jPSpecImage.setMaximumSize(new java.awt.Dimension(450, 450));
        jPSpecImage.setMinimumSize(new java.awt.Dimension(350, 350));
        jPSpecImage.setPreferredSize(new java.awt.Dimension(350, 350));
        jPSpecImage.setLayout(new java.awt.BorderLayout());

        jSColum.setValue(0);
        jSColum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSColumStateChanged(evt);
            }
        });

        jSRow.setOrientation(javax.swing.JSlider.VERTICAL);
        jSRow.setValue(0);
        jSRow.setInverted(true);
        jSRow.setMinimumSize(new java.awt.Dimension(16, 350));
        jSRow.setPreferredSize(new java.awt.Dimension(16, 350));
        jSRow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSRowStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPSpecImage, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPSpecImage, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel10.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jLabel2.text")); // NOI18N

        jCBDispCurveShow.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCBDispCurveShow, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jCBDispCurveShow.text")); // NOI18N
        jCBDispCurveShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDispCurveShowActionPerformed(evt);
            }
        });

        jTFCentrWave.setEditable(false);
        jTFCentrWave.setText(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTFCentrWave.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jLabel3.text")); // NOI18N

        jTFCurvParam.setEditable(false);
        jTFCurvParam.setText(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jTFCurvParam.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBDispCurveShow)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCentrWave, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCurvParam, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jCBDispCurveShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCentrWave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFCurvParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jLabel4.text")); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel16.add(jLabel4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel15.add(jPanel16, gridBagConstraints);

        jPanel17.setLayout(new java.awt.GridBagLayout());

        jPSingValuesPart.setBackground(new java.awt.Color(255, 255, 255));
        jPSingValuesPart.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.26;
        gridBagConstraints.weighty = 1.0;
        jPanel17.add(jPSingValuesPart, gridBagConstraints);

        jPLeftSingVectorsPart.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectorsPart.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.37;
        jPanel17.add(jPLeftSingVectorsPart, gridBagConstraints);

        jPRightSingVectorsPart.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectorsPart.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.37;
        gridBagConstraints.weighty = 1.0;
        jPanel17.add(jPRightSingVectorsPart, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel15.add(jPanel17, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(378, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel13.TabConstraints.tabTitle"), jPanel13); // NOI18N

        jToolBar3.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton4);

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton5.text")); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(jButton5);

        jScrollPane5.setBorder(null);
        jScrollPane5.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPSelTimeTrCollection.setPreferredSize(new java.awt.Dimension(900, 800));
        jPSelTimeTrCollection.setLayout(new java.awt.GridLayout(2, 2));
        jScrollPane5.setViewportView(jPSelTimeTrCollection);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel12.TabConstraints.tabTitle"), jPanel12); // NOI18N

        jToolBar4.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton6, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton6.text")); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar4.add(jButton6);

        org.openide.awt.Mnemonics.setLocalizedText(jButton7, org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jButton7.text")); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(jButton7);

        jScrollPane8.setBorder(null);
        jScrollPane8.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPSelWavTrCollection.setPreferredSize(new java.awt.Dimension(923, 969));
        jPSelWavTrCollection.setLayout(new java.awt.GridLayout(2, 2));
        jScrollPane8.setViewportView(jPSelWavTrCollection);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jPanel11.TabConstraints.tabTitle"), jPanel11); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSRowStateChanged
        crosshair2.setValue(dataset.GetImageHeigth()-jSRow.getValue());
        int xIndex = jSRow.getValue();
        XYSeriesCollection trace = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Trace");
        XYSeries series2 = new XYSeries("Fit");
        XYSeries series3 = new XYSeries("Residuals");

        for (int j = 0; j < res.getX2().length; j++) {
            series1.add(res.getX2()[j], res.getTraces().get(xIndex, j));
            series2.add(res.getX2()[j], res.getFittedTraces().get(xIndex, j));
            series3.add(res.getX2()[j], res.getTraces().get(xIndex, j)-res.getFittedTraces().get(xIndex, j));
        }
        
        trace.addSeries(series1);
        trace.addSeries(series2);
        subchartWaveTrace.getXYPlot().setDataset(trace);
        subchartResidualsWave.getXYPlot().setDataset(new XYSeriesCollection(series3));
        
    }//GEN-LAST:event_jSRowStateChanged

    private void jSColumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSColumStateChanged
        crosshair1.setValue(jSColum.getValue());
        updateTrace(jSColum.getValue());
    }//GEN-LAST:event_jSColumStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//create dialog
        SelectTracesForPlot selTracePanel = new SelectTracesForPlot();
        selTracePanel.setMaxNumbers(res.getX().length, res.getX2().length);
        NotifyDescriptor selTracesDialog = new NotifyDescriptor(
                selTracePanel,
                NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("selTracesForReport"),
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                null,
                NotifyDescriptor.OK_OPTION);
//show dialog
        if (DialogDisplayer.getDefault().notify(selTracesDialog).equals(NotifyDescriptor.OK_OPTION)){
//create time traces collection
            if (selTracePanel.getSelectXState()){
                int numSelTraces = selTracePanel.getSelectXNum();
                int w = res.getX2().length/numSelTraces;
                int xIndex = 0;
                selectedTimeTraces.clear();
                jPSelTimeTrCollection.removeAll();
                CommonTools.restorePanelSize(jPSelTimeTrCollection);
                CommonTools.checkPanelSize(jPSelTimeTrCollection,numSelTraces);

                for (int i = 0; i < numSelTraces; i++) {
                    xIndex = i*w;
                    if (!jTBLinLogTraces.isSelected()){

                        XYSeriesCollection trace = CommonTools.createFitRawTraceCollection(xIndex, 0, res.getX().length, res);
                        XYSeriesCollection resid = CommonTools.createResidTraceCollection(xIndex, 0, res.getX().length, res);
                        ChartPanel linTime = CommonTools.makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), String.valueOf(res.getX2()[xIndex]));
                        linTime.getChart().setTitle(String.valueOf(res.getX2()[xIndex]));
                        jPSelTimeTrCollection.add(linTime);
                    }
                    else {
                        ChartPanel linLogTime = makeLinLogTimeTraceResidChart(xIndex);
                        linLogTime.getChart().setTitle(String.valueOf(res.getX2()[xIndex]));
                        jPSelTimeTrCollection.add(linLogTime);
                    }
//Add index ot selected trace into list
                    selectedTimeTraces.add(xIndex);
                }
            }

//create wave traces colection
            if (selTracePanel.getSelectYState()){
                int numSelTraces = selTracePanel.getSelectYNum();
                int w = res.getX().length/numSelTraces;
                XYSeriesCollection trace;
                XYSeries series1, series2, series3;
                int xIndex;
                NumberAxis xAxis;
                ChartPanel chpan;
                selectedWaveTraces.clear();
                jPSelWavTrCollection.removeAll();
                CommonTools.restorePanelSize(jPSelWavTrCollection);
                CommonTools.checkPanelSize(jPSelWavTrCollection,numSelTraces);

                for (int i = 0; i < numSelTraces; i++) {
//create common X axe for plot
                    xAxis = new NumberAxis("Wavelength (nm)");

                    if (res.getX2()[res.getX2().length-1]<res.getX2()[0]){
                        xAxis.setUpperBound(res.getX2()[0]);
                        xAxis.setLowerBound(res.getX2()[res.getX2().length-1]);
                    }
                    else {
                        xAxis.setLowerBound(res.getX2()[0]);
                        xAxis.setUpperBound(res.getX2()[res.getX2().length-1]);
                       }

                    trace = new XYSeriesCollection();
                    series1 = new XYSeries("Trace");
                    series2 = new XYSeries("Fit");
                    series3 = new XYSeries("Residuals");
                    xIndex = i*w;
                    selectedWaveTraces.add(xIndex);

                    for (int j = 0; j < res.getX2().length; j++) {
                        series1.add(res.getX2()[j], res.getTraces().get(xIndex, j));
                        series2.add(res.getX2()[j], res.getFittedTraces().get(xIndex, j));
                        series3.add(res.getX2()[j], res.getResiduals().get(xIndex, j));
                    }

                    trace.addSeries(series1);
                    trace.addSeries(series2);
                    chpan = CommonTools.makeLinTimeTraceResidChart(
                            trace,
                            new XYSeriesCollection(series3),
                            xAxis,
                            String.valueOf(res.getX()[xIndex]).concat(" ps"));
                    jPSelWavTrCollection.add(chpan);

                }

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        selectedTimeTraces.add(jSColum.getValue());
        
        if (!jTBLinLogTraces.isSelected()){

            XYSeriesCollection trace = CommonTools.createFitRawTraceCollection(jSColum.getValue(), 0, res.getX().length,res);
            XYSeriesCollection resid = CommonTools.createResidTraceCollection(jSColum.getValue(), 0, res.getX().length,res);
            ChartPanel linTime = CommonTools.makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), String.valueOf(res.getX2()[jSColum.getValue()]));
            jPSelTimeTrCollection.add(linTime);
        }
        else {
            ChartPanel linLogTime = makeLinLogTimeTraceResidChart(jSColum.getValue());
            linLogTime.getChart().setTitle(String.valueOf(res.getX2()[jSColum.getValue()]));
            jPSelTimeTrCollection.add(linLogTime);
        }

        CommonTools.checkPanelSize(jPSelTimeTrCollection, selectedTimeTraces.size());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        selectedWaveTraces.add(jSRow.getValue());
        NumberAxis xAxis = new NumberAxis("Wavelenth (nm)");
        if (res.getX2()[res.getX2().length-1]<res.getX2()[0]){
            xAxis.setUpperBound(res.getX2()[0]);
            xAxis.setLowerBound(res.getX2()[res.getX2().length-1]);
        }
        else {
            xAxis.setLowerBound(res.getX2()[0]);
            xAxis.setUpperBound(res.getX2()[res.getX2().length-1]);
        }
        xAxis.setAutoRangeIncludesZero(false);
        xAxis.setAutoRange(false);
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(5.0);
        try {
            plot.add((XYPlot) subchartWaveTrace.getXYPlot().clone(), 3);
            plot.add((XYPlot) subchartResidualsWave.getXYPlot().clone(), 1);
        } catch (CloneNotSupportedException ex) {
            Exceptions.printStackTrace(ex);
        }
        plot.setOrientation(PlotOrientation.VERTICAL);
        Font titleFont = new Font(JFreeChart.DEFAULT_TITLE_FONT.getFontName(),JFreeChart.DEFAULT_TITLE_FONT.getStyle(),12);
        JFreeChart tracechart = new JFreeChart(String.valueOf(res.getX()[jSRow.getValue()]), titleFont, plot, true);
        tracechart.removeLegend();
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPSelWavTrCollection.add(chpan);
        CommonTools.checkPanelSize(jPSelWavTrCollection, selectedWaveTraces.size());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jPSelWavTrCollection.removeAll();
        CommonTools.restorePanelSize(jPSelWavTrCollection);
        jPSelWavTrCollection.repaint();
        selectedWaveTraces.clear();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jPSelTimeTrCollection.removeAll();
        CommonTools.restorePanelSize(jPSelTimeTrCollection);
        jPSelTimeTrCollection.repaint();
        selectedTimeTraces.clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCBDispCurveShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDispCurveShowActionPerformed
        showDispCurve();
    }//GEN-LAST:event_jCBDispCurveShowActionPerformed

    private void jTBLinLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBLinLogActionPerformed

        if (jTBLinLog.isSelected()){
            try {
                updateLinLogPlotSumary();
            }
            catch (Exception e) {
                CoreErrorMessages.updLinLogException();
                jTBLinLog.setSelected(false);
            }
        } else {
            ChartPanel conc = createLinTimePlot(res.getConcentrations(), res.getX());
            ChartPanel lsv =createLinTimePlot(leftSingVec, res.getX());
            lsv.getChart().setTitle("Left singular vectors");
            lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));
            jPConcentrations.removeAll();
//            conc.setSize(jPConcentrations.getSize());
            jPConcentrations.add(conc);
            jPConcentrations.validate();
            jPLeftSingVectors.removeAll();
//            lsv.setSize(jPLeftSingVectors.getSize());
            jPLeftSingVectors.add(lsv);
            jPLeftSingVectors.validate();
            jBUpdLinLog.setEnabled(false);

        }
    }//GEN-LAST:event_jTBLinLogActionPerformed

    private void jBUpdLinLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdLinLogActionPerformed
    try {
            updateLinLogPlotSumary();
        }
        catch (Exception e) {
            CoreErrorMessages.updLinLogException();
        }
    }//GEN-LAST:event_jBUpdLinLogActionPerformed

    private void jTBLinLogTracesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBLinLogTracesActionPerformed
        updateTrace(jSColum.getValue());
    }//GEN-LAST:event_jTBLinLogTracesActionPerformed

    private void jTBShowChohSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBShowChohSpecActionPerformed
        plotSpectrTrace();
        jPSAS.validate();
        jPDAS.validate();
        jPDASnorm.validate();
        jPSASnorm.validate();
    }//GEN-LAST:event_jTBShowChohSpecActionPerformed

    private void jTBNormToMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBNormToMaxActionPerformed
        plotSpectrTrace();
        jPSAS.validate();
        jPDAS.validate();
        jPDASnorm.validate();
        jPSASnorm.validate();
    }//GEN-LAST:event_jTBNormToMaxActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Range yRange = chartMain.getXYPlot().getRangeAxis().getRange();
        Range xRange = chartMain.getXYPlot().getDomainAxis().getRange();
        Matrix tempResidMatrix;
        tempResidMatrix = res.getResiduals().getMatrix(res.getX().length-(int)yRange.getUpperBound(),
                                                       res.getX().length-(int)yRange.getLowerBound()-1,
                                                       (int)xRange.getLowerBound(),
                                                       (int)xRange.getUpperBound()-1);
                                    
        int newHeigh= (int)yRange.getUpperBound() - (int)yRange.getLowerBound();
        int newWidth = (int)xRange.getUpperBound() - (int)xRange.getLowerBound();
        double[] tempX = new double[newHeigh]; 
        double[] tempX2 = new double[newWidth]; 
        for (int i = 0; i < newWidth; i++){
            tempX2[i] =  res.getX2()[i+(int)xRange.getLowerBound()];
        }
        for (int i = 0; i < newHeigh; i++){
            tempX[i] =  res.getX()[i+res.getX().length-(int)yRange.getUpperBound()];
        }
        timePart = tempX;
        leftSingVecPart = calculateSVDresid(tempResidMatrix,tempX,tempX2,jPRightSingVectorsPart, jPLeftSingVectorsPart, jPSingValuesPart);
        updateTrace(jSColum.getValue());
        jPRightSingVectorsPart.validate();
        jPSingValuesPart.validate();
    }//GEN-LAST:event_jButton8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBUpdLinLog;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCBDispCurveShow;
    private javax.swing.JList jLKineticParameters;
    private javax.swing.JList jLSpectralParameters;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPConcentrations;
    private javax.swing.JPanel jPDAS;
    private javax.swing.JPanel jPDASnorm;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPLeftSingVectorsPart;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPRightSingVectorsPart;
    private javax.swing.JPanel jPSAS;
    private javax.swing.JPanel jPSASnorm;
    private javax.swing.JPanel jPSelTimeTrCollection;
    private javax.swing.JPanel jPSelWavTrCollection;
    private javax.swing.JPanel jPSelectedTimeTrace;
    private javax.swing.JPanel jPSelectedWaveTrace;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPSingValuesPart;
    private javax.swing.JPanel jPSpecImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSlider jSColum;
    private javax.swing.JSlider jSRow;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToggleButton jTBLinLog;
    private javax.swing.JToggleButton jTBLinLogTraces;
    private javax.swing.JToggleButton jTBNormToMax;
    private javax.swing.JToggleButton jTBShowChohSpec;
    private javax.swing.JTextField jTFCentrWave;
    private javax.swing.JTextField jTFCurvParam;
    private javax.swing.JTextField jTFLinPart;
    private javax.swing.JTextField jTFLinPartTraces;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized SpecResultsTopComponent getDefault() {
        if (instance == null) {
            instance = new SpecResultsTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the StreakOutTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized SpecResultsTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SpecResultsTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SpecResultsTopComponent) {
            return (SpecResultsTopComponent) win;
        }
        Logger.getLogger(SpecResultsTopComponent.class.getName()).warning(
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
        
    }

    @Override
    public void componentClosed() {
        
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

    public void chartChanged(ChartChangeEvent arg0) {
        XYPlot plot = this.chartMain.getXYPlot();
        double lowBound = plot.getDomainAxis().getRange().getLowerBound();
        double upBound = plot.getDomainAxis().getRange().getUpperBound();
        boolean recreate = false;
        int lowInd, upInd;

        if (lowBound < wholeXRange.getLowerBound()) {
            lowBound = wholeXRange.getLowerBound();
            recreate = true;
        }
        if (upBound > wholeXRange.getUpperBound()) {
            upBound = wholeXRange.getUpperBound();
            recreate = true;
        }
        if (recreate){
            plot.getDomainAxis().setRange(new Range(lowBound, upBound));
//            this.chartMain.getPlot().getDomainAxis().setRange(new Range(lowBound, upBound));
        }
        recreate = false;
        lowBound = plot.getRangeAxis().getRange().getLowerBound();
        upBound = plot.getRangeAxis().getRange().getUpperBound();
        if (lowBound < wholeYRange.getLowerBound()) {
            lowBound = wholeYRange.getLowerBound();
            recreate = true;
        }
        if (upBound > wholeYRange.getUpperBound()) {
            upBound = wholeYRange.getUpperBound();
            recreate = true;
        }
        if (recreate){
            plot.getRangeAxis().setRange(new Range(lowBound, upBound));
//            this.chartMain.getPlot().getDomainAxis().setRange(new Range(lowBound, upBound));
        }

    }

    public void chartMouseClicked(ChartMouseEvent event) {

    }

    public void chartMouseMoved(ChartMouseEvent event) {

    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return SpecResultsTopComponent.getDefault();
        }
    }

    private ChartPanel createLinLogTimePlot(double timeZero, double portion, Matrix data, double[] timesteps){

        XYSeriesCollection linCollection = new XYSeriesCollection();
        XYSeries seria;
        int index = 0;
        int traceNumber = data.getColumnDimension();
        for (int j = 0; j < traceNumber; j++) {
            seria = new XYSeries("Comp" + (j + 1));
            index = 0;
            while (timesteps[index]<timeZero+portion){
                seria.add(timesteps[index], data.get(index, j));
                index++;
            }
            linCollection.addSeries(seria);
        }

        XYSeriesCollection logCollection = new XYSeriesCollection();
        for (int j = 0; j < traceNumber; j++) {
            seria = new XYSeries("Comp" + (j + 1));
            for (int i = index; i<timesteps.length; i++){
                seria.add(timesteps[i], data.get(i, j));
            }
            logCollection.addSeries(seria);
        }

//============================combined plot
        JFreeChart linePart, logPart;
        linePart = ChartFactory.createXYLineChart(
            null,
            "Time",
            null,
            linCollection,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        logPart = ChartFactory.createXYLineChart(
            null,
            "log(Time)",
            null,
            logCollection,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );

        XYPlot linPlot = linePart.getXYPlot();
        linPlot.getDomainAxis().setLowerMargin(0.0);
        linPlot.getDomainAxis().setUpperMargin(0.0);
        linPlot.setRangeZeroBaselineVisible(true);

        XYPlot logPlot =logPart.getXYPlot();// new XYPlot(logCollection,new LogAxis(),null,linPlot.getRenderer());

        logPlot.setDomainAxis(new LogAxis("log(Time)"));
        logPlot.getDomainAxis().setLabelFont(linPlot.getDomainAxis().getLabelFont());
        logPlot.getDomainAxis().setLowerMargin(0.0);
        logPlot.getDomainAxis().setUpperMargin(0.0);
        logPlot.setRangeZeroBaselineVisible(true);

        NumberAxis yAxis = new NumberAxis();

        for (int i = 0; i < traceNumber; i++) {
            logPlot.getRenderer().setSeriesPaint(i, ((AbstractRenderer) linPlot.getRenderer()).lookupSeriesPaint(i));
        }

        CombinedRangeXYPlot plot = new CombinedRangeXYPlot(yAxis);
        plot.setGap(-7.5);
        plot.add(linPlot);
        plot.add(logPlot);

        JFreeChart tracechart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        LegendTitle legend = new LegendTitle(linPlot);
        legend.setPosition(RectangleEdge.BOTTOM);
        tracechart.removeLegend();
        legend.setVisible(false);
        tracechart.addLegend(legend);
        return new ChartPanel(tracechart,true);
    }

    private GraphPanel createLinTimePlot(Matrix data, double[] timesteps){
        int traceNumber = data.getColumnDimension();
        XYSeriesCollection concCollection = new XYSeriesCollection();
        XYSeries seria;
        for (int j = 0; j < traceNumber; j++) {
            seria = new XYSeries("Conc" + (j + 1));
            for (int i = 0; i < timesteps.length; i++) {
                seria.add(timesteps[i], data.get(i, j));
            }
            concCollection.addSeries(seria);
        }
        
        JFreeChart tracechart = ChartFactory.createXYLineChart(
                    null,
                    "Time",
                    null,
                    concCollection,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);

        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);
        tracechart.getXYPlot().setRangeZeroBaselineVisible(true);

        for (int i = 0; i < traceNumber; i++) {
            tracechart.getXYPlot().getRenderer().setSeriesPaint(i, ((AbstractRenderer) tracechart.getXYPlot().getRenderer()).lookupSeriesPaint(i));
        }

        GraphPanel chpan = new GraphPanel(tracechart, false);
        chpan.getChartRenderingInfo().setEntityCollection(null);
        return chpan;
    }

    private void plotSpectrTrace() {
        String specName = null;
        boolean errorBars = false;
        if (res.getJvec()!=null){
            specName = "SAS";
        } else {
            specName = "EAS";
        }
        if (res.getSpectraErr()!=null){
            errorBars = true;
        }

        int compNum = 0;
        double maxAmpl = 0;
        double maxDasAmpl = 0;
        if (jTBShowChohSpec.isSelected()) {
            compNum = numberOfComponents + 1;
        }
        else {
            compNum = numberOfComponents;
        }

        YIntervalSeriesCollection realSasCollection = new YIntervalSeriesCollection();
        YIntervalSeriesCollection normSasCollection = new YIntervalSeriesCollection();
        XYSeriesCollection realDasCollection = new XYSeriesCollection();
        XYSeriesCollection normDasCollection = new XYSeriesCollection();
        YIntervalSeries seria;
        XYSeries dasSeria;
//        XYSeries seria;
//create collection of real sas and normalizes all of them to max or abs(max) and creates collection with normSAS
//TODO create collection of real das and normalizes all of them to max and creates collection with normDAS
        for (int j = 0; j < compNum; j++) {
            seria =new YIntervalSeries(specName + (j + 1));// new XYSeries(specName + (j + 1));
            dasSeria = new XYSeries("DAS" + (j + 1));
            maxAmpl = 0;
            maxDasAmpl = 0;
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], res.getSpectra().get(j, i),
                        res.getSpectra().get(j, i)-res.getSpectraErr().get(j,i),
                        res.getSpectra().get(j, i)+res.getSpectraErr().get(j,i));
                if (res.getSpectra().getRowDimension()>compNum){
                    dasSeria.add(res.getX2()[i], res.getSpectra().get(j+compNum, i));
                }
                if (jTBNormToMax.isSelected()){
                    if (maxAmpl<(res.getSpectra().get(j, i))){
                        maxAmpl=(res.getSpectra().get(j, i));
                    }
                    if (res.getSpectra().getRowDimension()>compNum){
                        if (maxDasAmpl<(res.getSpectra().get(j+compNum, i))){
                            maxDasAmpl=(res.getSpectra().get(j+compNum, i));
                        }
                    }
                }
                else {
                    if (maxAmpl<abs(res.getSpectra().get(j, i))){
                        maxAmpl=abs(res.getSpectra().get(j, i));
                    }
                    if (res.getSpectra().getRowDimension()>compNum){
                        if (maxDasAmpl<abs(res.getSpectra().get(j+compNum, i))){
                            maxDasAmpl=abs(res.getSpectra().get(j+compNum, i));
                        }
                    }
                }
            }
            realSasCollection.addSeries(seria);
            realDasCollection.addSeries(dasSeria);

            seria = new YIntervalSeries("Norm"+specName + (j + 1));
            dasSeria = new XYSeries("NormDas" + (j + 1));
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], res.getSpectra().get(j, i)/maxAmpl,
                        res.getSpectra().get(j, i)/maxAmpl-res.getSpectraErr().get(j,i)/maxAmpl,
                        res.getSpectra().get(j, i)/maxAmpl+res.getSpectraErr().get(j,i)/maxAmpl);
                if (res.getSpectra().getRowDimension()>compNum){
                    dasSeria.add(res.getX2()[i], res.getSpectra().get(j+compNum, i)/maxDasAmpl);
                }
            }
            normSasCollection.addSeries(seria);
            normDasCollection.addSeries(dasSeria);
        }

        JFreeChart tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                specName,
                realSasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        GraphPanel chpan = new GraphPanel(tracechart, errorBars);
        jPSAS.removeAll();
        jPSAS.add(chpan);

        tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                "norm"+specName,
                normSasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        
        chpan = new GraphPanel(tracechart, errorBars);
        jPSASnorm.removeAll();
        jPSASnorm.add(chpan);

        tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                "DAS",
                realDasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        chpan = new GraphPanel(tracechart, false);
        jPDAS.removeAll();
        jPDAS.add(chpan);

        tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                "normDAS",
                normDasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        chpan = new GraphPanel(tracechart, false);
        jPDASnorm.removeAll();
        jPDASnorm.add(chpan);

    }

    private void MakeTracesChart() {

//make timetrace chart
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        subchartResidualsTime = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        subchartTimeTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        subchartTimeTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);
        subchartResidualsTime.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);


        XYPlot plot1_1 = subchartTimeTrace.getXYPlot();
        plot1_1.getDomainAxis().setLowerMargin(0.0);
        plot1_1.getDomainAxis().setUpperMargin(0.0);
        plot1_1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_1.getDomainAxis().setInverted(true);
        plot1_1.setRangeZeroBaselineVisible(true);

        XYPlot plot1_2 = subchartResidualsTime.getXYPlot();
        plot1_2.getDomainAxis().setLowerMargin(0.0);
        plot1_2.getDomainAxis().setUpperMargin(0.0);
        plot1_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_2.getDomainAxis().setInverted(true);
        plot1_2.setRangeZeroBaselineVisible(true);

        NumberAxis xAxis = new NumberAxis("Time");
        xAxis.setRange(res.getX()[0], res.getX()[res.getX().length - 1]);
        xAxis.setUpperBound(res.getX()[res.getX().length-1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        tracechart.getLegend().setVisible(false);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPSelectedTimeTrace.add(chpan);

//make wave chart
        subchartWaveTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        subchartResidualsWave = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        if (res.getX2()[res.getX2().length-1]<res.getX2()[0]){
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[0]);
            subchartWaveTrace.getXYPlot().getDomainAxis().setLowerBound(res.getX2()[res.getX2().length-1]);
            subchartResidualsWave.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[0]);
            subchartResidualsWave.getXYPlot().getDomainAxis().setLowerBound(res.getX2()[res.getX2().length-1]);
        }
        else {
            subchartWaveTrace.getXYPlot().getDomainAxis().setLowerBound(res.getX2()[0]);
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length-1]);
            subchartResidualsWave.getXYPlot().getDomainAxis().setLowerBound(res.getX2()[0]);
            subchartResidualsWave.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length-1]);
        }

        XYPlot plot2_1 = (XYPlot) subchartWaveTrace.getPlot();
        plot2_1.getDomainAxis().setLowerMargin(0.0);
        plot2_1.getDomainAxis().setUpperMargin(0.0);
        plot2_1.getDomainAxis().setAutoRange(false);
        plot2_1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot2_1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot2_1.setRangeZeroBaselineVisible(true);

        XYPlot plot2_2 = (XYPlot) subchartResidualsWave.getPlot();
        plot2_2.getDomainAxis().setLowerMargin(0.0);
        plot2_2.getDomainAxis().setUpperMargin(0.0);
        plot2_2.getDomainAxis().setAutoRange(false);
        plot2_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot2_2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot2_2.setRangeZeroBaselineVisible(true);

        NumberAxis xAxisWave = new NumberAxis("Wavelength (ns)");
        xAxisWave.setAutoRangeIncludesZero(false);
        xAxisWave.setAutoRange(false);
        if (res.getX2()[res.getX2().length-1]<res.getX2()[0]){
            xAxisWave.setRange(res.getX2()[res.getX2().length - 1], res.getX2()[0]);
            xAxisWave.setUpperBound(res.getX2()[0]);
            xAxisWave.setLowerBound(res.getX2()[res.getX2().length-1]);
        }
        else {
            xAxisWave.setRange(res.getX2()[0], res.getX2()[res.getX2().length - 1]);
            xAxisWave.setUpperBound(res.getX2()[res.getX2().length-1]);
            xAxisWave.setLowerBound(res.getX2()[0]);
        }

        CombinedDomainXYPlot plot2 = new CombinedDomainXYPlot(xAxisWave);
        plot2.setGap(10.0);
        plot2.add(plot2_1, 3);
        plot2.add(plot2_2, 1);
        plot2.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart specchart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot2, true);
        specchart.getLegend().setVisible(false);
        ChartPanel subchart2Panel = new ChartPanel(specchart,true);
        subchart2Panel.setMinimumDrawHeight(0);
        subchart2Panel.setMinimumDrawWidth(0);
        jPSelectedWaveTrace.add(subchart2Panel);
//        jPSelectedWaveTrace.repaint();

    }

    private JFreeChart createChart(XYDataset dataset1) {

        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, dataset1, PlotOrientation.VERTICAL, false, false,
                false);

        PaintScale ps = new RainbowPaintScale(res.getMinInt(), res.getMaxInt());
        BufferedImage image = ImageUtilities.createColorCodedImage(this.dataset, ps);

        XYDataImageAnnotation ann = new XYDataImageAnnotation(image, 0,0,
                dataset.GetImageWidth(), dataset.GetImageHeigth(), true);
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
        return chart_temp;
    }

    private void makeImageChart(){
        dataset = new ColorCodedImageDataset(res.getX2().length,res.getX().length,
            res.getTraces().getRowPackedCopy(),res.getX2(),res.getX(),false);
        PaintScale ps = new RainbowPaintScale(res.getMinInt(), res.getMaxInt());
        this.chartMain = createChart(new XYSeriesCollection());
        this.chartMain.addChangeListener(this);
        XYPlot tempPlot = (XYPlot)this.chartMain.getPlot();
        this.wholeXRange = tempPlot.getDomainAxis().getRange();
        this.wholeYRange = tempPlot.getRangeAxis().getRange();
        tempPlot.setInsets(new RectangleInsets(0, 0, 0, 0));
        chpanImage = new ChartPanel(chartMain, true);
        chpanImage.setFillZoomRectangle(true);
        chpanImage.setMouseWheelEnabled(true);
        chpanImage.setZoomFillPaint(new Color(68, 68, 78, 63));
        ImageCrosshairLabelGenerator crossLabGen1 = new ImageCrosshairLabelGenerator(res.getX2(),false);
        ImageCrosshairLabelGenerator crossLabGen2 = new ImageCrosshairLabelGenerator(res.getX(),true);

        CrosshairOverlay overlay = new CrosshairOverlay();
        crosshair1 = new Crosshair(0.0);
        crosshair1.setPaint(Color.red);
        crosshair2 = new Crosshair(0.0);
        crosshair2.setPaint(Color.GRAY);
        overlay.addDomainCrosshair(crosshair1);
        overlay.addRangeCrosshair(crosshair2);
        chpanImage.addOverlay(overlay);
        crosshair1.setLabelGenerator(crossLabGen1);
        crosshair1.setLabelVisible(true);
        crosshair1.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        crosshair1.setLabelBackgroundPaint(new Color(255, 255, 0, 100));
        crosshair2.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        crosshair2.setLabelGenerator(crossLabGen2);
        crosshair2.setLabelVisible(true);
        crosshair2.setLabelBackgroundPaint(new Color(255, 255, 0, 100));

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(res.getMinInt(), res.getMaxInt());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(ps, scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        chartMain.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        legend.setBackgroundPaint(chartMain.getBackgroundPaint());
        chartMain.addSubtitle(legend);
        jPSpecImage.add(chpanImage);
        showDispCurve();
    }

    private Matrix calculateSVDresid(Matrix rezid, double[] x, double[] x2, JPanel jPRSV, JPanel jPLSV, JPanel jPSV){
//do SVD
        ArrayList<Matrix> svdResult = null;
        TimpControllerInterface controller;
        controller = Lookup.getDefault().lookup(TimpControllerInterface.class);
        if (controller != null) {
            svdResult = controller.doSingularValueDecomposition(rezid);
        } else {
            CoreErrorMessages.noRFoundException();
            return null;
        }
        int n = 2;
//creare collection with first 2 LSV
        XYSeriesCollection lSVCollection = new XYSeriesCollection();
        XYSeries seria;
        for (int j = 0; j < n; j++) {
            seria = new XYSeries("LSV" + (j + 1));
            for (int i = 0; i < x.length; i++) {
                seria.add(x[i], svdResult.get(1).get(i, j));
            }
            lSVCollection.addSeries(seria);
        }

//creare collection with first 2 RSV
        XYSeriesCollection rSVCollection = new XYSeriesCollection();
        for (int j = 0; j < n; j++) {
            seria = new XYSeries("RSV" + (j + 1));
            for (int i = 0; i < x2.length; i++) {
                seria.add(x2[i], svdResult.get(2).get(i, j));
            }
            rSVCollection.addSeries(seria);
        }

//creare collection with singular values
        XYSeriesCollection sVCollection = new XYSeriesCollection();
        seria = new XYSeries("SV");
        for (int i = 0; i < svdResult.get(0).getRowDimension(); i++) {
            seria.add(i + 1, svdResult.get(0).get(i, 0));
        }
        sVCollection.addSeries(seria);

        createSVDPlots(jPRSV, rSVCollection, jPLSV, lSVCollection, jPSV, sVCollection);

        return svdResult.get(1).getMatrix(0, svdResult.get(1).getRowDimension()-1, 0, 1);
    }

    private void createSVDPlots(JPanel jPRSV, XYSeriesCollection colRSV, JPanel jPLSV, XYSeriesCollection colLSV, JPanel jPSV, XYSeriesCollection colSV){
//creare chart for 2 LSV
        JFreeChart tracechart = ChartFactory.createXYLineChart(
                    "Left singular vectors",
                    "Time",
                    null,
                    colLSV,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
        tracechart.getXYPlot().getDomainAxis().setUpperMargin(0.0);
        tracechart.getXYPlot().getDomainAxis().setLowerMargin(0.0);
        tracechart.getXYPlot().setRangeZeroBaselineVisible(true);
         for (int i = 0; i<2; i++){
            tracechart.getXYPlot().getRenderer().setSeriesPaint(i, ((AbstractRenderer) tracechart.getXYPlot().getRenderer()).lookupSeriesPaint(i));
        }
        ChartPanel chpan = new GraphPanel(tracechart, false);
//add chart with 2 LSV to JPannel
        jPLSV.removeAll();
        jPLSV.add(chpan);

//creare chart for 2 RSV
        tracechart = ChartFactory.createXYLineChart(
                    "Right singular vectors",
                    "Wavelength (nm)",
                    null,
                    colRSV,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getTitle().setFont(new Font(tracechart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
         for (int i = 0; i<2; i++){
            tracechart.getXYPlot().getRenderer().setSeriesPaint(i, ((AbstractRenderer) tracechart.getXYPlot().getRenderer()).lookupSeriesPaint(i));
        }
        tracechart.getXYPlot().getDomainAxis().setUpperMargin(0.0);
        tracechart.getXYPlot().getDomainAxis().setLowerMargin(0.0);
        tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
        tracechart.getXYPlot().setRangeZeroBaselineVisible(true);
        chpan = new GraphPanel(tracechart, false);
//add chart with 2 RSV to JPannel
        jPRSV.removeAll();
        jPRSV.add(chpan);

//creare chart for 2 RSV
        tracechart = ChartFactory.createXYLineChart(
                    "Screeplot",
                    "Number of factors (#)",
                    null,
                    colSV,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.getXYPlot().setRangeAxis(new LogAxis("Log(SV)"));
        int index = colSV.getSeries(0).getItemCount()-1;
        while (colSV.getSeries(0).getDataItem(index).getYValue()<=0){
            index--;
        }

        tracechart.getXYPlot().getRangeAxis().setRange(colSV.getSeries(0).getDataItem(index).getYValue(), colSV.getSeries(0).getDataItem(0).getYValue());
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

        chpan = new GraphPanel(tracechart, false);
//add chart with 2 RSV to JPannel
        jPSV.removeAll();
        jPSV.add(chpan);
    }

    private ChartPanel makeLinLogTimeTraceResidChart(int xIndex){
        double timeZero = t0Curve[xIndex];
        double portion = Double.valueOf(jTFLinPartTraces.getText());
        int index=0;
        while (res.getX()[index]<timeZero+portion){
            index++;
        }
        if (index == 0) {
            index = 1;
        }
        XYSeriesCollection trace = CommonTools.createFitRawTraceCollection(xIndex, 0, index,res);
        XYSeriesCollection resid = CommonTools.createResidTraceCollection(xIndex, 0, index,res);
        ChartPanel linTime = CommonTools.makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), null);

        trace = CommonTools.createFitRawTraceCollection(xIndex, index-1, res.getX().length,res);
        resid = CommonTools.createResidTraceCollection(xIndex, index-1, res.getX().length,res);
        ChartPanel logTime = CommonTools.makeLinTimeTraceResidChart(trace, resid, new LogAxis("log(Time)"), null);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setVisible(false);
        CombinedDomainXYPlot linPlot = (CombinedDomainXYPlot)linTime.getChart().getPlot();
        CombinedDomainXYPlot logPlot = (CombinedDomainXYPlot)logTime.getChart().getPlot();

        ((XYPlot)logPlot.getSubplots().get(0)).getRenderer().setSeriesPaint(
                1,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(1));
        ((XYPlot)linPlot.getSubplots().get(0)).getRenderer().setSeriesPaint(
                1,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(1));
        ((XYPlot)linPlot.getSubplots().get(0)).getRenderer().setSeriesPaint(
                0,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(0));
        ((XYPlot)logPlot.getSubplots().get(0)).getRenderer().setSeriesPaint(
                0,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(0));
        ((XYPlot)logPlot.getSubplots().get(1)).getRenderer().setSeriesPaint(
                0,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(2));
        ((XYPlot)linPlot.getSubplots().get(1)).getRenderer().setSeriesPaint(
                0,((AbstractRenderer)((XYPlot)logPlot.getSubplots().get(0)).getRenderer()).lookupSeriesPaint(2));

        CombinedRangeXYPlot plot = new CombinedRangeXYPlot(yAxis);
        plot.setGap(-7.5);
        plot.add(linPlot);
        plot.add(logPlot);

        Range residRange = Range.combine(
                ((XYPlot)linPlot.getSubplots().get(1)).getRangeAxis().getRange(),
                ((XYPlot)logPlot.getSubplots().get(1)).getRangeAxis().getRange());

        ((XYPlot)linPlot.getSubplots().get(0)).getRangeAxis().setRange(yAxis.getRange());
        ((XYPlot)logPlot.getSubplots().get(0)).getRangeAxis().setRange(yAxis.getRange());
        ((XYPlot)linPlot.getSubplots().get(1)).getRangeAxis().setRange(residRange);
        ((XYPlot)logPlot.getSubplots().get(1)).getRangeAxis().setRange(residRange);
        ((XYPlot)logPlot.getSubplots().get(0)).getRangeAxis().setVisible(false);
        ((XYPlot)logPlot.getSubplots().get(1)).getRangeAxis().setVisible(false);
        
        Font titleFont = new Font(JFreeChart.DEFAULT_TITLE_FONT.getFontName(),JFreeChart.DEFAULT_TITLE_FONT.getStyle(),12);
        JFreeChart tracechart = new JFreeChart(null, titleFont, plot, true);
        LegendTitle legend = new LegendTitle(linPlot);
        legend.setPosition(RectangleEdge.BOTTOM);
        tracechart.removeLegend();
        legend.setVisible(false);
        tracechart.addLegend(legend);

        return new ChartPanel(tracechart,true);
    }

    private XYDataset createDispersionCurve(){
        XYSeries curve = new XYSeries("dispersion");
        int k=0;
        for(int i = 0; i<res.getX2().length; i++){
            while (t0Curve[i] > res.getX()[k])
                k++;
            curve.add(i,res.getX().length-k);
        }
        XYDataset curveDataset = new XYSeriesCollection(curve);
        return curveDataset;
    }

    private void calculateDispersionCurve(double centrW, double[] param, double time0, int order){
        t0Curve = new double[res.getX2().length];
        double point = 0;
        int k=0;
        for(int i = 0; i<res.getX2().length; i++){
            point = 0;
            for (int j = 0; j < order; j++){
                point = point + param[j]*pow((res.getX2()[i]-centrW)/100,j+1);
            }
            k=0;
            point = point + time0;
            t0Curve[i] = point;
        }

    }

    private void updateLinLogPlotSumary(){
        double linPortion = Double.valueOf(jTFLinPart.getText());
        ChartPanel conc = createLinLogTimePlot(t0Curve[0], linPortion, res.getConcentrations(), res.getX());
        ChartPanel lsv = createLinLogTimePlot(t0Curve[0], linPortion, leftSingVec, res.getX());
        lsv.getChart().setTitle("Left singular vectors");
        lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));

        jPConcentrations.removeAll();
        conc.setSize(jPConcentrations.getSize());
        jPConcentrations.add(conc);
        jPConcentrations.repaint();

        jPLeftSingVectors.removeAll();
        lsv.setSize(jPLeftSingVectors.getSize());
        jPLeftSingVectors.add(lsv);
        jPLeftSingVectors.repaint();
        jBUpdLinLog.setEnabled(true);

    }


    private void updateTrace(int xIndex){
        if (!jTBLinLogTraces.isSelected()){
            XYSeriesCollection trace = CommonTools.createFitRawTraceCollection(xIndex, 0, res.getX().length,res);
            XYSeriesCollection resid = CommonTools.createResidTraceCollection(xIndex, 0, res.getX().length,res);
            ChartPanel linTime =CommonTools.makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), null);
            jPSelectedTimeTrace.removeAll();
            jPSelectedTimeTrace.add(linTime);
            jPSelectedTimeTrace.validate();

            if (leftSingVecPart!=null){
                ChartPanel lsv =createLinTimePlot(leftSingVecPart, timePart);
                lsv.getChart().setTitle("Left singular vectors");
                lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));
                jPLeftSingVectorsPart.removeAll();
                jPLeftSingVectorsPart.add(lsv);
                jPLeftSingVectorsPart.validate();
                }
        }
        else {
            ChartPanel linLogTime = makeLinLogTimeTraceResidChart(xIndex);
            jPSelectedTimeTrace.removeAll();
            jPSelectedTimeTrace.add(linLogTime);
            jPSelectedTimeTrace.validate();
            if (leftSingVecPart!=null){
                double linPortion = Double.valueOf(jTFLinPartTraces.getText());
                ChartPanel lsv = createLinLogTimePlot(t0Curve[0], linPortion, leftSingVecPart, timePart);
                lsv.getChart().setTitle("Left singular vectors");
                lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));
                jPLeftSingVectorsPart.removeAll();
                jPLeftSingVectorsPart.add(lsv);
                jPLeftSingVectorsPart.validate();
            }
        }     

    }
    
    private void showDispCurve(){
        if (jCBDispCurveShow.isSelected()){
            XYDataset dispCurve = createDispersionCurve();
            XYLineAndShapeRenderer rendererDisp = new  XYLineAndShapeRenderer();

            rendererDisp.setSeriesShapesVisible(0,false);
            rendererDisp.setSeriesPaint(0, Color.BLACK);

            chartMain.getXYPlot().setDataset(1, dispCurve);
            chartMain.getXYPlot().setRenderer(1,(XYItemRenderer)rendererDisp);
            chartMain.getXYPlot().setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        } else {
            chartMain.getXYPlot().setDataset(1, null);
            chartMain.getXYPlot().setRenderer(1,null);
        }
    }
}        
