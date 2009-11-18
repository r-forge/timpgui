/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes;

import java.awt.Image;
import java.io.IOException;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

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

    @Override
    public void destroy() throws IOException {
        super.destroy();
        FileObject file = obj.getPrimaryFile();
        file.getParent().getFileObject("cache").getFileObject(obj.getTgd().getCacheFolderName()).delete();
    }
}
