/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import org.glotaran.tgmfilesupport.TgmDataObject;

/**
 *
 * @author slapten
 */
public class VisualModelNode extends VisualAbstractNode{
    TgmDataObject model;
    VisualModelNode(String name, String category, int id, TgmDataObject obj){
        super(name,category,id);
        this.model = obj;
    }
}
