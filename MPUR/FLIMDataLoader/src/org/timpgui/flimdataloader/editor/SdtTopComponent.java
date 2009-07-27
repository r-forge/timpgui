/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import nl.wur.flim.jfreechartcustom.GrayPaintScalePlus;
import nl.wur.flim.jfreechartcustom.IntensImageDataset;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import static java.lang.Math.round;
import nl.vu.nat.jfreechartcustom.FastXYPlot;
import nl.vu.nat.tgmprojectsupport.TGProject;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.windows.CloneableTopComponent;
import org.timpgui.flimdataloader.FlimImage;
import org.timpgui.flimdataloader.SdtDataObject;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.tgproject.nodes.TgdDataChildren;

/**
 * Top component which displays something.
 */
final public class SdtTopComponent extends CloneableTopComponent implements ChartMouseListener {
    
    private JFileChooser fc;
    private FlimImage flimImage;
    private JFreeChart chart;
    private ChartPanel chpanIntenceImage, chpanSelectedTrace;
    private IntensImageDataset dataset;
    private XYSeriesCollection tracesCollection;
    private int numSelPix;
    TgdDataObject dataObject;
    private static SdtTopComponent instance;
 /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SdtTopComponent";

    public SdtTopComponent() {
        
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter(".sdt flim imadges", "sdt"));
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

