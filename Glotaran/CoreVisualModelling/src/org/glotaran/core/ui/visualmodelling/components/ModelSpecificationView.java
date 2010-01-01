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
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.ui.visualmodelling.nodes.CohSpecNode;
import org.glotaran.core.ui.visualmodelling.nodes.DispersionModelingNode;
import org.glotaran.core.ui.visualmodelling.nodes.IrfParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KineticParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.WeightParametersNode;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.TreeTableView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class ModelSpecificationView extends TreeTableView {

    public ModelSpecificationView() {
          setRootVisible(false);
          setDropTarget();
    }

   private void setDropTarget() {
      DropTarget dt = new DropTarget(this, new DropTargetAdapter() {

         @Override
         public void dragEnter(DropTargetDragEvent dtde) {
             if (dtde.getTransferable().isDataFlavorSupported(PaletteNode.DATA_FLAVOR)){
                 PaletteItem item = null;
                try {
                    item = (PaletteItem) dtde.getTransferable().getTransferData(PaletteNode.DATA_FLAVOR);
                } catch (UnsupportedFlavorException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                if (item.getCategory().compareTo("Modelling") != 0) {
                    dtde.rejectDrag();
                }
             } else {
                 dtde.rejectDrag();
             }
         }

         public void drop(DropTargetDropEvent dtde) {
             PaletteItem item = null;
             try {
                 item = (PaletteItem) dtde.getTransferable().getTransferData(PaletteNode.DATA_FLAVOR);
                 Children nodes = ExplorerManager.find(getParent()).getRootContext().getChildren();
                 boolean present = false;
//================ kinetic parameter node creation ===================
                 if (item.getName().equalsIgnoreCase("Kinetic Parameters")){
                     for (int i = 0; i < nodes.getNodesCount(); i ++){
                         if (nodes.getNodes()[i] instanceof KineticParametersNode){
                             present = true;
                         }
                     }
                     if (!present){
                         nodes.add(new Node[]{new KineticParametersNode()});
                     }
                     else {
                         CoreErrorMessages.parametersExists("Kinetic parameters ");
                     }
                 }
                 else {
//================ irf parameter node creation ===================
                     if (item.getName().equalsIgnoreCase("IRF Parameters")) {
                         for (int i = 0; i < nodes.getNodesCount(); i++) {
                             if (nodes.getNodes()[i] instanceof IrfParametersNode) {
                                 present = true;
                             }
                         }
                         if (!present) {
                             nodes.add(new Node[]{new IrfParametersNode()});
                         } else {
                             CoreErrorMessages.parametersExists("IRF Parameters ");
                         }
                     }
                     else {
//================ disp parameter node creation ===================
                         if (item.getName().equalsIgnoreCase("Dispersion")) {
                             int paramNumb = 0;
                             for (int i = 0; i < nodes.getNodesCount(); i++) {
                                 if (nodes.getNodes()[i] instanceof DispersionModelingNode) {
                                     paramNumb++;
                                 }
                             }
                             if (paramNumb < 2) {
                                 nodes.add(new Node[]{new DispersionModelingNode()});
                             } else {
                                 CoreErrorMessages.parametersExists("2 Dispersion parameters ");
                             }
                         }
                         else {
//================ cohspec parameter node creation ===================
                             if (item.getName().equalsIgnoreCase("Cohspec Parameters")) {
                                 for (int i = 0; i < nodes.getNodesCount(); i++) {
                                     if (nodes.getNodes()[i] instanceof CohSpecNode) {
                                         present = true;
                                     }
                                 }
                                 if (!present) {
                                     nodes.add(new Node[]{new CohSpecNode()});
                                 } else {
                                     CoreErrorMessages.parametersExists("CohSpec parameters ");
                                 }
                             } 
                             else {
//================ weight parameter node creation ===================
                                 if (item.getName().equalsIgnoreCase("Weight Parameters")) {
                                     for (int i = 0; i < nodes.getNodesCount(); i++) {
                                         if (nodes.getNodes()[i] instanceof WeightParametersNode) {
                                             present = true;
                                         }
                                     }
                                     if (!present) {
                                         nodes.add(new Node[]{new WeightParametersNode()});
                                     } else {
                                         CoreErrorMessages.parametersExists("Weight parameters ");
                                     }
                                 }
                                 else {
//================ rest node creation ===================
                                     ExplorerManager.find(getParent()).getRootContext().getChildren().add(new Node[]{new AbstractNode(Children.LEAF)});
                                 }
                             }
                         }
                     }
                 }
            } catch(Exception e) {
               e.printStackTrace();
               //dtde.rejectDrop();
               
            }
         }

      });

      setDropTarget(dt);
   }
}