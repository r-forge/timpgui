/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.components;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetComponentNode;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.explorer.view.TreeTableView;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class DatasetSpecificationView extends TreeTableView {

    public DatasetSpecificationView() {
        setRootVisible(false);
        setDropTarget();
    }

    private void setDropTarget() {
        DropTarget dt = new DropTarget(this, new DropTargetAdapter() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                if (dtde.isDataFlavorSupported(TimpDatasetNode.DATA_FLAVOR)) {
                    dtde.acceptDrag(dtde.getDropAction());
                } else {
                    dtde.rejectDrag();
                }
            }

            public void drop(DropTargetDropEvent dtde) {
                try {
                    TimpDatasetNode n = (TimpDatasetNode) dtde.getTransferable().getTransferData(TimpDatasetNode.DATA_FLAVOR);
                    ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new DatasetComponentNode(n,Children.create(new DummyChildFactory(), true))});
                } catch (Exception e) {
                    e.printStackTrace();
                    dtde.rejectDrop();
                }
            }
        });

        setDropTarget(dt);
    }
}
