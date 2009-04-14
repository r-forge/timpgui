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
//import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
//import org.jfree.chart.renderer.xy.XYBlockRenderer;
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
//import javax.swing.text.Utilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import static java.lang.Math.round;
import nl.vu.nat.jfreechartcustom.FastXYPlot;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.openide.util.Exceptions;
import org.openide.util.Exceptions;
import org.openide.windows.CloneableTopComponent;
import org.timpgui.flimdataloader.FlimImage;
import org.timpgui.flimdataloader.SdtDataObject;
import org.timpgui.structures.DatasetTimp;

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

                        //TFfilenam.setText(file.getName());
                //flimImage = new FlimImage(file);
                MakeIntImageChart(MakeXYZDataset());
//                MakeTracesChart(MakeTracesCollection(), true);
                MakeTracesChart(PlotFirstTrace(), false);
                chpanIntenceImage = new ChartPanel(chart, true);
                jPIntensImage.removeAll();
                chpanIntenceImage.setSize(jPIntensImage.getMaximumSize());
                //chpanIntenceImage.setSize(128, 128);
                chpanIntenceImage.addChartMouseListener(this);

                jPIntensImage.add(chpanIntenceImage);
                jPIntensImage.repaint();

                jLNumSelPix.setText(Integer.toString(numSelPix));
                jLMaxAmpl.setText(Integer.toString(flimImage.getMaxIntens()));
                jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
                jLHeigth.setText(Integer.toString(flimImage.getY()));
                jLWidth.setText(Integer.toString(flimImage.getX()));
                jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));
    }

    public SdtTopComponent(String filename) {
        initComponents();
        setName(NbBundle.getMessage(SdtTopComponent.class, "CTL_SdtTopComponent"));
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

                        //TFfilenam.setText(file.getName());
                //flimImage = new FlimImage(file);
                MakeIntImageChart(MakeXYZDataset());
//                MakeTracesChart(MakeTracesCollection(), true);
                MakeTracesChart(PlotFirstTrace(), false);
                chpanIntenceImage = new ChartPanel(chart, true);
                jPIntensImage.removeAll();
                chpanIntenceImage.setSize(jPIntensImage.getMaximumSize());
                //chpanIntenceImage.setSize(128, 128);
                chpanIntenceImage.addChartMouseListener(this);

                jPIntensImage.add(chpanIntenceImage);
                jPIntensImage.repaint();

                jLNumSelPix.setText(Integer.toString(numSelPix));
                jLMaxAmpl.setText(Integer.toString(flimImage.getMaxIntens()));
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

        jPIntensImage = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TFfilenam = new javax.swing.JTextField();
        Bloadsamp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLChNumm = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLChWidth = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLWidth = new javax.swing.JLabel();
        jLHeigth = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTFDatasetName = new javax.swing.JTextField();
        jBMakeDataset = new javax.swing.JButton();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLMaxAmpl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLNumSelPix = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFPortion = new javax.swing.JTextField();
        jBSelect = new javax.swing.JButton();
        jBUnselect = new javax.swing.JButton();
        jBSumSelPix = new javax.swing.JButton();
        jBExportPixels = new javax.swing.JButton();
        jPSelectedTrace = new javax.swing.JPanel();

        jPIntensImage.setBackground(new java.awt.Color(0, 0, 0));
        jPIntensImage.setMaximumSize(new java.awt.Dimension(500, 400));
        jPIntensImage.setMinimumSize(new java.awt.Dimension(500, 400));
        jPIntensImage.setPreferredSize(new java.awt.Dimension(500, 400));
        jPIntensImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPIntensImageMouseClicked(evt);
            }
        });
        jPIntensImage.setLayout(new javax.swing.BoxLayout(jPIntensImage, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SampleFile"));
        jPanel3.setMaximumSize(new java.awt.Dimension(460, 85));
        jPanel3.setMinimumSize(new java.awt.Dimension(460, 85));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel1.text")); // NOI18N

        TFfilenam.setEditable(false);
        TFfilenam.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.TFfilenam.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(Bloadsamp, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.Bloadsamp.text")); // NOI18N
        Bloadsamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloadsampActionPerformed(evt);
            }
        });

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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TFfilenam, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Bloadsamp, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(jLWidth)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLHeigth))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLChWidth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLChNumm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(TFfilenam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bloadsamp))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLWidth)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLChNumm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLChWidth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLHeigth))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dataset"));
        jPanel4.setMaximumSize(new java.awt.Dimension(282, 87));
        jPanel4.setMinimumSize(new java.awt.Dimension(282, 87));
        jPanel4.setRequestFocusEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jLabel5.text")); // NOI18N

        jTFDatasetName.setText(org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jTFDatasetName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSaveIvoFile.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jBMakeDataset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBSaveIvoFile))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTFDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTFDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBMakeDataset)
                    .addComponent(jBSaveIvoFile))
                .addContainerGap(25, Short.MAX_VALUE))
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

        org.openide.awt.Mnemonics.setLocalizedText(jBSumSelPix, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBSumSelPix.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBExportPixels, org.openide.util.NbBundle.getMessage(SdtTopComponent.class, "SdtTopComponent.jBExportPixels.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFPortion))
                            .addComponent(jBSumSelPix, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jBSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBUnselect))
                            .addComponent(jBExportPixels))))
                .addGap(181, 181, 181))
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
                    .addComponent(jBSelect)
                    .addComponent(jTFPortion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBUnselect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSumSelPix)
                    .addComponent(jBExportPixels))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPSelectedTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedTrace.setMaximumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setMinimumSize(new java.awt.Dimension(550, 300));
        jPSelectedTrace.setPreferredSize(new java.awt.Dimension(550, 300));

        javax.swing.GroupLayout jPSelectedTraceLayout = new javax.swing.GroupLayout(jPSelectedTrace);
        jPSelectedTrace.setLayout(jPSelectedTraceLayout);
        jPSelectedTraceLayout.setHorizontalGroup(
            jPSelectedTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        jPSelectedTraceLayout.setVerticalGroup(
            jPSelectedTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPIntensImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))))
                .addGap(335, 335, 335))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPIntensImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSelectedTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jPIntensImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPIntensImageMouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jPIntensImageMouseClicked

