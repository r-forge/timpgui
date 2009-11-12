/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.datadisplayers;

import org.glotaran.core.datadisplayers.spec.SpecEditorTopCompNew;
import org.glotaran.core.main.interfaces.DatasetLoaderInterface;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;

/**
 *
 * @author lsp
 */
public class ShowSpecEditor implements DatasetLoaderInterface{

    public String getType() {
        return "spec";
    }

    public void openDatasetEditor(TgdDataObject dataObj) {
        SpecEditorTopCompNew tc = new SpecEditorTopCompNew(dataObj);
        tc.open();
        tc.requestActive();
//        SpecEditorTopComponent tc = new SpecEditorTopComponent(filename);
    }

    public void openDatasetEditor(TimpDatasetDataObject dataObj) {
        SpecEditorTopCompNew tc = new SpecEditorTopCompNew(dataObj);
        tc.open();
        tc.requestActive();
    }
}
