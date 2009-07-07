/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import nl.vu.nat.tgmfilesupport.TgmDataNode;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import org.timpgui.projectmanagement.SelectedDatasetsViewTopComponent;
import org.timpgui.projectmanagement.SelectedModelsViewTopComponent;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.tgproject.nodes.TimpDatasetNode;
import org.timpgui.timpinterface.TimpInterface;

public final class StartAnalysis implements ActionListener {

    final static int NO_OF_ITERATIONS = 1;
    Tgm[] models;
    private TimpInterface service;
    private DatasetTimp[] datasets;


    public void actionPerformed(ActionEvent e) {

        // TODO implement action body
        final TGProject proj = (TGProject)OpenProjects.getDefault().getMainProject();
        FileObject resultsfolder;
        if (proj!=null){
            service = Lookup.getDefault().lookup(TimpInterface.class);
            SelectedDatasetsViewTopComponent tcd = (SelectedDatasetsViewTopComponent)WindowManager.getDefault().findTopComponent("SelectedDatasetsViewTopComponent");
            Node[] nsd = tcd.getExplorerManager().getRootContext().getChildren().getNodes();
            datasets = new DatasetTimp[nsd.length];
             for (int i = 0; i < nsd.length; i++) {
                TimpDatasetNode node = (TimpDatasetNode) nsd[i];
                 TimpDatasetDataObject dataObject = node.getLookup().lookup(TimpDatasetDataObject.class);
                 datasets[i] = dataObject.getDatasetTimp();
             }

             SelectedModelsViewTopComponent tcm = (SelectedModelsViewTopComponent)WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent");
             Node[] nsm = tcm.getExplorerManager().getRootContext().getChildren().getNodes();
             models = new Tgm[nsm.length];
             for (int i = 0; i < nsm.length; i++) {
                 TgmDataNode node = (TgmDataNode) nsm[i];
                 models[i] = node.getObject().getTgm();
            }
            TimpResultDataset[] results = service.runAnalysis(datasets, models, NO_OF_ITERATIONS);
            if (results != null){
                NotifyDescriptor.InputLine resultNameDialog = new NotifyDescriptor.InputLine(
                     "Analysys name",
                     "Please specify the name for results folder");
                Object res = DialogDisplayer.getDefault().notify(resultNameDialog);
                if (res.equals(NotifyDescriptor.OK_OPTION)){
                    //get results directory
                    resultsfolder = proj.getResultsFolder(true);
                    try {
                        //todo resove problem with same name for directory
                        resultsfolder = resultsfolder.createFolder(resultNameDialog.getInputText());
                        for (int i = 0; i < results.length; i++) {
                            TimpResultDataset timpResultDataset = results[i];
                            timpResultDataset.setType(datasets[i].getType());
                            FileObject writeTo;
                               try {
                                   writeTo = resultsfolder.createData("dataset"+(i+1)+"_"+timpResultDataset.getDatasetName(), "timpres");
                                   ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                                   stream.writeObject(timpResultDataset);
                                   stream.close();
                               } catch (IOException ex) {
                                   Exceptions.printStackTrace(ex);
                               }
                        }
                    }
                    catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
           }
           else {
               NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                        new Exception("Analysis didn't returne anything try onece again :)"));
               DialogDisplayer.getDefault().notify(errorMessage);
           }

        }
        else {
            NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                    new Exception("Please select main project"));
            DialogDisplayer.getDefault().notify(errorMessage);
        }
     }
}
