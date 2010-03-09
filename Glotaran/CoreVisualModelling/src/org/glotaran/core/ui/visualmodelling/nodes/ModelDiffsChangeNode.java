/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import org.glotaran.core.messages.CoreErrorMessages;
import org.glotaran.core.models.gta.GtaChangesModel;
import org.glotaran.core.models.tgm.Dat;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.WeakListeners;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author slapten
 */
public class ModelDiffsChangeNode extends PropertiesAbstractNode{
    private final Image CHANGE_ICON = ImageUtilities.loadImage("org/glotaran/core/ui/visualmodelling/resources/ChageParam_16.png", true);
    private PropertyChangeListener propListner;
    private int datasetIndex;
    private FileObject parentFolder = null;

    public ModelDiffsChangeNode(String type, PropertyChangeListener listn , int datasetInd){
        super(type, new Index.ArrayChildren());
        propListner = listn;
        datasetIndex = datasetInd;
        addPropertyChangeListener(WeakListeners.propertyChange(propListner, this));
    }

    public ModelDiffsChangeNode(String type, int datasetInd, GtaChangesModel changes, FileObject schemaFolder, PropertyChangeListener listn) {
        super(type, new Index.ArrayChildren());
        propListner = listn;
        datasetIndex = datasetInd+1;
        parentFolder = schemaFolder;
        updateChangesNodes(changes);
        addPropertyChangeListener(WeakListeners.propertyChange(propListner, this));
    }

    @Override
    public Image getIcon(int type) {
        return CHANGE_ICON;
    }

    @Override
    public PasteType getDropType(Transferable t, int action, int index) {
        if (t.isDataFlavorSupported(PaletteNode.DATA_FLAVOR)){
            try {
                final PaletteItem pi = (PaletteItem)t.getTransferData(PaletteNode.DATA_FLAVOR);
                return new PasteType() {
                    @Override
                    public Transferable paste() throws IOException {
                        boolean present = false;
                        if (pi.getName().equalsIgnoreCase("Kinetic Parameters")) {
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof KineticParametersNode) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                getChildren().add(new Node[]{new KineticParametersNode(propListner)});
                            } else {
                                CoreErrorMessages.parametersExists("Kinetic parameters ");
                            }
                        }
                        present = false;
//================ irf parameter node creation ===================
                        if (pi.getName().equalsIgnoreCase("IRF Parameters")) {
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof IrfParametersNode) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                getChildren().add(new Node[]{new IrfParametersNode(propListner)});
                            } else {
                                CoreErrorMessages.parametersExists("IRF Parameters ");
                            }
                        }
//================ disp parameter node creation ===================
                        if (pi.getName().equalsIgnoreCase("Dispersion")) {
                            int paramNumb = 0;
                            EnumTypes.DispersionTypes type = null;
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof DispersionModelingNode) {
                                    paramNumb++;
                                    type = ((DispersionModelingNode) getChildren().getNodes()[i]).getDisptype();
                                    ((DispersionModelingNode) getChildren().getNodes()[i]).setSingle(false);
                                    ((DispersionModelingNode) getChildren().getNodes()[i]).recreateSheet();
                                }
                            }
                            if (paramNumb < 2) {
                                if (type != null) {
                                    type = (type.equals(EnumTypes.DispersionTypes.PARMU))
                                            ? EnumTypes.DispersionTypes.PARTAU
                                            : EnumTypes.DispersionTypes.PARMU;
                                    getChildren().add(new Node[]{new DispersionModelingNode(type, false, propListner)});
                                } else {
                                    getChildren().add(new Node[]{new DispersionModelingNode(EnumTypes.DispersionTypes.PARMU, true, propListner)});
                                }
                            } else {
                                CoreErrorMessages.parametersExists("2 Dispersion parameters ");
                            }
                        }
//================ cohspec parameter node creation ===================
                        if (pi.getName().equalsIgnoreCase("Cohspec Parameters")) {
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof CohSpecNode) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                getChildren().add(new Node[]{new CohSpecNode(propListner)});
                            } else {
                                CoreErrorMessages.parametersExists("CohSpec parameters ");
                            }
                        }
