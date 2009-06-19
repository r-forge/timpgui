/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.dataeditors;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.JDialog;
import nl.vu.nat.tgmprojectsupport.TGProject;
import nl.wur.flim.jfreechartcustom.ColorCodedImageDataset;
import nl.wur.flim.jfreechartcustom.ImageCrosshairLabelGenerator;
import nl.wur.flim.jfreechartcustom.RainbowPaintScale;
import nl.wur.flim.jfreechartcustom.ImageUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDataImageAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.NotifyDescriptor.Exception;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.tgproject.nodes.TgdDataChildren;



/**
 * Top component which displays something.
 */
final public class SpecEditorTopCompNew extends CloneableTopComponent { //implements ChartMouseListener {

    private static SpecEditorTopCompNew instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "SpecEditorTopComponent";
    
    private JFreeChart chart;
    private JFreeChart subchart1;
    private JFreeChart subchart2;
    private Crosshair crosshair1;
    private Crosshair crosshair2;
    private ChartPanel chpanImage;
    private XYSeriesCollection timeTracesCollection, waveTracesCollection;
    private DatasetTimp data;
    private ColorCodedImageDataset dataset;
    private TgdDataObject dataObject;

    public SpecEditorTopCompNew() {
        data = new DatasetTimp();
        initComponents();
        setName(NbBundle.getMessage(SpecEditorTopCompNew.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
    }

     public SpecEditorTopCompNew(TgdDataObject dataObj) {
        String filename;
        dataObject = dataObj;
        initComponents();
        setName((String)dataObject.getTgd().getFilename());
        filename = (String)dataObject.getTgd().getPath();
        filename = filename.concat("/").concat((String)dataObject.getTgd().getFilename());


//        setName(NbBundle.getMessage(SpecEditorTopCompNew.class, "CTL_StreakLoaderTopComponent"));
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
        data = new DatasetTimp();
        try {
            data.LoadASCIIFile(new File(filename));
            MakeImageChart(MakeXYZDataset());
//            MakeWaveTraceChart(PlotFirstTrace(false));
//            MakeTimeTraceChart(PlotFirstTrace(true));
//            chpanImage = new ChartPanel(chart,true);
//            jPStreakImage.removeAll();
//            chpanImage.setSize(jPStreakImage.getMaximumSize());
//            chpanImage.addChartMouseListener(this);
//
//            jPStreakImage.add(chpanImage);
//            jPStreakImage.repaint();
//            jLTimsteps.setText(Integer.toString(data.GetNt()[0]));
//            jLWavewind.setText(Double.toString(data.GetX2()[data.GetX2().length-1]-data.GetX2()[0]));
//            jLTimeWind.setText(Double.toString(data.GetX()[data.GetX().length-1]-data.GetX()[0]));
//            jLWavesteps.setText(Integer.toString(data.GetNl()[0]));


        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (InstantiationException ex) {
            System.out.println("InstantiationException");
        }

    }

    public SpecEditorTopCompNew(DatasetTimp dataObj) {
        initComponents();
        data = dataObj;
        setName((String)data.GetDatasetName());
        setToolTipText(NbBundle.getMessage(SpecEditorTopCompNew.class, "HINT_StreakLoaderTopComponent"));        
        MakeImageChart(MakeXYZDataset());

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        inputDatasetName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jBMakeDataset = new javax.swing.JButton();
        jBSaveIvoFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPSpecImage = new javax.swing.JPanel();
        jSColum = new javax.swing.JSlider();
        jSRow = new javax.swing.JSlider();
        jPXTrace = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jPYTrace = new javax.swing.JPanel();

        inputDatasetName.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.inputDatasetName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton4.text")); // NOI18N

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(inputDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(inputDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        org.openide.awt.Mnemonics.setLocalizedText(jBMakeDataset, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBMakeDataset.text")); // NOI18N
        jBMakeDataset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMakeDatasetActionPerformed(evt);
            }
        });
        jToolBar1.add(jBMakeDataset);

        org.openide.awt.Mnemonics.setLocalizedText(jBSaveIvoFile, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jBSaveIvoFile.text")); // NOI18N
        jBSaveIvoFile.setEnabled(false);
        jToolBar1.add(jBSaveIvoFile);

        jPSpecImage.setBackground(new java.awt.Color(0, 0, 0));
        jPSpecImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPSpecImage.setMaximumSize(new java.awt.Dimension(450, 380));
        jPSpecImage.setMinimumSize(new java.awt.Dimension(450, 380));
        jPSpecImage.setPreferredSize(new java.awt.Dimension(450, 380));
        jPSpecImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPSpecImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPSpecImageLayout = new javax.swing.GroupLayout(jPSpecImage);
        jPSpecImage.setLayout(jPSpecImageLayout);
        jPSpecImageLayout.setHorizontalGroup(
            jPSpecImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );
        jPSpecImageLayout.setVerticalGroup(
            jPSpecImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );

        jSColum.setMinimum(1);
        jSColum.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSColumStateChanged(evt);
            }
        });

        jSRow.setMinimum(1);
        jSRow.setOrientation(javax.swing.JSlider.VERTICAL);
        jSRow.setInverted(true);
        jSRow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSRowStateChanged(evt);
            }
        });

        jPXTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPXTrace.setMaximumSize(new java.awt.Dimension(380, 170));
        jPXTrace.setMinimumSize(new java.awt.Dimension(380, 170));
        jPXTrace.setPreferredSize(new java.awt.Dimension(380, 170));

        javax.swing.GroupLayout jPXTraceLayout = new javax.swing.GroupLayout(jPXTrace);
        jPXTrace.setLayout(jPXTraceLayout);
        jPXTraceLayout.setHorizontalGroup(
            jPXTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        jPXTraceLayout.setVerticalGroup(
            jPXTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jLabel3.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTextField2.text")); // NOI18N
        jTextField2.setEnabled(false);
        jTextField2.setMinimumSize(new java.awt.Dimension(60, 20));

        jTextField3.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTextField3.text")); // NOI18N
        jTextField3.setEnabled(false);
        jTextField3.setMinimumSize(new java.awt.Dimension(60, 20));

        jTextField4.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTextField4.text")); // NOI18N
        jTextField4.setEnabled(false);
        jTextField4.setMinimumSize(new java.awt.Dimension(60, 20));

        jTextField5.setText(org.openide.util.NbBundle.getMessage(SpecEditorTopCompNew.class, "SpecEditorTopCompNew.jTextField5.text")); // NOI18N
        jTextField5.setEnabled(false);
        jTextField5.setMinimumSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel19Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField2, jTextField3, jTextField4, jTextField5});

        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextField2, jTextField3, jTextField4, jTextField5});

        jPYTrace.setBackground(new java.awt.Color(255, 255, 255));
        jPYTrace.setMaximumSize(new java.awt.Dimension(170, 380));
        jPYTrace.setMinimumSize(new java.awt.Dimension(170, 380));
        jPYTrace.setPreferredSize(new java.awt.Dimension(170, 380));

        javax.swing.GroupLayout jPYTraceLayout = new javax.swing.GroupLayout(jPYTrace);
        jPYTrace.setLayout(jPYTraceLayout);
        jPYTraceLayout.setHorizontalGroup(
            jPYTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );
        jPYTraceLayout.setVerticalGroup(
            jPYTraceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPSpecImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSColum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
                        .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPYTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPXTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPSpecImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSColum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSRow, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPYTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPXTrace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jBMakeDatasetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMakeDatasetActionPerformed

//        JDialog dialog = new JDialog(WindowManager.getDefault().getMainWindow(), "Module Explorer", true);
//        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        dialog.setContentPane(new NewDatasetNameDialog());
//        dialog.setSize(300, 200);
//        dialog.setVisible(true);
//      
    NotifyDescriptor.InputLine datasetNameDialod = new NotifyDescriptor.InputLine(
            "Dataset name",
            "Please specify the name for a dataset");
    Object res = DialogDisplayer.getDefault().notify(datasetNameDialod);

    if (res.equals(NotifyDescriptor.OK_OPTION)){
        DatasetTimp newdataset = new DatasetTimp();
        newdataset.SetDatasetName(datasetNameDialod.getInputText());
        FileObject cachefolder = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getCacheFolder(true);
        } else {
            Confirmation msg = new NotifyDescriptor.Confirmation("Select main project", NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(msg);
        }
        cachefolder = cachefolder.getFileObject(dataObject.getTgd().getCacheFolderName().toString());


        //FileObject folder = Repository.getDefault().getDefaultFileSystem().getRoot();

        FileObject writeTo;
            try {
                writeTo = cachefolder.createData(newdataset.GetDatasetName(), "timpdataset");
                ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                stream.writeObject(newdataset);
                stream.close();
                TimpDatasetDataObject dObj = (TimpDatasetDataObject) DataObject.find(writeTo);
                TgdDataChildren chidrens = (TgdDataChildren) dataObject.getNodeDelegate().getChildren();
                chidrens.addObj(dObj);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

    }

    //TODO ======================
    //Check if all parameters are ok
    //endTODO=====================
    //create timpdataset.

    
  //  InstanceCookie ck = DataObject.find(folder).getNodeDelegate().getLookup()lookup(InstanceCookie.class);



}//GEN-LAST:event_jBMakeDatasetActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jButton1ActionPerformed

private void jPSpecImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPSpecImageMouseClicked
    // TODO add your han   dling code here:
}//GEN-LAST:event_jPSpecImageMouseClicked

