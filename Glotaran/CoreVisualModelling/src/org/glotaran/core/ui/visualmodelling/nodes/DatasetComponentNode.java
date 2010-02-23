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
import org.glotaran.core.main.mesages.CoreErrorMessages;
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
    PropertyChangeListener propListner;

    public DatasetComponentNode(String name, Children children) {
        super(name, children);
        name = tdn.getDisplayName();
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children);
        this.tdn = tdn;
        this.propListner = propListn;
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.put(tdn.getPropertySets()[0].getProperties());
        sheet.put(set);
        setSheet(sheet);
        addPropertyChangeListener(propListn);
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children, Lookup lookup, PropertyChangeListener propListn) {
        super(tdn.getDisplayName(), children, lookup);
        this.tdn = tdn;
        this.propListner = propListn;
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.put(tdn.getPropertySets()[0].getProperties());
        sheet.put(set);
        setSheet(sheet);
        addPropertyChangeListener(propListn);
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
    public PropertySet[] getPropertySets() {
        return getSheet().toArray();
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
                            if (pi.getName().equals("FreeParam")){
                                getChildren().add(new Node[]{new ModelDiffsNode("FreeParameter",propListner, getdatasetIndex())});
                                return null;
                            }
                            if (pi.getName().equals("ChangeParam")){
                                getChildren().add(new Node[]{new ModelDiffsChangeNode("ChangeParameter",propListner, getdatasetIndex())});
                                return null;
                            }
                            if (pi.getName().equals("AddParam")){
                                getChildren().add(new Node[]{new ModelDiffsNode("AddParameter",propListner, getdatasetIndex())});
                                return null;
                            }
                            if (pi.getName().equals("RemoveParam")){
                                getChildren().add(new Node[]{new ModelDiffsNode("RemoveParameter",propListner, getdatasetIndex())});
                                return null;
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
        firePropertyChange("mainNodeDeleted", getdatasetIndex(), getLookup().lookup(GtaDataset.class));
        super.destroy();
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
