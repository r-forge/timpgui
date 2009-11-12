package org.glotaran.core.datadisplayers.flim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import java.io.File;
import java.io.ObjectOutputStream;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.main.nodes.TgdDataChildren;
import org.glotaran.core.main.nodes.TgdDataNode;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.main.structures.DatasetTimp;
import org.glotaran.jfreechartcustom.FastXYPlot;
import org.glotaran.jfreechartcustom.GraphPanel;
import org.glotaran.jfreechartcustom.GrayPaintScalePlus;
import org.glotaran.jfreechartcustom.IntensImageDataset;
import org.glotaran.sdtdataloader.FlimImage;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import static java.lang.Math.round;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.windows.CloneableTopComponent;

/**
 * Top component which displays something.
 */
final public class SdtTopComponent extends CloneableTopComponent implements ChartMouseListener {
    
    private FlimImage flimImage;
    private JFreeChart chart;
    private ChartPanel chpanIntenceImage;
    private GraphPanel chpanSelectedTrace;
    private IntensImageDataset dataset;
    private XYSeriesCollection tracesCollection;
    private int numSelPix;
    TgdDataObject dataObject;
    private static SdtTopComponent instance;
 /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SdtTopComponent";

    public SdtTopComponent() {
        flimImage = null;
        chart=null;
        chpanIntenceImage=null;
//        chpanSelectedTrace = null;
        dataset = null;
        numSelPix = 0;
        tracesCollection = null;
        initComponents();
        setName(NbBundle.getMessage(SdtTopComponent.class, "CTL_SdtTopComponent"));
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));
    }

    public SdtTopComponent(TgdDataObject dataObj) {
        String filename;
        dataObject = dataObj;
        initComponents();
        setName(dataObj.getName());
        //setName(dataObject.getTgd().getFilename());
        filename = dataObject.getTgd().getPath();
        filename = filename.concat("/").concat(dataObject.getTgd().getFilename());
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));

        if (flimImage == null) {
            try {
                flimImage = new FlimImage(new File(filename));
            } catch (FileNotFoundException ex) {
                CoreErrorMessages.fileNotFound();
            } catch (IOException ex) {
                CoreErrorMessages.IOException();
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

        flimImage.makeBinnedImage(1);
        flimImage.buildIntMap(1);
        MakeIntImageChart(MakeXYZDataset());
        MakeTracesChart(PlotFirstTrace(0), false);
        chpanIntenceImage = new ChartPanel(chart, true);
        chpanIntenceImage.addChartMouseListener(this);
        jPIntensImage.add(chpanIntenceImage);
        jLNumSelPix.setText(Integer.toString(numSelPix));
        jTFMaxIntence.setText(Integer.toString(flimImage.getMaxIntens()));
        jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
        jLHeigth.setText(Integer.toString(flimImage.getY()));
        jLWidth.setText(Integer.toString(flimImage.getX()));
        jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));

    }


    public SdtTopComponent(TimpDatasetDataObject dataObj) {
        String filename;
        DatasetTimp tempData;
        TgdDataNode tddNode = (TgdDataNode)dataObj.getNodeDelegate().getParentNode();
        dataObject = tddNode.getLookup().lookup(TgdDataObject.class);
        initComponents();
        setName(dataObject.getTgd().getFilename());
        filename = dataObject.getTgd().getPath();
        filename = filename.concat("/").concat(dataObject.getTgd().getFilename());
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));

        if (flimImage == null) {
            try {
                flimImage = new FlimImage(new File(filename));
            } catch (FileNotFoundException ex) {
                CoreErrorMessages.fileNotFound();
            } catch (IOException ex) {
                CoreErrorMessages.IOException();
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

        flimImage.makeBinnedImage(1);
        tempData = dataObj.getDatasetTimp();
        flimImage.buildIntMap(1);
        if (tempData.getMaxInt()>flimImage.getMaxIntens()){
            flimImage.setBinned(1);
            jTButBin.setSelected(true);
            flimImage.buildIntMap(1);
        }
        
        MakeXYZDataset();
        for (int i = 0; i < tempData.getNl(); i++){
            dataset.SetValue((int)tempData.getX2()[i], -1);
        }
        numSelPix = tempData.getNl();
        jLNumSelPix.setText(Integer.toString(numSelPix));
        MakeIntImageChart(dataset);
        MakeTracesChart(PlotFirstTrace((int)tempData.getX2()[0]), false);
        chpanIntenceImage = new ChartPanel(chart, true);
        chpanIntenceImage.addChartMouseListener(this);
        jPIntensImage.add(chpanIntenceImage);
        jLNumSelPix.setText(Integer.toString(numSelPix));
        jTFMaxIntence.setText(Integer.toString(flimImage.getMaxIntens()));
        jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
        jLHeigth.setText(Integer.toString(flimImage.getY()));
        jLWidth.setText(Integer.toString(flimImage.getX()));
        jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jTButBin = new javax.swing.JToggleButton();
        jTButAmpl = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jBSumSelPix = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jBMakeDataset = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jBSaveIvoFile = new javax.swing.JButton();
        jPIntensImage = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLChNumm = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLChWidth = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLWidth = new javax.swing.JLabel();
        jLHeigth = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLNumSelPix = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFPortion = new javax.swing.JTextField();
        jBSelect = new javax.swing.JButton();
        jBUnselect = new javax.swing.JButton();
        jPSelectedTrace = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTFMaxIntence = new javax.swing.JTextField();
        jTFMinIntence = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jPSumTrace = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSnumSV = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jTFtotalNumSV = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPSingValues = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPLeftSingVectors = new javax.swing.JPanel();
        jPRightSingVectors = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jTButBin, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTButBin.text")); // NOI18N
        jTButBin.setFocusable(false);
        jTButBin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTButBin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTButBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTButBinActionPerformed(evt);
            }
        });
        jToolBar1.add(jTButBin);

        org.openide.awt.Mnemonics.setLocalizedText(jTButAmpl, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTButAmpl.text")); // NOI18N
        jTButAmpl.setFocusable(false);
        jTButAmpl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTButAmpl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTButAmpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTButAmplActionPerformed(evt);
            }
        });
        jToolBar1.add(jTButAmpl);
        jToolBar1.add(jSeparator4);

        org.openide.awt.Mnemonics.setLocalizedText(jBSumSelPix, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSumSelPix.text")); // NOI18N
        jBSumSelPix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSumSelPixActionPerformed(evt);
            }
        });
        jToolBar1.add(jBSumSelPix);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator2);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jToolBar1.add(jBMakeDataset);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSaveIvoFile.text")); // NOI18N
        jBSaveIvoFile.setEnabled(false);
        jToolBar1.add(jBSaveIvoFile);

        jPIntensImage.setBackground(new java.awt.Color(0, 0, 0));
        jPIntensImage.setMaximumSize(new java.awt.Dimension(500, 400));
        jPIntensImage.setMinimumSize(new java.awt.Dimension(500, 400));
        jPIntensImage.setPreferredSize(new java.awt.Dimension(500, 400));
        jPIntensImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPIntensImageMouseClicked(evt);
            }
        });
        jPIntensImage.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SampleFile"));
        jPanel3.setMaximumSize(new java.awt.Dimension(460, 85));
        jPanel3.setMinimumSize(new java.awt.Dimension(460, 85));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLChNumm, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLChNumm.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLChWidth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLChWidth.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLWidth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLWidth.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLHeigth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLHeigth.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(jLWidth)
                        .addGap(95, 95, 95)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLHeigth)
                        .addGap(99, 99, 99))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLChNumm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jLabel9)))
                .addGap(60, 60, 60)
                .addComponent(jLChWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jLWidth))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(jLHeigth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLChNumm)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLChWidth)))
                .addContainerGap(1, Short.MAX_VALUE))
        );

        jPanel5.setMaximumSize(new java.awt.Dimension(418, 105));
        jPanel5.setMinimumSize(new java.awt.Dimension(418, 105));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel3.text")); // NOI18N

        jLNumSelPix.setFont(new java.awt.Font("Tahoma", 1, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLNumSelPix, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLNumSelPix.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel2.text")); // NOI18N

        jTFPortion.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTFPortion.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBSelect, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSelect.text")); // NOI18N
        jBSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelectActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jBUnselect, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBUnselect.text")); // NOI18N
        jBUnselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUnselectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLNumSelPix))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTFPortion, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addGap(141, 141, 141)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jBSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBUnselect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(105, 105, 105))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLNumSelPix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFPortion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBUnselect, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPSelectedTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedTrace.setMaximumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setMinimumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setPreferredSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jButton2.text")); // NOI18N
        jButton2.setIconTextGap(2);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTFMaxIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMaxIntence.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTFMaxIntence.text")); // NOI18N

        jTFMinIntence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMinIntence.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTFMinIntence.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(11, 11, 11)
                        .addComponent(jTFMinIntence, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFMaxIntence, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel4)))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(jTFMinIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jTFMaxIntence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, 0, 438, Short.MAX_VALUE)
                    .addComponent(jPIntensImage, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel5, 0, 103, Short.MAX_VALUE))
                    .addComponent(jPIntensImage, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE))
                .addGap(242, 242, 242))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jToolBar2.setRollover(true);

        jPSumTrace.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
            .addComponent(jPSumTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSumTrace, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        jToolBar3.setRollover(true);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel5.text")); // NOI18N
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel5, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel10.text")); // NOI18N

        jSnumSV.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSnumSVStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel11.text")); // NOI18N

        jTFtotalNumSV.setEditable(false);
        jTFtotalNumSV.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTFtotalNumSV.text")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSnumSV, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTFtotalNumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jSnumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTFtotalNumSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPSingValues.setBackground(new java.awt.Color(255, 255, 255));
        jPSingValues.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPLeftSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPLeftSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel13.add(jPLeftSingVectors);

        jPRightSingVectors.setBackground(new java.awt.Color(255, 255, 255));
        jPRightSingVectors.setLayout(new java.awt.BorderLayout());
        jPanel13.add(jPRightSingVectors);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPSingValues, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPSingValues, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel7);

        jTabbedPane1.addTab("SVD", jScrollPane1);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void jPIntensImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPIntensImageMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jPIntensImageMouseClicked

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed
    NotifyDescriptor.InputLine datasetNameDialog = new NotifyDescriptor.InputLine(
           NbBundle.getBundle("org/glotaran/core/datadisplayers/Bundle").getString("dataset_name"),
           NbBundle.getBundle("org/glotaran/core/datadisplayers/Bundle").getString("spec_datasetname"));
    Object res = DialogDisplayer.getDefault().notify(datasetNameDialog);

    if (res.equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp timpDat = new DatasetTimp();
        timpDat.setType("flim");
        timpDat.setDatasetName(datasetNameDialog.getInputText());

        timpDat.setIntenceIm(intToDoubleArray(flimImage.getIntMap()));
        int k=0;
        for (int i=0; i<flimImage.getCurveNum(); i++){
            if (dataset.getZValue(1,i)==-1){
                for (int j = 0; j<flimImage.getCannelN(); j++){
                    timpDat.getPsisim()[k*flimImage.getCannelN()+j]=flimImage.getData()[i*flimImage.getCannelN()+j];
                }
                timpDat.getX2()[k]=i;
                k++;
            }
        }
        for (int i = 0; i<flimImage.getCannelN(); i++) {
                timpDat.getX()[i] = i * flimImage.getCannelW();
            }

        timpDat.setMaxInt(flimImage.getMaxIntens());
        timpDat.setMinInt(flimImage.getMinIntens());


//create serfile
        FileObject cachefolder = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getCacheFolder(true);
            cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
            FileObject writeTo;
            try {
                writeTo = cachefolder.createData(timpDat.getDatasetName(), "timpdataset");
                ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                stream.writeObject(timpDat);
                stream.close();
                TimpDatasetDataObject dObj = (TimpDatasetDataObject) DataObject.find(writeTo);
                TgdDataChildren chidrens = (TgdDataChildren) dataObject.getNodeDelegate().getChildren();
                chidrens.addObj(dObj);
            } catch (IOException ex) {
                CoreErrorMessages.IOException();
            }

        } else {
            CoreErrorMessages.noMainProjectFound();
        }
    
    
    }

     
}//GEN-LAST:event_jBMakeDatasetActionPerformed

