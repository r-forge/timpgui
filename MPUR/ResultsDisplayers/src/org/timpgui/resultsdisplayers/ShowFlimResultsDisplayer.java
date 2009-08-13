/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.resultsdisplayers;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.timpgui.tgproject.datasets.ResultsLoaderInterface;
import org.timpgui.tgproject.datasets.TimpResultDataObject;

/**
 *
 * @author lsp
 */
public class ShowFlimResultsDisplayer implements ResultsLoaderInterface{

    public String getType() {
        return "flim";
    }

    public void openResultDisplayer(TimpResultDataObject dataObj) {
        FlimResultsTopComponent tc = new FlimResultsTopComponent(dataObj);
        tc.open();
        tc.requestActive();
//        Confirmation msg = new NotifyDescriptor.Confirmation("FLIM results", NotifyDescriptor.OK_CANCEL_OPTION);
//        DialogDisplayer.getDefault().notify(msg);
    }

}
