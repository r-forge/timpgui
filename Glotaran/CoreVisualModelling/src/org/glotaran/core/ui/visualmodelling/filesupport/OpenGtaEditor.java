package org.glotaran.core.ui.visualmodelling.filesupport;

import org.glotaran.gtafilesupport.GtaDataObject;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.cookies.OpenCookie;

public final class OpenGtaEditor implements ActionListener {

    private final GtaDataObject context;

    public OpenGtaEditor(GtaDataObject context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        ((GtaOpenSupport)context.getCookie(OpenCookie.class)).view();
    }
}
