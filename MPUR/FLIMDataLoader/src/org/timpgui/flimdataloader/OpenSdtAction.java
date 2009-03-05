/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.flimdataloader;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class OpenSdtAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
       // SdtDataObject sdtDataObject = activatedNodes[0].getLookup().lookup(SdtDataObject.class);
    // TODO use sdtDataObject
       // SdtDataObject d = (SdtDataObject) activatedNodes[0].getCookie(SdtDataObject.class);
     SdtDataObject d = (SdtDataObject) activatedNodes[0].getLookup().lookup(SdtDataObject.class);
	FileObject f = d.getPrimaryFile();
	String displayName = FileUtil.getFileDisplayName(f);
	String msg = "I am " + displayName + ". Hear me roar!";
        NotifyDescriptor nd = new NotifyDescriptor.Message(msg);
        DialogDisplayer.getDefault().notify(nd);

    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(OpenSdtAction.class, "CTL_OpenSdtAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{SdtDataObject.class};
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


