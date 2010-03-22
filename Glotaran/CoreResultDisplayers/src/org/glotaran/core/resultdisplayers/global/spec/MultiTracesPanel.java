/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MultiTracesPanel.java
 *
 * Created on Mar 18, 2010, 9:40:41 AM
 */
package org.glotaran.core.resultdisplayers.global.spec;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.resultdisplayers.common.panels.CommonTools;
import org.glotaran.core.resultdisplayers.common.panels.RelationFrom;
import org.glotaran.jfreechartcustom.GlotaranDrawingSupplier;
import org.glotaran.jfreechartcustom.GraphPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleAnchor;

/**
 *
 * @author slapten
 */
public class MultiTracesPanel extends javax.swing.JPanel {

    private final static long serialVersionUID = 1L;
    private RelationFrom relation = null;
    private List<TimpResultDataset> resultDatasets = null;
    private GraphPanel spectraImage;
    private Crosshair crosshair;
    private JFreeChart subchartTimeTrace;
    private JFreeChart subchartResidualsTime;
    private TimpResultDataset fromDataset;
    private double[] t0curveFrom = null;
    private ArrayList<double[]> t0curvesTo = new ArrayList<double[]>();

    int numberOfComponents;

    /** Creates new form MultiTracesPanel */
    public MultiTracesPanel() {
        initComponents();
    }

    public MultiTracesPanel(RelationFrom relations, List<TimpResultDataset> results) {
        initComponents();
        relation = relations;
        resultDatasets = results;
        fromDataset = results.get(relations.indexFrom);
        t0curveFrom = CommonTools.calculateDispersionTrace(fromDataset);
        for (int i = 0; i < relation.scaledDatasets.size(); i++){
            t0curvesTo.add(CommonTools.calculateDispersionTrace(results.get(relation.scaledDatasets.get(i).indexTo)));
        }

        numberOfComponents = fromDataset.getJvec() != null ? fromDataset.getJvec().length / 2 : fromDataset.getKineticParameters().length / 2;
        if (fromDataset.getSpectra().getRowDimension() > numberOfComponents * 2) {
            jTBShowChohSpec.setEnabled(true);
        }
        createCroshair();
//create spectra plot form "from dataset"
        jPSpectra.removeAll();
        spectraImage = createSpectraPlot(fromDataset);

        spectraImage.getChart().getXYPlot().getDomainAxis().setLowerMargin(0.0);
        spectraImage.getChart().getXYPlot().getDomainAxis().setUpperMargin(0.0);

        jPSpectra.add(spectraImage);
//create plot with curves from "from dataset";
        updateTrace(0);
        
//initialise slider from "from dataset"
        jSWavelengths.getModel().setRangeProperties(0, 1, 0, fromDataset.getX2().length - 1, true);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPSpectra = new javax.swing.JPanel();
        jPTraces = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jSWavelengths = new javax.swing.JSlider();
        jToolBar1 = new javax.swing.JToolBar();
        jTBLinLog = new javax.swing.JToggleButton();
        jTFLinPart = new javax.swing.JTextField();
        jBUpdLinLog = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jTBShowChohSpec = new javax.swing.JToggleButton();

        setLayout(new java.awt.GridBagLayout());

        jPSpectra.setBackground(new java.awt.Color(255, 255, 255));
        jPSpectra.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        add(jPSpectra, gridBagConstraints);

        jPTraces.setBackground(new java.awt.Color(255, 255, 255));
        jPTraces.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        add(jPTraces, gridBagConstraints);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jSWavelengths.setValue(0);
        jSWavelengths.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSWavelengthsStateChanged(evt);
            }
        });
        jPanel10.add(jSWavelengths, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel10, gridBagConstraints);

        jToolBar1.setRollover(true);

        jTBLinLog.setText(org.openide.util.NbBundle.getMessage(MultiTracesPanel.class, "MultiTracesPanel.jTBLinLog.text")); // NOI18N
        jTBLinLog.setFocusable(false);
        jTBLinLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBLinLog.setMaximumSize(new java.awt.Dimension(59, 21));
        jTBLinLog.setMinimumSize(new java.awt.Dimension(59, 21));
        jTBLinLog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBLinLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBLinLogActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBLinLog);

        jTFLinPart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFLinPart.setText(org.openide.util.NbBundle.getMessage(MultiTracesPanel.class, "MultiTracesPanel.jTFLinPart.text")); // NOI18N
        jTFLinPart.setMaximumSize(new java.awt.Dimension(70, 21));
        jTFLinPart.setMinimumSize(new java.awt.Dimension(4, 21));
        jToolBar1.add(jTFLinPart);

        jBUpdLinLog.setText(org.openide.util.NbBundle.getMessage(MultiTracesPanel.class, "MultiTracesPanel.jBUpdLinLog.text")); // NOI18N
        jBUpdLinLog.setEnabled(false);
        jBUpdLinLog.setFocusable(false);
        jBUpdLinLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBUpdLinLog.setMaximumSize(new java.awt.Dimension(64, 21));
        jBUpdLinLog.setMinimumSize(new java.awt.Dimension(64, 21));
        jBUpdLinLog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBUpdLinLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdLinLogActionPerformed(evt);
            }
        });
        jToolBar1.add(jBUpdLinLog);
        jToolBar1.add(jSeparator1);

        jTBShowChohSpec.setText(org.openide.util.NbBundle.getMessage(MultiTracesPanel.class, "MultiTracesPanel.jTBShowChohSpec.text")); // NOI18N
        jTBShowChohSpec.setEnabled(false);
        jTBShowChohSpec.setFocusable(false);
        jTBShowChohSpec.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTBShowChohSpec.setMaximumSize(new java.awt.Dimension(112, 21));
        jTBShowChohSpec.setMinimumSize(new java.awt.Dimension(112, 21));
        jTBShowChohSpec.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jTBShowChohSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBShowChohSpecActionPerformed(evt);
            }
        });
        jToolBar1.add(jTBShowChohSpec);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        add(jToolBar1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jSWavelengthsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSWavelengthsStateChanged
        crosshair.setValue(fromDataset.getX2()[jSWavelengths.getValue()]);
        updateTrace(jSWavelengths.getValue());
}//GEN-LAST:event_jSWavelengthsStateChanged

    private void jTBLinLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBLinLogActionPerformed
