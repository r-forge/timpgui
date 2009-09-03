/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.gui.scheme;

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
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.util.Utilities;
import org.timpgui.gui.scheme.components.DatasetContainer;
import org.timpgui.gui.scheme.nodes.DatasetContainerNode;
import org.timpgui.gui.scheme.palette.TimpguiComponent;

/**
 *
 * @author jsg210
 */
public class CustomSceneAcceptProvider implements AcceptProvider {

    private GraphSceneImpl scene;
    private Point point;
    private int nodeCount=1;

    public CustomSceneAcceptProvider(GraphScene scene) {
        this.scene = (GraphSceneImpl) scene;
    }

    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        //Image dragImage = getImageFromTransferable(transferable);
        ConnectorState accept;
        TimpguiComponent shape = getTimpguiComponentFromTransferable(transferable);
        if (shape!=null) {
        Image dragImage = shape.getImage();
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
        final TimpguiComponent tgc = getTimpguiComponentFromTransferable(transferable);
        System.out.println(tgc.getCategory());
        System.out.println(tgc.getName());
        DatasetContainerNode dcn = new DatasetContainerNode("test");
        Widget newWidget = scene.addNode(dcn);
        //Widget newWidget2 = scene.attachNodeWidget(dcn);
            //String hm = "Pallete Node"+(nodeCount++);
            //if shape.getCategory()
            //ComponentWidget cw = new ComponentWidget(scene, new DatasetContainer());
            //cw.setPreferredLocation(point);
            //cw.getActions().addAction(scene.getMoveAction());
            //cw.getActions().addAction(scene.getConnectAction());
        
            //Widget newNode = scene.addNode(hm);
            //scene.getSceneAnimator().animatePreferredLocation(newNode,point);
          scene.validate();
          scene.repaint();
    }

    private TimpguiComponent getTimpguiComponentFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(new DataFlavor(TimpguiComponent.class, "TimpguiComponent"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof TimpguiComponent ? (TimpguiComponent) o : null; //TODO: not null
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