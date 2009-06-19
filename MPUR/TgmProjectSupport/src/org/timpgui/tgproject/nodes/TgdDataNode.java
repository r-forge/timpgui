/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.nodes;

import org.timpgui.tgproject.datasets.*;
import org.openide.loaders.DataNode;
import org.openide.util.Lookup;

public class TgdDataNode extends DataNode{

    private static final String IMAGE_ICON_BASE = "nl/vu/nat/tgmfilesupport/povicon.gif";
    //public static final DataFlavor DATA_FLAVOR = new DataFlavor(TgdDataNode.class, "TgdDataNode");
    private TgdDataObject obj;

    public TgdDataNode(TgdDataObject obj) {
        super(obj,new TgdDataChildren(obj));
        this.obj = obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);

    }

    TgdDataNode(TgdDataObject obj, Lookup lookup) {
        super(obj, new TgdDataChildren(obj), lookup);
        this.obj = obj;
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

 
}