    public SdtTopComponent(SdtDataObject dObj) {
        initComponents();
        setName(NbBundle.getMessage(SdtTopComponent.class, "CTL_SdtTopComponent"));
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));

        flimImage = dObj.getFlimImage();
        flimImage.buildIntMap(1);

               
        MakeIntImageChart(MakeXYZDataset());
        MakeTracesChart(PlotFirstTrace(), false);
        chpanIntenceImage = new ChartPanel(chart, true);
        chpanIntenceImage.addChartMouseListener(this);
        jPIntensImage.add(chpanIntenceImage);

        jLNumSelPix.setText(Integer.toString(numSelPix));
        jLMaxAmpl.setText(Integer.toString(flimImage.getMaxIntens()));
        jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
        jLHeigth.setText(Integer.toString(flimImage.getY()));
        jLWidth.setText(Integer.toString(flimImage.getX()));
        jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));
        buttonGroup1.add(jRBnoBin);
        buttonGroup1.add(jRBbin);
        buttonGroup1.add(jRBbinAv);
    }

    public SdtTopComponent(TgdDataObject dataObj) {
        String filename;
        dataObject = dataObj;
        initComponents();
        setName(dataObject.getTgd().getFilename());
        filename = dataObject.getTgd().getPath();
        filename = filename.concat("/").concat(dataObject.getTgd().getFilename());
        setToolTipText(NbBundle.getMessage(SdtTopComponent.class, "HINT_SdtTopComponent"));


        if (flimImage == null) {
            try {
                flimImage = new FlimImage(new File(filename));
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

        flimImage.buildIntMap(1);

        MakeIntImageChart(MakeXYZDataset());
        MakeTracesChart(PlotFirstTrace(), false);
        chpanIntenceImage = new ChartPanel(chart, true);
        jPIntensImage.removeAll();
        chpanIntenceImage.setSize(jPIntensImage.getMaximumSize());
        chpanIntenceImage.addChartMouseListener(this);

        jPIntensImage.add(chpanIntenceImage);
        jPIntensImage.repaint();

        jLNumSelPix.setText(Integer.toString(numSelPix));
        jLMaxAmpl.setText(Integer.toString(flimImage.getMaxIntens()));
        jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
        jLHeigth.setText(Integer.toString(flimImage.getY()));
        jLWidth.setText(Integer.toString(flimImage.getX()));
        jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));
        buttonGroup1.add(jRBnoBin);
        buttonGroup1.add(jRBbin);
        buttonGroup1.add(jRBbinAv);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPIntensImage = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLChNumm = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLChWidth = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLWidth = new javax.swing.JLabel();
        jLHeigth = new javax.swing.JLabel();
        TFfilenam = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLMaxAmpl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLNumSelPix = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFPortion = new javax.swing.JTextField();
        jBSelect = new javax.swing.JButton();
        jBUnselect = new javax.swing.JButton();
        jPSelectedTrace = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jBMakeDataset = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jBSumSelPix = new javax.swing.JButton();
        jBExportPixels = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jRBnoBin = new javax.swing.JRadioButton();
        jRBbin = new javax.swing.JRadioButton();
        jRBbinAv = new javax.swing.JRadioButton();

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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLChNumm, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLChNumm.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLChWidth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLChWidth.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLWidth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLWidth.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLHeigth, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLHeigth.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(TFfilenam, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.TFfilenam.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(TFfilenam))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(21, 21, 21)
                                .addComponent(jLWidth)
                                .addGap(95, 95, 95)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLHeigth)))
                        .addGap(170, 170, 170))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLChNumm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(60, 60, 60)
                        .addComponent(jLChWidth)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TFfilenam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(jLabel9)
                    .addComponent(jLChWidth)))
        );

        jPanel5.setMaximumSize(new java.awt.Dimension(418, 105));
        jPanel5.setMinimumSize(new java.awt.Dimension(418, 105));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel4.text")); // NOI18N

        jLMaxAmpl.setFont(new java.awt.Font("Tahoma", 1, 12));
        org.openide.awt.Mnemonics.setLocalizedText(jLMaxAmpl, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLMaxAmpl.text")); // NOI18N

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
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLMaxAmpl)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLNumSelPix))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFPortion, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                .addGap(141, 141, 141)))
                        .addGap(98, 98, 98))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jBSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBUnselect)
                        .addContainerGap(298, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLMaxAmpl)
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
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPSelectedTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedTrace.setMaximumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setMinimumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setPreferredSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jToolBar1.add(jBMakeDataset);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jBSumSelPix, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSumSelPix.text")); // NOI18N
        jToolBar1.add(jBSumSelPix);

        org.openide.awt.Mnemonics.setLocalizedText(jBExportPixels, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBExportPixels.text")); // NOI18N
        jToolBar1.add(jBExportPixels);
        jToolBar1.add(jSeparator3);

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSaveIvoFile.text")); // NOI18N
        jToolBar1.add(jBSaveIvoFile);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jPanel1.border.title"))); // NOI18N

        jRBnoBin.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRBnoBin, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jRBnoBin.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jRBbin, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jRBbin.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jRBbinAv, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jRBbinAv.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRBnoBin)
                    .addComponent(jRBbin)
                    .addComponent(jRBbinAv))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRBnoBin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRBbin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRBbinAv)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPIntensImage, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPSelectedTrace, 0, 491, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(349, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPIntensImage, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPSelectedTrace, 0, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jPIntensImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPIntensImageMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jPIntensImageMouseClicked

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed
    NotifyDescriptor.InputLine datasetNameDialog = new NotifyDescriptor.InputLine(
            "Dataset name",
            "Please specify the name for a dataset");
    Object res = DialogDisplayer.getDefault().notify(datasetNameDialog);

    if (res.equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp timpDat = new DatasetTimp(flimImage.getCannelN(),numSelPix,flimImage.getCurveNum(),datasetNameDialog.getInputText());
        timpDat.setType("flim");

        int k=0;
        for (int i=0; i<flimImage.getCurveNum(); i++){
            timpDat.GetIntenceIm()[i]= flimImage.getIntMap()[i];
            if (dataset.getZValue(1,i)==-1){
                for (int j = 0; j<flimImage.getCannelN(); j++){
                    timpDat.GetPsisim()[k*flimImage.getCannelN()+j]=flimImage.getData()[i*flimImage.getCannelN()+j];
                }
                timpDat.GetX2()[k]=i;
                k++;
            }
        }
        for (int i = 0; i<flimImage.getCannelN(); i++)
            timpDat.GetX()[i] = i*flimImage.getCannelW();

//create serfile file
        FileObject cachefolder = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getCacheFolder(true);
            cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());
            FileObject writeTo;
            try {
                writeTo = cachefolder.createData(timpDat.GetDatasetName(), "timpdataset");
                ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                stream.writeObject(timpDat);
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

private void jBSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelectActionPerformed
// TODO add your handling code here:
        double portion = 0.7;
        dataset.SetActive(false);
        
        try{
        portion = Double.parseDouble(jTFPortion.getText());      
        }
        catch (NumberFormatException ex) {
            System.out.println("Wrong number format, default portion of 0.7 has been used!");            
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
// TODO add your handling code here:
        dataset.SetIntenceImage(flimImage.getIntMap().clone()); 
        numSelPix=0;
        jLNumSelPix.setText(Integer.toString(numSelPix));
}//GEN-LAST:event_jBUnselectActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TFfilenam;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBExportPixels;
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jBSelect;
    private javax.swing.JButton jBSumSelPix;
    private javax.swing.JButton jBUnselect;
    private javax.swing.JLabel jLChNumm;
    private javax.swing.JLabel jLChWidth;
    private javax.swing.JLabel jLHeigth;
    private javax.swing.JLabel jLMaxAmpl;
    private javax.swing.JLabel jLNumSelPix;
    private javax.swing.JLabel jLWidth;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPIntensImage;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRBbin;
    private javax.swing.JRadioButton jRBbinAv;
    private javax.swing.JRadioButton jRBnoBin;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTextField jTFPortion;
    private javax.swing.JToolBar jToolBar1;
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
        return TopComponent.PERSISTENCE_ONLY_OPENED;
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
        
//        ChartEntity entity = event.getEntity();
//        XYItemEntity cei = (XYItemEntity)entity;
//        int item = 0;
//        
//        try {
//            item = cei.getItem();            
//            if (dataset.getZValue(1,item)==-1){
//                dataset.SetValue(item,flimImage.getIntMap()[item]);
//                numSelPix--;
//                jLNumSelPix.setText(Integer.toString(numSelPix));
//            }else {
//                dataset.SetValue(item, -1);
//                numSelPix++;
//                jLNumSelPix.setText(Integer.toString(numSelPix));
//            }
////            chpanSelectedTrace.getChart().getXYPlot().getRenderer().setSeriesVisible(item, true);
//            UpdateSelectedTrace(item);
//        }catch (Exception ex){
//            System.out.println("Error pixel out of chart");
//        }

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
        
        if ((chartX<=flimImage.getX())&&(chartY<=flimImage.getY())){
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
    
    private XYSeriesCollection MakeTracesCollection(){
        tracesCollection = new XYSeriesCollection();
        for (int i=0; i<flimImage.getCurveNum(); i++){
            String nam = "Trace "+ Integer.toString(i);
            XYSeries seria = new XYSeries(nam);
            for (int j=0; j<flimImage.getCannelN(); j++){
                seria.add(j*flimImage.getCannelW(), flimImage.getData()[i*flimImage.getCannelN()+j]);
            }
            tracesCollection.addSeries(seria);
        } 
        return tracesCollection;       
    }
    
    private XYSeriesCollection PlotFirstTrace(){
            tracesCollection = new XYSeriesCollection();
            XYSeries seria = new XYSeries("Trace");
            for (int j=0; j<flimImage.getCannelN(); j++){
                seria.add(j*flimImage.getCannelW(), flimImage.getData()[j]);
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
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        if (compleateSet){
            for (int i = 0; i < flimImage.getCurveNum(); i++)
                tracechart.getXYPlot().getRenderer().setSeriesVisible(i, false);
        }
        chpanSelectedTrace = new ChartPanel(tracechart,true);
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
/*
        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);
*/
        chart = new JFreeChart(plot);
        chart.setAntiAlias(false);
        chart.removeLegend();   

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setUpperBound(flimImage.getMaxIntens());
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
    
    
    
}