private void jSRowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSRowStateChanged
    crosshair2.setValue(dataset.GetImageHeigth()-jSRow.getValue());
    int xIndex = jSRow.getValue() - jSRow.getMinimum();
    XYDataset d = ImageUtilities.extractRowFromImageDataset(dataset, xIndex, "Spec");
    subchart2.getXYPlot().setDataset(d);
}//GEN-LAST:event_jSRowStateChanged

private void jSColumStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSColumStateChanged
    crosshair1.setValue(jSColum.getValue());
    int xIndex = jSColum.getValue() - jSColum.getMinimum();
    XYDataset d = ImageUtilities.extractColumnFromImageDataset(dataset, xIndex, "Spec");
    subchart1.getXYPlot().setDataset(d);
}//GEN-LAST:event_jSColumStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputDatasetName;
    private javax.swing.JButton jBMakeDataset;
    private javax.swing.JButton jBSaveIvoFile;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPSpecImage;
    private javax.swing.JPanel jPXTrace;
    private javax.swing.JPanel jPYTrace;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JSlider jSColum;
    private javax.swing.JSlider jSRow;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized SpecEditorTopCompNew getDefault() {
        if (instance == null) {
            instance = new SpecEditorTopCompNew();
        }
        return instance;
    }

    /**
     * Obtain the StreakLoaderTopComponent instance. Never call {@link #getDefault} directly!
     */

    public static synchronized SpecEditorTopCompNew findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(SpecEditorTopCompNew.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof SpecEditorTopCompNew) {
            return (SpecEditorTopCompNew) win;
        }
        Logger.getLogger(SpecEditorTopCompNew.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return CloneableTopComponent.PERSISTENCE_ALWAYS;
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
            return SpecEditorTopCompNew.getDefault();
        }
    }
    
     private XYSeriesCollection PlotFirstTrace(boolean mode){
        if (mode){
             timeTracesCollection = new XYSeriesCollection();
             XYSeries seria = new XYSeries("TimeTrace");
             for (int j=0; j<data.GetNt()[0]; j++){
                 seria.add(data.GetX()[j], data.GetPsisim()[j]);
             }
            timeTracesCollection.addSeries(seria);
            return timeTracesCollection;         
        }
        else {
            waveTracesCollection = new XYSeriesCollection();
            XYSeries seria = new XYSeries("WaveTrace");
            for (int j=0; j<data.GetNl()[0]; j++){
                seria.add(data.GetX2()[j], data.GetPsisim()[j*data.GetNt()[0]]);
            }
            waveTracesCollection.addSeries(seria);
            return waveTracesCollection;         
            
        }
    }
    
