/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.view;

import org.glotaran.core.ui.visualmodelling.nodes.VisualAbstractNode;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.glotaran.core.ui.visualmodelling.common.VisualCommonFunctions;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class CustomSceneAcceptProvider implements AcceptProvider {

    private GraphScene scene;
    private Point point;
    private int nodeCount=0;
    private Widget newWidget;

    public CustomSceneAcceptProvider(GraphScene scene) {
        this.scene = (GraphScene) scene;
    }

    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        //Image dragImage = getImageFromTransferable(transferable);
        ConnectorState accept = ConnectorState.REJECT;
        if (transferable.isDataFlavorSupported(TgmDataNode.DATA_FLAVOR)){
            accept = ConnectorState.ACCEPT;
        } else {
            if (transferable.isDataFlavorSupported(PaletteNode.DATA_FLAVOR)){
                PaletteItem item = null;
                try {
                    item = (PaletteItem) transferable.getTransferData(PaletteNode.DATA_FLAVOR);
                } catch (UnsupportedFlavorException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                if (item.getCategory().compareTo("Containers") == 0) {
                    accept = ConnectorState.ACCEPT;
                }
            } else {
                accept = ConnectorState.REJECT;
            }
        }
        return accept;
    }

    public void accept(Widget widget, Point point, Transferable transferable) {
        VisualAbstractNode newNode = null;
        if (transferable.isDataFlavorSupported(TgmDataNode.DATA_FLAVOR)){
            try {
                TgmDataNode tgmNode = (TgmDataNode) transferable.getTransferData(TgmDataNode.DATA_FLAVOR);
                newWidget = scene.addNode(tgmNode);

            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {

            final PaletteItem item = VisualCommonFunctions.getPaletteItemTransferable(transferable);
            //DatasetComponentNode newNode = new DatasetComponentNode("test");
            newNode = new VisualAbstractNode(item.getName(), item.getCategory(), nodeCount++); //TODO: move Mynode and rename
            newWidget = scene.addNode(newNode);

        //Widget newWidget2 = scene.attachNodeWidget(dcn);
            //String hm = "Pallete Node"+(nodeCount++);
            //if shape.getCategory()
            //ComponentWidget cw = new ComponentWidget(scene, new DatasetContainer());
            //cw.setPreferredLocation(point);
            //cw.getActions().addAction(scene.getMoveAction());
            //cw.getActions().addAction(scene.getConnectAction());

            //Widget newNode = scene.addNode(hm);
            //scene.getSceneAnimator().animatePreferredLocation(newNode,point);

        }
            
            newWidget.setPreferredLocation(point);
            scene.validate();
//            scene.repaint();
//            scene.getSceneAnimator().animatePreferredLocation(newWidget,point);
    }
}