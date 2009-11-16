/*
 *  Puzzle-GIS - OpenSource mapping program
 *  http://docs.codehaus.org/display/PUZZLEGIS
 *  Copyright (C) 2007 Puzzle-GIS
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.glotaran.core.main.nodes;

import java.awt.Image;
import javax.swing.Action;
import javax.swing.ImageIcon;

import org.glotaran.core.main.project.TGProject;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;


/**
 * This class provides a {@link org.openide.nodes.Node} for the
 * {@link org.puzzle.core.project.TGProject}.
 * <br>
 * This is the root node used in the the "Projects" window provided
 * by NetBeans.
 *
 * @author Johann Sorel (Puzzle-GIS)
 * @author  Thomas Bonavia (comments)
 *
 * @see     org.openide.nodes.FilterNode
 */
public class TGProjectNode extends FilterNode{

    private final ImageIcon ICON = new ImageIcon(ImageUtilities.loadImage("org/glotaran/core/main/resources/Project-icon.png", true));

    private final TGProject project;

    /**
     * Constructor.<br>
     * This constructor only initialize a new array to contain project node
     * childrens ("src", "doc", "map").
     *
     * @param   project The current {@code TGProject}.
     */
    public  TGProjectNode(Node node, TGProject project){
        super(node, new TGProjectNodeFilter(node,project),
                    //The projects system wants the project in the Node's lookup.
                    //NewAction and friends want the original Node's lookup.
                    //Make a merge of both
                    Lookups.singleton(project));
        this.project = project;
        //this.getChildren().remove(node.getLookup().lookup(new Lookup));
    }

    @Override
    public Image getIcon(int arg0) {
        return ICON.getImage();
    }

    @Override
    public Image getOpenedIcon(int arg0) {
        return ICON.getImage();
    }

    @Override
    public String getDisplayName() {
        ProjectInformation info = project.getLookup().lookup(ProjectInformation.class);
        return info.getDisplayName();
    }

    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
            CommonProjectActions.setAsMainProjectAction(),
            CommonProjectActions.closeProjectAction()
        };
    }

}

class TGProjectNodeFilter extends FilterNode.Children{

    private final TGProject project;

    public TGProjectNodeFilter(Node original,TGProject project){
        super(original);
        this.project = project;
    }

 


    @Override
    protected Node[] createNodes(Node node) {
        final String name = node.getName();
        
        final DataObject dob = node.getLookup().lookup (DataObject.class);
        final FileObject file = dob.getPrimaryFile();
        if(file.equals(project.getDatasetsFolder(true))){
            return new Node[] {new TGDatasetNode(node)};
        }else if(file.equals(project.getModelsFolder(true))){
            return new Node[] {new TGModelsNode(node)};
        }else if(file.equals(project.getResultsFolder(true))){
            return new Node[] {new TGResultsNode(node)};
        }
        return new Node[] {};
    }

}


