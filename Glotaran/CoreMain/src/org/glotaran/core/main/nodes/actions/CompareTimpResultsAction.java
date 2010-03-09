/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import org.glotaran.core.main.nodes.dataobjects.TimpResultDataObject;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.openide.loaders.DataObject;

public final class CompareTimpResultsAction implements ActionListener {

    private final List<DataObject> context;
    private List<TimpResultDataset> results;

    public CompareTimpResultsAction(List<DataObject> context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        for (DataObject dataObject : context) {
            // TODO use dataObject
            if (context instanceof TimpResultDataObject) {
            results.add(((TimpResultDataObject)context).getTimpResultDataset());
            }
        }
        if (results.size()>1) {
            //TODO: open custom TopComponment

    
        } else {
            //TODO: open ShowResultsDataset
        }
    }
}
