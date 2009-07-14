package org.timpgui.gui.scheme;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows Shape component.
 */
public class TGSceneAction extends AbstractAction {
    
    public TGSceneAction() {
        super(NbBundle.getMessage(TGSceneAction.class, "CTL_TGSceneAction"));
        //        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(TGSceneTopComponent.ICON_PATH, true)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = TGSceneTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
