/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere.testaddmodel;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows testAddModel component.
 */
public class testAddModelAction extends AbstractAction {

    public testAddModelAction() {
        super(NbBundle.getMessage(testAddModelAction.class, "CTL_testAddModelAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(testAddModelTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = testAddModelTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
