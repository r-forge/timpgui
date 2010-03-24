/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.core.ui.visualmodelling.common;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.glotaran.core.models.tgm.CohspecPanelModel;
import org.glotaran.core.models.tgm.Dat;
import org.glotaran.core.models.tgm.IrfparPanelModel;
import org.glotaran.core.models.tgm.KinPar;
import org.glotaran.core.models.tgm.KinparPanelModel;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.core.models.tgm.WeightPar;
import org.glotaran.core.models.tgm.WeightParPanelModel;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.CohSpecTypes;
import org.glotaran.core.ui.visualmodelling.common.EnumTypes.IRFTypes;
import org.glotaran.core.ui.visualmodelling.nodes.CohSpecNode;
import org.glotaran.core.ui.visualmodelling.nodes.DispersionModelingNode;
import org.glotaran.core.ui.visualmodelling.nodes.IrfParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.KineticParametersNode;
import org.glotaran.core.ui.visualmodelling.nodes.ParametersSubNode;
import org.glotaran.core.ui.visualmodelling.nodes.WeightParametersNode;
import org.glotaran.core.ui.visualmodelling.palette.PaletteItem;
import org.glotaran.tgmfilesupport.TgmDataNode;
import org.openide.nodes.Children;
import static java.lang.Math.floor;

/**
 *
 * @author slapten
 */
public class VisualCommonFunctions {

    public static TgmDataNode createNewTgmFile(File file) {
        Tgm tgm = new Tgm();
        tgm.setDat(new Dat());
        tgm.getDat().setModType("ModelDifferences");
        tgm.getDat().setModelName(file.getName());

        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(tgm.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(tgm, file);

        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }


        return null;
    }

    public static PaletteItem getPaletteItemTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(new DataFlavor(PaletteItem.class, "PaletteItem"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        }
        return o instanceof PaletteItem ? (PaletteItem) o : null; //TODO: not null
    }

    public static ArrayList<Double> strToParams(String paramStr) {
        ArrayList<Double> paramList = new ArrayList<Double>();
        String[] paramStrArr = paramStr.split(",");
        for (int i = 0; i < paramStrArr.length; i++) {
            paramList.add(Double.parseDouble(paramStrArr[i]));
        }
//        StringTools.getListFromCsv(paramStr);
        return paramList;
    }

