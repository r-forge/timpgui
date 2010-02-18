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
    public enum ConnectionTypes{
        GTADATASETCONTAINER,GTAMODELREFERENCE,GTAOUTPUT;
        private String[] strNames = new String[]{"Datasetcontainer","ModelReference","Output"};

        @Override
        public String toString() {
            switch (this) {
                case GTADATASETCONTAINER: return strNames[0];
                case GTAMODELREFERENCE: return strNames[1];
                case GTAOUTPUT: return strNames[2];
                default: return strNames[0];
            }
        }

        public ConnectionTypes setFromStr(String str) {
            int index = 0;
            for (int i = 0; i < strNames.length; i++) {
                if (strNames[i].equalsIgnoreCase(str)) {
                    index = i;
                }
            }
            switch (index) {
                case 0: return GTADATASETCONTAINER;
                case 1: return GTAMODELREFERENCE;
                case 2: return GTAOUTPUT;
                default: return GTADATASETCONTAINER;
            }
        }
    }

    public enum IRFTypes {
        GAUSSIAN, DOUBLE_GAUSSIAN, MEASURED_IRF;
        private String[] strNames = new String[]{"Gaussian", "Double Gaussian", "Measured IRF"};
        @Override
        public String toString() {
            switch (this) {
                case GAUSSIAN: return strNames[0];
                case DOUBLE_GAUSSIAN: return strNames[1];
                case MEASURED_IRF: return strNames[2];
                default: return strNames[0];
            }
        }

        public IRFTypes setFromStr(String str) {
            int index = 0;
            for (int i = 0; i < strNames.length; i++) {
                if (strNames[i].equalsIgnoreCase(str)) {
                    index = i;
                }
            }
            switch (index) {
                case 0: return GAUSSIAN;
                case 1: return DOUBLE_GAUSSIAN;
                case 2: return MEASURED_IRF;
                default: return GAUSSIAN;
            }
        }
    };

public enum DispersionTypes {
    PARMU, PARTAU;
    private String[] strNames = new String[]{"ParMu","ParTau"};
    @Override
    public String toString() {
        switch (this){
            case PARMU : return strNames[0];
            case PARTAU : return strNames[1];
            default : return strNames[0];
        }
    }

    public DispersionTypes setFromStr(String str){
        int index = 0;
        for (int i = 0; i < strNames.length; i++) {
            if (strNames[i].equalsIgnoreCase(str)) {
                index = i;
            }
        }
        switch (index) {
            case 0: return PARMU;
            case 1: return PARTAU;
            default: return PARMU;
        }
    }
};

public enum CohSpecTypes {
    IRF, FREE_IRF, IRF_MULTY, SEQ, MIXED;
    private static String[] strNames = new String[]{"Irf", "FreeIrfDisp", "IrfMulti", "Seq", "Mix"};

    @Override
    public String toString() {
        switch (this) {
            case IRF: return strNames[0];
            case FREE_IRF: return strNames[1];
            case IRF_MULTY: return strNames[2];
            case SEQ: return strNames[3];
            case MIXED: return strNames[4];
            default: return strNames[0];
        }
    }

    public CohSpecTypes setFromStr(String str) {
        int index = 0;
        for (int i = 0; i < strNames.length; i++) {
            if (strNames[i].equalsIgnoreCase(str)) {
                index = i;
            }
        }
        switch (index) {
            case 0: return IRF;
            case 1: return FREE_IRF;
            case 2: return IRF_MULTY;
            case 3: return SEQ;
            case 4: return MIXED;
            default: return IRF;
        }
    }
};


    public static String[] getStrNames(Object obj) {
        if (obj.getClass().equals(DispersionTypes.class)){
            return ((DispersionTypes)obj).strNames;
        }

        if (obj.getClass().equals(IRFTypes.class)){
            return ((IRFTypes)obj).strNames;
        }

        if (obj.getClass().equals(CohSpecTypes.class)){
            return ((CohSpecTypes)obj).strNames;
        }
        return new String[]{};
    }

    public static Object[] getTagsNames(Object obj) {
        if (obj.getClass().equals(DispersionTypes.class)){
            return ((DispersionTypes)obj).values();
        }

        if (obj.getClass().equals(IRFTypes.class)){
            return ((IRFTypes)obj).values();
        }

        if (obj.getClass().equals(CohSpecTypes.class)){
            return ((CohSpecTypes)obj).values();
        }
        return new String[]{};
    }
};
