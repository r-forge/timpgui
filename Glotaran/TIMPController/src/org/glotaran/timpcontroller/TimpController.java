/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.timpcontroller;

import Jama.Matrix;
import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.interfaces.TimpControllerInterface;
import org.glotaran.core.models.structures.DatasetTimp;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.models.tgm.Tgm;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRList;
import org.rosuda.irconnect.IRMap;
import org.rosuda.irconnect.IRMatrix;
import org.rosuda.irconnect.ITwoWayConnection;
import org.rosuda.rengine.REngineConnectionFactory;

public class TimpController implements TimpControllerInterface {

    private static ITwoWayConnection connection = null;
    private ArrayList<String> initModelCall;
    private String fitModelCall;


    public TimpController() {
        if (connection==null) {
            connection = new REngineConnectionFactory().createTwoWayConnection(null);
            if (connection.isConnected()) {
                connection.voidEval("try(require(TIMP))");
            }
        }
        //connection = JRIConnectionFactory.getInstance().createTwoWayConnection(null);
    }

    public TimpResultDataset[] runAnalysis(DatasetTimp[] datasets, Tgm[] models, int iterations) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TimpResultDataset[] runAnalysis(DatasetTimp[] datasets, ArrayList<String> initModelCalls, String fitModelCall) {
        
        TimpResultDataset[] results = null;
        String cmd = null;

        for (int i = 0; i < datasets.length; i++) {
            sendDataset(datasets[i], i);
        }

        for (int i = 0; i < initModelCalls.size(); i++) {
            sendModel(initModelCalls.get(i), i);
        }

        cmd = NAME_OF_RESULT_OBJECT + "<-" + fitModelCall;
        connection.voidEval("try("+fitModelCall+")");
        //TODO: store this somewhere, possible as private variable
        if (getBool("exists(\""+NAME_OF_RESULT_OBJECT+"\")")) {
            results = new TimpResultDataset[datasets.length];
            for (int i = 0; i < datasets.length; i++) {
                results[i] = getTimpResultDataset(i);
            }
            connection.voidEval("try(rm(list=ls()))");
            connection.voidEval("try(gc())");
            //TODO: make sure this is possible
            connection.close();
        }
        return results;
    }

    private Matrix getTempMatrix(String cmd) {
        connection.voidEval("temp <-" + cmd);
        int[] dim = getIntArray("dim(temp)");
        //TODO: replace this with getDoubleArray
        IREXP ret = connection.eval("temp");
        return ret.getType()==ret.XT_NULL ? null : new Matrix(ret.asDoubleArray(), dim[0]);
    }

    private void sendDataset(DatasetTimp dd, int index) {
        index++;
        connection.assign("psisim", dd.getPsisim());
        connection.assign("x", dd.getX());
        connection.assign("x2", dd.getX2());
        connection.assign("nl", new int[] {dd.getNl()});
        connection.assign("nt", new int[] {dd.getNt()});
        connection.assign("intenceIm", dd.getIntenceIm());

        connection.eval("psisim <- as.matrix(psisim)");
        connection.eval("if(is.null(intenceIm))  intenceIm <- matrix(1,1,1)");
        connection.eval("intenceIm <- as.matrix(intenceIm)");
        connection.eval("dim(psisim) <- c(nt, nl)");


        connection.eval(NAME_OF_DATASET + String.valueOf(index) + " <- dat(psi.df = psisim, x = x, nt = nt, x2 = x2, nl = nl, " +
                "inten = intenceIm)");
    }

    private Boolean sendModel(String modelString, int index) {
        String nameOfModel, modelCall;        
        nameOfModel = NAME_OF_MODEL + String.valueOf(index);
        modelCall = nameOfModel + " <- " + modelString;

        //TODO: remove this ugly try and catch, by checking if the model is valid before we send it to R
        try {
            connection.voidEval("try(" + modelCall + ")");
        } catch (Exception e) {
           CoreErrorMessages.initModelException();
                                return null;
        }
        return getBool("exists(\""+nameOfModel+"\")");
    }

    private String getOptions(String modelType, int iterations) {
        String result = "opt = " + modelType + "opt(" +
                "iter = " + String.valueOf(iterations) +
                ", plot=FALSE)";
        connection.eval(result);
        return result;
    }

