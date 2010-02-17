/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.view;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaLayout;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;
import org.glotaran.core.ui.visualmodelling.common.VisualCommonFunctions;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.Widget;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class CustomSceneAcceptProvider implements AcceptProvider {

    private GraphSceneImpl scene;

    public CustomSceneAcceptProvider(GraphSceneImpl scene) {
        this.scene = scene;
    }

    public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
        //Image dragImage = getImageFromTransferable(transferable);
        ConnectorState accept = ConnectorState.REJECT;
        if (transferable.isDataFlavorSupported(TgmDataNode.DATA_FLAVOR)) {
            accept = ConnectorState.ACCEPT;
        } else if (transferable.isDataFlavorSupported(PaletteNode.DATA_FLAVOR)) {
                PaletteItem item = null;
                try {
                    item = (PaletteItem) transferable.getTransferData(PaletteNode.DATA_FLAVOR);
                } catch (UnsupportedFlavorException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                if (item.getCategory().compareTo("Containers") == 0 ||
                        item.getCategory().compareTo("Output") == 0) {
                    accept = ConnectorState.ACCEPT;
                } else { 
                    accept = ConnectorState.REJECT;
                }
            } else {
                accept = ConnectorState.REJECT;
            }        
        return accept;
    }

    public void accept(Widget widget, Point point, Transferable transferable) {
        Widget newWidget = null;

        // A new components is added from the Pallete
        if (transferable.isDataFlavorSupported(PaletteNode.DATA_FLAVOR)) {
            final PaletteItem item = VisualCommonFunctions.getPaletteItemTransferable(transferable);
            if (item.getName().equalsIgnoreCase("Dataset Container")) {
                GtaDatasetContainer newDatasetContainer = new GtaDatasetContainer();
                GtaLayout widlayout = new GtaLayout();
                widlayout.setXposition(point.getX());
                widlayout.setYposition(point.getY());
                newDatasetContainer.setLayout(widlayout);
                newWidget = scene.addNode(newDatasetContainer);
            } else if (item.getName().equalsIgnoreCase("StandardOutput")) {
                GtaOutput gtaOutput = new GtaOutput();
                GtaLayout widlayout = new GtaLayout();
                widlayout.setXposition(point.getX());
                widlayout.setYposition(point.getY());
                gtaOutput.setLayout(widlayout);
                newWidget = scene.addNode(gtaOutput);
            }           
        }

        // A existing dataobject is dragged from within the platform onto the GraphScene
        if (transferable.isDataFlavorSupported(TgmDataNode.DATA_FLAVOR)) {
            try {
                TgmDataNode tgmNode = (TgmDataNode) transferable.getTransferData(TgmDataNode.DATA_FLAVOR);
                GtaModelReference newModel = new GtaModelReference();
                FileObject fo = tgmNode.getObject().getPrimaryFile();
                String path = FileUtil.getRelativePath(OpenProjects.getDefault().getMainProject().getProjectDirectory(), fo);
                newModel.setPath(path);
                newModel.setFilename(fo.getName());
                newModel.setId(path);
                GtaLayout widlayout = new GtaLayout();
                widlayout.setXposition(point.getX());
                widlayout.setYposition(point.getY());
                newModel.setLayout(widlayout);
                newWidget = scene.addNode(newModel);
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                CoreErrorMessages.IOException("CustomScenAcceptProvider");
            }
        }
        newWidget.setPreferredLocation(point);
        scene.validate();
    }
}
