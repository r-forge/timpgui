/*
 *
 * THIS FILE IS OLD AND NOT USED ANYMORE
 * 
 */

package org.timpgui.dataeditors;

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
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import nl.vu.nat.jfreechartcustom.XYBlockRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.util.NbBundle;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.DatasetTimp;



/**
 * Top component which displays something.
 */
final public class SpecEditorTopComponent extends CloneableTopComponent implements ChartMouseListener {

    private static SpecEditorTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SpecEditorTopComponent";
    
    private JFreeChart chart;
    private ChartPanel chpanImage;
    private XYSeriesCollection timeTracesCollection, waveTracesCollection;
    private DatasetTimp data;
    private ColorCodedImageDataset dataset;

    public SpecEditorTopComponent() {
        data = new DatasetTimp();
        initComponents();
        setName(NbBundle.getMessage(SpecEditorTopComponent.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopComponent.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

    public SpecEditorTopComponent(String filename) {
        initComponents();
        setName(NbBundle.getMessage(SpecEditorTopComponent.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopComponent.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
        data = new DatasetTimp();
        try {
            data.loadASCIIFile(new File(filename));
            MakeImageChart(MakeXYZDataset());
            MakeWaveTraceChart(PlotFirstTrace(false));
            MakeTimeTraceChart(PlotFirstTrace(true));
            chpanImage = new ChartPanel(chart,true);
            jPStreakImage.removeAll();
            chpanImage.setSize(jPStreakImage.getMaximumSize());
            chpanImage.addChartMouseListener(this);

            jPStreakImage.add(chpanImage);
            jPStreakImage.repaint();
            jLTimsteps.setText(Integer.toString(data.getNt()[0]));
            jLWavewind.setText(Double.toString(data.getX2()[data.getX2().length-1]-data.getX2()[0]));
            jLTimeWind.setText(Double.toString(data.getX()[data.getX().length-1]-data.getX()[0]));
            jLWavesteps.setText(Integer.toString(data.getNl()[0]));


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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel_Dataset = new javax.swing.JPanel();
        jPanel_2RegionSelect = new javax.swing.JPanel();
        jPStreakImage = new javax.swing.JPanel();
        jPSelectedTimeTrace = new javax.swing.JPanel();
        jPSelectedWaveTrace = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTFDatasetName = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLTimeWind = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLWavewind = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLTimsteps = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLWavesteps = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jBSaveIvoFile = new javax.swing.JButton();
        jBMakeDataset = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(800, 600));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel_Dataset.setLayout(new java.awt.GridBagLayout());

        jPanel_2RegionSelect.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_2RegionSelect.setMaximumSize(new java.awt.Dimension(820, 400));
        jPanel_2RegionSelect.setMinimumSize(new java.awt.Dimension(820, 400));
        jPanel_2RegionSelect.setPreferredSize(new java.awt.Dimension(820, 400));
        jPanel_2RegionSelect.setLayout(new java.awt.GridBagLayout());

        jPStreakImage.setBackground(new java.awt.Color(0, 0, 0));
        jPStreakImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPStreakImage.setMaximumSize(new java.awt.Dimension(420, 380));
        jPStreakImage.setMinimumSize(new java.awt.Dimension(420, 380));
        jPStreakImage.setPreferredSize(new java.awt.Dimension(420, 380));
        jPStreakImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPStreakImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPStreakImageLayout = new javax.swing.GroupLayout(jPStreakImage);
        jPStreakImage.setLayout(jPStreakImageLayout);
        jPStreakImageLayout.setHorizontalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        jPStreakImageLayout.setVerticalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel_2RegionSelect.add(jPStreakImage, gridBagConstraints);

        jPSelectedTimeTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTimeTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedTimeTrace.setMaximumSize(new java.awt.Dimension(366, 185));
        jPSelectedTimeTrace.setMinimumSize(new java.awt.Dimension(366, 185));
        jPSelectedTimeTrace.setPreferredSize(new java.awt.Dimension(366, 185));

        javax.swing.GroupLayout jPSelectedTimeTraceLayout = new javax.swing.GroupLayout(jPSelectedTimeTrace);
        jPSelectedTimeTrace.setLayout(jPSelectedTimeTraceLayout);
        jPSelectedTimeTraceLayout.setHorizontalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );
        jPSelectedTimeTraceLayout.setVerticalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel_2RegionSelect.add(jPSelectedTimeTrace, gridBagConstraints);

        jPSelectedWaveTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedWaveTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSelectedWaveTrace.setMaximumSize(new java.awt.Dimension(366, 185));
        jPSelectedWaveTrace.setMinimumSize(new java.awt.Dimension(366, 185));
        jPSelectedWaveTrace.setPreferredSize(new java.awt.Dimension(366, 185));

        javax.swing.GroupLayout jPSelectedWaveTraceLayout = new javax.swing.GroupLayout(jPSelectedWaveTrace);
        jPSelectedWaveTrace.setLayout(jPSelectedWaveTraceLayout);
        jPSelectedWaveTraceLayout.setHorizontalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );
        jPSelectedWaveTraceLayout.setVerticalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 181, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel_2RegionSelect.add(jPSelectedWaveTrace, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel_Dataset.add(jPanel_2RegionSelect, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setMaximumSize(new java.awt.Dimension(400, 120));
        jPanel4.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 120));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel14.setMaximumSize(new java.awt.Dimension(32767, 30));
        jPanel14.setMinimumSize(new java.awt.Dimension(100, 30));
        jPanel14.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel14.setLayout(null);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel5.text")); // NOI18N
        jPanel14.add(jLabel5);
        jLabel5.setBounds(10, 10, 87, 15);

        jTFDatasetName.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jTFDatasetName.text")); // NOI18N
        jPanel14.add(jTFDatasetName);
        jTFDatasetName.setBounds(110, 10, 200, 19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel14, gridBagConstraints);

        jPanel13.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel7.text")); // NOI18N
        jPanel15.add(jLabel7);

        org.openide.awt.Mnemonics.setLocalizedText(jLTimeWind, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLTimeWind.text")); // NOI18N
        jPanel15.add(jLTimeWind);

        jPanel13.add(jPanel15, new java.awt.GridBagConstraints());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel8.text")); // NOI18N
        jPanel16.add(jLabel8);

        org.openide.awt.Mnemonics.setLocalizedText(jLWavewind, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLWavewind.text")); // NOI18N
        jPanel16.add(jLWavewind);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel13.add(jPanel16, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel6.text")); // NOI18N
        jPanel17.add(jLabel6);

        org.openide.awt.Mnemonics.setLocalizedText(jLTimsteps, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLTimsteps.text")); // NOI18N
        jPanel17.add(jLTimsteps);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel13.add(jPanel17, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel9.text")); // NOI18N
        jPanel18.add(jLabel9);

        org.openide.awt.Mnemonics.setLocalizedText(jLWavesteps, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLWavesteps.text")); // NOI18N
        jPanel18.add(jLWavesteps);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel13.add(jPanel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel_Dataset.add(jPanel4, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(null);

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jBSaveIvoFile.text")); // NOI18N
        jBSaveIvoFile.setEnabled(false);
        jPanel6.add(jBSaveIvoFile);
        jBSaveIvoFile.setBounds(180, 20, 128, 25);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jPanel6.add(jBMakeDataset);
        jBMakeDataset.setBounds(30, 20, 126, 25);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel_Dataset.add(jPanel6, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel19.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 3, 2, 3);
        jPanel19.add(jLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 3, 2, 3);
        jPanel19.add(jLabel3, gridBagConstraints);

        jTextField2.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jTextField2.text")); // NOI18N
        jTextField2.setEnabled(false);
        jTextField2.setMinimumSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        jPanel19.add(jTextField2, gridBagConstraints);

        jTextField3.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jTextField3.text")); // NOI18N
        jTextField3.setEnabled(false);
        jTextField3.setMinimumSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        jPanel19.add(jTextField3, gridBagConstraints);

        jTextField4.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jTextField4.text")); // NOI18N
        jTextField4.setEnabled(false);
        jTextField4.setMinimumSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        jPanel19.add(jTextField4, gridBagConstraints);

        jTextField5.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jTextField5.text")); // NOI18N
        jTextField5.setEnabled(false);
        jTextField5.setMinimumSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        jPanel19.add(jTextField5, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 3);
        jPanel19.add(jButton1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(SpecEditorTopComponent.class, "SpecEditorTopComponent.jButton2.text")); // NOI18N
        jButton2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 3);
        jPanel19.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel_Dataset.add(jPanel19, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel_Dataset);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed
// TODO add your handling code here:
    //TimpController.RegisterData(data);
}//GEN-LAST:event_jBMakeDatasetActionPerformed

private void jPStreakImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPStreakImageMouseClicked
// TODO add your han   dling code here:
}//GEN-LAST:event_jPStreakImageMouseClicked

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLTimeWind;
    private javax.swing.JLabel jLTimsteps;
    private javax.swing.JLabel jLWavesteps;
    private javax.swing.JLabel jLWavewind;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPSelectedTimeTrace;
    private javax.swing.JPanel jPSelectedWaveTrace;
    private javax.swing.JPanel jPStreakImage;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_2RegionSelect;
    private javax.swing.JPanel jPanel_Dataset;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFDatasetName;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized SpecEditorTopComponent getDefault() {
        if (instance == null) {
            instance = new SpecEditorTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the StreakLoaderTopComponent instance. Never call {@link #getDefault} directly!
     */

    public static synchronized SpecEditorTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SpecEditorTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SpecEditorTopComponent) {
            return (SpecEditorTopComponent) win;
        }
        Logger.getLogger(SpecEditorTopComponent.class.getName()).warning(
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
            return SpecEditorTopComponent.getDefault();
        }
    }
    
     private XYSeriesCollection PlotFirstTrace(boolean mode){
        if (mode){
             timeTracesCollection = new XYSeriesCollection();
             XYSeries seria = new XYSeries("TimeTrace");
             for (int j=0; j<data.getNt()[0]; j++){
                 seria.add(data.getX()[j], data.getPsisim()[j]);
             }
            timeTracesCollection.addSeries(seria);
            return timeTracesCollection;         
        }
        else {
            waveTracesCollection = new XYSeriesCollection();
            XYSeries seria = new XYSeries("WaveTrace");
            for (int j=0; j<data.getNl()[0]; j++){
                seria.add(data.getX2()[j], data.getPsisim()[j*data.getNt()[0]]);
            }
            waveTracesCollection.addSeries(seria);
            return waveTracesCollection;         
            
        }
    }
    
    private void UpdateSelectedTimeTrace(int index){
        timeTracesCollection.getSeries(0).clear();
        for (int j=0; j<data.getNt()[0]; j++){
            timeTracesCollection.getSeries(0).add(data.getX()[j], data.getPsisim()[index*data.getNt()[0]+j]);
        }
    }
    
    private void UpdateSelectedWaveTrace(int index){
        waveTracesCollection.getSeries(0).clear();
        for (int j=0; j<data.getNl()[0]; j++){
            waveTracesCollection.getSeries(0).add(data.getX2()[j], data.getPsisim()[j*data.getNt()[0]+index]);
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
        tracechart.getXYPlot().getDomainAxis().setUpperBound(data.getX()[data.getX().length-1]);
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setSize(jPSelectedTimeTrace.getMaximumSize());
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
        if (data.getX2()[data.getX2().length-1]<data.getX2()[0])
            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.getX2()[0]);
        else 
            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.getX2()[data.getX2().length-1]);
        //tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setSize(jPSelectedWaveTrace.getMaximumSize());
        jPSelectedWaveTrace.removeAll();
        jPSelectedWaveTrace.add(chpan);
        jPSelectedWaveTrace.repaint(); 
    }
    
    private XYZDataset MakeXYZDataset(){ 
        DefaultXYZDataset dataset2 = new DefaultXYZDataset();
        double[] xValues  = new double[data.getNl()[0]*data.getNt()[0]];
        double[] yValues  = new double[data.getNl()[0]*data.getNt()[0]];
        
        for (int i = 0; i<data.getNt()[0]; i++){
            for (int j = 0; j<data.getNl()[0]; j++){
                xValues[j*data.getNt()[0]+i] = data.getX2()[j];
                yValues[j*data.getNt()[0]+i] = data.getX()[i];
            }
        }
        double[][] chartdata = {xValues,yValues,data.getPsisim()};
        dataset2.addSeries("Image", chartdata);
        
        dataset = new ColorCodedImageDataset(data.getNl()[0],data.getNt()[0],
                data.getPsisim(), data.getX2(), data.getX(), true);
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
        PaintScale scale = new RainbowPaintScale(data.getMinInt(), data.getMaxInt());
        renderer.setPaintScale(scale);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);        
   
        plot.setDomainCrosshairVisible(false);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(false);
        plot.setRangeCrosshairLockedOnData(false);

        chart = new JFreeChart(plot);
        chart.setAntiAlias(false);
        chart.removeLegend();
  
        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(data.getMinInt(),data.getMaxInt());
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
        XYPlot plot = (XYPlot) this.chart.getPlot();
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
