/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes.dataobjects;

import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.models.tgm.KinPar;
import org.glotaran.core.ui.visualmodelling.nodes.ParametersSubNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author slapten
 */
public class KineticParametersDO  extends Children.Keys{
    private List<KinPar> kinpar;

    public KineticParametersDO(int compNum){
        kinpar = new ArrayList<KinPar>();
        kinpar.add(newDefaultKinpar());
    }

    public KineticParametersDO(List<KinPar> paramList){
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
       return new Node[] {new ParametersSubNode((KinPar)key)};
    }

    public void addObj(KinPar objToAdd){
        if (kinpar!=null){
             kinpar.add(objToAdd);
        } else {
            kinpar = new ArrayList<KinPar>();
            kinpar.add(objToAdd);
        }
        setKeys(kinpar);
    }

    public void removeParams(int num){
//remove num last components
        if (kinpar!=null){
            if (kinpar.size()<num){
                kinpar.clear();
                kinpar.add(newDefaultKinpar());
            } else {
                for (int i = 0; i<num; i++){
                    kinpar.remove(kinpar.size()-1);
                }
            }
        }
        setKeys(kinpar);
    }

    public void addDefaultObj(int numObj){
        if (kinpar!=null){
            for (int i = 0; i<numObj; i++){
                kinpar.add(newDefaultKinpar());
            }
        } else {
            kinpar = new ArrayList<KinPar>();
            for (int i = 0; i<numObj; i++){
                kinpar.add(newDefaultKinpar());
            }
        }
        setKeys(kinpar);
    }



    private KinPar newDefaultKinpar(){
        KinPar newKinpar = new KinPar();
        newKinpar.setStart(1);
        newKinpar.setFixed(Boolean.FALSE);
        newKinpar.setConstrained(Boolean.FALSE);
        newKinpar.setMax(0.0);
        newKinpar.setMin(0.0);
        return newKinpar;
    }

    @Override
    public boolean remove(Node[] arg0) {
        for (int i = 0; i<arg0.length; i++){
            ParametersSubNode node = (ParametersSubNode)arg0[i];
            kinpar.remove(node.getDataObj());
            setKeys(kinpar);
        }
        return true;
    }
}
