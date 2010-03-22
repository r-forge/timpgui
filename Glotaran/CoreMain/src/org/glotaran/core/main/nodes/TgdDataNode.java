/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes;

import java.awt.Image;
import java.io.IOException;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.project.TGProject;
import org.netbeans.api.project.FileOwnerQuery;
import org.openide.loaders.DataNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

public class TgdDataNode extends DataNode {

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/Tgd-dataset-icon-16.png", true);
    //private static Image BADGE_BAD = ImageUtilities.loadImage("org/glotaran/core/main/resources/error_badge.png");
    private TgdDataObject obj;

    public TgdDataNode(TgdDataObject obj) {
        super(obj,new TgdDataChildren(obj));
        this.obj = obj;

    }

    public TgdDataNode(TgdDataObject obj, Lookup lookup) {
        super(obj, new TgdDataChildren(obj), lookup);
        this.obj = obj;
    }
    
     @Override
    public Image getIcon(int type) {
        //DataFolder root = DataFolder.findFolder(Repository.getDefault().getDefaultFileSystem().getRoot());
        //Image ICON = root.getNodeDelegate().getIcon(type);
        //return ImageUtilities.mergeImages(ICON, BADGE_BAD, 7, 7);
         return ICON;
    }


    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public void destroy() throws IOException {
        TGProject project = findProject();
        if (this.getChildren().getNodesCount()>0){
            for (int i = 0; i<this.getChildren().getNodesCount(); i++){
                this.getChildren().getNodes()[i].destroy();
            }
        }
        project.getCacheFolder(false).getFileObject(obj.getTgd().getCacheFolderName()).delete();
        super.destroy();
    }

    public TGProject findProject(){
        return (TGProject) FileOwnerQuery.getOwner(obj.getPrimaryFile());
    }
        
}