    public static boolean modelParametersChange(Dat model, PropertyChangeEvent evt) {
        if (evt.getSource().getClass().equals(KineticParametersNode.class)) {
            if (model.getKinparPanel() == null) {
                model.setKinparPanel(new KinparPanelModel());
            }
            if (evt.getPropertyName().equalsIgnoreCase("Number of components")) {
                if ((Integer) evt.getNewValue() > (Integer) evt.getOldValue()) {
                    for (int i = 0; i < (Integer) evt.getNewValue() - (Integer) evt.getOldValue(); i++) {
                        KinPar newkp = new KinPar();
                        newkp.setFixed(Boolean.FALSE);
                        newkp.setConstrained(Boolean.FALSE);
                        model.getKinparPanel().getKinpar().add(newkp);
                    }
                } else {
                    for (int i = 0; i < (Integer) evt.getOldValue() - (Integer) evt.getNewValue(); i++) {
                        model.getKinparPanel().getKinpar().remove(
                                model.getKinparPanel().getKinpar().size() - 1);
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("Positise rates")) {
                model.getKinparPanel().setPositivepar((Boolean) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("Sequential model")) {
                model.getKinparPanel().setSeqmod((Boolean) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("start")) {
                model.getKinparPanel().getKinpar().get((int) floor((Double) evt.getOldValue())).setStart((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")) {
                model.getKinparPanel().getKinpar().get((int) floor((Double) evt.getOldValue())).setFixed((Boolean) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")) {
                int index = (Integer) evt.getNewValue();
                model.getKinparPanel().getKinpar().remove(index);
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")) {
                model.getKinparPanel().getKinpar().clear();
                model.getKinparPanel().setPositivepar(false);
                model.getKinparPanel().setSeqmod(false);
            }
            return true;
        }
        if (evt.getSource().getClass().equals(IrfParametersNode.class)) {
            if (model.getIrfparPanel() == null) {
                model.setIrfparPanel(new IrfparPanelModel());
            }
            if (evt.getPropertyName().equalsIgnoreCase("SetBackSweep")) {
                model.getIrfparPanel().setBacksweepEnabled((Boolean) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("SetBackSweepPeriod")) {
                model.getIrfparPanel().setBacksweepPeriod((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")) {
                model.getIrfparPanel().getIrf().clear();
                model.getIrfparPanel().getFixed().clear();
                model.getIrfparPanel().setParmufixed(Boolean.FALSE);
                model.getIrfparPanel().setPartaufixed(Boolean.FALSE);
                model.getIrfparPanel().setBacksweepEnabled(Boolean.FALSE);
                model.getIrfparPanel().setMirf(Boolean.FALSE);
            }
            if (evt.getPropertyName().equalsIgnoreCase("SetIRFType")) {
                setIrfType(model, evt, (IRFTypes) evt.getNewValue());
            }

            if (evt.getPropertyName().equalsIgnoreCase("start")) {
                if (model.getIrfparPanel().getIrf().isEmpty()) {
                    setIrfType(model, evt, ((IrfParametersNode) evt.getSource()).getIRFType());
                }
                model.getIrfparPanel().getIrf().set((int) floor((Double) evt.getOldValue()), (Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")) {
                if (model.getIrfparPanel().getIrf().isEmpty()) {
                    setIrfType(model, evt, ((IrfParametersNode) evt.getSource()).getIRFType());
                }
                model.getIrfparPanel().getFixed().set((int) floor((Double) evt.getOldValue()), (Boolean) evt.getNewValue());
            }
            return true;
        }
        if (evt.getSource().getClass().equals(CohSpecNode.class)) {
            if (model.getCohspecPanel() == null) {
                model.setCohspecPanel(new CohspecPanelModel());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setClpMax")) {
                model.getCohspecPanel().setClp0Max((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setClpMin")) {
                model.getCohspecPanel().setClp0Min((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setClpZero")) {
                model.getCohspecPanel().setClp0Enabled((Boolean) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setCohType")) {
                EnumTypes.CohSpecTypes cohType = (CohSpecTypes) evt.getNewValue();
                switch (cohType) {
                    case IRF: {
                        model.getCohspecPanel().getCohspec().setType("irf");
                        model.getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case FREE_IRF: {
                        model.getCohspecPanel().getCohspec().setType("freeirfdisp");
                        model.getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case IRF_MULTY: {
                        model.getCohspecPanel().getCohspec().setType("irfmulti");
                        model.getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case MIXED: {
                        model.getCohspecPanel().getCohspec().setType("mix");
                        model.getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                    case SEQ: {
                        model.getCohspecPanel().getCohspec().setType("seq");
                        model.getCohspecPanel().getCohspec().setSet(true);
                        break;
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")) {
                model.getCohspecPanel().setClp0Enabled(null);
                model.getCohspecPanel().setClp0Max(null);
                model.getCohspecPanel().setClp0Min(null);
                model.getCohspecPanel().setCoh("");
                model.getCohspecPanel().getCohspec().setSet(false);
                model.getCohspecPanel().getCohspec().setType("");
            }
            return true;

        }
        if (evt.getSource().getClass().equals(DispersionModelingNode.class)) {
            if (model.getIrfparPanel() == null) {
                model.setIrfparPanel(new IrfparPanelModel());
            }
            boolean parMu = evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU);
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")) {
                Children nodes = ((DispersionModelingNode) evt.getSource()).getParentNode().getChildren();

                for (int i = 0; i < nodes.getNodesCount(); i++) {
                    if (nodes.getNodes()[i] instanceof DispersionModelingNode) {
                        ((DispersionModelingNode) nodes.getNodes()[i]).setSingle(true);
                        ((DispersionModelingNode) nodes.getNodes()[i]).recreateSheet();
                    }
                }
                if (parMu) {
                    model.getIrfparPanel().setParmufixed(false);
                    model.getIrfparPanel().setDispmufun("");
                    model.getIrfparPanel().setParmu("");
                } else {
                    model.getIrfparPanel().setPartaufixed(false);
                    model.getIrfparPanel().setDisptaufun("");
                    model.getIrfparPanel().setPartau("");
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("setCentralWave")) {
                model.getIrfparPanel().setLamda((Double) evt.getNewValue());
            }

            if (evt.getPropertyName().equalsIgnoreCase("setDisptype")) {
                IrfparPanelModel irfModel = model.getIrfparPanel();
                if ((evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU))
                        & (evt.getNewValue().equals(EnumTypes.DispersionTypes.PARTAU))) {

                    irfModel.setPartaufixed(irfModel.isParmufixed());
                    irfModel.setDisptaufun(irfModel.getDispmufun());
                    irfModel.setPartau(irfModel.getParmu());
                    model.getIrfparPanel().setParmufixed(false);
                    model.getIrfparPanel().setDispmufun("");
                    model.getIrfparPanel().setParmu("");
                } else {
                    if ((evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU))
                            & (evt.getNewValue().equals(EnumTypes.DispersionTypes.PARMU))) {

                        irfModel.setPartaufixed(irfModel.isParmufixed());
                        irfModel.setDisptaufun(irfModel.getDispmufun());
                        irfModel.setPartau(irfModel.getParmu());
                        model.getIrfparPanel().setParmufixed(false);
                        model.getIrfparPanel().setDispmufun("");
                        model.getIrfparPanel().setParmu("");
                    }
                }
            }
            if ((evt.getPropertyName().equalsIgnoreCase("start")) || (evt.getPropertyName().equalsIgnoreCase("delete"))) {
                String paramString = null;
                ParametersSubNode paramSubNode = (ParametersSubNode) ((DispersionModelingNode) evt.getSource()).getChildren().getNodeAt(0);
                paramString = String.valueOf(paramSubNode.getDataObj().getStart());
                for (int i = 1; i < ((DispersionModelingNode) evt.getSource()).getChildren().getNodesCount(); i++) {
                    paramSubNode = (ParametersSubNode) ((DispersionModelingNode) evt.getSource()).getChildren().getNodeAt(i);
                    paramString = paramString + ", " + String.valueOf(paramSubNode.getDataObj().getStart());
                }
                if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU)) {
                    model.getIrfparPanel().setDispmufun("poly");
                    model.getIrfparPanel().setParmu(paramString);
                } else {
                    if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU)) {
                        model.getIrfparPanel().setDisptaufun("poly");
                        model.getIrfparPanel().setPartau(paramString);
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("fixed")) {
                if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARMU)) {
                    model.getIrfparPanel().setParmufixed((Boolean) evt.getNewValue());
                } else {
                    if (evt.getOldValue().equals(EnumTypes.DispersionTypes.PARTAU)) {
                        model.getIrfparPanel().setPartaufixed((Boolean) evt.getNewValue());

                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")) {
            }
            return true;

        }
        if (evt.getSource().getClass().equals(WeightParametersNode.class)) {
            if (model.getWeightParPanel() == null) {
                model.setWeightParPanel(new WeightParPanelModel());
            }
            if (evt.getPropertyName().equalsIgnoreCase("Number of components")) {
                if ((Integer) evt.getNewValue() > (Integer) evt.getOldValue()) {
                    for (int i = 0; i < (Integer) evt.getNewValue() - (Integer) evt.getOldValue(); i++) {
                        model.getWeightParPanel().getWeightpar().add(new WeightPar());
                    }
                } else {
                    for (int i = 0; i < (Integer) evt.getOldValue() - (Integer) evt.getNewValue(); i++) {
                        model.getWeightParPanel().getWeightpar().remove(
                                model.getWeightParPanel().getWeightpar().size() - 1);
                    }
                }
            }
            if (evt.getPropertyName().equalsIgnoreCase("mainNodeDeleted")) {
                model.getWeightParPanel().getWeightpar().clear();
            }
            if (evt.getPropertyName().equalsIgnoreCase("weight")) {
                model.getWeightParPanel().getWeightpar().get((int) floor((Double) evt.getOldValue())).setWeight((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmin1")) {
                model.getWeightParPanel().getWeightpar().get((int) floor((Double) evt.getOldValue())).setMin1((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmin2")) {
                model.getWeightParPanel().getWeightpar().get((int) floor((Double) evt.getOldValue())).setMin2((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmax1")) {
                model.getWeightParPanel().getWeightpar().get((int) floor((Double) evt.getOldValue())).setMax1((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("setmax2")) {
                model.getWeightParPanel().getWeightpar().get((int) floor((Double) evt.getOldValue())).setMax2((Double) evt.getNewValue());
            }
            if (evt.getPropertyName().equalsIgnoreCase("delete")) {
                int index = (Integer) evt.getNewValue();
                model.getWeightParPanel().getWeightpar().remove(index);
            }
            return true;
        }
        return false;
    }

    private static void setIrfType(Dat model, PropertyChangeEvent evt, EnumTypes.IRFTypes type) {
        EnumTypes.IRFTypes irfType = type;
        switch (irfType) {
            case GAUSSIAN: {
                model.getIrfparPanel().setMirf(Boolean.FALSE);
                if (model.getIrfparPanel().getIrf() != null) {
                    model.getIrfparPanel().getIrf().clear();
                    model.getIrfparPanel().getFixed().clear();
                }
                for (int i = 0; i < 2; i++) {
                    model.getIrfparPanel().getIrf().add(
                            ((ParametersSubNode) ((IrfParametersNode) evt.getSource()).getChildren().getNodes()[i]).getDataObj().getStart());
                    model.getIrfparPanel().getFixed().add(
                            ((ParametersSubNode) ((IrfParametersNode) evt.getSource()).getChildren().getNodes()[i]).getDataObj().isFixed());
                }
                break;
            }
            case DOUBLE_GAUSSIAN: {
                model.getIrfparPanel().setMirf(Boolean.FALSE);
                if (model.getIrfparPanel().getIrf() != null) {
                    model.getIrfparPanel().getIrf().clear();
                    model.getIrfparPanel().getFixed().clear();
                }
                for (int i = 0; i < 4; i++) {
                    model.getIrfparPanel().getIrf().add(
                            ((ParametersSubNode) ((IrfParametersNode) evt.getSource()).getChildren().getNodes()[i]).getDataObj().getStart());
                    model.getIrfparPanel().getFixed().add(
                            ((ParametersSubNode) ((IrfParametersNode) evt.getSource()).getChildren().getNodes()[i]).getDataObj().isFixed());
                }
                break;
            }
            case MEASURED_IRF: {
                model.getIrfparPanel().setMirf(Boolean.TRUE);
                //todo finish measured IRF implementation
                break;
            }
        }

    }
}
