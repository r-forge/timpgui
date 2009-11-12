package org.glotaran.core.main.nodes.actions;

import java.util.Collection;
import org.glotaran.core.main.interfaces.DatasetLoaderInterface;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

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
        }else
        {
            CoreErrorMessages.somethingStrange();
        }
    }

    protected int mode() {
        return CookieAction.MODE_ALL;
    }

    public String getName() {
        return NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("showDataset");
    }

    protected Class[] cookieClasses() {
        return new Class[]{DataObject.class};
    }

    @Override
    protected void initialize() {
        super.initialize();
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

