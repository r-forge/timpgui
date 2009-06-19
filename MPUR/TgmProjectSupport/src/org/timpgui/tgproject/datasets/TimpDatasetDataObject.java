/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.datasets;

import java.io.IOException;
import org.openide.cookies.InstanceCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.InstanceDataObject;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;
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

    public DatasetTimp getDatasetTimp() throws DataObjectNotFoundException {
       DatasetTimp dataset = null;
       DataObject ob = DataObject.find(this.getPrimaryFile());
       InstanceCookie ck = ob.getLookup().lookup(InstanceCookie.class);
       if (ck!=null) {
            try {
                dataset = (DatasetTimp) ck.instanceCreate();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
       }
       return dataset;
   }
}
