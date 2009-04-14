/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.timpinterface;

import Jama.Matrix;
import java.util.List;
import org.rosuda.irconnect.IRList;
import org.rosuda.irconnect.IRMap;
import org.rosuda.irconnect.IRMatrix;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.structures.TimpResultDataset;

/**
 *
 * @author jsg210
 */
public interface TimpInterface {

    void DoAnalysis();

    void RegisterData(DatasetTimp timpDat);

    void RegisterMeasuredIRF(String name, float[] refArray);

    void SendDatasetsToTIMP();

    void SendModelsToTIMP();

    boolean existsInR(String varname);

    boolean getBool(String cmd);

    void getBoolArray(String cmd);

    Matrix getCLP(String resultVariable, int dataset);

    Matrix getCLP(String resultVariable, boolean getclperr, int dataset);

    List<Matrix> getCLPList(String resultVariable);

    List<Matrix> getCLPList(String resultVariable, boolean getclperr);

    Matrix getData(String resultVariable, int dataset);

    Matrix getData(String resultVariable, int dataset, boolean weighted);

    List<Matrix> getDataList(String resultVariable);

    List<Matrix> getDataList(String resultVariable, boolean weighted);

    void getDatasetTimp(String ddname);

    double getDouble(String cmd);

    double[] getDoubleArray(String cmd);

    Matrix getDoubleMatrix(String cmd);

    void getFactor(String cmd);

    IRList getIRList(String cmd);

    IRMap getIRMap(String cmd);

    IRMatrix getIRMatrix(String cmd);

    int getInt(String cmd);

    int[] getIntArray(String cmd);

    Matrix getIntMatrix(String cmd);

    List<?> getList(String cmd);

    double[] getParEst(String resultVariable, int dataset, String param);

    Matrix getResiduals(String resultVariable);

    Matrix getResiduals(String resultVariable, int dataset);

    List<Matrix> getResidualsList(String resultVariable, boolean weighted);

    List getSVDResiduals(String resultVariable);

    List getSVDResiduals(String resultVariable, int numsing, int dataset);

    String getString(String cmd);

    String[] getStringArray(String cmd);

    void getSymbol(String cmd);

    Matrix getTraces(String resultVariable);

    Matrix getTraces(String resultVariable, int dataset);

    List<Matrix> getTracesList(String resultVariable, boolean weighted);

    void getVector(String cmd);

    Matrix getX(String resultVariable, int index, boolean single);

    Matrix getX(String resultVariable, int group, int index, boolean single);

    List<Matrix> getXList(String resultVariable, boolean single);

    List<Matrix> getXList(String resultVariable, int group, boolean single);

    double[] getdim1(String resultVariable);

    double[] getdim2(String resultVariable);

    void onls(String resultVariable);

    void sendDatasetTimp(DatasetTimp dd);

    TimpResultDataset setTimpResultDataset(String datasetName, int dataset, ResultObject resultObject);

    void sumnls(String resultVariable);

}
