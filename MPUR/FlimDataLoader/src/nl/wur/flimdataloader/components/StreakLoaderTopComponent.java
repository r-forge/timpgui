/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.flimdataloader.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nl.vu.nat.timpinterface.Register;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import nl.vu.nat.jfreechartcustom.XYBlockRenderer;
import nl.wur.flimdataloader.flimpac.DatasetTimp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import nl.vu.nat.jfreechartcustom.FastXYPlot;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
//import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final class StreakLoaderTopComponent extends TopComponent implements ChartMouseListener {

    private static StreakLoaderTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "StreakLoaderTopComponent";
    
    private JFileChooser fc;
    private JFreeChart chart;
    private ChartPanel chpanImage;
    private XYSeriesCollection timeTracesCollection, waveTracesCollection;
    private DatasetTimp data;
    private ColorCodedImageDataset dataset;

    private StreakLoaderTopComponent() {
                fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter(" .ivo ASCII files", "ivo"));
        data = new DatasetTimp();
        initComponents();
        setName(NbBundle.getMessage(StreakLoaderTopComponent.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(StreakLoaderTopComponent.class, "HINT_StreakLoaderTopComponent"));
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

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TFfilenam = new javax.swing.JTextField();
        Bloadsamp = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTFDatasetName = new javax.swing.JTextField();
        jBMakeDataset = new javax.swing.JButton();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPSelectedTimeTrace = new javax.swing.JPanel();
        jPSelectedWaveTrace = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLWavesteps = new javax.swing.JLabel();
        jLTimsteps = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLTimeWind = new javax.swing.JLabel();
        jLWavewind = new javax.swing.JLabel();
        jPStreakImage = new javax.swing.JPanel();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jPanel3.border.title"))); // NOI18N
        jPanel3.setMaximumSize(new java.awt.Dimension(460, 85));
        jPanel3.setMinimumSize(new java.awt.Dimension(460, 85));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel1.text")); // NOI18N

        TFfilenam.setEditable(false);
        TFfilenam.setText(org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.TFfilenam.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(Bloadsamp, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.Bloadsamp.text")); // NOI18N
        Bloadsamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloadsampActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                .addComponent(Bloadsamp, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TFfilenam, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addGap(209, 209, 209))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Bloadsamp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TFfilenam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dataset"));
        jPanel4.setMaximumSize(new java.awt.Dimension(282, 87));
        jPanel4.setMinimumSize(new java.awt.Dimension(282, 87));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel5.text")); // NOI18N

        jTFDatasetName.setText(org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jTFDatasetName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jBSaveIvoFile.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jTFDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jBSaveIvoFile))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                        .addGap(115, 115, 115)
                        .addComponent(jBMakeDataset)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jBMakeDataset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSaveIvoFile)
                    .addComponent(jTFDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPSelectedTimeTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTimeTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSelectedTimeTraceLayout = new javax.swing.GroupLayout(jPSelectedTimeTrace);
        jPSelectedTimeTrace.setLayout(jPSelectedTimeTraceLayout);
        jPSelectedTimeTraceLayout.setHorizontalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPSelectedTimeTraceLayout.setVerticalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jPSelectedTimeTrace, gridBagConstraints);

        jPSelectedWaveTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedWaveTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSelectedWaveTraceLayout = new javax.swing.GroupLayout(jPSelectedWaveTrace);
        jPSelectedWaveTrace.setLayout(jPSelectedWaveTraceLayout);
        jPSelectedWaveTraceLayout.setHorizontalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPSelectedWaveTraceLayout.setVerticalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        jPanel2.add(jPSelectedWaveTrace, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLWavesteps, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLWavesteps.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLTimsteps, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLTimsteps.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLTimeWind, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLTimeWind.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLWavewind, org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jLWavewind.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLTimsteps))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLWavesteps)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLWavewind, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLTimeWind, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(338, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLTimeWind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLWavewind))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLTimsteps))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLWavesteps)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPStreakImage.setBackground(new java.awt.Color(0, 0, 0));
        jPStreakImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPStreakImage.setMaximumSize(new java.awt.Dimension(612, 512));
        jPStreakImage.setMinimumSize(new java.awt.Dimension(612, 512));
        jPStreakImage.setPreferredSize(new java.awt.Dimension(612, 512));
        jPStreakImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPStreakImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPStreakImageLayout = new javax.swing.GroupLayout(jPStreakImage);
        jPStreakImage.setLayout(jPStreakImageLayout);
        jPStreakImageLayout.setHorizontalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
        );
        jPStreakImageLayout.setVerticalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPStreakImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPStreakImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel3.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(StreakLoaderTopComponent.class, "StreakLoaderTopComponent.jPanel3.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

private void BloadsampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloadsampActionPerformed
// TODO add your handling code here:
    int returnVal = fc.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
            File file = fc.getSelectedFile();
            TFfilenam.setText(file.getAbsolutePath());
            data.LoadIvoFile(file);
            MakeImageChart(MakeXYZDataset());  
            MakeWaveTraceChart(PlotFirstTrace(false));
            MakeTimeTraceChart(PlotFirstTrace(true));
            chpanImage = new ChartPanel(chart,true);
            jPStreakImage.removeAll();
            chpanImage.setSize(jPStreakImage.getSize());
            chpanImage.addChartMouseListener(this);

            jPStreakImage.add(chpanImage);
            jPStreakImage.repaint(); 
            jLTimsteps.setText(Integer.toString(data.GetNt()[0]));
            jLWavewind.setText(Double.toString(data.GetX2()[data.GetX2().length-1]-data.GetX2()[0]));
            jLTimeWind.setText(Double.toString(data.GetX()[data.GetX().length-1]-data.GetX()[0]));
            jLWavesteps.setText(Integer.toString(data.GetNl()[0]));

            
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
    Register.RegisterData(data);
}//GEN-LAST:event_jBMakeDatasetActionPerformed

