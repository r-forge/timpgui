/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.nodecontainers;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.TreeTableView;
import org.openide.nodes.Node;

/**
 *
 * @author jsg210
 */
public class DatasetView extends TreeTableView {

    public DatasetView() {
        setRootVisible(false);
        setDropTarget();
    }

    public void setDefaultActionProcessor(final ActionListener action) {
        setDefaultActionAllowed(false);
        tree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    action.actionPerformed(null);
                }
            }
        });

        treeTable.registerKeyboardAction(action,
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);
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
                    ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new TimpDatasetNode((TimpDatasetDataObject) n.getDataObject())});
                } catch (Exception e) {
                    CoreErrorMessages.dragDropException();
                }
            }
        });
        setDropTarget(dt);
    }
}
