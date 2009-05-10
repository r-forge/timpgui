/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.asciidataloader;

import org.timpgui.tgproject.datasets.DatasetLoaderInterface;

/**
 *
 * @author lsp
 */
public class ShowFlimASCIIEditor implements  DatasetLoaderInterface {

    public String getType() {
        return "FLIMascii";
    }

    public void openDatasetEditor(String filename) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
