package org.glotaran.core.ui.visualmodelling.filesupport;

import org.glotaran.core.ui.visualmodelling.VisualModellingTopComponent;
import org.openide.cookies.CloseCookie;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;

class GtaOpenSupport extends OpenSupport implements OpenCookie, CloseCookie {

    public GtaOpenSupport(GtaDataObject.Entry entry) {
        super(entry);
    }

    protected CloneableTopComponent createCloneableTopComponent() {
        GtaDataObject dobj = (GtaDataObject) entry.getDataObject();
        VisualModellingTopComponent tc = new VisualModellingTopComponent();
        tc.setDisplayName(dobj.getName());
        return tc;
    }

}