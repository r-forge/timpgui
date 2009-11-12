
package org.glotaran.core.resultdisplayers;

import org.glotaran.core.main.interfaces.ResultsLoaderInterface;
import org.glotaran.core.main.nodes.dataobjects.TimpResultDataObject;
import org.glotaran.core.resultdisplayers.flim.FlimResultsTopComponent;

/**
 *
 * @author Sergey
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
