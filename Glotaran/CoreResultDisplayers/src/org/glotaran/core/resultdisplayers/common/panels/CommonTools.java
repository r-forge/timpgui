/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.resultdisplayers.common.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import static java.lang.Math.ceil;

/**
 *
 * @author slapten
 */
public class CommonTools {
    private static final int CHART_SIZE = 200;
    private static final int LAYOUT_GAP = 2;
    private static final int REPORT_PANEL_DEFAULT_WIDTH = 900;
    private static final int REPORT_PANEL_DEFAULT_HEIGHT = 810;

    public static void checkPanelSize(JPanel panelToResize, int numSelTraces) {
        int rowNum = (int)ceil((double)numSelTraces/4);
        if (rowNum > 4){
            panelToResize.setPreferredSize(new Dimension(REPORT_PANEL_DEFAULT_WIDTH,rowNum*CHART_SIZE+LAYOUT_GAP*(rowNum+1)));
        }
        GridLayout gl = (GridLayout)panelToResize.getLayout();
        if (numSelTraces/4>=gl.getRows()){
            panelToResize.setLayout(new GridLayout(rowNum, 4,LAYOUT_GAP,LAYOUT_GAP));
        }
    }

    public static void restorePanelSize(JPanel panelToResize) {
        panelToResize.setPreferredSize(new Dimension(REPORT_PANEL_DEFAULT_WIDTH, REPORT_PANEL_DEFAULT_HEIGHT));
        panelToResize.setLayout(new GridLayout(2, 2));
    }

    public static ChartPanel makeLinTimeTraceResidChart(XYSeriesCollection trace, XYSeriesCollection residuals, ValueAxis xAxis, String name){
        JFreeChart subchartResiduals = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            residuals,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        JFreeChart subchartTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            trace,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );

        XYPlot plot1_1 = subchartTrace.getXYPlot();
        plot1_1.getDomainAxis().setLowerMargin(0.0);
        plot1_1.getDomainAxis().setUpperMargin(0.0);
        plot1_1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_1.getDomainAxis().setInverted(true);
        plot1_1.setRangeZeroBaselineVisible(true);

        XYPlot plot1_2 = subchartResiduals.getXYPlot();
        plot1_2.getDomainAxis().setLowerMargin(0.0);
        plot1_2.getDomainAxis().setUpperMargin(0.0);
        plot1_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_2.getDomainAxis().setInverted(true);
        plot1_2.setRangeZeroBaselineVisible(true);

        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(5.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);
        Font titleFont = new Font(JFreeChart.DEFAULT_TITLE_FONT.getFontName(),JFreeChart.DEFAULT_TITLE_FONT.getStyle(),12);
        JFreeChart tracechart = new JFreeChart(name, titleFont, plot, true);
        tracechart.getLegend().setVisible(false);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        return chpan;
    }

//    private XYSeriesCollection createFitRawTraceCollection(int xIndex, int startInd, int stopInd){
//
//        XYSeriesCollection trace = new XYSeriesCollection();
//        XYSeries series1 = new XYSeries("Trace");
//        XYSeries series2 = new XYSeries("Fit");
//
//        for (int j = startInd; j < stopInd; j++) {
//            series1.add(res.getX()[j], res.getTraces().get(j, xIndex));
//            series2.add(res.getX()[j], res.getFittedTraces().get(j, xIndex));
//        }
//        trace.addSeries(series1);
//        trace.addSeries(series2);
//        return trace;
//    }
//
//    private XYSeriesCollection createResidTraceCollection(int xIndex, int startInd, int stopInd){
//        XYSeries series3 = new XYSeries("Residuals");
//         for (int j = startInd; j < stopInd; j++) {
//            series3.add(res.getX()[j], res.getTraces().get(j, xIndex)-res.getFittedTraces().get(j, xIndex));
//        }
//        return new XYSeriesCollection(series3);
//    }
}
