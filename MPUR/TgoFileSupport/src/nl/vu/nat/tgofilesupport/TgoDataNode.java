/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgofilesupport;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

public class TgoDataNode extends DataNode implements Transferable {

    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgofilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgoDataNode.class, "TgoDataNode");
    private TgoDataObject obj;

    public TgoDataNode(TgoDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    TgoDataNode(TgoDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
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
//        // TODO add some relevant properties: ss.put(...)
//        return s;
//    }
    
    private FileObject getFile() {
        return getDataObject().getPrimaryFile();
    }

        @Override
    public Transferable drag() {
        return (this);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return (new DataFlavor[]{DATA_FLAVOR});
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor == DATA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor == DATA_FLAVOR) {
            return (this);
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

}
