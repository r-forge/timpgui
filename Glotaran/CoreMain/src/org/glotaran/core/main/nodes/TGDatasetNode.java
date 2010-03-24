package org.glotaran.core.main.nodes;

import java.awt.Image;
import java.io.IOException;
import javax.swing.Action;

import org.glotaran.core.main.nodes.actions.OpenDataset;
import org.glotaran.core.main.nodes.dataobjects.TgdDataObject;
import org.glotaran.core.main.project.TGProject;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public class TGDatasetNode extends FilterNode {

    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/main/resources/Folder-datasets-icon-16.png", true);

    public TGDatasetNode(Node node) {
        super(node, new TGDatasetChildrenNode(node));

        //nodeactions[0] = new OpenDatasetFile((TGProject)proj);
    }

    @Override
    public void destroy() throws IOException {
        if (this.getChildren().getNodesCount() > 0) {
            this.getChildren().remove(this.getChildren().getNodes());
        }
        super.destroy();
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
        if (this.getParentNode() instanceof TGDatasetNode) {
            return this.getName();
        }
        return NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("datasets");
//                .getMessage(TGDatasetNode.class, "datasets");//Utilities.getString("datasets");
    }

    @Override
    public boolean canRename() {
        if (this.getParentNode() instanceof TGDatasetNode) {
            return true;
        }
        return false;
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] actions = super.getActions(arg0);

        final DataObject obj = getLookup().lookup(DataObject.class);
        final Project proj = FileOwnerQuery.getOwner(obj.getPrimaryFile());

        if (proj != null && proj instanceof TGProject) {
            Action[] temp = actions;
            actions = new Action[actions.length + 1];
            actions[0] = new OpenDataset();


            for (int i = 0; i < temp.length; i++) {
                actions[i + 1] = temp[i];

            }
        }

        return actions;
    }

    private static class TGDatasetChildrenNode extends FilterNode.Children {

        TGDatasetChildrenNode(Node node) {
            super(node);
        }

        @Override
        protected Node[] createNodes(Node n) {
            if (n.getLookup().lookup(DataFolder.class) != null) {
                return new Node[]{new TGDatasetNode(n)};
            } else {
                if (n.getLookup().lookup(TgdDataObject.class) != null) {
                    return new Node[]{n.getLookup().lookup(TgdDataObject.class).getNodeDelegate()};
                }
            }
            // best effort
            return new Node[]{};
        }
    }
}