private void jBSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelectActionPerformed
        double portion = 0.7;
        dataset.SetActive(false);
        
        try{
        portion = Double.parseDouble(jTFPortion.getText());      
        }
        catch (NumberFormatException ex) {
            NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception(NbBundle.getBundle("org/glotaran/core/datadisplayers/Bundle").getString("wrongPortion")));
        }
         
        for (int i=0; i<flimImage.getCurveNum(); i++){
            if (dataset.getZValue(1, i)>portion*flimImage.getMaxIntens()){
                dataset.SetValue(i, -1);
                numSelPix++;
            }
        }
        jLNumSelPix.setText(Integer.toString(numSelPix));
        dataset.SetActive(true);
        dataset.Update();
}//GEN-LAST:event_jBSelectActionPerformed

private void jBUnselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUnselectActionPerformed
        dataset.SetIntenceImage(flimImage.getIntMap().clone()); 
        numSelPix=0;
        jLNumSelPix.setText(Integer.toString(numSelPix));
}//GEN-LAST:event_jBUnselectActionPerformed

private void jSnumSVStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSnumSVStateChanged

//    double n = (Double)jSnumSV.getModel().getValue();
//
//    //creare collection with first 2 LSV
//    XYSeriesCollection lSVCollection = new XYSeriesCollection();
//    XYSeries seria;
//    for (int j =0; j < n; j++){
//        seria = new XYSeries("LSV1"+j+1);
//        for (int i = 0; i < data.GetX().length; i++) {
//            seria.add(data.GetX()[i], svdResult.getU().get(i, j));
//        }
//        lSVCollection.addSeries(seria);
//    }
//    leftSVChart.getXYPlot().setDataset(lSVCollection);
//
//    XYSeriesCollection rSVCollection = new XYSeriesCollection();
//    for (int j = 0; j < n; j++) {
//        seria = new XYSeries("RSV" + (j + 1));
//        for (int i = 0; i < data.GetX2().length; i++) {
//            seria.add(data.GetX2()[i], svdResult.getV().get(i, j));
//        }
//        rSVCollection.addSeries(seria);
//    }
//
//    rightSVChart.getXYPlot().setDataset(rSVCollection);
}//GEN-LAST:event_jSnumSVStateChanged

