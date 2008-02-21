/* -------------------
 * Custom3DLinePlot.java
 * -------------------
 * (C) Copyright 2003-2006, by Object Refinery Limited.
 *
 */

package TIMPGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.NumberCellRenderer;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;

/**
 * An example of a crosshair being controlled by an external UI component.
 */
public class Custom3DLinePlot extends ApplicationFrame {

    private static class DemoPanel extends JPanel 
                                   implements ChangeListener, 
                                              ChartProgressListener {
    
        private XYZDataset data;
        
        private XYSeries series;
        
        private ChartPanel chartPanel;
        
        private DemoTableModel model;
        
        private JFreeChart chart;
        
        private JSlider slider;

        /**
         * Creates a new demo panel.
         */
        public DemoPanel() {
            super(new BorderLayout());
            this.chart = createChart();
            this.chart.addProgressListener(this);
            this.chartPanel = new ChartPanel(this.chart);
            this.chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
            this.chartPanel.setDomainZoomable(true);
            this.chartPanel.setRangeZoomable(true);
            Border border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4, 4, 4, 4),
                    BorderFactory.createEtchedBorder());
            this.chartPanel.setBorder(border);
            add(this.chartPanel);
            
            JPanel dashboard = new JPanel(new BorderLayout());
            dashboard.setPreferredSize(new Dimension(400, 60));
            dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
            
            this.model = new DemoTableModel(3);
            XYPlot plot = (XYPlot) this.chart.getPlot();
            //this.model.setValueAt(plot.getDataset().getSeriesKey(0), 0, 0);
            this.model.setValueAt(plot.getDataset().getSeriesKey(0), 0, 0);
            this.model.setValueAt(new Double("0.00"), 0, 1);
            this.model.setValueAt(new Double("0.00"), 0, 2);
            this.model.setValueAt(new Double("0.00"), 0, 3);
            this.model.setValueAt(new Double("0.00"), 0, 4);
            this.model.setValueAt(new Double("0.00"), 0, 5);
            this.model.setValueAt(new Double("0.00"), 0, 6);
            JTable table = new JTable(this.model);
            TableCellRenderer renderer1 = new NumberCellRenderer();
            //TableCellRenderer renderer2 = new NumberCellRenderer();
            table.getColumnModel().getColumn(1).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(2).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(3).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(4).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(5).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(6).setCellRenderer(renderer1);
            dashboard.add(new JScrollPane(table));
            
