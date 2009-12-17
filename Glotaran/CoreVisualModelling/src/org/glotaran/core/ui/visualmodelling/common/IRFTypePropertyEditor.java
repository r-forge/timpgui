package org.glotaran.core.ui.visualmodelling.common;

import java.beans.PropertyEditorSupport;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.IRFTypes;
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
        IRFTypes obj = (EnumTypes.IRFTypes) getValue();
        if (obj.equals(EnumTypes.IRFTypes.GAUSSIAN)) {
            return strNames[0];
        } else {
            if (obj.equals(EnumTypes.IRFTypes.DOUBLE_GAUSSIAN)) {
                return strNames[1];
            }
        }
        return strNames[2];
    }
    @Override
    public void setAsText(String str) {
        if (str.equalsIgnoreCase(strNames[0])) {
            setValue(EnumTypes.IRFTypes.GAUSSIAN);
        } else {
            if (str.equalsIgnoreCase(strNames[1])) {
                setValue(EnumTypes.IRFTypes.DOUBLE_GAUSSIAN);
            } else {
                if (str.equalsIgnoreCase(strNames[2])) {
                    setValue(EnumTypes.IRFTypes.MEASURED_IRF);
                }
            }
        }
    }
};
