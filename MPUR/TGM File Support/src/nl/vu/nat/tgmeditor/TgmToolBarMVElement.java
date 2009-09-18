/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmeditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.FlimPanelModel;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.ui.SectionPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author joris
 */
public class TgmToolBarMVElement extends ToolBarMultiViewElement {

    private TgmDataObject dObj;
    private TgmView view;
    private ToolBarDesignEditor comp;
    private PanelFactory factory;
    private ArrayList<Boolean> activeSectionViewPanels = new ArrayList<Boolean>();

    public TgmToolBarMVElement(TgmDataObject dObj) {

        super(dObj);
        this.dObj = dObj;
        comp = new ToolBarDesignEditor();
        factory = new PanelFactory(comp, dObj);
        setVisualEditor(comp);

    }

    @Override
    public SectionView getSectionView() {
        return view;
    }

    @Override
    public void componentShowing() {

        super.componentShowing();
        view = new TgmView(this.dObj);
        comp.setContentView(view);
        //This determines what view is opened by default:
        //try {
        Node[] nodeArray = view.getNodeArray();
        //} //catch(java.io.IOException ex){}
        if (!activeSectionViewPanels.isEmpty()) {
            for (int i = 0; i < nodeArray.length; i++) {
                if (activeSectionViewPanels.get(i)) {
                    view.openPanel(nodeArray[i]);
                }
            }
        }
        view.checkValidity();

    }

    @Override
    public void componentClosed() {
        super.componentClosed();
    }

    @Override
    public void componentHidden() {
        // this is called before componentClosed
        super.componentHidden();
        Node[] nodeArray = view.getNodeArray();
        ArrayList<Boolean> tempBooleanArrayList = new ArrayList<Boolean>();
        for (Node node : nodeArray) {
            tempBooleanArrayList.add(view.getSection(node).isActive());
        }
        activeSectionViewPanels = tempBooleanArrayList;
    }

    private class TgmView extends SectionView {

        private Node[] nodeArray;

        TgmView(TgmDataObject dObj) {//throws IOException {
            super(factory);

            // This is where we design the Design View of the TGM Editor
            Children rootChildren = new Children.Array();
            Node root = new AbstractNode(rootChildren);

            Tgm tgm = dObj.getTgm();
            TgmNode tgmNode = new TgmNode(tgm);

            Dat dat = tgm.getDat();
            Node datNode = new DatNode(dat);

            KinparPanelModel kinparPanelModel = dat.getKinparPanel();
            Node kinparPanelNode = new KinparPanelNode(kinparPanelModel);

            IrfparPanelModel irfparPanelModel = dat.getIrfparPanel();
            Node irfparPanelNode = new IrfparPanelNode(irfparPanelModel);

            CohspecPanelModel cohspecPanelModel = dat.getCohspecPanel();
            Node cohspecPanelNode = new CohspecPanelNode(cohspecPanelModel);

            KMatrixPanelModel kMatrixPanelModel = dat.getKMatrixPanel();
            Node kMatrixPanelNode = new KMatrixPanelNode(kMatrixPanelModel);

            FlimPanelModel flimPanelModel = dat.getFlimPanel();
            Node flimPanelNode = new FlimPanelNode(flimPanelModel);

            nodeArray = new Node[]{tgmNode, datNode, kinparPanelNode, irfparPanelNode, cohspecPanelNode, kMatrixPanelNode, flimPanelNode};
            rootChildren.add(nodeArray);

            // add panels
            addSection(new SectionPanel(this, tgmNode, tgm)); //NOI18N
            addSection(new SectionPanel(this, datNode, dat)); //NOI18N
            addSection(new SectionPanel(this, kinparPanelNode, kinparPanelModel)); //NOI18N
            addSection(new SectionPanel(this, irfparPanelNode, irfparPanelModel)); //NOI18N
            addSection(new SectionPanel(this, cohspecPanelNode, cohspecPanelModel)); //NOI18N
            addSection(new SectionPanel(this, kMatrixPanelNode, kMatrixPanelModel)); //NOI18N
            addSection(new SectionPanel(this, flimPanelNode, flimPanelModel)); //NOI18N

            setRoot(root);
        }

        public Node[] getNodeArray() {
            return nodeArray;
        }
    }

    private class TgmNode extends org.openide.nodes.AbstractNode {

        TgmNode(Tgm tgm) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("TIMPGUI Model (tgm) specification:");
        //setIconBase("org/netbeans/modules/web/dd/multiview/resources/class"); //NOI18N
        }
    }

    private class DatNode extends org.openide.nodes.AbstractNode {

        DatNode(Dat dat) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model name and type:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class KinparPanelNode extends org.openide.nodes.AbstractNode {

        KinparPanelNode(KinparPanelModel kinparPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model specification decay rates:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class IrfparPanelNode extends org.openide.nodes.AbstractNode {

        IrfparPanelNode(IrfparPanelModel irfparPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model specification for instrument response:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class CohspecPanelNode extends org.openide.nodes.AbstractNode {

        CohspecPanelNode(CohspecPanelModel cohspecPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model for coherent artifact:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class KMatrixPanelNode extends org.openide.nodes.AbstractNode {

        KMatrixPanelNode(KMatrixPanelModel kMatrixPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model for K Matrix:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class FlimPanelNode extends org.openide.nodes.AbstractNode {

        FlimPanelNode(FlimPanelModel flimPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Options for FLIM data:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }
}



