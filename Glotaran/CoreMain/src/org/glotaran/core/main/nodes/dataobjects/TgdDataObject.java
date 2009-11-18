package org.glotaran.core.main.nodes.dataobjects;

import org.glotaran.core.models.tgd.Tgd;
import java.io.IOException;
import javax.xml.bind.Unmarshaller;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.main.nodes.TgdDataNode;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.InstanceDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.text.DataEditorSupport;
import org.openide.util.Lookup;

public class TgdDataObject extends InstanceDataObject {
    private Tgd tgd;

    public TgdDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        CookieSet cookies = getCookieSet();
        cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
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

    public Tgd getTgd() {
          // If the Object "tgd" doesn't exist yet, read in from file

        if (tgd == null) {
            tgd = new Tgd();

            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgd.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgd = (Tgd) unmarshaller.unmarshal(FileUtil.toFile(this.getPrimaryFile()));
            } catch (javax.xml.bind.JAXBException ex) {
                //TODO Handle exception
                CoreErrorMessages.jaxbException();
            }
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
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgd.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgd = (Tgd) unmarshaller.unmarshal(is); //NOI18N
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                CoreErrorMessages.jaxbException();
            }

        }
    }

}
