/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import org.glotaran.core.models.tgm.KMatrixPanelModel;
import org.glotaran.core.ui.visualmodelling.components.ModelContainer;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author slapten
 */
public class KmatrixNode extends PropertiesAbstractNode{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Kmatr_16.png", true);

    public KmatrixNode(PropertyChangeListener listn){
        super("Kmatrix", Children.LEAF);
        this.addPropertyChangeListener(listn);
    }

    public KmatrixNode(KMatrixPanelModel kMatrixPanel, ModelContainer listn) {
        super("Kmatrix", Children.LEAF);
        this.addPropertyChangeListener(listn);
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }
}
