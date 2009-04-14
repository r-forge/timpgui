
package org.timpgui.structures;

import Jama.Matrix;
//import org.openide.util.Lookup;
/**
 *
 * @author Sergey
 */
import Jama.Matrix;

public class TimpResultDataset {
    private double maxInt;
    private double minInt;
    private String datasetName;
    private double[] kineticParameters;
    private double[] spectralParameters;
    private Matrix concentrations;
    private Matrix spectras;    
    private double[] x;
    private double[] x2;
    private Matrix residuals;
    private Matrix traces;
    private Matrix fittedTraces;
    
    public void SetDatasetName(String datasetNameValue){datasetName = datasetNameValue;}
    public void SetKineticParameters(double[] kineticParametersValue){kineticParameters = kineticParametersValue;} 
    public void SetSpectralParameters(double[] spectralParametersValue){spectralParameters = spectralParametersValue;}
    public void SetConcentrations(Matrix concentrationsValue){concentrations = concentrationsValue;}
    public void SetSpectras(Matrix spectrasValue){spectras = spectrasValue;}    
    public void SetX(double[] xValue){x = xValue;}
    public void SetX2(double[] x2Value){x2 = x2Value;}
    public void SetResiduals(Matrix residualsValue){residuals = residualsValue;}
    public void SetTraces(Matrix tracesValue){traces =tracesValue;}
    public void SetFittedTraces(Matrix fittedTracesValue){fittedTraces = fittedTracesValue;}
    
    public double GetMaxInt(){return maxInt;}
    public double GetMinInt(){return minInt;}
    public String GetDatasetName(){return datasetName;}
    public double[] GetKineticParameters(){return kineticParameters;} 
    public double[] GetSpectralParameters(){return spectralParameters;}
    public Matrix GetConcentrations(){return concentrations;}
    public Matrix GetSpectras(){return spectras;}    
    public double[] GetX(){return x;}
    public double[] GetX2(){return x2;}
    public Matrix GetResiduals(){return residuals;}
    public Matrix GetTraces(){return traces;}
    public Matrix GetFittedTraces(){return fittedTraces;}
    
    public TimpResultDataset(){
        datasetName = "Dataset1";
        kineticParameters = new double[]{10, 3, .5};
        spectralParameters = null;
        concentrations = Matrix.random(15,3);
        spectras = Matrix.random(3, 10);    
        x = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.3, 1.4};
        x2 = new double[]{0,1,2,3,4,5,6,7,8,9};
//        x2 = new double[]{6,7,12,13,14,15,18,19,21,22};
        residuals = Matrix.random(15, 10);    
        traces = Matrix.random(15, 10);    
        fittedTraces = Matrix.random(15, 10);    
        CalcRangeInt();
    }
    
    public void CalcRangeInt(){
        maxInt = 0;
        minInt = 0; 
        for (int i = 0; i < x2.length*x.length; i++){
            if (traces.getRowPackedCopy()[i]>maxInt)
                maxInt = traces.getRowPackedCopy()[i];
            if (traces.getRowPackedCopy()[i]<minInt)
                minInt = traces.getRowPackedCopy()[i];
        }
  //      return maxInt; 
    }
    

}