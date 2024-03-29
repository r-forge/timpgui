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
import org.timpgui.tgproject.datasets.DatasetLoaderInterface;
import org.timpgui.tgproject.datasets.TgdDataObject;

public final class ShowDataset extends CookieAction {
    
    private final Collection<? extends DatasetLoaderInterface> services;

    public ShowDataset(){
        services = Lookup.getDefault().lookupAll(DatasetLoaderInterface.class);
    }

    protected void performAction(Node[] activatedNodes) {
        String filename, filetype;
        TgdDataObject dataObject = activatedNodes[0].getLookup().lookup(TgdDataObject.class);
        if (!(dataObject==null)){
            filename = dataObject.getTgd().getPath();
            filename = filename.concat("/").concat((String)dataObject.getTgd().getFilename());
            filetype = dataObject.getTgd().getFiletype();

            for (final DatasetLoaderInterface service : services) {
                if (service.getType().equalsIgnoreCase(filetype)){
                    service.openDatasetEditor(dataObject);
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
        return NbBundle.getMessage(ShowDataset.class, "CTL_ShowDataset");
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