//    private void UpdateSelectedTimeTrace(int index){
//        timeTracesCollection.getSeries(0).clear();
//        for (int j=0; j<data.GetNt()[0]; j++){
//            timeTracesCollection.getSeries(0).add(data.GetX()[j], data.GetPsisim()[index*data.GetNt()[0]+j]);
//        }
//    }
//
//    private void UpdateSelectedWaveTrace(int index){
//        waveTracesCollection.getSeries(0).clear();
//        for (int j=0; j<data.GetNl()[0]; j++){
//            waveTracesCollection.getSeries(0).add(data.GetX2()[j], data.GetPsisim()[j*data.GetNt()[0]+index]);
//        }
//    }
//
//    private void MakeTimeTraceChart(XYSeriesCollection dat){
//        JFreeChart tracechart;
//        tracechart = ChartFactory.createXYLineChart(
//            "Selected trace",
//            "Time (ns)",
//            "Number of counts",
//            dat,
//            PlotOrientation.VERTICAL,
//            false,
//            false,
//            false
//        );
//        tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length-1]);
////        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
//        ChartPanel chpan = new ChartPanel(tracechart,true);
//        chpan.setSize(jPSelectedTimeTrace.getMaximumSize());
//        jPSelectedTimeTrace.removeAll();
//        jPSelectedTimeTrace.add(chpan);
//        jPSelectedTimeTrace.repaint();
//    }

