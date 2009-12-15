/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import org.glotaran.core.ui.visualmodelling.common.IRFType;
import org.glotaran.core.ui.visualmodelling.common.IRFTypePropertyEditor;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.IrfParametersKeys;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class IrfParametersNode extends ModelComponentNode {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/IRFpar_16.png", true);
    IRFType irfTypeProperty = IRFType.GAUSSIAN;

    public IrfParametersNode(){
        super("IRFPar", new IrfParametersKeys(2));
    }

    @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        name = name + " ("+irfTypeProperty.toString()+")";
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
        PropertySupport.Reflection irfType = null;
        Property name = null;
       try {
            irfType = new PropertySupport.Reflection(this, IRFType.class, "getIRFType", "setIRFType");
            irfType.setPropertyEditorClass(IRFTypePropertyEditor.class);
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        irfType.setName("Type");
        name.setName("Name");
        set.put(name);
        set.put(irfType);
        sheet.put(set);
        return sheet;
    }

    public void setIRFType(IRFType irfType) {
        IrfParametersKeys childColection = (IrfParametersKeys)getChildren();
        int currCompNum = childColection.getNodesCount();
        if (irfType.equals(IRFType.GAUSSIAN)) {
            if (currCompNum==1){
                childColection.backFromMeasuredIrf();
                childColection = (IrfParametersKeys)getChildren();
            }
            if (currCompNum < 2) {
                childColection.addDefaultObj(2 - currCompNum);
            } else {
                childColection.removeParams(currCompNum - 2);
            }

        }
        if (irfType.equals(IRFType.DOUBLE_GAUSSIAN)) {
            if (currCompNum==1){
                childColection.backFromMeasuredIrf();
                childColection = (IrfParametersKeys)getChildren();
            }
            if (currCompNum < 4) {
                childColection.addDefaultObj(4 - currCompNum);
            } else {
                childColection.removeParams(currCompNum - 2);
            }
        }
        if (irfType.equals(IRFType.MEASURED_IRF)) {
            childColection.setMeasuredIrf();
        }
        irfTypeProperty = irfType;
        fireDisplayNameChange(null, getDisplayName());

    }

    public IRFType getIRFType() {
        return irfTypeProperty;
    }
}
