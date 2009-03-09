/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.flimdataloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;

public class SdtDataObject extends MultiDataObject {
    private FlimImage flimImage;

    public SdtDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
        cookies.add((Node.Cookie) new SdtOpenSupport(getPrimaryEntry()));

        if (!(FileUtil.toFile(this.getPrimaryFile()) == null)) {
        try {
            parseDocument();
        } catch (IOException ex) {
            System.out.println("ex=" + ex);
        }
        }

    }

    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.LEAF, getLookup());
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }

     public FlimImage getFlimImage() { //perhaps IOExeption?
        // If the Object "tgm" doesn't exist yet, read in from file

        if (flimImage == null) {
            try {
                flimImage = new FlimImage(FileUtil.toFile(getPrimaryFile()));
            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
        // Else simply return the object
        return flimImage;
    }

    /**
     *
     * @throws IOException
     */
    private void parseDocument() throws IOException {
        if (flimImage == null) {
            flimImage = getFlimImage();
        } else {
        }
    }
}
