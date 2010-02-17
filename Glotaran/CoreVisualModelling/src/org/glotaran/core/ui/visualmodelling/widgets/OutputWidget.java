/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.widgets;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.glotaran.core.models.gta.GtaConnection;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.ui.visualmodelling.components.DatasetContainerComponent;
import org.glotaran.core.ui.visualmodelling.menu.NodeMenu;
import org.glotaran.core.ui.visualmodelling.view.GraphSceneImpl;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author slapten
 */
public class OutputWidget extends Widget{

    private boolean connected = false;
    private List listeners = Collections.synchronizedList(new LinkedList());

    public OutputWidget(GraphSceneImpl scene, JComponent component, String name){
        super(scene);
        setLayout(LayoutFactory.createVerticalFlowLayout());
        setBorder(BorderFactory.createLineBorder());//createRoundedBorder(5, 5, Color.gray, Color.black));//
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getReconnectAction());
        getActions().addAction(scene.getSelectAction());
        getActions().addAction(scene.getResizeAction());
        getActions().addAction(scene.getMoveAction());
        setBorder (BorderFactory.createResizeBorder (4));
        LabelWidget label = new LabelWidget(scene, name);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.getActions().addAction(scene.getConnectAction());
        label.getActions().addAction(scene.getReconnectAction());
        addChild(0, label);
        ComponentWidget componentWidget = new ComponentWidget(scene, component);
        addChild(1, componentWidget);
        getActions().addAction(ActionFactory.createPopupMenuAction(new NodeMenu(scene)));
        //addPropertyChangeListener(component);
    }

    public DatasetContainerComponent getContainerComponent(){        
        return (DatasetContainerComponent)((ComponentWidget)getChildren().get(1)).getComponent();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        if (connected){
            GraphSceneImpl scene = (GraphSceneImpl) getScene();
            for (Object connection : scene.findNodeEdges(scene.findObject(this), false, true)){
                if (connection instanceof GtaConnection){
                    if (((GtaConnection)connection).isActive()){
                        GtaConnection gtaConnection = (GtaConnection)connection;
                        if (gtaConnection.getModelDifferences()==null){
                            gtaConnection.setModelDifferences(new GtaModelDifferences());
                        }
                        fire("connectionChange",
                                ((ModelContainerWidget)scene.findWidget(scene.getNodeForID(gtaConnection.getModelID()))).getModelTgm(),
                                gtaConnection.getModelDifferences());
                    }
                }
            }
        }
        else {
            fire("connectionChange", null, null);
        }
        this.connected = connected;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    public void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

}
