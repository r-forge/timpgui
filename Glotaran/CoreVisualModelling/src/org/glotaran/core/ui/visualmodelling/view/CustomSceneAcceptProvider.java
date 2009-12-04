/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

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
        ConnectorState accept;
        PaletteItem item = getPaletteItemTransferable(transferable);
        if (item.getCategory().compareTo("Containers")==0) {
        Image dragImage = item.getImage();
        JComponent view = scene.getView();
        Graphics2D g2 = (Graphics2D) view.getGraphics();
        Rectangle visRect = view.getVisibleRect();
        view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
        g2.drawImage(dragImage,
                AffineTransform.getTranslateInstance(point.getLocation().getX(),
                point.getLocation().getY()),
                null);
        accept = ConnectorState.ACCEPT;
        } else {
            accept = ConnectorState.REJECT;
        }
        return accept;
    }

    public void accept(Widget widget, Point point, Transferable transferable) {

        final PaletteItem item = getPaletteItemTransferable(transferable);
            //DatasetComponentNode newNode = new DatasetComponentNode("test");
            MyNode newNode = new MyNode(item.getName(), item.getCategory(), nodeCount++); //TODO: move Mynode and rename
            newWidget = scene.addNode(newNode);
            newWidget.setPreferredLocation(point);
            scene.validate();
            scene.repaint();
            scene.getSceneAnimator().animatePreferredLocation(newWidget,point);

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

    private PaletteItem getPaletteItemTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(new DataFlavor(PaletteItem.class, "PaletteItem"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof PaletteItem ? (PaletteItem) o : null; //TODO: not null
    }

}