/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.timpgui.tdatasets.DatasetTimp;
import org.timpgui.tgproject.datasets.TgdDataObject;

/**
 *
 * @author lsp
 */
class TgdDataChildren extends Children.Keys {
    private TgdDataObject dataObj;
    private FileObject serFile = null;
    public TgdDataChildren(TgdDataObject obj) {
        dataObj = obj;
        FileObject cachefolder = null;
        final Project proj = OpenProjects.getDefault().getMainProject();
        if (proj!=null){
            cachefolder = proj.getProjectDirectory();
        } else {
            Confirmation msg = new NotifyDescriptor.Confirmation("Select main project", NotifyDescriptor.OK_CANCEL_OPTION);
            DialogDisplayer.getDefault().notify(msg);
        }
        cachefolder = cachefolder.getFileObject(".cache");
        serFile = cachefolder.getFileObject(dataObj.getTgd().getCacheFolderName());
    }



    @Override
    protected Node[] createNodes(Object arg0) {
        FileObject serf = (FileObject) arg0;
        ArrayList<Node> nodesSerFiles = new ArrayList<Node>();
        FileObject[] serFiles = serf.getChildren();
        for (int i = 0; i<serFiles.length; i++){
            nodesSerFiles.add(new TgdDataChildrenNode(serFiles[i]));
        }
        return (nodesSerFiles.toArray(new Node[nodesSerFiles.size()]));
    }

    @Override
    protected void addNotify() {
        setKeys(new FileObject[] {serFile});
    }

}
