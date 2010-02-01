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

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import org.glotaran.core.models.gta.GtaProjectScheme;
import org.glotaran.core.ui.visualmodelling.filesupport.GtaDataObject;
import org.glotaran.core.ui.visualmodelling.menu.SceneMainMenu;
import org.glotaran.core.ui.visualmodelling.menu.EdgeMenu;
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
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collections;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.main.nodes.dataobjects.TimpDatasetDataObject;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.ui.visualmodelling.components.DatasetContainerComponent;
import org.glotaran.core.ui.visualmodelling.components.ModelContainer;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetComponentNode;
import org.glotaran.core.ui.visualmodelling.widgets.DatasetContainerWidget;
import org.glotaran.core.ui.visualmodelling.widgets.ModelContainerWidget;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 * @author Alex
 */
public class GraphSceneImpl extends GraphScene implements PropertyChangeListener{ //TODO: implement <VisualAbstractNode, MyEdge>

    private static final Image IMAGE = Utilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/node.png"); // NOI18N
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
    private LayerWidget interractionLayer = new LayerWidget(this);
    private LayerWidget backgroundLayer = new LayerWidget(this);
    private WidgetAction moveAction = ActionFactory.createMoveAction(ActionFactory.createFreeMoveStrategy() , new TGSMoveProvider());
    private Router router = RouterFactory.createFreeRouter();
    private WidgetAction connectAction = ActionFactory.createExtendedConnectAction(interractionLayer, new TGSceneConnectProvider(this));
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new TGSceneReconnectProvider(this));
    private WidgetAction moveControlPointAction = ActionFactory.createFreeMoveControlPointAction();
    private WidgetAction selectAction = ActionFactory.createSelectAction(new ObjectSelectProvider());
    private WidgetAction sceneAcceptAction = ActionFactory.createAcceptAction(new CustomSceneAcceptProvider(this));
