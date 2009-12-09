/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import org.openide.loaders.DataNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author slapten
 */
public class KineticParametersNode extends AbstractNode{
    private Image ICON;


    public KineticParametersNode(){
        super(Children.LEAF);
    }

     @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

//    @Override
//    public String getDisplayName() {
//        return obj.getName();
//    }

}
