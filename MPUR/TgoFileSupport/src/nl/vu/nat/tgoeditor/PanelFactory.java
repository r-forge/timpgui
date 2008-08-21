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
import nl.vu.nat.tgoeditor.panels.TgoPanel;
import nl.vu.nat.tgoeditor.panels.AlgorithmPanel;

import nl.vu.nat.tgofilesupport.TgoDataObject;

import nl.vu.nat.tgoeditor.panels.IterPanel;
import nl.vu.nat.tgoeditor.panels.OptPanel;
import nl.vu.nat.tgoeditor.panels.OutputPanel;
import org.netbeans.modules.xml.multiview.ui.InnerPanelFactory;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;

/**
 *
 * @author joris
 */
public class PanelFactory implements InnerPanelFactory {

    private TgoDataObject dObj;
    private ToolBarDesignEditor editor;

    PanelFactory(ToolBarDesignEditor editor, TgoDataObject dObj) {
        this.dObj = dObj;
        this.editor = editor;
    }

    public SectionInnerPanel createInnerPanel(Object key) {
        if (key instanceof Tgo) {
            return new TgoPanel((SectionView) editor.getContentView(), dObj, (Tgo) key);
        } else if (key instanceof OptPanelElements) {
            return new OptPanel((SectionView) editor.getContentView(), dObj, (OptPanelElements) key);
        } else if (key instanceof OutputPanelElements) {
            return new OutputPanel((SectionView) editor.getContentView(), dObj, (OutputPanelElements) key);
        } else if (key instanceof IterPanelElements) {
            return new IterPanel((SectionView) editor.getContentView(), dObj, (IterPanelElements) key);
        } else if (key instanceof AlgorithmPanelElements) {
            return new AlgorithmPanel((SectionView) editor.getContentView(), dObj, (AlgorithmPanelElements) key);
        } else {
            return null;
        }
    } //endof createInnerPanel
}
