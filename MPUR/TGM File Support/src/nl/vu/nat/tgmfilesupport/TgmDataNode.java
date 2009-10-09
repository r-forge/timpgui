/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmfilesupport;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.timpgui.tgproject.nodes.TimpResultsNode;

public class TgmDataNode extends DataNode implements Transferable, DropTargetListener {

    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgmDataNode.class, "TgmDataNode");
    private TgmDataObject obj;

    public TgmDataNode(TgmDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    TgmDataNode(TgmDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    /** Creates a property sheet. */
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
    //Aditonal code added for TGM Project Support:
    private FileObject getFile() {
        return getDataObject().getPrimaryFile();
    }

    public TgmDataObject getObject() {
        return obj;
    }

    private Object getFromProject(Class clazz) {
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

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        if(!dtde.isDataFlavorSupported(TimpResultsNode.DATA_FLAVOR)) {
               dtde.rejectDrag();
        }
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
