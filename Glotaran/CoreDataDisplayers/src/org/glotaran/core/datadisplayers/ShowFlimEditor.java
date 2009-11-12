/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.datadisplayers;

import org.glotaran.core.datadisplayers.flim.SdtTopComponent;
import org.glotaran.core.main.interfaces.DatasetLoaderInterface;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;


/**
 *
 * @author lsp
 */
public class ShowFlimEditor implements  DatasetLoaderInterface{

    public String getType() {
        return "FLIM";
    }

    public void openDatasetEditor(TgdDataObject dataObj) {
        SdtTopComponent tc = new SdtTopComponent(dataObj);
        tc.open();
        tc.requestActive();
    }

    public void openDatasetEditor(TimpDatasetDataObject dataObj) {
        SdtTopComponent tc = new SdtTopComponent(dataObj);
        tc.open();
        tc.requestActive();
    }

}
