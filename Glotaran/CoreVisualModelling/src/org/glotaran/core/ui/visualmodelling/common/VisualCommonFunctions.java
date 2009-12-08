/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.common;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
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

}
