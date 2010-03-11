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
import org.glotaran.analysis.support.InitModel;
import org.glotaran.core.interfaces.TimpControllerInterface;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.models.gta.GtaChangesModel;
import org.glotaran.core.models.structures.DatasetTimp;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaLinkCLP;
import org.glotaran.core.models.gta.GtaModelDiffContainer;
import org.glotaran.core.models.gta.GtaModelDiffDO;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.results.Dataset;
import org.glotaran.core.models.results.DatasetRelation;
import org.glotaran.core.models.results.OutputFile;
import org.glotaran.core.models.results.Results;
import org.glotaran.core.models.results.Summary;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import static java.lang.Math.floor;

/**
 *
 * @author jsg210
 */
public class AnalysisWorker implements Runnable {

    private int NO_OF_ITERATIONS = 0;
    private DatasetTimp[] datasets;
    private TimpResultDataset[] results = null;
    private GtaOutput output;
    private GtaDatasetContainer datasetContainer;
    private GtaModelReference modelReference;
    private GtaModelDifferences modelDifferences;
    private FileObject resultsfolder;
    private FileObject writeTo;
    private TGProject project;
    private TimpControllerInterface timpcontroller;
    private ArrayList<String> modelCalls = new ArrayList<String>();
    private String fitModelCall;
    private int numIterations;
    private ArrayList<Double[]> relationsList = new ArrayList<Double[]>();

    public AnalysisWorker() {
        //TODO: implement constructor
    }

    public AnalysisWorker(TGProject currentProject, GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences) {
        this.output = gtaOutput;
        this.datasetContainer = gtaDatasetContainer;
        this.modelReference = gtaModelReference;
        this.modelDifferences = gtaModelDifferences;
        this.project = currentProject;
    }

