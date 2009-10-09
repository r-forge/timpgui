/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmfilesupport;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.timpgui.tgproject.nodes.TimpResultsNode;

public class TgmDataNode extends DataNode implements Transferable{

    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgmDataNode.class, "TgmDataNode");
    private TgmDataObject obj;

    public TgmDataNode(TgmDataObject obj) {
        super(obj, Children.LEAF);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
        setDropTarget();
    }

    TgmDataNode(TgmDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        this.obj=obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
        setDropTarget();
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


    private void setDropTarget() {
         DropTargetAdapter dt = new DropTargetAdapter() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                if(!dtde.isDataFlavorSupported(TimpResultsNode.DATA_FLAVOR)) {
               dtde.rejectDrag();
            }
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                NotifyDescriptor noNumItMessage = new NotifyDescriptor.Message(
                                "Incorrect number of iterations. 0 iterations will be done.");
                        DialogDisplayer.getDefault().notify(noNumItMessage);

            }
        };

    }
}