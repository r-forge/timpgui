/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import org.timpgui.timpinterface.TimpInterface;


public final class StartAnalysis implements ActionListener {
    private TimpInterface service;

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        service = Lookup.getDefault().lookup(TimpInterface.class);
        //Result<TgmDataNode> result = WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent").getLookup().lookupResult(TgmDataNode.class);
        Lookup.Template template = new Lookup.Template(TgmDataObject.class);
        Lookup.Result<TgmDataObject> foos =  WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent").getLookup().lookup(template);
        Collection<? extends TgmDataObject> col = foos.allInstances();
        for (Iterator it = col.iterator(); it.hasNext();) {
            TgmDataObject tgdo = (TgmDataObject) it.next();
            Tgm tgm = tgdo.getTgm();
            System.out.println(tgm.getDat().getModelName());
            //service.
        }

        }


//        root.getLookup().lookup(TgmDataObject.class);
//
//        Children children = root.getChildren();
//        for (int i = 0; i < children.getNodesCount(); i++) {
//            Node n = children.getNodeAt(i);
//             DataObject  dob = (DataObject) n.getCookie()
//            if (dob == null) {
//                TgmDataObject tgd = (TgmDataObject)dob;
//            } else {
//                // could also get all files in the data object, if desired:
//                FileObject fo = (FileObject) dob.getPrimaryFile();
//                fo.
//            // do something with fo
//            }

}