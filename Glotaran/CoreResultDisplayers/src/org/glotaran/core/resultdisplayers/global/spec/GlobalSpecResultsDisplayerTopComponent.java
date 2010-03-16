/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.resultdisplayers.global.spec;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.glotaran.core.models.results.GtaResult;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.resultdisplayers.common.panels.CommonTools;
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
import org.jfree.util.StrokeList;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.windows.CloneableTopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.glotaran.core.resultdisplayers.global.spec//GlobalSpecResultsDisplayer//EN",
autostore = false)
public final class GlobalSpecResultsDisplayerTopComponent extends CloneableTopComponent {

    private class RelationTo {
        public Integer indexTo;
        public Double valueTo;
        public RelationTo(Integer index, Double value){
            indexTo = index;
            valueTo = value;
        }
    };

    private class RelationFrom {
        public Integer indexFrom;
        public ArrayList<RelationTo> scaledDatasets;
        public RelationFrom(Integer index){
            indexFrom = index;
            scaledDatasets = new ArrayList<RelationTo>();
        }
    };


    private static GlobalSpecResultsDisplayerTopComponent instance;
    private final static long serialVersionUID = 1L;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "GlobalSpecResultsDisplayerTopComponent";

    private List<TimpResultDataset> resultDatasets = null;
    private GtaResult gtaResultObj;

    private ArrayList<RelationFrom> relationGroups = new ArrayList <RelationFrom>();

    private GraphPanel spectraImage;
    private Crosshair crosshair;
    private JFreeChart subchartTimeTrace;
    private JFreeChart subchartResidualsTime;
    
    private TimpResultDataset fromDataset;
    private TimpResultDataset toDataset;
    private double scaleValue;

    public GlobalSpecResultsDisplayerTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "CTL_GlobalSpecResultsDisplayerTopComponent"));
        setToolTipText(NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "HINT_GlobalSpecResultsDisplayerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

    }

    public GlobalSpecResultsDisplayerTopComponent(List<TimpResultDataset> results, GtaResult gtaResult) {
        initComponents();
        this.resultDatasets = results;
        this.gtaResultObj = gtaResult;
        setName(NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "CTL_GlobalSpecResultsDisplayerTopComponent"));
        setToolTipText(NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "HINT_GlobalSpecResultsDisplayerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        ArrayList<String> datasetsID = new ArrayList<String>();
        if (gtaResultObj !=null){
            for (int i = 0; i < results.size(); i++ ){
                for (int j = 0; j < gtaResult.getDatasets().size(); j++){
                    if (gtaResult.getDatasets().get(j).getResultFile().getFilename().equals(results.get(i).getDatasetName())){
                        datasetsID.add(gtaResult.getDatasets().get(j).getId());
                    }
                }
            }
            if (gtaResult.getDatasetRelations()!=null){
//"to" is the dataset whish should be scalled
//"from" is the dataset with weight 1
//"values" linear relations - for now it is only one number;
                for (int j = 0; j < datasetsID.size(); j++) {
                    for (int i = 0; i < gtaResult.getDatasetRelations().size(); i++) {
                        if (gtaResult.getDatasetRelations().get(i).getFrom().equals(datasetsID.get(j))) {
                            relationGroups.add(new RelationFrom(j));
                            for (int k = 0; k < datasetsID.size(); k++) {
                                if (gtaResult.getDatasetRelations().get(i).getTo().equals(datasetsID.get(k))){
                                    relationGroups.get(relationGroups.size()-1).scaledDatasets.add(new RelationTo(k,gtaResult.getDatasetRelations().get(i).getValues().get(0)));
                                }
                            }
                        }
                    }
                }
            }
// relationGroups complex list with groups of all relations
            if (!relationGroups.isEmpty()){
//for every relations group create tab with comparions spectra coming from "from dataset"
                for (int i = 0; i < relationGroups.size(); i++){
                    fromDataset = results.get(relationGroups.get(i).indexFrom);
                    toDataset = results.get(relationGroups.get(i).scaledDatasets.get(0).indexTo);
                    scaleValue = relationGroups.get(i).scaledDatasets.get(0).valueTo;
//create spectra from "from dataset"
                    jPSpectra.removeAll();
                    spectraImage = createSpectraPlot(fromDataset);
                    jPSpectra.add(spectraImage);
//add croshair to the image
//                    ImageCrosshairLabelGenerator crossLabGen = new ImageCrosshairLabelGenerator(fromDataset.getX2(),false);
                    CrosshairOverlay overlay = new CrosshairOverlay();
                    crosshair = new Crosshair(fromDataset.getX2()[0]);
                    crosshair.setPaint(Color.blue);
//                    crosshair.setLabelGenerator(crossLabGen);
                    crosshair.setLabelVisible(true);
                    crosshair.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                    overlay.addDomainCrosshair(crosshair);
                    overlay.addRangeCrosshair(crosshair);
                    spectraImage.addOverlay(overlay);
                    
//initialise slider from "from dataset"
                    jSWavelengths.getModel().setRangeProperties(0, 1, 0, fromDataset.getX2().length-1, true);
//create plot with curves from "from dataset";
                    jPTraces.removeAll();
                    jPTraces.add(makeTracesChart(fromDataset));
                }
            }
            
        }
        else {
//            plotSpectra(results.get(0));

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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPMultiTraces = new javax.swing.JPanel();
        jPSpectra = new javax.swing.JPanel();
        jPTraces = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jSWavelengths = new javax.swing.JSlider();
        jPSpectraTab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPMultiTraces.setLayout(new java.awt.GridBagLayout());

        jPSpectra.setBackground(new java.awt.Color(255, 255, 255));
        jPSpectra.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.45;
        jPMultiTraces.add(jPSpectra, gridBagConstraints);

        jPTraces.setBackground(new java.awt.Color(255, 255, 255));
        jPTraces.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.55;
        jPMultiTraces.add(jPTraces, gridBagConstraints);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jSWavelengths.setValue(0);
        jSWavelengths.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSWavelengthsStateChanged(evt);
            }
        });
        jPanel10.add(jSWavelengths, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 1.0;
        jPMultiTraces.add(jPanel10, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "GlobalSpecResultsDisplayerTopComponent.jPMultiTraces.TabConstraints.tabTitle"), jPMultiTraces); // NOI18N

        jPanel3.setLayout(new java.awt.GridLayout(2, 2, 2, 2));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel7);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPSpectraTabLayout = new javax.swing.GroupLayout(jPSpectraTab);
        jPSpectraTab.setLayout(jPSpectraTabLayout);
        jPSpectraTabLayout.setHorizontalGroup(
            jPSpectraTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );
        jPSpectraTabLayout.setVerticalGroup(
            jPSpectraTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPSpectraTabLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(GlobalSpecResultsDisplayerTopComponent.class, "GlobalSpecResultsDisplayerTopComponent.jPSpectraTab.TabConstraints.tabTitle"), jPSpectraTab); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jSWavelengthsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSWavelengthsStateChanged
        crosshair.setValue(fromDataset.getX2()[jSWavelengths.getValue()]);
        updateTrace(jSWavelengths.getValue());
    }//GEN-LAST:event_jSWavelengthsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPMultiTraces;
    private javax.swing.JPanel jPSpectra;
    private javax.swing.JPanel jPSpectraTab;
    private javax.swing.JPanel jPTraces;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSlider jSWavelengths;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized GlobalSpecResultsDisplayerTopComponent getDefault() {
        if (instance == null) {
            instance = new GlobalSpecResultsDisplayerTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the GlobalSpecResultsDisplayerTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized GlobalSpecResultsDisplayerTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(GlobalSpecResultsDisplayerTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof GlobalSpecResultsDisplayerTopComponent) {
            return (GlobalSpecResultsDisplayerTopComponent) win;
        }
        Logger.getLogger(GlobalSpecResultsDisplayerTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    private GraphPanel createSpectraPlot(TimpResultDataset dataset) {
        String specName = dataset.getJvec()!=null ? "SAS" : "EAS";
        boolean errorBars = dataset.getSpectraErr()!=null ? true : false;
        int numberOfComponents = dataset.getJvec()!=null ? dataset.getJvec().length/2 : dataset.getKineticParameters().length / 2;
        int compNum = 0;
        double maxAmpl = 0;

        int compNumFull = 0;
//add remove coherentartifact
        if (false) {
            compNum = numberOfComponents + 1;
        }
        else {
            compNum = numberOfComponents;
        }

        YIntervalSeriesCollection realSasCollection = new YIntervalSeriesCollection();
        YIntervalSeriesCollection normSasCollection = new YIntervalSeriesCollection();
        YIntervalSeries seria;

//        XYSeries seria;
//create collection of real sas(eas)
        for (int j = 0; j < compNum; j++) {
            seria =new YIntervalSeries(specName + (j + 1));// new XYSeries(specName + (j + 1));
            maxAmpl = 0;
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

        JFreeChart tracechart = ChartFactory.createXYLineChart(
                null,
                "Wavelength (nm)",
                specName,
                realSasCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(dataset.getX2()[dataset.getX2().length - 1]);
        return new GraphPanel(tracechart, errorBars);
    }

    private ChartPanel makeTracesChart(TimpResultDataset res) {

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
            false
        );
        subchartTimeTrace = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        subchartTimeTrace.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);
        subchartResidualsTime.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length-1]);

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
        xAxis.setUpperBound(res.getX()[res.getX().length-1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);
        plot.add(plot1_1, 3);
        plot.add(plot1_2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart tracechart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        tracechart.getLegend().setVisible(false);
        ChartPanel chpan = new ChartPanel(tracechart,true);
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        return chpan;
    }


    private void updateTrace(int xIndex){
        if (true){
            XYSeriesCollection traceFrom = CommonTools.createFitRawTraceCollection(xIndex, 0, fromDataset.getX().length,fromDataset);
            XYSeriesCollection residFrom = CommonTools.createResidTraceCollection(xIndex, 0, fromDataset.getX().length,fromDataset);

            XYSeriesCollection traceTo = CommonTools.createFitRawTraceCollection(xIndex, 0, toDataset.getX().length,toDataset);
            XYSeriesCollection residTo = CommonTools.createResidTraceCollection(xIndex, 0, toDataset.getX().length,toDataset);


            for (int i = 0; i < traceTo.getSeries(0).getItemCount(); i++){
                traceTo.getSeries(0).getDataItem(i).setY(traceTo.getSeries(0).getDataItem(i).getYValue()*scaleValue);
                traceTo.getSeries(1).getDataItem(i).setY(traceTo.getSeries(1).getDataItem(i).getYValue()*scaleValue);
                residTo.getSeries(0).getDataItem(i).setY(residTo.getSeries(0).getDataItem(i).getYValue()*scaleValue);
            }

            XYSeriesCollection trace = new XYSeriesCollection();
            XYSeriesCollection resid = new XYSeriesCollection();

            trace.addSeries(traceTo.getSeries(0));
            trace.addSeries(traceTo.getSeries(1));
            trace.addSeries(traceFrom.getSeries(0));
            trace.addSeries(traceFrom.getSeries(1));
            resid.addSeries(residTo.getSeries(0));
            resid.addSeries(residFrom.getSeries(0));

            ChartPanel linTime = makeLinTimeTraceResidChart(trace, resid, new NumberAxis("Time"), null);

            jPTraces.removeAll();
            jPTraces.add(linTime);
            jPTraces.validate();
        }
        else {
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
        plot1_1.getRenderer().setSeriesPaint(0, Color.blue);
        plot1_1.getRenderer().setSeriesPaint(1, Color.blue);
        plot1_1.getRenderer().setSeriesPaint(2, Color.red);
        plot1_1.getRenderer().setSeriesPaint(3, Color.red);

        plot1_1.getRenderer().setSeriesStroke(0, new BasicStroke(1.0f));
        plot1_1.getRenderer().setSeriesStroke(1,
                new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));

        plot1_1.getRenderer().setSeriesStroke(2, new BasicStroke(1.0f));
        plot1_1.getRenderer().setSeriesStroke(3,
                new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));

        plot1_1.getRangeAxis().setAutoRange(true);


        XYPlot plot1_2 = subchartResiduals.getXYPlot();
        plot1_2.getDomainAxis().setLowerMargin(0.0);
        plot1_2.getDomainAxis().setUpperMargin(0.0);
        plot1_2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1_2.getDomainAxis().setInverted(true);
        plot1_2.setRangeZeroBaselineVisible(true);
        plot1_2.getRenderer().setSeriesPaint(0, Color.blue);
        plot1_2.getRenderer().setSeriesPaint(1, Color.red);
        plot1_2.getRangeAxis().setAutoRange(true);

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

}
