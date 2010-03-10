/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.main.nodes.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import org.glotaran.core.main.interfaces.GlobalSpecResultsProviderInterface;
import org.glotaran.core.main.nodes.dataobjects.TimpResultDataObject;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.windows.CloneableTopComponent;

public final class CompareTimpResultsAction implements ActionListener {

    private final List<DataObject> context;
    private ArrayList<TimpResultDataset> results;

    public CompareTimpResultsAction(List<DataObject> context) {
        this.context = context;
        results = new ArrayList<TimpResultDataset>();
    }

    public void actionPerformed(ActionEvent ev) {
        for (DataObject dataObject : context) {
            // TODO use dataObject
            if (dataObject instanceof TimpResultDataObject) {
                results.add(((TimpResultDataObject) dataObject).getTimpResultDataset());
            }
        }
        if (results.size() > 1) {
            //TODO: open custom TopComponment

        GlobalSpecResultsProviderInterface globalResultsProvider = Lookup.getDefault().lookup(GlobalSpecResultsProviderInterface.class);
        CloneableTopComponent tc = globalResultsProvider.getCloneableTopComponent(results);
        tc.setDisplayName("test");
        tc.open();
        } else {
            //TODO: open ShowResultsDataset
        }
    }


}
