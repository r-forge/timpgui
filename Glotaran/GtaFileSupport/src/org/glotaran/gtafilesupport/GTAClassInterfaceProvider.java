/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.gtafilesupport;

import org.glotaran.core.interfaces.XMLFilesSupportIntarface;

/**
 *
 * @author slapten
 */
public class GTAClassInterfaceProvider implements XMLFilesSupportIntarface {

    public Object getDataObjectClass() {
        return GtaDataObject.class;
    }

    public String getType() {
        return "GTADataObject";
    }

}
