/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmprojectsupport;

import org.netbeans.spi.project.ui.PrivilegedTemplates;

/**
 *
 * @author joris
 */
public class TGFileTypes implements PrivilegedTemplates {

    public String[] getPrivilegedTemplates() {
        String[] trialTypes = {"Templates/Other/TgoTemplate.xml",
            "Templates/Other/TgmTemplate.xml"
        };

        return trialTypes;
    }
}