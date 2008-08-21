
package nl.vu.nat.tgmprojectsupport;

import java.io.File;
import java.io.Serializable;

public class Dataset implements Serializable {

    private static final long serialVersionUID = 1L;

    private final File path;
    private String name;
    
    public Dataset(File path) {
        this.path = path;
        name = path.getName();
    }
    
    public File getPath() {
        return path;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
