/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgmeditor;

import java.util.ArrayList;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmodels.tgm.WeightParPanelModel;
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
        this.dObj.modelUpdatedFromUI();
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

            WeightParPanelModel weightParPanelModel = dat.getWeightParPanel();
            Node weightparPanelNode = new WeightparPanelNode(weightParPanelModel);

            CohspecPanelModel cohspecPanelModel = dat.getCohspecPanel();
            Node cohspecPanelNode = new CohspecPanelNode(cohspecPanelModel);

            KMatrixPanelModel kMatrixPanelModel = dat.getKMatrixPanel();
            Node kMatrixPanelNode = new KMatrixPanelNode(kMatrixPanelModel);

            nodeArray = new Node[]{tgmNode, datNode, kinparPanelNode, irfparPanelNode, weightparPanelNode, cohspecPanelNode, kMatrixPanelNode};
            rootChildren.add(nodeArray);

            // add panels
            addSection(new SectionPanel(this, tgmNode, tgm)); //NOI18N
            addSection(new SectionPanel(this, datNode, dat)); //NOI18N
            addSection(new SectionPanel(this, kinparPanelNode, kinparPanelModel)); //NOI18N
            addSection(new SectionPanel(this, irfparPanelNode, irfparPanelModel)); //NOI18N
            addSection(new SectionPanel(this, weightparPanelNode, weightParPanelModel)); //NOI18N
            addSection(new SectionPanel(this, cohspecPanelNode, cohspecPanelModel)); //NOI18N
            addSection(new SectionPanel(this, kMatrixPanelNode, kMatrixPanelModel)); //NOI18N

            setRoot(root);
        }

        public Node[] getNodeArray() {
            return nodeArray;
        }
    }

    private class TgmNode extends org.openide.nodes.AbstractNode {

        TgmNode(Tgm tgm) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("TIMPGUI Model Root Node:");
        //setIconBase("org/netbeans/modules/web/dd/multiview/resources/class"); //NOI18N
        }
    }

    private class DatNode extends org.openide.nodes.AbstractNode {

        DatNode(Dat dat) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("1) Model name and type:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class KinparPanelNode extends org.openide.nodes.AbstractNode {

        KinparPanelNode(KinparPanelModel kinparPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("2) Decay rates:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class IrfparPanelNode extends org.openide.nodes.AbstractNode {

        IrfparPanelNode(IrfparPanelModel irfparPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("3) Instrument Response Function (IRF):");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class WeightparPanelNode extends org.openide.nodes.AbstractNode {

        WeightparPanelNode(WeightParPanelModel weightParPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("4) Weighting parameters:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class CohspecPanelNode extends org.openide.nodes.AbstractNode {

        CohspecPanelNode(CohspecPanelModel cohspecPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("5) Coherent artifact:");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

    private class KMatrixPanelNode extends org.openide.nodes.AbstractNode {

        KMatrixPanelNode(KMatrixPanelModel kMatrixPanelModel) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("6) K Matrix (advanced):");
            setIconBaseWithExtension("nl/vu/nat/tgmfilesupport/povicon.gif"); //NOI18N
        }
    }

  
}



