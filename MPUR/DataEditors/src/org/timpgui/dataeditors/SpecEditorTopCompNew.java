/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.dataeditors;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.SpinnerNumberModel;
import nl.vu.nat.tgmprojectsupport.TGProject;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.ImageCrosshairLabelGenerator;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import nl.wur.flim.jfreechartcustom.ImageUtilities;
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
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
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
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.structures.FormatedASCIIFileLoader;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.tgproject.nodes.TgdDataChildren;



/**
 * Top component which displays something.
 */
final public class SpecEditorTopCompNew extends CloneableTopComponent 
        implements ChartChangeListener { //implements ChartMouseListener {

    private static SpecEditorTopCompNew instance;
    /** path to the icon used by the component and its open action */
    //    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SpecEditorTopComponent";
    
    private JFreeChart chartMain;
    private JFreeChart subchartTimeTrace;
    private JFreeChart subchartWaveTrace;
    private JFreeChart leftSVChart;
    private JFreeChart rightSVChart;
    private Crosshair crosshair1;
    private Crosshair crosshair2;
    private ChartPanel chpanImage;
    //private XYSeriesCollection timeTracesCollection, waveTracesCollection;
    private DatasetTimp data;
    private ColorCodedImageDataset dataset;
    private TgdDataObject dataObject;
    private TimpDatasetDataObject dataObject2;
    private Range lastXRange;
    private Range lastYRange;
    private Range wholeXRange;
    private Range wholeYRange;
    SingularValueDecomposition svdResult;

    public SpecEditorTopCompNew() {
        data = new DatasetTimp();
        initComponents();
        setName(NbBundle.getMessage(SpecEditorTopCompNew.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

     public SpecEditorTopCompNew(TgdDataObject dataObj) {
        String filename;
        dataObject = dataObj;
        initComponents();
        setName(dataObject.getTgd().getFilename());
        filename = dataObject.getTgd().getPath();
        filename = filename.concat("/").concat(dataObject.getTgd().getFilename());
//        setName(NbBundle.getMessage(SpecEditorTopCompNew.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
        data = new DatasetTimp();
        try {
            data = FormatedASCIIFileLoader.loadASCIIFile(new File(filename));//.loadASCIIFile(new File(filename));
            MakeImageChart(MakeXYZDataset());
            updateFileInfo();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InstantiationException ex) {
            System.out.println("InstantiationException");
        }

    }

    public SpecEditorTopCompNew(TimpDatasetDataObject dataObj) {
        initComponents();
        dataObject2 = dataObj;
        data = dataObj.getDatasetTimp();
        setName(data.getDatasetName());
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));        
        MakeImageChart(MakeXYZDataset());
        updateFileInfo();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        inputDatasetName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jTBZoomX = new javax.swing.JToggleButton();
        jTBZoomY = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jBResample = new javax.swing.JButton();
        jBResample1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jBMakeDataset = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jBdoSVD = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPSpecImage = new javax.swing.JPanel();
        jSColum = new javax.swing.JSlider();
        jSRow = new javax.swing.JSlider();
        jPXTrace = new javax.swing.JPanel();
        jPYTrace = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAInfo = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTFMaxIntence = new javax.swing.JTextField();
        jTFMinIntence = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSnumSV = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jTFtotalNumSV = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();

        inputDatasetName.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.inputDatasetName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton4.text")); // NOI18N

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(inputDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(inputDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(1200, 800));

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jTBZoomX, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTBZoomX.text")); // NOI18N
        jTBZoomX.setFocusable(false);
        jTBZoomX.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBZoomX.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBZoomX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBZoomXActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBZoomX);

        org.openide.awt.Mnemonics.setLocalizedText(jTBZoomY, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTBZoomY.text")); // NOI18N
        jTBZoomY.setFocusable(false);
        jTBZoomY.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBZoomY.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBZoomY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBZoomYActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBZoomY);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jBResample, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBResample.text")); // NOI18N
        jBResample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResampleActionPerformed(evt);
            }
        });
        jToolBar1.add(jBResample);

        org.openide.awt.Mnemonics.setLocalizedText(jBResample1, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBResample1.text")); // NOI18N
        jBResample1.setFocusable(false);
        jBResample1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBResample1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBResample1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResample1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jBResample1);
        jToolBar1.add(jSeparator2);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jToolBar1.add(jBMakeDataset);
        jToolBar1.add(jSeparator3);

        org.openide.awt.Mnemonics.setLocalizedText(jBdoSVD, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBdoSVD.text")); // NOI18N
        jBdoSVD.setFocusable(false);
        jBdoSVD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBdoSVD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBdoSVD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBdoSVDActionPerformed(evt);
            }
        });
        jToolBar1.add(jBdoSVD);
        jToolBar1.add(jSeparator4);

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBSaveIvoFile.text")); // NOI18N
        jBSaveIvoFile.setEnabled(false);
        jBSaveIvoFile.setFocusable(false);
        jBSaveIvoFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBSaveIvoFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jBSaveIvoFile);

        jPSpecImage.setBackground(new java.awt.Color(0, 0, 0));
        jPSpecImage.setMaximumSize(new java.awt.Dimension(423, 356));
        jPSpecImage.setMinimumSize(new java.awt.Dimension(423, 356));
        jPSpecImage.setPreferredSize(new java.awt.Dimension(423, 356));
        jPSpecImage.setLayout(new java.awt.BorderLayout());

        jSColum.setMinimum(1);
        jSColum.setValue(1);
        jSColum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSColumStateChanged(evt);
            }
        });

        jSRow.setMinimum(1);
        jSRow.setOrientation(javax.swing.JSlider.VERTICAL);
        jSRow.setValue(1);
        jSRow.setInverted(true);
        jSRow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSRowStateChanged(evt);
            }
        });

        jPXTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPXTrace.setMaximumSize(new java.awt.Dimension(423, 178));
        jPXTrace.setMinimumSize(new java.awt.Dimension(423, 178));
        jPXTrace.setPreferredSize(new java.awt.Dimension(423, 178));
        jPXTrace.setVerifyInputWhenFocusTarget(false);
        jPXTrace.setLayout(new java.awt.BorderLayout());

        jPYTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPYTrace.setMaximumSize(new java.awt.Dimension(211, 356));
        jPYTrace.setMinimumSize(new java.awt.Dimension(211, 356));
        jPYTrace.setPreferredSize(new java.awt.Dimension(211, 356));
        jPYTrace.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jScrollPane3.border.title"))); // NOI18N

        jTAInfo.setColumns(20);
        jTAInfo.setRows(5);
        jTAInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTAInfo.border.title"))); // NOI18N
        jScrollPane3.setViewportView(jTAInfo);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jPanel10.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton1.text")); // NOI18N
        jButton1.setIconTextGap(2);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTFMaxIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMaxIntence.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTFMaxIntence.text")); // NOI18N

        jTFMinIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMinIntence.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTFMinIntence.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFMaxIntence, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(jTFMinIntence, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFMinIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTFMaxIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPXTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(jPSpecImage, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPYTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPYTrace, 0, 358, Short.MAX_VALUE)
                    .addComponent(jPSpecImage, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(jSRow, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPXTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 2772, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2064, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(800, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jToolBar2.setRollover(true);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel2.text")); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel3.text")); // NOI18N

        jSnumSV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSnumSVStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel4.text")); // NOI18N

        jTFtotalNumSV.setEditable(false);
        jTFtotalNumSV.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTFtotalNumSV.text")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSnumSV, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTFtotalNumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSnumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTFtotalNumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPSingValues.setBackground(new java.awt.Color(255, 255, 255));
        jPSingValues.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel6.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel7.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jPLeftSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jPLeftSingVectors);

        jPRightSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jPRightSingVectors);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPSingValues, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPSingValues, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 909, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(807, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed

    int startX, startY, endX, endY;
    int newWidth, newHeight;

    NotifyDescriptor.InputLine datasetNameDialog = new NotifyDescriptor.InputLine(
            "Dataset name",
            "Please specify the name for a dataset");
    Object res = DialogDisplayer.getDefault().notify(datasetNameDialog);

    if (res.equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp newdataset = new DatasetTimp(); //data;
        newdataset.setDatasetName(datasetNameDialog.getInputText());
        newdataset.setType("spec");
        startX = (int)(this.lastXRange.getLowerBound());
        endX = (int)(this.lastXRange.getUpperBound())-1;
        startY = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getUpperBound());
        endY = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getLowerBound())-1;
        newWidth = endX - startX+1;
        newHeight = endY - startY+1;

        double[] newvec = new double[newWidth];
   
        for (int i = 0; i<newWidth; i++){
            newvec[i]=data.getX2()[i+startX];
        }
        newdataset.setX2(newvec);
        newdataset.setNl(newWidth);

        newvec = new double[newHeight];
        for (int i = 0; i<newHeight; i++){
            newvec[i]=data.getX()[i+startY];
        }
        newdataset.setX(newvec);
        newdataset.setNt(newHeight);

        newvec = new double[newHeight*newWidth];

        for (int i = 0; i < newWidth; i++){
            for (int j = 0; j < newHeight; j++){
                newvec[(i)*newHeight+j] = data.getPsisim()[(startX+i)*data.getNt()[0]+startY+j];
            }
        }
        newdataset.setPsisim(newvec);
        newdataset.calcRangeInt();

        FileObject cachefolder = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getCacheFolder(true);
            cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
            FileObject writeTo;
            try {
                writeTo = cachefolder.createData(newdataset.getDatasetName(), "timpdataset");
                ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                stream.writeObject(newdataset);
                stream.close();
                TimpDatasetDataObject dObj = (TimpDatasetDataObject) DataObject.find(writeTo);
                TgdDataChildren chidrens = (TgdDataChildren) dataObject.getNodeDelegate().getChildren();
                chidrens.addObj(dObj);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please select main project"));
            DialogDisplayer.getDefault().notify(errorMessage);
        }
    }

}//GEN-LAST:event_jBMakeDatasetActionPerformed

