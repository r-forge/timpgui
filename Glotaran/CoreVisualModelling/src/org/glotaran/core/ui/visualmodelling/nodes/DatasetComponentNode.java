/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.datatransfer.PasteType;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;

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

}
