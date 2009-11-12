/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.nodecontainers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.windows.TopComponent;

public final class ShowModelsNodeContainer implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        TopComponent win = SelectedModelsViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
