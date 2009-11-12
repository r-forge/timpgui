/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.resultdisplayers;

import org.glotaran.core.main.interfaces.ResultsLoaderInterface;
import org.glotaran.core.main.nodes.dataobjects.TimpResultDataObject;
import org.glotaran.core.resultdisplayers.spec.SpecResultsTopComponent;

/**
 *
 * @author Sergey
 */
public class ShowSpecResultsDisplayer implements ResultsLoaderInterface{

    public String getType() {
        return "spec";
    }

    public void openResultDisplayer(TimpResultDataObject dataObj) {
        SpecResultsTopComponent tc = new SpecResultsTopComponent(dataObj);
        tc.open();
        tc.requestActive();
    }
}