    private TimpResultDataset getTimpResultDataset(int datasetNumber) {

        String datasetName = "dataset" + String.valueOf(datasetNumber);
        datasetNumber++;
        TimpResultDataset result = new TimpResultDataset();

        result.setDatasetName(datasetName);
        result.setSpectra(getCLP(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setSpectraErr(getCLP(NAME_OF_RESULT_OBJECT, true,datasetNumber));
        result.setX(getdim1(NAME_OF_RESULT_OBJECT));
        result.setX2(getdim2(NAME_OF_RESULT_OBJECT));
        result.setResiduals(getResiduals(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setTraces(getData(NAME_OF_RESULT_OBJECT, datasetNumber)); //if weighted=TRUE then use 3rd argument
        result.setFittedTraces(getTraces(NAME_OF_RESULT_OBJECT, datasetNumber));

        result.setKineticParameters(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "kinpar"));
        result.setSpectralParameters(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "specpar"));
        result.setIrfpar(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber,"irfpar"));
//        result.setIrfpar(getIrfpar(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setParmu(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber,"parmu"));
//        result.setParmu(getParmu(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setSpecdisppar(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "specdisppar"));
//        result.setSpecdisppar(getSpecdisppar(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setJvec(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber,"jvec"));
//        result.setJvec(getJvec(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setClpequ(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber,"clpequ"));
//        result.setClpequ(getClpeq(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setKinscal(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "kinscal"));
//        result.setKinscal(getKinscal(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setPrel(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber,"prel"));
//        result.setPrel(getPrel(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setRms(getRMS(NAME_OF_RESULT_OBJECT, datasetNumber));
        result.setPartau(getParEst(NAME_OF_RESULT_OBJECT, datasetNumber, "partau"));
//        result.setPartau(getPartau(NAME_OF_RESULT_OBJECT, datasetNumber));

        if (result.getKineticParameters().length > 2) {
            result.setConcentrations(getX(NAME_OF_RESULT_OBJECT, datasetNumber, false));
        } else {
            result.setConcentrations(getX(NAME_OF_RESULT_OBJECT, datasetNumber, true));
        }
        return result;
    }

    // TIMP specific functions
    public double[] getIrfpar(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@irfpar");
    }

    public double[] getParmu(String resultVariable, int dataset) {
        return getDoubleArray("as.vector(matrix(unlist(" + resultVariable +
                "$currTheta[[" + dataset + "]]@parmu),nrow=1))");
    }

    public double[] getPartau(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@partau");
    }

    public double[] getClpeq(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@clpequ");
    }

    public double[] getKinscal(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@kinscal");
    }

    public double[] getPrel(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@prel");
    }

    public double[] getSpecdisppar(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@specdisppar");
    }

    public double[] getJvec(String resultVariable, int dataset) {
        return getDoubleArray(resultVariable + "$currTheta[[" +
                dataset + "]]@jvec");
    }

    public double getLamdac(String resultVariable, int dataset) {
        return getDouble(resultVariable + "$currTheta[["+dataset    +"]]@lamdac");
    }

    public double getRMS(String resultVariable, int dataset) {
        return getDouble("onls("+resultVariable+")$m$deviance()");
    }

    public double[] getParEst(String resultVariable, int dataset,
            String param) {
        return getDoubleArray(
                new StringBuffer().append("" +
                "unlist(" +
                "parEst(" +
                resultVariable + ", " +
                "param = \"" + param + "\"" +
                ",dataset = " + dataset +
                "))").toString());
    }

    public List<Matrix> getXList(String resultVariable, boolean single) {
        return getXList(resultVariable, 0, single);
    }

    public List<Matrix> getXList(String resultVariable, int group, boolean single) {
        //getXList(cmd, group = vector())
        List<Matrix> ls = null;
        int length = connection.eval("length(" + resultVariable + ")").asInt();
        for (int i = 0; i < length; i++) {
            ls.add(i, getX(resultVariable, group, i, single));
        }
        return ls;
    }

    public Matrix getX(String resultVariable, int index, boolean single) {
        return getX(resultVariable, 1, index, single);
    }

    public Matrix getX(String resultVariable, int group, int index, boolean single) {
        //getX(cmd, group = vector(), dataset=1)
        if (group == 0) {
            String groupStr = "vector()";
        } else {
            String groupStr = Integer.toString(group);
        }
        if (single) {
            double[] temp = connection.eval("getX(" + resultVariable + ", dataset =" + index + ")").asDoubleArray();
            Matrix x = new Matrix(1, temp.length);
            for (int i = 0; i < temp.length; i++) {
                x.set(0, i, temp[i]);
            }
            return x;
        } else {
            //return getDoubleMatrix("getX(" + resultVariable + ", dataset =" + index + ")");
            return getTempMatrix("getX(" + resultVariable + ", dataset =" + index + ")");

        }
    }

    public List<Matrix> getCLPList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public List<Matrix> getCLPList(String resultVariable, boolean getclperr) {
        //getCLPList(cmd, group = vector())
        List<Matrix> ls = null;
        int length = getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getCLP(resultVariable, getclperr, i));
        }
        return ls;
    }

    public Matrix getCLP(String resultVariable, int dataset) {
        return getCLP(resultVariable, false, dataset);
    }

    public Matrix getCLP(String resultVariable, boolean getclperr, int dataset) {
        //getCLP(cmd, group = vector(), dataset=1)
        String getclperrStr;
        if (getclperr) {
            getclperrStr = "TRUE";
            boolean clperr = getBool(resultVariable+"$currModel@stderrclp");
            if (!clperr){
                return null;
            }
        } else {
            getclperrStr = "FALSE";
        }
        //TODO: Have Ralf fix this bug related to asMatrix not working.
        //return getDoubleMatrix("t(getCLP(" + resultVariable + ", getclperr = " + getclperrStr + ", dataset =" + dataset + "))");
        String cmd = "t(getCLP(" + resultVariable + ", getclperr = " + getclperrStr + ", dataset =" + dataset + "))";
        return getTempMatrix(cmd);

    }

    public List<Matrix> getDataList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public List<Matrix> getDataList(String resultVariable, boolean weighted) {
        //getCLPList(cmd, group = vector())
        List<Matrix> ls = null;
        int length = getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public Matrix getData(String resultVariable, int dataset) {
        return getData(resultVariable, dataset, false);
    }

    public Matrix getData(String resultVariable, int dataset, boolean weighted) {
        //getCLP(cmd, group = vector(), dataset=1)
        String weightedStr;
        if (weighted) {
            weightedStr = "TRUE";
        } else {
            weightedStr = "FALSE";
        }
        //return getDoubleMatrix("getData(" + resultVariable + ", dataset =" + dataset + ", weighted = " + weightedStr + ")");
        return getTempMatrix("getData(" + resultVariable + ", dataset =" + dataset + ", weighted = " + weightedStr + ")");
    }

    public List<Matrix> getResidualsList(String resultVariable, boolean weighted) {
        //getCLPList(cmd, group = vector())
        List<Matrix> ls = null;
        int length = getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public Matrix getResiduals(String resultVariable) {
        return getResiduals(resultVariable, 1);
    }

    public Matrix getResiduals(String resultVariable, int dataset) {
        //getCLP(cmd, group = vector(), dataset=1)
        //return getDoubleMatrix("getResiduals(" + resultVariable + ", dataset =" + dataset + ")");
        return getTempMatrix("getResiduals(" + resultVariable + ", dataset =" + dataset + ")");
    }

    public List getSVDResiduals(String resultVariable) {
        return getSVDResiduals(resultVariable, 2, 1);
    }

    public List getSVDResiduals(String resultVariable, int numsing, int dataset) {
        //TODO: this function will not return a proper list
        return getList("getSVDResiduals(" + resultVariable + ", numsing =" + numsing + ",dataset =" + ")");
    }

    public List<Matrix> getTracesList(String resultVariable, boolean weighted) {
        //getCLPList(cmd, group = vector())
        List<Matrix> ls = null;
        int length = getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public Matrix getTraces(String resultVariable) {
        return getTraces(resultVariable, 1);
    }

    public Matrix getTraces(String resultVariable, int dataset) {
        //getCLP(cmd, group = vector(), dataset=1)
        //return getDoubleMatrix("getTraces(" + resultVariable + ", dataset =" + dataset + ")");

        if (getBool(resultVariable + "$currModel@modellist[[" + dataset + "]]@weight")) {
            connection.eval("w <- " + resultVariable + "$currModel@modellist[[" + dataset + "]]@weightM");
            connection.eval("f <- " + resultVariable + "$currModel@fit@resultlist[[" + dataset + "]]@fitted");
            connection.eval("f2 = matrix(unlist(f), ncol=ncol(w))/w");
            double[] dim = connection.eval("dim(f2)").asDoubleArray();
            double[] temp = connection.eval("f2").asDoubleArray();
            return new Matrix(temp, (int) dim[0]);
        } else {
            return getTempMatrix("getTraces(" + resultVariable + ", dataset =" + dataset + ")");
        }
    }

    public double[] getdim1(String resultVariable) {
        //getdim1(cmd)
        return connection.eval("getdim1(" + resultVariable + ")").asDoubleArray();
    }

    public double[] getdim2(String resultVariable) {
        //getdim2(cmd)
        return connection.eval("getdim2(" + resultVariable + ")").asDoubleArray();

    }

    public boolean existsInR(String varname) {
        return connection.eval("exists(\"" + varname + "\")").asBool().isTRUE();
    }

    public void sendMeasuredIRF(String name, float[] refArray) {

        double[] refD = new double[refArray.length];
        for (int i = 0; i < refD.length; i++) {
            refD[i] = (double) refArray[i];
        }
        connection.assign(name, refD);
    }

    public static void fitModel(int[] datasetIndices, int modelIndex, int optionIndex, String resultsName) {

    }

    public boolean getBool(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_BOOL ? ret.asBool().isTRUE() : null;
    }

    public void getBoolArray(String cmd) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public double getDouble(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_DOUBLE ? ret.asDouble() : null;
    }

    public double[] getDoubleArray(String cmd) {
       final IREXP ret = connection.eval(new StringBuffer().append(
               "try(").append(cmd).append(")").toString());
       return ret.getType()==ret.XT_ARRAY_DOUBLE ? ret.asDoubleArray() : null;
   }


    public void getFactor(String cmd) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int getInt(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_INT ? ret.asInt() : null;
    }

    public int[] getIntArray(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_ARRAY_INT ? ret.asIntArray() : null;
    }

    public IRMatrix getIRMatrix(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_MATRIX ? ret.asMatrix() : null;
    }

    public Matrix getDoubleMatrix(String cmd) {
        //just once the values
        IRMatrix matrix = getIRMatrix(cmd);
        Matrix doubleMatrix = new Matrix(matrix.getRows(), matrix.getColumns());
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getColumns(); col++) {
                doubleMatrix.set(row, col, matrix.getValueAt(row, col).asDouble());
            }
        }
        return doubleMatrix;
    }

    public Matrix getIntMatrix(String cmd) {
        //just once the values
        IRMatrix matrix = getIRMatrix(cmd);
        Matrix intMatrix = new Matrix(matrix.getRows(), matrix.getColumns());
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getColumns(); col++) {
                intMatrix.set(row, col, matrix.getValueAt(row, col).asInt());
            }
        }
        return intMatrix;
    }

    public String getString(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_STR ? ret.asString() : null;
    }

    public String[] getStringArray(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_ARRAY_STR ? ret.asStringArray() : null;
    }

    public IRList getIRList(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_LIST ? ret.asList() : null;
    }

    public IRMap getIRMap(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.getType()==ret.XT_MAP ? ret.asMap() : null;
    }

    public List<?> getList(String cmd) {
        // TODO: this function is not yet working.
        List ls = null;
        final IRMap namedRList = getIRMap(cmd);
        final String[] keys = namedRList.keys();
        final IREXP header = namedRList.at(keys[1]);
        final IREXP footer = namedRList.at(keys[1]);
        final IREXP results = namedRList.at(keys[1]);
        ls.add(results.asDoubleArray());
        return ls;
    }

    public void getSymbol(String cmd) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getVector(String cmd) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ArrayList<Matrix> doSingularValueDecomposition(Matrix matrix) {
        ArrayList<Matrix> result = new ArrayList();
        //TODO: fix this
        connection.assign("tempMatrix", matrix.getColumnPackedCopy());
        int dimRow = matrix.getColumnDimension();
        int dimCol = matrix.getRowDimension();
        connection.voidEval("dim(tempMatrix) <- c("+ dimCol +","+dimRow+")");
        connection.voidEval("resSVD <- svd(as.matrix(tempMatrix))");
        Matrix values = getTempMatrix("as.matrix(resSVD$d)");
        Matrix left = getTempMatrix("as.matrix(resSVD$u)");
        Matrix right = getTempMatrix("as.matrix(resSVD$v)");
        result.add(values);
        result.add(left);
        result.add(right);
        return result;
    }

}
