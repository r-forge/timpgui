/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.vu.nat.tgoeditor;


import nl.vu.nat.tgmodels.tgo.AlgorithmPanelElements;
import nl.vu.nat.tgmodels.tgo.IterPanelElements;
import nl.vu.nat.tgmodels.tgo.OptPanelElements;
import nl.vu.nat.tgmodels.tgo.OutputPanelElements;
import nl.vu.nat.tgmodels.tgo.Tgo;
import nl.vu.nat.tgofilesupport.TgoDataObject;

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
public class TgoToolBarMVElement extends ToolBarMultiViewElement {

    private TgoDataObject dObj;
    private SectionView view;
    private ToolBarDesignEditor comp;
    private PanelFactory factory;

    public TgoToolBarMVElement(TgoDataObject dObj) {

        super(dObj);
        this.dObj = dObj;
        comp = new ToolBarDesignEditor();
        factory = new PanelFactory(comp, dObj);
        setVisualEditor(comp);

    }

    public SectionView getSectionView() {

        return view;

    }

    @Override
    public void componentShowing() {

        super.componentShowing();
        view = new TgoView(dObj);
        comp.setContentView(view);
        //This determines what view is opened by default.
        //try {
        view.openPanel(dObj.getTgo());
        //} //catch(java.io.IOException ex){}
        view.checkValidity();

    }

    private class TgoView extends SectionView {

        TgoView(TgoDataObject dObj) {//throws IOException {
            super(factory);

            // This is where we design the Design View of the TGO Editor
            Children rootChildren = new Children.Array();
            Node root = new AbstractNode(rootChildren);

            Tgo tgo = dObj.getTgo();
            Node tgoNode = new TgoNode(tgo);

            OptPanelElements opt = tgo.getOpt();//tgo.getOpt();
            Node optNode = new OptNode(opt);

            IterPanelElements iterPanelElements = opt.getIterPanel();//tgo.getOpt();
            Node iterPanelElementsNode = new IterPanelElementsNode(iterPanelElements);

            AlgorithmPanelElements algorithmPanelElements = opt.getAlgorithmPanel();//tgo.getOpt();
            Node algorithmPanelElementsNode = new AlgorithmPanelElementsNode(algorithmPanelElements);

            OutputPanelElements outputPanelElements = opt.getOutputpanel();//tgo.getOpt();
            Node outputPanelElementsNode = new OutputPanelElementsNode(outputPanelElements);

            rootChildren.add(new Node[]{tgoNode, optNode, iterPanelElementsNode, algorithmPanelElementsNode, outputPanelElementsNode});
            // add panels
            addSection(new SectionPanel(this, tgoNode, tgo)); //NOI18N
            addSection(new SectionPanel(this, optNode, opt)); //NOI18N
            addSection(new SectionPanel(this, iterPanelElementsNode, iterPanelElements)); //NOI18N
            addSection(new SectionPanel(this, algorithmPanelElementsNode, algorithmPanelElements)); //NOI18N
            addSection(new SectionPanel(this, outputPanelElementsNode, outputPanelElements)); //NOI18N

            setRoot(root);
        }
    }

    private class TgoNode extends org.openide.nodes.AbstractNode {

        TgoNode(Tgo tgo) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model Optimization Options");
            setIconBaseWithExtension("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }
    }

    private class OptNode extends org.openide.nodes.AbstractNode {

        OptNode(OptPanelElements opt) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Model Options Name");
            setIconBaseWithExtension("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }
    }

    private class AlgorithmPanelElementsNode extends org.openide.nodes.AbstractNode {

        AlgorithmPanelElementsNode(AlgorithmPanelElements algorithmPanelElements) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Optimization algorithm and constrains");
            setIconBaseWithExtension("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }
    }

    private class IterPanelElementsNode extends org.openide.nodes.AbstractNode {

        IterPanelElementsNode(IterPanelElements iterPanelElements) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Output options");
            setIconBaseWithExtension("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }
    }

    private class OutputPanelElementsNode extends org.openide.nodes.AbstractNode {

        OutputPanelElementsNode(OutputPanelElements outputPanelElements) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName("Output PanelElements");
            setIconBaseWithExtension("nl/vu/nat/tgofilesupport/povicon.gif"); //NOI18N
        }
    }
}


