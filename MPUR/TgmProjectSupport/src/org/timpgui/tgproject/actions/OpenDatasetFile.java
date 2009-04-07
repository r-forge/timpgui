/*
 *  Puzzle-GIS - OpenSource mapping program
 *  http://docs.codehaus.org/display/PUZZLEGIS
 *  Copyright (C) 2007 Puzzle-GIS
 *  
 *  GPLv3 + Classpath exception
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.timpgui.tgproject.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.WizardDescriptor;

//import org.timpgui.tgproject.datasets.GISSourceInfo;

/**
 * action to open dataset.
 *
 * @author Johann Sorel (Puzzle-GIS)
 */
public final class OpenDatasetFile extends AbstractAction {

    private final TGProject project;
    private Component frame;

    public OpenDatasetFile(){
        this(null);
    }

    public OpenDatasetFile(final TGProject project){
        super(Utilities.getString("openDatasetFile"));
        this.project = project;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) {
        final Project candidate;
        if(project == null){
            candidate = OpenProjects.getDefault().getMainProject();
        }else{
            candidate = project;
        }


        if(candidate != null && candidate instanceof TGProject) {
//            final GISProject gis =(GISProject) candidate;
            final JFileSourcePane pane = new JFileSourcePane();
//            final ActionListener lst = new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (e.getActionCommand().equalsIgnoreCase("Finish")) {
//                        pane.setVisible(false);
//                        final Map<String,GISSourceInfo> sources = pane.getSources();
//                        final Set<String> names = sources.keySet();
//                        for (final String name : names) {
//                            gis.registerSource(name,sources.get(name));
//                        }
//                    }
//                }
//            };
//
            final WizardDescriptor wdesc = WizardUtilities.createSimplewWizard(pane, Utilities.getString("openFile"));
//            wdesc.setButtonListener(lst);
            DialogDisplayer.getDefault().notify(wdesc);
        }else{
            NotifyDescriptor msg =  new NotifyDescriptor.Message(Utilities.getString("projectIsNotTG"), NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(msg);
        }
//
       

        Confirmation msg = new NotifyDescriptor.Confirmation("Bla bla bla", NotifyDescriptor.OK_CANCEL_OPTION);
        DialogDisplayer.getDefault().notify(msg);

            
        
    }



}
