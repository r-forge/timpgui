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
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.timpgui.tgproject.datasets.*;

/**
 *
 * @author lsp
 */
public class TimpResultsNode extends DataNode implements Transferable{

    private TimpResultDataObject obj;
    private final Image ICON = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/results.png", true);
    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TimpResultsNode.class, "TimpResultsNode");


    public TimpResultsNode(TimpResultDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    TimpResultsNode(TimpResultDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

   public TimpResultDataObject getObject(){
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

    public DataFlavor[] getTransferDataFlavors() {
        return(new DataFlavor[]{DATA_FLAVOR});
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return(flavor == DATA_FLAVOR);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if(flavor == DATA_FLAVOR) {
         return(this);
      } else {
         throw new UnsupportedFlavorException(flavor);
      }
    }
}
