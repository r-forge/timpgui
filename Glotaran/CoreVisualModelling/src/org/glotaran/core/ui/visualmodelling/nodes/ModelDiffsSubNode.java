/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Formatter;
import org.glotaran.core.models.gta.GtaModelDiffDO;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.ModelDiffsDO;
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
public class ModelDiffsSubNode extends PropertiesAbstractNode implements PropertyChangeListener{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/DiffsSubnode_16.png", true);
    //    private NonLinearParameter dataObj;

    public ModelDiffsSubNode(ModelDiffsDO data){
        super("parameter",Children.LEAF, Lookups.singleton(data));
        data.addPropertyChangeListener(WeakListeners.propertyChange(this, data));
    }
    
    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    public ModelDiffsDO getDataObj() {
        return getLookup().lookup(ModelDiffsDO.class);
    }

    @Override
    public String getDisplayName() {
        return new Formatter().format("%g",getLookup().lookup(ModelDiffsDO.class).getStart()).toString();
    }


    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        GtaModelDiffDO obj = getLookup().lookup(GtaModelDiffDO.class);
        
        Property modelDiffType = null;

        Property startingValue = null;
//        Property fixedValue = null;
//        Property constrainedValue = null;
//        Property constrainedMin = null;
//        Property constrainedMax = null;
//
        try {
            modelDiffType = new PropertySupport.ReadOnly("Type", String.class, "Type", "Type of the modeldifference") {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return ((ModelDiffsNode) getParentNode()).getType();
                }
            };
            startingValue = new PropertySupport.Reflection(obj, Double.class, "start");
//            fixedValue = new PropertySupport.Reflection(obj, Boolean.class, "isFixed","setFixed");
//            constrainedValue = new PropertySupport.Reflection(obj, Boolean.class, "isConstrained","setConstrained");
//            constrainedMin = new PropertySupport.Reflection(obj, Double.class, "minimum");
//            constrainedMax = new PropertySupport.Reflection(obj, Double.class, "maximum");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        startingValue.setName("Starting value");
//        fixedValue.setName("Value fixed");
//        constrainedValue.setName("Value constrained");
//        constrainedMin.setName("Minimal value");
//        constrainedMax.setName("Maximal value");
//
        set.put(startingValue);
//        set.put(fixedValue);
//        set.put(constrainedValue);
//        set.put(constrainedMin);
//        set.put(constrainedMax);
        set.put(modelDiffType);
        sheet.put(set);
        return sheet;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("start".equals(evt.getPropertyName())) {
            this.fireDisplayNameChange(null, getDisplayName());

            int ind = 0;
            for (int i = 0; i < getParentNode().getChildren().getNodes().length; i++){
                if (this.equals(getParentNode().getChildren().getNodes()[i])){
                    ind = i;
                }
            }
            ((PropertiesAbstractNode)this.getParentNode()).fire(ind, evt);
        }

    }

    @Override
    public void destroy() throws IOException {
        PropertiesAbstractNode parent = (PropertiesAbstractNode)getParentNode();
        super.destroy();
        parent.updateName();
    }
}
