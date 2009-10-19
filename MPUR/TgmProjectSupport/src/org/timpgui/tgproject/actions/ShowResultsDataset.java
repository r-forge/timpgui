/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.actions;

import java.util.Collection;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.timpgui.structures.TimpResultDataset;
import org.timpgui.tgproject.datasets.ResultsLoaderInterface;
import org.timpgui.tgproject.datasets.TimpResultDataObject;

public final class ShowResultsDataset extends CookieAction {

    private final Collection<? extends ResultsLoaderInterface> services;

    public ShowResultsDataset() {
        services = Lookup.getDefault().lookupAll(ResultsLoaderInterface.class);
    }

    protected void performAction(Node[] activatedNodes) {
        TimpResultDataObject dataObject = activatedNodes[0].getLookup().lookup(TimpResultDataObject.class);
        String datatype = null;
        if (!(dataObject == null)) {
            TimpResultDataset dataset = null;
            dataset = dataObject.getTimpResultDataset();
            if (dataset != null) {
                datatype = dataset.getType();
            }

            for (final ResultsLoaderInterface service : services) {
                if (service.getType().equalsIgnoreCase(datatype)) {
                    service.openResultDisplayer(dataObject);
                }
            }
        } else {
            Confirmation msg = new NotifyDescriptor.Confirmation("Bla bla bla", NotifyDescriptor.OK_CANCEL_OPTION);
            DialogDisplayer.getDefault().notify(msg);
        }

    }

    protected int mode() {
        return CookieAction.MODE_ALL;
    }

    public String getName() {
        return NbBundle.getMessage(ShowResultsDataset.class, "CTL_ShowResultsDataset");
    }

    protected Class[] cookieClasses() {
        return new Class[]{DataObject.class};
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}

