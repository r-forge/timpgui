/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.util.ArrayList;
import org.glotaran.core.models.tgm.IrfparPanelModel;
import org.glotaran.core.ui.visualmodelling.common.DispTypePropertyEditor;
import org.glotaran.core.ui.visualmodelling.common.EnumPropertyEditor;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.DispersionTypes;
import org.glotaran.core.ui.visualmodelling.common.VisualCommonFunctions;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParameter;
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
    private Double centralWave;

    public DispersionModelingNode(){
         super("Dispersion", new NonLinearParametersKeys(1));
    }

    public DispersionModelingNode(IrfparPanelModel irfparPanel, DispersionTypes dispersionTypes) {
        super("Dispersion", new NonLinearParametersKeys(0));
        NonLinearParametersKeys params = (NonLinearParametersKeys) getChildren();
        ArrayList <Double> paramList;
        Boolean fixed;
        if (dispersionTypes.equals(EnumTypes.DispersionTypes.PARMU)){
            paramList = VisualCommonFunctions.strToParams(irfparPanel.getParmu());
            fixed = irfparPanel.isParmufixed();
        }
        else {
            paramList = VisualCommonFunctions.strToParams(irfparPanel.getPartau());
            fixed = irfparPanel.isPartaufixed();
        }
        for (int i = 0; i < paramList.size(); i++){
            params.addObj(new NonLinearParameter(paramList.get(i),fixed));
        }
        setDisptype(dispersionTypes);
        setCentralWave(irfparPanel.getLamda());
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
        Property lambdaC = null;
        PropertySupport.Reflection dispType = null;
        EnumPropertyEditor editor = new EnumPropertyEditor(
                new String[]{"Parmu", "ParTau"},
                new Object[]{EnumTypes.DispersionTypes.PARMU,EnumTypes.DispersionTypes.PARTAU});
        try {
            dispType = new PropertySupport.Reflection(this, EnumTypes.DispersionTypes.class, "disptype");
            dispType.setPropertyEditorClass(DispTypePropertyEditor.class); //EnumPropertyEditor.class;
            numberOfComponents = new PropertySupport.Reflection(this, Integer.class, "getCompNum", "setCompNum");
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
            lambdaC = new PropertySupport.Reflection(this, Double.class,"centralWave");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        numberOfComponents.setName("Order of polinomial");
        name.setName("Name");
        dispType.setName("Dispersion type");
        lambdaC.setName("Central wave");
        set.put(name);
        set.put(dispType);
        set.put(numberOfComponents);
        set.put(lambdaC);
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

    public Double getCentralWave() {
        return centralWave;
    }

    public void setCentralWave(Double centralWave) {
        this.centralWave = centralWave;
    }

}
