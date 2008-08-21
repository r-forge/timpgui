/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmprojectsupport;

import java.util.Properties;
import nl.vu.nat.api.tgm.MainFileProvider;
import org.openide.filesystems.FileObject;

/**
 *
 * @author joris
 */
class MainFileProviderImpl extends MainFileProvider {

    private final TgmProject proj;
    private FileObject mainFile = null;
    private boolean checked = false;

    MainFileProviderImpl(TgmProject proj) {
        this.proj = proj;
    }

    public FileObject getMainFile() {
        //Try to look up the main file in the project properties
        //the first time this is called;  no need to look it up every
        //time, either it's there or it's not and when the user sets it
        //we'll save it when the project is closed
        if (mainFile == null && !checked) {
            checked = true;
            Properties props = proj.getLookup().lookup(
                    Properties.class);

            String path = props.getProperty(TgmProject.KEY_MAINFILE); //NOTE: was PovrayProject.KEY_MAINFILE
            props.getProperty(TgmProjectFactory.PROJECT_PROPFILE);
            if (path != null) {
                FileObject projectDir = proj.getProjectDirectory();
                mainFile = projectDir.getFileObject(path);
            }
        }
        if (mainFile != null && !mainFile.isValid()) {
            return null;
        }
        return mainFile;
    }

    public void setMainFile(FileObject file) {
        String projPath = proj.getProjectDirectory().getPath();
        assert file == null ||
                file.getPath().startsWith(projPath) :
                "Main file not under project";

        boolean change = ((mainFile == null) != (file == null)) ||
                (mainFile != null && !mainFile.equals(file));

        if (change) {
            mainFile = file;
            //Get the project properties (loaded from
            //$PROJECT/tgproject/project.properties)
            Properties props = proj.getLookup().lookup(
                    Properties.class);

            //Store the relative path from the project root as the main file
            String relPath = file.getPath().substring(projPath.length());
            props.put(TgmProject.KEY_MAINFILE, relPath);
        }
    }
}

