package org.timpgui.tdatasets;
/**
 *
 * @author Sergey
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import static java.lang.Math.sqrt;

public final class DatasetTimp {
    private double maxInt;
    private double minInt;
    private double[] psisim;
    private double[] x;
    private double[] x2;
    private int[] nt;
    private int[] nl;
    private int[] orheigh;
    private int[] orwidth;
    private double[] intenceIm;
    private String datasetName;


    public DatasetTimp() {
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
        maxInt = 0;
        minInt = 0; 
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
        maxInt = 0;
        minInt = 0; 
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
        CalcRangeInt();
 }
    public int[] GetNt(){return nt;}
    public int[] GetNl(){return nl;}
    public int[] GetOrigHeigh(){return orheigh;}
    public int[] GetOrigWidth(){return orwidth;}
    public double[] GetPsisim(){return psisim;}
    public double[] GetX(){return x;}
    public double[] GetX2(){return x2;}
    public double[] GetIntenceIm(){return intenceIm;}
    public double GetMaxInt(){return maxInt;}
    public double GetMinInt(){return minInt;}
    public String GetDatasetName(){return datasetName;}
    public void SetNt(int ntvalue){this.nt[0] = ntvalue;}
    public void SetNl(int nlvalue){this.nl[0] = nlvalue;}
    public void SetOrigHeigh(int orheighValue){orheigh[0] = orheighValue;}
    public void SetOrigWidth(int orwidthValue){orwidth[0] = orwidthValue;}
    public void SetPsisim(double[] psisimValue){psisim = psisimValue;}
    public void SetX(double[] xValue){x = xValue;}
    public void SetX2(double[] x2Value){x2 = x2Value;}
    public void SetIntenceIm(double[] x2Value){x2 = x2Value;} 
    public String SetDatasetName(String datasetName){return datasetName;}
    public void LoadIvoFile(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException {
        datasetName = file.getName();
        maxInt = 0;
        minInt = 0; 
        Vector x2Vector = new Vector();
        Vector psisimVector = new Vector();
        String loadedString;
        Scanner sc = new Scanner(file);
        loadedString = sc.nextLine();
        loadedString = sc.nextLine();
        loadedString = sc.nextLine();
        if (loadedString.trim().equalsIgnoreCase("Time explicit")){
            sc.skip("Intervalnr");
            nt[0] = sc.nextInt();
            x = new double[nt[0]];
            for (int i = 0; i < nt[0]; i++){               
                x[i] = Double.parseDouble(sc.next());
            }
            while (sc.hasNext()) {
                x2Vector.addElement(Double.parseDouble(sc.next()));
                for (int i = 0; i < nt[0]; i++){               
                    psisimVector.addElement(Double.parseDouble(sc.next()));
                }
            }
            nl[0] = x2Vector.size();
            x2 = new double[nl[0]];
            psisim = new double[nt[0]*nl[0]];
            
            for (int j = 0; j<nl[0]; j++){
                for (int i = 0; i<nt[0]; i++){
                    psisim[j*nt[0]+i]=(Double)psisimVector.elementAt(j*nt[0]+i);
                    if (psisim[j*nt[0]+i]>maxInt)
                        maxInt = psisim[j*nt[0]+i];
                    if (psisim[j*nt[0]+i]<minInt)
                        minInt = psisim[j*nt[0]+i];
                }
                x2[j] = (Double)x2Vector.elementAt(j);
            }
            
        }
        else {
            if (loadedString.trim().equalsIgnoreCase("Wavelength explicit")){
                sc.next();
                nl[0] = sc.nextInt();
                x2 = new double[nl[0]];
                for (int i = 0; i < nl[0]; i++){               
                    x2[i] = Double.parseDouble(sc.next());
                }
                while (sc.hasNext()) {
                    x2Vector.addElement(Double.parseDouble(sc.next()));
                    for (int i = 0; i < nl[0]; i++){               
                        psisimVector.addElement(Double.parseDouble(sc.next()));

                    }
                }
                nt[0] = x2Vector.size();
                x = new double[nt[0]];
                psisim = new double[nt[0]*nl[0]];

                for (int j = 0; j<nt[0]; j++){
                    for (int i = 0; i<nl[0]; i++){
                        psisim[i*nt[0]+j]=(Double)psisimVector.elementAt(j*nl[0]+i);
                        if (psisim[i*nt[0]+j]>maxInt)
                            maxInt = psisim[i*nt[0]+j];
                        if (psisim[i*nt[0]+j]<minInt)
                            minInt = psisim[i*nt[0]+j];
                    }
                    x[j] = (Double)x2Vector.elementAt(j);
                }

            }
            else{
                if (loadedString.trim().equalsIgnoreCase("FLIM image")){
                    System.out.println("flim");
                }   
                else{
                    throw new IllegalAccessException(); 
                }   
            } 
        }
    }    
    public void CalcRangeInt(){
        maxInt = 0;
        minInt = 0; 
        for (int i = 0; i < nl[0]*nt[0]; i++){
            if (psisim[i]>maxInt)
                maxInt = psisim[i];
            if (psisim[i]<maxInt)
                minInt = psisim[i];
        }
  //      return maxInt; 
    }
}