private void jBResampleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBResampleActionPerformed

//    AverageSpecDatasetDialog resDiag = new ResampleSpecDatasetDialog(, true);
    AverageSpecDataset averagePanel = new AverageSpecDataset();
    averagePanel.setInitialNumbers(data.getNl()[0], data.getNt()[0]);
    NotifyDescriptor resampleDatasetDialod = new NotifyDescriptor(
            averagePanel,
            "Resample dataset",
            NotifyDescriptor.OK_CANCEL_OPTION,
            NotifyDescriptor.PLAIN_MESSAGE,
            null,
            NotifyDescriptor.CANCEL_OPTION);
    DatasetTimp findataset = new DatasetTimp();
    findataset.setPsisim(data.getPsisim());
    findataset.setX(data.getX());
    findataset.setX2(data.getX2());
    findataset.setType("spec");
    findataset.setNl(data.getNl()[0]);
    findataset.setNt(data.getNt()[0]);

    if (DialogDisplayer.getDefault().notify(resampleDatasetDialod).equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp newdataset = new DatasetTimp(); //data;
        if (averagePanel.getAverageXState()){
            //resample x dimention
            int num = averagePanel.getAverageXNum();
            double sum;
            int imwidth = findataset.getNl()[0];
            int imheight =findataset.getNt()[0];
            int w = findataset.getNl()[0]/num;

            double[] temp = new double[num*imheight];
            int count=0;

            for (int i=0; i<imheight; i++){
                for (int j=0; j<num-1; j++){
                    sum=0;
                    for (int k=0; k<w; k++)
                        sum+=findataset.getPsisim()[i+(j*w+k)*imheight];
                    temp[i+j*imheight]=sum/w;
                }

                sum=0;
                count=0;
                for (int k=i+((num-1)*w)*imheight; k<i+(imwidth-1)*imheight; k=k+imheight){
                    sum+=findataset.getPsisim()[k];
                    count++;
                }
                temp[i+(num-1)*imheight]=sum/count;
            }

            newdataset.setPsisim(temp);

            temp = new double[num];

            for (int j=0; j<num-1; j++){
                sum=0;
                for (int k=j*w; k<(j+1)*w; k++){
                    sum+=findataset.getX2()[k];
                    }
                    temp[j]=sum/w;
                }
            sum=0;
            count=0;
            for (int k=(num-1)*w; k<imwidth; k++){
                sum+=findataset.getX2()[k];
                count++;
            }
            temp[num-1]=sum/count;    
            newdataset.setX2(temp);
            newdataset.setNl(num);
            newdataset.setNt(findataset.getNt()[0]);
            newdataset.setX(findataset.getX().clone());
            newdataset.calcRangeInt();
            newdataset.setType("spec");
            findataset = newdataset;
        }

        if (averagePanel.getAverageYState()){
            //resample y dimention
            newdataset = new DatasetTimp(); //data;
            int num = averagePanel.getAverageYNum();
            double sum;
            int imwidth = findataset.getNl()[0];
            int imheight =findataset.getNt()[0];
            int w = findataset.getNt()[0]/num;

            double[] temp = new double[num*imwidth];
            int count=0;

            for (int i = 0; i < imwidth; i++){
                for (int j = 0; j < num-1; j++){
                     sum=0;
                     for (int k=0; k<w; k++)
                         sum+=findataset.getPsisim()[i*imheight+j*w+k];
                     temp[i*num+j]=sum/w;
                }

                sum=0;
                count=0;
                for (int k = i*imheight+(num-1)*w; k<(i+1)*imheight; k++){
                    sum+=findataset.getPsisim()[k];
                    count++;
                }
                temp[(i+1)*num-1]=sum/count;
            }

            newdataset.setPsisim(temp);

            temp = new double[num];

            for (int j=0; j<num-1; j++){
                sum=0;
                for (int k=j*w; k<(j+1)*w; k++){
                    sum+=findataset.getX()[k];
                    }
                    temp[j]=sum/w;
                }
            sum=0;
            count=0;
            for (int k=(num-1)*w; k<imheight; k++){
                sum+=findataset.getX()[k];
                count++;
            }
            temp[num-1]=sum/count;

            newdataset.setX(temp);
            newdataset.setNt(num);
            newdataset.setNl(findataset.getNl()[0]);
            newdataset.setX2(findataset.getX2());
            newdataset.calcRangeInt();
            newdataset.setType("spec");
            findataset = newdataset;
         
        }

        if (averagePanel.getNewDatasetState()){
            NotifyDescriptor.InputLine datasetNameDialod = new NotifyDescriptor.InputLine(
                    "Dataset name",
                    "Please specify the name for a dataset");
            if (DialogDisplayer.getDefault().notify(datasetNameDialod).equals(NotifyDescriptor.OK_OPTION)){
                findataset.setDatasetName(datasetNameDialod.getInputText());
                FileObject cachefolder = null;
                final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
                if (proj!=null){
                    cachefolder = proj.getCacheFolder(true);
                } else {
                    NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                        new Exception("Please select main project"));
                    DialogDisplayer.getDefault().notify(errorMessage);
                }
                if ((dataObject==null)&&(dataObject2!=null)){
                    cachefolder = dataObject2.getFolder().getPrimaryFile();
                }
                else
                {
                    cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
                }
                
                FileObject writeTo;
                try {
                    writeTo = cachefolder.createData(findataset.getDatasetName(), "timpdataset");
                    ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                    stream.writeObject(findataset);
                    stream.close();
                    TgdDataChildren chidrens;
                    TimpDatasetDataObject dObj = (TimpDatasetDataObject) DataObject.find(writeTo);
                    if ((dataObject==null)&&(dataObject2!=null)){
                        chidrens = (TgdDataChildren) dataObject2.getNodeDelegate().getParentNode().getChildren();
                    }
                    else
                    {
                        chidrens = (TgdDataChildren) dataObject.getNodeDelegate().getChildren();
                    }
                    chidrens.addObj(dObj);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        else {
            data=findataset;
            MakeImageChart(MakeXYZDataset());
            updateFileInfo();
        }
        this.repaint();

   }
}//GEN-LAST:event_jBResampleActionPerformed

private void jSRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSRowStateChanged
    crosshair2.setValue(dataset.GetImageHeigth()-jSRow.getValue());
    int xIndex = jSRow.getValue();
    XYDataset d = ImageUtilities.extractRowFromImageDataset(dataset, xIndex, "Spec");
    subchartWaveTrace.getXYPlot().setDataset(d);
}//GEN-LAST:event_jSRowStateChanged

