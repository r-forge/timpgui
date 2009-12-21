/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.common;

/**
 *
 * @author slapten
 */
public class EnumTypes {
public enum IRFTypes {
    GAUSSIAN, DOUBLE_GAUSSIAN, MEASURED_IRF;
    @Override
    public String toString() {
        String[] strNames = new String[]{"Gaussian","Double Gaussian","Measured IRF"};
        if (this.equals(IRFTypes.GAUSSIAN)) {
            return strNames[0];
        } else {
            if (this.equals(IRFTypes.DOUBLE_GAUSSIAN)) {
                return strNames[1];
            }
        }
        return strNames[2];
    }
};

public enum DispersionTypes {
    PARMU, PARTAU;
    @Override
    public String toString() {
        String[] strNames = new String[]{"ParMu","ParTau"};
        if (this.equals(DispersionTypes.PARMU)) {
            return strNames[0];
        } else {
            if (this.equals(DispersionTypes.PARTAU)) {
                return strNames[1];
            }
        }
        return null;
    }
};

public enum CohSpecTypes {
    IRF, FREE_IRF, IRF_MULTY, SEQ, MIXED;

};

};