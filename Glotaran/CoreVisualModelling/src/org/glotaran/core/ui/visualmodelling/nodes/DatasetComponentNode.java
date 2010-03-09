/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author jsg210
 */
public class DatasetComponentNode extends PropertiesAbstractNode implements Transferable{

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/doc.png", true);
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(DatasetComponentNode.class, "DatasetComponentNode");
    private TimpDatasetNode tdn;
    private PropertyChangeListener propListner;
    private Integer group = 0;
    private Boolean scalEnabled = false;
    private Double dscalValue = 1.0;
    private String[] propNames = new String[]{"Name","Group Index","Scaling enabled","Scaling value"};

    public DatasetComponentNode(String name, Children children) {
        super(name, children);
        name = tdn.getDisplayName();
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children);
        this.tdn = tdn;
        this.propListner = propListn;
        addPropertyChangeListener(propListn);
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, Lookup lookup, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children, lookup);
        this.tdn = tdn;
        this.propListner = propListn;
        addPropertyChangeListener(propListn);
    }

    @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        if (isConnected()){
            return name+" ["+String.valueOf(getGroup()+1)+"]";
        }
        return name;
    }

    public Integer getGroup() {
        if (group >= getParentNode().getChildren().getNodesCount()){
            setGroup(getParentNode().getChildren().getNodesCount()-1);
//            CoreErrorMessages.selCorrChNum();
        }
        return group;
    }

    public void setGroup(Integer group) {
        if (group < getParentNode().getChildren().getNodesCount()){
            this.group = group;
            fireNameChange(null, getDisplayName());
            firePropertyChange("groupIndexChanged", null, this.group+1);
//            System.out.println(String.valueOf(group));
        } else {
            CoreErrorMessages.selCorrChNum();
        }
    }

    public Double getDscalValue() {
        return dscalValue;
    }

    public void setDscalValue(Double dscalValue) {
        this.dscalValue = dscalValue;
        firePropertyChange("dscalValue", null, dscalValue);
    }

    
    public Boolean getScalEnabled() {
        return scalEnabled;
    }

    public void setScalEnabled(Boolean scalEnabled) {
        this.scalEnabled = scalEnabled;
        if (scalEnabled) {
            try {
                Property<Double> dscalVal = new PropertySupport.Reflection<Double>(this, Double.class, "dscalValue");
                dscalVal.setName(propNames[3]);
                getSheet().get(Sheet.PROPERTIES).put(dscalVal);
                firePropertyChange("dscalEnabled", null, scalEnabled);
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        else {
            getSheet().get(Sheet.PROPERTIES).remove(propNames[3]);
        }
//        firePropertyChange("SetBackSweep", null, backSweep);
        
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
    public Transferable drag() throws IOException {
        return this;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        Property<String> datasetNname= null;
        Property<Boolean> discalEnabled = null;
        Property<Double> discalVal = null;
        datasetNname = new PropertySupport.ReadOnly<String>(propNames[0], String.class, "Name", "Name of the dataset") {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return tdn.getDisplayName();
            }
        };
        try {
            discalEnabled = new PropertySupport.Reflection<Boolean>(this, Boolean.class, "scalEnabled");
            discalVal = new PropertySupport.Reflection<Double>(this, Double.class, "dscalValue");
            discalEnabled.setName(propNames[2]);
            discalVal.setName(propNames[3]);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        set.put(datasetNname);
        if (isConnected()){
            set.put(createGroupProperty());
            if (enableDscalProperty()){
                set.put(discalEnabled);
                if (scalEnabled){
                    set.put(discalVal);
                }
            }
        }
        sheet.put(set);
        return sheet;
    }

    private String[] craeteGroupList(){
        int count = getParentNode().getChildren().getNodesCount();
        String[] groupList = new String[count];
        for (int i = 0; i < count; i++){
            groupList[i] = "Group"+String.valueOf(i+1);
        }
        return groupList;
    }

    private int[] craeteIndList(){
        int count = getParentNode().getChildren().getNodesCount();
        int[] groupInd = new int[count];
        for (int i = 0; i < count; i++){
            groupInd[i] = i;
        }
        return groupInd;
    }

    private Property<Integer> createGroupProperty(){

        Property<Integer> paramIndex = new Property<Integer>(Integer.class) {
            @Override
            public boolean canRead() {
                return true;
            }
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return getGroup();
            }
            @Override
            public boolean canWrite() {
                return true;
            }
            @Override
            public void setValue(Integer val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setGroup(val);
            }
        };
        paramIndex.setValue("intValues",craeteIndList());
        paramIndex.setValue("stringKeys",craeteGroupList());
        paramIndex.setName(propNames[1]);
        return paramIndex;
    }

    //TODO implement dscal property (only for datasets same group)


    private boolean enableDscalProperty(){
        int groupMembCount = 0;
        if (isConnected()) {
            if (((DatasetsRootNode) getParentNode()).getContainerComponent().getModelDifferences().getThreshold() >= 0) {
                int groupIndex = getGroup();
                for (Node node : getParentNode().getChildren().getNodes()) {
                    if (((DatasetComponentNode) node).getGroup() == groupIndex) {
                        groupMembCount++;
                    }
                }
            }
        }
        if (groupMembCount > 1){
            return true;
        }
        return false;
    }

    public void updatePropSheet(){
        setSheet(createSheet());
    }

    @Override
    public PasteType getDropType(Transferable t, int action, int index) {        
        if (t.isDataFlavorSupported(PaletteNode.DATA_FLAVOR)){
            try {
                final PaletteItem pi = (PaletteItem)t.getTransferData(PaletteNode.DATA_FLAVOR);
                return new PasteType() {
                    @Override
                    public Transferable paste() throws IOException {
                        if (isConnected()){
                            Boolean present = false;
                            if (pi.getName().equals("FreeParam")){
                                for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                    if (getChildren().getNodes()[i] instanceof ModelDiffsNode &&
                                            (((ModelDiffsNode)getChildren().getNodes()[i])).getType().equalsIgnoreCase("FreeParameter")){
                                        present = true;
                                    }
                                }
                                if (!present){
                                    getChildren().add(new Node[]{new ModelDiffsNode("FreeParameter",propListner, getdatasetIndex())});
                                    return null;
                                } else {
                                    CoreErrorMessages.parametersExists("Free Parameter");
                                }
                            }
                            
                            if (pi.getName().equals("AddParam")){
                                for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                    if (getChildren().getNodes()[i] instanceof ModelDiffsNode &&
                                            (((ModelDiffsNode)getChildren().getNodes()[i])).getType().equalsIgnoreCase("AddParameter")){
                                        present = true;
                                    }
                                }
                                if (!present){
                                    getChildren().add(new Node[]{new ModelDiffsNode("AddParameter",propListner, getdatasetIndex())});
                                    return null;
                                } else {
                                    CoreErrorMessages.parametersExists("Add Parameter");
                                }
                            }
                            if (pi.getName().equals("RemoveParam")){
                                for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                    if (getChildren().getNodes()[i] instanceof ModelDiffsNode &&
                                            (((ModelDiffsNode)getChildren().getNodes()[i])).getType().equalsIgnoreCase("RemoveParameter")){
                                        present = true;
                                    }
                                }
                                if (!present){
                                    getChildren().add(new Node[]{new ModelDiffsNode("RemoveParameter",propListner, getdatasetIndex())});
                                    return null;
                                } else {
                                    CoreErrorMessages.parametersExists("Remove Parameter");
                                }
                            }
                        
                            
                            if (pi.getName().equals("ChangeParam")){
                                for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                    if (getChildren().getNodes()[i] instanceof ModelDiffsChangeNode) {
                                        present = true;
                                    }
                                }
                                if (!present){
                                    getChildren().add(new Node[]{new ModelDiffsChangeNode("ChangeParameter",propListner, getdatasetIndex())});
                                    firePropertyChange("ChangeParamAdded", getdatasetIndex(), null );
                                    return null;
                                } else {
                                    CoreErrorMessages.parametersExists("Change Parameter");
                                }
                            }

                        }
                        else {
                            CoreErrorMessages.containerNotConnected();
                        }
                        return null;
                    }
                };
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            return null;
        }
        else {
            return null;
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
      return(new DataFlavor[]{DATA_FLAVOR});
   }

   public boolean isDataFlavorSupported(DataFlavor flavor) {
      return(flavor == DATA_FLAVOR);
   }

   public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
      if(flavor == DATA_FLAVOR) {
         return(this);
      } else {
         throw new UnsupportedFlavorException(flavor);
      }
   }

    public TimpDatasetNode getTdn() {
        return tdn;
    }

    @Override
    public void destroy() throws IOException {
        DatasetsRootNode parNode = (DatasetsRootNode) getParentNode();
        firePropertyChange("mainNodeDeleted", getdatasetIndex(), getLookup().lookup(GtaDataset.class));
        super.destroy();
        parNode.updateChildrensProperties();
    }

    private boolean isConnected(){
        return ((DatasetsRootNode)getParentNode()).getContainerComponent().isConnected();
    }

    public int getdatasetIndex(){
        for (int i = 0; i < getParentNode().getChildren().getNodesCount(); i++){
            if (getParentNode().getChildren().getNodes()[i].equals(this)){
                return i+1;
            }
        }
        return 1;
    }
}
