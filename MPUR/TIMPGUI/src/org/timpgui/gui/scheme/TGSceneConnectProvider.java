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
package org.timpgui.gui.scheme;

import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;
import org.timpgui.gui.scheme.nodes.DatasetComponentNode;
import org.timpgui.gui.scheme.palette.PaletteItem;

/**
 *
 * @author alex
 */
public class TGSceneConnectProvider implements ConnectProvider {
    
    private MyNode source = null;
    private MyNode target = null;
    
    private GraphScene scene;
    
    public TGSceneConnectProvider(GraphScene scene){
        this.scene=scene;
    }
    
    public boolean isSourceWidget(Widget sourceWidget) {
        Object object = scene.findObject(sourceWidget);
        source = scene.isNode(object) ? (MyNode) object : null;
        return source != null;
    }
    
    public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
        Object object = scene.findObject(targetWidget);
        target = scene.isNode(object) ? (MyNode) object : null;
        if (target != null) {
            if (target.getName().equalsIgnoreCase("Model Container"))
            return (! source.equals(target) && source.getName().equalsIgnoreCase("Dataset Container")) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
        }
        return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }
    
    public boolean hasCustomTargetWidgetResolver(Scene scene) {
        return false;
    }
    
    public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
        return null;
    }
    
    int edgeCounter;
    public void createConnection(Widget sourceWidget, Widget targetWidget) {
        String edge = "edge" + edgeCounter ++;
        scene.addEdge(edge);
        scene.setEdgeSource(edge, source);
        scene.setEdgeTarget(edge, target);
        scene.validate();
    }
    
}
