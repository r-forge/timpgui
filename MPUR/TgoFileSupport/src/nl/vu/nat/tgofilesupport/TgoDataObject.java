/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgofilesupport;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import nl.vu.nat.tgmodels.tgo.OptPanelElements;
import nl.vu.nat.tgmodels.tgo.Tgo;
import nl.vu.nat.tgoeditor.TgoToolBarMVElement;

import org.netbeans.api.xml.cookies.CheckXMLCookie;
import org.netbeans.api.xml.cookies.ValidateXMLCookie;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataObject;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataSynchronizer;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectExistsException;
import org.openide.nodes.Node;

public class TgoDataObject extends XmlMultiViewDataObject {
    // Model synchronizer takes care of two way synchronization between the view and the XML file
    private ModelSynchronizer modelSynchronizer;    // Here we declare the variable, representing the type of design view, which will be discussed in some future installment of this series:
    private static final int TYPE_TOOLBAR = 0;
    Tgo tgo;

    public TgoDataObject(FileObject pf, TgoDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        //CookieSet cookies = getCookieSet();
        //cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));

        // Added code from vdblog
        modelSynchronizer = new ModelSynchronizer(this);
        org.xml.sax.InputSource in = DataObjectAdapters.inputSource(this);
        CheckXMLCookie checkCookie = new CheckXMLSupport(in);
        getCookieSet().add(checkCookie);
        ValidateXMLCookie validateCookie = new ValidateXMLSupport(in);
        getCookieSet().add(validateCookie);
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
        return new TgoDataNode(this); // removed: getLookup()
    }

//    @Override
//    public Lookup getLookup() {
//        return getCookieSet().getLookup();
//    }
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
        if (element instanceof OptPanelElements) { //TODO: change
            openView(0);
            target = element;
        }
        if (target != null) {
            final Object key = target;
            org.netbeans.modules.xml.multiview.Utils.runInAwtDispatchThread(new Runnable() {

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
        if (tgo == null) {
            tgo = getTgo();
        } else {
            java.io.InputStream is = getEditorSupport().getInputStream();

            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgo.getClass().getPackage().getName());
                javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgo = (Tgo) unmarshaller.unmarshal(is); //NOI18N
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }

        }
    }

    private static class DesignView extends DesignMultiViewDesc {

        private int type;

        DesignView(TgoDataObject dObj, int type) {
            super(dObj, "Design");
            this.type = type;
        }

        public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
            TgoDataObject dObj = (TgoDataObject) getDataObject();
            return new TgoToolBarMVElement(dObj);
        }

        public java.awt.Image getIcon() {
            return org.openide.util.Utilities.loadImage("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }

        public String preferredID() {
            return "Tgo_multiview_design" + String.valueOf(type);
        }
    }

    public Tgo getTgo() { //perhaps IOExeption?
        // If the Object "tgo" doesn't exist yet, read in from file
        if (tgo == null) {
            tgo = new Tgo();
            try {
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgo.getClass().getPackage().getName());
                javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                tgo = (Tgo) unmarshaller.unmarshal(FileUtil.toFile(this.getPrimaryFile())); //NOI18N //replaced: new java.io.File("File path") //Fix this: java.lang.IllegalArgumentException: file parameter must not be null
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            }
        }
        // Else simply return the object
        return tgo;
    }

    public void modelUpdatedFromUI() {
        modelSynchronizer.requestUpdateData();
    }

    private class ModelSynchronizer extends XmlMultiViewDataSynchronizer {

        public ModelSynchronizer(XmlMultiViewDataObject dataObject) {
            super(dataObject, 250);
        }

        protected boolean mayUpdateData(boolean allowDialog) {
            return true;
        }

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
            //marshaller.marshal(model, FileUtil.toFile(TgoDataObject.this.getPrimaryFile()));
            // But this would generate a read/write every time we changed something.

            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            } catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }

        protected Object getModel() {
            return getTgo();
        }

        protected void reloadModelFromData() {
            try {
                parseDocument();
            } catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }
    }
}
