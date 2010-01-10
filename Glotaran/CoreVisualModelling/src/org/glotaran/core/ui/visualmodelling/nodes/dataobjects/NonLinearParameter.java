/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes.dataobjects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.glotaran.core.models.tgm.KinPar;

/**
 *
 * @author lsp
 */
public class NonLinearParameter {

    private Double start;
    private Boolean fixed;
    private Boolean constrained;
    private Double maximum;
    private Double minimum;
    private List listeners = Collections.synchronizedList(new LinkedList());


    public NonLinearParameter(){
        start = new Double(0.0);
        fixed = new Boolean(false);
        constrained = new Boolean(false);
        maximum = new Double(0.0);
        minimum = new Double(0.0);

    }

    public NonLinearParameter(Double irfPar, Boolean fixedPar){
        start = irfPar;
        fixed = fixedPar;
        constrained = new Boolean(false);
        maximum = new Double(0.0);
        minimum = new Double(0.0);

    }

    public NonLinearParameter(KinPar param){
        start = param.getStart();
        fixed = param.isFixed();
        constrained = param.isConstrained();
        maximum = param.getMax();
        minimum = param.getMin();
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double value) {
        Double oldStart = start;
        this.start = value;
        fire("start", oldStart, start);
    }

    public Boolean isFixed() {
        return fixed;
    }

    public void setFixed(Boolean value) {
        Boolean oldFixed = fixed;
        this.fixed = value;
        fire("fixed", oldFixed, fixed);
    }

    public Boolean isConstrained() {
        return constrained;
    }

    public void setConstrained(Boolean value) {
        this.constrained = value;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double value) {
        this.maximum = value;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double value) {
        this.minimum = value;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }
}
