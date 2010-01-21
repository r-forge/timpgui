/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.filesupport;

import java.awt.Image;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

public class GtaDataObject extends MultiDataObject {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/schema-icon.png", true);
    public GtaDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
        cookies.add((Node.Cookie) new GtaOpenSupport(getPrimaryEntry()));

    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.LEAF, getLookup()){
            @Override
            public Image getIcon(int type) {
                return ICON;
            }
        };
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }
}
