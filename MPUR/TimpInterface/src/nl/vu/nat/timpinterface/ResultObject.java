/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.timpinterface;

import java.util.ArrayList;

/**
 *
 * @author jsg210
 */
public class ResultObject {
    private ArrayList<String> namesOfDatasets;
    private String nameOfResultsObjects;
    
    public ResultObject(ArrayList<String> namesOfDatasets, String nameOfResultsObjects) {
        this.namesOfDatasets = namesOfDatasets;
        this.nameOfResultsObjects = nameOfResultsObjects;
    }
    
    public ArrayList<String> getNamesOfDatasets() {
        return namesOfDatasets;
    }
    
    public String getNameOfResultsObjects() {
        return nameOfResultsObjects;
    }
}
