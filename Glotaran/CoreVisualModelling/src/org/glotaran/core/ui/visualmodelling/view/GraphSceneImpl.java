/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.glotaran.core.ui.visualmodelling.view;

import org.glotaran.core.ui.visualmodelling.menu.SceneMainMenu;
import org.glotaran.core.ui.visualmodelling.menu.EdgeMenu;
import org.glotaran.core.ui.visualmodelling.menu.NodeMenu;
import org.glotaran.core.ui.visualmodelling.nodes.VisualAbstractNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import org.glotaran.core.ui.visualmodelling.components.DatasetContainerComponent;
import org.glotaran.core.ui.visualmodelling.components.ModelContainer;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;


/**
 * @author Alex
 */
public class GraphSceneImpl extends GraphScene { //TODO: implement <VisualAbstractNode, MyEdge>

    private static final Image IMAGE = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/node.png"); // NOI18N
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
    private LayerWidget interractionLayer = new LayerWidget(this);
    private LayerWidget backgroundLayer = new LayerWidget(this);
    private WidgetAction moveAction = ActionFactory.createMoveAction();
    private Router router = RouterFactory.createFreeRouter();
    private WidgetAction connectAction = ActionFactory.createExtendedConnectAction(interractionLayer, new TGSceneConnectProvider(this));
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new TGSceneReconnectProvider(this));
    private WidgetAction moveControlPointAction = ActionFactory.createFreeMoveControlPointAction();
    private WidgetAction selectAction = ActionFactory.createSelectAction(new ObjectSelectProvider());
    private WidgetAction sceneAcceptAction = ActionFactory.createAcceptAction(new CustomSceneAcceptProvider(this));
    private NodeMenu nodeMenu = new NodeMenu(this);
    private EdgeMenu edgeMenu = new EdgeMenu(this);
    private int nodeCount=0;

    public GraphSceneImpl() {
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);

        addChild(interractionLayer);
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
        getActions().addAction(ActionFactory.createPopupMenuAction(new SceneMainMenu(this)));
        getActions().addAction(sceneAcceptAction);
        setToolTipText("Drag components from the palette onto this design pane");
        initGrids();
    }

    protected Widget attachEdgeWidget(String edge) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setRouter(router);
        connection.setToolTipText("Double-click for Add/Remove Control Point");
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connectionLayer.addChild(connection);
        connection.getActions().addAction(reconnectAction);
        connection.getActions().addAction(createSelectAction());
        connection.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
        connection.getActions().addAction(moveControlPointAction);
        connection.getActions().addAction(ActionFactory.createPopupMenuAction(edgeMenu));
        return connection;
    }

    protected void attachEdgeSourceAnchor(String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget sourceNodeWidget = findWidget(sourceNode);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }

    protected void attachEdgeTargetAnchor(String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget targetNodeWidget = findWidget(targetNode);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        Widget cw = null;
        if (node instanceof TgmDataNode){
            TgmDataNode myNode = (TgmDataNode) node;
            cw = createMoveableComponent(new ModelContainer(), myNode.getDisplayName(), nodeCount++);
            mainLayer.addChild(cw);
        }else {
            VisualAbstractNode myNode = (VisualAbstractNode) node;
            if (myNode.getName().equalsIgnoreCase("Model")) {
                cw = createMoveableComponent(new ModelContainer(), myNode.getName(), nodeCount++);
                mainLayer.addChild(cw);
            }
            if (myNode.getName().equalsIgnoreCase("Dataset Container")) {
                cw = createMoveableComponent(new DatasetContainerComponent(), myNode.getName(), nodeCount++);
                mainLayer.addChild(cw);
            }
        }
        return cw;
    }

    @Override
    protected Widget attachEdgeWidget(Object edge) {
        return attachEdgeWidget((String) edge);
    }

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object oldSourceNode, Object sourceNode) {
//        VisualAbstractNode sourceMyNode = (VisualAbstractNode) sourceNode;
//        VisualAbstractNode oldSourceMyNode = (VisualAbstractNode) oldSourceNode;
        attachEdgeSourceAnchor((String) edge, (String) oldSourceNode, (String) sourceNode);
    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetNode, Object targetNode) {
        attachEdgeTargetAnchor((String) edge, (String) oldTargetNode, (String) targetNode);
    }

    private class ObjectSelectProvider implements SelectProvider {

        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = findObject(widget);

            if (object != null) {
                if (getSelectedObjects().contains(object)) {
                    return;
                }
                userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else {
                userSelectionSuggested(Collections.emptySet(), invertSelection);
            }
        }
    }

    public void initGrids() {
        Image sourceImage = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/paper_grid17.png"); // NOI18N
        int width = sourceImage.getWidth(null);
        int height = sourceImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.dispose();
        TexturePaint PAINT_BACKGROUND = new TexturePaint(image, new Rectangle(0, 0, width, height));
        setBackground(PAINT_BACKGROUND);
        repaint();
        revalidate(false);
        validate();
    }

    public WidgetAction getConnectAction() {
        return connectAction;
    }

    public WidgetAction getMoveAction() {
        return moveAction;
    }

    public WidgetAction getReconnectAction() {
        return reconnectAction;
    }

    public WidgetAction getSelectAction() {
        return selectAction;
    }

    private Widget createMoveableComponent(Component component, String name, int id) {
        Widget widget = new Widget(this);
        widget.setLayout(LayoutFactory.createVerticalFlowLayout());
        widget.setBorder(BorderFactory.createLineBorder());        
//        widget.getActions().addAction(connectAction);
//        widget.getActions().addAction(reconnectAction);
        widget.getActions().addAction(selectAction);
        widget.getActions().addAction(moveAction);

        LabelWidget label = new LabelWidget(this, name + " " + id);

        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.getActions().addAction(connectAction);
        label.getActions().addAction(reconnectAction);

        widget.addChild(label);

        ComponentWidget componentWidget = new ComponentWidget(this, component);
        widget.addChild(componentWidget);
        widget.getActions().addAction(ActionFactory.createPopupMenuAction(nodeMenu));
//        pos += 100;
//        widget.setPreferredLocation (new Point (pos, pos));
        return widget;
    }
}
//public class GraphSceneImpl extends GraphScene<VisualAbstractNode, String> {
//
//    private LayerWidget mainLayer;
//
//    private WidgetAction editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor());
//
//    public GraphSceneImpl() {
//
//        mainLayer = new LayerWidget(this);
//        addChild(mainLayer);
//
//        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {
//
//            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
//                Image dragImage = getImageFromTransferable(transferable);
//                JComponent view = getView();
//                Graphics2D g2 = (Graphics2D) view.getGraphics();
//                Rectangle visRect = view.getVisibleRect();
//                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
//                g2.drawImage(dragImage,
//                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
//                        point.getLocation().getY()),
//                        null);
//                return ConnectorState.ACCEPT;
//            }
//
//            public void accept(Widget widget, Point point, Transferable transferable) {
//                Image image = getImageFromTransferable(transferable);
//                Widget w = GraphSceneImpl.this.addNode(new VisualAbstractNode(image));
//                w.setPreferredLocation(widget.convertLocalToScene(point));
//                repaint();
//            }
//
//        }));
//
//        getActions().addAction(ActionFactory.createZoomAction());
//        getActions().addAction(ActionFactory.createPanAction());
//
//    }
//
//    private Image getImageFromTransferable(Transferable transferable) {
//        Object o = null;
//        try {
//            o = transferable.getTransferData(DataFlavor.imageFlavor);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (UnsupportedFlavorException ex) {
//            ex.printStackTrace();
//        }
//        return o instanceof Image ? (Image) o : Utilities.loadImage("org/netbeans/shapesample/palette/shape1.png");
//    }
//
//    protected Widget attachNodeWidget(VisualAbstractNode node) {
//        ComponentWidget widget = new ComponentWidget(this, new TestCustomComponent());
//        TestCustomComponent testCustomComponent = (TestCustomComponent) widget.getComponent();
//        widget.setPreferredBounds(new Rectangle(200, 200));
//    //    testCustomComponent.jLabel1.setText("test");
//        //setImage(node.getImage());
//        //widget.setLabel(Long.toString(node.hashCode()));
//
//        //double-click, the event is consumed while double-clicking only:
//        //widget.getLabelWidget().getActions().addAction(editorAction);
//
//        //single-click, the event is not consumed:
//        widget.getActions().addAction(createSelectAction());
//
//        //mouse-dragged, the event is consumed while mouse is dragged:
//        widget.getActions().addAction(ActionFactory.createMoveAction());
//
//        //mouse-over, the event is consumed while the mouse is over the widget:
//        widget.getActions().addAction(createObjectHoverAction());
//
//        mainLayer.addChild(widget);
//        return widget;
//    }
//
//    protected Widget attachEdgeWidget(String arg0) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    protected void attachEdgeSourceAnchor(String arg0, VisualAbstractNode arg1, VisualAbstractNode arg2) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    protected void attachEdgeTargetAnchor(String arg0, VisualAbstractNode arg1, VisualAbstractNode arg2) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    private class LabelTextFieldEditor implements TextFieldInplaceEditor {
//
//        public boolean isEnabled(Widget widget) {
//            return true;
//        }
//
//        public String getText(Widget widget) {
//            return ((LabelWidget) widget).getLabel();
//        }
//
//        public void setText(Widget widget, String text) {
//            ((LabelWidget) widget).setLabel(text);
//        }
//
//    }
//
//}
