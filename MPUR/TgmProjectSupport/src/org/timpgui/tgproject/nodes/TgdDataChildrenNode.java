/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.tgproject.nodes;

import java.util.ArrayList;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.timpgui.tgproject.datasets.TgdDataObject;

/**
 *
 * @author lsp
 */
class TgdDataChildrenNode extends Children.Keys {
    private TgdDataObject dataObj;
    public TgdDataChildrenNode(TgdDataObject obj) {
        dataObj = obj;
    }

    @Override
    protected Node[] createNodes(Object arg0) {
        ArrayList <Node> nodesArr = new  ArrayList<Node>();
 //       dataObj.getFolder().files()

        return nodesArr.toArray(new Node[nodesArr.size()]);
    }

    @Override
    protected void addNotify() {
        super.addNotify();
    }

}
