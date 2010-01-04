/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.List;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.ui.visualmodelling.components.DummyChildFactory;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author jsg210
 */
public class DatasetComponentNode extends PropertiesAbstractNode {

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/doc.png", true);

    private TimpDatasetNode tdn;

    public DatasetComponentNode(String name, Children children) {
        super(name, children);
        name = tdn.getDisplayName();
    }

    public DatasetComponentNode(TimpDatasetNode tdn, Children children) {
        super(tdn.getDisplayName(), children);
        this.tdn = tdn;
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
    public Transferable drag() throws IOException {
        return super.drag();
    }

    @Override
    public PasteType getDropType(Transferable t, int action, int index) {
        return super.getDropType(t, action, index);
    }

   
}
