/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.dataeditors;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.awt.BasicStroke;
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
import org.jfree.data.xy.DefaultXYZDataset;
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
            data.LoadASCIIFile(new File(filename));
            MakeImageChart(MakeXYZDataset());
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
        setName(data.GetDatasetName());
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));        
        MakeImageChart(MakeXYZDataset());
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialog1 = new javax.swing.JDialog();
        inputDatasetName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jBMakeDataset = new javax.swing.JButton();
        jBResample = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jTBZoomX = new javax.swing.JToggleButton();
        jTBZoomY = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jBdoSVD = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPSpecImage = new javax.swing.JPanel();
        jSColum = new javax.swing.JSlider();
        jSRow = new javax.swing.JSlider();
        jPXTrace = new javax.swing.JPanel();
        jPYTrace = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

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
        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jToolBar1.add(jBMakeDataset);

        org.openide.awt.Mnemonics.setLocalizedText(jBResample, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBResample.text")); // NOI18N
        jBResample.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBResampleActionPerformed(evt);
            }
        });
        jToolBar1.add(jBResample);
        jToolBar1.add(jSeparator1);

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
        jToolBar1.add(jSeparator2);

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
        jToolBar1.add(jSeparator3);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPSpecImage, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                    .addComponent(jPXTrace, 0, 423, Short.MAX_VALUE)
                    .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPYTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPYTrace, javax.swing.GroupLayout.Alignment.LEADING, 0, 356, Short.MAX_VALUE)
                    .addComponent(jPSpecImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPXTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel2.text")); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel2, java.awt.BorderLayout.CENTER);

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
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPSingValues, gridBagConstraints);

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPanel4, gridBagConstraints);

        jPanel6.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPanel6, gridBagConstraints);

        jPanel7.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel7.setLayout(new java.awt.GridLayout());

        jPLeftSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jPLeftSingVectors);

        jPRightSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jPRightSingVectors);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jPanel8, gridBagConstraints);

        jToolBar2.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel4.text")); // NOI18N

        jTextField1.setEditable(false);
        jTextField1.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTextField1.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(29, 29, 29)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
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
            newvec[i]=data.GetX2()[i+startX];
        }
        newdataset.SetX2(newvec);
        newdataset.SetNl(newWidth);

        newvec = new double[newHeight];
        for (int i = 0; i<newHeight; i++){
            newvec[i]=data.GetX()[i+startY];
        }
        newdataset.SetX(newvec);
        newdataset.SetNt(newHeight);

        newvec = new double[newHeight*newWidth];

        for (int i = 0; i < newWidth; i++){
            for (int j = 0; j < newHeight; j++){
                newvec[(i)*newHeight+j] = data.GetPsisim()[(startX+i)*data.GetNt()[0]+startY+j];
            }
        }
        newdataset.SetPsisim(newvec);
        newdataset.CalcRangeInt();

        FileObject cachefolder = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getCacheFolder(true);
            cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
            FileObject writeTo;
            try {
                writeTo = cachefolder.createData(newdataset.GetDatasetName(), "timpdataset");
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
// TODO resampling
//    ResampleSpecDatasetDialog resDiag = new ResampleSpecDatasetDialog(, true);
    ResampleSpecDataset resamplePanel = new ResampleSpecDataset();
    resamplePanel.setInitialNumbers(data.GetNl()[0], data.GetNt()[0]);
    NotifyDescriptor resampleDatasetDialod = new NotifyDescriptor(
            resamplePanel,
            "Resample dataset",
            NotifyDescriptor.OK_CANCEL_OPTION,
            NotifyDescriptor.PLAIN_MESSAGE,
            null,
            NotifyDescriptor.CANCEL_OPTION);
    DatasetTimp findataset = new DatasetTimp();
    findataset.SetPsisim(data.GetPsisim());
    findataset.SetX(data.GetX());
    findataset.SetX2(data.GetX2());
    findataset.setType("spec");
    findataset.SetNl(data.GetNl()[0]);
    findataset.SetNt(data.GetNt()[0]);

    if (DialogDisplayer.getDefault().notify(resampleDatasetDialod).equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp newdataset = new DatasetTimp(); //data;
        if (resamplePanel.getResampleXState()){
            //resample x dimention
            int num = resamplePanel.getResampleXNum();
            double sum;
            int imwidth = findataset.GetNl()[0];
            int imheight =findataset.GetNt()[0];
            int w = findataset.GetNl()[0]/num;

            double[] temp = new double[num*imheight];
            int count=0;

            for (int i=0; i<imheight; i++){
                for (int j=0; j<num-1; j++){
                    sum=0;
                    for (int k=0; k<w; k++)
                        sum+=findataset.GetPsisim()[i+(j*w+k)*imheight];
                    temp[i+j*imheight]=sum/w;
                }

                sum=0;
                count=0;
                for (int k=i+((num-1)*w)*imheight; k<i+(imwidth-1)*imheight; k=k+imheight){
                    sum+=findataset.GetPsisim()[k];
                    count++;
                }
                temp[i+(num-1)*imheight]=sum/count;
            }

            newdataset.SetPsisim(temp);

            temp = new double[num];

            for (int j=0; j<num-1; j++){
                sum=0;
                for (int k=j*w; k<(j+1)*w; k++){
                    sum+=findataset.GetX2()[k];
                    }
                    temp[j]=sum/w;
                }
            sum=0;
            count=0;
            for (int k=(num-1)*w; k<imwidth; k++){
                sum+=findataset.GetX2()[k];
                count++;
            }
            temp[num-1]=sum/count;    
            newdataset.SetX2(temp);
            newdataset.SetNl(num);
            newdataset.SetNt(findataset.GetNt()[0]);
            newdataset.SetX(findataset.GetX());
            newdataset.CalcRangeInt();
            newdataset.setType("spec");
            findataset = newdataset;
        }

        if (resamplePanel.getResampleYState()){
            //resample y dimention
            newdataset = new DatasetTimp(); //data;
            int num = resamplePanel.getResampleYNum();
            double sum;
            int imwidth = findataset.GetNl()[0];
            int imheight =findataset.GetNt()[0];
            int w = findataset.GetNt()[0]/num;

            double[] temp = new double[num*imwidth];
            int count=0;

            for (int i = 0; i < imwidth; i++){
                for (int j = 0; j < num-1; j++){
                     sum=0;
                     for (int k=0; k<w; k++)
                         sum+=findataset.GetPsisim()[i*imheight+j*w+k];
                     temp[i*num+j]=sum/w;
                }

                sum=0;
                count=0;
                for (int k = i*imheight+(num-1)*w; k<(i+1)*imheight; k++){
                    sum+=findataset.GetPsisim()[k];
                    count++;
                }
                temp[(i+1)*num-1]=sum/count;
            }

            newdataset.SetPsisim(temp);

            temp = new double[num];

            for (int j=0; j<num-1; j++){
                sum=0;
                for (int k=j*w; k<(j+1)*w; k++){
                    sum+=findataset.GetX()[k];
                    }
                    temp[j]=sum/w;
                }
            sum=0;
            count=0;
            for (int k=(num-1)*w; k<imheight; k++){
                sum+=findataset.GetX()[k];
                count++;
            }
            temp[num-1]=sum/count;

            newdataset.SetX(temp);
            newdataset.SetNt(num);
            newdataset.SetNl(findataset.GetNl()[0]);
            newdataset.SetX2(findataset.GetX2());
            newdataset.CalcRangeInt();
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
                    writeTo = cachefolder.createData(findataset.GetDatasetName(), "timpdataset");
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
        }
   }
}//GEN-LAST:event_jBResampleActionPerformed

