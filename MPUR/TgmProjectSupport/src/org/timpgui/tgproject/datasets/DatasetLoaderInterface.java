/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.datasets;

import org.timpgui.structures.DatasetTimp;


/**
 *
 * @author lsp
 */
public interface DatasetLoaderInterface {

    public String getType();

    public void openDatasetEditor(TgdDataObject dataObj);

    public void openDatasetEditor(DatasetTimp dataObj);

}
