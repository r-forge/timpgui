package org.glotaran.core.ui.visualmodelling.common;

import java.beans.PropertyEditorSupport;
public class IRFTypePropertyEditor extends PropertyEditorSupport {

    private String[] strNames = new String[]{"Gaussian","Double Gaussian","Measured IRF"};

    public IRFTypePropertyEditor() {
        super();
    }

    @Override
    public String[] getTags() {
        return strNames;
    }


    @Override
    public String getAsText() {
        IRFType obj = (IRFType) getValue();
        if (obj.equals(IRFType.GAUSSIAN)) {
            return strNames[0];
        } else {
            if (obj.equals(IRFType.DOUBLE_GAUSSIAN)) {
                return strNames[1];
            }
        }
        return strNames[2];
    }
    @Override
    public void setAsText(String str) {
        if (str.equalsIgnoreCase(strNames[0])) {
            setValue(IRFType.GAUSSIAN);
        } else {
            if (str.equalsIgnoreCase(strNames[1])) {
                setValue(IRFType.DOUBLE_GAUSSIAN);
            } else {
                if (str.equalsIgnoreCase(strNames[2])) {
                    setValue(IRFType.MEASURED_IRF);
                }
            }
        }
    }
};
