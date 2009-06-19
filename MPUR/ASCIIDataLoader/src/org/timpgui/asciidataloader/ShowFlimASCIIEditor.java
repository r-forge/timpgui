/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.asciidataloader;


import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;

/**
 *
 * @author lsp
 */
public class ShowFlimASCIIEditor implements  DatasetLoaderInterface {

    public String getType() {
        return "FLIMascii";
    }

    public void openDatasetEditor(TgdDataObject dataObj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void openDatasetEditor(DatasetTimp dataObj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
