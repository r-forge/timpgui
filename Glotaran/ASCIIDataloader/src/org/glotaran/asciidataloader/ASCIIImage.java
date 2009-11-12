
package org.glotaran.asciidataloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.glotaran.core.main.interfaces.TGDatasetInterface;
import org.glotaran.core.main.mesages.CoreErrorMessages;

/**
 *
 * @author lsp
 */
public class ASCIIImage implements TGDatasetInterface{
    
    public String getExtention() {
        return "ascii";
    }

    public String getFilterString() {
        return ".ascii formated TIMP files";
    }

    public String getType(File file) throws FileNotFoundException {
       String loadedString;
        Scanner sc = new Scanner(file);
        try {
            loadedString = sc.nextLine();
            loadedString = sc.nextLine();
            loadedString = sc.nextLine();
            if (loadedString.trim().equalsIgnoreCase("Time explicit") | loadedString.trim().equalsIgnoreCase("Wavelength explicit")) {
                return "spec";
            } else {
               if (loadedString.trim().equalsIgnoreCase("FLIM image")) {
                    return "FLIMascii";
            } else return "error";

            }
        } catch (Exception e) {
            return "error";
        }
    }

    public boolean Validator(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException {
        String loadedString;
        Scanner sc = new Scanner(file);
        loadedString = sc.nextLine();
        loadedString = sc.nextLine();
        loadedString = sc.nextLine();
        if (loadedString.trim().equalsIgnoreCase("Time explicit")|
                loadedString.trim().equalsIgnoreCase("Wavelength explicit")|
                loadedString.trim().equalsIgnoreCase("FLIM image")){
            return true;
        }
        else{
            CoreErrorMessages.headerFileException();
            return false;
         }
    }


}
