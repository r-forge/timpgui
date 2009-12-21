/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.components;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetComponentNode;
import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.TreeTableView;
import org.openide.nodes.Node;

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
              if (!dtde.isDataFlavorSupported(TimpDatasetNode.DATA_FLAVOR)) {
                    dtde.rejectDrag();
//                }
//             if (dtde.getTransferable().isDataFlavorSupported(PaletteNode.DATA_FLAVOR)){
//                 PaletteItem item = null;
//                try {
//                    item = (PaletteItem) dtde.getTransferable().getTransferData(PaletteNode.DATA_FLAVOR);
//                } catch (UnsupportedFlavorException ex) {
//                    Exceptions.printStackTrace(ex);
//                } catch (IOException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
//                if (item.getCategory().compareTo("MOdeldiffs") != 0) {
//                    dtde.rejectDrag();
//                }
             } else {
                 
             }
         }

         public void drop(DropTargetDropEvent dtde) {

                  try {
                    TimpDatasetNode n = (TimpDatasetNode) dtde.getTransferable().getTransferData(TimpDatasetNode.DATA_FLAVOR);
                    //   TopComponent currentTopComponent = (TopComponent)Utilities.actionsGlobalContext().lookup(TopComponent.class);
                    // ExplorerManager.find(currentTopComponent).getRootContext().getChildren().add(new Node[]{n});
                    ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new DatasetComponentNode(n, new NonLinearParametersKeys(3))});
                } catch (Exception e) {
                    e.printStackTrace();
                    //dtde.rejectDrop();
                }

//             PaletteItem item = null;
//             try {
//                 item = (PaletteItem) dtde.getTransferable().getTransferData(PaletteNode.DATA_FLAVOR);
////               TimpDatasetNode n = (TimpDatasetNode)dtde.getTransferable().getTransferData(TimpDatasetNode.DATA_FLAVOR);
////               TopComponent currentTopComponent = (TopComponent)Utilities.actionsGlobalContext().lookup(TopComponent.class);
////               ExplorerManager.find(currentTopComponent).getRootContext().getChildren().add(new Node[]{n});
//
//                 if (item.getName().equalsIgnoreCase("Kinetic Parameters")){
//                     ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new KineticParametersNode()});
//                 }
//                 else {
//                     if(item.getName().equalsIgnoreCase("IRF Parameters")){
//                         ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new IrfParametersNode()});
//                     }
//                     else {
//                        ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new AbstractNode(Children.LEAF)});
//                     }
//                 }
//            } catch(Exception e) {
//               e.printStackTrace();
//               //dtde.rejectDrop();
//
//            }
 }

      });

      setDropTarget(dt);
   }
}