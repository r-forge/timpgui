/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.flimdataloader.components;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows MeasuredIrf component.
 */
public class MeasuredIrfAction extends AbstractAction {

    public MeasuredIrfAction() {
        super(NbBundle.getMessage(MeasuredIrfAction.class, "CTL_MeasuredIrfAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(MeasuredIrfTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = MeasuredIrfTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