//    private NodeMenu nodeMenu = new NodeMenu(this);
    private EdgeMenu edgeMenu = new EdgeMenu(this);
    private Integer nodeCount = 0;
    private GtaDataObject dobj = null;

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

    public GraphSceneImpl(GtaDataObject dobj) {
        this();
        this.dobj = dobj;
        loadScene(dobj.getProgectScheme());

    }

    public GtaDataObject getDobj() {
        return dobj;
    }

    public void setDobj(GtaDataObject dobj) {
        this.dobj = dobj;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public Integer getNewNodeCount() {
        nodeCount++;
        return nodeCount;
    }

    public Object getNodeForID(String id) {
        GtaDatasetContainer gdc = null;
        GtaModelReference gmr = null;
        for (Object node : getNodes()) {
            if (node instanceof GtaDatasetContainer) {
                gdc = (GtaDatasetContainer) node;
                if (gdc.getId() != null) {
                    if (gdc.getId().equals(id)) {
                    return node;
                }
            }
            }
            if (node instanceof GtaModelReference) {
                gmr = (GtaModelReference) node;
                if (gmr.getId() != null) {
                    if (gmr.getId().equals(id)) {
                    return node;
                    }
                }
            }
        }
        //TODO: find more elegant solution than returning null
        return null;
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        TgmDataObject tgmDObj = null;
        Widget cw = null;

        if (node instanceof GtaModelReference) {
            GtaModelReference modelRef = (GtaModelReference) node;
            if (modelRef.getId()==null) {
                modelRef.setId(String.valueOf(getNewNodeCount()));
            }
            try {
                File fl = new File(OpenProjects.getDefault().getMainProject().getProjectDirectory().getPath() + File.separator + modelRef.getPath());
                FileObject test = FileUtil.createData(fl);
                DataObject dObj = DataObject.find(test);
                if (dObj != null) {
                    tgmDObj = (TgmDataObject) dObj;
                }
            } catch (DataObjectExistsException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            cw = new ModelContainerWidget(this, new ModelContainer(tgmDObj), modelRef.getFilename());
            cw.getActions();
            mainLayer.addChild(cw);
        }
        if (node instanceof GtaDatasetContainer) {
            GtaDatasetContainer myNode = (GtaDatasetContainer) node;
            if (myNode.getId()==null) {
                myNode.setId(String.valueOf(getNewNodeCount()));
            }
            cw = new DatasetContainerWidget(this, new DatasetContainerComponent(myNode, this), myNode.getId());
            mainLayer.addChild(cw);
        }
        return cw;
    }

    @Override
    protected Widget attachEdgeWidget(Object edge) {
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

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object oldSourceNode, Object sourceNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget sourceNodeWidget = findWidget(sourceNode);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetNode, Object targetNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget targetNodeWidget = findWidget(targetNode);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }

    public void loadScene(GtaProjectScheme gtaScheme) {
        Widget widget;
        Point location;
        Dimension size;
        TimpDatasetDataObject tdobj;
        TgmDataObject tgmDObj = null;
        if(gtaScheme.getCounter()!=null) {
            nodeCount = gtaScheme.getCounter().isEmpty() ? 0 : Integer.valueOf(gtaScheme.getCounter());
        } else {
            nodeCount=0;
        }

        //get datasetContainer and add nodes
        for (GtaDatasetContainer container : gtaScheme.getDatasetContainer()) {
            widget = addNode(container);
            location = new Point((int) Math.floor(container.getLayout().getXposition()), (int) Math.floor(container.getLayout().getYposition()));
            size = new Dimension((int) Math.floor(container.getLayout().getWidth()), (int) Math.floor(container.getLayout().getHeight()));
            widget.setPreferredLocation(location);
            //widget.setPreferredSize(size);
            validate();

            for (GtaDataset dataset : container.getDatasets()) {
                try {
                    File fl = new File(OpenProjects.getDefault().getMainProject().getProjectDirectory().getPath() + File.separator + dataset.getPath());
                    FileObject test = FileUtil.createData(fl);
                    DataObject dObj = DataObject.find(test);
                    if (dObj != null) {
                        tdobj = (TimpDatasetDataObject) dObj;
                        for (Widget testWidget : widget.getChildren()) {
                            if (testWidget instanceof ComponentWidget) {
                                ComponentWidget cw = ((ComponentWidget) testWidget);
                                if (cw.getComponent() instanceof DatasetContainerComponent) {
                                    GtaDatasetContainer gdc = new GtaDatasetContainer();
                                    DatasetContainerComponent dcc = (DatasetContainerComponent) cw.getComponent();
                                    dcc.getExplorerManager().getRootContext().getChildren().add(new Node[]{
                                                new DatasetComponentNode(
                                                        (TimpDatasetNode) tdobj.getNodeDelegate(),
                                                        new Index.ArrayChildren(),
                                                        Lookups.singleton(dataset),
                                                        dcc)});
                                }
                            }
                        }
                    }
                } catch (DataObjectExistsException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        }

        for (GtaModelReference model : gtaScheme.getModel()) {
            try {
                File fl = new File(OpenProjects.getDefault().getMainProject().getProjectDirectory().getPath() + File.separator + model.getPath());
                FileObject test = FileUtil.createData(fl);
                DataObject dObj = DataObject.find(test);
                if (dObj != null) {
                    tgmDObj = (TgmDataObject) dObj;
                }
                //fo = new TgmDataObject(FileUtil.toFileObject(fl), null);
            } catch (DataObjectExistsException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            widget = addNode(model);
            location = new Point((int) Math.floor(model.getLayout().getXposition()), (int) Math.floor(model.getLayout().getYposition()));
            size = new Dimension((int) Math.floor(model.getLayout().getWidth()), (int) Math.floor(model.getLayout().getHeight()));
            widget.setPreferredLocation(location);
            validate();
        }

        for (GtaConnection connection : gtaScheme.getConnection()) {
            if (connection.isActive()){
                Object sourceNode = getNodeForID(connection.getModelID());
                Object targetNode = getNodeForID(connection.getDatasetContainerID());
                addEdge(connection);
                setEdgeSource(connection, sourceNode);
                setEdgeTarget(connection, targetNode);
                ((DatasetContainerWidget) findWidget(targetNode)).setConnected(true);
                validate();
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("datasetNodeChanged")){
            dobj.setModified(true);
        }
    }

    private class ObjectSelectProvider implements SelectProvider {

        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        @SuppressWarnings("element-type-mismatch")
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

}

