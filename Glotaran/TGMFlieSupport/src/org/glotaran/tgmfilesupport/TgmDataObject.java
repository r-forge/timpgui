/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.tgmfilesupport;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.glotaran.core.models.tgm.Dat;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.tgmeditor.view.TgmToolBarMVElement;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataObject;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataSynchronizer;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

public class TgmDataObject extends XmlMultiViewDataObject {
    // Model synchronizer takes care of two way synchronization between the view and the XML file
    private ModelSynchronizer modelSynchronizer;    // Here we declare the variable, representing the type of design view, which will be discussed in some future installment of this series:
    private static final int TYPE_TOOLBAR = 0;
    Tgm tgm;

    public TgmDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        // Added code from vdblog
        modelSynchronizer = new ModelSynchronizer(this);
//        org.xml.sax.InputSource in = DataObjectAdapters.inputSource(this);
//        CheckXMLCookie checkCookie = new CheckXMLSupport(in);
//        getCookieSet().add(checkCookie);
//        ValidateXMLCookie validateCookie = new ValidateXMLSupport(in);
//        getCookieSet().add(validateCookie);
        if (!(FileUtil.toFile(this.getPrimaryFile()) == null)) {
        try {
            parseDocument();
        } catch (IOException ex) {
            System.out.println("ex=" + ex);
        }
        }
    }

    @Override
    protected Node createNodeDelegate() {
        return new TgmDataNode(this); // removed: getLookup()
    }
   
    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }

    @Override
    protected DesignMultiViewDesc[] getMultiViewDesc() {
        // Returns an instance of DesignMultiViewDesc, which is comparable to Interface MultiViewDescription in the MultiView Windows API.
        return new DesignMultiViewDesc[]{new DesignView(this, TYPE_TOOLBAR)};
    }

    @Override
    protected String getPrefixMark() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Enable to focus specific object in Multiview Editor
     *  The default implementation opens the XML View
     */
    @Override
    public void showElement(Object element) {
        Object target = null;
        if (element instanceof Dat) { //TODO: change
            openView(0);
            target = element;
        }
        if (target != null) {
            final Object key = target;
            org.netbeans.modules.xml.multiview.Utils.runInAwtDispatchThread(new Runnable() {

                @Override
                public void run() {
                    getActiveMultiViewElement0().getSectionView().openPanel(key);
                }
            });
        }
    }

    /** Enable to get active MultiViewElement object
     */
    public ToolBarMultiViewElement getActiveMultiViewElement0() {
        return (ToolBarMultiViewElement) super.getActiveMultiViewElement();
    }

    /**
     *
     * @throws IOException
     */
    private void parseDocument() throws IOException {
        if (tgm == null) {
            tgm = getTgm();
        } else {
            java.io.InputStream is = getEditorSupport().getInputStream();
            Tgm newTgm = null;
            try {
                JAXBContext jaxbCtx = JAXBContext.newInstance(tgm.getClass().getPackage().getName());
                Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                newTgm = (Tgm) unmarshaller.unmarshal(is); //NOI18N
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }
            if (newTgm != null) {
                tgm = newTgm;
            }
        }
    }

    private static class DesignView extends DesignMultiViewDesc {
        
        //Added becuase of:
        //WARNING [org.openide.util.io.NbObjectOutputStream]: Serializable class nl.vu.nat.tgmfilesupport.TgmDataObject$DesignView does not declare serialVersionUID field. Encountered while storing: [org.openide.windows.TopComponent$Replacer, java.lang.Short, java.lang.Number, org.openide.windows.CloneableOpenSupport$Listener, org.openide.windows.CloneableTopComponent$Ref, org.netbeans.modules.xml.multiview.XmlMultiViewEditorSupport$XmlEnv, org.openide.text.DataEditorSupport$Env, org.openide.loaders.OpenSupport$Env, org.openide.loaders.DataObject$Replace, org.netbeans.modules.masterfs.filebasedfs.fileobjects.ReplaceForSerialization, org.netbeans.modules.xml.multiview.XmlMultiViewEditorSupport$MyCloseHandler] See also http://www.netbeans.org/issues/show_bug.cgi?id=19915
        private static final long serialVersionUID = 1L;

        private int type;

        DesignView(TgmDataObject dObj, int type) {
            super(dObj, "Design");
            this.type = type;
        }

        @Override
        public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
            TgmDataObject dObj = (TgmDataObject) getDataObject();
            return new TgmToolBarMVElement(dObj);
        }

        @Override
        public java.awt.Image getIcon() {
            return org.openide.util.Utilities.loadImage("org/glotaran/core/main/resources/Model-icon-16.png"); //NOI18N
        }

        @Override
        public String preferredID() {
            return "Tgm_multiview_design" + String.valueOf(type);
        }
    }

    public Tgm getTgm() { //perhaps IOExeption?
        // If the Object "tgm" doesn't exist yet, read in from file

        if (tgm == null) {
            tgm = new Tgm();
            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgm.getClass().getPackage().getName());
                javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgm = (Tgm) unmarshaller.unmarshal(FileUtil.toFile(this.getPrimaryFile())); //NOI18N //replaced: new java.io.File("File path") //Fix this: java.lang.IllegalArgumentException: file parameter must not be null
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }
        }
        // Else simply return the object
        return tgm;
    }

    public void modelUpdatedFromUI() {
        modelSynchronizer.requestUpdateData();
        setModified(true);
    }

    private class ModelSynchronizer extends XmlMultiViewDataSynchronizer {

        public ModelSynchronizer(XmlMultiViewDataObject dataObject) {
            super(dataObject, 250);
        }

        @Override
        protected boolean mayUpdateData(boolean allowDialog) {
            return true;
        }

        @Override
        protected void updateDataFromModel(Object model, org.openide.filesystems.FileLock lock, boolean modify) {
            if (model == null) {
                return;
            }
            Writer out = new StringWriter();
            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(model.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                // Here we marshal the data back into a StringWriter object to update the editor's DataCache and achieve syncronization.
                marshaller.marshal(model, out);
                out.close();
                getDataCache().setData(lock, out.toString(), modify);

                // If we wanted to write it away to a file instead we'd use:
                //marshaller.marshal(model, FileUtil.toFile(TgmDataObject.this.getPrimaryFile()));
                // But this would generate a read/write every time we changed something.

            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            } catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }

        @Override
        protected Object getModel() {
            return getTgm();
        }

        @Override
        protected void reloadModelFromData() {
            try {
                parseDocument();
            } catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }
    }
}
