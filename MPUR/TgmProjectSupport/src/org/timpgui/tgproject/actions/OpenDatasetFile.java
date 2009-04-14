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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import javax.swing.AbstractAction;

import nl.vu.nat.tgmprojectsupport.TGProject;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.DialogDisplayer;
import org.openide.ErrorManager;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.timpgui.tgproject.datasets.TGDatasetService;
import org.timpgui.tgproject.datasets.Tgd;

//import org.timpgui.tgproject.datasets.GISSourceInfo;
/**
 * action to open dataset.
 *
 * @author Johann Sorel (Puzzle-GIS)
 */
public final class OpenDatasetFile extends AbstractAction {

    private final TGProject project;
//    private Component frame;
    private final Collection<? extends TGDatasetService> services;

    public OpenDatasetFile() {
        this(null);
    }

    public OpenDatasetFile(final TGProject project) {
        super(Utilities.getString("openDatasetFile"));
        this.project = project;
        services = Lookup.getDefault().lookupAll(TGDatasetService.class);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) {
        final Project candidate;

        if (project == null) {
            candidate = OpenProjects.getDefault().getMainProject();
        } else {
            candidate = project;
        }


        if (candidate != null && candidate instanceof TGProject) {
//            final GISProject gis =(GISProject) candidate;
            final JFileSourcePane pane = new JFileSourcePane();

            final ActionListener lst = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    File[] files;
                    if (e.getActionCommand().equalsIgnoreCase("Finish")) {
                        pane.setVisible(false);
                        files = pane.getSelectedFiles();
                        for (File f : files) {
                            for (final TGDatasetService service : services) {
                                if (f.getName().endsWith(service.getExtention())) {
                                    try {
                                        if (service.Validator(f)) {
                                            //TODO create xml file.
                                            Tgd tgd = new Tgd();
                                            tgd.setFileName(f.getName());
                                            tgd.setFiltype(service.getType());
                                            tgd.setPath(f.getParent());
                                            // Get Dataset folder if exists, else recreate it.
                                             FileObject d = project.getDatasetFolder(true);
                                            // TODO: make it OS independent (don't use "/")
                                             String filenamepath = FileUtil.toFile(d).getAbsolutePath();
                                             String filename = FileUtil.toFileObject(f).getName().concat(".xml");
                                             File out = new File(filenamepath,filename);
                                            try {
                                                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgd.getClass().getPackage().getName());
                                                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                                                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                                                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                                                // We marshal the data to a new xml file
                                                marshaller.marshal(tgd, out);


                                            } catch (javax.xml.bind.JAXBException ex) {
                                                // XXXTODO Handle exception
                                                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
                                            } 

                                            //END TODO

                                        }
                                    } catch (FileNotFoundException ex) {
                                        Exceptions.printStackTrace(ex);
                                    } catch (IOException ex) {
                                         ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
                                    } catch (IllegalAccessException ex) {
                                        Exceptions.printStackTrace(ex);
                                    } catch (InstantiationException ex) {
                                        Exceptions.printStackTrace(ex);
                                    }

                                }


                            }
                        }
                    }
                }
            };

            final WizardDescriptor wdesc = WizardUtilities.createSimplewWizard(pane, Utilities.getString("openFile"));
            wdesc.setButtonListener(lst);
            DialogDisplayer.getDefault().notify(wdesc);
        } else {

            NotifyDescriptor msg = new NotifyDescriptor.Message(Utilities.getString("projectIsNotTG"), NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(msg);
        }
//





//        DataObject dataObject = (DataObject) activatedNodes[0].getLookup().lookup(DataObject.class);
//
//        FileObject fileObject = dataObject.getPrimaryFile();
//        String mimeType = fileObject.getMIMEType();
//        try {
//            InputStream stream = fileObject.getInputStream();
//            try {
//                Validation.validate(mimeType, stream);
//            } finally {
//                stream.close();
//            }
//        } catch (IOException ioe) {
//            // in 5.5 and older:
//            ErrorManager.getDefault().notify(ioe);
//        // in 6.0 use:
////         org.openide.util.Exceptions.printStackTrace(ex);
//        }







    }
}