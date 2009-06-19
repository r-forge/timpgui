/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.asciidataloader;

import org.timpgui.dataeditors.SpecEditorTopCompNew;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;

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

    public void openDatasetEditor(DatasetTimp dataObj) {
        SpecEditorTopCompNew tc = new SpecEditorTopCompNew(dataObj);
        tc.open();
        tc.requestActive();
    }
}
