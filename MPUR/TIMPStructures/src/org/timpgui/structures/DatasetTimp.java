package org.timpgui.structures;
/**
 *
 * @author Sergey
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import static java.lang.Math.sqrt;

public final class DatasetTimp implements Serializable{
    private double maxInt;      //max(psisim)
    private double minInt;      //min(psysim)
    private double[] psisim;    //matrix with data stored as vector timetraces in row
    private double[] x;         //vector with timesteps (xdimention)
    private double[] x2;        //vector with wavelengts (ydimention)
    private int[] nt;           //length of x
    private int[] nl;           //length of x2
    private int[] orheigh;      //original hight of flimimage
    private int[] orwidth;      //original widht of flimimage
    private double[] intenceIm; //intensity image for flimimage
    private String datasetName; //dataset name
    private String type;        //type: spec/flim/mas


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
        type = null;
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
        calcRangeInt();
 }
    public int[] getNt(){return nt;}
    public int[] getNl(){return nl;}
    public int[] getOrigHeigh(){return orheigh;}
    public int[] getOrigWidth(){return orwidth;}
    public double[] getPsisim(){return psisim;}
    public double[] getX(){return x;}
    public double[] getX2(){return x2;}
    public double[] getIntenceIm(){return intenceIm;}
    public double getMaxInt(){return maxInt;}
    public double getMinInt(){return minInt;}
    public String getDatasetName(){return datasetName;}
    public String getType() {return type;}
    public void setNt(int ntvalue){this.nt[0] = ntvalue;}
    public void setNl(int nlvalue){this.nl[0] = nlvalue;}
    public void setOrigHeigh(int orheighValue){orheigh[0] = orheighValue;}
    public void setOrigWidth(int orwidthValue){orwidth[0] = orwidthValue;}
    public void setPsisim(double[] psisimValue){psisim = psisimValue;}
    public void setX(double[] xValue){x = xValue;}
    public void setX2(double[] x2Value){x2 = x2Value;}
    public void setIntenceIm(double[] x2Value){x2 = x2Value;}
    public void setDatasetName(String dataName){this.datasetName=dataName;}
    public void setType(String type) {this.type = type;}
    public void setMaxInt(double maxInt) {this.maxInt = maxInt;}
    public void setMinInt(double minInt) {this.minInt = minInt;}

    public void loadASCIIFile(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException {
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
            sc.skip(Pattern.compile("Intervalnr",2));
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
    public void calcRangeInt(){
        maxInt = psisim[0];
        minInt = psisim[0];
        for (int i = 0; i < nl[0]*nt[0]; i++){
            if (psisim[i]>maxInt)
                maxInt = psisim[i];
            if (psisim[i]<minInt)
                minInt = psisim[i];
        }
//        double out;
//        out = maxInt;
  //      return maxInt;
    }
}
