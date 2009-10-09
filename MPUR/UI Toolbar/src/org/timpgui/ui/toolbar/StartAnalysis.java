/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import nl.vu.nat.tgmfilesupport.TgmDataNode;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.options.ExternalPanel;
import org.timpgui.projectmanagement.SelectedDatasetsViewTopComponent;
import org.timpgui.projectmanagement.SelectedModelsViewTopComponent;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.tgproject.nodes.TimpDatasetNode;
import org.timpgui.timpinterface.TimpInterface;

public final class StartAnalysis implements ActionListener {

    private int NO_OF_ITERATIONS = 0;
    Tgm[] models;
    private TimpInterface service;
    private DatasetTimp[] datasets;
    private TimpResultDataset[] results = null;
    private Object resultFromDialog = null;

    public void actionPerformed(ActionEvent e) {

        //Add JRI to library path
        Preferences pref = NbPreferences.forModule(ExternalPanel.class);
        String pathToR = pref.get("pathToRInstallation", "");
        String pathToJRI = pref.get("pathToJRILibrary", "");

        try {
            LibPathHacker.addDir(pathToR);
            LibPathHacker.addDir(pathToJRI);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

            NotifyDescriptor.InputLine numIterations = new NotifyDescriptor.InputLine(
                    "Number of iterations",
                    "Please specify the number of iterations for this analysis");
            Object res1 = DialogDisplayer.getDefault().notify(numIterations);
            if (res1.equals(NotifyDescriptor.OK_OPTION)) {
                try {
                    NO_OF_ITERATIONS = Integer.parseInt(numIterations.getInputText());
                }catch (Exception exept) {
                    NotifyDescriptor noNumItMessage = new NotifyDescriptor.Message(
                                "Incorrect number of iterations. 0 iterations will be done.");
                        DialogDisplayer.getDefault().notify(noNumItMessage);
                }

                final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
                FileObject resultsfolder;
                if (proj != null) {
                    service = Lookup.getDefault().lookup(TimpInterface.class);
                    SelectedDatasetsViewTopComponent tcd = (SelectedDatasetsViewTopComponent) WindowManager.getDefault().findTopComponent("SelectedDatasetsViewTopComponent");
                    Node[] nsd = tcd.getExplorerManager().getRootContext().getChildren().getNodes();
                    datasets = new DatasetTimp[nsd.length];
                    for (int i = 0; i < nsd.length; i++) {
                        TimpDatasetNode node = (TimpDatasetNode) nsd[i];
                        TimpDatasetDataObject dataObject = node.getLookup().lookup(TimpDatasetDataObject.class);
                        datasets[i] = dataObject.getDatasetTimp();
                    }

                    SelectedModelsViewTopComponent tcm = (SelectedModelsViewTopComponent) WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent");
                    Node[] nsm = tcm.getExplorerManager().getRootContext().getChildren().getNodes();
                    models = new Tgm[nsm.length];
                    for (int i = 0; i < nsm.length; i++) {
                        TgmDataNode node = (TgmDataNode) nsm[i];
                        models[i] = node.getObject().getTgm();
                    }
                    try {
                        //TODO: implement a working busy cursor, or progress indicator
                        TopComponent.getRegistry().getActivated().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        results = service.runAnalysis(datasets, models, NO_OF_ITERATIONS);
                    } finally {
                        TopComponent.getRegistry().getActivated().setCursor(Cursor.getDefaultCursor());
                    }
                    if (results != null) {
                        NotifyDescriptor.InputLine resultNameDialog = new NotifyDescriptor.InputLine(
                                "Analysys name",
                                "Please specify the name for results folder");
                        Object res = DialogDisplayer.getDefault().notify(resultNameDialog);
                        if (res.equals(NotifyDescriptor.OK_OPTION)) {
                            //get results directory
                            resultsfolder = proj.getResultsFolder(true);
                            try {
                                //todo resove problem with same name for directory
                                resultsfolder = resultsfolder.createFolder(resultNameDialog.getInputText());
                                for (int i = 0; i < results.length; i++) {
                                    TimpResultDataset timpResultDataset = results[i];
                                    timpResultDataset.setType(datasets[i].getType());
                                    //TODO: change this
                                    timpResultDataset.setLamdac(models[0].getDat().getIrfparPanel().getLamda());
                                    if (datasets[i].getType().equalsIgnoreCase("flim")) {
                                        timpResultDataset.setOrheigh(datasets[i].getOrigHeigh()[0]);
                                        timpResultDataset.setOrwidth(datasets[i].getOrigWidth()[0]);
                                        timpResultDataset.setIntenceIm(datasets[i].getIntenceIm().clone());
                                        timpResultDataset.setMaxInt(datasets[i].getMaxInt());
                                        timpResultDataset.setMinInt(datasets[i].getMinInt());
                                        timpResultDataset.setX(datasets[i].getX().clone());
                                        timpResultDataset.setX2(datasets[i].getX2().clone());
                                    }
                                    FileObject writeTo;
                                    try {
                                        writeTo = resultsfolder.createData("dataset" + (i + 1) + "_" + timpResultDataset.getDatasetName(), "timpres");
                                        ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                                        stream.writeObject(timpResultDataset);
                                        stream.close();
                                        writeTo = resultsfolder.createData(resultsfolder.getName() + "_summary", "txt");
                                        BufferedWriter output = new BufferedWriter(new FileWriter(FileUtil.toFile(writeTo)));
                                        //TODO: Complete the summary here:

                                        output.append("Summary");
                                        output.newLine();
                                        output.append("Used dataset(s): ");
                                        for (int j = 0; j < datasets.length; j++) {
                                            DatasetTimp dataset = datasets[i];
                                            output.append(dataset.getDatasetName());
                                            output.newLine();
                                        }
                                        output.append("Used model(s): ");
                                        for (int j = 0; j < nsm.length; j++) {
                                            output.append(nsm[i].getName());
                                            output.newLine();
                                        }
                                        output.append("Number of iterations: ");
                                        output.append(String.valueOf(NO_OF_ITERATIONS));
                                        output.newLine();
                                        ArrayList<String> list = service.getInitModelCalls();
                                        for (String string : list) {
                                            output.append(string);
                                            output.newLine();
                                        }
                                        output.write(service.getFitModelCall());
                                        output.close();
                                    } catch (IOException ex) {
                                        Exceptions.printStackTrace(ex);
                                    }
                                }
                            } catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }

                        }
                    } else {
                        NotifyDescriptor errorMessage = new NotifyDescriptor.Exception(
                                new Exception("The analysis did not return valid results. Try again with different paramters please."));
                        DialogDisplayer.getDefault().notify(errorMessage);
                    }

                } else {
                    NotifyDescriptor errorMessage = new NotifyDescriptor.Exception(
                            new Exception("Please select your main project."));
                    DialogDisplayer.getDefault().notify(errorMessage);
                }
            }
        }
    }