//================ weight parameter node creation ===================
                        if (pi.getName().equalsIgnoreCase("Weight Parameters")) {
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof WeightParametersNode) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                getChildren().add(new Node[]{new WeightParametersNode(propListner)});
                            } else {
                                CoreErrorMessages.parametersExists("Weight parameters ");
                            }
                        }
//================ Kmatrix parameter node creation ===================
                        if (pi.getName().equalsIgnoreCase("KMatrix")) {
                            for (int i = 0; i < getChildren().getNodesCount(); i++) {
                                if (getChildren().getNodes()[i] instanceof KmatrixNode) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                getChildren().add(new Node[]{new KmatrixNode(propListner)});
                            } else {
                                CoreErrorMessages.parametersExists("Kmatrix ");
                            }
                        }
                        return null;
                    }
                };

            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            return null;
        }
        else {
            return null;
        }
    }

    private void updateChangesNodes(GtaChangesModel changes){
        String pathToSchemaFolder;
        if (parentFolder!=null) {
          pathToSchemaFolder = parentFolder.getPath();
        } else if(getParentNode() != null) {
          pathToSchemaFolder = ((DatasetsRootNode)getParentNode().getParentNode()).getContainerComponent().getSchemaFolder().getPath();
        } else {
            pathToSchemaFolder = "";
        }
        FileObject tgmFO = FileUtil.toFileObject(new File(pathToSchemaFolder + File.separator + changes.getFilename()));
        Dat tgmDat = null;
        TgmDataObject tgmDO = null;
        if (tgmFO!=null){
            try {
                tgmDO = ((TgmDataObject)DataObject.find(tgmFO));
                tgmDat = tgmDO.getTgm().getDat();
            } catch (DataObjectNotFoundException ex) {
                CoreErrorMessages.fileLoadException("changes TGM");
                return;
            }

            if (tgmDat.getKMatrixPanel() != null) {
                if (tgmDat.getKMatrixPanel().getJVector().getVector().isEmpty()) {
                    getChildren().add(
                            new Node[]{new KmatrixNode(tgmDO, propListner)});
                }
            } else {
                if (tgmDat.getKinparPanel() != null) {
                    if (!tgmDat.getKinparPanel().getKinpar().isEmpty()) {
                        getChildren().add(
                                new Node[]{new KineticParametersNode(tgmDat.getKinparPanel(), propListner)});
                    }
                }
            }

            if (tgmDat.getIrfparPanel() != null) {
                if (!tgmDat.getIrfparPanel().getIrf().isEmpty()
                        && (!tgmDat.getIrfparPanel().isMirf())) {
                    getChildren().add(
                            new Node[]{new IrfParametersNode(tgmDat.getIrfparPanel(), propListner)});
                }

                if (tgmDat.getIrfparPanel().getParmu() != null) {
                    if (tgmDat.getIrfparPanel().getParmu().length() != 0) {
                        getChildren().add(
                                new Node[]{new DispersionModelingNode(tgmDat.getIrfparPanel(), EnumTypes.DispersionTypes.PARMU, propListner)});
                    }
                }

                if (tgmDat.getIrfparPanel().getPartau() != null) {
                    if (tgmDat.getIrfparPanel().getPartau().length() != 0) {
                        getChildren().add(
                                new Node[]{new DispersionModelingNode(tgmDat.getIrfparPanel(), EnumTypes.DispersionTypes.PARTAU, propListner)});
                    }
                }
            }

            if (tgmDat.getWeightParPanel()!=null) {
                getChildren().add(
                        new Node[]{new WeightParametersNode(tgmDat.getWeightParPanel().getWeightpar(), propListner)});
            }

            if (tgmDat.getCohspecPanel()!=null) {
                getChildren().add(
                        new Node[]{new CohSpecNode(tgmDat.getCohspecPanel(), propListner)});
            }
        }
    }
}
