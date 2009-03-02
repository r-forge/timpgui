/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.timpinterface;

import Jama.Matrix;
import java.util.List;
import org.rosuda.irconnect.IRList;
import org.rosuda.irconnect.IRMap;
import org.rosuda.irconnect.IRMatrix;

/**
 *
 * @author jsg210
 */
public interface TimpInterface {

    boolean existsInR(String varname);

    double getDouble(String cmd);

    double[] getDoubleArray(String cmd);

    Matrix getDoubleMatrix(String cmd);

    IRList getIRList(String cmd);

    IRMap getIRMap(String cmd);

    IRMatrix getIRMatrix(String cmd);

    int getInt(String cmd);

    int[] getIntArray(String cmd);

    Matrix getIntMatrix(String cmd);

    List<?> getList(String cmd);

}