    public AnalysisWorker(TGProject currentProject, GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference) {
        this.output = gtaOutput;
        this.datasetContainer = gtaDatasetContainer;
        this.modelReference = gtaModelReference;
        this.modelDifferences = null;
        this.project = currentProject;
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

    private Tgm getModel(String filename, String relativeFilePath) {
        TgmDataObject tgmDO;
        Tgm model = null;
        String newPath = project.getProjectDirectory() + File.separator + relativeFilePath + File.separator + filename;
        try {
            File datasetF = new File(newPath);
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

    private boolean isValidAnalysis(DatasetTimp[] datasets, Tgm tgm) {
        String feedback = null;
        boolean run = true;
        for (DatasetTimp dataset : datasets) {
            if (dataset == null) {
                run = false;
            }
        }

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
        return run;
    }

    private void writeSummary(FileObject resultsfolder, String freeFilename) throws IOException {
        writeTo = resultsfolder.createData(freeFilename, "summary");
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(FileUtil.toFile(writeTo)));
        //TODO: Complete the summary here:
        outputWriter.append("Summary");
        outputWriter.newLine();
//        outputWriter.append("Used dataset(s): ");
//        for (int j = 0; j < datasets.length; j++) {
//            DatasetTimp dataset = datasets[j];
//            if (j > 0) {
//                outputWriter.append(", ");
//            }
//            outputWriter.append(dataset.getDatasetName());
//        }
//        outputWriter.newLine();
//        outputWriter.newLine();
//        outputWriter.append("Used model(s): ");
//        for (int j = 0; j < models.length; j++) {
//            if (j > 0) {
//                outputWriter.append(", ");
//            }
//            outputWriter.append(models[j].getDat().getModelName());
//        }
//        outputWriter.newLine();
//        outputWriter.newLine();

        outputWriter.append("Number of iterations: ");
        outputWriter.append(String.valueOf(numIterations));
        outputWriter.newLine();
        outputWriter.newLine();

        outputWriter.append("R Call for the TIMP function \"initModel\": ");
        outputWriter.newLine();
        ArrayList<String> list = modelCalls;
        for (String string : list) {
            outputWriter.append(string);
            outputWriter.newLine();
        }
        outputWriter.newLine();
        outputWriter.append("R Call for the TIMP function \"fitModel\": ");
        outputWriter.newLine();
        outputWriter.write(fitModelCall);
        outputWriter.newLine();
        outputWriter.newLine();

        if (results != null) {

            outputWriter.append("Final residual standard error: ");
            outputWriter.append((new Formatter().format("%g", results[0].getRms())).toString());
            outputWriter.newLine();
            outputWriter.newLine();

            String[] slots = {"getKineticParameters", "getSpectralParameters", "getIrfpar", "getSpecdisppar", "getParmu", "getPartau", "getKinscal", "getPrel", "getJvec"};
            String[] slotsName = {"Kinetic parameters", "Spectral parameters", "Irf parameters", "Specdisppar", "Parmu", "Partau", "Kinscal", "Prel", "J vector"};
            double[] params = null;

            for (int k = 0; k < slots.length; k++) {
                try {
                    try {
                        for (int i = 0; i < results.length; i++) {
                            //TODO: verify the next line
                            //params = (double[]) results[i].getClass().getMethod(slots[k], new Class[]{results[i].getClass().getClass()}).invoke(results[i], new Object[]{results});
                            params = (double[]) results[i].getClass().getMethod(slots[k], null).invoke(results[i], null);
                            if (params != null) {

                                outputWriter.append("Estimated " + slotsName[k] + ": ");
                                outputWriter.newLine();
                                outputWriter.newLine();
                                outputWriter.append("Dataset" + (i + 1) + ": ");
                                for (int j = 0; j < params.length / 2; j++) {
                                    if (j > 0) {
                                        outputWriter.append(", ");
                                    }
                                    outputWriter.append((new Formatter().format("%g", params[j])).toString());
                                }
                                outputWriter.newLine();
                                outputWriter.newLine();
                                outputWriter.append("Standard errors: ");
                                for (int j = 0; j < params.length / 2; j++) {
                                    if (j > 0) {
                                        outputWriter.append(", ");
                                    }
                                    outputWriter.append((new Formatter().format("%g",
                                            params[j + params.length / 2])).toString());
                                }
                                outputWriter.newLine();
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
        } else {
            outputWriter.newLine();
            outputWriter.append("Error: The analysis did not return valid results.");
            outputWriter.newLine();
            outputWriter.append("Try again with different parameters.");
        }
        outputWriter.close();
    }

    public void run() {

        if (project != null) {
            if (!output.getIterations().isEmpty()) {
                numIterations = Integer.parseInt(output.getIterations());
            } else {
                numIterations = NO_OF_ITERATIONS;
            }
            if (output.getOutputPath() != null) {
                String outputPath = project.getResultsFolder(true) + File.separator + output.getOutputPath();
                resultsfolder = FileUtil.toFileObject(new File(outputPath));

                datasets = getDatasets(datasetContainer);
                modelCalls.add(getModelCall(modelReference, 0));
                Tgm model = getModel(modelReference);
                fitModelCall = getFitModelCall(datasets, modelCalls, modelDifferences, numIterations);

                if (isValidAnalysis(datasets, model)) {
                    timpcontroller = Lookup.getDefault().lookup(TimpControllerInterface.class);
                    if (timpcontroller != null) {
                        results = timpcontroller.runAnalysis(datasets, modelCalls, fitModelCall);
                    }
                } else {
                    //TODO: CoreErrorMessages warning
                    return;
                }

                if (results != null) {
                    Results newResultsObject = new Results();
                    String freeResultsFilename = FileUtil.findFreeFileName(resultsfolder, resultsfolder.getName() + "analysisSummary", "summary");
                    try {
                        writeSummary(resultsfolder, freeResultsFilename);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    for (int i = 0; i < results.length; i++) {
                        TimpResultDataset timpResultDataset = results[i];
                        timpResultDataset.setType(datasets[i].getType());

                        newResultsObject.getDatasets().add(new Dataset());
                        newResultsObject.getDatasets().get(i).setDatasetFile(new OutputFile());
                        newResultsObject.getDatasets().get(i).getDatasetFile().setFilename(datasetContainer.getDatasets().get(i).getFilename());
                        newResultsObject.getDatasets().get(i).getDatasetFile().setPath(datasetContainer.getDatasets().get(i).getPath());
                        newResultsObject.getDatasets().get(i).setId(String.valueOf(i+1));

                        if (model.getDat().getIrfparPanel().getLamda() != null) {
                            timpResultDataset.setLamdac(model.getDat().getIrfparPanel().getLamda());
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

                        try {
                            String freeFilename = FileUtil.findFreeFileName(resultsfolder, resultsfolder.getName() + "_d" + (i + 1) + "_" + timpResultDataset.getDatasetName(), "timpres");
                            writeTo = resultsfolder.createData(freeFilename, "timpres");
                            ObjectOutputStream stream = new ObjectOutputStream(writeTo.getOutputStream());
                            stream.writeObject(timpResultDataset);
                            stream.close();

                            newResultsObject.getDatasets().get(i).setResultFile(new OutputFile());
                            newResultsObject.getDatasets().get(i).getResultFile().setFilename(freeFilename);
                            newResultsObject.getDatasets().get(i).getResultFile().setPath(resultsfolder.getPath());

                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                    }
                    try {
                        writeResultsXml(newResultsObject);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    try {
                        writeSummary(resultsfolder, FileUtil.findFreeFileName(resultsfolder, resultsfolder.getName() + "_errorlog", "summary"));
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
    }

    private ArrayList<String> getModelCalls(ArrayList<GtaModelReference> modelReferences) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < modelReferences.size(); i++) {
            GtaModelReference ref = modelReferences.get(i);
            result.add(getModelCall(ref, i));
        }
        return result;
    }

    private String getModelCall(GtaModelReference modelReference, int i) {
        String result;
        Tgm tgm = getModel(modelReference);
        String modelCall = InitModel.parseModel(tgm);
        result = TimpControllerInterface.NAME_OF_MODEL + (i + 1) + " <- " + modelCall;
        return result;
    }

    private String getFitModelCall(DatasetTimp[] datasets, ArrayList<String> modelCalls, GtaModelDifferences modelDifferences, int numIterations) {
        String result = "";

        ArrayList<String> listOfDatasets = new ArrayList<String>();
        for (int i = 0; i < datasets.length; i++) {
            listOfDatasets.add(TimpControllerInterface.NAME_OF_DATASET + (i + 1));
        }

        ArrayList<String> listOfModels = new ArrayList<String>();
        for (int i = 0; i < modelCalls.size(); i++) {
            listOfModels.add(TimpControllerInterface.NAME_OF_MODEL + (i + 1));
        }
        result = TimpControllerInterface.NAME_OF_RESULT_OBJECT + " <- fitModel(";

        if (listOfDatasets != null) {
            result = result.concat("data = list(");
            for (int i = 0; i < listOfDatasets.size(); i++) {
                if (i > 0) {
                    result = result + ",";
                }
                result = result.concat(listOfDatasets.get(i));
            }
            result = result.concat(")");
        }

        if (listOfModels != null) {
            result = result.concat(",modspec = list(");
            for (int i = 0; i < listOfModels.size(); i++) {
                if (i > 0) {
                    result = result + ",";
                }
                result = result.concat(listOfModels.get(i));
            }
            result = result.concat(")");
        }

        String modeldiffsCall = getModelDifferences(modelDifferences);
        if (!modeldiffsCall.isEmpty()) {
            result = result.concat(",");
            result = result.concat(modeldiffsCall);
        }

        String optResult = getOptResult(getModelType(modelCalls), numIterations);
        if (!optResult.isEmpty()) {
            result = result.concat(",");
            result = result.concat(optResult);
        }

        result = result.concat(")");
        return result;
    }

    private String getOptResult(String modelType, int iterations) {
        String result = "opt = " + modelType + "opt("
                + "iter = " + String.valueOf(iterations)
                + ", plot=FALSE)";
        return result;
    }

    public String getModelDifferences(GtaModelDifferences modelDifferences) {
        String result = "";
        String tempString = "";
        if (modelDifferences != null) {
            if (modelDifferences.getThreshold()!=null){
                if (modelDifferences.getThreshold()!=0){
                    result = result + "thresh = " + String.valueOf(modelDifferences.getThreshold());
                }
            }
            tempString = getModelDiffsLinkCLP(modelDifferences.getLinkCLP());

            tempString = getModelDiffsFree(modelDifferences.getDifferences());
            if (!tempString.isEmpty()){
                if (!result.isEmpty()){
                    result = result + ", ";
                }
                result = result + tempString;
            }
            tempString = "";

            tempString = getModelDiffsDScal(modelDifferences);
            if (!tempString.isEmpty()){
                if (!result.isEmpty()){
                    result = result + ", ";
                }
                result = result + tempString;
            }
            tempString = "";

            tempString = getModelDiffsFree(modelDifferences.getDifferences());

            if (!tempString.isEmpty()){
                if (!result.isEmpty()){
                    result = result + ", ";
                }
                result = result + tempString;
            }
            tempString = "";
            
            //Fill in the "change" parameter
            for (GtaModelDiffContainer diffContainer : modelDifferences.getDifferences()) {
                result = result + getModelDiffsChange(diffContainer);
            }
        }
        if (!result.isEmpty()) {
            result = "modeldiffs = list(" + result + ")";
        }
        return result;
    }

    private String getModelDiffsFree(java.util.List<GtaModelDiffContainer> diffContainers) {
        String result = "";
        //Fill in the "free" parameter
        for (GtaModelDiffContainer diffContainer : diffContainers) {
            if (diffContainer != null) {
                for (int i = 0; i < diffContainer.getFree().size(); i++) {
                    GtaModelDiffDO modelDiffDO = diffContainer.getFree().get(i);
                    if (modelDiffDO != null) {
                        if (!result.isEmpty()) {
                            result = result + ",";
                        }
                        result = result
                                + "list(what = \"" + modelDiffDO.getWhat() + "\","
                                + "ind = " + (modelDiffDO.getIndex()) + ","
                                + "dataset = " + modelDiffDO.getDataset() + ","
                                + "start = " + modelDiffDO.getStart() + ")";
                    }
                }
            }
        }
        if (!result.isEmpty()) {
            result = "free = list(" + result + ")";
        }
        return result;
    }

    private String getModelDiffsLinkCLP(java.util.List<GtaLinkCLP> gtaLinkCLPList) {
        String result = "";
        if (gtaLinkCLPList != null) {
            int total = gtaLinkCLPList.size();
            String[] listOfCLP = new String[total];
            for (int i = 0; i < listOfCLP.length; i++) {
                listOfCLP[i] = "";
            }
            for (int i = 0; i < gtaLinkCLPList.size(); i++) {
                int num = gtaLinkCLPList.get(i).getGroupNumber()-1;
                if (listOfCLP[num].isEmpty()) {
                    listOfCLP[num] = listOfCLP[num] + String.valueOf(i+1);
                } else {
                    listOfCLP[num] = listOfCLP[num] + "," + String.valueOf(i+1);
                }
            }
            String clp = "";
            for (int i = 0; i < listOfCLP.length; i++) {
                if (!listOfCLP[i].isEmpty()) {
                    if (!clp.isEmpty()) {
                        clp = clp + ',';
                    }
                    clp = clp + "c(" + listOfCLP[i] + ")";
                }

            }
            if (!clp.isEmpty()) {
                result = result + "linkclp = list(" + clp + ")";
            }
        }
        return result;
    }

    private String getModelDiffsChange(GtaModelDiffContainer diffContainer) {
        String result = "";

        TgmDataObject tgmDO;
        Tgm changesModel = null;
        if (diffContainer != null) {
            if (diffContainer.getChanges() != null) {
                GtaChangesModel changes = diffContainer.getChanges();
                String fileName = changes.getFilename();
                String pathName = changes.getPath();
                changesModel = getModel(fileName, pathName);
            }

        }

        return result;
    }

    private String getModelDiffsDScal(GtaModelDifferences modelDiffs) {
        String result = "";
        int datasetNum = modelDiffs.getLinkCLP().size();

        ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i< datasetNum; i++){
            groups.add(new ArrayList<Integer>());
        }

        for (int i = 0; i< datasetNum; i++){
            groups.get(modelDiffs.getLinkCLP().get(i).getGroupNumber()-1).add(i);
        }

        String tempString = "";

        for (int i = 0; i < datasetNum; i++){
            if (groups.get(i).size()>1){
//index of first dataset in the group
                int fromInd = groups.get(i).get(0);
//scaling parameter from first dataset in the group
                double fromVal = modelDiffs.getDscal().get(fromInd).getValue()!=null ?
                    modelDiffs.getDscal().get(groups.get(i).get(0)).getValue() : 1;
                double toVal;
                for (int j = 1; j < groups.get(i).size(); j++) {
//scaling parameter to
                    toVal = modelDiffs.getDscal().get(groups.get(i).get(j)).getValue() != null
                            ? modelDiffs.getDscal().get(groups.get(i).get(j)).getValue() / fromVal : 1 / fromVal;
                    if (!tempString.isEmpty()) {
                        tempString = tempString + ", ";
                    }
                    relationsList.add(new Double[3]);
                    relationsList.get(relationsList.size()-1)[0] = (double)groups.get(i).get(j) + 1;
                    relationsList.get(relationsList.size()-1)[1] = (double)fromInd + 1;
                    relationsList.get(relationsList.size()-1)[2] = toVal;
                    tempString = tempString + "list(to = " + String.valueOf(groups.get(i).get(j) + 1)
                            + ", from = " + String.valueOf(fromInd + 1)
                            + ", value = " + toVal
                            + ")";
                }

            }

        }

        if (!tempString.isEmpty()){
            result = "dscal = list("+tempString+")";                        
        }
        return result;
    }

    private String getModelType(ArrayList<String> modelCalls) {
        String result = "";
        for (String string : modelCalls) {
            if (string.contains("mod_type = \"kin\"")) {
            result = "kin";
            } else if (string.contains("mod_type = \"spec\"")) {
                result = "spec";
            } else if (string.contains("mod_type = \"mass\"")) {
                result = "mass";
            } else {
                result = "kin";
            }
            if (!result.isEmpty()) {
                return result;
            }
        }
        return result;
    }

    private void writeResultsXml(Results resultsObject ) throws IOException {

        String newAnResFileName = FileUtil.findFreeFileName(resultsfolder, resultsfolder.getName() +modelReference.getFilename() + "_results" , "xml");
        FileObject newAnResFile = resultsfolder.createData(newAnResFileName,"xml");
        resultsObject.setSummary(new Summary());
        resultsObject.getSummary().setFitModelCall(fitModelCall);
//TODO resolve problem with multiple modelcalls        
        resultsObject.getSummary().setInitModelCall(modelCalls.get(0));
//TODO fill it used schema file        
        resultsObject.getSummary().setUsedAnalysisSchema(null); 

        for (int i = 0; i < relationsList.size(); i++){
            resultsObject.getDatasetRelations().add(new DatasetRelation());
            resultsObject.getDatasetRelations().get(i).setTo(String.valueOf((int)floor(relationsList.get(i)[0])));
            resultsObject.getDatasetRelations().get(i).setFrom(String.valueOf((int)floor(relationsList.get(i)[1])));
//TODO fill in drel values!!
            resultsObject.getDatasetRelations().get(i).getValues().add(1.1);
            
        }
        
        createAnalysisResultsFile(resultsObject, FileUtil.toFile(newAnResFile));

    }


    private void createAnalysisResultsFile(Results resultsObject, File file){
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(resultsObject.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(resultsObject, file);

        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
    }
}



   
