/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader;

import org.timpgui.flimdataloader.editor.SdtTopComponent;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
