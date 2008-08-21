/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmfilesupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class TgmDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-tgm+xml";
    private static final long serialVersionUID = 1L;

    public TgmDataLoader() {
        super("nl.vu.nat.tgmfilesupport.TgmDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(TgmDataLoader.class, "LBL_Tgm_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new TgmDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
