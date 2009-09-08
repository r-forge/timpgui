/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.model.support;

import java.util.ArrayList;

/**
 *
 * @author jsg210
 */
public class ParameterIrf {
    
    private ArrayList<Double> values;
    private ArrayList<Boolean> fixed;

    public ArrayList<Double> getValues() {
        return this.values;
    }

    public ArrayList<Boolean> getFixed() {
        return this.fixed;
    }


}
