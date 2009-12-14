/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParameter;

/**
 *
 * @author slapten
 */
public class IrfParametersSubNode extends ParametersSubNode{
    private String[] nodeNames = new String[]{"",""};
    public IrfParametersSubNode(NonLinearParameter data){
        super(data);
    }

    @Override
    public String getDisplayName() {
        String name = null;
        for (int i = 0; i < getParentNode().getChildren().getNodesCount(); i++){
            if (getParentNode().getChildren().getNodes()[i].equals(this)){

            }
        }

        return super.getDisplayName();
    }


}
