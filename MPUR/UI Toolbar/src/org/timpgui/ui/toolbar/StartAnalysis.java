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
import org.timpgui.projectmanagement.SelectedDatasetsViewTopComponent;

public final class StartAnalysis implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body

       SelectedDatasetsViewTopComponent tc = (SelectedDatasetsViewTopComponent)WindowManager.getDefault().findTopComponent("SelectedDatasetsViewTopComponent");

//       ExplorerManager em = ExplorerManager.find(tc);
//       em.getRootContext();

       Node[] ns = tc.getExplorerManager().getRootContext().getChildren().getNodes();
    }
}
