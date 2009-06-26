/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.datasets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.InstanceDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Exceptions;
import org.timpgui.structures.DatasetTimp;
import org.timpgui.tgproject.nodes.TimpDatasetNode;

public class TimpDatasetDataObject extends InstanceDataObject {

    private DatasetTimp obj;

    public TimpDatasetDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
    //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
    }

    @Override
    protected Node createNodeDelegate() {
        return new TimpDatasetNode(this);
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }

    public DatasetTimp getDatasetTimp() {
        DatasetTimp dataset = null;
        ObjectInputStream ois = null;
        try {
            File file = FileUtil.toFile(this.getPrimaryFile());
            ois = new ObjectInputStream(new FileInputStream(file));
            try {
                dataset = (DatasetTimp) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return dataset;
    }
}
