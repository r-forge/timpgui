/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.glotaran.core.models.tgm.KinparPanelModel;
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
    Boolean positiveKinpar = false;
    Boolean seqModel = false;

    public KineticParametersNode(PropertyChangeListener listn){
        super("KinPar", new NonLinearParametersKeys(0));
        this.addPropertyChangeListener(listn);
//        propListner = listn;
//        addPropertyChangeListener(WeakListeners.propertyChange(propListner, this));
    }

    public KineticParametersNode(KinparPanelModel kinparPanel, PropertyChangeListener listn) {
        super("KinPar", new NonLinearParametersKeys(kinparPanel.getKinpar()));
        positiveKinpar = (kinparPanel.isPositivepar());
        seqModel = (kinparPanel.isSeqmod());
        this.addPropertyChangeListener(listn);
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
        Property posKin = null;
        Property seqMod = null;
        try {
            numberOfComponents = new PropertySupport.Reflection(this, Integer.class, "getCompNum", "setCompNum");
            name = new PropertySupport.Reflection(this, String.class, "getDisplayName", null);
            posKin = new PropertySupport.Reflection(this, Boolean.class, "positiveKinpar");
            seqMod = new PropertySupport.Reflection(this, Boolean.class, "seqModel");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        numberOfComponents.setName("Number of components");
        name.setName("Name");
        posKin.setName("Positise rates");
        seqMod.setName("Sequential model");
        set.put(name);
        set.put(numberOfComponents);
        set.put(posKin);
        set.put(seqMod);
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
//        propListner.propertyChange(new PropertyChangeEvent(this, "Number of components", new Integer(currCompNum), compNum));
        firePropertyChange("Number of components", new Integer(currCompNum), compNum);
    }

    public Boolean getPositiveKinpar() {
        return positiveKinpar;
    }

    public void setPositiveKinpar(Boolean positiveKinpar) {
        this.positiveKinpar = positiveKinpar;
        firePropertyChange("Positise rates", null, positiveKinpar);
    }

    public Boolean getSeqModel() {
        return seqModel;
    }

    public void setSeqModel(Boolean seqModel) {
        this.seqModel = seqModel;
        firePropertyChange("Sequential model", null, seqModel);
    }

    @Override
    public void fire(int index, PropertyChangeEvent evt){
        if ("start".equals(evt.getPropertyName())) {
            firePropertyChange("start", index, evt.getNewValue());
        }
        if ("fixed".equals(evt.getPropertyName())) {
            firePropertyChange("fixed", index, evt.getNewValue());
        }
        if ("delete".equals(evt.getPropertyName())) {
            firePropertyChange("delete", index, evt.getNewValue());
        }
    }
}
