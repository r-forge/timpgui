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

import org.glotaran.core.ui.visualmodelling.nodes.VisualAbstractNode;
import java.awt.Point;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;


/**
 *
 * @author alex
 */
public class TGSceneConnectProvider implements ConnectProvider {
    
    private Object source = null;
    private Object target = null;
    int edgeCounter = 0;
    
    private GraphScene scene;
    
    public TGSceneConnectProvider(GraphScene scene){
        this.scene=scene;
    }
    
    public boolean isSourceWidget(Widget sourceWidget) {
        Object object = scene.findObject(sourceWidget);
        source = scene.isNode(object) ? object : null;
        return source != null;
    }
    
    public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
        target = scene.findObject(targetWidget);
        if (scene.isNode(target)){
            if (target instanceof GtaDatasetContainer && source instanceof GtaModelReference){
            return ConnectorState.ACCEPT;// : ConnectorState.REJECT_AND_STOP;
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
        if (scene.findEdgesBetween(source, target).isEmpty()){
            GtaConnection gtac = new GtaConnection();
            gtac.setId(String.valueOf(edgeCounter));
            gtac.setName("edge " + edgeCounter ++);
            gtac.setActive(true);

            source = scene.findObject(sourceWidget);
            if (source instanceof GtaModelReference) {
            gtac.setModelID(((GtaModelReference)source).getId());
            }

            target = scene.findObject(targetWidget);
            if (target instanceof GtaDatasetContainer) {
            gtac.setDatasetContainerID(((GtaDatasetContainer)target).getId());
            }
                        
            scene.addEdge(gtac);
            scene.setEdgeSource(gtac, source);
            scene.setEdgeTarget(gtac, target);
            scene.validate();
        }
    }
    
}
