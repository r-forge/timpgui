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
import org.openide.util.Utilities;

public class TimpguiComponentChildren  extends Index.ArrayChildren {

    private Category category;

    private String[][] items = new String[][]{
        {"0", "Preprocessing", "org/timpgui/gui/scheme/resources/image1.png", "Preprocessing Test 1"},
        {"0", "Data", "org/timpgui/gui/scheme/resources/image2.png", "Dataset Container"},
        {"0", "Modelling", "org/timpgui/gui/scheme/resources/image3.png", "Kinetic Model"},
        {"1", "Modelling", "org/timpgui/gui/scheme/resources/image3.png", "Spectral Model"},
        {"2", "Modelling", "org/timpgui/gui/scheme/resources/image3.png", "Mass Model"},
        {"0", "Plotting", "org/timpgui/gui/scheme/resources/image3.png", "SVD Plot"},
    };

    public TimpguiComponentChildren(Category Category) {
        this.category = Category;
    }

    @Override
    protected java.util.List<Node> initCollection() {
        ArrayList childrenNodes = new ArrayList( items.length );
        for( int i=0; i<items.length; i++ ) {
            if( category.getName().equals( items[i][1] ) ) {
                TimpguiComponent item = new TimpguiComponent();
                item.setNumber(new Integer(items[i][0]));
                item.setCategory(items[i][1]);
                item.setImage(Utilities.loadImage(items[i][2]));
                item.setImageLocation(items[i][2]);
                item.setName(items[i][3]);
                childrenNodes.add( new TimpguiComponentNode( item ) );
            }
        }
        return childrenNodes;
    }

}