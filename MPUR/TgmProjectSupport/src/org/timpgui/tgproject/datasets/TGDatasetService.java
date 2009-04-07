package org.timpgui.tgproject.datasets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * this interfays should be implemente d by all possible filetypes
 */
public interface TGDatasetService {

    /**
     * get type - extention of the suported files.
     * @return A {String} containing the extention.
     */
    public String getMime();
    
    /**
     * Get the file type.
     * @return A {@code String} containing the type of the file for loaders.
     */
    public String getType();

    public boolean checkFile(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException;
       
}
