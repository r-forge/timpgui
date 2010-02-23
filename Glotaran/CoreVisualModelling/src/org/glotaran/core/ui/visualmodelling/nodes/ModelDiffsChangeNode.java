/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.ui.visualmodelling.nodes;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.glotaran.core.main.mesages.CoreErrorMessages;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.core.ui.visualmodelling.palette.PaletteNode;
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

    public ModelDiffsChangeNode(String type, PropertyChangeListener listn , int datasetInd){
        super(type, new Index.ArrayChildren());
        propListner = listn;
        datasetIndex = datasetInd;
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

}
