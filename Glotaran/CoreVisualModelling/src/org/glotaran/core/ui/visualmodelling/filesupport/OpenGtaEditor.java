/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.filesupport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.cookies.OpenCookie;

public final class OpenGtaEditor implements ActionListener {

    private final GtaDataObject context;

    public OpenGtaEditor(GtaDataObject context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        ((GtaOpenSupport)context.getCookie(OpenCookie.class)).createCloneableTopComponent().open();
    }
}