private void jBSumSelPixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSumSelPixActionPerformed


    JFreeChart tracechart;
    XYSeriesCollection trace = new XYSeriesCollection();
    XYSeries seria = new XYSeries("Trace");
    double[] sumTrace = new double[flimImage.getCannelN()];

    for (int i = 0; i < sumTrace.length; i++) {
        sumTrace[i]=0;
    }

    for (int i=0; i<flimImage.getCurveNum(); i++){
        if (dataset.getZValue(1,i)==-1){
            for (int j = 0; j<flimImage.getCannelN(); j++){
                sumTrace[j]+=flimImage.getData()[i*flimImage.getCannelN()+j];
            }
        }
     }
     for (int i = 0; i<flimImage.getCannelN(); i++)
         seria.add(flimImage.getCannelW()*i, sumTrace[i]);
    trace.addSeries(seria);
    tracechart = ChartFactory.createXYLineChart(
        "Sum Trace",
        "Time (ns)",
        "Number of counts",
        trace,
        PlotOrientation.VERTICAL,
        false,
        false,
        false
    );
    tracechart.getXYPlot().getDomainAxis().setUpperBound(flimImage.getTime());
    GraphPanel chpanSumTrace = new GraphPanel(tracechart,true);
    jPSumTrace.removeAll();
    jPSumTrace.setLayout(new BorderLayout());
    jPSumTrace.add(chpanSumTrace);
}//GEN-LAST:event_jBSumSelPixActionPerformed

