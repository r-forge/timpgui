/*
 * TPlots.java
 *
 * Created on September 20, 2007, 9:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package timpgui;

import Jama.Matrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYZDataset;
import org.rosuda.JRI.Rengine;

/**
 *
 * @author Joris
 */
public class TPlots {
    public static TDatasets datasets = null;
    
    /**
     * Creates a new instance of TPlots
     */
    public TPlots() {
        TDatasets datasets = new TDatasets();
        
    }
    
    public static void outputGraphics() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Joris", 75);
        dataset.setValue("Kate", 20);
        dataset.setValue("Ivo", 5);
        
        // create a chart...
        JFreeChart chart = ChartFactory.createPieChart("TIMPGUI Development Pie Chart",
                dataset, true, // legend?
                true, // tooltips?
                false // URLs?
                );
        // create and display a frame...
        ChartFrame frame = new ChartFrame("Test", chart);
        frame.pack();
        frame.setVisible(true);
    }
    //String datasetLabel, String xLabel, String x2Label
    public static void outputGraphics2() {
        

//            // System.out.println(m.get(1,1));
//        XYBlockChartDemo1 demo = new XYBlockChartDemo1("XY Area Chart Demo", matrixToData(m));
//        demo.showJGraph();

        // create a new frame and display the chart in it ...
        XYBlockChartDemo1 demo2 = new XYBlockChartDemo1("JFreeChart: XYBlockChartDemo1", datasets.createDatasetXYZ());
        demo2.showGraph();
        
//        LineChartDemo4 demo4= new LineChartDemo4("JFreeChart: LineChartDemo4", selectFromXYZDataset(createDatasetXYZ(psidf,x,x2)));
//        demo4.showGraph();
    }
    
    public static void outputGraphics3() {
        // TODOFIX: This implementations doesn't work yet'
        
// create a new frame and display the chart in it ...        
        LineChartDemo4 demo4 = new LineChartDemo4("JFreeChart: LineChartDemo4", datasets.SelectColumn(datasets.createDatasetXYZ(), 1));
        demo4.showGraph();
    }
    
}
