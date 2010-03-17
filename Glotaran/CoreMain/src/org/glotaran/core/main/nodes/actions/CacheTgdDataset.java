/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.project.TGProject;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;

public final class CacheTgdDataset implements ActionListener {

    private final List<DataObject> context;

    public CacheTgdDataset(List<DataObject> context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        for (DataObject dataObject : context) {
            // TODO use dataObject
            if (dataObject instanceof TgdDataObject) {
                TgdDataObject tgdDataObject = ((TgdDataObject)dataObject);
                TGProject project = (TGProject) FileOwnerQuery.getOwner(tgdDataObject.getPrimaryFile());

//                File datasetFile = new File(tgdDataObject.getTgd().getPath() + File.separator + );
//                if (datasetFile.exists()) {
//                    FileObject datasetSourceFileObject = FileUtil.toFileObject(datasetFile);
//                    if(datasetFileObject.canRead()){
//                        FileUtil.copyFile(datasetFileObject, datasetFileObject);
//                    }
//                } else {
//                    //TODO: warning file is not accesible on local computer
//                }

            }
        }
    }
}
