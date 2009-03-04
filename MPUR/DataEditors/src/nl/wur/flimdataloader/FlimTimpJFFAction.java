/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.flimdataloader;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows FlimTimpJFF component.
 */
public class FlimTimpJFFAction extends AbstractAction {

    public FlimTimpJFFAction() {
        super(NbBundle.getMessage(FlimTimpJFFAction.class, "CTL_FlimTimpJFFAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(FlimTimpJFFTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = FlimTimpJFFTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
