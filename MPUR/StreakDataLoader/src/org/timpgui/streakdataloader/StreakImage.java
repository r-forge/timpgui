/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.streakdataloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.timpgui.tgproject.datasets.TGDatasetService;

/**
 *
 * @author Sergey
 */
public class StreakImage implements TGDatasetService{



    public String getExtention() {
        return "img";
    }

    public String getType(File file) {
        return "spec";
    }


    public String getFilterString() {
          return ".img Streak Image";
    }

    public boolean Validator(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
