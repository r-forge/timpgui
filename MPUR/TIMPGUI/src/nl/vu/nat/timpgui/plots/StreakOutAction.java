/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.timpgui.plots;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows StreakOut component.
 */
public class StreakOutAction extends AbstractAction {

    public StreakOutAction() {
        super(NbBundle.getMessage(StreakOutAction.class, "CTL_StreakOutAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(StreakOutTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = StreakOutTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
