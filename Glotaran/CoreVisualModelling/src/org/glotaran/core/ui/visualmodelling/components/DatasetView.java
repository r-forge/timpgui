/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.components;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.JList;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.ListView;
import org.openide.nodes.Node;

/**
 *
 * @author jsg210
 */
public class DatasetView extends ListView {

    public DatasetView() {
        createList();
        list.setCellRenderer(new datasetPanel());
        setDropTarget();
    }

    private void setDropTarget() {
        DropTarget dt = new DropTarget(list, new DropTargetAdapter() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                if (!dtde.isDataFlavorSupported(TimpDatasetNode.DATA_FLAVOR)) {
                    dtde.rejectDrag();
                }
            }

            public void drop(DropTargetDropEvent dtde) {
                try {
                    TimpDatasetNode n = (TimpDatasetNode) dtde.getTransferable().getTransferData(TimpDatasetNode.DATA_FLAVOR);
                    //   TopComponent currentTopComponent = (TopComponent)Utilities.actionsGlobalContext().lookup(TopComponent.class);
                    // ExplorerManager.find(currentTopComponent).getRootContext().getChildren().add(new Node[]{n});
                    ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new TimpDatasetNode((TimpDatasetDataObject)n.getDataObject())});
                } catch (Exception e) {
                    e.printStackTrace();
                    //dtde.rejectDrop();

                }
            }
        });
        setDropTarget(dt);
    }
}
