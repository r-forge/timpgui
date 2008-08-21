package nl.vu.nat.tdatasets;

/**
 *
 * @author Sergey
 */

import static java.lang.Math.sqrt;

public final class DatasetTimp {
    private double[] psisim;
    private double[] x;
    private double[] x2;
    private int[] nt;
    private int[] nl;
    private int[] orheigh;
    private int[] orwidth;
    private double[] intenceIm;
    private String datasetName;

    public void DatasetTimp() {
        psisim = null;
        x = null;
        x2 = null;
        intenceIm = null;
        nt = new int [1];
        nt[0] = 1;
        nl = new int [1];
        nl[0] = 1;
        orheigh = new int [1];
        orheigh[0] = 1;
        orwidth = new int [1];
	orwidth[0] = 1;
        datasetName = "dataset1";
    }
    
    public DatasetTimp(int ntValue, int nlValue, int curveNumValue, String dataset) {      
        psisim = new double[ntValue*nlValue];
        x = new double[ntValue];
        x2 = new double[nlValue];
        intenceIm = new double[curveNumValue];
        nt = new int [1];
        nt[0] = ntValue;
        nl = new int [1];
        nl[0] = nlValue;
        orheigh = new int [1];
        orwidth = new int [1];
        orheigh[0] = (int)sqrt(curveNumValue);
        orwidth[0] = (int)sqrt(curveNumValue);
    	datasetName = dataset; 
	}
   
 public DatasetTimp(double[] x1, double[] x21, int[] nt1, int[] nl1, double[] psisim1, 
            double[] intenceim1, String datasetName1) {      
        x=x1;
        x2=x21;
        nt=nt1;
        nl=nl1;
        psisim=psisim1;
        intenceIm=intenceim1;
        datasetName=datasetName1;
        orheigh = new int [1];
        orheigh[0] = 1;
        orwidth = new int [1];
	orwidth[0] = 1;
 }
    
    public int[] GetNt(){return nt;}
    public int[] GetNl(){return nl;}
    public int[] GetOrigHeigh(){return orheigh;}
    public int[] GetOrigWidth(){return orwidth;}
    public double[] GetPsisim(){return psisim;}
    public double[] GetX(){return x;}
    public double[] GetX2(){return x2;}
    public double[] GetIntenceIm(){return intenceIm;}
    public String GetDatasetName(){return datasetName;}
    public void SetNt(int ntvalue){this.nt[0] = ntvalue;}
    public void SetNl(int nlvalue){this.nl[0] = nlvalue;}
    public void SetOrigHeigh(int orheighValue){orheigh[0] = orheighValue;}
    public void SetOrigWidth(int orwidthValue){orwidth[0] = orwidthValue;}
    public void SetPsisim(double[] psisimValue){psisim = psisimValue;}
    public void SetX(double[] xValue){x = xValue;}
    public void SetX2(double[] x2Value){x2 = x2Value;}
    public void SetIntenceIm(double[] x2Value){x2 = x2Value;} 
    public String GetDatasetName(String datasetName){return datasetName;}

}
