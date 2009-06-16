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
import org.openide.filesystems.FileObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author lsp
 */
public class TgdDataChildrenNode extends AbstractNode implements Transferable{
    private FileObject filObj;
    private final Image ICON = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/doc.png", true);
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgdDataChildrenNode.class, "TgdDataChildrenNode");

    public TgdDataChildrenNode(FileObject fileObject) {
        super(Children.LEAF);
        filObj = fileObject;

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
        return filObj.getName();
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
