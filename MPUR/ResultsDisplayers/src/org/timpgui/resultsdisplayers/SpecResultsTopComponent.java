/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.resultsdisplayers;

import Jama.SingularValueDecomposition;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.ImageCrosshairLabelGenerator;
import nl.wur.flim.jfreechartcustom.ImageUtilities;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
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
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.TimpResultDataObject;
import static java.lang.Math.pow;
/**
 * Top component which displays something.
 */
public final class SpecResultsTopComponent extends TopComponent implements ChartChangeListener {

    private static SpecResultsTopComponent instance;
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


    public SpecResultsTopComponent(TimpResultDataObject dataObj) {
        initComponents();
        setToolTipText(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
        setName(dataObj.getName());
        res = dataObj.getTimpResultDataset();
        res.calcRangeInt();
        numberOfComponents = res.getKineticParameters().length/2;
        Object[] rates = new Object[res.getKineticParameters().length];
        for (int i = 0; i < numberOfComponents; i++) {
            rates[i] = "k" + (i) + "=" + String.format(String.valueOf(res.getKineticParameters()[i]), "#.###");
            rates[i+numberOfComponents] = "er_k"+ (i) + "=" + String.format(String.valueOf(res.getKineticParameters()[i+numberOfComponents]), "#.###");
        }
        jLKineticParameters.setListData(rates);


//first tab
        PlotSpectrTrace(); //TODO calculate das from eas
        PlotConcentrations();
        calculateSVDResiduals();

//second tab (can be done in BG)
        makeImageChart();
        MakeTracesChart();
        jSColum.setMaximum(dataset.GetImageWidth()-1);
        jSColum.setMinimum(0);
        jSColum.setValue(0);
        jSRow.setMaximum(dataset.GetImageHeigth()-1);
        jSRow.setMinimum(0);
        jSRow.setValue(0);
         
    }

    private SpecResultsTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecResultsTopComponent.class, "HINT_StreakOutTopComponent"));
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
        jToolBar1 = new javax.swing.JToolBar();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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
        jScrollPane8 = new javax.swing.JScrollPane();
        jPSelWavTrCollectionTop = new javax.swing.JPanel();
        jPSelWavTrCollection = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPSelTimeTrCollectionTop = new javax.swing.JPanel();
        jPSelTimeTrCollection = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1000, 1200));

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1500, 1500));
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 1000));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel6.setPreferredSize(new java.awt.Dimension(900, 1000));

        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        jPSAS.setBackground(new java.awt.Color(255, 255, 255));
        jPSAS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSAS.setPreferredSize(new java.awt.Dimension(230, 110));
        jPSAS.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPSAS);

        jPDAS.setBackground(new java.awt.Color(255, 255, 255));
        jPDAS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPDAS.setPreferredSize(new java.awt.Dimension(230, 110));
        jPDAS.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPDAS);

        jPSASnorm.setBackground(new java.awt.Color(255, 255, 255));
        jPSASnorm.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSASnorm.setPreferredSize(new java.awt.Dimension(230, 110));
        jPSASnorm.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPSASnorm);

        jPDASnorm.setBackground(new java.awt.Color(255, 255, 255));
        jPDASnorm.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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
        jPConcentrations.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPConcentrations.setPreferredSize(new java.awt.Dimension(230, 110));
        jPConcentrations.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

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
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, 0, 0, Short.MAX_VALUE)
                    .addComponent(jPConcentrations, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPConcentrations, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel6);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jScrollPane3.TabConstraints.tabTitle"), jScrollPane3); // NOI18N

        jScrollPane4.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel3.setPreferredSize(new java.awt.Dimension(900, 1000));

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
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(476, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel3);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jScrollPane4.TabConstraints.tabTitle"), jScrollPane4); // NOI18N

        jScrollPane8.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPSelWavTrCollectionTop.setMaximumSize(new java.awt.Dimension(900, 1000));
        jPSelWavTrCollectionTop.setPreferredSize(new java.awt.Dimension(900, 1000));

        jPSelWavTrCollection.setPreferredSize(new java.awt.Dimension(923, 969));
        jPSelWavTrCollection.setLayout(new java.awt.GridLayout(2, 2));

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

        javax.swing.GroupLayout jPSelWavTrCollectionTopLayout = new javax.swing.GroupLayout(jPSelWavTrCollectionTop);
        jPSelWavTrCollectionTop.setLayout(jPSelWavTrCollectionTopLayout);
        jPSelWavTrCollectionTopLayout.setHorizontalGroup(
            jPSelWavTrCollectionTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
            .addComponent(jPSelWavTrCollection, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
        );
        jPSelWavTrCollectionTopLayout.setVerticalGroup(
            jPSelWavTrCollectionTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSelWavTrCollectionTopLayout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSelWavTrCollection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane8.setViewportView(jPSelWavTrCollectionTop);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jScrollPane8.TabConstraints.tabTitle"), jScrollPane8); // NOI18N

        jScrollPane7.setPreferredSize(new java.awt.Dimension(1000, 700));

        jPSelTimeTrCollectionTop.setMaximumSize(new java.awt.Dimension(900, 1000));
        jPSelTimeTrCollectionTop.setPreferredSize(new java.awt.Dimension(900, 1000));

        jPSelTimeTrCollection.setLayout(new java.awt.GridLayout(2, 2));

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

        javax.swing.GroupLayout jPSelTimeTrCollectionTopLayout = new javax.swing.GroupLayout(jPSelTimeTrCollectionTop);
        jPSelTimeTrCollectionTop.setLayout(jPSelTimeTrCollectionTopLayout);
        jPSelTimeTrCollectionTopLayout.setHorizontalGroup(
            jPSelTimeTrCollectionTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
            .addComponent(jPSelTimeTrCollection, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
        );
        jPSelTimeTrCollectionTopLayout.setVerticalGroup(
            jPSelTimeTrCollectionTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSelTimeTrCollectionTopLayout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSelTimeTrCollection, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE))
        );

        jScrollPane7.setViewportView(jPSelTimeTrCollectionTop);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecResultsTopComponent.class, "SpecResultsTopComponent.jScrollPane7.TabConstraints.tabTitle"), jScrollPane7); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSRowStateChanged
        // TODO add your handling code here:
        crosshair2.setValue(dataset.GetImageHeigth()-jSRow.getValue());
        int xIndex = jSRow.getValue();
        XYSeriesCollection trace = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Trace");
        XYSeries series2 = new XYSeries("Fit");
        XYSeries series3 = new XYSeries("Residuals");

        for (int j = 0; j < res.getX2().length; j++) {
            series1.add(res.getX2()[j], res.getTraces().get(xIndex, j));
            series2.add(res.getX2()[j], res.getFittedTraces().get(xIndex, j));
            series3.add(res.getX2()[j], res.getResiduals().get(xIndex, j));
        }
        
        trace.addSeries(series1);
        trace.addSeries(series2);
        subchartWaveTrace.getXYPlot().setDataset(trace);
        subchartResidualsWave.getXYPlot().setDataset(new XYSeriesCollection(series3));
        
    }//GEN-LAST:event_jSRowStateChanged

    private void jSColumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSColumStateChanged
        // TODO add your handling code here:
        crosshair1.setValue(jSColum.getValue());
        int xIndex = jSColum.getValue();// - jSColum.getMinimum();

        XYSeriesCollection trace = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Trace");
        XYSeries series2 = new XYSeries("Fit");
        XYSeries series3 = new XYSeries("Residuals");

        for (int j = 0; j < res.getX().length; j++) {
            series1.add(res.getX()[j], res.getTraces().get(j, xIndex));
            series2.add(res.getX()[j], res.getFittedTraces().get(j, xIndex));
            series3.add(res.getX()[j], res.getResiduals().get(j, xIndex));
        }

        trace.addSeries(series1);
        trace.addSeries(series2);
        subchartTimeTrace.getXYPlot().setDataset(trace);
        subchartResidualsTime.getXYPlot().setDataset(new XYSeriesCollection(series3));

    }//GEN-LAST:event_jSColumStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//create dialog
        SelectTracesForPlot selTracePanel = new SelectTracesForPlot();
        selTracePanel.setMaxNumbers(res.getX().length, res.getX2().length);
        NotifyDescriptor selTracesDialog = new NotifyDescriptor(
                selTracePanel,
                "Select traces for repot plot",
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
                XYSeriesCollection trace;
                XYSeries series1, series2, series3;
                int xIndex;
                NumberAxis xAxis;
                ChartPanel chpan;
                jPSelTimeTrCollection.setLayout(new GridLayout(numSelTraces/4+1, 4));

                for (int i = 0; i < numSelTraces; i++) {
                    xAxis = new NumberAxis("Time (ns)");
                    xAxis.setRange(res.getX()[0], res.getX()[res.getX().length - 1]);
                    xAxis.setUpperBound(res.getX()[res.getX().length-1]);
                    trace = new XYSeriesCollection();
                    series1 = new XYSeries("Trace");
                    series2 = new XYSeries("Fit");
                    series3 = new XYSeries("Residuals");
                    xIndex = i*w;
//Add index ot selected trace into list
                    selectedTimeTraces.add(xIndex);
//create XYdatasets for charts
                    for (int j = 0; j < res.getX().length; j++) {
                            series1.add(res.getX()[j], res.getTraces().get(j, xIndex));
                            series2.add(res.getX()[j], res.getFittedTraces().get(j, xIndex));
                            series3.add(res.getX()[j], res.getResiduals().get(j, xIndex));
                        }

                        trace.addSeries(series1);
                        trace.addSeries(series2);

//create ChartPanel using xydatasets from above
                        chpan = makeTimeTraceResidChart(
                                trace,
                                new XYSeriesCollection(series3),
                                xAxis,
                                String.valueOf(res.getX2()[xIndex]).concat(" nm"));
//add chartpanel
                        jPSelTimeTrCollection.add(chpan);

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
                jPSelWavTrCollection.setLayout(new GridLayout(numSelTraces/4+1, 4));

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
                    selectedTimeTraces.add(xIndex);

                    for (int j = 0; j < res.getX2().length; j++) {
                        series1.add(res.getX2()[j], res.getTraces().get(xIndex, j));
                        series2.add(res.getX2()[j], res.getFittedTraces().get(xIndex, j));
                        series3.add(res.getX2()[j], res.getResiduals().get(xIndex, j));
                    }

                    trace.addSeries(series1);
                    trace.addSeries(series2);
                    chpan = makeTimeTraceResidChart(
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
        NumberAxis xAxis = new NumberAxis("Time (ns)");
        xAxis.setRange(res.getX()[0], res.getX()[res.getX().length - 1]);
        xAxis.setUpperBound(res.getX()[res.getX().length-1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(5.0);
        try {
            plot.add((XYPlot) subchartTimeTrace.getXYPlot().clone(), 3);
            plot.add((XYPlot) subchartResidualsTime.getXYPlot().clone(), 1);
        } catch (CloneNotSupportedException ex) {
            Exceptions.printStackTrace(ex);
        }
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(plot);
        tracechart.removeLegend();
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPSelTimeTrCollection.add(chpan);
        GridLayout gl = (GridLayout)jPSelTimeTrCollection.getLayout();
        if (selectedTimeTraces.size()/4>=gl.getRows()){
            jPSelTimeTrCollection.setLayout(new GridLayout(gl.getRows()+1, 4));
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        selectedWaveTraces.add(jSRow.getValue());
        NumberAxis xAxis = new NumberAxis("Time (ns)");
        if (res.getX2()[res.getX2().length-1]<res.getX2()[0]){
            xAxis.setUpperBound(res.getX2()[0]);
            xAxis.setLowerBound(res.getX2()[res.getX2().length-1]);
        }
        else {
            xAxis.setLowerBound(res.getX2()[0]);
            xAxis.setUpperBound(res.getX2()[res.getX2().length-1]);
        }

        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(5.0);
        try {
            plot.add((XYPlot) subchartWaveTrace.getXYPlot().clone(), 3);
            plot.add((XYPlot) subchartResidualsWave.getXYPlot().clone(), 1);
        } catch (CloneNotSupportedException ex) {
            Exceptions.printStackTrace(ex);
        }
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(plot);
        tracechart.removeLegend();
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPSelWavTrCollection.add(chpan);
        GridLayout gl = (GridLayout)jPSelWavTrCollection.getLayout();
        if (selectedWaveTraces.size()/4>=gl.getRows()){
            jPSelWavTrCollection.setLayout(new GridLayout(gl.getRows()+1, 4));
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jPSelWavTrCollection.removeAll();
        jPSelWavTrCollection.setLayout(new GridLayout(2, 2));
        selectedWaveTraces.clear();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jPSelTimeTrCollection.removeAll();
        jPSelTimeTrCollection.setLayout(new GridLayout(2, 2));
        selectedTimeTraces.clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCBDispCurveShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDispCurveShowActionPerformed
        //switch off/on the dispersion curve on top of image
        //if (jCBDispCurveShow.isSelected()){
        double centrWave;
        double[] dispParam;
        double timeZero;
        dispParam = new double[]{0.046, 0.31, -0.13};
        centrWave = 550;
        timeZero = 1.2;
        //calculate curve;
        XYDataset dispCurve = calculateDispersionCurve(centrWave, dispParam, timeZero, dispParam.length);
        LineAndShapeRenderer rendererDisp = new LineAndShapeRenderer();
 //       rendererDisp.setDrawOutlines(true);
        rendererDisp.setBasePaint(Color.BLACK);
        rendererDisp.setBaseOutlinePaint(Color.BLACK);
        chartMain.getXYPlot().setDataset(1, dispCurve);
        chartMain.getXYPlot().setRenderer(1,(XYItemRenderer)rendererDisp);
        chartMain.getXYPlot().setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       // }
        jPSpecImage.repaint();

    }//GEN-LAST:event_jCBDispCurveShowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCBDispCurveShow;
    private javax.swing.JList jLKineticParameters;
    private javax.swing.JList jLSpectralParameters;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPConcentrations;
    private javax.swing.JPanel jPDAS;
    private javax.swing.JPanel jPDASnorm;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSAS;
    private javax.swing.JPanel jPSASnorm;
    private javax.swing.JPanel jPSelTimeTrCollection;
    private javax.swing.JPanel jPSelTimeTrCollectionTop;
    private javax.swing.JPanel jPSelWavTrCollection;
    private javax.swing.JPanel jPSelWavTrCollectionTop;
    private javax.swing.JPanel jPSelectedTimeTrace;
    private javax.swing.JPanel jPSelectedWaveTrace;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPSpecImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTFCentrWave;
    private javax.swing.JTextField jTFCurvParam;
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

//        if (!plot.getDomainAxis().getRange().equals(this.lastXRange)) {
//            this.lastXRange = plot.getDomainAxis().getRange();
//            XYPlot plot2 = (XYPlot) this.subchartWaveTrace.getPlot();
//            lowInd = (int)(this.lastXRange.getLowerBound());
//            upInd = (int)(this.lastXRange.getUpperBound()-1);
//            plot2.getDomainAxis().setRange(new Range(data.GetX2()[lowInd],data.GetX2()[upInd]));
//            jSColum.setMinimum(lowInd);
//            jSColum.setMaximum(upInd);
//        }
//
//         if (!plot.getRangeAxis().getRange().equals(this.lastYRange)) {
//            this.lastYRange = plot.getRangeAxis().getRange();
//            XYPlot plot1 = (XYPlot) this.subchartTimeTrace.getPlot();
//            lowInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getUpperBound());
//            upInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getLowerBound()-1);
//            plot1.getDomainAxis().setRange(new Range(data.GetX()[lowInd],data.GetX()[upInd]));
//            jSRow.setMinimum(lowInd);
//            jSRow.setMaximum(upInd);
//        }
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return SpecResultsTopComponent.getDefault();
        }
    }

    private void PlotConcentrations(){
        XYSeriesCollection concCollection = new XYSeriesCollection();
        XYSeries seria;
        for (int j = 0; j < numberOfComponents; j++) {
            seria = new XYSeries("Conc" + (j + 1));
            for (int i = 0; i < res.getX().length; i++) {
                seria.add(res.getX()[i], res.getConcentrations().get(i, j));
            }
            concCollection.addSeries(seria);
        }

        JFreeChart tracechart = ChartFactory.createXYLineChart(
                    "Concentration",
                    "Time (ns)",
                    null,
                    concCollection,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);
        ChartPanel chpan = new ChartPanel(tracechart);
        chpan.getChartRenderingInfo().setEntityCollection(null);
        jPConcentrations.removeAll();
        jPConcentrations.add(chpan);
//        jPConcentrations.repaint();

    }
    private void PlotSpectrTrace() {

        double max=0;
        XYSeriesCollection realSasCollection = new XYSeriesCollection();
        XYSeriesCollection normSasCollection = new XYSeriesCollection();
        XYSeries seria;
//create collection of real sas and normalizes all of them to max and creates collection with normSAS
        for (int j = 0; j < numberOfComponents; j++) {
            seria = new XYSeries("SAS" + (j + 1));
            max = 0;
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], res.getSpectra().get(j, i));
                if (max<res.getSpectra().get(j, i)){
                    max=res.getSpectra().get(j, i);
                }
            }
            realSasCollection.addSeries(seria);
            seria = new XYSeries("NormSAS" + (j + 1));
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], res.getSpectra().get(j, i)/max);
            }
            normSasCollection.addSeries(seria);
        }

        JFreeChart tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                "SAS",
                realSasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        ChartPanel chpan = new ChartPanel(tracechart);