private void jSColumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSColumStateChanged
    crosshair1.setValue(jSColum.getValue());
    int xIndex = jSColum.getValue();
    XYDataset d = ImageUtilities.extractColumnFromImageDataset(dataset, xIndex, "Spec");
    subchartTimeTrace.getXYPlot().setDataset(d);
}//GEN-LAST:event_jSColumStateChanged

private void jTBZoomYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBZoomYActionPerformed
    jTBZoomX.setSelected(false);
    if (jTBZoomY.isSelected()){
        chpanImage.setDomainZoomable(false);
        chpanImage.setRangeZoomable(true);
    } else {
        chpanImage.setDomainZoomable(true);
        chpanImage.setRangeZoomable(true);
    }
}//GEN-LAST:event_jTBZoomYActionPerformed

private void jTBZoomXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBZoomXActionPerformed
    jTBZoomY.setSelected(false);
    if (jTBZoomX.isSelected()){
        chpanImage.setDomainZoomable(true);
        chpanImage.setRangeZoomable(false);
    } else {
        chpanImage.setDomainZoomable(true);
        chpanImage.setRangeZoomable(true);
    }
}//GEN-LAST:event_jTBZoomXActionPerformed

private void jBdoSVDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBdoSVDActionPerformed

    Matrix pSIsim = new Matrix(data.getPsisim(), data.getNt()[0]);
    svdResult =  pSIsim.svd();
    jTFtotalNumSV.setText(String.valueOf(svdResult.getSingularValues().length));
    jSnumSV.setModel(new SpinnerNumberModel(1.0,0.0,svdResult.getSingularValues().length,1.0));
    int n = 1;
    //creare collection with first 2 LSV
    XYSeriesCollection lSVCollection = new XYSeriesCollection();
    XYSeries seria;
    seria = new XYSeries("LSV1");
    for (int i = 0; i < data.getX().length; i++) {
        seria.add(data.getX()[i], svdResult.getU().get(i, 0));
    }
    lSVCollection.addSeries(seria);

