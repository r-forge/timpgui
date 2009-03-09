/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.datasetsinterface;

import Jama.Matrix;

/**
 *
 * @author Sergey
 */
public interface ResultTimpInterface {

    Matrix GetConcentrations();

    String GetDatasetName();

    Matrix GetFittedTraces();

    double[] GetKineticParameters();

    double GetMaxInt();

    double GetMinInt();

    Matrix GetResiduals();

    double[] GetSpectralParameters();

    Matrix GetSpectras();

    Matrix GetTraces();

    double[] GetX();

    double[] GetX2();

    void SetConcentrations(Matrix concentrationsValue);

    void SetDatasetName(String datasetNameValue);

    void SetFittedTraces(Matrix fittedTracesValue);

    void SetKineticParameters(double[] kineticParametersValue);

    void SetResiduals(Matrix residualsValue);

    void SetSpectralParameters(double[] spectralParametersValue);

    void SetSpectras(Matrix spectrasValue);

    void SetTraces(Matrix tracesValue);

    void SetX(double[] xValue);

    void SetX2(double[] x2Value);

}