//        chpan.getChartRenderingInfo().setEntityCollection(null);
        jPSAS.removeAll();
        jPSAS.add(chpan);

        tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                "normSAS",
                normSasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
        chpan = new ChartPanel(tracechart);
        jPSASnorm.removeAll();
        jPSASnorm.add(chpan);

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

        XYPlot plot1_2 = subchartResidualsTime.getXYPlot();
        plot1_2.getDomainAxis().setLowerMargin(0.0);
        plot1_2.getDomainAxis().setUpperMargin(0.0);
        plot1_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_2.getDomainAxis().setInverted(true);

        NumberAxis xAxis = new NumberAxis("Time (ns)");
        xAxis.setRange(res.getX()[0], res.getX()[res.getX().length - 1]);
        xAxis.setUpperBound(res.getX()[res.getX().length-1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart("Selected trace", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPSelectedTimeTrace.add(chpan);
//        jPSelectedTimeTrace.repaint();

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

        XYPlot plot2_2 = (XYPlot) subchartResidualsWave.getPlot();
        plot2_2.getDomainAxis().setLowerMargin(0.0);
        plot2_2.getDomainAxis().setUpperMargin(0.0);
        plot2_2.getDomainAxis().setAutoRange(false);
        plot2_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot2_2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

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
        JFreeChart specchart = new JFreeChart("Selected spectrum", JFreeChart.DEFAULT_TITLE_FONT, plot2, true);

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
        chpanImage = new ChartPanel(chartMain);
        chpanImage.setFillZoomRectangle(true);
        chpanImage.setMouseWheelEnabled(true);
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
//        jPSpecImage.repaint();
    }

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

//creare collection with first 2 RSV
        XYSeriesCollection rSVCollection = new XYSeriesCollection();
        for (int j = 0; j < n; j++) {
            seria = new XYSeries("RSV" + (j + 1));
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], result.getV().get(i, j));
            }
            rSVCollection.addSeries(seria);
        }

