/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class FreeParametersNode  extends PropertiesAbstractNode{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/FreeParam_16.png", true);
    Boolean positiveKinpar = false;
    Boolean seqModel = false;
    PropertyChangeListener propListner;

    public FreeParametersNode(){
        super("FreePar", new NonLinearParametersKeys(1));
//        propListner = listn;
//        addPropertyChangeListener(WeakListeners.propertyChange(propListner, this));
    }

    @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        name = name + " ("+String.valueOf(getChildren().getNodesCount())+")";
        return name;
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }


}
