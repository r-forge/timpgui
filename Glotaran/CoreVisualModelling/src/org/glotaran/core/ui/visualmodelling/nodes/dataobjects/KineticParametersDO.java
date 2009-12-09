/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes.dataobjects;

import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.models.tgm.KinPar;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author slapten
 */
public class KineticParametersDO  extends Children.Keys{
    private List<KinPar> kinpar;
    KineticParametersDO(List<KinPar> paramList){
        if (paramList !=null){
            kinpar = paramList;
        } else {
            kinpar = new ArrayList<KinPar>();
        }
    }

    @Override
    protected void addNotify() {
        setKeys(kinpar);
    }

    @Override
    protected Node[] createNodes(Object key) {
  //     TimpDatasetDataObject datasetObject = (TimpDatasetDataObject) key;
       //TimpDatasetNode tn = (TimpDatasetNode) datasetObject.getNodeDelegate();//new TimpDatasetNode(datasetObject);
       return new Node[] {new AbstractNode(Children.LEAF)};
    }

//    @Override
//    public boolean remove(Node[] arg0) {
//        for (int i = 0; i<arg0.length; i++){
//            TimpDatasetNode node = (TimpDatasetNode)arg0[i];
//            datasets.remove(node.getObject());
//            setKeys(datasets);
//        }
//        return true;
//    }
}
