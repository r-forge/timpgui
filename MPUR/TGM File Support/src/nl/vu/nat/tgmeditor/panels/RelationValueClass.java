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
    private double c0;
    private double c1;
    private boolean fixedC0;
    private boolean fixedC1;

    RelationValueClass(){
        c0 = 0;
        c1 = 0;
        fixedC0 = false;
        fixedC1 = false;
    }

    RelationValueClass(double c0val, double c1val, boolean fixC0, boolean fixC1){
        c0 = c0val;
        c1 = c1val;
        fixedC0 = fixC0;
        fixedC1 = fixC1;
    }

    public boolean isFixedC0() {
        return fixedC0;
    }

    public void setFixedC0(boolean fixedC0) {
        this.fixedC0 = fixedC0;
    }

    public boolean isFixedC1() {
        return fixedC1;
    }

    public void setFixedC1(boolean fixedC1) {
        this.fixedC1 = fixedC1;
    }

    public double getC0() {
        return c0;
    }

    public void setC0(double c0) {
        this.c0 = c0;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }
}
