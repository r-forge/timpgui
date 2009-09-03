/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;


public class TimpguiComponentNode extends AbstractNode {

    private TimpguiComponent shape;

    /** Creates a new instance of InstrumentNode */
    public TimpguiComponentNode(TimpguiComponent key) {
        super(Children.LEAF, Lookups.fixed( new Object[] {key} ) );
        this.shape = key;
        setIconBaseWithExtension(key.getImageLocation());
    }

}
