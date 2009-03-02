/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpservice;

import nl.vu.nat.timpinterface.*;
import nl.vu.nat.timpinterface.TimpInterface;
import Jama.Matrix;
import java.util.List;
import nl.wur.flimdataloader.flimpac.DatasetTimp;
import nl.wur.flimdataloader.flimpac.TimpResultDataset;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRList;
import org.rosuda.irconnect.IRMap;
import org.rosuda.irconnect.IRMatrix;
import org.rosuda.irconnect.ITwoWayConnection;
import org.rosuda.jri.JRIConnectionFactory;

public class TimpController implements TimpInterface {

    private static ITwoWayConnection connection;

    public TimpController() {
        connection = JRIConnectionFactory.getInstance().createTwoWayConnection(null);
    }

    public boolean getBool(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asBool().isTRUE();
    }

    // getBoolArray
    public double getDouble(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asDouble();
    }

    public double[] getDoubleArray(String cmd) {
        final IREXP evalREXP = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return evalREXP.asDoubleArray();
    }

    //getFactor
    public int getInt(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asInt();
    }

    public int[] getIntArray(String cmd) {
        final IREXP evalREXP = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return evalREXP.asIntArray();
    }

    //asList, asMap
    public IRMatrix getIRMatrix(String cmd) {
        final IREXP evalREXP = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return evalREXP.asMatrix();
    }

    public Matrix getDoubleMatrix(String cmd) {
        //just once the values
        IRMatrix matrix = getIRMatrix(cmd);
        Matrix doubleMatrix = new Matrix(matrix.getRows(), matrix.getColumns());
        for (int row = 0; row < matrix.getRows(); row++) {
            for (int col = 0; col < matrix.getColumns(); col++) {
                doubleMatrix.set(row,col,matrix.getValueAt(row, col).asDouble());
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
                intMatrix.set(row,col,matrix.getValueAt(row, col).asInt());
            }
        }
        return intMatrix;
    }

    public String getString(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asString();
    }

    public String[] getStringArray(String cmd) {
        final IREXP evalREXP = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return evalREXP.asStringArray();
    }

    	public IRList getIRList(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asList();
        }

        public IRMap getIRMap(String cmd) {
        final IREXP ret = connection.eval(new StringBuffer().append(
                "try(").append(cmd).append(")").toString());
        return ret.asMap();
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

    //asSymbol, asVector.


    // TIMP specific functions

    public double[] getParEst(String resultVariable, int dataset, String param) {
        //System.out.println("parEst(" + resultVariable + ", param = \"" + param + "\", dataset =" + dataset + ")");
        return getDoubleArray(
                new StringBuffer().append("" +
                "unlist(" +
                "parEst(" +
                resultVariable + ", " +
                "param = \"" + param + "\"" +
                "dataset = \"" + dataset + "\"" +
                "))").toString());
    }

    public  List<Matrix> getXList(String resultVariable, boolean single) {
        return getXList(resultVariable, 0, single);
    }

    public List<Matrix> getXList(String resultVariable, int group, boolean single) {
        //getXList(result, group = vector())
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
        //getX(result, group = vector(), dataset=1)
        if (group == 0) {
            String groupStr = "vector()";
        } else {
            String groupStr = Integer.toString(group);
        }
        //return new Matrix(RController.getDoubleMatrix("getX(" + resultVariable + ", group = " + groupStr + ", dataset =" + index + ")"));
        if (single) {
            double[] temp = connection.eval("getX(" + resultVariable + ", dataset =" + index + ")").asDoubleArray();
            Matrix x = new Matrix(1, temp.length);
            for (int i = 0; i < temp.length; i++) {
                x.set(0, i, temp[i]);
            }
            return x;
        } else {
            return getDoubleMatrix("getX(" + resultVariable + ", dataset =" + index + ")");
        }
    }

    public List<Matrix> getCLPList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public List<Matrix> getCLPList(String resultVariable, boolean getclperr) {
        //getCLPList(result, group = vector())
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
        //getCLP(result, group = vector(), dataset=1)
        String getclperrStr;
        if (getclperr) {
            getclperrStr = "TRUE";
        } else {
            getclperrStr = "FALSE";
        }
        return getDoubleMatrix("t(getCLP(" + resultVariable + ", getclperr = " + getclperrStr + ", dataset =" + dataset + "))");
    }

    public List<Matrix> getDataList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public List<Matrix> getDataList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
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
        //getCLP(result, group = vector(), dataset=1)
        String weightedStr;
        if (weighted) {
            weightedStr = "TRUE";
        } else {
            weightedStr = "FALSE";
        }
        return getDoubleMatrix("getData(" + resultVariable + ", dataset =" + dataset + ", weighted = " + weightedStr + ")");
    }