            this.slider = new JSlider(0, 100, 50);
            this.slider.addChangeListener(this);
            dashboard.add(this.slider, BorderLayout.SOUTH);
            add(dashboard, BorderLayout.SOUTH);
        }
        
        public DemoPanel(XYZDataset dataset) {
            super(new BorderLayout());
            data = dataset;
            int slideLowerBound = 0;
            int sliderUpperBound = data.getItemCount(0);
            
            this.chart = createChart(data,0);
            //this.chart.addProgressListener(this);
            this.chartPanel = new ChartPanel(this.chart);
            this.chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
            this.chartPanel.setDomainZoomable(true);
            this.chartPanel.setRangeZoomable(true);
            Border border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(4, 4, 4, 4),
                    BorderFactory.createEtchedBorder());
            this.chartPanel.setBorder(border);
            add(this.chartPanel);
            
            JPanel dashboard = new JPanel(new BorderLayout());
            dashboard.setPreferredSize(new Dimension(400, 60));
            dashboard.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
            
            this.model = new DemoTableModel(3);
            XYPlot plot = (XYPlot) this.chart.getPlot();
            //this.model.setValueAt(plot.getDataset().getSeriesKey(0), 0, 0);
            this.model.setValueAt(plot.getDataset().getSeriesKey(0), 0, 0);
            this.model.setValueAt(new Double("0.00"), 0, 1);
            this.model.setValueAt(new Double("0.00"), 0, 2);
            this.model.setValueAt(new Double("0.00"), 0, 3);
            this.model.setValueAt(new Double("0.00"), 0, 4);
            this.model.setValueAt(new Double("0.00"), 0, 5);
            this.model.setValueAt(new Double("0.00"), 0, 6);
            JTable table = new JTable(this.model);
            TableCellRenderer renderer1 = new NumberCellRenderer();
            //TableCellRenderer renderer2 = new NumberCellRenderer();
            table.getColumnModel().getColumn(1).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(2).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(3).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(4).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(5).setCellRenderer(renderer1);
            table.getColumnModel().getColumn(6).setCellRenderer(renderer1);
            dashboard.add(new JScrollPane(table));
            this.slider = new JSlider(slideLowerBound, sliderUpperBound, 0);
                  
            this.slider.setMajorTickSpacing(10);
            this.slider.setMinorTickSpacing(5);
            this.slider.setPaintLabels(true);
            this.slider.setPaintTicks(true);
            this.slider.addChangeListener(this);
            dashboard.add(this.slider, BorderLayout.SOUTH);
            add(dashboard, BorderLayout.SOUTH);
        }
        
        /**
         * Creates the demo chart.
         * 
         * @return The chart.
         */
        private JFreeChart createChart() {

            XYDataset dataset1 = createDataset("Random 1", 100.0, 200);
            
            JFreeChart chart1 = ChartFactory.createXYLineChart(
            "Custom",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset1,                  // data
            PlotOrientation.VERTICAL, 
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

            chart1.setBackgroundPaint(Color.white);
            XYPlot plot = (XYPlot) chart1.getPlot();
            plot.setOrientation(PlotOrientation.VERTICAL);
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);
            plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
            
            plot.setDomainCrosshairVisible(true);
            plot.setDomainCrosshairLockedOnData(false);
            plot.setRangeCrosshairVisible(false);
            XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(0, Color.black);
                           
            return chart1;
        }
        
        private JFreeChart createChart(XYZDataset dataset, int value) {

            // XYDataset dataset1 = createDataset("Random 1", 100.0, 200);
            XYDataset dataset1 = selectRowFromDatasetXYZ(data,value);
            
            JFreeChart chart1 = ChartFactory.createXYLineChart(
            "Custom",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset1,                  // data
            PlotOrientation.VERTICAL, 
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

            chart1.setBackgroundPaint(Color.white);
            XYPlot plot = (XYPlot) chart1.getPlot();
            plot.setOrientation(PlotOrientation.VERTICAL);
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);
            plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
            
            plot.setDomainCrosshairVisible(true);
            plot.setDomainCrosshairLockedOnData(false);
            plot.setRangeCrosshairVisible(false);
            XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(0, Color.black);
                           
            return chart1;
        }
        
        
        /**
         * Creates a sample dataset.
         * 
         * @param name  the dataset name.
         * @param base  the starting value.
         * @param start  the starting period.
         * @param count  the number of values to generate.
         *
         * @return The dataset.
         */
    private XYDataset createDataset(String name, double base, int count) {

            this.series = new XYSeries(name);
            double xvalue = base;
            double yvalue = base;

            for (int i = 0; i < count; i++) {
                this.series.add(xvalue, yvalue);    
                xvalue = xvalue + 1*count;
                yvalue = yvalue * (1 + (Math.random() - 0.495) / 10.0);
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(this.series);

            return dataset;     
    }
        
        /**
         * Handles a state change event.
         * 
         * @param event  the event.
         */
        public void stateChanged(ChangeEvent event) {
            int value = this.slider.getValue();
            XYPlot plot = (XYPlot) this.chart.getPlot();
            plot.setDataset(selectRowFromDatasetXYZ(data,value));
        }

        /**
         * Handles a chart progress event.
         * 
         * @param event  the event.
         */
        public void chartProgress(ChartProgressEvent event) {
            if (event.getType() != ChartProgressEvent.DRAWING_FINISHED) {
                return;
            }
            if (this.chartPanel != null) {
                JFreeChart c = this.chartPanel.getChart();
                if (c != null) {
                    XYPlot plot = (XYPlot) c.getPlot();
                    XYDataset dataset = plot.getDataset();
                    Comparable seriesKey = dataset.getSeriesKey(0);
                    double xx = plot.getDomainCrosshairValue();
                    Number xxx = (Number) xx;
                    // update the table...
                    this.model.setValueAt(seriesKey, 0, 0);
                    this.model.setValueAt(new Double(xx), 0, 1);
                    int itemIndex = this.series.indexOf(xx);
                    if (itemIndex >= 0) {
                        XYDataItem item = this.series.getDataItem(
                                Math.min(199, Math.max(0, itemIndex)));
                        XYDataItem prevItem = this.series.getDataItem(
                                Math.max(0, itemIndex - 1));
                        XYDataItem nextItem = this.series.getDataItem(
                                Math.min(199, itemIndex + 1));
                        double x = item.getX().doubleValue();          
                        double y = item.getY().doubleValue();          
                        double prevX 
                            = prevItem.getX().doubleValue();       
                        double prevY = prevItem.getY().doubleValue();             
                        double nextX 
                            = nextItem.getX().doubleValue(); 
                        double nextY = nextItem.getY().doubleValue();
                        this.model.setValueAt(new Double(x), 0, 1);
                        this.model.setValueAt(new Double(y), 0, 2);
                        this.model.setValueAt(new Double(prevX), 0, 3);
                        this.model.setValueAt(new Double(prevY), 0, 4);
                        this.model.setValueAt(new Double(nextX), 0, 5);
                        this.model.setValueAt(new Double(nextY), 0, 6);
                    }
                    
                }
            }
        }

public XYDataset selectRowFromDatasetXYZ(final XYZDataset data, final int colum) {
        
        return new XYDataset() {
            
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                // return 10000;
                return data.getSeriesCount(); //row dimension
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return data.getXValue(item,series);
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return data.getZValue(item,colum);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "psi.df[n][x2]";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }

    }
    
    /**
     * A demonstration application showing how to control a crosshair using an
     * external UI component.
     *
     * @param title  the frame title.
     */
    public Custom3DLinePlot(String title) {
        super(title);
        setContentPane(new DemoPanel());
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        return new DemoPanel();
    }

     /**
     * Creates a panel for the demo.
     *  
     * @return A panel.
     */
    public static JPanel createDemoPanel(XYZDataset completeData) {
                return new DemoPanel(completeData);
    }    
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        Custom3DLinePlot demo = new Custom3DLinePlot("Crosshair Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    /**
     * A demo table model.
     */
    static class DemoTableModel extends AbstractTableModel 
                                implements TableModel {
    
        private Object[][] data;
    
        /**
         * Creates a new demo table model. 
         * 
         * @param rows  the row count.
         */
        public DemoTableModel(int rows) {
            this.data = new Object[rows][7];
        }
     
        /**
         * Returns the number of columns.
         * 
         * @return 7.
         */
        public int getColumnCount() {
            return 7;
        }
        
        /**
         * Returns the row count.
         * 
         * @return 1.
         */
        public int getRowCount() {
            return 1;
        }
        
        /**
         * Returns the value at the specified cell in the table.
         * 
         * @param row  the row index.
         * @param column  the column index.
         * 
         * @return The value.
         */
        public Object getValueAt(int row, int column) {
            return this.data[row][column];
        }
        
        /**
         * Sets the value at the specified cell.
         * 
         * @param value  the value.
         * @param row  the row index.
         * @param column  the column index.
         */
        @Override
        public void setValueAt(Object value, int row, int column) {
            this.data[row][column] = value;
            fireTableDataChanged();
        }
        
        /**
         * Returns the column name.
         * 
         * @param column  the column index.
         * 
         * @return The column name.
         */
        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0 : return "Series Name:";
                case 1 : return "X:";
                case 2 : return "Y:";
                case 3 : return "X (prev)";
                case 4 : return "Y (prev):";
                case 5 : return "X (next):";
                case 6 : return "Y (next):";
            }
            return null;
        }
        
    }
    
}

