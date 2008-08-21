/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows RunRScripts component.
 */
public class RunRScriptsAction extends AbstractAction {

    public RunRScriptsAction() {
        super(NbBundle.getMessage(RunRScriptsAction.class, "CTL_RunRScriptsAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(RunRScriptsTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = RunRScriptsTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
