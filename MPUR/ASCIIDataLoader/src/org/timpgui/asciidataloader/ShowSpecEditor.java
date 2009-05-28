/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.asciidataloader;

import org.timpgui.dataeditors.SpecEditorTopCompNew;
import org.timpgui.dataeditors.SpecEditorTopComponent;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;

/**
 *
 * @author lsp
 */
public class ShowSpecEditor implements  DatasetLoaderInterface{

    public String getType() {
        return "spec";
    }

    public void openDatasetEditor(String filename) {

//        SpecEditorTopComponent tc = new SpecEditorTopComponent(filename);
        SpecEditorTopCompNew tc = new SpecEditorTopCompNew(filename);
        tc.open();
        tc.requestActive();
    }

}