private void jSRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSRowStateChanged
    crosshair2.setValue(dataset.GetImageHeigth()-jSRow.getValue());
    int xIndex = jSRow.getValue();// - jSRow.getMinimum();
    XYDataset d = ImageUtilities.extractRowFromImageDataset(dataset, xIndex, "Spec");
    subchartWaveTrace.getXYPlot().setDataset(d);
}//GEN-LAST:event_jSRowStateChanged

private void jSColumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSColumStateChanged
    crosshair1.setValue(jSColum.getValue());
    int xIndex = jSColum.getValue();// - jSColum.getMinimum();
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

    Matrix pSIsim = new Matrix(data.GetPsisim(), data.GetNt()[0]);
    svdResult =  pSIsim.svd();
    
    

    int n = 1;
    //creare collection with first 2 LSV
    XYSeriesCollection lSVCollection = new XYSeriesCollection();
    XYSeries seria;
    seria = new XYSeries("LSV1");
    for (int i = 0; i < data.GetX().length; i++) {
        seria.add(data.GetX()[i], svdResult.getU().get(i, 0));
    }
    lSVCollection.addSeries(seria);

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
    tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length - 1]);
    tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
    ChartPanel chpan = new ChartPanel(tracechart);
//add chart with 2 LSV to JPannel
    jPLeftSingVectors.removeAll();
    jPLeftSingVectors.add(chpan);

