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
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;

public final class ShowTimpDataset extends CookieAction {
    
    private final Collection<? extends DatasetLoaderInterface> services;

    public ShowTimpDataset(){
        services = Lookup.getDefault().lookupAll(DatasetLoaderInterface.class);
    }

    protected void performAction(Node[] activatedNodes) {
        String filename, filetype;
        TimpDatasetDataObject dataObject = activatedNodes[0].getLookup().lookup(TimpDatasetDataObject.class);
        String datatype = null;
        if (!(dataObject==null)){
            DatasetTimp dataset = null;
            try {
                dataset = dataObject.getDatasetTimp();
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }

            if(dataset!=null){datatype = dataset.getType();}

            for (final DatasetLoaderInterface service : services) {
                if (service.getType().equalsIgnoreCase(datatype)){
                    service.openDatasetEditor(dataset);
                }

            }

            //filename = FileUtil.toFile(dataObject.getPrimaryFile()).getAbsolutePath();
//            filename = dataObject.getPrimaryFile().getPath().concat(dataObject. getPrimaryFile().getName());
//            Confirmation msg = new NotifyDescriptor.Confirmation(filename, NotifyDescriptor.OK_CANCEL_OPTION);
//                DialogDisplayer.getDefault().notify(msg);
        }else
        {
            Confirmation msg = new NotifyDescriptor.Confirmation("2Bla 2bla 2bla", NotifyDescriptor.OK_CANCEL_OPTION);
                DialogDisplayer.getDefault().notify(msg);
        }

    }

    protected int mode() {
        return CookieAction.MODE_ALL;
    }

    public String getName() {
        return NbBundle.getMessage(ShowTimpDataset.class, "CTL_ShowDataset");
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

