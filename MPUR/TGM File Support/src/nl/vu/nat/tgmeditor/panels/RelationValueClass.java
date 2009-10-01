/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.tgmeditor.panels;

/**
 *
 * @author slapten
 */
public class RelationValueClass {
    private double value;
    private boolean fixed;

    RelationValueClass(){
        value = 0;
        fixed = false;
    }

    RelationValueClass(double val, boolean fix){
        value = val;
        fixed = fix;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
