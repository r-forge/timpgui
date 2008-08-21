/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmprojectsupport;

import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode.Children;
import java.awt.Image;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author jsg210
 */
class TgmLogicalView implements LogicalViewProvider {

    private final TgmProject project;

    public TgmLogicalView(TgmProject project) {
        this.project = project;
    }

    public org.openide.nodes.Node createLogicalView() {
        try {
            //Get the scenes directory, creating if deleted
            FileObject models = project.getModelsFolder(true);

            //Get the DataObject that represents it
            DataFolder modelsDataObject =
                    DataFolder.findFolder(models);

            //Get its default node - we'll wrap our node around it to change the
            //display name, icon, etc.
            Node realModelsFolderNode = modelsDataObject.getNodeDelegate();

            //This FilterNode will be our project node
            return new ModelsNode(realModelsFolderNode, project);
        } catch (DataObjectNotFoundException donfe) {
            ErrorManager.getDefault().notify(donfe);
            //Fallback - the directory couldn't be created -
            //read-only filesystem or something evil happened
            return new AbstractNode(Children.LEAF);
        }
    }

    /** This is the node you actually see in the project tab for the project */
    private static final class ModelsNode extends FilterNode {

        final TgmProject project;

        public ModelsNode(Node node, TgmProject project) throws DataObjectNotFoundException {
            super(node, new FilterNode.Children(node),
                    //The projects system wants the project in the Node's lookup.
                    //NewAction and friends want the original Node's lookup.
                    //Make a merge of both
                    new ProxyLookup(new Lookup[]{Lookups.singleton(project),
                        node.getLookup()
                    }));
            this.project = project;
        }
        
        @Override
        public Action[] getActions(boolean popup) {
            DataFolder df = (DataFolder)getLookup().lookup(DataFolder.class);
            return new Action[] {
            //new AddRssAction(df),s
            //new AddFolderAction(df)
                
        };
        }

        @Override
        public Image getIcon(int type) {
            return Utilities.loadImage(
                    "nl/vu/nat/tgmprojectsupport/resources/scenes.gif");
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }
    }
    
    /** This is the node you actually see in the project tab for the project */
    private static final class DatasetsNode extends FilterNode {

        final TgmProject project;

        public DatasetsNode(Node node, TgmProject project) throws DataObjectNotFoundException {
            super(node, new FilterNode.Children(node),
                    //The projects system wants the project in the Node's lookup.
                    //NewAction and friends want the original Node's lookup.
                    //Make a merge of both
                    new ProxyLookup(new Lookup[]{Lookups.singleton(project),
                        node.getLookup()
                    }));
            this.project = project;
        }
        
        @Override
        public Action[] getActions(boolean popup) {
            DataFolder df = (DataFolder)getLookup().lookup(DataFolder.class);
            return new Action[] {
            //new AddRssAction(df),s
            //new AddFolderAction(df)
                
        };
        }

        @Override
        public Image getIcon(int type) {
            return Utilities.loadImage(
                    "nl/vu/nat/tgmprojectsupport/resources/scenes.gif");
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }
    }

    public Node findPath(Node node, Object obj) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return node;
    }

}
    

 
    
    