    public List<Matrix> getResidualsList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
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
        //getCLP(result, group = vector(), dataset=1)
        return getDoubleMatrix("getResiduals(" + resultVariable + ", dataset =" + dataset + ")");
    }

    public List getSVDResiduals(String resultVariable) {
        return getSVDResiduals(resultVariable, 2, 1);
    }

    public List getSVDResiduals(String resultVariable, int numsing, int dataset) {
        //TODO: this function will not return a proper list
        return getList("getSVDResiduals(" + resultVariable + ", numsing =" + numsing + ",dataset =" + ")");
    }

    public List<Matrix> getTracesList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
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
        //getCLP(result, group = vector(), dataset=1)
        return getDoubleMatrix("getTraces(" + resultVariable + ", dataset =" + dataset + ")");
    }

    public double[] getdim1(String resultVariable) {
        //getdim1(result)
        return connection.eval("getdim1(" + resultVariable + ")").asDoubleArray();
    }

    public double[] getdim2(String resultVariable) {
        //getdim2(result)
        return connection.eval("getdim2(" + resultVariable + ")").asDoubleArray();

    }

    public TimpResultDataset setTimpResultDataset(String datasetName, int dataset, ResultObject resultObject) {
        dataset++;
        TimpResultDataset result = new TimpResultDataset();
        result.SetDatasetName(datasetName);
        result.SetSpectras(getCLP(resultObject.getNameOfResultsObjects(), dataset));
        result.SetX(getdim1(resultObject.getNameOfResultsObjects()));
        result.SetX2(getdim2(resultObject.getNameOfResultsObjects()));
        result.SetResiduals(getResiduals(resultObject.getNameOfResultsObjects(), dataset));
        result.SetTraces(getData(resultObject.getNameOfResultsObjects(), dataset)); //if weighted=TRUE then use 3rd argument
        result.SetFittedTraces(getTraces(resultObject.getNameOfResultsObjects(), dataset));
        result.SetKineticParameters(getParEst(resultObject.getNameOfResultsObjects(), dataset, "kinpar"));
        result.SetSpectralParameters(getParEst(resultObject.getNameOfResultsObjects(), dataset, "specpar"));
        if (result.GetKineticParameters().length > 1) {
            result.SetConcentrations(getX(resultObject.getNameOfResultsObjects(), dataset, false));
        } else {
            result.SetConcentrations(getX(resultObject.getNameOfResultsObjects(), dataset, true));
        }
        return result;
    }

    public boolean existsInR(String varname) {
        return connection.eval("exists(\"" + varname + "\")").asBool().isTRUE();
    }

    public void sendDatasetTimp(DatasetTimp dd) {

        connection.assign("psisim", dd.GetPsisim());
        connection.assign("x", dd.GetX());
        connection.assign("x2", dd.GetX2());
        connection.assign("nl", dd.GetNl());
        connection.assign("nt", dd.GetNt());
        connection.assign("intenceIm", dd.GetIntenceIm());

        connection.eval("psisim <- as.matrix(psisim)");
        connection.eval("if(is.null(intenceIm))  intenceIm <- matrix(1,1,1)");
        connection.eval("intenceIm <- as.matrix(intenceIm)");
        connection.eval("dim(psisim) <- c(nt, nl)");

        connection.eval(dd.GetDatasetName() + "<- dat(psi.df = psisim, x = x, nt = nt, x2 = x2, nl = nl, " +
                "inten = intenceIm)");

    }

    public void getDatasetTimp(String ddname) {

        double[] jx = connection.eval("slot(" + ddname + ",'x' )").asDoubleArray();
        double[] jx2 = connection.eval("slot(" + ddname + ",'x2' )").asDoubleArray();
        int[] jnt = connection.eval("slot(" + ddname + ",'nt' )").asIntArray();
        int[] jnl = connection.eval("slot(" + ddname + ",'nl' )").asIntArray();
        double[] jpsisim = connection.eval("slot(" + ddname + ",'psisim' )").asDoubleArray();
        double[] jintenceim = connection.eval("slot(" + ddname + ",'intenceim' )").asDoubleArray();
        String jdatasetname = connection.eval("slot(" + ddname + ",'datasetname' )").asString();

        DatasetTimp dd = new DatasetTimp(jx, jx2, jnt, jnl, jpsisim, jintenceim, jdatasetname);

    }

    public void onls(String resultVariable) {
        //onls(result)
        // not yet inplemented
    }

    public void sumnls(String resultVariable) {
//sumnls(result)

        // not yet inplemented
    }

    public void RegisterData(DatasetTimp timpDat) {

    // send dataset to timp
    sendDatasetTimp(timpDat);
    // add its name to list of names

    Current.addDataset(timpDat.GetDatasetName());
    // uncomment below when want multiple datasets
    //ArrayList<String> x = Current.getDatasetNames();
//    ArrayList<String> x = new ArrayList<String>();
//
//    x.add((String)timpDat.GetDatasetName());
//
//    Current.SetdatasetNames(x);

    }
    public void RegisterMeasuredIRF(String name, float[] refArray) {

    double[] refD = new double[refArray.length];
     for (int i = 0; i < refD.length; i++)
        refD[i] = (double)refArray[i];

    // send IRF to timp
    connection.assign(name, refD);

    // TODO: different way to keep track of objects
    //Current.SetcurrMIRF(name);

    }

    public void SendDatasetsToTIMP() {}

    public void SendModelsToTIMP() {}

    public void DoAnalysis() {
    //TODO: call upon SendDatasetsToTIMP, SendModelsToTIMP


    }

}