//        if (jTBLinLog.isSelected()){
//            try {
//                updateLinLogPlotSumary();
//            } catch (Exception e) {
//                CoreErrorMessages.updLinLogException();
//                jTBLinLog.setSelected(false);
//            }
//        } else {
//            ChartPanel conc = createLinTimePlot(res.getConcentrations(), res.getX());
//            ChartPanel lsv =createLinTimePlot(leftSingVec, res.getX());
//            lsv.getChart().setTitle("Left singular vectors");
//            lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));
//            jPConcentrations.removeAll();
//            //            conc.setSize(jPConcentrations.getSize());
//            jPConcentrations.add(conc);
//            jPConcentrations.validate();
//            jPLeftSingVectors.removeAll();
//            //            lsv.setSize(jPLeftSingVectors.getSize());
//            jPLeftSingVectors.add(lsv);
//            jPLeftSingVectors.validate();
//            jBUpdLinLog.setEnabled(false);
//        }
}//GEN-LAST:event_jTBLinLogActionPerformed

    private void jBUpdLinLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdLinLogActionPerformed
//        try {
//            updateLinLogPlotSumary();
//        } catch (Exception e) {
//            CoreErrorMessages.updLinLogException();
//        }
}//GEN-LAST:event_jBUpdLinLogActionPerformed

    private void jTBShowChohSpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBShowChohSpecActionPerformed
        jPSpectra.removeAll();
        spectraImage = createSpectraPlot(fromDataset);
        jPSpectra.add(spectraImage);
        jPSpectra.validate();
}//GEN-LAST:event_jTBShowChohSpecActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBUpdLinLog;
    private javax.swing.JPanel jPSpectra;
    private javax.swing.JPanel jPTraces;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JSlider jSWavelengths;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToggleButton jTBLinLog;
    private javax.swing.JToggleButton jTBShowChohSpec;
    private javax.swing.JTextField jTFLinPart;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    private GraphPanel createSpectraPlot(TimpResultDataset dataset) {
        String specName = dataset.getJvec() != null ? "SAS" : "EAS";
        boolean errorBars = dataset.getSpectraErr() != null ? true : false;
        int numComp = jTBShowChohSpec.isSelected() ? numberOfComponents + 1 : numberOfComponents;

        YIntervalSeriesCollection realSasCollection = new YIntervalSeriesCollection();
        YIntervalSeries seria;

//        XYSeries seria;
//create collection of sas(eas)
        for (int j = 0; j < numComp; j++) {
            seria = new YIntervalSeries(specName + (j + 1));// new XYSeries(specName + (j + 1));
            for (int i = 0; i < dataset.getX2().length; i++) {
                if (errorBars) {
                    seria.add(dataset.getX2()[i], dataset.getSpectra().get(j, i),
                            dataset.getSpectra().get(j, i) - dataset.getSpectraErr().get(j, i),
                            dataset.getSpectra().get(j, i) + dataset.getSpectraErr().get(j, i));
                } else {
                    seria.add(dataset.getX2()[i], dataset.getSpectra().get(j, i),
                            dataset.getSpectra().get(j, i),
                            dataset.getSpectra().get(j, i));
                }
            }
            realSasCollection.addSeries(seria);
        }
        GraphPanel chartPanel = CommonTools.createGraphPanel(realSasCollection, specName, "Wavelength (nm)", errorBars, dataset.getX2()[dataset.getX2().length - 1]);
        CrosshairOverlay overlay = new CrosshairOverlay();
        overlay.addDomainCrosshair(crosshair);
        chartPanel.addOverlay(overlay);
        return chartPanel;
    }

    public void createCroshair() {
        crosshair = new Crosshair(fromDataset.getX2()[jSWavelengths.getValue()]);
        crosshair.setPaint(Color.red);
        crosshair.setLabelVisible(true);
        crosshair.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
    }

    private ChartPanel createTraceResidChart(TimpResultDataset res) {

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
                false);
        subchartTimeTrace = ChartFactory.createXYLineChart(
                null,
                null,
                null,
                dataset1,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);

        subchartTimeTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);
        subchartResidualsTime.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);

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
        xAxis.setUpperBound(res.getX()[res.getX().length - 1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        tracechart.getLegend().setVisible(false);
        ChartPanel chpan = new ChartPanel(tracechart, true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        return chpan;
    }

    private void updateTrace(int xIndex) {
        if (true) {
            XYSeriesCollection trace = CommonTools.createFitRawTraceCollection(xIndex, 0, fromDataset.getX().length, fromDataset, t0curveFrom[xIndex],"From");
            XYSeriesCollection resid = CommonTools.createResidTraceCollection(xIndex, 0, fromDataset.getX().length, fromDataset, t0curveFrom[xIndex],"From");

            XYSeriesCollection traceTo = null;
            XYSeriesCollection residTo = null;
            TimpResultDataset toDataset = null;
            int indexTo = 0;
            double toValue = 1;
            for (int i = 0; i < relation.scaledDatasets.size(); i++){
                indexTo = relation.scaledDatasets.get(i).indexTo;
                toDataset = resultDatasets.get(indexTo);
                toValue = relation.scaledDatasets.get(i).valueTo;
                traceTo = CommonTools.createFitRawTraceCollection(xIndex, 0, toDataset.getX().length,toDataset, t0curvesTo.get(i)[xIndex], ("to"+i), toValue);
                residTo = CommonTools.createResidTraceCollection(xIndex, 0, toDataset.getX().length,toDataset, t0curvesTo.get(i)[xIndex], ("to"+i), toValue);
                trace.addSeries(traceTo.getSeries(0));
                trace.addSeries(traceTo.getSeries(1));
                resid.addSeries(residTo.getSeries(0));
            }

            ChartPanel linTime = CommonTools.makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), null, true);

            jPTraces.removeAll();
            jPTraces.add(linTime);
            jPTraces.validate();
        } else {
//            ChartPanel linLogTime = makeLinLogTimeTraceResidChart(xIndex);
//            jPSelectedTimeTrace.removeAll();
//            jPSelectedTimeTrace.add(linLogTime);
//            jPSelectedTimeTrace.validate();
//            if (leftSingVecPart!=null){
//                double linPortion = Double.valueOf(jTFLinPartTraces.getText());
//                ChartPanel lsv = createLinLogTimePlot(t0Curve[0], linPortion, leftSingVecPart, timePart);
//                lsv.getChart().setTitle("Left singular vectors");
//                lsv.getChart().getTitle().setFont(new Font(lsv.getChart().getTitle().getFont().getFontName(), Font.PLAIN, 12));
//                jPLeftSingVectorsPart.removeAll();
//                jPLeftSingVectorsPart.add(lsv);
//                jPLeftSingVectorsPart.validate();
//            }
        }

    }
}