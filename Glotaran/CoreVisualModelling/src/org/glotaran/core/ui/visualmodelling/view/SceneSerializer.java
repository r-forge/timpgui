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

import java.awt.Point;
import java.io.File;
import org.glotaran.core.models.gta.GtaDataset;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaLayout;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaProjectScheme;
import org.glotaran.core.ui.visualmodelling.components.DatasetContainerComponent;
import org.glotaran.core.ui.visualmodelling.nodes.DatasetComponentNode;
import org.glotaran.core.ui.visualmodelling.nodes.VisualAbstractNode;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;

/**
 * @author David Kaspar
 */
public class SceneSerializer {

    private static final String SCENE_ELEMENT = "Scene"; // NOI18N
    private static final String VERSION_ATTR = "version"; // NOI18N
    private static final String SCENE_NODE_COUNTER_ATTR = "nodeIDcounter"; // NOI18N
    private static final String SCENE_EDGE_COUNTER_ATTR = "edgeIDcounter"; // NOI18N
    private static final String NODE_ELEMENT = "Node"; // NOI18N
    private static final String NODE_ID_ATTR = "id"; // NOI18N
    private static final String NODE_X_ATTR = "x"; // NOI18N
    private static final String NODE_Y_ATTR = "y"; // NOI18N
    private static final String EDGE_ELEMENT = "Edge"; // NOI18N
    private static final String EDGE_ID_ATTR = "id"; // NOI18N
    private static final String EDGE_SOURCE_ATTR = "source"; // NOI18N
    private static final String EDGE_TARGET_ATTR = "target"; // NOI18N
    private static final String VERSION_VALUE_1 = "1"; // NOI18N

    // call in AWT to serialize scene
    public static void serialize(GraphSceneImpl scene, File file) {
        GtaProjectScheme gtaProjectScheme = new GtaProjectScheme();
        GtaLayout layout;
//        Node sceneElement = document.getFirstChild ();
//        setAttribute (document, sceneElement, VERSION_ATTR, VERSION_VALUE_1);
//        setAttribute (document, sceneElement, SCENE_NODE_COUNTER_ATTR, Long.toString (scene.nodeIDcounter));
//        setAttribute (document, sceneElement, SCENE_EDGE_COUNTER_ATTR, Long.toString (scene.edgeIDcounter));

        for (Object node : scene.getNodes()) {

            // Iterate over all model containers
            if (node instanceof TgmDataNode) {
                TgmDataNode tgmDataNode = (TgmDataNode) node;
                tgmDataNode.getDataObject().getPrimaryFile();
                Widget widget = scene.findWidget(node);
                Point location = widget.getPreferredLocation();
                GtaModelReference modelRef = new GtaModelReference();
                layout = new GtaLayout();
                layout.setXposition(location.getX());
                layout.setYposition(location.getY());
                layout.setHeight(widget.getBounds().getHeight());
                layout.setWidth(widget.getBounds().getWidth());
                modelRef.setLayout(layout);
                gtaProjectScheme.getModel().add(modelRef);
            }
            if (node instanceof VisualAbstractNode) {
                VisualAbstractNode van = (VisualAbstractNode) node;
                if (van.getCategory().equalsIgnoreCase("Dataset Container")) {
                    Widget widget = scene.findWidget(van);
                    for (Widget testWidget : widget.getChildren()) {
                        if (testWidget instanceof ComponentWidget) {
                            ComponentWidget cw = ((ComponentWidget) testWidget);
                            if (cw.getComponent() instanceof DatasetContainerComponent) {
                                GtaDatasetContainer gdc = new GtaDatasetContainer();
                                DatasetContainerComponent dcc = (DatasetContainerComponent) cw.getComponent();
                                for (Node datasetNode : dcc.getExplorerManager().getRootContext().getChildren().getNodes()) {
                                    DatasetComponentNode dcn = (DatasetComponentNode) datasetNode;
                                    FileObject fo = dcn.getTdn().getObject().getPrimaryFile();
                                    GtaDataset gtaDataset = new GtaDataset();
                                    gtaDataset.setPath(FileUtil.getRelativePath(OpenProjects.getDefault().getMainProject().getProjectDirectory(), fo));
                                    gtaDataset.setFilename(fo.getName());
                                    gdc.getDatasets().add(gtaDataset);
                                }
                                Point location = widget.getPreferredLocation();
                                layout = new GtaLayout();
                                layout.setXposition(location.getX());
                                layout.setYposition(location.getY());
                                layout.setHeight(widget.getBounds().getHeight());
                                layout.setWidth(widget.getBounds().getWidth());
                                gdc.setLayout(layout);
                                //gdc.setId(dcc.getDisplayName());
                                gtaProjectScheme.getDatasetContainer().add(gdc);
                            }
                        }
                    }

                }
            }

        }
        for (Object edge : scene.getEdges()) {
//            Element edgeElement = document.createElement (EDGE_ELEMENT);
//            setAttribute (document, edgeElement, EDGE_ID_ATTR, edge);
//            String sourceNode = scene.getEdgeSource (edge);
//            if (sourceNode != null)
//                setAttribute (document, edgeElement, EDGE_SOURCE_ATTR, sourceNode);
//            String targetNode = scene.getEdgeTarget (edge);
//            if (targetNode != null)
//                setAttribute (document, edgeElement, EDGE_TARGET_ATTR, targetNode);
//            sceneElement.appendChild (edgeElement);
        }
        
        gtaProjectScheme.setName("test");
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(gtaProjectScheme.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(gtaProjectScheme, file);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
    }

    // call in AWT to deserialize scene
    public static void deserialize(GraphSceneImpl scene, File file) {
        GtaProjectScheme gtaProjectScheme = null;
        if (gtaProjectScheme == null) {
            gtaProjectScheme = new GtaProjectScheme();
            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(gtaProjectScheme.getClass().getPackage().getName());
                javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                gtaProjectScheme = (GtaProjectScheme) unmarshaller.unmarshal(file); //NOI18N //replaced: new java.io.File("File path") //Fix this: java.lang.IllegalArgumentException: file parameter must not be null
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }
        }
        // Else simply return the object


//        if (! VERSION_VALUE_1.equals (getAttributeValue (sceneElement, VERSION_ATTR)))
//            return;
////        scene.nodeIDcounter = Long.parseLong (getAttributeValue (sceneElement, SCENE_NODE_COUNTER_ATTR));
////        scene.edgeIDcounter = Long.parseLong (getAttributeValue (sceneElement, SCENE_EDGE_COUNTER_ATTR));
//        for (Node element : getChildNode (sceneElement)) {
//            if (NODE_ELEMENT.equals (element.getNodeName ())) {
//                String node = getAttributeValue (element, NODE_ID_ATTR);
//                int x = Integer.parseInt (getAttributeValue (element, NODE_X_ATTR));
//                int y = Integer.parseInt (getAttributeValue (element, NODE_Y_ATTR));
//                Widget nodeWidget = scene.addNode (node);
//                nodeWidget.setPreferredLocation (new Point (x, y));
//            } else if (EDGE_ELEMENT.equals (element.getNodeName ())) {
//                String edge = getAttributeValue (element, EDGE_ID_ATTR);
//                String sourceNode = getAttributeValue (element, EDGE_SOURCE_ATTR);
//                String targetNode = getAttributeValue (element, EDGE_TARGET_ATTR);
//                scene.addEdge (edge);
//                scene.setEdgeSource (edge, sourceNode);
//                scene.setEdgeTarget (edge, targetNode);
//            }
//        }
        }
}
