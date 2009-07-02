/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.datasets;

/**
 *
 * @author lsp
 */
public interface ResultsLoaderInterface {

    public String getType();

//    public void openDatasetEditor(TgdDataObject dataObj);

    public void openResultDisplayer(TimpResultDataObject dataObj);

}
