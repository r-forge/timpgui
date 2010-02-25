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
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.models.gta.GtaProjectScheme;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.widgets.DatasetContainerWidget;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author alex
 */
public class GlotaranConnectProvider implements ConnectProvider {

    private Object source = null;
    private Object target = null;
    int edgeCounter = 0;
    private GlotaranGraphScene scene;

    public GlotaranConnectProvider(GlotaranGraphScene scene) {
        this.scene = scene;
    }

    public boolean isSourceWidget(Widget sourceWidget) {
        Object object = scene.findObject(sourceWidget);
        source = scene.isNode(object) ? object : null;
        return source != null;
    }

    public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
        target = scene.findObject(targetWidget);
        if (scene.isNode(target)) {
            if (target instanceof GtaDatasetContainer && source instanceof GtaModelReference) {
                return ConnectorState.ACCEPT;
            } else if (target instanceof GtaOutput && source instanceof GtaDatasetContainer) {
                return ConnectorState.ACCEPT;
            } else {
                return ConnectorState.REJECT_AND_STOP;
            }
        }
        return target != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomTargetWidgetResolver(Scene scene) {
        return false;
    }

    public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
        return null;
    }

    public void createConnection(Widget sourceWidget, Widget targetWidget) {
        GtaProjectScheme gtaProjectScheme = scene.getDobj().getProgectScheme();

        Object sourceObject = scene.findObject(sourceWidget);
        Object targetObject = scene.findObject(targetWidget);

        if (scene.findEdgesBetween(source, target).isEmpty()) {

            if (sourceObject instanceof GtaModelReference && targetObject instanceof GtaDatasetContainer) {
                String sourceId = ((GtaModelReference) sourceObject).getId();
                String targetId = ((GtaDatasetContainer) targetObject).getId();
                for (GtaConnection connection : gtaProjectScheme.getConnection()) {
                    if (connection != null) {
                        if (connection.getSourceID() != null && connection.getTargetID() != null) {
                            if (connection.getSourceID().equalsIgnoreCase(sourceId) && connection.getTargetID().equalsIgnoreCase(targetId)) {
                                if (targetWidget.getParentWidget() instanceof DatasetContainerWidget) {
                                    if (!((DatasetContainerWidget) targetWidget.getParentWidget()).isConnected()) {
                                        connection.setActive(true);
                                        scene.addEdge(connection);
                                        scene.setEdgeSource(connection, source);
                                        scene.setEdgeTarget(connection, target);
                                        ((DatasetContainerWidget) targetWidget.getParentWidget()).setConnected(true);
                                        scene.validate();
//                                        return;
                                    } else {
                                        CoreErrorMessages.containerConnected(targetId, sourceId);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (scene.findEdgesBetween(source, target).isEmpty()) {
                GtaConnection gtac = new GtaConnection();
                gtac.setId(String.valueOf(scene.getNewEdgeCount()));
                gtac.setName("Connection " + scene.getEdgeCount());
                gtac.setActive(true);

                source = scene.findObject(sourceWidget);
                if (source instanceof GtaModelReference) {
                    gtac.setSourceID(((GtaModelReference) source).getId());
                    gtac.setSourceType(EnumTypes.ConnectionTypes.GTAMODELREFERENCE.toString());
                } else if (source instanceof GtaDatasetContainer) {
                    gtac.setSourceID(((GtaDatasetContainer) source).getId());
                    gtac.setSourceType(EnumTypes.ConnectionTypes.GTADATASETCONTAINER.toString());
                }

                target = scene.findObject(targetWidget);
                if (target instanceof GtaDatasetContainer) {
                    gtac.setTargetID(((GtaDatasetContainer) target).getId());
                    gtac.setTargetType(EnumTypes.ConnectionTypes.GTADATASETCONTAINER.toString());
                } else if (target instanceof GtaOutput) {
                    gtac.setTargetID(((GtaOutput) target).getId());
                    gtac.setTargetType(EnumTypes.ConnectionTypes.GTAOUTPUT.toString());
                }
                scene.addEdge(gtac);
                scene.setEdgeSource(gtac, source);
                scene.setEdgeTarget(gtac, target);
                if (targetWidget.getParentWidget() instanceof DatasetContainerWidget) {
                    ((DatasetContainerWidget) targetWidget.getParentWidget()).setConnected(true);
                }
                scene.validate();
            }

        } //end of isempty

     scene.validate();

    }
}
