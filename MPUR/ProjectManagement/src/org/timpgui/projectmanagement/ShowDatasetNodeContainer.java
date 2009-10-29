/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.projectmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.windows.TopComponent;

public final class ShowDatasetNodeContainer implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        TopComponent win = SelectedDatasetsViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