//    private void MakeWaveTraceChart(XYSeriesCollection dat){
//        JFreeChart tracechart;
//        tracechart = ChartFactory.createXYLineChart(
//            "Selected Spectrum",
//            "Wavelength (nm)",
//            "Number of counts",
//            dat,
//            PlotOrientation.VERTICAL,
//            false,
//            false,
//            false
//        );
//        if (data.GetX2()[data.GetX2().length-1]<data.GetX2()[0])
//            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[0]);
//        else
//            tracechart.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[data.GetX2().length-1]);
//        //tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
//        ChartPanel chpan = new ChartPanel(tracechart,true);
//        chpan.setSize(jPSelectedWaveTrace.getMaximumSize());
//        jPSelectedWaveTrace.removeAll();
//        jPSelectedWaveTrace.add(chpan);
//        jPSelectedWaveTrace.repaint();
//    }
    
    private ColorCodedImageDataset MakeXYZDataset(){
        DefaultXYZDataset dataset2 = new DefaultXYZDataset();
        double[] xValues  = new double[data.GetNl()[0]*data.GetNt()[0]];
        double[] yValues  = new double[data.GetNl()[0]*data.GetNt()[0]];
        
        for (int i = 0; i<data.GetNt()[0]; i++){
            for (int j = 0; j<data.GetNl()[0]; j++){
                xValues[j*data.GetNt()[0]+i] = data.GetX2()[j];
                yValues[j*data.GetNt()[0]+i] = data.GetX()[i];
            }
        }
        double[][] chartdata = {xValues,yValues,data.GetPsisim()};
        dataset2.addSeries("Image", chartdata);
        
        dataset = new ColorCodedImageDataset(data.GetNl()[0],data.GetNt()[0],
                data.GetPsisim(), data.GetX2(), data.GetX(), true);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset1) {

        JFreeChart chart_temp = ChartFactory.createScatterPlot(null,
                null, null, dataset1, PlotOrientation.VERTICAL, false, false,
                false);

        PaintScale ps = new RainbowPaintScale(data.GetMinInt(), data.GetMaxInt());
        BufferedImage image = ImageUtilities.createColorCodedImage(this.dataset, ps);

        XYDataImageAnnotation ann = new XYDataImageAnnotation(image, 0,0, 
                dataset.GetImageWidth(), dataset.GetImageHeigth(), true);
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
        return chart_temp;
    }
    
    private void MakeImageChart(ColorCodedImageDataset dataset){
        PaintScale ps = new RainbowPaintScale(data.GetMinInt(), data.GetMaxInt());
        chart = createChart(new XYSeriesCollection());
        //chart.addChangeListener(this);
        chpanImage = new ChartPanel(chart);
        chpanImage.setFillZoomRectangle(true);
        chpanImage.setMouseWheelEnabled(true);
        jPSpecImage.removeAll();
        chpanImage.setSize(jPSpecImage.getMaximumSize());
        jPSpecImage.add(chpanImage);
        jPSpecImage.repaint();

        ImageCrosshairLabelGenerator crossLabGen1 = new ImageCrosshairLabelGenerator(data.GetX2(),false);
        ImageCrosshairLabelGenerator crossLabGen2 = new ImageCrosshairLabelGenerator(data.GetX(),true);

        CrosshairOverlay overlay = new CrosshairOverlay();
        crosshair1 = new Crosshair(0.0);
        crosshair1.setPaint(Color.red);
        crosshair2 = new Crosshair(0.0);
        crosshair2.setPaint(Color.GRAY);
        
        overlay.addDomainCrosshair(crosshair1);
        overlay.addRangeCrosshair(crosshair2);

        chpanImage.addOverlay(overlay);
        crosshair1.setLabelGenerator(crossLabGen1);
        crosshair1.setLabelVisible(true);
        crosshair1.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        crosshair1.setLabelBackgroundPaint(new Color(255, 255, 0, 100));
        crosshair2.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        crosshair2.setLabelGenerator(crossLabGen2);
        crosshair2.setLabelVisible(true);
        crosshair2.setLabelBackgroundPaint(new Color(255, 255, 0, 100));

//        //TODO add creation of the other charts
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        subchart1 = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset1,
            PlotOrientation.HORIZONTAL,
            false,
            false,
            false
        );
        subchart1.getXYPlot().getDomainAxis().setUpperBound(data.GetX()[data.GetX().length-1]);
