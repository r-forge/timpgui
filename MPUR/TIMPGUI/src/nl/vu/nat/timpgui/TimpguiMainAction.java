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
 * Action which shows TimpguiMain component.
 */
public class TimpguiMainAction extends AbstractAction {

    public TimpguiMainAction() {
        super(NbBundle.getMessage(TimpguiMainAction.class, "CTL_TimpguiMainAction"));
        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(TimpguiMainTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = TimpguiMainTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
