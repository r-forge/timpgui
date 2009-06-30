/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme.palette;

/**
 *
 * @author jsg210
 */

import java.util.ArrayList;
import org.openide.nodes.Index;
import org.openide.nodes.Node;

public class ShapeChildren  extends Index.ArrayChildren {

    private Category category;

    private String[][] items = new String[][]{
        {"0", "Shapes", "org/timpgui/gui/scheme/resources/image1.png"},
        {"1", "Shapes", "org/timpgui/gui/scheme/resources/image2.png"},
        {"2", "Shapes", "org/timpgui/gui/scheme/resources/image3.png"},
    };

    public ShapeChildren(Category Category) {
        this.category = Category;
    }

    @Override
    protected java.util.List<Node> initCollection() {
        ArrayList childrenNodes = new ArrayList( items.length );
        for( int i=0; i<items.length; i++ ) {
            if( category.getName().equals( items[i][1] ) ) {
                Shape item = new Shape();
                item.setNumber(new Integer(items[i][0]));
                item.setCategory(items[i][1]);
                item.setImage(items[i][2]);
                childrenNodes.add( new ShapeNode( item ) );
            }
        }
        return childrenNodes;
    }

}