/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.palette;

/**
 *
 * @author jsg210
 */

import java.util.ArrayList;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Utilities;

public class PaletteChildren  extends Index.ArrayChildren {

    private Category category;

    private String[][] items = new String[][]{
        {"0", "Containers", "org/glotarn/core/ui/resources/image1.png", "Dataset Container"},
        {"1", "Containers", "org/glotarn/core/ui/resources/image2.png", "Model Container"},
        {"2", "Containers", "org/glotarn/core/ui/resources/image3.png", "Modeldiffs Container"},
        {"0", "Modelling", "org/glotarn/core/ui/resources/image1.png", "Kinetic Parameter"},
    };

    public PaletteChildren(Category Category) {
        this.category = Category;
    }

    @Override
    protected java.util.List<Node> initCollection() {
        ArrayList childrenNodes = new ArrayList( items.length );
        for( int i=0; i<items.length; i++ ) {
            if( category.getName().equals( items[i][1] ) ) {
                PaletteItem item = new PaletteItem();
                item.setNumber(new Integer(items[i][0]));
                item.setCategory(items[i][1]);
                item.setImage(Utilities.loadImage(items[i][2]));
                item.setImageLocation(items[i][2]);
                item.setName(items[i][3]);
                childrenNodes.add( new PaletteNode( item ) );
            }
        }
        return childrenNodes;
    }

}