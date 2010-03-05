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

    public DatasetComponentNode(String name, Children children) {
        super(name, children);
        name = tdn.getDisplayName();
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children);
        this.tdn = tdn;
        this.propListner = propListn;
//        Sheet sheet = Sheet.createDefault();
//        Sheet.Set set = Sheet.createPropertiesSet();
//        set.put(tdn.getPropertySets()[0].getProperties());
//        sheet.put(set);
//        setSheet(sheet);
        addPropertyChangeListener(propListn);
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, Lookup lookup, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children, lookup);
        this.tdn = tdn;
        this.propListner = propListn;
//        Sheet sheet = Sheet.createDefault();
//        Sheet.Set set = Sheet.createPropertiesSet();
//        set.put(tdn.getPropertySets()[0].getProperties());
//        sheet.put(set);
//        setSheet(sheet);
        addPropertyChangeListener(propListn);
    }

    @Override
    public String getDisplayName() {
        String name = super.getDisplayName();
        return name+" ["+String.valueOf(getGroup()+1)+"]";
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
            firePropertyChange("groupIndexChanged", getdatasetIndex()-1, this.group+1);
        } else {
            CoreErrorMessages.selCorrChNum();
        }
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
        set.put(createGroupProperty());
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
        paramIndex.setName("GroupIndex");
        return paramIndex;
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
