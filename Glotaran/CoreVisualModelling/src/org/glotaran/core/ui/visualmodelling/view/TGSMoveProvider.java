/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.view;

import java.awt.Point;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelReference;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author slapten
 */
public class TGSMoveProvider implements MoveProvider{

    public void movementStarted(Widget widget) {

    }

    public void movementFinished(Widget widget) {
        GraphSceneImpl scene = (GraphSceneImpl)widget.getScene();
        if (scene.findObject(widget) instanceof GtaModelReference){
            ((GtaModelReference)scene.findObject(widget)).getLayout().setXposition(widget.getPreferredLocation().getX());
            ((GtaModelReference)scene.findObject(widget)).getLayout().setYposition(widget.getPreferredLocation().getY());
        }
        if (scene.findObject(widget) instanceof GtaDatasetContainer){
            ((GtaDatasetContainer)scene.findObject(widget)).getLayout().setXposition(widget.getPreferredLocation().getX());
            ((GtaDatasetContainer)scene.findObject(widget)).getLayout().setYposition(widget.getPreferredLocation().getY());
        }
        scene.getDobj().setModified(true);
    }

    public Point getOriginalLocation(Widget widget) {
        return widget.getLocation();
    }

    public void setNewLocation(Widget widget, Point location) {
        widget.setPreferredLocation(location);
    }

}
