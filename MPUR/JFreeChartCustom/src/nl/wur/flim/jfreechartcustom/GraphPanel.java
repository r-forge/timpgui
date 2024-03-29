/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.flim.jfreechartcustom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ExtensionFileFilter;
import org.openide.windows.TopComponent;

/**
 *
 * @author slapten
 */
public class GraphPanel extends ChartPanel{
    public static final String SAVE_ASCII_COMMAND = "SAVE_ASCII";
    public static final String OPEN_IN_NEW_WINDOW_COMMAND = "OPEN_IN_NEW_WINDOW";

    public GraphPanel(JFreeChart chart){
        this(chart, true);
    }

    public GraphPanel(JFreeChart chart, boolean useBuffer){
        super(chart, useBuffer);
        addCommandsToPopupMenu();
        updateSelectRectangle();


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
                Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void addCommandsToPopupMenu(){
        JPopupMenu popmenu;
        popmenu = getPopupMenu();
        JMenuItem saveToASCIIItem = new JMenuItem("Save to ASCII");
        saveToASCIIItem.setActionCommand(SAVE_ASCII_COMMAND);
        saveToASCIIItem.addActionListener(this);
        popmenu.insert(saveToASCIIItem,4);

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
            XYSeriesCollection data = (XYSeriesCollection)this.getChart().getXYPlot().getDataset();
            int tracenum = data.getSeriesCount();
            for (int i = 0; i < tracenum; i++){
                output.append("X    ");
                output.append(data.getSeries(i).getKey().toString());
            }
            output.newLine();
            for (int j = 0; j < data.getSeries(0).getItemCount(); j++ ){
                output.append(new Formatter().format("%g",data.getSeries(0).getX(j)).toString());
                output.append("\t");
                for (int i = 0; i < tracenum; i++){
                    output.append(new Formatter().format("%g",data.getSeries(i).getY(j)).toString());
                    output.append("\t");
                }
                output.newLine();
            }
            output.close();
        }
    }

    private void doOpenInSeparateWindow() throws CloneNotSupportedException {
        TopComponent win = new TopComponent();
        win.setLayout(new BorderLayout());
        win.setName("Single graph");
        win.add(new GraphPanel((JFreeChart) this.getChart().clone()));
        win.open();
        win.requestActive();
    }

}
