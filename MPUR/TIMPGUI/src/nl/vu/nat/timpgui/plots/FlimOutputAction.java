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
 * Action which shows FlimOutput component.
 */
public class FlimOutputAction extends AbstractAction {

    public FlimOutputAction() {
        super(NbBundle.getMessage(FlimOutputAction.class, "CTL_FlimOutputAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(FlimOutputTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = FlimOutputTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
