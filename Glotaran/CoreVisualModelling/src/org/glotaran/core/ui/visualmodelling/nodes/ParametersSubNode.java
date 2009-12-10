/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.util.Formatter;
import org.glotaran.core.models.tgm.KinPar;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class ParametersSubNode extends AbstractNode{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Subnode_16.png", true);
    private KinPar dataObj;

    public ParametersSubNode(KinPar data){
        super(Children.LEAF);
        this.dataObj = data;
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    public KinPar getDataObj() {
        return dataObj;
    }

    @Override
    public String getDisplayName() {
        return new Formatter().format("%g",dataObj.getStart()).toString();
    }

}
