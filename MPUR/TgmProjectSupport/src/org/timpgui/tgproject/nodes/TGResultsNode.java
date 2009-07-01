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

import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

/**
 * This class provides a {@link org.openide.nodes.Node} for the "doc" folder
 * defined in the {@link org.puzzle.core.project.GISProject}.
 * This folder is intended to contain all files document files, used to make
 * reports or printable maps...
 *
 * @author Johann Sorel (Puzzle-GIS)
 * @author  Thomas Bonavia (comments)
 *
 * @see     org.openide.nodes.FilterNode
 */
public class TGResultsNode extends FilterNode {

    private final Image ICON_ROOT = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/folder-results.png", true);
    private final Image ICON_LEAF = ImageUtilities.loadImage("nl/vu/nat/tgmprojectsupport/resultsname.png", true);
    /**
     * Constructor.
     * @param   node      The {@code Node} describing the folder.
     */
    public TGResultsNode(Node node) {
        super(node, new TGResultsChildrenNode(node));
    }

    @Override
    public Image getIcon(int type) {
        if (this.getParentNode() instanceof TGResultsNode){
            return ICON_LEAF;
        }
        return ICON_ROOT;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        if (this.getParentNode() instanceof TGResultsNode){
            return this.getName();
        }
        return Utilities.getString("results");
    }

    @Override
    public boolean canRename() {
        if (this.getParentNode() instanceof TGResultsNode){
            return true;
        }
        return false;
    }

        private static class TGResultsChildrenNode extends FilterNode.Children {
        TGResultsChildrenNode(Node node) {
            super(node);
        }

        @Override
        protected Node[] createNodes(Node n) {
            if (n.getLookup().lookup(DataFolder.class) != null) {
                return new Node[] {new TGResultsNode(n)};
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