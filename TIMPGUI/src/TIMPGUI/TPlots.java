/*
 * TPlots.java
 *
 * Created on September 20, 2007, 9:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package TIMPGUI;

//~--- non-JDK imports --------------------------------------------------------
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 *
 * @author Joris
 */
public class TPlots {

    public static TDatasets datasets = new TDatasets();

    /**
     * Creates a new instance of TPlots
     */
    public TPlots() {
        datasets = new TDatasets();
    }

    public XYZDataset createXYZDataset(String name) {
        XYZDataset completeDataset = datasets.createDatasetXYZ(name);

        return completeDataset;
    }

    public static void outputGraphics() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Joris", 75);
        dataset.setValue("Kate", 20);
        dataset.setValue("Ivo", 5);

        // create a chart...
        JFreeChart chart = ChartFactory.createPieChart("TIMPGUI Development Pie Chart", dataset, true, // legend?
                true, // tooltips?
                false // URLs?
                );

        // add Chart to preview window...
        preview2DChart(chart);
    }

    // String datasetLabel, String xLabel, String x2Label
    public static JPanel outputGraphics2(String name) {
        XYZDataset completeDataset = datasets.createDatasetXYZ(name);

        return XYBlockChartDemo1.createCustomPanel(completeDataset);
    }

    public static void outputGraphics3(String name, int value) {
        XYZDataset completeDataset = datasets.createDatasetXYZ(name);
        XYDataset dataset = datasets.selectRowFromDatasetXYZ(completeDataset, value);

        completeDataset.getSeriesCount();

        JFreeChart chart = createLinLogChart(dataset);

        preview2DChart(chart);
    }

    public static JPanel outputGraphics4(String name) {

        // XYZDataset completeDataset = datasets.createDatasetXYZ(name);
        // return Custom3DLinePlot.createDemoPanel(completeDataset);
        XYDataset completeDataset = datasets.createConcentrations(name);

        return CustomLinePlot.createDemoPanel(completeDataset);
    }

    public static JPanel outputGraphics5(String name) {
        XYDataset completeDataset = datasets.createSpectra(name);

        return CustomLinePlot.createDemoPanel(completeDataset);
    }

//  public static JPanel outputGraphics6(String name) {
//     XYDataset completeDataset = datasets.createSpectra(name);
//     return HideSeriesDemo1.createDemoPanel(completeDataset);  
//    } 
    private static JFreeChart createBlockChart(XYZDataset dataset) {
        NumberAxis xAxis = new NumberAxis("X");

        // change this to linear log axis
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // xAxis.setStandardTickUnits(NumberAxis.cre)
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        xAxis.setAxisLinePaint(Color.white);
        xAxis.setTickMarkPaint(Color.white);

        NumberAxis yAxis = new NumberAxis("X2");

        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        yAxis.setAxisLinePaint(Color.white);
        yAxis.setTickMarkPaint(Color.white);

        XYBlockRenderer renderer = new XYBlockRenderer();
        PaintScale scale = new GrayPaintScale(-2.0, 1.0);

        renderer.setPaintScale(scale);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        plot.setOutlinePaint(Color.blue);

        JFreeChart chart = new JFreeChart("psi.df", plot);

        chart.removeLegend();

        // TODO: analyze this piece of code
        NumberAxis scaleAxis = new NumberAxis("Scale");

        scaleAxis.setAxisLinePaint(Color.white);
        scaleAxis.setTickMarkPaint(Color.white);
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 7));

//      PaintScaleLegend legend = new PaintScaleLegend(new GrayPaintScale(-2.0,1.0),
//              scaleAxis);
//      legend.setAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
//      legend.setAxisOffset(5.0);
//      legend.setMargin(new RectangleInsets(5, 5, 5, 5));
//      legend.setFrame(new BlockBorder(Color.red));
//      legend.setPadding(new RectangleInsets(10, 10, 10, 10));
//      legend.setStripWidth(10);
//      legend.setPosition(RectangleEdge.RIGHT);
//      legend.setBackgroundPaint(new Color(120, 120, 180));
//      chart.addSubtitle(legend);
        chart.setBackgroundPaint(new Color(180, 180, 250));

        return chart;
    }

    private static JFreeChart createLinLogChart(XYDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart("Lin-Log Chart to be ...", // chart title
                "X", // x axis label
                "Y", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
                );
        XYPlot plot = (XYPlot) chart.getPlot();

        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();

        renderer.setLegendLine(new Rectangle2D.Double(-4.0, -3.0, 8.0, 6.0));

        return chart;
    }

    private static void preview3DChart(JFreeChart chart) {
        ChartPanel p = new ChartPanel(chart);

        TIMPGUI.TIMPInterface.jPanel9.removeAll();    // TODO: replace with more elegant method
        p.setPreferredSize(new Dimension(480, 360));
        TIMPGUI.TIMPInterface.jPanel9.add(p);

    // p.setVisible(true);
    }

    private static void preview2DChart(JFreeChart chart) {
        ChartPanel p = new ChartPanel(chart);

        TIMPGUI.TIMPInterface.jPanel10.removeAll();    // TODO: replace with more elegant method
        p.setPreferredSize(new Dimension(480, 360));
        TIMPGUI.TIMPInterface.jPanel10.add(p);

    // p.setVisible(true);
    }

    static void plot3DPreview(String name) {
        XYZDataset completeDataset = datasets.createDatasetXYZ(name);

        preview3DChart(createBlockChart(completeDataset));
    }

    static void plot2DPreview(String name) {

//      XYZDataset completeDataset = datasets.createDatasetXYZ(name);
//      XYDataset dataset = datasets.selectRowFromDatasetXYZ(
//                      completeDataset,value);
//              completeDataset.getSeriesCount();
//              preview2DChart(createLinLogChart(dataset));
    }
}
