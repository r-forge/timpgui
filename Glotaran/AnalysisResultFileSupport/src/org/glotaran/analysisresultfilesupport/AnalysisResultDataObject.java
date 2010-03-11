/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.analysisresultfilesupport;

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
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;
import org.openide.util.ImageUtilities;

public class AnalysisResultDataObject extends MultiDataObject {

    public AnalysisResultDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.LEAF, getLookup()){
            private final Image ICON = ImageUtilities.loadImage("org/glotaran/analysisresultfilesupport/AnalysisResultsObject16.png", true);
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
