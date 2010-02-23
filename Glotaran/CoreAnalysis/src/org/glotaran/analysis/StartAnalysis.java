/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Formatter;
import org.glotaran.core.main.interfaces.AnalysisInterface;
import org.glotaran.core.main.interfaces.TimpControllerInterface;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.main.structures.DatasetTimp;
import org.glotaran.core.main.structures.TimpResultDataset;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.gta.Tgm;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author jsg210
 */
public class StartAnalysis implements AnalysisInterface {

    private int NO_OF_ITERATIONS = 0;    
    private TimpControllerInterface controller;
    private DatasetTimp[] datasets;
    private TimpResultDataset[] results = null;
    private boolean run;
    private Tgm[] models;
    private TGProject project;

    public StartAnalysis() {
        //TODO: implement constructor
    }

    public void runAnalysis(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences) {
        runAnalysis(gtaOutput, gtaDatasetContainer, gtaModelReference, gtaModelDifferences, null);
    }

    public void runAnalysis(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences, File projectPath) {

        String numIterations = "0";
        NO_OF_ITERATIONS = Integer.parseInt(numIterations);
        if (projectPath != null) {
            try {
            FileObject projectToBeOpened = FileUtil.toFileObject(new File(gtaOutput.getOutputPath()));
            project = (TGProject) ProjectManager.getDefault().findProject(projectToBeOpened);
            } catch (IOException ex) {
                  Exceptions.printStackTrace(ex);
            }
        } else {
            project = (TGProject) OpenProjects.getDefault().getMainProject();
        }

        if (project != null) {
           int numberOfDatasets = gtaDatasetContainer.getDatasets().size();
            datasets = new DatasetTimp[numberOfDatasets];
            for (int i = 0; i < numberOfDatasets; i++) {
                GtaDataset gtaDataset = gtaDatasetContainer.getDatasets().get(i);
                String path = FileUtil.getRelativePath(project.getProjectDirectory(), FileUtil.toFileObject(new File(gtaDataset.getPath())));

                
               // datasets[i] = dataObject.getDatasetTimp();
            }

//            models = new Tgm[nsm.length];
//            for (int i = 0; i < nsm.length; i++) {
//                TgmDataNode node = (TgmDataNode) nsm[i];
//                models[i] = node.getObject().getTgm();
//            }
//            for (int i = 0; i < models.length; i++) {
//                validateModel(models[i]);
//            }

//                try {
//                    //TODO: implement a working busy cursor, or progress indicator
//                    TopComponent.getRegistry().getActivated().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (run) {
                controller = Lookup.getDefault().lookup(TimpControllerInterface.class);
                if (controller != null) {
                    //results = controller.runAnalysis(datasets, models, NO_OF_ITERATIONS);

                } else {
                    CoreErrorMessages.noRFoundException();
                    return;
                }
            } else {
                return;
            }
//                    } finally {
////                        TopComponent.getRegistry().getActivated().setCursor(Cursor.getDefaultCursor());
//                    }

            if (results != null) {
//                try {
//                    while (!folderOK) {
//                        res = DialogDisplayer.getDefault().notify(resultNameDialog);
//                        if (res.equals(NotifyDescriptor.OK_OPTION)) {
//                            //get results directory
//                            resultsfolder = proj.getResultsFolder(true);
//                            if (resultsfolder.getFileObject(resultNameDialog.getInputText()) != null) {
//                                res2 = CoreWarningMessages.folderExistsWarning();
//                                if (res2.equals(NotifyDescriptor.OK_OPTION)) {
//                                    resultsfolder.getFileObject(resultNameDialog.getInputText()).delete();
//                                    folderOK = true;
//                                }
//                            } else {
//                                folderOK = true;
//                            }
//                        } else {
//                            folderOK = true;
//                        }
//                    }
//                    if (res.equals(NotifyDescriptor.OK_OPTION)) {
//                        resultsfolder = resultsfolder.createFolder(resultNameDialog.getInputText());
//                        for (int i = 0; i < results.length; i++) {
//                            TimpResultDataset timpResultDataset = results[i];
//                            timpResultDataset.setType(datasets[i].getType());
//                            //TODO: change this
//                            if (models[0].getDat().getIrfparPanel().getLamda() != null) {
//                                timpResultDataset.setLamdac(models[0].getDat().getIrfparPanel().getLamda());
//                            }
//                            if (datasets[i].getType().equalsIgnoreCase("flim")) {
//                                timpResultDataset.setOrheigh(datasets[i].getOriginalHeight());
//                                timpResultDataset.setOrwidth(datasets[i].getOriginalWidth());
//                                timpResultDataset.setIntenceIm(datasets[i].getIntenceIm().clone());
//                                timpResultDataset.setMaxInt(datasets[i].getMaxInt());
//                                timpResultDataset.setMinInt(datasets[i].getMinInt());
//                                timpResultDataset.setX(datasets[i].getX().clone());
//                                timpResultDataset.setX2(datasets[i].getX2().clone());
//                            }
//                            FileObject writeTo;
//                            writeTo = resultsfolder.createData(resultNameDialog.getInputText() + "_d" + (i + 1) + "_" + timpResultDataset.getDatasetName(), "timpres");
//                            ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
//                            stream.writeObject(timpResultDataset);
//                            stream.close();
//                        }
//                        writeSummary(resultsfolder);
//                    }
//                } catch (IOException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
            } else {
                CoreErrorMessages.noResultsException();
            }
        } else {
            CoreErrorMessages.noMainProjectFound();
        }


    }

    private void writeSummary(FileObject resultsfolder) throws IOException {
        FileObject writeTo;
        writeTo = resultsfolder.createData(resultsfolder.getName(), "summary");
        BufferedWriter output = new BufferedWriter(new FileWriter(FileUtil.toFile(writeTo)));
        //TODO: Complete the summary here:
        output.append("Summary");
        output.newLine();
        output.append("Used dataset(s): ");
        for (int j = 0; j < datasets.length; j++) {
            DatasetTimp dataset = datasets[j];
            if (j > 0) {
                output.append(", ");
            }
            output.append(dataset.getDatasetName());
        }
        output.newLine();
        output.newLine();
        output.append("Used model(s): ");
        for (int j = 0; j < models.length; j++) {
            if (j > 0) {
                output.append(", ");
            }
            output.append(models[j].getDat().getModelName());
        }
        output.newLine();
        output.newLine();

        output.append("Number of iterations: ");
        output.append(String.valueOf(NO_OF_ITERATIONS));
        output.newLine();
        output.newLine();

        output.append("R Call fot TIMP function initModel: ");
        output.newLine();
        ArrayList<String> list = controller.getInitModelCalls();
        for (String string : list) {
            output.append(string);
            output.newLine();
        }
        output.newLine();
        output.append("R Call fot TIMP function fitModel: ");
        output.newLine();
        output.write(controller.getFitModelCall());
        output.newLine();
        output.newLine();

        output.append("Final residual standard error: ");
        output.append((new Formatter().format("%g", results[0].getRms())).toString());
        output.newLine();
        output.newLine();

        String[] slots = {"getKineticParameters", "getSpectralParameters", "getIrfpar", "getSpecdisppar", "getParmu", "getPartau", "getKinscal", "getPrel", "getJvec"};
        String[] slotsName = {"Kinetic parameters", "Spectral parameters", "Irf parameters", "Specdisppar", "Parmu", "Partau", "Kinscal", "Prel", "J vector"};
        double[] params = null;

        for (int k = 0; k < slots.length; k++) {
            try {
                try {
                    for (int i = 0; i < results.length; i++) {
                        params = (double[]) results[i].getClass().getMethod(slots[k], null).invoke(results[i], null);
                        if (params != null) {

                            output.append("Estimated " + slotsName[k] + ": ");
                            output.newLine();
                            output.append("Dataset" + (i + 1) + ": ");
                            for (int j = 0; j < params.length / 2; j++) {
                                if (j > 0) {
                                    output.append(", ");
                                }
                                output.append((new Formatter().format("%g", params[j])).toString());
                            }
                            output.newLine();
                            output.append("Standard errors: ");
                            for (int j = 0; j < params.length / 2; j++) {
                                if (j > 0) {
                                    output.append(", ");
                                }
                                output.append((new Formatter().format("%g",
                                        params[j + params.length / 2])).toString());
                            }
                            output.newLine();
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
        output.close();
    }

    private void validateModel(Tgm tgm) {
        String feedback = null;
        run = true;
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
        if (feedback != null) {
            NotifyDescriptor errorMessage = new NotifyDescriptor.Exception(
                    new Exception("Invalid Model: \n"
                    + "" + feedback + "\n"
                    + "Analysis was not started."));
            DialogDisplayer.getDefault().notify(errorMessage);
        }
    }
}
