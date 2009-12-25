/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes.dataobjects;

import org.glotaran.core.models.tgm.WeightPar;

/**
 *
 * @author lsp
 */
public class WeightParameter {
    private Double min1;
    private Double min2;
    private Double max1;
    private Double max2;
    private Double weight;

    public WeightParameter(){
        min1=null;
        min2=null;
        max1=null;
        max2=null;
        weight = 1.0;

    }

    WeightParameter(WeightPar parameter) {
        min1 = parameter.getMin1();
        min2 = parameter.getMin2();
        max1 = parameter.getMax1();
        max2 = parameter.getMax2();
        weight = parameter.getWeight();
    }

    public Double getMax1() {
        return max1;
    }

    public void setMax1(Double max1) {
        this.max1 = max1;
    }

    public Double getMax2() {
        return max2;
    }

    public void setMax2(Double max2) {
        this.max2 = max2;
    }

    public Double getMin1() {
        return min1;
    }

    public void setMin1(Double min1) {
        this.min1 = min1;
    }

    public Double getMin2() {
        return min2;
    }

    public void setMin2(Double min2) {
        this.min2 = min2;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }




}