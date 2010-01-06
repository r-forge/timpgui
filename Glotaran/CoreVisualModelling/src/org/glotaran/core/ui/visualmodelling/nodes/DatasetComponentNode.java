/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author jsg210
 */
public class DatasetComponentNode extends PropertiesAbstractNode implements Transferable{

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/doc.png", true);
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(DatasetComponentNode.class, "DatasetComponentNode");

    private TimpDatasetNode tdn;

    public DatasetComponentNode(String name, Children children) {
        super(name, children);
        name = tdn.getDisplayName();
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children) {
        super(tdn.getDisplayName(), children);
        this.tdn = tdn;
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.put(tdn.getPropertySets()[0].getProperties());
        sheet.put(set);
        setSheet(sheet);
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
        if (t.isDataFlavorSupported(PaletteItem.DATA_FLAVOR)){
            try {
                final PaletteItem pi = (PaletteItem)t.getTransferData(PaletteItem.DATA_FLAVOR);
                return new PasteType() {
                    @Override
                    public Transferable paste() throws IOException {
                        if (pi.getName().equals("FreeParam")){
                            getChildren().add(new Node[]{new FreeParametersNode()});
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





   
}