//creare chart for 2 LSV
    leftSVChart = ChartFactory.createXYLineChart(
                "Left singular vectors",
                "Time (ns)",
                null,
                lSVCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
    leftSVChart.getTitle().setFont(new Font(leftSVChart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
    leftSVChart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
    leftSVChart.getXYPlot().getDomainAxis().setUpperBound(data.getX()[data.getX().length - 1]);
    leftSVChart.getXYPlot().getDomainAxis().setAutoRange(false);
    ChartPanel chpan = new ChartPanel(leftSVChart);
//add chart with 2 LSV to JPannel
    jPLeftSingVectors.removeAll();
    jPLeftSingVectors.add(chpan);

//creare collection with first 2 RSV
    XYSeriesCollection rSVCollection = new XYSeriesCollection();
    for (int j = 0; j < n; j++) {
        seria = new XYSeries("RSV" + (j + 1));
        for (int i = 0; i < data.getX2().length; i++) {
            seria.add(data.getX2()[i], svdResult.getV().get(i, j));
        }
        rSVCollection.addSeries(seria);
    }

//creare chart for 2 RSV
    rightSVChart = ChartFactory.createXYLineChart(
                "Right singular vectors",
                "Wavelength (nm)",
                null,
                rSVCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
    rightSVChart.getTitle().setFont(new Font(rightSVChart.getTitle().getFont().getFontName(), Font.PLAIN, 12));
    rightSVChart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
    rightSVChart.getXYPlot().getDomainAxis().setUpperBound(data.getX2()[data.getX2().length - 1]);
    rightSVChart.getXYPlot().getDomainAxis().setAutoRange(false);
    chpan = new ChartPanel(rightSVChart);
//add chart with 2 RSV to JPannel
    jPRightSingVectors.removeAll();
    jPRightSingVectors.add(chpan);

//creare collection with singular values
    XYSeriesCollection sVCollection = new XYSeriesCollection();
    seria = new XYSeries("SV");
    for (int i = 0; i < svdResult.getSingularValues().length; i++) {
        seria.add(i+1, svdResult.getSingularValues()[i]);
    }
    sVCollection.addSeries(seria);


//creare chart for 2 RSV
    JFreeChart tracechart = ChartFactory.createXYLineChart(
                "Screeplot",
                "Number of factors (#)",
                null,
                sVCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
    LogAxis logAxe = new LogAxis("Log(SV)");
    logAxe.setAutoRange(true);
  //  logAxe.setLowerBound(svdResult.getSingularValues()[svdResult.getSingularValues().length-2]);
    tracechart.getXYPlot().setRangeAxis(logAxe);
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

}//GEN-LAST:event_jBdoSVDActionPerformed

private void jSnumSVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSnumSVStateChanged

    double n = (Double)jSnumSV.getModel().getValue();

    //creare collection with first 2 LSV
    XYSeriesCollection lSVCollection = new XYSeriesCollection();
    XYSeries seria;
    for (int j =0; j < n; j++){
        seria = new XYSeries("LSV1"+j+1);
        for (int i = 0; i < data.getX().length; i++) {
            seria.add(data.getX()[i], svdResult.getU().get(i, j));
        }
        lSVCollection.addSeries(seria);
    }
    leftSVChart.getXYPlot().setDataset(lSVCollection);

    XYSeriesCollection rSVCollection = new XYSeriesCollection();
    for (int j = 0; j < n; j++) {
        seria = new XYSeries("RSV" + (j + 1));
        for (int i = 0; i < data.getX2().length; i++) {
            seria.add(data.getX2()[i], svdResult.getV().get(i, j));
        }
        rSVCollection.addSeries(seria);
    }

    rightSVChart.getXYPlot().setDataset(rSVCollection);

}//GEN-LAST:event_jSnumSVStateChanged

private void jBResample1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBResample1ActionPerformed
   
  
    ResampleSpecDataset resamplePanel = new ResampleSpecDataset();
    resamplePanel.setInitialNumbers(data.getNl()[0], data.getNt()[0]);
    NotifyDescriptor resampleDatasetDialod = new NotifyDescriptor(
            resamplePanel,
            "Resample dataset",
            NotifyDescriptor.OK_CANCEL_OPTION,
            NotifyDescriptor.PLAIN_MESSAGE,
            null,
            NotifyDescriptor.CANCEL_OPTION);
    DatasetTimp findataset = new DatasetTimp();
    findataset.setPsisim(data.getPsisim());
    findataset.setX(data.getX());
    findataset.setX2(data.getX2());
    findataset.setType("spec");
    findataset.setNl(data.getNl()[0]);
    findataset.setNt(data.getNt()[0]);

    if (DialogDisplayer.getDefault().notify(resampleDatasetDialod).equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp newdataset = new DatasetTimp(); //data;
        if (resamplePanel.getResampleXState()){
            //resample x dimention
            int num = resamplePanel.getResampleXNum();
            int imwidth = findataset.getNl()[0];
            int imheight =findataset.getNt()[0];
            int w = findataset.getNl()[0]/num;

            double[] temp = new double[num*imheight];

            for (int i=0; i<imheight; i++){
                for (int j=0; j<num; j++)
                    temp[i+j*imheight]=findataset.getPsisim()[i+(j*w)*imheight];
            }

            newdataset.setPsisim(temp);
            temp = new double[num];

            for (int j=0; j<num; j++)    
                temp[j]=findataset.getX2()[j*w];

            newdataset.setX2(temp);
            newdataset.setNl(num);
            newdataset.setNt(findataset.getNt()[0]);
            newdataset.setX(findataset.getX().clone());
            newdataset.calcRangeInt();
            newdataset.setType("spec");
            findataset = newdataset;
        }

        if (resamplePanel.getResampleYState()){
            //resample y dimention
            newdataset = new DatasetTimp(); //data;
            int num = resamplePanel.getResampleYNum();
            int imwidth = findataset.getNl()[0];
            int imheight =findataset.getNt()[0];
            int w = findataset.getNt()[0]/num;

            double[] temp = new double[num*imwidth];
            for (int i = 0; i < imwidth; i++){
                for (int j = 0; j < num; j++)
                    temp[i*num+j]=findataset.getPsisim()[i*imheight+j*w];
            }

            newdataset.setPsisim(temp);
            temp = new double[num];

            for (int j=0; j<num; j++)
                temp[j]=findataset.getX()[j*w];

            newdataset.setX(temp);
            newdataset.setNt(num);
            newdataset.setNl(findataset.getNl()[0]);
            newdataset.setX2(findataset.getX2());
            newdataset.calcRangeInt();
            newdataset.setType("spec");
            findataset = newdataset;

        }

        if (resamplePanel.getNewDatasetState()){
            NotifyDescriptor.InputLine datasetNameDialod = new NotifyDescriptor.InputLine(
                    "Dataset name",
                    "Please specify the name for a dataset");
            if (DialogDisplayer.getDefault().notify(datasetNameDialod).equals(NotifyDescriptor.OK_OPTION)){
                findataset.setDatasetName(datasetNameDialod.getInputText());
                FileObject cachefolder = null;
                final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
                if (proj!=null){
                    cachefolder = proj.getCacheFolder(true);
                } else {
                    NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                        new Exception("Please select main project"));
                    DialogDisplayer.getDefault().notify(errorMessage);
                }
                if ((dataObject==null)&&(dataObject2!=null)){
                    cachefolder = dataObject2.getFolder().getPrimaryFile();
                }
                else
                {
                    cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
                }

                FileObject writeTo;
                try {
                    writeTo = cachefolder.createData(findataset.getDatasetName(), "timpdataset");
                    ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                    stream.writeObject(findataset);
                    stream.close();
                    TgdDataChildren chidrens;
                    TimpDatasetDataObject dObj = (TimpDatasetDataObject) DataObject.find(writeTo);
                    if ((dataObject==null)&&(dataObject2!=null)){
                        chidrens = (TgdDataChildren) dataObject2.getNodeDelegate().getParentNode().getChildren();
                    }
                    else
                    {
                        chidrens = (TgdDataChildren) dataObject.getNodeDelegate().getChildren();
                    }
                    chidrens.addObj(dObj);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        else {
            data=findataset;
            data.calcRangeInt();
            MakeImageChart(MakeXYZDataset());
            updateFileInfo();
        }
        this.repaint();
   }
}//GEN-LAST:event_jBResample1ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    double newMinAmpl, newMaxAmpl;
    try {
        newMinAmpl = Double.parseDouble(jTFMinIntence.getText());
        newMaxAmpl = Double.parseDouble(jTFMaxIntence.getText());
        PaintScale ps = new RainbowPaintScale(newMinAmpl, newMaxAmpl);
        
        BufferedImage image = ImageUtilities.createColorCodedImage(this.dataset, ps);
        XYDataImageAnnotation ann = new XYDataImageAnnotation(image, 0,0, 
                dataset.GetImageWidth(), dataset.GetImageHeigth(), true);
        
        XYPlot plot = (XYPlot) chartMain.getPlot();
        plot.getRenderer().removeAnnotations();
        plot.getRenderer().addAnnotation(ann, Layer.BACKGROUND);

//        JFreeChart intIm = createScatChart(ImageUtilities.createColorCodedImage(intensutyImageDataset, ps), ps,res.getOrwidth(),res.getOrheigh());
//        ChartPanel intImPanel = new ChartPanel(intIm);
//        intImPanel.setFillZoomRectangle(true);
//        intImPanel.setMouseWheelEnabled(true);
//        jPIntenceImage.removeAll();
//        intImPanel.setSize(jPIntenceImage.getSize());
//        jPIntenceImage.add(intImPanel);
//        jPIntenceImage.repaint();

    }catch(NumberFormatException ex) {
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception("Please specify correct number of channels"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputDatasetName;
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBResample;
    private javax.swing.JButton jBResample1;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jBdoSVD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPSpecImage;
    private javax.swing.JPanel jPXTrace;
    private javax.swing.JPanel jPYTrace;
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
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JSpinner jSnumSV;
    private javax.swing.JTextArea jTAInfo;
    private javax.swing.JToggleButton jTBZoomX;
    private javax.swing.JToggleButton jTBZoomY;
    private javax.swing.JTextField jTFMaxIntence;
    private javax.swing.JTextField jTFMinIntence;
    private javax.swing.JTextField jTFtotalNumSV;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized SpecEditorTopCompNew getDefault() {
        if (instance == null) {
            instance = new SpecEditorTopCompNew();
        }
        return instance;
    }

    /**
     * Obtain the StreakLoaderTopComponent instance. Never call {@link #getDefault} directly!
     */

    public static synchronized SpecEditorTopCompNew findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SpecEditorTopCompNew.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SpecEditorTopCompNew) {
            return (SpecEditorTopCompNew) win;
        }
        Logger.getLogger(SpecEditorTopCompNew.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return CloneableTopComponent.PERSISTENCE_NEVER;
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
            return SpecEditorTopCompNew.getDefault();
        }
    }
    
    
    private ColorCodedImageDataset MakeXYZDataset(){
        dataset = new ColorCodedImageDataset(data.getNl()[0],data.getNt()[0],
                data.getPsisim(), data.getX2(), data.getX(), true);
        return dataset;
    }

   public void chartChanged(ChartChangeEvent event) {
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

        if (!plot.getDomainAxis().getRange().equals(this.lastXRange)) {
            this.lastXRange = plot.getDomainAxis().getRange();
            XYPlot plot2 = (XYPlot) this.subchartWaveTrace.getPlot();
            lowInd = (int)(this.lastXRange.getLowerBound());
            upInd = (int)(this.lastXRange.getUpperBound()-1);
            plot2.getDomainAxis().setRange(new Range(data.getX2()[lowInd],data.getX2()[upInd]));
            jSColum.setMinimum(lowInd);
            jSColum.setMaximum(upInd);
        }

         if (!plot.getRangeAxis().getRange().equals(this.lastYRange)) {
            this.lastYRange = plot.getRangeAxis().getRange();
            XYPlot plot1 = (XYPlot) this.subchartTimeTrace.getPlot();
            lowInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getUpperBound());
            upInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getLowerBound()-1);
            plot1.getDomainAxis().setRange(new Range(data.getX()[lowInd],data.getX()[upInd]));
            jSRow.setMinimum(lowInd);
            jSRow.setMaximum(upInd);
        }
    }

    private JFreeChart createChart(XYDataset dataset1) {
        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, dataset1, PlotOrientation.VERTICAL, false, false,
                false);

        PaintScale ps = new RainbowPaintScale(data.getMinInt(), data.getMaxInt());
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
    
    private void MakeImageChart(ColorCodedImageDataset dataset){
        PaintScale ps = new RainbowPaintScale(data.getMinInt(), data.getMaxInt());
        this.chartMain = createChart(new XYSeriesCollection());
        this.chartMain.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        this.chartMain.addChangeListener(this);
        XYPlot tempPlot = (XYPlot)this.chartMain.getPlot();
        this.wholeXRange = tempPlot.getDomainAxis().getRange();
        this.wholeYRange = tempPlot.getRangeAxis().getRange();
        chpanImage = new ChartPanel(chartMain);
        chpanImage.setFillZoomRectangle(true);
        chpanImage.setMouseWheelEnabled(true);
        jPSpecImage.removeAll();
//        chpanImage.setSize(jPSpecImage.getMaximumSize());
        jPSpecImage.setLayout(new BorderLayout());

        ImageCrosshairLabelGenerator crossLabGen1 = new ImageCrosshairLabelGenerator(data.getX2(),false);
        ImageCrosshairLabelGenerator crossLabGen2 = new ImageCrosshairLabelGenerator(data.getX(),true);

        CrosshairOverlay overlay = new CrosshairOverlay();
        crosshair1 = new Crosshair(0.0);
        crosshair1.setPaint(Color.red);
        crosshair2 = new Crosshair(data.getNt()[0]);
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

        jPSpecImage.add(chpanImage);

        XYSeriesCollection dataset1 = new XYSeriesCollection();
        subchartTimeTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.HORIZONTAL,
            false,
            false,
            false
        );
        subchartTimeTrace.getXYPlot().getDomainAxis().setUpperBound(data.getX()[data.getX().length-1]);
        subchartTimeTrace.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
////        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(subchartTimeTrace,true);
//        chpan.setSize(jPYTrace.getMaximumSize());
        jPYTrace.removeAll();
        jPYTrace.setLayout(new BorderLayout());
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPYTrace.add(chpan);

        XYPlot plot1 = (XYPlot) subchartTimeTrace.getPlot();
        plot1.getDomainAxis().setLowerMargin(0.0);
        plot1.getDomainAxis().setUpperMargin(0.0);
        plot1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1.getDomainAxis().setInverted(true);

        XYSeriesCollection dataset2 = new XYSeriesCollection();
        subchartWaveTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset2,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        if (data.getX2()[data.getX2().length-1]<data.getX2()[0])
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(data.getX2()[0]);
        else
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(data.getX2()[data.getX2().length-1]);

        subchartWaveTrace.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        XYPlot plot2 = (XYPlot) subchartWaveTrace.getPlot();
        plot2.getDomainAxis().setLowerMargin(0.0);
        plot2.getDomainAxis().setUpperMargin(0.0);
        plot2.getDomainAxis().setAutoRange(true);
  //      plot2.getDomainAxis().resizeRange(100);
        plot2.getRenderer().setSeriesPaint(0, Color.blue);
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);

        ChartPanel subchart2Panel = new ChartPanel(subchartWaveTrace,true);
//        subchart2Panel.setSize(jPXTrace.getMaximumSize());
        jPXTrace.removeAll();
        jPXTrace.setLayout(new BorderLayout());
        subchart2Panel.setMinimumDrawHeight(0);
        subchart2Panel.setMinimumDrawWidth(0);
        jPXTrace.add(subchart2Panel);

        jSColum.setValueIsAdjusting(true);
        jSColum.setMaximum(dataset.GetImageWidth()-1);
        jSColum.setMinimum(0);
        jSColum.setValue(0);
        jSColum.setValueIsAdjusting(false);

        jSRow.setValueIsAdjusting(true);
        jSRow.setMaximum(dataset.GetImageHeigth()-1);
        jSRow.setMinimum(0);
        jSRow.setValue(0);
        jSRow.setValueIsAdjusting(false);

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(data.getMinInt(),data.getMaxInt());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(ps,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
    //        legend.setAxisOffset(5.0);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
    //        legend.setFrame(new BlockBorder(Color.red));
    //        legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chartMain.getBackgroundPaint());
        chartMain.addSubtitle(legend);
        
    }

    protected void updateFileInfo(){
        String tempString;
        jTAInfo.removeAll();
        tempString = "File name: "+data.getDatasetName()+"\n";
        jTAInfo.append(tempString);
        tempString = "Time window: "+String.valueOf(data.getX()[data.getNt()[0]-1]-data.getX()[0])+"\n";
        jTAInfo.append(tempString);
        tempString = "Nuber of time steps: "+String.valueOf(data.getNt()[0])+"\n";
        jTAInfo.append(tempString);
        tempString = "Time step: "+String.valueOf(data.getX()[1]-data.getX()[0])+"\n";
        jTAInfo.append(tempString);

        tempString = "Wave window: "+String.valueOf(data.getX2()[data.getNl()[0]-1]-data.getX2()[0])+"\n";
        jTAInfo.append(tempString);
        tempString = "Nuber of wave steps: "+String.valueOf(data.getNl()[0])+"\n";
        jTAInfo.append(tempString);
        tempString = "Wave step: "+String.valueOf(data.getX2()[1]-data.getX2()[0])+"\n";
        jTAInfo.append(tempString);

        jTFMaxIntence.setText(String.valueOf(data.getMaxInt()));
        jTFMinIntence.setText(String.valueOf(data.getMinInt()));

    }
}