////        tracechart.getXYPlot().setDomainZeroBaselineVisible(true);
        ChartPanel chpan = new ChartPanel(subchart1,true);
        chpan.setSize(jPYTrace.getMaximumSize());
        jPYTrace.removeAll();
        chpan.setMinimumDrawHeight(0);
        chpan.setMinimumDrawWidth(0);
        jPYTrace.add(chpan);
        jPYTrace.repaint();

        XYPlot plot1 = (XYPlot) subchart1.getPlot();
        plot1.getDomainAxis().setLowerMargin(0.0);
        plot1.getDomainAxis().setUpperMargin(0.0);
        plot1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot1.getDomainAxis().setInverted(true);

        XYSeriesCollection dataset2 = new XYSeriesCollection();
        subchart2 = ChartFactory.createXYLineChart(
            null,
            null,
            null,
            dataset2,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );
        if (data.GetX2()[data.GetX2().length-1]<data.GetX2()[0])
            subchart2.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[0]);
        else
            subchart2.getXYPlot().getDomainAxis().setUpperBound(data.GetX2()[data.GetX2().length-1]);

        XYPlot plot2 = (XYPlot) subchart2.getPlot();
        plot2.getDomainAxis().setLowerMargin(0.0);
        plot2.getDomainAxis().setUpperMargin(0.0);
        plot2.getDomainAxis().setAutoRange(true);
  //      plot2.getDomainAxis().resizeRange(100);
        plot2.getRenderer().setSeriesPaint(0, Color.blue);
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);

        ChartPanel subchart2Panel = new ChartPanel(subchart2,true);
        subchart2Panel.setSize(jPXTrace.getMaximumSize());
        jPXTrace.removeAll();
        subchart2Panel.setMinimumDrawHeight(0);
        subchart2Panel.setMinimumDrawWidth(0);
        jPXTrace.add(subchart2Panel);
        jPXTrace.repaint();

        jSColum.setMaximum(dataset.GetImageWidth()-1);
        jSColum.setMinimum(1);
        jSRow.setMaximum(dataset.GetImageHeigth()-1);
        jSRow.setMinimum(1);


