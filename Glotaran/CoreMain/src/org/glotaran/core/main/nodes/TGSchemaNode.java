
package org.glotaran.core.main.nodes;

import java.awt.Image;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.loaders.DataFolder;
import org.openide.util.NbBundle;

/**
 */
public class TGSchemaNode extends FilterNode {
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/Folder-scheme-icon.png", true);

    /**
     * Constructor.
     * @param Node, map folder
     */
    public TGSchemaNode(Node node) {
        super(node, new TGSchemaChildrenNode(node));
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        if (this.getParentNode() instanceof TGSchemaNode){
            return this.getName();
        }
        return NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("schema");
    }

    @Override
    public boolean canRename() {
        if (this.getParentNode() instanceof TGSchemaNode){
            return true;
        }
        return false;
    }

     private static class TGSchemaChildrenNode extends FilterNode.Children {

        TGSchemaChildrenNode(Node node) {
            super(node);
        }

        @Override
        protected Node[] createNodes(Node n) {
            if (n.getLookup().lookup(DataFolder.class) != null) {
                if (false){
                    DataFolder folder = n.getLookup().lookup(DataFolder.class);
                    for (int i = 0; i < folder.getChildren().length; i++){
//                        if (folder.getChildren()[i] instanceof
                    }
                } else {
                    return new Node[]{new TGSchemaNode(n)};
                }
            } else {

//                if (n.getLookup().lookup(Tgmdataobject.class)!=null){
//                    return new Node[]{new TgdDataNode(n.getLookup().lookup(TgdDataObject.class))};
//                }
            }
//            return new Node[]{};
            return new Node[]{new FilterNode(n)};
        }
    }
}
