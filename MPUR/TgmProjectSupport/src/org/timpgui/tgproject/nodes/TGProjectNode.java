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
package org.timpgui.tgproject.nodes;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ImageIcon;

import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ui.support.CommonProjectActions;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Index.ArrayChildren;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

import nl.vu.nat.tgmprojectsupport.TGProject;


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

    private final ImageIcon ICON = new ImageIcon(ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/boussole.png", true));

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
    protected Node copyNode(Node node) {
        final DataObject dob = node.getLookup().lookup (DataObject.class);
        final FileObject file = dob.getPrimaryFile();

        if(file.equals(project.getDatasetFolder(true))){
            return new TGDatasetNode(node);
        }else if(file.equals(project.getModelFolder(true))){
            return new TGModelNode(node);
        }else if(file.equals(project.getResultFolder(true))){
            return new TGResultNode(node);
        }else{
            return new FilterNode(node);
        }
    }


    protected Node[] createNodes(Node node) {
        final String name = node.getName();
        if(name.equals("gisproject")){
            return new Node[0];
        }else{
            return super.createNodes(node);
        }
    }

}


