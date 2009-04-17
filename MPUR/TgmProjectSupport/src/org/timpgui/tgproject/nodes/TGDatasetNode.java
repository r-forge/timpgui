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
import javax.swing.Action;

import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.loaders.DataObject;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

import nl.vu.nat.tgmprojectsupport.TGProject;
import org.openide.loaders.DataFolder;
import org.timpgui.tgproject.actions.OpenDatasetFile;

/**
 * This class represents the folder "src" defined in the
 * {@link org.puzzle.core.project.TGProject}, which is intended to
 * contain all sources describing the datas of a project.
 *
 * @author Johann Sorel (Puzzle-GIS)
 * @author  Thomas Bonavia (comments)
 *
 * @see     org.openide.nodes.FilterNode
 */
public class TGDatasetNode extends FilterNode {

    private final Image ICON = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/source.png", true);

    /**
     * Constructor.
     * @param  node      The "src" folder.
     */
    public TGDatasetNode(Node node) {
        super(node, new TGDatasetChildrenNode(node));
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
        if (this.getParentNode() instanceof TGDatasetNode){
            return this.getName();
        }
        return Utilities.getString("datasets");
    }

    @Override
    public boolean canRename() {
        return false;
    }

    @Override
    public Action[] getActions(boolean arg0) {
        Action[] actions = super.getActions(arg0);

        final DataObject obj = getLookup().lookup(DataObject.class);
        final Project proj = FileOwnerQuery.getOwner(obj.getPrimaryFile());
        
        if(proj != null && proj instanceof TGProject){
            Action[] temp = actions;
            actions = new Action[actions.length+1];
            actions[0] = new OpenDatasetFile((TGProject)proj);
            for (int i = 0; i < temp.length; i++) {
                actions[i+1] = temp[i];

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
                return new Node[] {new TGDatasetNode(n)};
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