private void BloadsampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloadsampActionPerformed
// TODO add your handling code here:
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                TFfilenam.setText(file.getName());
                flimImage = new FlimImage(file);
                flimImage.buildIntMap(1);
                MakeIntImageChart(MakeXYZDataset());  
//                MakeTracesChart(MakeTracesCollection(), true);
                MakeTracesChart(PlotFirstTrace(), false);
                chpanIntenceImage = new ChartPanel(chart, true);
                jPIntensImage.removeAll();
                chpanIntenceImage.setSize(jPIntensImage.getSize());
                chpanIntenceImage.addChartMouseListener(this);
                
                jPIntensImage.add(chpanIntenceImage);
                jPIntensImage.repaint(); 
                
                jLNumSelPix.setText(Integer.toString(numSelPix));
                jLMaxAmpl.setText(Integer.toString(flimImage.getMaxIntens()));
                jLChNumm.setText(Integer.toString(flimImage.getCannelN()));
                jLHeigth.setText(Integer.toString(flimImage.getY()));
                jLWidth.setText(Integer.toString(flimImage.getX()));
                jLChWidth.setText(Double.toString(flimImage.getCannelW()).substring(0, 7));
                
                
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
}//GEN-LAST:event_BloadsampActionPerformed

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed
// TODO add your handling code here:
    DatasetTimp timpDat = new DatasetTimp(flimImage.getCannelN(),numSelPix,flimImage.getCurveNum(),jTFDatasetName.getText());
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
    
       //Register.RegisterData(timpDat);
       //paintComponent(super.getGraphics());
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
    private javax.swing.JButton Bloadsamp;
    private javax.swing.JTextField TFfilenam;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPIntensImage;
    private javax.swing.JPanel jPSelectedTrace;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTFDatasetName;
    private javax.swing.JTextField jTFPortion;
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
            if (dataset.getZValue(1, (int)chartY*flimImage.getX()+(int)chartX)==-1){
                dataset.SetValue(chartY*flimImage.getX()+chartX,flimImage.getIntMap()[chartY*flimImage.getX()+chartX]);
                numSelPix--;
                jLNumSelPix.setText(Integer.toString(numSelPix));
            }else {
                dataset.SetValue((int)chartX, (int)chartY, -1);
                numSelPix++;
                jLNumSelPix.setText(Integer.toString(numSelPix));
            }
            UpdateSelectedTrace((int)chartY*flimImage.getX()+(int)chartX);
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
