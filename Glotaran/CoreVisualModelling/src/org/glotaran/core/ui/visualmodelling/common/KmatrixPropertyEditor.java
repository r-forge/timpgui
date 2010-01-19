/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.common;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.glotaran.tgmeditor.panels.KMatrixPanelForm;
import org.glotaran.tgmfilesupport.TgmDataObject;

/**
 *
 * @author slapten
 */
public class KmatrixPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
//        Date d = (Date) getValue();
//        if (d == null) {
//            return "No Date Set";
//        }
//        return new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(d);
        return "Kmatrix";
    }

    @Override
    public void setAsText(String s) {

        if (true){
            System.out.print("test");

        }
//        try {
//            setValue(new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(s));
//        } catch (ParseException pe) {
//            IllegalArgumentException iae = new IllegalArgumentException("Could not parse date");
//            throw iae;
//        }
    }

    @Override
    public Component getCustomEditor() {
        return new KMatrixPanelForm((TgmDataObject)getValue());
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }


}
