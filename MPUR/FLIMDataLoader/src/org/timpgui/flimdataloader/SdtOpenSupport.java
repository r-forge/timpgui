/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.flimdataloader;

import org.openide.cookies.CloseCookie;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;
import org.timpgui.flimdataloader.editor.SdtTopComponent;

/**
 *
 * @author jsg210
 */
class SdtOpenSupport extends OpenSupport implements OpenCookie, CloseCookie {

    public SdtOpenSupport(SdtDataObject.Entry entry) {
        super(entry);
    }

        protected CloneableTopComponent createCloneableTopComponent() {
//            SdtDataObject dobj = (SdtDataObject) entry.getDataObject();
//            SdtTopComponent tc = new SdtTopComponent(dobj);
//            tc.setDisplayName(dobj.getName());
//            return tc;
            return new SdtTopComponent();
        }

    }
