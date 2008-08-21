/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmprojectsupport;

import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import nl.vu.nat.api.tgm.MainFileProvider;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.ErrorManager;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

/**
 *
 * @author jsg210
 */
public class TgmProject implements Project {

    private static final String DATASETS_DIR = "datasets"; //NOI18N
    private static final String MODELS_DIR = "models"; //NOI18N
    private static final String PLOTS_DIR = "plots"; //NOI18N
    private static final String OUTPUT_DIR = "output"; //NOI18N
    //TODO: Investigate the correctness of this statement:
    //      See also: http://platform.netbeans.org/tutorials/nbm-povray-6.html
    //      And: https://dip.felk.cvut.cz/browse/pdfcache/krisij1_2008bach.pdf
    public static final String KEY_MAINFILE = "main.file"; //NOI18N
    private final FileObject projectDir;
    
    LogicalViewProvider logicalView = new TgmLogicalView(this);
    
    private final ProjectState state;
    private ModelingServiceImpl modelingServiceImpl;

    public TgmProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.state = state;
    }

    public FileObject getProjectDirectory() {
        return projectDir;
    }

    FileObject getModelsFolder(boolean create) {
        FileObject result =
                projectDir.getFileObject(TgmProject.MODELS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(TgmProject.MODELS_DIR);
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            }
        }
        return result;
    }

    FileObject getDatasetsFolder(boolean create) {
        FileObject result =
                projectDir.getFileObject(TgmProject.DATASETS_DIR);

        if (result == null && create) {
            try {
                result = projectDir.createFolder(TgmProject.DATASETS_DIR);
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            }
        }
        return result;
    }

    FileObject getPlotsFolder(boolean create) {
        FileObject result =
                projectDir.getFileObject(PLOTS_DIR);
        if (result == null && create) {
            try {
                result = projectDir.createFolder(PLOTS_DIR);
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            }
        }
        return result;
    }

    FileObject getOuputFolder(boolean create) {
        FileObject result =
                projectDir.getFileObject(OUTPUT_DIR);
        if (result == null && create) {
            try {
                result = projectDir.createFolder(OUTPUT_DIR);
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            }
        }
        return result;
    }    // Lookup
    
//    private File getImagesDir() {
//    PovrayProject proj = renderService.getProject();
//    FileObject fob = proj.getImagesFolder(true);
//    File result = FileUtil.toFile(fob);
//    assert result != null && result.exists();
//    return result;
//}
    
    private String stripExtension(File f) {
    String fileName = f.getName();
    int endIndex;
    if ((endIndex = fileName.lastIndexOf('.')) != -1) {
        fileName = fileName.substring(0, endIndex);
    }
    return fileName;
}
    
    
private void showMsg (String msg) {
    StatusDisplayer.getDefault().setStatusText(msg);
}  

//    public TgmProject (ModelingServiceImpl modelingServiceImpl, Tgm toModel) {
//        this.modelingServiceImpl = modelingServiceImpl;
//        //this.toModel = toModel;
//    }
    
//    private File getFileToRender() throws IOException {
//    TgmDataObject model = toModel;
//    if (model == null) {
//        TgmProject proj = modelingServiceImpl.getProject();
//        MainFileProvider provider = (MainFileProvider)
//            proj.getLookup().lookup (MainFileProvider.class);
//        if (provider == null) {
//            throw new IllegalStateException ("Main file provider missing");
//        }
//        model = provider.getMainFile();
//        if (model == null) {
//            ProjectInformation info = (ProjectInformation)
//                    proj.getLookup().lookup(ProjectInformation.class);
//
//            //XXX let the user choose
//            throw new IOException (NbBundle.getMessage(TgmProject.class,
//                    "MSG_NoMainFile", info.getDisplayName()));
//        }
//    }
//    assert model != null;
//    File result = FileUtil.toFile (model);
//    if (result == null) {
//            throw new IOException (NbBundle.getMessage(TgmProject.class,
//                    "MSG_VirtualFile", model.getName()));
//    }
//    assert result.exists();
//    assert result.isFile();
//    return result;
//}

    
    static class OutHandler implements Runnable {
        private Reader out;
        private OutputWriter writer;
        public OutHandler (Reader out, OutputWriter writer) {
            this.out = out;
            this.writer = writer;
        }

        public void run() {
            while (true) {
                try {
                    while (!out.ready()) {
                        try {
                            Thread.currentThread().sleep(200);
                        } catch (InterruptedException e) {
                            close();
                            return;
                        }
                    }
                    if (!readOneBuffer() || Thread.currentThread().isInterrupted()) {
                        close();
                        return;
                    }
                } catch (IOException ioe) {
                    //Stream already closed, this is fine
                    return;
                }
            }
        }

        private boolean readOneBuffer() throws IOException {
            char[] cbuf = new char[255];
            int read;
            while ((read = out.read(cbuf)) != -1) {
                writer.write(cbuf, 0, read);
            }
            return read != -1;
        }

        private void close() {
            try {
                out.close();
            } catch (IOException ioe) {
                ErrorManager.getDefault().notify(ioe);
            } finally {
                writer.close();
            }
        }
    }
    
