
package org.timpgui.tgproject.nodes;

import java.awt.Image;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.loaders.DataFolder;

/**
 */
public class TGModelsNode extends FilterNode {
    private final Image ICON = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/folder-models.png", true);

    /**
     * Constructor.
     * @param Node, map folder
     */
    public TGModelsNode(Node node) {
        super(node, new TGModelsChildrenNode(node));
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
        if (this.getParentNode() instanceof TGModelsNode){
            return this.getName();
        }
        return Utilities.getString("models");
    }

    @Override
    public boolean canRename() {
        if (this.getParentNode() instanceof TGModelsNode){
            return true;
        }
        return false;
    }

//    @Override
//    public Action[] getActions(boolean arg0) {
//        Action[] actions = super.getActions(arg0);
//
//        final DataObject obj = getLookup().lookup(DataObject.class);
//        final Project proj = FileOwnerQuery.getOwner(obj.getPrimaryFile());
//
//        if(proj != null && proj instanceof TGProject){
//            Action[] temp = actions;
//            actions = new Action[actions.length+1];
//            actions[0] = new NewModels( (TGProject)proj);
//            for (int i = 0; i < temp.length; i++) {
//                actions[i+1] = temp[i];
//
//            }
//        }
//
//        return actions;
//    }

      private static class TGModelsChildrenNode extends FilterNode.Children {
        TGModelsChildrenNode(Node node) {
            super(node);
        }

        @Override
        protected Node[] createNodes(Node n) {
            if (n.getLookup().lookup(DataFolder.class) != null) {
                return new Node[] {new TGModelsNode(n)};
            } else {
//                Feed feed = getFeed(n);
//                if (feed != null) {
//                    try {
//                        return new Node[] {new OneFeedNode(n, feed.getSyndFeed())};
//                    } catch (IOException ioe) {
//                        Exceptions.printStackTrace(ioe);
//                    }
//                }
            }
            // best effort
            return new Node[] {new FilterNode(n)};
        }
    }

}