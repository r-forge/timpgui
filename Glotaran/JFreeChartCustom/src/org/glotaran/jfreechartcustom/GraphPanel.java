/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.jfreechartcustom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.glotaran.core.messages.CoreErrorMessages;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ExtensionFileFilter;
import org.openide.windows.TopComponent;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 *
 * @author slapten
 */
public class GraphPanel extends ChartPanel{
    public static final String SAVE_ASCII_COMMAND = "SAVE_ASCII";
    public static final String SAVE_SVG_COMMAND = "SAVE_SVG";
    public static final String SAVE_PNG_COMMAND = "SAVE_PNG";
    public static final String OPEN_IN_NEW_WINDOW_COMMAND = "OPEN_IN_NEW_WINDOW";

    public GraphPanel(JFreeChart chart){
        this(chart, true, false, true, true, true);
    }

    public GraphPanel(JFreeChart chart,
                      boolean properties,
                      boolean save,
                      boolean print,
                      boolean zoom,
                      boolean tooltips){
         super(chart, properties, save, print, zoom, tooltips);
         addCommandsToPopupMenu();
         updateSelectRectangle();
         setPannable();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
        String command = event.getActionCommand();
        if (command.equals(OPEN_IN_NEW_WINDOW_COMMAND)){
            try {
                doOpenInSeparateWindow();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (command.equals(SAVE_ASCII_COMMAND)){
            try {
                doSaveTracesToAscii();
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(null);
            }
        }

        if (command.equals(SAVE_SVG_COMMAND)){
            try {
                doSaveChartToSVG();
            } catch (FileNotFoundException ex) {
                CoreErrorMessages.fileNotFound();
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(null);
            }
        }

        if (command.equals(SAVE_PNG_COMMAND)){
            try {
                try {
                    doSaveChartToPNG();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                CoreErrorMessages.fileSaveError(null);
            }
        }

    }

    private void addCommandsToPopupMenu(){
        JPopupMenu popmenu;
        popmenu = getPopupMenu();
        JMenuItem saveToASCIIItem = new JMenuItem("Save data to ASCII");
        saveToASCIIItem.setActionCommand(SAVE_ASCII_COMMAND);
        saveToASCIIItem.addActionListener(this);
        popmenu.insert(saveToASCIIItem,3);

        JMenuItem saveToPNGItem = new JMenuItem("Render chart to PNG");
        saveToPNGItem.setActionCommand(SAVE_PNG_COMMAND);
        saveToPNGItem.addActionListener(this);
        popmenu.insert(saveToPNGItem,4);

        JMenuItem saveToSVGItem = new JMenuItem("Render chart to SVG");
        saveToSVGItem.setActionCommand(SAVE_SVG_COMMAND);
        saveToSVGItem.addActionListener(this);
        popmenu.insert(saveToSVGItem,5);

        JMenuItem openInSepWindItem = new JMenuItem("Open in new window");
        openInSepWindItem.setActionCommand(OPEN_IN_NEW_WINDOW_COMMAND);
        openInSepWindItem.addActionListener(this);
        popmenu.insert(openInSepWindItem,0);
        popmenu.insert(new JPopupMenu.Separator(),1);
    }

    private void updateSelectRectangle(){
        this.setFillZoomRectangle(true);
        this.setMouseWheelEnabled(true);
        this.setZoomFillPaint(new Color(68, 68, 78, 63));
    }

    private void doSaveTracesToAscii() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(this.getDefaultDirectoryForSaveAs());
        ExtensionFileFilter filter = new ExtensionFileFilter("Tab Separated ASCII files", ".ascii");
        fileChooser.addChoosableFileFilter(filter);
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (isEnforceFileExtensions()) {
                if (!filename.endsWith(".ascii")) {
                    filename = filename + ".ascii";
                }
            }
//write all tracess that presented in chart, in colums, using domain axe from first seria in colums
            BufferedWriter output = new BufferedWriter(new FileWriter(new File(filename)));
            AbstractXYDataset data = (AbstractXYDataset) this.getChart().getXYPlot().getDataset();
            int tracenum = data.getSeriesCount();
            output.append("X");
            for (int i = 0; i < tracenum; i++) {
                output.append("\t");
                output.append(data.getSeriesKey(i).toString());
            }
            output.newLine();
            for (int j = 0; j < data.getItemCount(0); j++) {
                output.append(new Formatter().format("%g", data.getXValue(0, j)).toString());
                output.append("\t");
                for (int i = 0; i < tracenum; i++) {
                    output.append(new Formatter().format("%g", data.getYValue(i, j)).toString());
                    output.append("\t");
                }
                output.newLine();
            }
            output.close();
        }
    }

    private void doOpenInSeparateWindow() throws CloneNotSupportedException {
        TopComponent win = new TopComponent()
        {
            @Override
            public int getPersistenceType() {
                return TopComponent.PERSISTENCE_NEVER;

            }
        };
        win.setLayout(new BorderLayout());
        win.setName((String)((this.getChart().getTitle() != null) ? this.getChart().getTitle() : "Single graph"));

        JFreeChart chart = (JFreeChart)this.getChart().clone();
        if (this.getChart().getXYPlot()!=null){
            chart.getXYPlot().setOrientation(PlotOrientation.VERTICAL);
            chart.getXYPlot().getDomainAxis().setInverted(false);
        }
        win.add(new GraphPanel(chart));
        win.open();
        win.requestActive();
    }

    private void doSaveChartToPNG() throws CloneNotSupportedException, IOException {
        doSaveAs();
    }

    private void doSaveChartToSVG() throws FileNotFoundException, IOException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(this.getDefaultDirectoryForSaveAs());
        ExtensionFileFilter filter = new ExtensionFileFilter("SVG graphics files", ".svg");
        fileChooser.addChoosableFileFilter(filter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (isEnforceFileExtensions()) {
                if (!filename.endsWith(".svg")) {
                    filename = filename + ".svg";
                }
            }
            DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();
            Document document = domImpl.createDocument(null, "svg", null);

            // Create an instance of the SVG Generator
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

            svgGenerator.getGeneratorContext().setPrecision(6);

            // draw the chart in the SVG generator
            getChart().draw(svgGenerator,  new Rectangle2D.Double(0, 0, 400, 300), null);

            // Write svg file
            Writer out = new OutputStreamWriter(
                    new FileOutputStream(new File(filename)), "UTF-8");
            svgGenerator.stream(out, true);
        }
    }

    private void setPannable() {
        if (this.getChart().getXYPlot()!=null){
            this.getChart().getXYPlot().setDomainPannable(true);
            this.getChart().getXYPlot().setRangePannable(true);
        }
    }
}
