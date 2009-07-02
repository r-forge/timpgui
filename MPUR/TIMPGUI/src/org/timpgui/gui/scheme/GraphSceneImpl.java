/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme;

import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author jsg210
 */
public class GraphSceneImpl extends VMDGraphScene {

    @Override
    protected void attachEdgeSourceAnchor(String arg0, String arg1, String arg2) {
        super.attachEdgeSourceAnchor(arg0, arg1, arg2);
    }

    @Override
    protected void attachEdgeTargetAnchor(String arg0, String arg1, String arg2) {
        super.attachEdgeTargetAnchor(arg0, arg1, arg2);
    }

    @Override
    protected Widget attachEdgeWidget(String arg0) {
        return super.attachEdgeWidget(arg0);
    }

    @Override
    protected Widget attachNodeWidget(String arg0) {
        return super.attachNodeWidget(arg0);
    }

    @Override
    protected Widget attachPinWidget(String arg0, String arg1) {
        return super.attachPinWidget(arg0, arg1);
    }

    @Override
    public void layoutScene() {
        super.layoutScene();
    }
    
}
