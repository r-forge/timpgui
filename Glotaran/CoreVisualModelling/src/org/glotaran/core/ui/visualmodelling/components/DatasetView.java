/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.components;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.openide.explorer.view.TreeTableView;

/**
 *
 * @author jsg210
 */
public class DatasetView extends JPanel {

    public DatasetView() {
        setDropTarget();
    }

    private void setDropTarget() {
        DropTarget dt = new DropTarget(this, new DropTargetAdapter() {

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
                    //ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new TimpDatasetNode((TimpDatasetDataObject)n.getDataObject())});
                } catch (Exception e) {
                    e.printStackTrace();
                    //dtde.rejectDrop();

                }
            }
        });
        setDropTarget(dt);
    }

    private void addTimpDatasetNode() {
    }
}
