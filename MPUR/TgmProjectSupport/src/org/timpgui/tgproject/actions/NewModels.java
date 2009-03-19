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
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.TemplateWizard;

import nl.vu.nat.tgmprojectsupport.TGProject;

/**
 * Action to create a new map.
 *
 * @author Johann Sorel (Puzzle-GIS)
 */
public final class NewModels extends AbstractAction {

    private final TGProject project;
    
    public NewModels(){
        this(null);
    }
    
    public NewModels(final TGProject project){
        super(Utilities.getString("newMap"));
        this.project = project;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        final Project candidate;
        if(project == null){
            candidate = OpenProjects.getDefault().getMainProject();
        }else{
            candidate = project;
        }
        
        if(candidate != null && candidate instanceof TGProject) {
            final TGProject gis = (TGProject) candidate;
            TemplateWizard tw = new TemplateWizard();
            final Enumeration<DataObject> enu = tw.getTemplatesFolder().children();
            DataObject temp = null;
            while (enu.hasMoreElements()) {
                temp = enu.nextElement();
                if (temp.getName().equals("Other")) {
                    break;
                }
            }

            //System.out.println("templates Other folder = " + temp.getPrimaryFile().getPath());
            FileObject[] templates = temp.getPrimaryFile().getChildren();
            for (FileObject fo : templates) {
                if (fo.getName().equals("GISContextTemplate")) {
                    try {
                        tw.instantiate(DataObject.find(fo), DataFolder.findFolder(gis.getModelFolder(true)));
                    } catch (DataObjectNotFoundException donfe) {
                        Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                                "Unable to find object " + fo.getPath(), donfe);
                    } catch (IOException ioe) {
                        Logger.getLogger(TGProject.class.getName()).log(Level.SEVERE,
                                "Unable to find object " + fo.getPath(), ioe);
                    }
                    break;
                }
            }
        } else {
            NotifyDescriptor d =  new NotifyDescriptor.Message(Utilities.getString("projectIsNotGIS"), NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
        }
        
    }
    
}
