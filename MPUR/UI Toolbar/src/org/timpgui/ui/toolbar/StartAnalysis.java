/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.vu.nat.tgmfilesupport.TgmDataNode;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.timpgui.projectmanagement.SelectedDatasetsViewTopComponent;
import org.timpgui.projectmanagement.SelectedModelsViewTopComponent;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.TgdDataObject;
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

       service.runAnalysis(datasets, models, NO_OF_ITERATIONS);

        } 
}
