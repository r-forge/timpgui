package org.glotaran.core.ui.visualmodelling.filesupport;

import org.glotaran.gtafilesupport.GtaDataObject;
import org.glotaran.core.ui.visualmodelling.VisualModellingTopComponent;
import org.glotaran.gtafilesupport.api.GtaEditorProvider;
import org.openide.windows.CloneableTopComponent;

public final class OpenGtaEditor implements GtaEditorProvider {

    public CloneableTopComponent getCloneableTopComponent(GtaDataObject dataObject) {
        VisualModellingTopComponent tc = new VisualModellingTopComponent(dataObject);
        tc.setDisplayName(dataObject.getName());
        return tc;
    }
}
