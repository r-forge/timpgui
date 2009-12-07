package org.glotaran.core.ui.visualmodelling.view;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author alex
 */
public class SceneMainMenu implements PopupMenuProvider, ActionListener {
    
    private static final String ADD_NEW_MODEL = "addNewModelAction"; // NOI18N
    private static final String ADD_NEW_DATASET_CONTAINER = "addNewDatasetContainerAction"; // NOI18N

    private GraphScene scene;

    private JPopupMenu menu;
    private Point point;
    
    private int nodeCount=3;
    
    public SceneMainMenu(GraphScene scene) {
        this.scene=scene;
        menu = new JPopupMenu("Scene Menu");
        JMenuItem item;
        
        item = new JMenuItem("Add New Model");
        item.setActionCommand(ADD_NEW_MODEL);
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Add New Dataset container");
        item.setActionCommand(ADD_NEW_DATASET_CONTAINER);
        item.addActionListener(this);
        menu.add(item);
    }
    
    public JPopupMenu getPopupMenu(Widget widget, Point point){
        this.point=point;
        return menu;
    }
    
    public void actionPerformed(ActionEvent e) {
        Widget newWidget = null;
        if(ADD_NEW_MODEL.equals (e.getActionCommand ())) {
            MyNode newNode = new MyNode("Model", "Containers", nodeCount++); //TODO: move Mynode and rename
            newWidget = scene.addNode(newNode);
        }
        if(ADD_NEW_DATASET_CONTAINER.equals (e.getActionCommand ())) {
            MyNode newNode = new MyNode("Dataset Container", "Containers", nodeCount++); //TODO: move Mynode and rename
            newWidget = scene.addNode(newNode);
        }
        newWidget.setPreferredLocation(point);
        scene.validate();
    }
    
}
