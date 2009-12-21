/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class KineticParametersNode extends PropertiesAbstractNode{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Kinpar_16.png", true);

    public KineticParametersNode(){
        super("KinPar", new NonLinearParametersKeys(1));
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

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        Property numberOfComponents = null;
        Property name = null;
        try {
            numberOfComponents = new PropertySupport.Reflection(this, Integer.class, "getCompNum", "setCompNum");
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        numberOfComponents.setName("Number of components");
        name.setName("Name");
        set.put(name);
        set.put(numberOfComponents);
        sheet.put(set);
        return sheet;
    }

    public Integer getCompNum(){
        return getChildren().getNodesCount();
    }

    public void setCompNum(Integer compNum){
        NonLinearParametersKeys childColection = (NonLinearParametersKeys)getChildren();
        int currCompNum = childColection.getNodesCount();
        if (currCompNum < compNum){
            childColection.addDefaultObj(compNum-currCompNum);
        } else {
            childColection.removeParams(currCompNum-compNum);
        }
        fireDisplayNameChange(null, getDisplayName());
    }
}
