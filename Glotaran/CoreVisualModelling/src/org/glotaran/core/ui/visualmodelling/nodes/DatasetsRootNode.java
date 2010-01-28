/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.glotaran.core.main.nodes.TimpDatasetNode;
import org.glotaran.core.models.gta.GtaDataset;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author slapten
 */
public class DatasetsRootNode extends AbstractNode{
    private PropertyChangeListener propListn;
    public DatasetsRootNode(Children children){
        super(children);
    }

    public DatasetsRootNode(Children children, PropertyChangeListener listn){
        super(children);
        addPropertyChangeListener(listn);
        propListn = listn;
    }

    private GtaDataset createDatasetRef(TimpDatasetNode tdsNode){
        FileObject fo = tdsNode.getObject().getPrimaryFile();
        GtaDataset gtaDataset = new GtaDataset();
        gtaDataset.setPath(FileUtil.getRelativePath(OpenProjects.getDefault().getMainProject().getProjectDirectory(), fo));
        gtaDataset.setFilename(fo.getName());
        return gtaDataset;
    }

    @Override
    public PasteType getDropType(final Transferable t, int action, int index) {
        if (t.isDataFlavorSupported(TimpDatasetNode.DATA_FLAVOR)){
            return new PasteType() {
                @Override
                public Transferable paste() throws IOException {
                    try {
                        TimpDatasetNode timpDataNode = (TimpDatasetNode)t.getTransferData(TimpDatasetNode.DATA_FLAVOR);
                        GtaDataset addedDataset = createDatasetRef(timpDataNode);

                        getChildren().add(new Node[]{
                            new DatasetComponentNode(
                                    timpDataNode,
                                    new Index.ArrayChildren(),
                                    Lookups.singleton(addedDataset),
                                    propListn)});
                        firePropertyChange("datasetAdded", null, addedDataset);
                    } catch (UnsupportedFlavorException exption) {
                        Exceptions.printStackTrace(exption);
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
