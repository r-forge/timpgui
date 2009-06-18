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
import nl.vu.nat.tgmfilesupport.TgmDataNode;
import nl.vu.nat.tgmfilesupport.TgmDataObject;

/**
 *
 * @author jsg210
 */

public final class ModelsNodeContainer extends Index.ArrayChildren {

   private static final Logger LOG = Logger.getLogger(ModelsNodeContainer.class.getName());
   private ArrayList<Node> list = new ArrayList<Node>();

   public ModelsNodeContainer() {

   }

   @Override
   protected List<Node> initCollection() {
      return list;
   }

   public ListIterator<TgmDataNode> getRemaining(Node current) {
      Vector<TgmDataNode> v = new Vector<TgmDataNode>();
      for (Node n : list.subList(indexOf(current), list.size())) {
         v.add(n.getLookup().lookup(TgmDataNode.class));
      }
      return(v.listIterator());
   }

   public void add(Node n) {
      add(new Node[]{n});
   }


}
