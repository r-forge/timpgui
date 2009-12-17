package org.glotaran.core.ui.visualmodelling.common;

import java.beans.PropertyEditorSupport;

public class EnumPropertyEditor extends PropertyEditorSupport {

    private String[] mTags;
    private Object[] mValues;

    public EnumPropertyEditor(String[] tags, Object[] values) {
        super();
        mTags = tags;
        mValues = values;
    }

    @Override
    public String[] getTags() {
        return mTags;
    }

    @Override
    public String getAsText() {
        Object obj = getValue();
        for (int i = 0; i < mValues.length; i++) {
            if (obj.equals(mValues[i])) {
                return mTags[i];
            }
        }
        return null;
    }

    @Override
    public void setAsText(String str) {
        for (int i = 0; i < mTags.length; i++) {
            if (str.equals(mTags[i])) {
                setValue(mValues[i]);
                return;
            }
        }
    }
}
