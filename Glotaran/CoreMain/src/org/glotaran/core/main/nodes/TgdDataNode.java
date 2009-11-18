/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes;

import java.awt.Image;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.openide.loaders.DataNode;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class TgdDataNode extends DataNode {

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/Tgd-dataset-icon-16.png", true);
    private TgdDataObject obj;

    public TgdDataNode(TgdDataObject obj) {
        super(obj,new TgdDataChildren(obj));
        this.obj = obj;

    }

    TgdDataNode(TgdDataObject obj, Lookup lookup) {
        super(obj, new TgdDataChildren(obj), lookup);
        this.obj = obj;
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
