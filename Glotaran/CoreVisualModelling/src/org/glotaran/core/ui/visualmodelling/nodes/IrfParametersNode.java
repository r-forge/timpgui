/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import org.glotaran.core.models.tgm.IrfparPanelModel;
import org.glotaran.core.ui.visualmodelling.common.EnumPropertyEditor;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.IrfParametersKeys;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class IrfParametersNode extends PropertiesAbstractNode {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/IRFpar_16.png", true);
    private EnumTypes.IRFTypes irfTypeProperty = EnumTypes.IRFTypes.GAUSSIAN;
    private Boolean backSweep = false;
    private Double sweepPeriod = null;
    private String[] propNames = new String[]{"Name","Type","Use backsweep","Sweep period"};

    public IrfParametersNode(PropertyChangeListener listn){
        super("IRFPar", new IrfParametersKeys(2));
        setIRFType(irfTypeProperty);
        addPropertyChangeListener(listn);
    }

    public IrfParametersNode(IrfparPanelModel irfparPanel, PropertyChangeListener listn) {
        super("IRFPar", new IrfParametersKeys(irfparPanel.getIrf(), irfparPanel.getFixed()));
        if (irfparPanel.isBacksweepEnabled() != null) {
            if (irfparPanel.isBacksweepEnabled()) {
                backSweep = irfparPanel.isBacksweepEnabled();
                sweepPeriod = irfparPanel.getBacksweepPeriod();
            }
        }
        if (irfparPanel.isMirf()){
            setIRFType(EnumTypes.IRFTypes.MEASURED_IRF);
//============ todo finish measured irf =================
        } else {
            if (irfparPanel.getIrf().size()==2){
                setIRFType(EnumTypes.IRFTypes.GAUSSIAN);
            }
            else {
                setIRFType(EnumTypes.IRFTypes.DOUBLE_GAUSSIAN);
            }
        }
        addPropertyChangeListener(listn);
    }

    public Boolean getBackSweep() {
        return backSweep;
    }

    public void setBackSweep(Boolean backSweep) {
        this.backSweep = backSweep;
        if (backSweep) {
            try {
                Property<Double> sweepPeriodValue = new PropertySupport.Reflection<Double>(this, Double.class, "sweepPeriod");
                sweepPeriodValue.setName(propNames[3]);
                getSheet().get(Sheet.PROPERTIES).put(sweepPeriodValue);
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        else {
            getSheet().get(Sheet.PROPERTIES).remove(propNames[3]);
        }
        firePropertyChange("SetBackSweep", null, backSweep);
    }

    public Double getSweepPeriod() {
        return sweepPeriod;
    }

    public void setSweepPeriod(Double sweepPeriod) {
        this.sweepPeriod = sweepPeriod;
        firePropertyChange("SetBackSweepPeriod", null, sweepPeriod);
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
        PropertySupport.Reflection<EnumTypes.IRFTypes> irfType = null;
        Property<String> name = null;
        Property<Boolean> sweep = null;
       try {
            irfType = new PropertySupport.Reflection<EnumTypes.IRFTypes>(this, EnumTypes.IRFTypes.class, "getIRFType", "setIRFType");
            irfType.setPropertyEditorClass(EnumPropertyEditor.class);
            name = new PropertySupport.Reflection<String>(this, String.class, "getDisplayName", null);
            sweep = new PropertySupport.Reflection<Boolean>(this, Boolean.class, "backSweep");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        irfType.setName(propNames[1]);
        name.setName(propNames[0]);
        sweep.setName(propNames[2]);
    
        set.put(name);
        set.put(irfType);
        set.put(sweep);
        sheet.put(set);
        return sheet;
    }

    public void setIRFType(EnumTypes.IRFTypes irfType) {
        IrfParametersKeys childColection = (IrfParametersKeys)getChildren();
        int currCompNum = childColection.getNodesCount();
        if (irfType.equals(EnumTypes.IRFTypes.GAUSSIAN)) {
            if (currCompNum==1){
                childColection.backFromMeasuredIrf();
                childColection = (IrfParametersKeys)getChildren();
                currCompNum = childColection.getNodesCount();
            }
            if (currCompNum < 2) {
                childColection.addDefaultObj(2 - currCompNum);
            } else {
                childColection.removeParams(currCompNum - 2);
            }
            addStreackProp();
        }
        if (irfType.equals(EnumTypes.IRFTypes.DOUBLE_GAUSSIAN)) {
            if (currCompNum==1){
                childColection.backFromMeasuredIrf();
                childColection = (IrfParametersKeys)getChildren();
                currCompNum = childColection.getNodesCount();
            }
            if (currCompNum < 4) {
                childColection.addDefaultObj(4 - currCompNum);
            } else {
                childColection.removeParams(currCompNum - 4);
            }
            addStreackProp();
        }
        if (irfType.equals(EnumTypes.IRFTypes.MEASURED_IRF)) {
            childColection.setMeasuredIrf();
            if (backSweep){
                getSheet().get(Sheet.PROPERTIES).remove(propNames[3]);
            }
            getSheet().get(Sheet.PROPERTIES).remove(propNames[2]);
        }
        irfTypeProperty = irfType;
        fireDisplayNameChange(null, getDisplayName());
        firePropertyChange("SetIRFType", null, irfTypeProperty);
    }

    public EnumTypes.IRFTypes getIRFType() {
        return irfTypeProperty;
    }

    private void addStreackProp(){
        try {
            Property<Boolean> sweep = new PropertySupport.Reflection<Boolean>(this, Boolean.class, "backSweep");
            sweep.setName(propNames[2]);
            getSheet().get(Sheet.PROPERTIES).put(sweep);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (backSweep) {
            try {
                Property<Double> sweepPeriodValue = new PropertySupport.Reflection<Double>(this, Double.class, "sweepPeriod");
                sweepPeriodValue.setName(propNames[3]);
                getSheet().get(Sheet.PROPERTIES).put(sweepPeriodValue);
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

//    @Override
//    public void fire(int index, PropertyChangeEvent evt){
//        if ("start".equals(evt.getPropertyName())) {
//            firePropertyChange("start", index, evt.getNewValue());
//        }
//        if ("fixed".equals(evt.getPropertyName())) {
//            firePropertyChange("fixed", index, evt.getNewValue());
//        }
//    }
//

}
