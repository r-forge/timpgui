/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.main.mesages;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

/**
 *
 * @author slapten
 */
public class CoreErrorMessages {
    public static void fileNotFound(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("fileNotFound")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
    
    public static void noMainProjectFound(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
            new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("selMainProj")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void somethingStrange(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
            new Exception("Something strange!!!"));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void jaxbException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("JAXBException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void IOException(String place){
        NotifyDescriptor errorMessage;
        if (place!=null){
            errorMessage =new NotifyDescriptor.Exception(
                    new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("IOException")+
                    " "+place));
        }
        else {
            errorMessage =new NotifyDescriptor.Exception(
                    new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("IOException")));
        }
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void oldClassException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("oldClassException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void projectFolderException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("projectFolderException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void createFolderException(String foldername){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("createFolderException")
                + " "+foldername ));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void headerFileException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("headerNotValid")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void selCorrChNum(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("set_correct_chanNum")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void updLinLogException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("updateLinLogException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void fileLoadException(String filetype){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(filetype+" "+
                NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("fileLoadException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void dragDropException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("sdtFileLoadException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }

    public static void numberFormatException(){
        NotifyDescriptor errorMessage =new NotifyDescriptor.Exception(
                new Exception(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("numberFormatException")));
        DialogDisplayer.getDefault().notify(errorMessage);
    }
}