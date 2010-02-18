package org.glotaran.core.ui.visualmodelling.filesupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.glotaran.core.ui.visualmodelling.VisualModellingTopComponent;
import org.openide.cookies.CloseCookie;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;

class GtaOpenSupport extends OpenSupport implements OpenCookie, EditCookie, CloseCookie, GtaEditorCookie, PropertyChangeListener {

    public GtaOpenSupport(GtaDataObject.Entry entry) {
        super(entry);
    }

    protected CloneableTopComponent createCloneableTopComponent() {
        GtaDataObject dobj = (GtaDataObject) entry.getDataObject();
        VisualModellingTopComponent tc = new VisualModellingTopComponent(dobj);
        tc.setDisplayName(dobj.getName());
        return tc;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}