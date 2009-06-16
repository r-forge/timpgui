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
import org.timpgui.tgproject.datasets.TgdDataObject;

/**
 *
 * @author jsg210
 */

public final class OptionsNodeContainer extends Index.ArrayChildren {

   private static final Logger LOG = Logger.getLogger(OptionsNodeContainer.class.getName());
   private ArrayList<Node> list = new ArrayList<Node>();

   public OptionsNodeContainer() {

   }

   @Override
   protected List<Node> initCollection() {
      return list;
   }

   public ListIterator<TgdDataObject> getRemaining(Node current) {
      Vector<TgdDataObject> v = new Vector<TgdDataObject>();
      for (Node n : list.subList(indexOf(current), list.size())) {
         v.add(n.getLookup().lookup(TgdDataObject.class));
      }
      return(v.listIterator());
   }

   public void add(Node n) {
      add(new Node[]{n});
   }


}
