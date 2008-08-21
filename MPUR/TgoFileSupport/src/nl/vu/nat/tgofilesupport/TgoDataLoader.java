/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgofilesupport;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class TgoDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-tgo+xml";
    private static final long serialVersionUID = 1L;

    public TgoDataLoader() {
        super("nl.vu.nat.tgofilesupport.TgoDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(TgoDataLoader.class, "LBL_Tgo_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new TgoDataObject(primaryFile, this);
    }

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