//creare collection with first 2 RSV
    XYSeriesCollection rSVCollection = new XYSeriesCollection();
    for (int j = 0; j < n; j++) {
        seria = new XYSeries("RSV" + (j + 1));
        for (int i = 0; i < data.GetX2().length; i++) {
            seria.add(data.GetX2()[i], svdResult.getV().get(i, j));
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
    tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length - 1]);
    tracechart.getXYPlot().getDomainAxis().setAutoRange(false);
    chpan = new ChartPanel(tracechart);
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

}//GEN-LAST:event_jBdoSVDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputDatasetName;
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBResample;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jBdoSVD;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPSpecImage;
    private javax.swing.JPanel jPXTrace;
    private javax.swing.JPanel jPYTrace;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JToggleButton jTBZoomX;
    private javax.swing.JToggleButton jTBZoomY;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
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
        return CloneableTopComponent.PERSISTENCE_ALWAYS;
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
        dataset = new ColorCodedImageDataset(data.GetNl()[0],data.GetNt()[0],
                data.GetPsisim(), data.GetX2(), data.GetX(), true);
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
            plot2.getDomainAxis().setRange(new Range(data.GetX2()[lowInd],data.GetX2()[upInd]));
            jSColum.setMinimum(lowInd);
            jSColum.setMaximum(upInd);
        }

         if (!plot.getRangeAxis().getRange().equals(this.lastYRange)) {
            this.lastYRange = plot.getRangeAxis().getRange();
            XYPlot plot1 = (XYPlot) this.subchartTimeTrace.getPlot();
            lowInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getUpperBound());
            upInd = (int)(this.wholeYRange.getUpperBound() - this.lastYRange.getLowerBound()-1);
            plot1.getDomainAxis().setRange(new Range(data.GetX()[lowInd],data.GetX()[upInd]));
            jSRow.setMinimum(lowInd);
            jSRow.setMaximum(upInd);
        }
    }

    private JFreeChart createChart(XYDataset dataset1) {
        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, dataset1, PlotOrientation.VERTICAL, false, false,
                false);

        PaintScale ps = new RainbowPaintScale(data.GetMinInt(), data.GetMaxInt());
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
        PaintScale ps = new RainbowPaintScale(data.GetMinInt(), data.GetMaxInt());
        this.chartMain = createChart(new XYSeriesCollection());
        this.chartMain.addChangeListener(this);
        XYPlot tempPlot = (XYPlot)this.chartMain.getPlot();
        this.wholeXRange = tempPlot.getDomainAxis().getRange();
        this.wholeYRange = tempPlot.getRangeAxis().getRange();
        chpanImage = new ChartPanel(chartMain);
        chpanImage.setFillZoomRectangle(true);
        chpanImage.setMouseWheelEnabled(true);
//        jPSpecImage.removeAll();
//        chpanImage.setSize(jPSpecImage.getMaximumSize());
        jPSpecImage.add(chpanImage);
        jPSpecImage.repaint();

        ImageCrosshairLabelGenerator crossLabGen1 = new ImageCrosshairLabelGenerator(data.GetX2(),false);
        ImageCrosshairLabelGenerator crossLabGen2 = new ImageCrosshairLabelGenerator(data.GetX(),true);

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
        subchartTimeTrace.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length-1]);
////        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(subchartTimeTrace,true);
//        chpan.setSize(jPYTrace.getMaximumSize());
//        jPYTrace.removeAll();
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPYTrace.add(chpan);
        jPYTrace.repaint();

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
        if (data.GetX2()[data.GetX2().length-1]<data.GetX2()[0])
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[0]);
        else
            subchartWaveTrace.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[data.GetX2().length-1]);

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
//        jPXTrace.removeAll();
        subchart2Panel.setMinimumDrawHeight(0);
        subchart2Panel.setMinimumDrawWidth(0);
        jPXTrace.add(subchart2Panel);
        jPXTrace.repaint();

        jSColum.setMaximum(dataset.GetImageWidth()-1);
        jSColum.setMinimum(0);
        jSColum.setValue(0);
        jSRow.setMaximum(dataset.GetImageHeigth()-1);
        jSRow.setMinimum(0);
        jSRow.setValue(0);

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(data.GetMinInt(),data.GetMaxInt());
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

}
