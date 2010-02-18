/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.filesupport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import org.glotaran.core.main.interfaces.AnalysisInterface;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDiffContainer;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.gta.GtaProjectScheme;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;

public final class RunAnalysis implements ActionListener {

    private final DataObject context;
    private GtaProjectScheme scheme;

    public RunAnalysis(DataObject context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        if (ev.getActionCommand().equalsIgnoreCase("Start Analysis")) {
            if (context instanceof GtaDataObject) {
                scheme = ((GtaDataObject) context).getProgectScheme();
                if (isRunnable(scheme)) {
                    for(GtaOutput output : scheme.getOutput()) {
                        for(GtaDatasetContainer datasetContainer : getConnectedDatasetContainers()) {
                            for(GtaModelReference modelReference : getConnectedModelreferences()) {
                                GtaModelDiffContainer modelDiffContainer = getModelDiffContainer(modelReference, datasetContainer);
                                startAnalysis(output, datasetContainer, modelReference);
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
        return runnable;
    }

    private boolean hasValidConnection(Object object) {
        boolean valid = false;
        if (scheme.getConnection() != null) {
            if (object instanceof GtaOutput) {
                for (GtaConnection connection : scheme.getConnection()) {
                    if(connection.isActive()) {
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
        if(object instanceof GtaOutput) {

        }
        return null;
    }

    private void startAnalysis(GtaOutput output,GtaDatasetContainer datasetContainer,GtaModelReference modelReference) {
        //TODO: create a runnable for every analysis and send it off somewhere
        //TODO: use "output", "datasetContainer and "modelReference" to start an analysis
        //TODO: lookup the StartAnalyis Interface
        AnalysisInterface controller = Lookup.getDefault().lookup(AnalysisInterface.class);

        //controller.runAnalysis();

    }

    private ArrayList<GtaDatasetContainer> getConnectedDatasetContainers() {
        ArrayList<GtaDatasetContainer> datasetContainers = new ArrayList<GtaDatasetContainer>();
        datasetContainers.add(getConnectedDatasetContainer());
        return datasetContainers;
    }

    private GtaDatasetContainer getConnectedDatasetContainer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private ArrayList<GtaModelReference> getConnectedModelreferences() {
        ArrayList<GtaModelReference> models = new ArrayList<GtaModelReference>();
        models.add(getConnectedModelreference());
        return models;
    }

    private GtaModelReference getConnectedModelreference() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private GtaModelDiffContainer getModelDiffContainer(GtaModelReference modelReference, GtaDatasetContainer datasetContainer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
