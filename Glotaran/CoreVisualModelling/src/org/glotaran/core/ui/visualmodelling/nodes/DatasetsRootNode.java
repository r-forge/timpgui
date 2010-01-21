/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author slapten
 */
public class DatasetsRootNode extends AbstractNode{
    public DatasetsRootNode(Children children){
        super(children);
    }

    public DatasetsRootNode(Children children, Lookup lookup){
        super(children,lookup);
    }

    @Override
    public PasteType getDropType(final Transferable t, int action, int index) {
        if (t.isDataFlavorSupported(TimpDatasetNode.DATA_FLAVOR)){
            return new PasteType() {
                @Override
                public Transferable paste() throws IOException {
                    try {
                        getChildren().add(new Node[]{
                            new DatasetComponentNode((TimpDatasetNode)t.getTransferData(TimpDatasetNode.DATA_FLAVOR), new Index.ArrayChildren(), getLookup())});
                    } catch (UnsupportedFlavorException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    return null;
                }
            };

        }
        else {
            return null;
        }

    }
}
