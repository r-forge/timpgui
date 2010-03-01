/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Formatter;
import org.glotaran.core.interfaces.TimpControllerInterface;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.models.structures.DatasetTimp;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.messages.CoreWarningMessages;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class AnalysisWorker implements Runnable {

    private int NO_OF_ITERATIONS = 0;
    private TimpControllerInterface controller;
    private DatasetTimp[] datasets;
    private Tgm[] models = new Tgm[1];
    private TimpResultDataset[] results = null;
    private TGProject project;
    private FileObject resultsfolder, outputfolder;
    private String outputPath;
    private GtaOutput output;
    private GtaDatasetContainer datasetContainer;
    private GtaModelReference modelReference;
    private GtaModelDifferences modelDifferences;
    private File outputFile;


    public AnalysisWorker() {
        //TODO: implement constructor
    }

    public AnalysisWorker(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences) {
        this.output = gtaOutput;
        this.datasetContainer = gtaDatasetContainer;
        this.modelReference = gtaModelReference;
        this.modelDifferences = gtaModelDifferences;
        this.outputFile = null;
    }

    public void AnalysisWorker(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences, File outputFileLocation) {
        this.output = gtaOutput;
        this.datasetContainer = gtaDatasetContainer;
        this.modelReference = gtaModelReference;
        this.modelDifferences = gtaModelDifferences;
        this.outputFile = outputFileLocation;

    }

    private DatasetTimp[] getDatasets(GtaDatasetContainer gtaDatasetContainer) {
        TimpDatasetDataObject timpDatasetDO = null;
        int numberOfDatasets = gtaDatasetContainer.getDatasets().size();
        datasets = new DatasetTimp[numberOfDatasets];
        for (int i = 0; i < numberOfDatasets; i++) {
            GtaDataset gtaDataset = gtaDatasetContainer.getDatasets().get(i);
            String path = project.getProjectDirectory() + File.separator + gtaDataset.getPath();
            try {
                File datasetF = new File(path);
                FileObject datasetFO = FileUtil.createData(datasetF);
                DataObject datasetDO = DataObject.find(datasetFO);
                if (datasetDO != null) {
                    timpDatasetDO = (TimpDatasetDataObject) datasetDO;
                    datasets[i] = timpDatasetDO.getDatasetTimp();
                }
            } catch (DataObjectExistsException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return datasets;
    }

    private Tgm getModel(GtaModelReference gtaModelReference) {
        TgmDataObject tgmDO = null;
        Tgm model = null;
        String path = project.getProjectDirectory() + File.separator + gtaModelReference.getPath();
        try {
            File datasetF = new File(path);
            FileObject datasetFO = FileUtil.createData(datasetF);
            DataObject datasetDO = DataObject.find(datasetFO);
            if (datasetDO != null) {
                tgmDO = (TgmDataObject) datasetDO;
                model = tgmDO.getTgm();
            }
        } catch (DataObjectExistsException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return model;
    }

    private boolean isValidAnalysis(DatasetTimp[] datasets, Tgm[] models) {
        String feedback = null;
        boolean run = true;
        for (DatasetTimp dataset : datasets) {
            if (dataset==null){
                run = false;
            }
        }
        for (Tgm tgm : models) {
            if (!tgm.getDat().getIrfparPanel().isMirf()) {
                if (tgm.getDat().getIrfparPanel().getParmu() != null || tgm.getDat().getIrfparPanel().getPartau() != null) {
                    if (!tgm.getDat().getIrfparPanel().getParmu().isEmpty() || !tgm.getDat().getIrfparPanel().getPartau().isEmpty()) {
                        Double test = tgm.getDat().getIrfparPanel().getLamda();
                        if (test == null || test.isNaN()) {
                            run = false;
                            feedback = "Parmu or Partau specified but no center wavelength was specified.";
                        }
                    }
                }
            }

        }
        if (feedback != null) {
            NotifyDescriptor errorMessage = new NotifyDescriptor.Exception(
                    new Exception("Invalid Model: \n"
                    + "" + feedback + "\n"
                    + "Analysis was not started."));
            DialogDisplayer.getDefault().notify(errorMessage);
        }
        return run;
    }

    private void writeSummary(FileObject resultsfolder) throws IOException{
        FileObject writeTo;
        writeTo = resultsfolder.createData(resultsfolder.getName(), "summary");
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(FileUtil.toFile(writeTo)));
        //TODO: Complete the summary here:
        outputWriter.append("Summary");
        outputWriter.newLine();
        outputWriter.append("Used dataset(s): ");
        for (int j = 0; j < datasets.length; j++) {
            DatasetTimp dataset = datasets[j];
            if (j>0) {
                outputWriter.append(", ");
            }
            outputWriter.append(dataset.getDatasetName());
        }
        outputWriter.newLine();
        outputWriter.newLine();
        outputWriter.append("Used model(s): ");
        for (int j = 0; j < models.length; j++) {
            if (j>0) {
                outputWriter.append(", ");
            }
            outputWriter.append(models[j].getDat().getModelName());
        }
        outputWriter.newLine();outputWriter.newLine();

        outputWriter.append("Number of iterations: ");
        outputWriter.append(String.valueOf(NO_OF_ITERATIONS));
        outputWriter.newLine();outputWriter.newLine();

        outputWriter.append("R Call fot TIMP function initModel: ");
        outputWriter.newLine();
        ArrayList<String> list = controller.getInitModelCalls();
        for (String string : list) {
            outputWriter.append(string);
            outputWriter.newLine();
        }
        outputWriter.newLine();
        outputWriter.append("R Call fot TIMP function fitModel: ");
        outputWriter.newLine();
        outputWriter.write(controller.getFitModelCall());
        outputWriter.newLine();
        outputWriter.newLine();

        outputWriter.append("Final residual standard error: ");
        outputWriter.append((new Formatter().format("%g",results[0].getRms())).toString());
        outputWriter.newLine();outputWriter.newLine();

        String[] slots = {"getKineticParameters","getSpectralParameters","getIrfpar","getSpecdisppar","getParmu","getPartau","getKinscal","getPrel","getJvec"};
        String[] slotsName = {"Kinetic parameters","Spectral parameters","Irf parameters","Specdisppar","Parmu","Partau","Kinscal","Prel", "J vector"};
        double[] params = null;

        for (int k = 0; k < slots.length; k++) {
            try {
                try {
                    for (int i = 0; i < results.length; i++) {
                        //TODO: verify the next line
                        //params = (double[]) results[i].getClass().getMethod(slots[k], null).invoke(results[i], null);
                        params = (double[]) results[i].getClass().getMethod(slots[k], new Class[] { results[i].getClass().getClass() }).invoke(results[i], new Object[] { results });
                        if (params != null) {

                            outputWriter.append("Estimated "+slotsName[k]+": ");
                            outputWriter.newLine();
                            outputWriter.append("Dataset" + (i + 1) + ": ");
                            for (int j = 0; j < params.length / 2; j++) {
                                if (j > 0) {
                                    outputWriter.append(", ");
                                }
                                outputWriter.append((new Formatter().format("%g", params[j])).toString());
                            }
                            outputWriter.newLine();
                            outputWriter.append("Standard errors: ");
                            for (int j = 0; j < params.length / 2; j++) {
                                if (j > 0) {
                                    outputWriter.append(", ");
                                }
                                outputWriter.append((new Formatter().format("%g",
                                        params[j + params.length/2])).toString());
                            }
                            outputWriter.newLine();
                        }
                    }
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InvocationTargetException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            } catch (SecurityException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        outputWriter.close();
    }

    public void run() {
         String numIterations = "0";
        NO_OF_ITERATIONS = Integer.parseInt(numIterations);
        boolean folderOK = false;

        if (outputFile != null) {
            try {
                FileObject projectToBeOpened = FileUtil.toFileObject(new File(output.getOutputPath()));
                project = (TGProject) ProjectManager.getDefault().findProject(projectToBeOpened);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            project = (TGProject) OpenProjects.getDefault().getMainProject();
        }
        TimpControllerInterface timpcontroller;

        if (project != null) {

            datasets = getDatasets(datasetContainer);

            models[0] = getModel(modelReference);

            if (isValidAnalysis(datasets, models)) {
                //TODO: implements this
                results = null;
            } else {
                return;
            }

            resultsfolder = project.getResultsFolder(true);
            if (output.getOutputPath() != null) {
                outputPath = output.getOutputPath();
                folderOK = true;
            } else {
                //TODO: change this path
                outputPath = "testResults";
                folderOK = false;
            }

            if (resultsfolder.getFileObject(outputPath) != null) {
                Object notifyDialog = CoreWarningMessages.folderExistsWarning();
                if (notifyDialog.equals(NotifyDescriptor.OK_OPTION)) {
                    try {
                        resultsfolder.getFileObject(output.getOutputPath()).delete();
                        outputfolder = resultsfolder.createFolder(output.getOutputPath());
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
            }

            if (results!=null) {
            for (int i = 0; i < results.length; i++) {
                TimpResultDataset timpResultDataset = results[i];
                timpResultDataset.setType(datasets[i].getType());

                if (models[0].getDat().getIrfparPanel().getLamda() != null) {
                    timpResultDataset.setLamdac(models[0].getDat().getIrfparPanel().getLamda());
                }

                if (datasets[i].getType().equalsIgnoreCase("flim")) {
                    timpResultDataset.setOrheigh(datasets[i].getOriginalHeight());
                    timpResultDataset.setOrwidth(datasets[i].getOriginalWidth());
                    timpResultDataset.setIntenceIm(datasets[i].getIntenceIm().clone());
                    timpResultDataset.setMaxInt(datasets[i].getMaxInt());
                    timpResultDataset.setMinInt(datasets[i].getMinInt());
                    timpResultDataset.setX(datasets[i].getX().clone());
                    timpResultDataset.setX2(datasets[i].getX2().clone());
                }

                FileObject writeTo;
                try {
                    writeTo = outputfolder.createData(resultsfolder.getName() + "_d" + (i + 1) + "_" + timpResultDataset.getDatasetName(), "timpres");
                    ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                    stream.writeObject(timpResultDataset);
                    stream.close();
                    writeSummary(outputfolder);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
            }
        }
    }
}



   