/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.tgproject.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.timpgui.tgproject.datasets.TGDatasetService;
import org.timpgui.tgproject.datasets.Tgd;

public final class OpenDataset extends CookieAction {
    private final Collection<? extends TGDatasetService> services;

    public OpenDataset() {
        super();
        services = Lookup.getDefault().lookupAll(TGDatasetService.class);
    }

    protected void performAction(Node[] activatedNodes) {
        final DataObject dataObject = activatedNodes[0].getLookup().lookup(DataObject.class);

//        final Project candidate;

        final JFileSourcePane pane = new JFileSourcePane();
        final ActionListener lst = new ActionListener() {
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
                                            FileObject cachefolder = null;
                                            final Project proj = OpenProjects.getDefault().getMainProject();
                                            if (proj!=null){
                                                 cachefolder = proj.getProjectDirectory();
                                            } else {
                                                Confirmation msg = new NotifyDescriptor.Confirmation("Select main project", NotifyDescriptor.OK_CANCEL_OPTION);
                                                DialogDisplayer.getDefault().notify(msg);
                                            }

                                            //TODO create xml file.
                                            Tgd tgd = new Tgd();
                                            tgd.setFilename(f.getName());
                                            tgd.setFiletype(service.getType(f));
                                            tgd.setPath(f.getParent());
                                            // Get Dataset folder if exists, else recreate it.
                                            FileObject d = dataObject.getPrimaryFile();

                                            String filenamepath = FileUtil.toFile(d).getAbsolutePath();
                                            String filename = FileUtil.toFileObject(f).getName().concat(".xml");
                                            if(cachefolder.isValid()) {
                                            cachefolder = cachefolder.getFileObject("cache");
                                            } else {cachefolder = cachefolder.createFolder("cache");}
                                            String foldername = filename.concat("_".concat(String.valueOf(System.currentTimeMillis())));
                                            cachefolder.createFolder(foldername);
                                            tgd.setCacheFolderName(foldername);

                                            File out = new File(filenamepath, filename);
                                            try {
                                                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgd.getClass().getPackage().getName());
                                                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                                                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                                                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                                                // We marshal the data to a new xml file
                                                marshaller.marshal(tgd, out);
                                            } catch (javax.xml.bind.JAXBException ex) {
                                                // TODO Handle exception
                                                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
                                            }
                                            //TODO: pick one of the two following options (first one may not work)
                                            //cachefolder.getFileSystem().refresh(true);
                                            FileUtil.refreshAll();

                                        }
                                    } catch (IOException ex) {
                                        Exceptions.printStackTrace(ex);
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


    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(OpenDataset.class, "openDatasetFile");
    }

    protected Class[] cookieClasses() {
        return new Class[]{DataObject.class};
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}

