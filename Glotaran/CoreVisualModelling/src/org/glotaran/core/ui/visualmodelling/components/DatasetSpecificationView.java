/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.components;

import java.awt.dnd.DnDConstants;
import org.openide.explorer.view.BeanTreeView;

/**
 *
 * @author jsg210
 */
public class DatasetSpecificationView extends BeanTreeView {

//    private ExplorerManager manager;
    public DatasetSpecificationView() {
        super();
        setRootVisible(false);
        setAllowedDragActions(DnDConstants.ACTION_COPY);
        setAllowedDropActions(DnDConstants.ACTION_COPY);
    }

}
