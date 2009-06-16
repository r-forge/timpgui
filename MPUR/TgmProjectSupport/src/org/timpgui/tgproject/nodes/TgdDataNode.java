/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.nodes;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.timpgui.tgproject.datasets.*;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

public class TgdDataNode extends DataNode implements Transferable {

    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgdDataNode.class, "TgdDataNode");
    private TgdDataObject obj;


    public TgdDataNode(TgdDataObject obj) {
        super(obj, Children.LEAF);
        this.obj = obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    TgdDataNode(TgdDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj = obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }
    

//    /** Creates a property sheet. */
//    @Override
//    protected Sheet createSheet() {
//        Sheet s = super.createSheet();
//        Sheet.Set ss = s.get(Sheet.PROPERTIES);
//        if (ss == null) {
//            ss = Sheet.createPropertiesSet();
//            s.put(ss);
//        }
//
////        ss.put();
//        // TODO add some relevant properties: ss.put(...)
//        return s;
//    }
//
    //Aditonal code added for TGM Project Support:
    private FileObject getFile() {
        return getDataObject().getPrimaryFile();
    }
    
     private Object getFromProject (Class clazz) {
        // TODO: fix unchecked conversion here
        Object result;
        Project p = FileOwnerQuery.getOwner(getFile());
        if (p != null) {
            result = p.getLookup().lookup(clazz);
        } else {
            result = null;
        }
        return result;
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
