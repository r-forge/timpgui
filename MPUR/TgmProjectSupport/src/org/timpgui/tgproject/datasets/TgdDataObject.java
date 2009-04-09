/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.datasets;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;

public class TgdDataObject extends MultiDataObject {
    private Tgd tgd;

    public TgdDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        //CookieSet cookies = getCookieSet();
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
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
        return new TgdDataNode(this); // removed: getLookup()
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }

    private Tgd getTgd() {
          // If the Object "tgd" doesn't exist yet, read in from file

        if (tgd == null) {
                tgd = new Tgd();
        }
        // Else simply return the object
        return tgd;
    }

/**
     *
     * @throws IOException
     */
    private void parseDocument() throws IOException {
        if (tgd == null) {
            tgd = getTgd();
        } else {
            java.io.InputStream is = getPrimaryEntry().getFile().getInputStream();

            try {
                JAXBContext jaxbCtx = JAXBContext.newInstance(tgd.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgd = (Tgd) unmarshaller.unmarshal(is); //NOI18N
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }

        }
    }
}