//        chart.setAntiAlias(false);
//        chart.removeLegend();



//        NumberAxis xAxis = new NumberAxis("Wavelengts (nm)");
//        xAxis.setLowerMargin(0.0);
//        xAxis.setUpperMargin(0.0);
////        xAxis.setRange(data.GetX2()[0],data.GetX2()[data.GetX2().length-1]);
//        xAxis.setVisible(false);
//        NumberAxis yAxis = new NumberAxis("Time (ps)");
//        yAxis.setAutoRangeIncludesZero(false);
//        yAxis.setInverted(true);
//        yAxis.setLowerMargin(0.0);
//        yAxis.setUpperMargin(0.0);
// //       yAxis.setRange(data.GetX()[0],data.GetX()[data.GetX().length-1]);
//        yAxis.setVisible(false);
//        XYBlockRenderer renderer = new XYBlockRenderer();
//
//        renderer.setPaintScale(scale);
////        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
//
//        plot.setDomainCrosshairVisible(false);
//        plot.setDomainCrosshairLockedOnData(false);
//        plot.setRangeCrosshairVisible(false);
//        plot.setRangeCrosshairLockedOnData(false);

  
        NumberAxis scaleAxis = new NumberAxis();
        scaleAxis.setAxisLinePaint(Color.black);
        scaleAxis.setTickMarkPaint(Color.black);
        scaleAxis.setRange(data.GetMinInt(),data.GetMaxInt());
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 9));
        PaintScaleLegend legend = new PaintScaleLegend(ps,scaleAxis);
        legend.setAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
    //        legend.setAxisOffset(5.0);
        legend.setMargin(new RectangleInsets(5, 5, 5, 5));
    //        legend.setFrame(new BlockBorder(Color.red));
    //        legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        legend.setStripWidth(15);
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(chart.getBackgroundPaint());
        chart.addSubtitle(legend);
        
    }
    
//    public void chartMouseClicked(ChartMouseEvent event) {
//
//        int mouseX = event.getTrigger().getX();
//        int mouseY = event.getTrigger().getY();
//        Point2D p = this.chpanImage.translateScreenToJava2D(new Point(mouseX, mouseY));
//        XYPlot plot = (XYPlot) this.chart.getPlot();
//        ChartRenderingInfo info = this.chpanImage.getChartRenderingInfo();
//        Rectangle2D dataArea = info.getPlotInfo().getDataArea();
//
//        ValueAxis domainAxis = plot.getDomainAxis();
//        RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
//        ValueAxis rangeAxis = plot.getRangeAxis();
//        RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge();
//
//        int chartX =  (int) (domainAxis.java2DToValue(p.getX(), dataArea, domainAxisEdge));
//        int chartY =  (int) (rangeAxis.java2DToValue(p.getY(), dataArea, rangeAxisEdge));
//
////        System.out.println(chartX+"    "+chartY);
//
//        UpdateSelectedWaveTrace(chartY);
//        UpdateSelectedTimeTrace(chartX);
//
//    }

    public void chartMouseMoved(ChartMouseEvent event) {
//         System.out.println("ChartMouseMoved");
    }
}
