/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.tgmfilesupport;

import org.glotaran.core.main.interfaces.XMLFilesSupportIntarface;

/**
 *
 * @author slapten
 */
public class TGMClassInterfaceProvider implements XMLFilesSupportIntarface {

    public Object getDataObjectClass() {
        return TgmDataObject.class;
    }

    public String getType() {
        return "TgmDataObject";
    }

}
