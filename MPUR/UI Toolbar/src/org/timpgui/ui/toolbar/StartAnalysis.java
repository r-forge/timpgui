/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.vu.nat.tgmfilesupport.TgmDataNode;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public final class StartAnalysis implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body

       TopComponent tc = WindowManager.getDefault().findTopComponent("SelectedModelsViewTopComponent");
       ExplorerManager em = ExplorerManager.find(tc);
       Node[] ns = em.getSelectedNodes();
    }
}
