/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.datasetsinterface;

/**
 *
 * @author Sergey
 */
public interface DatasetTimpInterface {

    void CalcRangeInt();

    String GetDatasetName();

    double[] GetIntenceIm();

    double GetMaxInt();

    double GetMinInt();

    int[] GetNl();

    int[] GetNt();

    int[] GetOrigHeigh();

    int[] GetOrigWidth();

    double[] GetPsisim();

    double[] GetX();

    double[] GetX2();

    String SetDatasetName(String datasetName);

    void SetIntenceIm(double[] x2Value);

    void SetNl(int nlvalue);

    void SetNt(int ntvalue);

    void SetOrigHeigh(int orheighValue);

    void SetOrigWidth(int orwidthValue);

    void SetPsisim(double[] psisimValue);

    void SetX(double[] xValue);

    void SetX2(double[] x2Value);

}
