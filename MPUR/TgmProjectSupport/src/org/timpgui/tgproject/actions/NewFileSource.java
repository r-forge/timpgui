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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;

import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;

import org.timpgui.tgproject.datasets.GISSourceInfo;

/**
 * action to create new file sources.
 *
 * @author Johann Sorel (Puzzle-GIS)
 */
public final class NewFileSource extends AbstractAction {

    private final TGProject project;

    public NewFileSource(){
        this(null);
    }

    public NewFileSource(final TGProject project){
        super(Utilities.getString("newFileSource"));
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
     
            };

        
        
    }

}
