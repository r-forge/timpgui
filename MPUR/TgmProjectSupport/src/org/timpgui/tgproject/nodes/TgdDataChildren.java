/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.nodes;

import java.beans.PropertyChangeEvent;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;
import org.timpgui.tgproject.datasets.TDLayerDataset;
import org.timpgui.tgproject.datasets.TgdDataObject;
import org.timpgui.tgproject.datasets.TimpDatasetDataObject;

/**
 *
 * @author lsp
 */

public class TgdDataChildren extends Children.Keys {

    private TgdDataObject obj;

    private final List <TimpDatasetDataObject> datasets = new ArrayList<TimpDatasetDataObject>();
    private TimpDatasetDataObject dataset;
    private FileObject cachefolder;
    private FileObject datasetfolder;
    private FileObject[] files;
    private ChangeListener listener;

    public TgdDataChildren(TgdDataObject obj) {
        this.obj=obj;
        DataObject dObj = null;
        final TGProject proj = (TGProject) OpenProjects.getDefault().getMainProject();
        cachefolder = proj.getCacheFolder(true);
        datasetfolder = cachefolder.getFileObject(obj.getTgd().getCacheFolderName());
        files = datasetfolder.getChildren();
        for (FileObject file : files) {
            try {
                dObj = DataObject.find(file);
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (dObj!=null) {
            datasets.add((TimpDatasetDataObject)dObj);
            }
        }


    }

    public void addObj(TimpDatasetDataObject objToAdd){
        if (datasets!=null){
             datasets.add(objToAdd);
        }
        setKeys(datasets);
    }

    @Override
    protected void addNotify() {
        setKeys(datasets);
    }

    @Override
    protected Node[] createNodes(Object key) {
       TimpDatasetDataObject datasetObject = (TimpDatasetDataObject) key;
       TimpDatasetNode tn = new TimpDatasetNode(datasetObject);
       return new Node[] {tn};
    }

    @Override
    public boolean remove(Node[] arg0) {
        TimpDatasetNode node = (TimpDatasetNode)arg0[0];
        datasets.remove(node.getObject());
        setKeys(datasets);
        return true;
    }


}