private void jTButBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTButBinActionPerformed
    int[] tempSelectedPixels = new int[flimImage.getCurveNum()];
    for (int i=0; i<flimImage.getCurveNum(); i++){
        if (dataset.getZValue(1, i)==-1){
            tempSelectedPixels[i] = -1;
        }
    }
    if (jTButBin.isSelected())
        flimImage.setBinned(1);
    else
        flimImage.setBinned(0);
    
    if ( jTButAmpl.isSelected())
        flimImage.buildIntMap(0);
    else
        flimImage.buildIntMap(1);

    dataset.SetIntenceImage(flimImage.getIntMap().clone());

    for (int i=0; i<flimImage.getCurveNum(); i++){
        if (tempSelectedPixels[i] == -1){
            dataset.SetValue(i, -1);
        }
    }
    jTFMaxIntence.setText(String.valueOf(flimImage.getMaxIntens()));
    jTFMaxIntence.setText("0");
    updateIntenceImageChart(0, flimImage.getMaxIntens());
}//GEN-LAST:event_jTButBinActionPerformed

private void jTButAmplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTButAmplActionPerformed
    int[] tempSelectedPixels = new int[flimImage.getCurveNum()];
    for (int i=0; i<flimImage.getCurveNum(); i++){
        if (dataset.getZValue(1, i)==-1){
            tempSelectedPixels[i] = -1;
        }
    }
    if ( jTButAmpl.isSelected())
        flimImage.buildIntMap(0);
    else
        flimImage.buildIntMap(1);

    dataset.SetIntenceImage(flimImage.getIntMap().clone());
    for (int i=0; i<flimImage.getCurveNum(); i++){
        if (tempSelectedPixels[i] == -1){
            dataset.SetValue(i, -1);
        }
    }
    jTFMaxIntence.setText(String.valueOf(flimImage.getMaxIntens()));
    jTFMaxIntence.setText("0");
    updateIntenceImageChart(0, flimImage.getMaxIntens());
}//GEN-LAST:event_jTButAmplActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    double newMinAmpl, newMaxAmpl;
    try {
        newMinAmpl = Double.parseDouble(jTFMinIntence.getText());
        newMaxAmpl = Double.parseDouble(jTFMaxIntence.getText());
        updateIntenceImageChart(newMinAmpl, newMaxAmpl);
    }catch(NumberFormatException ex) {
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("set_correct_chanNum")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

}//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jBSelect;
    private javax.swing.JButton jBSumSelPix;
    private javax.swing.JButton jBUnselect;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLChNumm;
    private javax.swing.JLabel jLChWidth;
    private javax.swing.JLabel jLHeigth;
    private javax.swing.JLabel jLNumSelPix;
    private javax.swing.JLabel jLWidth;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPIntensImage;
    private javax.swing.JPanel jPLeftSingVectors;
    private javax.swing.JPanel jPRightSingVectors;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JPanel jPSingValues;
    private javax.swing.JPanel jPSumTrace;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JSpinner jSnumSV;
    private javax.swing.JToggleButton jTButAmpl;
    private javax.swing.JToggleButton jTButBin;
    private javax.swing.JTextField jTFMaxIntence;
    private javax.swing.JTextField jTFMinIntence;
    private javax.swing.JTextField jTFPortion;
    private javax.swing.JTextField jTFtotalNumSV;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized SdtTopComponent getDefault() {
        if (instance == null) {
            instance = new SdtTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the SdtTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized SdtTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SdtTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SdtTopComponent) {
            return (SdtTopComponent) win;
        }
        Logger.getLogger(SdtTopComponent.class.getName()).warning(
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

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return SdtTopComponent.getDefault();
        }
    }

     public void chartMouseClicked(ChartMouseEvent event) {
        int mouseX = event.getTrigger().getX();
        int mouseY = event.getTrigger().getY();
        Point2D p = this.chpanIntenceImage.translateScreenToJava2D(new Point(mouseX, mouseY));
        XYPlot plot = (XYPlot) this.chart.getPlot();
        ChartRenderingInfo info = this.chpanIntenceImage.getChartRenderingInfo();
        Rectangle2D dataArea = info.getPlotInfo().getDataArea();
        
        ValueAxis domainAxis = plot.getDomainAxis();
        RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
        ValueAxis rangeAxis = plot.getRangeAxis();
        RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge();
            
        int chartX =  (int) round(domainAxis.java2DToValue(p.getX(), dataArea, domainAxisEdge));
        int chartY =  (int) round(rangeAxis.java2DToValue(p.getY(), dataArea, rangeAxisEdge));
        
        if ((chartX<flimImage.getX())&&(chartY<flimImage.getY())&&(chartX>=0)&&(chartY>=0)){
            if (dataset.getZValue(1, chartY*flimImage.getX()+chartX)==-1){
                dataset.SetValue(chartY*flimImage.getX()+chartX,flimImage.getIntMap()[chartY*flimImage.getX()+chartX]);
                numSelPix--;
                jLNumSelPix.setText(Integer.toString(numSelPix));
            }else {
                dataset.SetValue(chartX, chartY, -1);
                numSelPix++;
                jLNumSelPix.setText(Integer.toString(numSelPix));
            }
            UpdateSelectedTrace(chartY*flimImage.getX()+chartX);
        }
    }

    public void chartMouseMoved(ChartMouseEvent event) {
//         System.out.println("ChartMouseMoved");
    }

    private XYZDataset MakeXYZDataset(){
        dataset = new IntensImageDataset(flimImage.getX(),flimImage.getY(),flimImage.getIntMap().clone());
        return dataset;
    }

    private XYSeriesCollection PlotFirstTrace(int index){
            tracesCollection = new XYSeriesCollection();
            XYSeries seria = new XYSeries("Trace");
            for (int j=0; j<flimImage.getCannelN(); j++){
                seria.add(j*flimImage.getCannelW(), flimImage.getData()[index*flimImage.getCannelN()+j]);
            }
           tracesCollection.addSeries(seria);
        return tracesCollection;         
    }
    
    private void UpdateSelectedTrace(int item){          
        tracesCollection.getSeries(0).clear();
        for (int j=0; j<flimImage.getCannelN(); j++){
            tracesCollection.getSeries(0).add(j*flimImage.getCannelW(), flimImage.getData()[item*flimImage.getCannelN()+j]);
        }
    }
    
    private void updateIntenceImageChart(double low, double higth){
        PaintScale scale = new GrayPaintScalePlus(low, higth, -1);
        XYBlockRenderer rend = (XYBlockRenderer) chart.getXYPlot().getRenderer();
        rend.setPaintScale(scale);
        updateColorBar(scale);   
    }
    
    private void updateColorBar(PaintScale scale){
        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setUpperBound(flimImage.getMaxIntens());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(scale,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart.getBackgroundPaint());
        chart.clearSubtitles();
        chart.addSubtitle(legend);
    }

    private void MakeTracesChart(XYSeriesCollection dat, boolean compleateSet){
        JFreeChart tracechart;        
        tracechart = ChartFactory.createXYLineChart(
            "Selected trace", 
            "Time (ns)",
            "Number of counts", 
            dat, 
            PlotOrientation.VERTICAL, 
            false, 
            false, 
            false
        );
        tracechart.getXYPlot().getDomainAxis().setUpperBound(flimImage.getTime());
        tracechart.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        if (compleateSet){
            for (int i = 0; i < flimImage.getCurveNum(); i++)
                tracechart.getXYPlot().getRenderer().setSeriesVisible(i, false);
        }
        chpanSelectedTrace = new GraphPanel(tracechart,true);
        chpanSelectedTrace.setSize(jPSelectedTrace.getMaximumSize());
        jPSelectedTrace.removeAll();
        jPSelectedTrace.add(chpanSelectedTrace);
        jPSelectedTrace.repaint(); 
    }
    
    private void MakeIntImageChart(XYZDataset dataset){
        NumberAxis xAxis = new NumberAxis("X");
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        xAxis.setVisible(false);
        NumberAxis yAxis = new NumberAxis("Y");
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setInverted(true);
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setVisible(false);
        
        XYBlockRenderer renderer = new XYBlockRenderer();
        PaintScale scale = new GrayPaintScalePlus(0, flimImage.getMaxIntens(),-1);        
        renderer.setPaintScale(scale);
        FastXYPlot plot = new FastXYPlot(dataset, xAxis, yAxis, renderer);        

        chart = new JFreeChart(plot);
        chart.setAntiAlias(false);
        chart.removeLegend();
        updateColorBar(scale);
    }

    public static double[] intToDoubleArray(int[] numbers) {
        double[] newNumbers = new double[numbers.length];
        for (int index = 0; index < numbers.length; index++) {
            newNumbers[index] = (double) numbers[index];
        }
        return newNumbers;
    }
}
