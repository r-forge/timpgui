/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.widgets;

import java.awt.Color;
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
public class DatasetContainerWidget extends Widget{

    private boolean connected = false;

    public DatasetContainerWidget(GraphSceneImpl scene, DatasetContainerComponent component, String name){
        super(scene);
        setLayout(LayoutFactory.createVerticalFlowLayout());
        setBorder(BorderFactory.createLineBorder());//createRoundedBorder(5, 5, Color.gray, Color.black));//
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getReconnectAction());
        getActions().addAction(scene.getSelectAction());
        getActions().addAction(scene.getMoveAction());
        LabelWidget label = new LabelWidget(scene, name);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.getActions().addAction(scene.getConnectAction());
        label.getActions().addAction(scene.getReconnectAction());
        addChild(0, label);
        ComponentWidget componentWidget = new ComponentWidget(scene, component);
        addChild(1, componentWidget);
        getActions().addAction(ActionFactory.createPopupMenuAction(new NodeMenu(scene)));
    }

    public DatasetContainerComponent getContainerComponent(){        
        return (DatasetContainerComponent) ((ComponentWidget)getChildren().get(1)).getComponent();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
