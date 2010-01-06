/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.gtafilesupport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.DataObject;

public final class ShowAnalysisScheme implements ActionListener {

    private final DataObject context;
    private GtaDataObject gdo;

    public ShowAnalysisScheme(DataObject context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        if(context instanceof GtaDataObject) {
        gdo = (GtaDataObject)context;        
        //gdo.getCookie(OpenCookie.class);
        }
    }
}
