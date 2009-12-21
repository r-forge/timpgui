package org.glotaran.core.ui.visualmodelling.common;

import java.beans.PropertyEditorSupport;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.DispersionTypes;

public class DispTypePropertyEditor extends PropertyEditorSupport {

    private String[] strNames = new String[]{"ParMu","ParTau"};

    public DispTypePropertyEditor() {
        super();
    }

    @Override
    public String[] getTags() {
        return strNames;
    }


    @Override
    public String getAsText() {
        DispersionTypes obj = (EnumTypes.DispersionTypes) getValue();
        if (obj.equals(EnumTypes.DispersionTypes.PARMU)) {
            return strNames[0];
        } else {
            if (obj.equals(EnumTypes.DispersionTypes.PARTAU)) {
                return strNames[1];
            }
        }
        return "something wrong";
    }
    @Override
    public void setAsText(String str) {
        if (str.equalsIgnoreCase(strNames[0])) {
            setValue(EnumTypes.DispersionTypes.PARMU);
        } else {
            if (str.equalsIgnoreCase(strNames[1])) {
                setValue(EnumTypes.DispersionTypes.PARTAU);
            } 
        }
    }
};
