/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader;

import org.timpgui.flimdataloader.editor.SdtTopComponent;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;

/**
 *
 * @author lsp
 */
public class ShowFlimEditor implements  DatasetLoaderInterface{

    public String getType() {
        return "FLIM";
    }

    public void openDatasetEditor(String filename) {
        SdtTopComponent tc = new SdtTopComponent(filename);        
        tc.open();
        tc.requestActive();
    }

}
