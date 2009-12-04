/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.view;

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
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;

public class TIMPGUIScene extends VMDGraphScene {

    private static final Image IMAGE_LIST = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/list_16.png"); // NOI18N
    private static final Image IMAGE_CANVAS = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/custom_displayable_16.png"); // NOI18N
    private static final Image IMAGE_COMMAND = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/command_16.png"); // NOI18N
    private static final Image IMAGE_ITEM = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/item_16.png"); // NOI18N
    private static final Image GLYPH_PRE_CODE = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/preCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_POST_CODE = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/postCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_CANCEL = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/cancelGlyph.png"); // NOI18N
    private static int nodeID = 1;
    private static int edgeID = 1;
    private static int pinID = 1;
    private long nodeCounter = 0;
    private long edgeCounter = 0;

    private LayerWidget interactionLayer = new LayerWidget (this);

    private WidgetAction connectAction;
    private WidgetAction acceptAction;
    private WidgetAction reconnectAction;


    /** Creates a new instance of TIMPGUIScene */
    public TIMPGUIScene() {

        addChild (interactionLayer = new LayerWidget (this));

        acceptAction = ActionFactory.createAcceptAction (new TIMPAcceptProvider());
        TIMPConnectProvider timpConnectprovider = new TIMPConnectProvider();
        connectAction = ActionFactory.createConnectAction (interactionLayer,timpConnectprovider);
        TIMPReconnectProvider timpReconnectprovider = new TIMPReconnectProvider();
        reconnectAction = ActionFactory.createReconnectAction (timpReconnectprovider);

        getActions ().addAction (createSelectAction ());
        getActions ().addAction (createAcceptAction ());

    }


    public void addNodeCommonActions (VMDNodeWidget widget) {
        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
//        actions.addAction (createMoveAction ());
        actions.addAction (createAcceptAction ());
        actions.addAction (createConnectAction ());
//        actions.addAction (createPopupMenuAction ());
//        actions.addAction (createEditAction ());
    }

    public void addEdgeCommonActions (ConnectionWidget widget) {
        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (createConnectAction ());
//        actions.addAction (createReconnectAction ());
//        actions.addAction (createPopupMenuAction ());
//        actions.addAction (createEditAction ());
    }

    public void addPinCommonActions (VMDPinWidget widget) {
        WidgetAction.Chain actions = widget.getActions ();
        actions.addAction (createObjectHoverAction ());
        actions.addAction (createSelectAction ());
        actions.addAction (createAcceptAction ());
        actions.addAction (createConnectAction ());
//        actions.addAction (createPopupMenuAction ());
//        actions.addAction (createEditAction ());
    }

    public WidgetAction createConnectAction () {
        return connectAction;
    }

    public WidgetAction createAcceptAction () {
        return acceptAction;
    }
    
    private static String createNode(final VMDGraphScene scene, int x, int y, Image image, String name, String type, List<Image> glyphs) {
        final String nodeID = "node" + TIMPGUIScene.nodeID++;
        VMDNodeWidget widget = (VMDNodeWidget) scene.addNode(nodeID);
        widget.setPreferredLocation(new Point(x, y));
        widget.setNodeProperties(image, name, type, glyphs);
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

     private class TIMPAcceptProvider implements AcceptProvider {

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
                //createNode (scene, 400, 400, IMAGE_LIST, "menu", "List", null);
                final String nodeID = "node" + TIMPGUIScene.nodeID++;
                widget.setPreferredLocation(point);
                VMDNodeWidget newWidget = (VMDNodeWidget) attachNodeWidget(nodeID);
                newWidget.setPreferredLocation (point);
                newWidget.setNodeProperties (IMAGE_LIST, nodeID, nodeID, null);
                addNodeCommonActions(newWidget);
                widget.getScene().addChild(newWidget);
                repaint();
            }
        }

     private class TIMPConnectProvider implements ConnectProvider {

        private String source = null;
        private String target = null;

        public boolean isSourceWidget (Widget sourceWidget) {
            Object object = findObject (sourceWidget);
            source = isNode (object) ? (String) object : null;
            return source != null;
        }

        public ConnectorState isTargetWidget (Widget sourceWidget, Widget targetWidget) {
            Object object = findObject (targetWidget);
            target = isNode (object) ? (String) object : null;
            if (target != null)
                return ! source.equals (target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
            return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
        }

        public boolean hasCustomTargetWidgetResolver (Scene scene) {
            return false;
        }

        public Widget resolveTargetWidget (Scene scene, Point sceneLocation) {
            return null;
        }

        public void createConnection (Widget sourceWidget, Widget targetWidget) {
            String edge = "edge" + edgeCounter ++;
            addEdge (edge);
            setEdgeSource (edge, source);
            setEdgeTarget (edge, target);
        }
        }

          private class TIMPReconnectProvider implements ReconnectProvider {

        public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean hasCustomReplacementWidgetResolver(Scene scene) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
            throw new UnsupportedOperationException("Not supported yet.");
        }


        }

}

