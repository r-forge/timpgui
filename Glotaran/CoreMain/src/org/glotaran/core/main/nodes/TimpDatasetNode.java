/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.main.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 *
 * @author lsp
 */
public class TimpDatasetNode extends DataNode implements Transferable{
    private TimpDatasetDataObject obj;
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/doc.png", true);
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TimpDatasetNode.class, "TimpDatasetNode");

    public TimpDatasetNode(TimpDatasetDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
    }

    public TimpDatasetNode(TimpDatasetDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
    }

   public TimpDatasetDataObject getObject(){
       return obj;
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
    public String getDisplayName() {
        return obj.getName();
    }

    @Override
   public Transferable drag() {
      return(this);
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
