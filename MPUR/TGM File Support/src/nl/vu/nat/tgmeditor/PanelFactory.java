/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.vu.nat.tgmeditor;

import nl.vu.nat.tgmeditor.panels.CohspecPanel;
import nl.vu.nat.tgmeditor.panels.TgmPanel;
import nl.vu.nat.tgmeditor.panels.DatPanel;
import nl.vu.nat.tgmeditor.panels.FlimPanel;
import nl.vu.nat.tgmeditor.panels.IrfparPanel;
import nl.vu.nat.tgmfilesupport.TgmDataObject;
import nl.vu.nat.tgmeditor.panels.KinparPanel;
import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.FlimPanelModel;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;
import org.netbeans.modules.xml.multiview.ui.InnerPanelFactory;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;

/**
 *
 * @author joris
 */
public class PanelFactory implements InnerPanelFactory {
   
    private TgmDataObject dObj;
    private ToolBarDesignEditor editor;
    
    PanelFactory(ToolBarDesignEditor editor, TgmDataObject dObj) {
        this.dObj=dObj;
        this.editor=editor;
    }
    
    public SectionInnerPanel createInnerPanel(Object key) {
       if (key instanceof Tgm) return new TgmPanel((SectionView)editor.getContentView(), dObj, (Tgm)key);
       else if (key instanceof KinparPanelModel) return new KinparPanel((SectionView)editor.getContentView(), dObj, (KinparPanelModel)key);
       else if (key instanceof IrfparPanelModel) return new IrfparPanel((SectionView)editor.getContentView(), dObj, (IrfparPanelModel)key);
       else if (key instanceof CohspecPanelModel) return new CohspecPanel((SectionView)editor.getContentView(), dObj, (CohspecPanelModel)key);
       else if (key instanceof FlimPanelModel) return new FlimPanel((SectionView)editor.getContentView(), dObj, (FlimPanelModel)key);
       else return new DatPanel((SectionView)editor.getContentView(), dObj, (Dat)key);
    }

}
