/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.view;

import org.glotaran.tgmfilesupport.TgmDataObject;

/**
 *
 * @author slapten
 */
public class VisualModelNode extends MyNode{
    TgmDataObject model;
    VisualModelNode(String name, String category, int id, TgmDataObject obj){
        super(name,category,id);
        this.model = obj;
    }
}
