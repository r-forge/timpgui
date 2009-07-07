/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.timpgui.gui.scheme;

/**
 *
 * @author jsg210
 */
import java.awt.Image;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.openide.util.Utilities;

public class TIMPGUIScene extends VMDGraphScene{
    private static final Image IMAGE_LIST = Utilities.loadImage ("org/timpgui/gui/scheme/resources/list_16.png"); // NOI18N
    private static final Image IMAGE_CANVAS = Utilities.loadImage ("org/timpgui/gui/scheme/resources/custom_displayable_16.png"); // NOI18N
    private static final Image IMAGE_COMMAND = Utilities.loadImage ("org/timpgui/gui/scheme/resources/command_16.png"); // NOI18N
    private static final Image IMAGE_ITEM = Utilities.loadImage ("org/timpgui/gui/scheme/resources/item_16.png"); // NOI18N
    private static final Image GLYPH_PRE_CODE = Utilities.loadImage ("org/timpgui/gui/scheme/resources/preCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_POST_CODE = Utilities.loadImage ("org/timpgui/gui/scheme/resources/postCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_CANCEL = Utilities.loadImage ("org/timpgui/gui/scheme/resources/cancelGlyph.png"); // NOI18N
    private static int nodeID = 1;
    private static int edgeID = 1;
    /** Creates a new instance of TIMPGUIScene */

    public TIMPGUIScene() {
        String mobile = createNode (this, 100, 100, IMAGE_LIST, "menu", "List", null);
        createPin (this, mobile, "start", IMAGE_ITEM, "Start", "Element");
        String game = createNode (this, 600, 100, IMAGE_CANVAS, "gameCanvas", "MyCanvas", Arrays.asList (GLYPH_PRE_CODE, GLYPH_CANCEL, GLYPH_POST_CODE));
        createPin (this, game, "ok", IMAGE_COMMAND, "okCommand1", "Command");
        createEdge (this, "start", game);
        createEdge (this, "ok", mobile);
    }

    private static String createNode (VMDGraphScene scene, int x, int y, Image image, String name, String type, List<Image> glyphs) {
        String nodeID = "node" + TIMPGUIScene.nodeID ++;
        VMDNodeWidget widget = (VMDNodeWidget) scene.addNode (nodeID);
        widget.setPreferredLocation (new Point (x, y));
        widget.setNodeProperties (image, name, type, glyphs);
        scene.addPin (nodeID, nodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
        return nodeID;
    }


    private static void createPin (VMDGraphScene scene, String nodeID, String pinID, Image image, String name, String type) {
        ((VMDPinWidget) scene.addPin (nodeID, pinID)).setProperties (name, null);
    }


    private static void createEdge (VMDGraphScene scene, String sourcePinID, String targetNodeID) {
        String edgeID = "edge" + TIMPGUIScene.edgeID ++;
        scene.addEdge (edgeID);
        scene.setEdgeSource (edgeID, sourcePinID);
        scene.setEdgeTarget (edgeID, targetNodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
    }
}

