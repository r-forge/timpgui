/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmprojectsupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author jsg210
 */
public class TgmProjectFactory implements ProjectFactory {

    public static final String PROJECT_DIR = "tgproject";
    public static final String PROJECT_PROPFILE = "project.properties";

    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_DIR) != null;
    }

    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new TgmProject(dir, state) : null;
    }

    public void saveProject(final Project project) throws IOException, ClassCastException {
        FileObject projectRoot = project.getProjectDirectory();
        if (projectRoot.getFileObject(PROJECT_DIR) == null) {
            throw new IOException("Project dir " + projectRoot.getPath() + " deleted," +
                    " cannot save project");
        }
        //Force creation of the models/ directory if it was deleted
        ((TgmProject) project).getModelsFolder(true);

        //Find the properties file tgproject/project.properties,
        //creating it if necessary
        String propsPath = PROJECT_DIR + "/" + PROJECT_PROPFILE;
        FileObject propertiesFile = projectRoot.getFileObject(propsPath);
        if (propertiesFile == null) {
            //Recreate the properties file if needed
            propertiesFile = projectRoot.createData(propsPath);
        }

        Properties properties = project.getLookup().lookup(Properties.class);

        File f = FileUtil.toFile(propertiesFile);
        properties.store(new FileOutputStream(f), "NetBeans Tgm Project Properties");
    }
}
