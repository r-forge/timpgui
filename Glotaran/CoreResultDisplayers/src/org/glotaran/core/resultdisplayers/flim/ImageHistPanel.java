/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImageHistPanel.java
 *
 * Created on Sep 3, 2009, 3:53:09 PM
 */

package org.glotaran.core.resultdisplayers.flim;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.jfreechartcustom.GraphPanel;
import org.glotaran.jfreechartcustom.ImageUtilities;
import org.glotaran.jfreechartcustom.IntensImageDataset;
import org.glotaran.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDataImageAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author slk230
 */
public class ImageHistPanel extends javax.swing.JPanel {

    private int imHeight,imWidth;
    private double minValue, maxValue;
    private double[] dataValues;
    private int[] selImInd;
    private int numChHist;
    private ChartMouseListener listener;

    /** Creates new form ImageHistPanel */
    public ImageHistPanel() {
        initComponents();
    }

    public ImageHistPanel(String name, double[] data, int[] selInd, int height, int width, double minVal, double maxVal, ChartMouseListener listen) {
        initComponents();
        this.dataValues = data;
        this.selImInd = selInd;
        this.imHeight = height;
        this.imWidth = width;
        this.minValue = minVal;
        this.maxValue = maxVal;
        this.jLName.setText(name);
        this.numChHist = 20;
        this.listener = listen;

        jTFChNumHist.setText(String.valueOf(numChHist));
        jTFMaxValue.setText(String.valueOf(maxValue));
        jTFMinValue.setText(String.valueOf(minValue));

        jPHist.add(updateHistPanel(data, minValue, maxValue, numChHist));

        IntensImageDataset dataValuesDataset = new IntensImageDataset(imHeight,imWidth,new double[imWidth*imHeight]);
        for (int i = 0; i < dataValues.length; i++){
            dataValuesDataset.SetValue(selImInd[i], dataValues[i]);
        }
        PaintScale ps = new RainbowPaintScale(0.001, maxValue);
        JFreeChart aveLifetimeChart = createScatChart(ImageUtilities.createColorCodedImage(dataValuesDataset, ps), ps,imWidth,imHeight);
        ChartPanel aveLifetimePanel = new ChartPanel(aveLifetimeChart);
        aveLifetimePanel.setFillZoomRectangle(true);
        aveLifetimePanel.setMouseWheelEnabled(true);
        aveLifetimePanel.addChartMouseListener(listener);
        jPImage.add(aveLifetimePanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jPHist = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTFChNumHist = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPImage = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTFMaxValue = new javax.swing.JTextField();
        jTFMinValue = new javax.swing.JTextField();
        jLName = new javax.swing.JLabel();

        jPHist.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLabel3.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLabel15.text")); // NOI18N

        jButton5.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jButton5.text")); // NOI18N
        jButton5.setIconTextGap(2);
        jButton5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTFChNumHist.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFChNumHist.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jTFChNumHist.text")); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(jTFChNumHist, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel15)
                .addComponent(jTFChNumHist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton5))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(jPHist, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPHist, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPImage.setBackground(new java.awt.Color(0, 0, 0));
        jPImage.setMaximumSize(new java.awt.Dimension(450, 350));
        jPImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPImageMouseClicked(evt);
            }
        });
        jPImage.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLabel8.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLabel9.text")); // NOI18N

        jButton2.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jButton2.text")); // NOI18N
        jButton2.setIconTextGap(2);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTFMaxValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMaxValue.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jTFMaxValue.text")); // NOI18N

        jTFMinValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTFMinValue.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jTFMinValue.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(11, 11, 11)
                .addComponent(jTFMinValue, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFMaxValue, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel8)
                .addComponent(jTFMinValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton2)
                .addComponent(jTFMaxValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 285, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPImage, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLName.setText(org.openide.util.NbBundle.getMessage(ImageHistPanel.class, "ImageHistPanel.jLName.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLName, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLName, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPImageMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_jPImageMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        double newMinLifetime, newMaxLifetime;
        try {
            newMinLifetime = Double.parseDouble(jTFMinValue.getText());
            newMaxLifetime = Double.parseDouble(jTFMaxValue.getText());

            IntensImageDataset dataValuesDataset = new IntensImageDataset(imHeight,imWidth,new double[imWidth*imHeight]);
            for (int i = 0; i < dataValues.length; i++){
                dataValuesDataset.SetValue(selImInd[i], dataValues[i]);
            }
            PaintScale ps = new RainbowPaintScale(newMinLifetime, newMaxLifetime);
            JFreeChart aveLifetimeChart = createScatChart(ImageUtilities.createColorCodedImage(dataValuesDataset, ps), ps,imWidth,imHeight);
            aveLifetimeChart.getXYPlot().getRangeAxis().setInverted(true);
            ChartPanel aveLifetimePanel = new ChartPanel(aveLifetimeChart);
            aveLifetimePanel.setFillZoomRectangle(true);
            aveLifetimePanel.setMouseWheelEnabled(true);
            jPImage.removeAll();
            aveLifetimePanel.setSize(jPImage.getSize());
            aveLifetimePanel.addChartMouseListener(listener);
            jPImage.add(aveLifetimePanel);
            jPImage.repaint();
        }catch(NumberFormatException ex) {
            CoreErrorMessages.selCorrChNum();
        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int newNumChHish;
        try {
            newNumChHish = Integer.parseInt(jTFChNumHist.getText());
            jPHist.removeAll();
            ChartPanel chpanHist = updateHistPanel(dataValues, minValue, maxValue, newNumChHish);
            chpanHist.setSize(jPHist.getSize());
            jPHist.add(chpanHist);
            jPHist.repaint();
        }catch(NumberFormatException ex) {
            CoreErrorMessages.selCorrChNum();
        }
}//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPHist;
    private javax.swing.JPanel jPImage;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jTFChNumHist;
    private javax.swing.JTextField jTFMaxValue;
    private javax.swing.JTextField jTFMinValue;
    // End of variables declaration//GEN-END:variables

    private ChartPanel updateHistPanel(double[] data, double minVal, double maxVal, int numPockets) {
        HistogramDataset datasetHist = new HistogramDataset();
        if(numPockets < 1)
            numPockets = 20;
        datasetHist.addSeries("seria1", data, numPockets, minVal, maxVal);
        JFreeChart charthist = ChartFactory.createHistogram(
            null,
            null,
            null,
            datasetHist,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );
        charthist.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        XYPlot plot = (XYPlot) charthist.getPlot();
        plot.setForegroundAlpha(0.85f);
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        return new GraphPanel(charthist);
    }

    private JFreeChart createScatChart(BufferedImage image, PaintScale ps, int plotWidth, int plotHeigh){
        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, new XYSeriesCollection(), PlotOrientation.VERTICAL, false, false,
                false);
        chart_temp.getXYPlot().getRangeAxis().setInverted(true);
        chart_temp.setBackgroundPaint(JFreeChart.DEFAULT_BACKGROUND_PAINT);
        XYDataImageAnnotation ann = new XYDataImageAnnotation(image, 0,0,
                plotWidth, plotHeigh, true);
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

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(ps.getLowerBound(),ps.getUpperBound());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(ps,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(10);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart_temp.getBackgroundPaint());
        chart_temp.addSubtitle(legend);

        return chart_temp;
    }

    public void chartMouseClicked(ChartMouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void chartMouseMoved(ChartMouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}