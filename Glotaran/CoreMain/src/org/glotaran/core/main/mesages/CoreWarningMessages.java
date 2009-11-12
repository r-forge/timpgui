/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.main.mesages;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Message;
import org.openide.util.NbBundle;

/**
 *
 * @author slapten
 */
public class CoreWarningMessages {
    public static void propFileWarning(){
        NotifyDescriptor warningMessage =new NotifyDescriptor.Message(
                new Message(NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("propFileWarning"),
                NotifyDescriptor.WARNING_MESSAGE));
        DialogDisplayer.getDefault().notify(warningMessage);
    }


}
