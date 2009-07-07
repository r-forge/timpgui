/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui.plots;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.timpgui.timpinterface.ResultObject;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
//import org.openide.util.Utilities;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.TimpResultDataset;
/**
 * Top component which displays something.
 */
final class StreakOutTopComponent extends TopComponent implements ChartMouseListener {

    private static StreakOutTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "StreakOutTopComponent";
    private TimpResultDataset res;
    private ChartPanel chpanImage;
    private JFreeChart chart;
    private XYSeriesCollection tracesTimeCollection;
    private XYSeriesCollection residualsTime;
    private XYSeriesCollection tracesWaveCollection;
    private XYSeriesCollection residualsWave;
    private static DefaultListModel listOfResultObjectNames = new DefaultListModel();
    private static DefaultListModel listOfDatasets = new DefaultListModel();
    private ArrayList<ResultObject> resultObjects;

    private StreakOutTopComponent() {

        initComponents();
        setName(NbBundle.getMessage(StreakOutTopComponent.class, "CTL_StreakOutTopComponent"));
        setToolTipText(NbBundle.getMessage(StreakOutTopComponent.class, "HINT_StreakOutTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListResultObjects = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListDatasets = new javax.swing.JList();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLKineticParameters = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLSpectralParameters = new javax.swing.JList();
        jPStreakImage = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPSelectedTimeTrace = new javax.swing.JPanel();
        jPSelectedWaveTrace = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPSpectras = new javax.swing.JPanel();
        jPConcentrations = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setLayout(new java.awt.GridLayout(0, 1));

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jListResultObjects.setBorder(javax.swing.BorderFactory.createTitledBorder("ListOfResults"));
        jListResultObjects.setModel(listOfDatasets);
        jListResultObjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListResultObjects.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListResultObjectsValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListResultObjects);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(StreakOutTopComponent.class, "StreakOutTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );

        jPanel5.add(jPanel4);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(StreakOutTopComponent.class, "StreakOutTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jListDatasets.setBorder(javax.swing.BorderFactory.createTitledBorder("ListOfDatasets"));
        jListDatasets.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListDatasetsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListDatasets);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        jPanel5.add(jPanel3);

        jPanel6.add(jPanel5);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jLKineticParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("KinParameters"));
        jScrollPane1.setViewportView(jLKineticParameters);

        jPanel7.add(jScrollPane1);

        jLSpectralParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("SpecParameters"));
        jScrollPane2.setViewportView(jLSpectralParameters);

        jPanel7.add(jScrollPane2);

        jPanel6.add(jPanel7);

        jPStreakImage.setBackground(new java.awt.Color(0, 0, 0));
        jPStreakImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPStreakImage.setMaximumSize(new java.awt.Dimension(450, 350));
        jPStreakImage.setMinimumSize(new java.awt.Dimension(450, 350));
        jPStreakImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPStreakImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPStreakImageLayout = new javax.swing.GroupLayout(jPStreakImage);
        jPStreakImage.setLayout(jPStreakImageLayout);
        jPStreakImageLayout.setHorizontalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );
        jPStreakImageLayout.setVerticalGroup(
            jPStreakImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jPanel6.add(jPStreakImage);

        add(jPanel6);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPSelectedTimeTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedTimeTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSelectedTimeTraceLayout = new javax.swing.GroupLayout(jPSelectedTimeTrace);
        jPSelectedTimeTrace.setLayout(jPSelectedTimeTraceLayout);
        jPSelectedTimeTraceLayout.setHorizontalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPSelectedTimeTraceLayout.setVerticalGroup(
            jPSelectedTimeTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jPanel1.add(jPSelectedTimeTrace);

        jPSelectedWaveTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPSelectedWaveTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSelectedWaveTraceLayout = new javax.swing.GroupLayout(jPSelectedWaveTrace);
        jPSelectedWaveTrace.setLayout(jPSelectedWaveTraceLayout);
        jPSelectedWaveTraceLayout.setHorizontalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPSelectedWaveTraceLayout.setVerticalGroup(
            jPSelectedWaveTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jPanel1.add(jPSelectedWaveTrace);

        add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(0, 1));

        jPSpectras.setBackground(new java.awt.Color(255, 255, 255));
        jPSpectras.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPSpectrasLayout = new javax.swing.GroupLayout(jPSpectras);
        jPSpectras.setLayout(jPSpectrasLayout);
        jPSpectrasLayout.setHorizontalGroup(
            jPSpectrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPSpectrasLayout.setVerticalGroup(
            jPSpectrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jPanel2.add(jPSpectras);

        jPConcentrations.setBackground(new java.awt.Color(255, 255, 255));
        jPConcentrations.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPConcentrationsLayout = new javax.swing.GroupLayout(jPConcentrations);
        jPConcentrations.setLayout(jPConcentrationsLayout);
        jPConcentrationsLayout.setHorizontalGroup(
            jPConcentrationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPConcentrationsLayout.setVerticalGroup(
            jPConcentrationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jPanel2.add(jPConcentrations);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    res = new TimpResultDataset();
    Object[] rates = new Object[res.getKineticParameters().length];
    for (int i = 0; i < res.getKineticParameters().length; i++) {
        rates[i] = "k" + (i + 1) + "=" + res.getKineticParameters()[i] + "ns";
    }
    jLKineticParameters.setListData(rates);

    chpanImage = new ChartPanel(MakeImageChart(MakeXYZDataset()),true);
    chpanImage.getChartRenderingInfo().setEntityCollection(null);
    jPStreakImage.removeAll();
    chpanImage.setSize(jPStreakImage.getSize());
    chpanImage.addChartMouseListener(this);

    jPStreakImage.add(chpanImage);
    jPStreakImage.repaint();

    PlotFirstTrace();
    MakeTracesChart();
    PlotConcSpectrTrace();
}//GEN-LAST:event_jButton1ActionPerformed

private void jPStreakImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPStreakImageMouseClicked
// TODO add your han   dling code here:
}//GEN-LAST:event_jPStreakImageMouseClicked

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    refreshLists();
}//GEN-LAST:event_jButton2ActionPerformed

private void jListResultObjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListResultObjectsValueChanged
// TODO add your handling code here:
    resultObjects = null; //Current.GetresultNames();
    jListDatasets.removeAll();
    listOfDatasets.removeAllElements();
    ResultObject results = resultObjects.get(jListResultObjects.getSelectedIndex());
    ArrayList<String> x = results.getNamesOfDatasets();
    for (int i = 0; i < x.size(); i++) {
        String e = x.get(i);
        listOfDatasets.addElement(e);
    }
    jListDatasets.setModel(listOfDatasets);
}//GEN-LAST:event_jListResultObjectsValueChanged

private void jListDatasetsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListDatasetsValueChanged
// TODO add your handling code here:
    if (jListDatasets.getSelectedIndex()!=-1) {
    //res = TimpController.setTimpResultDataset((String)jListDatasets.getSelectedValue(), jListDatasets.getSelectedIndex(), Current.GetresultNames().get(jListResultObjects.getSelectedIndex()));
  //  res.CalcRangeInt();
    Object[] rates = new Object[res.getKineticParameters().length];
    for (int i = 0; i < res.getKineticParameters().length; i++) {
        rates[i] = "k" + (i + 1) + "=" + res.getKineticParameters()[i] + "ns";
    }
    jLKineticParameters.setListData(rates);

    chpanImage = new ChartPanel(MakeImageChart(MakeXYZDataset()),true);
    chpanImage.getChartRenderingInfo().setEntityCollection(null);
    jPStreakImage.removeAll();
    chpanImage.setSize(jPStreakImage.getSize());
    chpanImage.addChartMouseListener(this);

    jPStreakImage.add(chpanImage);
    jPStreakImage.repaint();

    PlotFirstTrace();
    MakeTracesChart();
    PlotConcSpectrTrace();
    }
    
}//GEN-LAST:event_jListDatasetsValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jLKineticParameters;
    private javax.swing.JList jLSpectralParameters;
    private javax.swing.JList jListDatasets;
    private javax.swing.JList jListResultObjects;
    private javax.swing.JPanel jPConcentrations;
    private javax.swing.JPanel jPSelectedTimeTrace;
    private javax.swing.JPanel jPSelectedWaveTrace;
    private javax.swing.JPanel jPSpectras;
    private javax.swing.JPanel jPStreakImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized StreakOutTopComponent getDefault() {
        if (instance == null) {
            instance = new StreakOutTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the StreakOutTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized StreakOutTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(StreakOutTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof StreakOutTopComponent) {
            return (StreakOutTopComponent) win;
        }
        Logger.getLogger(StreakOutTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
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
            return StreakOutTopComponent.getDefault();
        }
    }

    private void UpdateSelectedTraces(int waveInd, int timeInd) {
        tracesTimeCollection.getSeries(0).clear();
        tracesTimeCollection.getSeries(1).clear();
        residualsTime.getSeries(0).clear();
        tracesWaveCollection.getSeries(0).clear();
        tracesWaveCollection.getSeries(1).clear();
        residualsWave.getSeries(0).clear();

        for (int j = 0; j < res.getX().length; j++) {
            tracesTimeCollection.getSeries(0).add(res.getX()[j], res.getTraces().get(j, timeInd));
            tracesTimeCollection.getSeries(1).add(res.getX()[j], res.getFittedTraces().get(j, timeInd));
            residualsTime.getSeries(0).add(res.getX()[j], res.getResiduals().get(j, timeInd));
        }

        for (int j = 0; j < res.getX2().length; j++) {
            tracesWaveCollection.getSeries(0).add(res.getX2()[j], res.getTraces().get(j, waveInd));
            tracesWaveCollection.getSeries(1).add(res.getX2()[j], res.getFittedTraces().get(j, waveInd));
            residualsWave.getSeries(0).add(res.getX2()[j], res.getResiduals().get(j, waveInd));
        }

    }

    private void PlotFirstTrace() {
        tracesTimeCollection = new XYSeriesCollection();
        residualsTime = new XYSeriesCollection();
        tracesWaveCollection = new XYSeriesCollection();
        residualsWave = new XYSeriesCollection();

        XYSeries seriaData = new XYSeries("Trace");
        XYSeries seriaFit = new XYSeries("Fittedtrace");
        XYSeries resid = new XYSeries("Residuals");
        for (int j = 0; j < res.getX().length; j++) {
            seriaData.add(res.getX()[j], res.getTraces().get(j, 0));
            seriaFit.add(res.getX()[j], res.getFittedTraces().get(j, 0));
            resid.add(res.getX()[j], res.getResiduals().get(j, 0));
        }
        tracesTimeCollection.addSeries(seriaData);
        tracesTimeCollection.addSeries(seriaFit);
        residualsTime.addSeries(resid);

        seriaData = new XYSeries("Trace");
        seriaFit = new XYSeries("Fittedtrace");
        resid = new XYSeries("Residuals");
        for (int j = 0; j < res.getX2().length; j++) {
            seriaData.add(res.getX2()[j], res.getTraces().get(0, j));
            seriaFit.add(res.getX2()[j], res.getFittedTraces().get(0, j));
            resid.add(res.getX2()[j], res.getResiduals().get(0, j));
        }

        tracesWaveCollection.addSeries(seriaData);
        tracesWaveCollection.addSeries(seriaFit);
        residualsWave.addSeries(resid);

    }

    private void PlotConcSpectrTrace() {

        XYSeriesCollection concCollection = new XYSeriesCollection();
        XYSeriesCollection specCollection = new XYSeriesCollection();

        XYSeries seria;


        for (int j = 0; j < res.getKineticParameters().length; j++) {
            seria = new XYSeries("Conc" + (j + 1));
            for (int i = 0; i < res.getX().length; i++) {
                seria.add(res.getX()[i], res.getConcentrations().get(i, j));
            }
            concCollection.addSeries(seria);
        }

        for (int j = 0; j < res.getKineticParameters().length; j++) {
            seria = new XYSeries("Spec" + (j + 1));
            for (int i = 0; i < res.getX2().length; i++) {
                seria.add(res.getX2()[i], res.getSpectra().get(j, i));
            }
            specCollection.addSeries(seria);
        }
        JFreeChart tracechart;
        // update multiple attributes in the chart, plot, axes, renderer(s), dataset(s) etc.
        tracechart = ChartFactory.createXYLineChart(
                "Concentrations",
                "Time (ns)",
                "Number of counts",
                concCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX()[res.getX().length - 1]);
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(tracechart);
        chpan.getChartRenderingInfo().setEntityCollection(null);
        chpan.setSize(jPConcentrations.getSize());
        jPConcentrations.removeAll();
        jPConcentrations.add(chpan);
        jPConcentrations.repaint();

        tracechart = ChartFactory.createXYLineChart(
                "Spectras",
                "Wavelength (nm)",
                "Number of counts",
                specCollection,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);
        tracechart.getXYPlot().getDomainAxis().setUpperBound(res.getX2()[res.getX2().length - 1]);
//        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        chpan = new ChartPanel(tracechart);
        chpan.getChartRenderingInfo().setEntityCollection(null);
        chpan.setSize(jPSpectras.getSize());
        jPSpectras.removeAll();
        jPSpectras.add(chpan);
        jPSpectras.repaint();


    }

    private void MakeTracesChart() {
        XYItemRenderer renderer1 = new StandardXYItemRenderer();
        NumberAxis rangeAxis1 = new NumberAxis("Number of counts");
        XYPlot subplot1 = new XYPlot(tracesTimeCollection, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        NumberAxis rangeAxis2 = new NumberAxis("Resid");
        rangeAxis2.setAutoRangeIncludesZero(false);
        XYPlot subplot2 = new XYPlot(residualsTime, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        NumberAxis xAxis = new NumberAxis("Time (ns)");
        xAxis.setRange(res.getX()[0], res.getX()[res.getX().length - 1]);
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);

        plot.add(subplot1, 3);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart tracechart = new JFreeChart("Selected trace", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        ChartPanel chpanSelectedTrace = new ChartPanel(tracechart);
        chpanSelectedTrace.setSize(jPSelectedTimeTrace.getSize());
        jPSelectedTimeTrace.removeAll();
        jPSelectedTimeTrace.add(chpanSelectedTrace);
        jPSelectedTimeTrace.repaint();

// plot spectra
        subplot1 = new XYPlot(tracesWaveCollection, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        subplot2 = new XYPlot(residualsWave, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        xAxis = new NumberAxis("Wavelength (nm)");
        xAxis.setRange(res.getX2()[0], res.getX2()[res.getX2().length - 1]);
        plot = new CombinedDomainXYPlot(xAxis);
        plot.setGap(10.0);

        plot.add(subplot1, 3);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        tracechart = new JFreeChart("Selected Spectrum", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        chpanSelectedTrace = new ChartPanel(tracechart);
        chpanSelectedTrace.setSize(jPSelectedWaveTrace.getSize());
        jPSelectedWaveTrace.removeAll();
        jPSelectedWaveTrace.add(chpanSelectedTrace);
        jPSelectedWaveTrace.repaint();

    }

    private XYZDataset MakeXYZDataset() {
        DefaultXYZDataset dataset2 = new DefaultXYZDataset();
        double[] xValues = new double[res.getX().length * res.getX2().length];
        double[] yValues = new double[res.getX().length * res.getX2().length];

        for (int i = 0; i < res.getX().length; i++) {
            for (int j = 0; j < res.getX2().length; j++) {
                xValues[j * res.getX().length + i] = res.getX2()[j];
                yValues[j * res.getX().length + i] = res.getX()[i];
            }
        }
        double[][] chartdata = {xValues, yValues, res.getTraces().getColumnPackedCopy()};
        dataset2.addSeries("Streak image", chartdata);

        ColorCodedImageDataset dataset = new ColorCodedImageDataset(res.getX2().length,res.getX().length,
                    res.getTraces().getRowPackedCopy(),res.getX2(),res.getX(),false);
        
        return dataset;
    }

    private JFreeChart MakeImageChart(XYZDataset dataset) {
        NumberAxis xAxis = new NumberAxis("Wavelengts (nm)");
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
     //   xAxis.setRange(res.GetX2()[0],res.GetX2()[res.GetX2().length-1]);      
        xAxis.setVisible(false);
        NumberAxis yAxis = new NumberAxis("Time (ps)");
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setInverted(true);
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
     //   yAxis.setRange(res.GetX()[0],res.GetX()[res.GetX().length-1]);
        yAxis.setVisible(false);
        XYBlockRenderer renderer = new XYBlockRenderer();
        PaintScale scale = new RainbowPaintScale(res.getMinInt(), res.getMaxInt(), true, false);
        renderer.setPaintScale(scale);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(false);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(false);

        chart = new JFreeChart(plot);
        chart.setNotify(false);
        chart.removeLegend();

        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(res.getMinInt(), res.getMaxInt());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(scale, scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
//        legend.setAxisOffset(5.0);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
//        legend.setFrame(new BlockBorder(Color.red));
//        legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart.getBackgroundPaint());
        chart.addSubtitle(legend);
        chart.setNotify(true);
        return chart;
    }

    public void chartMouseClicked(ChartMouseEvent event) {
        this.chart.setNotify(false);
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

        int chartX = (int) (domainAxis.java2DToValue(p.getX(), dataArea, domainAxisEdge));
        int chartY = (int) (rangeAxis.java2DToValue(p.getY(), dataArea, rangeAxisEdge));

//        System.out.println(chartX+"    "+chartY);

        UpdateSelectedTraces(chartY, chartX);
        this.chart.setNotify(true);

    }

    public void chartMouseMoved(ChartMouseEvent event) {
//         System.out.println("ChartMouseMoved");
    }
    
        
    public void refreshLists() {
        
        int selected = jListResultObjects.getSelectedIndex();
        
        listOfResultObjectNames.removeAllElements();
        resultObjects = null; //Current.GetresultNames();
        for (int i = 0; i < resultObjects.size(); i++) {
        String x = resultObjects.get(i).getNameOfResultsObjects();
        listOfResultObjectNames.addElement(x);
        }
        jListResultObjects.setModel(listOfResultObjectNames);
        
        jListResultObjects.setSelectedIndex(selected);

    }
}
