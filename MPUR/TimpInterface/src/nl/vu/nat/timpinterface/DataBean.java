/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.timpinterface;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmodels.tgo.Tgo;

/**
 * @author jsg210
 */
public class DataBean extends Object implements Serializable {

    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";

    private String sampleProperty;
    private ArrayList<Tgm> datList = new ArrayList<Tgm>();
    private ArrayList<Tgo> optList = new ArrayList<Tgo>();
    private ArrayList<String> datasetNames = new ArrayList<String>();

    private PropertyChangeSupport propertySupport;

    public DataBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public ArrayList<Tgm> getDatList() {
        return datList;
    }

    public void setDatList(String value) {
        ArrayList<Tgm> oldValue = datList;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
   public ArrayList<Tgo> getOptList() {
        return optList;
    }

    public void setOptList(String value) {
        ArrayList<Tgo> oldValue = optList;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public ArrayList<String> getDatasetNames() {
        return datasetNames;
    }

    public void setDatasetNames(String value) {
        ArrayList<String> oldValue = datasetNames;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}