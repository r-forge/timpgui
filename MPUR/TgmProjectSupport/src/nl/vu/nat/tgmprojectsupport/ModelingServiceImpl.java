/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.tgmprojectsupport;

//import java.awt.EventQueue;
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.util.Properties;
//import java.util.prefs.Preferences;
import nl.vu.nat.api.tgm.ModelingService;
import nl.vu.nat.tgmodels.tgm.Tgm;
//import nl.vu.nat.tgmprojectsupport.TgmProject.OutHandler;
//import nl.vu.nat.tgmprojectsupport.TgmParser;
//import org.openide.ErrorManager;
//import org.openide.filesystems.FileObject;
//import org.openide.filesystems.FileSystem;
//import org.openide.filesystems.FileUtil;
//import org.openide.filesystems.Repository;
//import org.openide.loaders.DataFolder;
//import org.openide.loaders.DataObject;
//import org.openide.loaders.DataObjectNotFoundException;
//import org.openide.util.NbBundle;
//import org.openide.util.RequestProcessor;
//import org.openide.windows.IOProvider;
//import org.openide.windows.InputOutput;

/**
 *
 * @author joris
 */
public class ModelingServiceImpl extends ModelingService {

    private TgmProject proj;
    
    public ModelingServiceImpl(TgmProject proj) {
        this.proj = proj;
    }

    TgmProject getProject() {
        return proj;
    }
    
//    @Override
//    public FileObject model(FileObject toModel, String propertiesName) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public FileObject model(FileObject toModel, Properties fittingSettings) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public FileObject model(FileObject toModel) {
//        return model (toModel, "");
//    }

//public FileObject model () throws IOException {
//    if (EventQueue.isDispatchThread()) {
//        throw new IllegalStateException ("Tried to run povray from the " +
//                "event thread");
//    }
//
//    //Find the scene file pass to POV-Ray as a java.io.File
//    File scene;
//    try {
//        scene = TgmParser.get();
//    } catch (IOException ioe) {
//        showMsg (ioe.getMessage());
//        return null;
//    }
//
//    //Get the POV-Ray executable
//    File povray = getPovray();
//    if (povray == null) {
//        //The user cancelled the file chooser w/o selecting
//        showMsg(NbBundle.getMessage(Povray.class, "MSG_NoPovrayExe"));
//        return null;
//    }
//
//    //Get the include dir, if it isn't under povray's home dir
//    File includesDir = getStandardIncludeDir(povray);
//    if (includesDir == null) {
//        //The user cancelled the file chooser w/o selecting
//        showMsg (NbBundle.getMessage(Povray.class, "MSG_NoPovrayInc"));
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


//    @Override
//    public Properties getModelingSettings(String name) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    
//    private FileObject getModelingSettingsFolder() {
//        String folderName = "Tgm/ModelingSettings/";
//        FileSystem systemFileSystem =
//                Repository.getDefault().getDefaultFileSystem();
//
//        FileObject result =
//                systemFileSystem.getRoot().getFileObject(folderName);
//
//        if (result == null && !logged) {
//            //Corrupted userdir or something is very very wrong.
//            //Log it and move on.
//            ErrorManager.getDefault().notify (ErrorManager.INFORMATIONAL,
//                    new IllegalStateException("Renderer settings dir missing!"));
//            logged = true;
//        }
//
//        return result;
//    }

//    private static boolean logged = false;
//    private FileObject fileFor (String settingsName) {
//        FileObject settingsFolder = getModelingSettingsFolder();
//        FileObject result;
//        if (settingsFolder != null) { //should never be null
//            result = settingsFolder.getFileObject(settingsName);
//        } else {
//            result = null;
//        }
//        return result;
//    }

//    private void getPreferredModelingSettingsNames(String val) {
//        getPreferences().put(KEY_PREFERRED_SETTINGS, val);
//    }

//    private static final String KEY_PREFERRED_SETTINGS = "preferredSettings";
//    static Preferences getPreferences() {
//        return Preferences.userNodeForPackage(ModelingServiceImpl.class);
//    }

//    @Override
//    public String getPreferredModelingSettingsNames() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    
//    public String[] getAvailableModelSettingsNames() {
//        FileObject settingsFolder = getModelingSettingsFolder();
//        String[] result;
//        if (settingsFolder != null) {
//            //Use a DataFolder here, so our ordering attributes in the layer
//            //file are applied, and our returned String array will be in the
//            //order we want
//            DataFolder fld = DataFolder.findFolder(settingsFolder);
//            DataObject[] kids = fld.getChildren();
//            result = new String[ kids.length ];
//            for (int i = 0; i < kids.length; i++) {
//                result[i] = kids[i].getPrimaryFile().getNameExt();
//            }
//        } else {
//            result = new String[0];
//        }
//        return result;
//    }
    
//       public Properties getRendererSettings(String name) {
//        Properties result = new Properties();
//        FileObject settingsFile = fileFor (name);
//        if (settingsFile != null) {
//            try {
//                result.load(new BufferedInputStream(settingsFile.getInputStream()));
//            } catch (FileNotFoundException ex) {
//                ErrorManager.getDefault().notify (ex);
//            } catch (IOException ex) {
//                ErrorManager.getDefault().notify (ex);
//            }
//        } else {
//            ErrorManager.getDefault().notify (ErrorManager.INFORMATIONAL,
//                    new NullPointerException("Requested non-existent settings " +
//                    "file " + name));
//        }
//        return result;
//    }
       
//    public String getPreferredRendererSettingsName() {
//        String result = getPreferences().get(KEY_PREFERRED_SETTINGS, null);
//        if (result == null) {
//            result = "640x480.properties";
//        }
//        return result;
//    }
    
//     public String getDisplayName(String settingsName) {
//        FileObject file = fileFor (settingsName);
//        String result;
//        if (file != null) {
//            DataObject dob;
//            try {
//                dob = DataObject.find(file);
//                result = dob.getNodeDelegate().getDisplayName();
//            } catch (DataObjectNotFoundException ex) {
//                ErrorManager.getDefault().notify (ex);
//                result = "[error]";
//            }
//        } else {
//            result = "";
//        }
//        return result;
//    }



    @Override
    public String getDisplayName(String settingsName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




    
    
   

}
