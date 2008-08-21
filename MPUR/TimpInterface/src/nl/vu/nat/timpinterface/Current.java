package nl.vu.nat.timpinterface;
//test comment feel free to remove this
import java.util.ArrayList;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmodels.tgo.Tgo;

public class Current {

    public static int indexOfCurrentModel;
    public static int indexOfCurrentOptions;
    public static int indexOfCurrentResults;
    public static int indexOfCurrentDataset;
    public static String nameOfCurrentModel;
    public static String nameOfCurrentOptions;
    public static String nameOfCurrentDataset;
    public static String nameOfCurrentResults;
    public static String nameOfCurrentMeasuredIrf;
    public static ArrayList<Tgm> datList = new ArrayList<Tgm>();
    public static ArrayList<Tgo> optList = new ArrayList<Tgo>();
    public static ArrayList<String> datasetNames = new ArrayList<String>();
    public static ArrayList<String> selectedDatasetNames = new ArrayList<String>();
    public static ArrayList<String> modelNames = new ArrayList<String>();
    public static ArrayList<String> optionsNames = new ArrayList<String>();
    public static ArrayList<ResultObject> resultObjects = new ArrayList<ResultObject>();
    public static ArrayList<String> measuredIrfNames = new ArrayList<String>();
    public static int numDat;

    public void Current() {
        indexOfCurrentModel = -1;
        indexOfCurrentOptions = -1;
        indexOfCurrentResults = -1;
        nameOfCurrentModel = null;
        nameOfCurrentOptions = null;
        nameOfCurrentDataset = null;
        nameOfCurrentResults = null;
        nameOfCurrentMeasuredIrf = null;
        optList = new ArrayList<Tgo>();
        datList = new ArrayList<Tgm>();
        datasetNames = new ArrayList<String>();
        selectedDatasetNames = new ArrayList<String>();
        modelNames = new ArrayList<String>();
        optionsNames = new ArrayList<String>();
        resultObjects = new ArrayList<ResultObject>();
        numDat = 0;
    }

    public static int getNumdat() {
        return numDat;
    }

    public static Tgm getCurrentModel() {
        return getSelectedModel(indexOfCurrentModel);
    }

    public static Tgm getSelectedModel(int index) {
        return datList.get(index);
    }

    public static Tgo getCurrentOpt() {
        return getSelectedOpt(indexOfCurrentOptions);
    }

    public static Tgo getSelectedOpt(int index) {
        return optList.get(index);
    }

    public static ArrayList<String> getDatasetNames() {
        return datasetNames;
    }

    public static ArrayList<String> getSelectedDatasetNames(int[] indices) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < indices.length; i++) {
            result.add(datasetNames.get(i));
        }
        return result;
    }
    
    public static void setSelectedDatasetNames(int[] indices) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < indices.length; i++) {
            result.add(datasetNames.get(i));
        }
        Current.selectedDatasetNames = result;
    }
    
    public static void setSelectedOptionsName(int selectedIndex) {
        Current.nameOfCurrentOptions = Current.getSelectedModelName(selectedIndex);
    }

    public static ArrayList<String> getModelNames() {
        return modelNames;
    }

    public static ArrayList<String> getSelectedModelNames(int[] indices) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < indices.length; i++) {
            result.add(modelNames.get(i));
        }
        return result;
    }

    public static String getSelectedModelName(int index) {
        return modelNames.get(index);
    }
    
    public static void setSelectedModelName(int index) {
        Current.setNameOfCurrentModel(modelNames.get(index));
    }
    
    private static void setNameOfCurrentModel(String get) {
        Current.nameOfCurrentModel=get;
    }

    public static ArrayList<String> getOptNames() {
        return optionsNames;
    }

    public static ArrayList<String> getSelectedOptNames(int[] indices) {
        ArrayList<String> result = null;
        for (int i = 0; i < indices.length; i++) {
            result.add(optionsNames.get(i));
        }
        return result;
    }

    public static String getSelectedOptName(int index) {
        return optionsNames.get(index);
    }

    public static ArrayList<ResultObject> GetresultNames() {
        return resultObjects;
    }

    public static String GetcurrDatasetList() {
        return nameOfCurrentDataset;
    }

    public static String GetcurrResult() {
        return nameOfCurrentResults;
    }

    public static String GetcurrMIRF() {
        return nameOfCurrentMeasuredIrf;
    }

//    public static void Setmodel(Tgm x) {
//        Current.currentModel = x;
//    }
    public static void SetnumDat(int x) {
        Current.numDat = x;
    }

//    public static void Setopt(Tgo x) {
//        Current.currentOptions = x;
//    }
    public static void SetdatasetNames(ArrayList<String> x) {
        Current.datasetNames = x;
    }

    public static void SetmodelNames(ArrayList<String> x) {
        Current.modelNames = x;
    }

    public static void SetoptList(ArrayList<Tgo> x) {
        Current.optList = x;
    }

    public static void SetcurrModel(String x) {
        Current.nameOfCurrentModel = x;
    }

    public static void SetcurrOpt(String x) {
        Current.nameOfCurrentOptions = x;
    }

    public static void SetcurrDatasetList(String x) {
        Current.nameOfCurrentDataset = x;
    }

    public static void SetcurrResult(String x) {
        Current.nameOfCurrentResults = x;
    }

    public static void SetcurrMIRF(String x) {
        Current.nameOfCurrentMeasuredIrf = x;
    }

    public static void addModel(Tgm newTgm) {
        Current.datList.add(newTgm);
        Current.nameOfCurrentModel=newTgm.getDat().getModelName();
        Current.modelNames.add(nameOfCurrentModel);
        Current.indexOfCurrentModel = datList.size();
    }

    public static void addOpt(Tgo newTgo) {
        Current.optList.add(newTgo);
        Current.nameOfCurrentOptions = newTgo.getOpt().getOptName();
        Current.optionsNames.add(nameOfCurrentOptions);
        Current.indexOfCurrentOptions = optList.size();
    }
        
        public static void addResults(ResultObject resultObject) {
        Current.resultObjects.add(resultObject);
    }
        
       public static void addDataset(String newDataset) {
        Current.datasetNames.add(newDataset);
        Current.nameOfCurrentDataset = newDataset;
        Current.indexOfCurrentDataset = datasetNames.size();
    }
       
       public static String getNameOfCurrentModel() {
           return nameOfCurrentModel;
       }
        
}
