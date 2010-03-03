/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import org.glotaran.core.models.tgm.KMatrixPanelModel;
import org.glotaran.core.ui.visualmodelling.common.KmatrixPropertyEditor;
import org.glotaran.core.ui.visualmodelling.components.ModelContainer;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author slapten
 */
public class KmatrixNode extends PropertiesAbstractNode{
    private final Image ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/Kmatr_16.png", true);
    private TgmDataObject kMatr;

    public KmatrixNode(PropertyChangeListener listn){
        super("Kmatrix", Children.LEAF);
        this.addPropertyChangeListener(listn);
    }

    public KmatrixNode(TgmDataObject model, PropertyChangeListener listn) {
        super("Kmatrix", Children.LEAF,Lookups.singleton(model));
        this.addPropertyChangeListener(listn);
    }

    @Override
    public Image getIcon(int type) {
        return ICON;
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

        @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        PropertySupport.Reflection kMatrix = null;
       try {
            kMatrix = new PropertySupport.Reflection(this, TgmDataObject.class, "getKmatrix", null);
            kMatrix.setPropertyEditorClass(KmatrixPropertyEditor.class);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        kMatrix.setName("Kmatrix");

        set.put(kMatrix);
        sheet.put(set);
        return sheet;
    }

    public TgmDataObject getKmatrix(){
        return getLookup().lookup(TgmDataObject.class);
    }

}
