/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.tgmeditor.view;

import org.glotaran.core.models.tgm.CohspecPanelModel;
import org.glotaran.core.models.tgm.Dat;
import org.glotaran.core.models.tgm.IrfparPanelModel;
import org.glotaran.core.models.tgm.KMatrixPanelModel;
import org.glotaran.core.models.tgm.KinparPanelModel;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.core.models.tgm.WeightParPanelModel;
import org.glotaran.tgmeditor.panels.CohspecPanel;
import org.glotaran.tgmeditor.panels.DatPanel;
import org.glotaran.tgmeditor.panels.IrfparPanel;
import org.glotaran.tgmeditor.panels.KMatrixPanel;
import org.glotaran.tgmeditor.panels.KinparPanel;
import org.glotaran.tgmeditor.panels.TgmPanel;
import org.glotaran.tgmeditor.panels.WeightparPanel;
import org.glotaran.tgmfilesupport.TgmDataObject;
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
    
    @Override
    public SectionInnerPanel createInnerPanel(Object key) {
       if (key instanceof Tgm) return new TgmPanel((SectionView)editor.getContentView(), dObj, (Tgm)key);
       else if (key instanceof KinparPanelModel) return new KinparPanel((SectionView)editor.getContentView(), dObj, (KinparPanelModel)key);
       else if (key instanceof IrfparPanelModel) return new IrfparPanel((SectionView)editor.getContentView(), dObj, (IrfparPanelModel)key);
       else if (key instanceof WeightParPanelModel) return new WeightparPanel((SectionView)editor.getContentView(), dObj, (WeightParPanelModel)key);
       else if (key instanceof CohspecPanelModel) return new CohspecPanel((SectionView)editor.getContentView(), dObj, (CohspecPanelModel)key);
       else if (key instanceof KMatrixPanelModel) return new KMatrixPanel((SectionView)editor.getContentView(), dObj, (KMatrixPanelModel)key);    
       else return new DatPanel((SectionView)editor.getContentView(), dObj, (Dat)key);
    }

}
