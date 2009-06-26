/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;
import org.timpgui.timpinterface.TimpInterface;


public final class StartAnalysis implements ActionListener {
    private TimpInterface service;
    private Tgm[] models;
    private DatasetTimp[] datasets;
    private final int ITERATIONS = 5;

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        service = Lookup.getDefault().lookup(TimpInterface.class);
        //Result<TgmDataNode> result = WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent").getLookup().lookupResult(TgmDataNode.class);
        Lookup.Template datasetsTemplate = new Lookup.Template(TimpDatasetDataObject.class);
        Lookup.Result<TimpDatasetDataObject> datasetsLkp =  WindowManager.getDefault().findTopComponent("SelectedDatasetsViewTopComponent").getLookup().lookup(datasetsTemplate);
        Collection<? extends TimpDatasetDataObject> datasetsCol = datasetsLkp.allInstances();
        TimpDatasetDataObject[] datasetArr = datasetsCol.toArray(new TimpDatasetDataObject[datasetsCol.size()]);
        for (int i = 0; i < datasetArr.length; i++) {
            datasets[i] = datasetArr[i].getDatasetTimp();
        }

        Lookup.Template modelsTemplate = new Lookup.Template(TgmDataObject.class);
        Lookup.Result<TgmDataObject> modelsLkp =  WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent").getLookup().lookup(modelsTemplate);
        Collection<? extends TgmDataObject> modelsCol = modelsLkp.allInstances();
        TgmDataObject[] modelArr = modelsCol.toArray(new TgmDataObject[modelsCol.size()]);
        for (int i = 0; i < modelArr.length; i++) {
            models[i] = modelArr[i].getTgm();
        }

        service.runAnalysis(datasets, models, ITERATIONS);
        }



//        root.getLookup().lookup(TgmDataObject.class);
//
//        Children children = root.getChildren();
//        for (int i = 0; i < children.getNodesCount(); i++) {
//            Node n = children.getNodeAt(i);
//             DataObject  dob = (DataObject) n.getCookie()
//            if (dob == null) {
//                TgmDataObject tgd = (TgmDataObject)dob;
//            } else {
//                // could also get all files in the data object, if desired:
//                FileObject fo = (FileObject) dob.getPrimaryFile();
//                fo.
//            // do something with fo
//            }

}