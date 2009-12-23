/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.openide.actions.DeleteAction;
import org.openide.actions.PropertiesAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;


/**
 *
 * @author jsg210
 */
public class PropertiesAbstractNode extends AbstractNode {
    private String type;

    public PropertiesAbstractNode(String name, Children children) {
        super(children);
        this.type = name;
    }

    public PropertiesAbstractNode(String name, Children children, Lookup lookup) {
        super(children, lookup);
        this.type = name;
    }

    public String getType() {
        return type;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public PropertySet[] getPropertySets() {
        return getSheet().toArray();
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public Action[] getActions(boolean context) {
         List<Action> actions = new ArrayList<Action>(); //super.getActions(context);
            actions.add(SystemAction.get(DeleteAction.class));
            actions.add(SystemAction.get(PropertiesAction.class));
            return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public String getDisplayName() {
        return type;
    }

    public void updateName(){
        fireNameChange(null, getDisplayName());
    }
}