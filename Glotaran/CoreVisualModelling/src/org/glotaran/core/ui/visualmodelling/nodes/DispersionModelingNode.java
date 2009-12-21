/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import org.glotaran.core.ui.visualmodelling.common.DispTypePropertyEditor;
import org.glotaran.core.ui.visualmodelling.common.EnumPropertyEditor;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.DispersionTypes;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class DispersionModelingNode extends PropertiesAbstractNode {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Dispersion_16.png", true);
    private EnumTypes.DispersionTypes disptype = EnumTypes.DispersionTypes.PARMU;

    public DispersionModelingNode(){
         super("Dispersion", new NonLinearParametersKeys(1));
    }

        @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        name = name + " ("+disptype+")";
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
        PropertySupport.Reflection dispType = null;
        EnumPropertyEditor editor = new EnumPropertyEditor(
                new String[]{"Parmu", "ParTau"},
                new Object[]{EnumTypes.DispersionTypes.PARMU,EnumTypes.DispersionTypes.PARTAU});
        try {
            dispType = new PropertySupport.Reflection(this, EnumTypes.DispersionTypes.class, "disptype");
            dispType.setPropertyEditorClass(DispTypePropertyEditor.class); //EnumPropertyEditor.class;
            numberOfComponents = new PropertySupport.Reflection(this, Integer.class, "getCompNum", "setCompNum");
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        numberOfComponents.setName("Order of polinomial");
        name.setName("Name");
        dispType.setName("Dispersion type");
        set.put(name);
        set.put(dispType);
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

    public DispersionTypes getDisptype() {
        return disptype;
    }

    public void setDisptype(DispersionTypes disptype) {
        this.disptype = disptype;
        fireDisplayNameChange(null, getDisplayName());
    }


}
