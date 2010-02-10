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
import java.util.ArrayList;
import java.util.Formatter;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.ModelDiffsDO;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
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
    private String[] parameters = null;
    private int[] paramInd = null;
    private String[] paramValues = null;
    private int[] paramValInd = null;
    private Integer selectedType = new Integer(0);
    private String[] paramNames = new String[]{"KinPar","IrfPar","ParMu","ParTau"};

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

    private Tgm getConnectedModel(){
        Node node = this;
        while (!node.getClass().equals(DatasetsRootNode.class)){
            node = node.getParentNode();
        }
        DatasetsRootNode rootNode = (DatasetsRootNode)node;
        return rootNode.getContainerComponent().getConnectedModel();
    }

    private void getParamsFromModel(){
        ArrayList<String> tempPar = new ArrayList<String>();
        Tgm model = getConnectedModel();

        if (!model.getDat().getKinparPanel().getKinpar().isEmpty()){
            tempPar.add(paramNames[0]);
        }
        if (!model.getDat().getIrfparPanel().getIrf().isEmpty()){
            tempPar.add(paramNames[1]);
        }
        if (!model.getDat().getIrfparPanel().getParmu().isEmpty()){
            tempPar.add(paramNames[2]);
        }
        if (!model.getDat().getIrfparPanel().getPartau().isEmpty()){
            tempPar.add(paramNames[3]);
        }
        parameters = new String[tempPar.size()];
        paramInd = new int[tempPar.size()];
        for (int i = 0; i < tempPar.size(); i++){
            parameters[i] = tempPar.get(i);
            paramInd[i] = i;
        }
    }

    private void getParamList(){
        Tgm model = getConnectedModel();
        if (parameters[selectedType].equalsIgnoreCase(paramNames[0])){
            paramValues = new String[model.getDat().getKinparPanel().getKinpar().size()];
            paramValInd = new int[model.getDat().getKinparPanel().getKinpar().size()];
            for (int i = 0; i < model.getDat().getKinparPanel().getKinpar().size(); i++){
                paramValInd[i] = i;
                paramValues[i] = "k"+(i+1)+" ("+
                        new Formatter().format("%g",model.getDat().getKinparPanel().getKinpar().get(i).getStart()).toString()+")";
            }
        }
        if (parameters[selectedType].equalsIgnoreCase(paramNames[1])){
            paramValues = new String[model.getDat().getIrfparPanel().getIrf().size()];
            paramValInd = new int[model.getDat().getIrfparPanel().getIrf().size()];
            for (int i = 0; i < model.getDat().getIrfparPanel().getIrf().size(); i++){
                paramValInd[i] = i;
                paramValues[i] = "irf"+(i+1)+" ("+
                        new Formatter().format("%g",model.getDat().getIrfparPanel().getIrf().get(i)).toString()+")";
            }
        }

        if (parameters[selectedType].equalsIgnoreCase(paramNames[2])){
//todo implement
        }
        if (parameters[selectedType].equalsIgnoreCase(paramNames[3])){
//todo implement
        }

    }

    private Property createFreParProperty(){
        getParamList();
        Property paramIndex = new Property(Integer.class) {
            @Override
            public boolean canRead() {
                return true;
            }
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return getDataObj().getIndex();
            }
            @Override
            public boolean canWrite() {
                return true;
            }
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                getDataObj().setIndex((Integer)val+1);
            }
        };
        paramIndex.setValue("intValues",paramValInd);
        paramIndex.setValue("stringKeys",paramValues);
        paramIndex.setName("ParamValue");
        return paramIndex;
    }

    private Property createFreParTypeProperty(){
        getParamsFromModel();
        Property freeParm = new Property(Integer.class) {
            @Override
            public boolean canRead() {
                return true;
            }
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return selectedType;
            }
            @Override
            public boolean canWrite() {
                return true;
            }
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                selectedType = (Integer)val;
                updateFreParProperty();
            }
        };
        freeParm.setValue("intValues",paramInd);
        freeParm.setValue("stringKeys",parameters);
        freeParm.setName("ParamType");
        return freeParm;
    }

    private void updateFreParProperty(){
        getSheet().get(Sheet.PROPERTIES).remove("ParamValue");
        getSheet().get(Sheet.PROPERTIES).put(createFreParProperty());
    }

    @Override
    public String getDisplayName() {
        return new Formatter().format("%g",getLookup().lookup(ModelDiffsDO.class).getStart()).toString();
    }


    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        ModelDiffsDO obj = getDataObj();
        Property modelDiffType = null;
        Property startingValue = null;
        try {
            modelDiffType = new PropertySupport.ReadOnly("Type", String.class, "Type", "Type of the modeldifference") {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return ((ModelDiffsNode) getParentNode()).getType();
                }
            };
            startingValue = new PropertySupport.Reflection(obj, Double.class, "start");
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }

        startingValue.setName("Starting value");

        set.put(modelDiffType);
        set.put(startingValue);
        set.put(createFreParTypeProperty());
        set.put(createFreParProperty());
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
