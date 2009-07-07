
package org.timpgui.structures;
/**
 *
 * @author Sergey
 */
import Jama.Matrix;
import java.io.Serializable;

public class TimpResultDataset  implements Serializable{
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
    private String type;
    
    public void setDatasetName(String datasetNameValue){datasetName = datasetNameValue;}
    public void setKineticParameters(double[] kineticParametersValue){kineticParameters = kineticParametersValue;}
    public void setSpectralParameters(double[] spectralParametersValue){spectralParameters = spectralParametersValue;}
    public void setConcentrations(Matrix concentrationsValue){concentrations = concentrationsValue;}
    public void setSpectra(Matrix spectraValue){spectras = spectraValue;}
    public void setX(double[] xValue){x = xValue;}
    public void setX2(double[] x2Value){x2 = x2Value;}
    public void setResiduals(Matrix residualsValue){residuals = residualsValue;}
    public void setTraces(Matrix tracesValue){traces =tracesValue;}
    public void retFittedTraces(Matrix fittedTracesValue){fittedTraces = fittedTracesValue;}
    public void setType(String type) {this.type = type;}

    public String getType() {return type;}
    public double getMaxInt(){return maxInt;}
    public double getMinInt(){return minInt;}
    public String getDatasetName(){return datasetName;}
    public double[] getKineticParameters(){return kineticParameters;}
    public double[] getSpectralParameters(){return spectralParameters;}
    public Matrix getConcentrations(){return concentrations;}
    public Matrix getSpectra(){return spectras;}
    public double[] getX(){return x;}
    public double[] getX2(){return x2;}
    public Matrix getResiduals(){return residuals;}
    public Matrix getTraces(){return traces;}
    public Matrix getFittedTraces(){return fittedTraces;}
    
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
        calcRangeInt();
    }
    
    public void calcRangeInt(){
        maxInt = 0;
        minInt = 0;
        double[] temp = traces.getColumnPackedCopy();
        for (int i = 0; i < x2.length*x.length; i++){
            if (temp[i]>maxInt)
                maxInt = temp[i];
            if (temp[i]<minInt)
                minInt = temp[i];
        }
    }
    

}