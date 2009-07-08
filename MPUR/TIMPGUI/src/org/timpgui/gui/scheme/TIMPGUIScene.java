/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.gui.scheme;

/**
 *
 * @author jsg210
 */
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;

public class TIMPGUIScene extends VMDGraphScene {

    private static final Image IMAGE_LIST = Utilities.loadImage("org/timpgui/gui/scheme/resources/list_16.png"); // NOI18N
    private static final Image IMAGE_CANVAS = Utilities.loadImage("org/timpgui/gui/scheme/resources/custom_displayable_16.png"); // NOI18N
    private static final Image IMAGE_COMMAND = Utilities.loadImage("org/timpgui/gui/scheme/resources/command_16.png"); // NOI18N
    private static final Image IMAGE_ITEM = Utilities.loadImage("org/timpgui/gui/scheme/resources/item_16.png"); // NOI18N
    private static final Image GLYPH_PRE_CODE = Utilities.loadImage("org/timpgui/gui/scheme/resources/preCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_POST_CODE = Utilities.loadImage("org/timpgui/gui/scheme/resources/postCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_CANCEL = Utilities.loadImage("org/timpgui/gui/scheme/resources/cancelGlyph.png"); // NOI18N
    private static int nodeID = 1;
    private static int edgeID = 1;
    private static int pinID = 1;

    /** Creates a new instance of TIMPGUIScene */
    public TIMPGUIScene() {
        String mobile = createNode(this, 100, 100, IMAGE_LIST, "menu", "List", null);
        createPin(this, mobile, "start", IMAGE_ITEM, "Start", "Element");
        String game = createNode(this, 600, 100, IMAGE_CANVAS, "gameCanvas", "MyCanvas", Arrays.asList(GLYPH_PRE_CODE, GLYPH_CANCEL, GLYPH_POST_CODE));
        createPin(this, game, "ok", IMAGE_COMMAND, "okCommand1", "Command");
        createEdge(this, "start", game);
        createEdge(this, "ok", mobile);

        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {

            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                Image dragImage = getImageFromTransferable(transferable);
                JComponent view = getView();
                Graphics2D g2 = (Graphics2D) view.getGraphics();
                Rectangle visRect = view.getVisibleRect();
                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
                g2.drawImage(dragImage,
                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
                        point.getLocation().getY()),
                        null);
                return ConnectorState.ACCEPT;
            }

            public void accept(Widget widget, Point point, Transferable transferable) {

                String test = TIMPGUIScene.createNode ((VMDGraphScene)widget.getScene(), (int) point.getX(), (int) point.getY(), IMAGE_CANVAS, "testCanvas", "MyCanvas", Arrays.asList (GLYPH_PRE_CODE, GLYPH_CANCEL, GLYPH_POST_CODE));
                TIMPGUIScene.this.repaint();
                //Image image = getImageFromTransferable(transferable);
                //Widget w = GraphSceneImpl.this.addNode(new MyNode(image));
                //w.setPreferredLocation(widget.convertLocalToScene(point));
            }


        }));

    }
    
    private static String createNode(final VMDGraphScene scene, int x, int y, Image image, String name, String type, List<Image> glyphs) {
        final String nodeID = "node" + TIMPGUIScene.nodeID++;
        VMDNodeWidget widget = (VMDNodeWidget) scene.addNode(nodeID);
        widget.setPreferredLocation(new Point(x, y));
        widget.setNodeProperties(image, name, type, glyphs);

        widget.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {

            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                return ConnectorState.ACCEPT;
            }

            public void accept(Widget widget, Point point, Transferable transferable) {
                String pinID = "pin" + TIMPGUIScene.pinID++;
                ((VMDPinWidget) scene.addPin(nodeID, pinID)).setProperties("testName" +  TIMPGUIScene.pinID++, null);
                //Image image = getImageFromTransferable(transferable);
                //Widget w = GraphSceneImpl.this.addNode(new MyNode(image));
                //w.setPreferredLocation(widget.convertLocalToScene(point));
            }


        }));



        scene.addPin(nodeID, nodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
        return nodeID;
    }

    private static void createPin(VMDGraphScene scene, String nodeID, String pinID, Image image, String name, String type) {
        ((VMDPinWidget) scene.addPin(nodeID, pinID)).setProperties(name, null);
    }

    private static void createEdge(VMDGraphScene scene, String sourcePinID, String targetNodeID) {
        String edgeID = "edge" + TIMPGUIScene.edgeID++;
        scene.addEdge(edgeID);
        scene.setEdgeSource(edgeID, sourcePinID);
        scene.setEdgeTarget(edgeID, targetNodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
    }

    @Override
    protected void attachEdgeSourceAnchor(String arg0, String arg1, String arg2) {
        super.attachEdgeSourceAnchor(arg0, arg1, arg2);
    }

    @Override
    protected void attachEdgeTargetAnchor(String arg0, String arg1, String arg2) {
        super.attachEdgeTargetAnchor(arg0, arg1, arg2);
    }

    @Override
    protected Widget attachEdgeWidget(String arg0) {
        return super.attachEdgeWidget(arg0);
    }

    @Override
    protected Widget attachNodeWidget(String arg0) {
        return super.attachNodeWidget(arg0);
    }

    @Override
    protected Widget attachPinWidget(String arg0, String arg1) {
        return super.attachPinWidget(arg0, arg1);
    }

    @Override
    public void layoutScene() {
        super.layoutScene();
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

