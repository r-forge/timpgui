/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.openide.cookies.InstanceCookie;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.timpgui.tdatasets.DatasetTimp;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;

/**
 *
 * @author lsp
 */
public class TimpDatasetNode extends DataNode implements Transferable{
    private TimpDatasetDataObject obj;
    private final Image ICON = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/doc.png", true);
    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TimpDatasetNode.class, "TimpDatasetNode");

    public TimpDatasetNode(TimpDatasetDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    TimpDatasetNode(TimpDatasetDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
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

   public DatasetTimp getTimpDataset() {
       DatasetTimp dataset = null;
       InstanceCookie ck = obj.getLookup().lookup(InstanceCookie.class);
       if (ck!=null) {
            try {
                dataset = (DatasetTimp) ck.instanceCreate();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
       }
       return dataset;
   }
}