private void jPStreakImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPStreakImageMouseClicked
// TODO add your han   dling code here:
}//GEN-LAST:event_jPStreakImageMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bloadsamp;
    private javax.swing.JTextField TFfilenam;
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JLabel jLTimeWind;
    private javax.swing.JLabel jLTimsteps;
    private javax.swing.JLabel jLWavesteps;
    private javax.swing.JLabel jLWavewind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPSelectedTimeTrace;
    private javax.swing.JPanel jPSelectedWaveTrace;
    private javax.swing.JPanel jPStreakImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTFDatasetName;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized StreakLoaderTopComponent getDefault() {
        if (instance == null) {
            instance = new StreakLoaderTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the StreakLoaderTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized StreakLoaderTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(StreakLoaderTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof StreakLoaderTopComponent) {
            return (StreakLoaderTopComponent) win;
        }
        Logger.getLogger(StreakLoaderTopComponent.class.getName()).warning(
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
            return StreakLoaderTopComponent.getDefault();
        }
    }
    
     private XYSeriesCollection PlotFirstTrace(boolean mode){
        if (mode){
             timeTracesCollection = new XYSeriesCollection();
             XYSeries seria = new XYSeries("TimeTrace");
             for (int j=0; j<data.GetNt()[0]; j++){
                 seria.add(data.GetX()[j], data.GetPsisim()[j]);
             }
            timeTracesCollection.addSeries(seria);
            return timeTracesCollection;         
        }
        else {
            waveTracesCollection = new XYSeriesCollection();
            XYSeries seria = new XYSeries("WaveTrace");
            for (int j=0; j<data.GetNl()[0]; j++){
                seria.add(data.GetX2()[j], data.GetPsisim()[j*data.GetNt()[0]]);
            }
            waveTracesCollection.addSeries(seria);
            return waveTracesCollection;         
            
        }
    }
    
    private void UpdateSelectedTimeTrace(int index){
        timeTracesCollection.getSeries(0).clear();
        for (int j=0; j<data.GetNt()[0]; j++){
            timeTracesCollection.getSeries(0).add(data.GetX()[j], data.GetPsisim()[index*data.GetNt()[0]+j]);
        }
    }
    
    private void UpdateSelectedWaveTrace(int index){
        waveTracesCollection.getSeries(0).clear();
        for (int j=0; j<data.GetNl()[0]; j++){
            waveTracesCollection.getSeries(0).add(data.GetX2()[j], data.GetPsisim()[j*data.GetNt()[0]+index]);
        }
    }
    
    private void MakeTimeTraceChart(XYSeriesCollection dat){   
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
        tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length-1]);
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setSize(jPSelectedTimeTrace.getSize());
        jPSelectedTimeTrace.removeAll();
        jPSelectedTimeTrace.add(chpan);
        jPSelectedTimeTrace.repaint(); 
    }

    private void MakeWaveTraceChart(XYSeriesCollection dat){   
        JFreeChart tracechart;        
        tracechart = ChartFactory.createXYLineChart(
            "Selected Spectrum", 
            "Wavelength (nm)",
            "Number of counts", 
            dat, 
            PlotOrientation.VERTICAL, 
            false, 
            false, 
            false
        );
        if (data.GetX2()[data.GetX2().length-1]<data.GetX2()[0])
            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[0]);
        else 
            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[data.GetX2().length-1]);
        //tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setSize(jPSelectedWaveTrace.getSize());
        jPSelectedWaveTrace.removeAll();
        jPSelectedWaveTrace.add(chpan);
        jPSelectedWaveTrace.repaint(); 
    }
    
    private XYZDataset MakeXYZDataset(){ 
        DefaultXYZDataset dataset2 = new DefaultXYZDataset();
        double[] xValues  = new double[data.GetNl()[0]*data.GetNt()[0]];
        double[] yValues  = new double[data.GetNl()[0]*data.GetNt()[0]];
        
        for (int i = 0; i<data.GetNt()[0]; i++){
            for (int j = 0; j<data.GetNl()[0]; j++){
                xValues[j*data.GetNt()[0]+i] = data.GetX2()[j];
                yValues[j*data.GetNt()[0]+i] = data.GetX()[i];
            }
        }
        double[][] chartdata = {xValues,yValues,data.GetPsisim()};
        dataset2.addSeries("Streak image", chartdata);
        
        dataset = new ColorCodedImageDataset(data.GetNl()[0],data.GetNt()[0],data.GetPsisim(), true);
        return dataset;
    }
    
    private void MakeImageChart(XYZDataset dataset){
        NumberAxis xAxis = new NumberAxis("Wavelengts (nm)");
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
        PaintScale scale = new RainbowPaintScale(data.GetMinInt(), data.GetMaxInt());
        renderer.setPaintScale(scale);
        FastXYPlot plot = new FastXYPlot(dataset, xAxis, yAxis, renderer);        
   
        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);

        chart = new JFreeChart(plot);
        chart.setAntiAlias(false);
        chart.removeLegend();
  
        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(data.GetMinInt(),data.GetMaxInt());
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
    
    public void chartMouseClicked(ChartMouseEvent event) {

        int mouseX = event.getTrigger().getX();
        int mouseY = event.getTrigger().getY();
        Point2D p = this.chpanImage.translateScreenToJava2D(new Point(mouseX, mouseY));
        FastXYPlot plot = (FastXYPlot) this.chart.getPlot();
        ChartRenderingInfo info = this.chpanImage.getChartRenderingInfo();
        Rectangle2D dataArea = info.getPlotInfo().getDataArea();
        
        ValueAxis domainAxis = plot.getDomainAxis();
        RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
        ValueAxis rangeAxis = plot.getRangeAxis();
        RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge();
            
        int chartX =  (int) (domainAxis.java2DToValue(p.getX(), dataArea, domainAxisEdge));
        int chartY =  (int) (rangeAxis.java2DToValue(p.getY(), dataArea, rangeAxisEdge));
        
//        System.out.println(chartX+"    "+chartY);
        
        UpdateSelectedWaveTrace(chartY);
        UpdateSelectedTimeTrace(chartX);
        
    }

    public void chartMouseMoved(ChartMouseEvent event) {
//         System.out.println("ChartMouseMoved");
    }
}
