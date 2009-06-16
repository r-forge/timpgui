/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader;

import org.timpgui.flimdataloader.editor.SdtTopComponent;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;

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

}
