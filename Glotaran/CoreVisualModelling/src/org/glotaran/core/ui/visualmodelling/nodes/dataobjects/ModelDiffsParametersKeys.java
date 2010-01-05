/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes.dataobjects;

import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.models.tgm.KinPar;
import org.glotaran.core.ui.visualmodelling.nodes.FreeParametersSubNode;
import org.glotaran.core.ui.visualmodelling.nodes.ParametersSubNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author slapten
 */
public class ModelDiffsParametersKeys  extends Children.Keys{
    protected List<ModelDiffsDO> parameters;

    public ModelDiffsParametersKeys(int paramNum){
        parameters = new ArrayList<ModelDiffsDO>();
        for (int i = 0; i<paramNum; i++){
            parameters.add(new ModelDiffsDO());
        }
    }

    public ModelDiffsParametersKeys(List<KinPar> paramList){
        parameters = new ArrayList<ModelDiffsDO>();
        if (paramList!=null){
            for (int i = 0; i < paramList.size(); i++){
               parameters.add(new ModelDiffsDO(paramList.get(i)));
            }
        }
    }

    @Override
    protected void addNotify() {
        setKeys(parameters);
    }

    @Override
    protected Node[] createNodes(Object key) {
       return new Node[] {new FreeParametersSubNode((ModelDiffsDO)key)};
    }

    public void addObj(ModelDiffsDO objToAdd){
        if (parameters!=null){
             parameters.add(objToAdd);
        } else {
            parameters = new ArrayList<ModelDiffsDO>();
            parameters.add(objToAdd);
        }
        setKeys(parameters);
    }

    public void removeParams(int num){
//remove num last components
        if (parameters!=null){
            if (parameters.size()<num){
                parameters.clear();
                parameters.add(new ModelDiffsDO());
            } else {
                for (int i = 0; i<num; i++){
                    parameters.remove(parameters.size()-1);
                }
            }
        }
        setKeys(parameters);
    }

    public void addDefaultObj(int numObj){
        if (parameters!=null){
            for (int i = 0; i<numObj; i++){
                parameters.add(new ModelDiffsDO());
            }
        } else {
            parameters = new ArrayList<ModelDiffsDO>();
            for (int i = 0; i<numObj; i++){
                parameters.add(new ModelDiffsDO());
            }
        }
        setKeys(parameters);
    }

    @Override
    public boolean remove(Node[] arg0) {
        for (int i = 0; i<arg0.length; i++){
            FreeParametersSubNode node = (FreeParametersSubNode)arg0[i];
            parameters.remove(node.getDataObj());
            setKeys(parameters);
        }
        return true;
    }
}
