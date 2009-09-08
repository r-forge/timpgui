/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme.nodes;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;


/**
 *
 * @author jsg210
 */
public class ModelComponentNode extends AbstractNode {

    private String type;

    public ModelComponentNode(String name) {
        super(Children.LEAF);
        this.type = name;
    }

    public String getType() {
        return type;
    }

}
