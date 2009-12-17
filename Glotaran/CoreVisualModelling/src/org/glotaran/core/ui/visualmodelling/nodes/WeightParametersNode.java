/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import org.glotaran.core.ui.visualmodelling.nodes.dataobjects.NonLinearParametersKeys;

/**
 *
 * @author slapten
 */
public class WeightParametersNode extends ModelComponentNode {
    WeightParametersNode(){
        super("weight", new NonLinearParametersKeys(3));
    }

}
