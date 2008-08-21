/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.timpinterface;

import java.util.ArrayList;
import nl.vu.nat.rjavainterface.RController;
import nl.wur.flimdataloader.flimpac.DatasetTimp;

/**
 *
 * @author kate
 */
public class Register {

    public static void RegisterData(DatasetTimp timpDat) {
      
    // send dataset to timp 
    RController.sendDatasetTimp(timpDat);
    // add its name to list of names 
    
    Current.addDataset(timpDat.GetDatasetName());
    // uncomment below when want multiple datasets
    //ArrayList<String> x = Current.getDatasetNames();
//    ArrayList<String> x = new ArrayList<String>();
//    
//    x.add((String)timpDat.GetDatasetName());
//    
//    Current.SetdatasetNames(x);
     
    }
    public static void RegisterMeasuredIRF(String x, float[] refArray) {
    
    double[] refD = new double[refArray.length];
     for (int i = 0; i < refD.length; i++) 
        refD[i] = (double)refArray[i];
    
    // send IRF to timp
    RController.setDoubleArray(refD, x);
   
    // add its name  
    Current.SetcurrMIRF(x); 
    
    }
}
