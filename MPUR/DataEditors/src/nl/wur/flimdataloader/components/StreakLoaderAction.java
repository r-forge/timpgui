/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.flimdataloader.components;

import org.timpgui.dataeditors.*;
import org.timpgui.dataeditors.SpecEditorTopComponent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows StreakLoader component.
 */
public class StreakLoaderAction extends AbstractAction {

    public StreakLoaderAction() {
        super(NbBundle.getMessage(StreakLoaderAction.class, "CTL_StreakLoaderAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(StreakLoaderTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = SpecEditorTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
