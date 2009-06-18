/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.projectmanagement;

import org.openide.nodes.Index;
import org.openide.nodes.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import java.util.logging.Logger;
import org.timpgui.tgproject.nodes.TgdDataChildrenNode;

/**
 *
 * @author jsg210
 */

public final class DatasetNodeContainer extends Index.ArrayChildren {

   private static final Logger LOG = Logger.getLogger(DatasetNodeContainer.class.getName());
   private ArrayList<Node> list = new ArrayList<Node>();

   public DatasetNodeContainer() {

   }

   @Override
   protected List<Node> initCollection() {
      return list;
   }

   public ListIterator<TgdDataChildrenNode> getRemaining(Node current) {
      Vector<TgdDataChildrenNode> v = new Vector<TgdDataChildrenNode>();
      for (Node n : list.subList(indexOf(current), list.size())) {
         v.add(n.getLookup().lookup(TgdDataChildrenNode.class));
      }
      return(v.listIterator());
   }

   public void add(Node n) {
      add(new Node[]{n});
   }


}
