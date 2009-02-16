/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpinterface;

import Jama.Matrix;
import java.util.List;
import nl.vu.nat.rjavainterface.RController;
import nl.wur.flimdataloader.flimpac.DatasetTimp;
import nl.wur.flimdataloader.flimpac.TimpResultDataset;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRMatrix;
import org.rosuda.irconnect.ITwoWayConnection;
import org.rosuda.jri.JRIConnectionFactory;

public class TimpController {

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

    public IRMatrix getMatrix(String cmd) {
		final IREXP evalREXP = connection.eval(new StringBuffer().append(
		"try(").append(cmd).append(")").toString());
		return evalREXP.asMatrix();
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

    //asSymbol, asVector.

    public static double[] getParEst(String resultVariable, int dataset, String param) {
        //System.out.println("parEst(" + resultVariable + ", param = \"" + param + "\", dataset =" + dataset + ")");
        final IREXP evalREXP = connection.eval(new StringBuffer().append("" +
                "unlist(" +
                "parEst(" +
                resultVariable + ", " +
                "param = \"" + param + "\"" +
                "dataset = \"" + dataset + "\"" +
                "))").toString());
        return evalREXP.asDoubleArray();
    }

    public static List<Matrix> getXList(String resultVariable, boolean single) {
        return getXList(resultVariable, 0, single);
    }

    public static List<Matrix> getXList(String resultVariable, int group, boolean single) {
        //getXList(result, group = vector())
        List<Matrix> ls = null;
        int length = RController.getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getX(resultVariable, group, i, single));
        }
        return ls;
    }

    public static Matrix getX(String resultVariable, int index, boolean single) {
        return getX(resultVariable, 1, index, single);
    }

    public static Matrix getX(String resultVariable, int group, int index, boolean single) {
        //getX(result, group = vector(), dataset=1)
        String groupStr;
        if (group == 0) {
            groupStr = "vector()";
        } else {
            groupStr = Integer.toString(group);
        }
        //return new Matrix(RController.getDoubleMatrix("getX(" + resultVariable + ", group = " + groupStr + ", dataset =" + index + ")"));
        if (single) {
            double[] temp = RController.getDoubleArray("getX(" + resultVariable + ", dataset =" + index + ")");
            Matrix x = new Matrix(1,temp.length);
            for (int i =0; i<temp.length; i++) {
            x.set(0,i,temp[i]);}
            return x;
        } else {
        return new Matrix(RController.getDoubleMatrix("getX(" + resultVariable + ", dataset =" + index + ")"));
        }
    }

    public static List<Matrix> getCLPList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public static List<Matrix> getCLPList(String resultVariable, boolean getclperr) {
        //getCLPList(result, group = vector())
        List<Matrix> ls = null;
        int length = RController.getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getCLP(resultVariable, getclperr, i));
        }
        return ls;
    }

    public static Matrix getCLP(String resultVariable, int dataset) {
        return getCLP(resultVariable, false, dataset);
    }

    public static Matrix getCLP(String resultVariable, boolean getclperr, int dataset) {
        //getCLP(result, group = vector(), dataset=1)
        String getclperrStr;
        if (getclperr) {
            getclperrStr = "TRUE";
        } else {
            getclperrStr = "FALSE";
        }
        return new Matrix(RController.getDoubleMatrix("t(getCLP(" + resultVariable + ", getclperr = " + getclperrStr + ", dataset =" + dataset + "))"));
    }

    public static List<Matrix> getDataList(String resultVariable) {
        return getCLPList(resultVariable, false);
    }

    public static List<Matrix> getDataList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
        List<Matrix> ls = null;
        int length = RController.getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public static Matrix getData(String resultVariable, int dataset) {
        return getData(resultVariable, dataset, false);
    }

    public static Matrix getData(String resultVariable, int dataset, boolean weighted) {
        //getCLP(result, group = vector(), dataset=1)
        String weightedStr;
        if (weighted) {
            weightedStr = "TRUE";
        } else {
            weightedStr = "FALSE";
        }
        return new Matrix(RController.getDoubleMatrix("getData(" + resultVariable + ", dataset =" + dataset + ", weighted = " + weightedStr + ")"));
    }

    public static List<Matrix> getResidualsList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
        List<Matrix> ls = null;
        int length = RController.getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public static Matrix getResiduals(String resultVariable) {
        return getResiduals(resultVariable, 1);
    }

    public static Matrix getResiduals(String resultVariable, int dataset) {
        //getCLP(result, group = vector(), dataset=1)
        return new Matrix(RController.getDoubleMatrix("getResiduals(" + resultVariable + ", dataset =" + dataset + ")"));
    }

    public static List getSVDResiduals(String resultVariable) {
        return getSVDResiduals(resultVariable, 2, 1);
    }

    public static List getSVDResiduals(String resultVariable, int numsing, int dataset) {
        //getSVDResiduals(result, numsing = 2, dataset = 1)
        return RController.getRList("getSVDResiduals(" + resultVariable + ", numsing =" + numsing + ",dataset =" + ")");
    }

    public static List<Matrix> getTracesList(String resultVariable, boolean weighted) {
        //getCLPList(result, group = vector())
        List<Matrix> ls = null;
        int length = RController.getInt("length(" + resultVariable + ")");
        for (int i = 0; i < length; i++) {
            ls.add(i, getData(resultVariable, i, weighted));
        }
        return ls;
    }

    public static Matrix getTraces(String resultVariable) {
        return getTraces(resultVariable, 1);
    }

    public static Matrix getTraces(String resultVariable, int dataset) {
        //getCLP(result, group = vector(), dataset=1)
        return new Matrix(RController.getDoubleMatrix("getTraces(" + resultVariable + ", dataset =" + dataset + ")"));
    }

    public static double[] getdim1(String resultVariable) {
        //getdim1(result)
        return RController.getDoubleArray("getdim1(" + resultVariable + ")");
    }

    public static double[] getdim2(String resultVariable) {
        //getdim2(result)
        return RController.getDoubleArray("getdim2(" + resultVariable + ")");

    }

    public static TimpResultDataset setTimpResultDataset(String datasetName, int dataset, ResultObject resultObject) {
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
    
    public static boolean existsInR(String varname) {
       return RController.getBoolean("exists(\""+varname+"\")");
    }
    public static void sendDatasetTimp(DatasetTimp dd) {
     
        RController.setDoubleArray(dd.GetPsisim(), "psisim");
        RController.setDoubleArray(dd.GetX(), "x");
        RController.setDoubleArray(dd.GetX2(), "x2");
        RController.setIntArray(dd.GetNl(), "nl");
        RController.setIntArray(dd.GetNt(), "nt");
        RController.setDoubleArray(dd.GetIntenceIm(), "intenceIm");
       
        RController.execute("psisim <- as.matrix(psisim)");
        RController.execute("if(is.null(intenceIm))  intenceIm <- matrix(1,1,1)");
        RController.execute("intenceIm <- as.matrix(intenceIm)");
        RController.execute("dim(psisim) <- c(nt, nl)");
        
        RController.execute(dd.GetDatasetName() + "<- dat(psi.df = psisim, x = x, nt = nt, x2 = x2, nl = nl, " +
                "inten = intenceIm)");
        
    }
    
    public static void getDatasetTimp(String ddname) {
        
	 double[] jx = RController.getDoubleArray("slot(" + ddname + ",'x' )");
	 double[] jx2 = RController.getDoubleArray("slot(" + ddname + ",'x2' )");
	 int[] jnt = RController.getIntArray("slot(" + ddname + ",'nt' )");
         int[] jnl = RController.getIntArray("slot(" + ddname + ",'nl' )");
         double[] jpsisim = RController.getDoubleArray("slot(" + ddname + ",'psisim' )");
         double[] jintenceim = RController.getDoubleArray("slot(" + ddname + ",'intenceim' )");
         String jdatasetname = RController.getString("slot(" + ddname + ",'datasetname' )");
         
         DatasetTimp dd = new DatasetTimp(jx, jx2, jnt, jnl, jpsisim, jintenceim, jdatasetname);
         
    }

        public static void onls(String resultVariable) {
        //onls(result)
        // not yet inplemented
    }

    public static void sumnls(String resultVariable) {
//sumnls(result)

        // not yet inplemented
    }
   
     
}
