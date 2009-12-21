/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.CohSpecTypes;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author lsp
 */
public class CohSpecNode extends PropertiesAbstractNode {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Cohspecpar_16.png", true);
    private EnumTypes.CohSpecTypes cohSpecType = EnumTypes.CohSpecTypes.IRF;
    private Boolean clpzero = Boolean.FALSE;
    private Double clpMin = 0.0;
    private Double clpMax = 0.0;
    private String[] propNames = new String[]{"Name", "CohSpec model", "Set coh to 0", "Min vave", "Max vave"};

    public CohSpecNode(){
         super("CohSpec", Children.LEAF);
    }

        @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        name = name + " ("+cohSpecType+")";
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

    public Double getClpMax() {
        return clpMax;
    }

    public void setClpMax(Double clpMax) {
        this.clpMax = clpMax;fireDisplayNameChange(null, getDisplayName());
    }

    public Double getClpMin() {
        return clpMin;
    }

    public void setClpMin(Double clpMin) {
        this.clpMin = clpMin;
    }

    public Boolean getClpzero() {
        return clpzero;
    }

    public void setClpzero(Boolean clpzero) {
        this.clpzero = clpzero;
        if (clpzero) {
            try {
                Property clpminimum = new PropertySupport.Reflection(this, Double.class, "clpMin");
                Property clpmaximum = new PropertySupport.Reflection(this, Double.class, "clpMax");
                clpminimum.setName(propNames[3]);
                clpmaximum.setName(propNames[4]);
                getSheet().get(Sheet.PROPERTIES).put(clpminimum);
                getSheet().get(Sheet.PROPERTIES).put(clpmaximum);
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        else {
            getSheet().get(Sheet.PROPERTIES).remove(propNames[3]);
            getSheet().get(Sheet.PROPERTIES).remove(propNames[4]);
        }
    }

    public CohSpecTypes getCohSpecType() {
        return cohSpecType;
    }

    public void setCohSpecType(CohSpecTypes cohSpecType) {
        this.cohSpecType = cohSpecType;
        fireDisplayNameChange(null, getDisplayName());
    }

     @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        Property clpzer = null;
        Property name = null;
        PropertySupport.Reflection cohType = null;

        try {
            cohType = new PropertySupport.Reflection(this, EnumTypes.CohSpecTypes.class, "cohSpecType");
//            dispType.setPropertyEditorClass(DispTypePropertyEditor.class); //EnumPropertyEditor.class;
            clpzer = new PropertySupport.Reflection(this, Boolean.class, "clpzero");
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        clpzer.setName(propNames[2]);
        name.setName(propNames[0]);
        cohType.setName(propNames[1]);
        set.put(name);
        set.put(cohType);
        set.put(clpzer);
        sheet.put(set);
        return sheet;
    }


}
