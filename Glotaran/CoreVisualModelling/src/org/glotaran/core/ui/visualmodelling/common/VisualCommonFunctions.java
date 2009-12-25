/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.common;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.openide.filesystems.FileObject;

/**
 *
 * @author slapten
 */
public class VisualCommonFunctions {
    public static TgmDataNode createNewTgmFile(FileObject modelsFolder){
        return null;
    }

    public static PaletteItem getPaletteItemTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(new DataFlavor(PaletteItem.class, "PaletteItem"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof PaletteItem ? (PaletteItem) o : null; //TODO: not null
    }

        public static ArrayList<Double> strToParams(String paramStr){
        ArrayList<Double> paramList = new ArrayList<Double>();
        String[] paramStrArr = paramStr.split(",");
        for (int i = 0; i < paramStrArr.length; i++){
            paramList.add(Double.parseDouble(paramStrArr[i]));
        }
//        StringTools.getListFromCsv(paramStr);
        return paramList;
    }

}
