/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Formatter;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParameter;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author slapten
 */
public class ParametersSubNode extends AbstractNode implements PropertyChangeListener{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Subnode_16.png", true);
//    private NonLinearParameter dataObj;

    public ParametersSubNode(NonLinearParameter data){
        super(Children.LEAF, Lookups.singleton(data));
//        this.dataObj = data;
        data.addPropertyChangeListener(WeakListeners.propertyChange(this, data));
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    public NonLinearParameter getDataObj() {
        return getLookup().lookup(NonLinearParameter.class);
    }

    @Override
    public String getDisplayName() {
        return new Formatter().format("%g",getLookup().lookup(NonLinearParameter.class).getStart()).toString();
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        NonLinearParameter obj = getLookup().lookup(NonLinearParameter.class);

        Property startingValue = null;
        Property fixedValue = null;
        Property constrainedValue = null;
        Property constrainedMin = null;
        Property constrainedMax = null;

        try {
            startingValue = new PropertySupport.Reflection(obj, Double.class, "start");
            fixedValue = new PropertySupport.Reflection(obj, Boolean.class, "isFixed","setFixed");
            constrainedValue = new PropertySupport.Reflection(obj, Boolean.class, "isConstrained","setConstrained");
            constrainedMin = new PropertySupport.Reflection(obj, Double.class, "minimum");
            constrainedMax = new PropertySupport.Reflection(obj, Double.class, "maximum");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        startingValue.setName("Starting value");
        fixedValue.setName("Value fixed");
        constrainedValue.setName("Value constrained");
        constrainedMin.setName("Minimal value");
        constrainedMax.setName("Maximal value");

        set.put(startingValue);
        set.put(fixedValue);
        set.put(constrainedValue);
        set.put(constrainedMin);
        set.put(constrainedMax);
        sheet.put(set);
        return sheet;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("start".equals(evt.getPropertyName())) {
            this.fireDisplayNameChange(null, getDisplayName());
        }
    }

}