//    public FileObject render () throws IOException {
//    if (EventQueue.isDispatchThread()) {
//        throw new IllegalStateException ("Tried to run povray from the " +
//                "event thread");
//    }
//
//    //Find the scene file pass to POV-Ray as a java.io.File
//    File scene;
//    try {
//        scene = getFileToRender();
//    } catch (IOException ioe) {
//        showMsg (ioe.getMessage());
//        return null;
//    }
//
//    //Get the POV-Ray executable
//    File tgmParser = TgmParser.getTgmParser();
//    if (tgmParser == null) {
//        //The user cancelled the file chooser w/o selecting
//        showMsg(NbBundle.getMessage(TgmParser.class, "MSG_NoPovrayExe"));
//        return null;
//    }
//
//    //Get the include dir, if it isn't under povray's home dir
//    File includesDir = getStandardIncludeDir(tgmParser);
//    if (includesDir == null) {
//        //The user cancelled the file chooser w/o selecting
//        showMsg (NbBundle.getMessage(TgmParser.class, "MSG_NoPovrayInc"));
//        return null;
//    }
//
//    //Find the image output directory for the project
//    File imagesDir = getImagesDir();
//
//    //Assemble and format the line switches for the POV-Ray process based
//    //on the contents of the Properties object
//    String args = getCmdLineArgs(includesDir);
//    String outFileName = stripExtension (scene) + ".png";
//
//    //Compute the name of the output image file
//    File outFile = new File(imagesDir.getPath() + File.separator +
//            outFileName);
//
//    //Delete the image if it exists, so that any current tab viewing the file is
//    //closed and the file will definitely be re-read when it is re-opened
//    if (outFile.exists() && !outFile.delete()) {
//        showMsg (NbBundle.getMessage(Povray.class,
//                "LBL_CantDelete", outFile.getName()));
//        return null;
//    }
//
//    //Append the input file and output file arguments to the command line
//    String cmdline = povray.getPath() + ' ' + args + " +I" +
//            scene.getPath() + " +O" + outFile.getPath();
//
//    System.err.println(cmdline);
//
//    showMsg (NbBundle.getMessage(Povray.class, "MSG_Rendering",
//            scene.getName()));
//    final Process process = Runtime.getRuntime().exec (cmdline);
//
//    //Get the standard out of the process
//    InputStream out = new BufferedInputStream (process.getInputStream(), 8192);
//    //Get the standard in of the process
//    InputStream err = new BufferedInputStream (process.getErrorStream(), 8192);
//
//    //Create readers for each
//    final Reader outReader = new BufferedReader (new InputStreamReader (out));
//    final Reader errReader = new BufferedReader (new InputStreamReader (err));
//
//    //Get an InputOutput to write to the output window
//    InputOutput io = IOProvider.getDefault().getIO(scene.getName(), false);
//
//    //Force it to open the output window/activate our tab
//    io.select();
//
//    //Print the command line we're calling for debug purposes
//    io.getOut().println(cmdline);
//
//    //Create runnables to poll each output stream
//    OutHandler processSystemOut = new OutHandler (outReader, io.getOut());
//    OutHandler processSystemErr = new OutHandler (errReader, io.getErr());
//
//    //Get two different threads listening on the output & err
//    //using the system-wide thread pool
//    RequestProcessor.getDefault().post(processSystemOut);
//    RequestProcessor.getDefault().post(processSystemErr);
//
//    try {
//        //Hang this thread until the process exits
//        process.waitFor();
//    } catch (InterruptedException ex) {
//        ErrorManager.getDefault().notify(ex);
//    }
//
//    //Close the output window's streams (title will become non-bold)
//    processSystemOut.close();
//    processSystemErr.close();
//
//    if (outFile.exists() && process.exitValue() == 0) {
//        //Try to find the new image file
//        FileObject outFileObject = FileUtil.toFileObject(outFile);
//        showMsg (NbBundle.getMessage(Povray.class, "MSG_Success",
//            outFile.getPath()));
//        return outFileObject;
//    } else {
//        showMsg (NbBundle.getMessage(Povray.class, "MSG_Failure",
//            scene.getPath()));
//        return null;
//    }
//}


 
    
    
    
    private Lookup lkp;

    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                        this, //project spec requires a project be in its own lookup
                        state, //allow outside code to mark the project as needing saving
                        new ActionProviderImpl(), //Provides standard actions like Build and Clean
                        loadProperties(), //The project properties
                        new Info(), //Project information implementation
                        logicalView, //Logical view of project implementation
                        new MainFileProviderImpl(this), //Recognizes the main project file
                        new TgmFileTypes(), //Creates new preferred file type in the "New" submenu
                    });
        }
        return lkp;
    }

    private Properties loadProperties() {
        FileObject fob = projectDir.getFileObject(TgmProjectFactory.PROJECT_DIR +
                "/" + TgmProjectFactory.PROJECT_PROPFILE);
        Properties properties = new NotifyProperties(state);

        if (fob != null) {
            try {
                properties.load(fob.getInputStream());
            } catch (Exception e) {
                ErrorManager.getDefault().notify(e);
            }
        }
        return properties;
    }

    private static class NotifyProperties extends Properties {

        private final ProjectState state;

        NotifyProperties(ProjectState state) {
            this.state = state;
        }

        @Override
        public Object put(Object key, Object val) {
            Object result = super.put(key, val);
            if (((result == null) != (val == null)) || (result != null &&
                    val != null && !val.equals(result))) {
                state.markModified();
            }
            return result;
        }
    }

    private final class ActionProviderImpl implements ActionProvider {

        public String[] getSupportedActions() {
            return new String[0];
        }

        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            //do nothing
        }

        public boolean isActionEnabled(String string, Lookup lookup) throws IllegalArgumentException {
            return false;
        }
    }

    /** Implementation of project system's ProjectInformation class */
    private final class Info implements ProjectInformation {

        public Icon getIcon() {
            return new ImageIcon(Utilities.loadImage(
                    "nl/vu/nat/tgmfilesupport/povicon.gif"));
        }

        public String getName() {
            return getProjectDirectory().getName();
        }

        public String getDisplayName() {
            return getName();
        }

        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        public Project getProject() {
            return TgmProject.this;
        }
    }
}
