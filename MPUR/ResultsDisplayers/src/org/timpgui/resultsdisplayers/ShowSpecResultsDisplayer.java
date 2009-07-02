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
public class ShowSpecResultsDisplayer implements ResultsLoaderInterface{

    public String getType() {
        return "spec";
    }

    public void openResultDisplayer(TimpResultDataObject dataObj) {
        SpecResultsTopComponent tc = new SpecResultsTopComponent(dataObj);
        tc.open();
        tc.requestActive();
//        Confirmation msg = new NotifyDescriptor.Confirmation("2Bla 2bla 2bla", NotifyDescriptor.OK_CANCEL_OPTION);
//            DialogDisplayer.getDefault().notify(msg);
    }
}
