/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.analysis.action;

import org.glotaran.gtafilesupport.GtaDataObject;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.glotaran.analysis.AnalysisWorker;
import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.gta.GtaProjectScheme;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.FileOwnerQuery;
import org.openide.loaders.DataObject;
import org.openide.util.Cancellable;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

public final class AnalysisAction implements ActionListener {

    private final DataObject context;
    private GtaProjectScheme scheme;
    private TGProject project;
    private final static RequestProcessor RP = new RequestProcessor("interruptible tasks", 1, true);
    private final static Logger LOG = Logger.getLogger(AnalysisAction.class.getName());
    private RequestProcessor.Task theTask = null;

    public AnalysisAction(DataObject context) {
        this.context = context;
        this.project = (TGProject) FileOwnerQuery.getOwner(context.getPrimaryFile());
    }

    public void actionPerformed(ActionEvent ev) {

        Runnable runnable = null;

        if (ev.getActionCommand().equalsIgnoreCase("Run Analysis")) {
            if (context instanceof GtaDataObject) {
                scheme = ((GtaDataObject) context).getProgectScheme();

                final ProgressHandle ph = ProgressHandleFactory.createHandle("Running analysis " + context.getPrimaryFile().getName() + " for project " + project.getProjectDirectory().getName(), new Cancellable() {

                    public boolean cancel() {
                        return handleCancel();
                    }
                });

                if (isRunnable(scheme)) {
                    for (GtaOutput output : scheme.getOutput()) {
                        for (GtaDatasetContainer datasetContainer : getConnectedDatasetContainers(output)) {
                            for (GtaModelReference modelReference : getConnectedModelreferences(datasetContainer)) {
                                GtaModelDifferences modelDifferences = getModelDifferences(modelReference, datasetContainer);
                                //TODO:
                                if (output.getOutputPath() != null) {
                                    runnable = new AnalysisWorker(project, output, datasetContainer, modelReference, modelDifferences, ph);
                                    theTask = RP.create(runnable); //the task is not started yet

                                    theTask.addTaskListener(new TaskListener() {

                                        public void taskFinished(Task task) {
                                            ph.finish();
                                        }
                                    });

                                    theTask.schedule(0); //start the task
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isRunnable(GtaProjectScheme scheme) {
        boolean runnable = false;
        if (scheme.getOutput() != null) {
            if (scheme.getOutput().size() > 0) {
                for (GtaOutput output : scheme.getOutput()) {
                    if (hasValidConnection(output)) {
                        GtaDatasetContainer datasetContainer = getDatasetContainer(output);
                    }
                }
            }
        }
        return true;
    }

    private boolean handleCancel() {
        LOG.info("handleCancel");
        if (null == theTask) {
            return false;
        }

        return theTask.cancel();
    }

    private boolean hasValidConnection(Object object) {
        boolean valid = false;
        if (scheme.getConnection() != null) {
            if (object instanceof GtaOutput) {
                for (GtaConnection connection : scheme.getConnection()) {
                    if (connection.isActive()) {
                        valid = ((GtaOutput) object).getId().equals(connection.getTargetID());
                    }
                }
            }
            if (object instanceof GtaDatasetContainer) {
                valid = true;
            }
            if (object instanceof GtaModelReference) {
                valid = true;
            }
        }
        return valid;
    }

    private GtaDatasetContainer getDatasetContainer(Object object) {
        if (object instanceof GtaOutput) {
        }
        return null;
    }

//    private void startAnalysis(GtaOutput output, GtaDatasetContainer datasetContainer, GtaModelReference modelReference, GtaModelDifferences modelDifferences) {
//        //TODO: run the worker object in a seperate thread
//        boolean folderOK = false;
//        if (output.getOutputPath() != null) {
//            Runnable worker = new AnalysisWorker(project, output, datasetContainer, modelReference, modelDifferences);
//            RequestProcessor.Task myTask = RequestProcessor.getDefault().post(worker);
////        Cancellable myCancellable = ...
////        ProgressHandle myProgressHandle =
////        ProgressHandleFactory.createHandle("Cooking noodles, please wait...", myCancellable );
////        myProgressHandle.start();
////        myProgressHandle.finish();
//
//        }
//    }
    private ArrayList<GtaDatasetContainer> getConnectedDatasetContainers(GtaOutput output) {
        ArrayList<GtaDatasetContainer> datasetContainers = new ArrayList<GtaDatasetContainer>();
        for (GtaConnection connection : scheme.getConnection()) {
            if (connection.getTargetID().equalsIgnoreCase(output.getId())) {
                datasetContainers.add(getConnectedDatasetContainer(connection.getSourceID()));
            }
        }
        return datasetContainers;
    }

    private GtaDatasetContainer getConnectedDatasetContainer(String sourceID) {
        GtaDatasetContainer connectedDatasetContainer = null;
        for (GtaDatasetContainer container : scheme.getDatasetContainer()) {
            if (container.getId().equalsIgnoreCase(sourceID)) {
                connectedDatasetContainer = container;
            }
        }
        return connectedDatasetContainer;
    }

    private ArrayList<GtaModelReference> getConnectedModelreferences(GtaDatasetContainer datasetContainer) {
        ArrayList<GtaModelReference> models = new ArrayList<GtaModelReference>();
        for (GtaConnection connection : scheme.getConnection()) {
            if (connection.getTargetID().equalsIgnoreCase(datasetContainer.getId())) {
                models.add(getConnectedModelreference(connection.getSourceID()));
            }
        }

        return models;
    }

    private GtaModelReference getConnectedModelreference(String sourceID) {
        GtaModelReference connectedModelReference = null;
        for (GtaModelReference model : scheme.getModel()) {
            if (model.getId().equalsIgnoreCase(sourceID)) {
                connectedModelReference = model;
            }
        }
        return connectedModelReference;
    }

    private GtaModelDifferences getModelDifferences(GtaModelReference modelReference, GtaDatasetContainer datasetContainer) {
        GtaModelDifferences modelDifferences = null;
        for (GtaConnection connection : scheme.getConnection()) {
            if (connection.isActive() && connection.getModelDifferences() != null) {
                if (connection.getTargetID().equalsIgnoreCase(datasetContainer.getId())
                        && connection.getSourceID().equalsIgnoreCase(modelReference.getId())) {
                    modelDifferences = connection.getModelDifferences();
                }
            }
        }
        return modelDifferences;
    }
}
