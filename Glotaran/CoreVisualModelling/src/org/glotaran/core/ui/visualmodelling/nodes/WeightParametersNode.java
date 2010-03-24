/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.glotaran.core.models.tgm.WeightPar;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.WeightParametersKeys;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class WeightParametersNode extends PropertiesAbstractNode {

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Weightpar_16.png", true);

    public WeightParametersNode(PropertyChangeListener listn) {
        super("WeightPar", new WeightParametersKeys(0));
        this.addPropertyChangeListener(listn);
    }

    public WeightParametersNode(List<WeightPar> weightpar, PropertyChangeListener listn) {
        super("WeightPar", new WeightParametersKeys(weightpar));
        this.addPropertyChangeListener(listn);
    }

    @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        name = name + " (" + String.valueOf(getChildren().getNodesCount()) + ")";
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

    public Integer getCompNum() {
        return getChildren().getNodesCount();
    }

    public void setCompNum(Integer compNum) {
        WeightParametersKeys childColection = (WeightParametersKeys) getChildren();
        int currCompNum = childColection.getNodesCount();
        if (currCompNum < compNum) {
            childColection.addDefaultObj(compNum - currCompNum);
        } else {
            childColection.removeParams(currCompNum - compNum);
        }
        updateName();
        firePropertyChange("Number of components", new Integer(currCompNum), compNum);
    }
}