//creare chart for 2 RSV
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


//creare chart for 2 RSV
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
//add chart with 2 RSV to JPannel
        jPSingValues.removeAll();
        jPSingValues.add(chpan);
    }

    private ChartPanel makeTimeTraceResidChart(XYSeriesCollection trace, XYSeriesCollection residuals, NumberAxis xAxis, String name){
//        XYSeriesCollection dataset1 = new XYSeriesCollection();
        JFreeChart subchartResiduals = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            residuals,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        JFreeChart subchartTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            trace,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
 //       subchartTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);
 //       subchartResiduals.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);


        XYPlot plot1_1 = subchartTrace.getXYPlot();
        plot1_1.getDomainAxis().setLowerMargin(0.0);
        plot1_1.getDomainAxis().setUpperMargin(0.0);
        plot1_1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_1.getDomainAxis().setInverted(true);

        XYPlot plot1_2 = subchartResiduals.getXYPlot();
        plot1_2.getDomainAxis().setLowerMargin(0.0);
        plot1_2.getDomainAxis().setUpperMargin(0.0);
        plot1_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_2.getDomainAxis().setInverted(true);

       
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(5.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(name, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
           
        return chpan;

    }
    private XYDataset calculateDispersionCurve(double centrW, double[] param, double time0, int order){
        XYSeries curve = new XYSeries("dispersion");
        double point = 0;
        for(int i = 0; i<res.getX2().length; i++){
            point = 0;
            for (int j = 0; j < order; j++){
                point = point + param[j]*pow((res.getX2()[i]-centrW)/100,order-j);
            }
            point = point + time0;
            curve.add(i, point);
        }
        XYDataset curveDataset = new XYSeriesCollection(curve);
        return curveDataset;
    }
}        
