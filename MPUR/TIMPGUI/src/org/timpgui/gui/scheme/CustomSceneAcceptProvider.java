/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.gui.scheme;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.Utilities;
import org.timpgui.gui.scheme.components.DatasetContainer;

/**
 *
 * @author jsg210
 */
public class CustomSceneAcceptProvider implements AcceptProvider {

    private GraphScene scene;
    private Point point;
    private int nodeCount=1;

    public CustomSceneAcceptProvider(GraphScene scene) {
        this.scene = scene;
    }

    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        //Image dragImage = getImageFromTransferable(transferable);
        ConnectorState accept;
        MyNode node = getNodeFromTransferable(transferable);
        if (node!=null) {
        Image dragImage = node.getImage();
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
        final MyNode node = getNodeFromTransferable(transferable);
            //String hm = "Pallete Node"+(nodeCount++);
            ComponentWidget cw = new ComponentWidget(scene, new DatasetContainer());
            cw.setPreferredLocation(point);
            cw.getActions().addAction(ActionFactory.createMoveAction());
            scene.addChild(cw);
            //Widget newNode = scene.addNode(hm);
            //scene.getSceneAnimator().animatePreferredLocation(newNode,point);
            scene.validate();
            scene.repaint();
    }

    private MyNode getNodeFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(new DataFlavor(Shape.class, "Shape"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof MyNode ? (MyNode) o : null; //TODO: not null
    }

    private String getNameFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof String ? (String) o : "unknown";
    }

    private Image getImageFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof Image ? (Image) o : Utilities.loadImage("org/netbeans/shapesample/palette/shape1.png");
    }
}