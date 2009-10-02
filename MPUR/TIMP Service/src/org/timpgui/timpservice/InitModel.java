/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timpgui.timpservice;

import nl.vu.nat.tgmodels.tgm.CohspecPanelModel;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.FlimPanelModel;
import nl.vu.nat.tgmodels.tgm.IrfparPanelModel;
import nl.vu.nat.tgmodels.tgm.KMatrixPanelModel;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmodels.tgm.WeightPar;
import nl.vu.nat.tgmodels.tgm.WeightParPanelModel;

/**
 *
 * @author Katharine Mullen
 * @author Joris Snellenburg
 */
public class InitModel {


// Public classes
    public static String parseModel(Tgm tgm) {
        String initModel = "initModel(";
        String tempStr = null;

        tempStr = get_mod_type(tgm);
        initModel = initModel.concat(tempStr + ",");

        tempStr = get_kinpar(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_weightpar(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_kmatrix(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_irf(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_measured_irf(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_parmu(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

//       tempStr = get_dispmufun(tgm);
//       if (tempStr!=null)
//           initModel=initModel.concat(tempStr+",");
//       tempStr = get_partau(tgm);
//       if (tempStr!=null)
//           initModel=initModel.concat(tempStr+",");
//       tempStr = get_disptaufun(tgm);
//       if (tempStr!=null)
//           initModel=initModel.concat(tempStr+",");

        tempStr = get_fixed(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }


        tempStr = get_positivepar(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_cohArtefact(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_clp0(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }

        tempStr = get_clpequspec(tgm);
        if (tempStr != null) {
            initModel = initModel.concat(tempStr + ",");
        }


        tempStr = get_seqmod(tgm);
        initModel = initModel.concat(tempStr + ")");


        return initModel;
    }

    private static String get_cohArtefact(Tgm tgm) {
        String cohSpecStr = null;
        CohspecPanelModel cohspecPanel = tgm.getDat().getCohspecPanel();
        String typeCoh = cohspecPanel.getCohspec().getType();
        cohspecPanel.getCoh();
        if (typeCoh != null) {
            cohSpecStr = "cohspec = list(type = \"" + typeCoh + "\") ";
        }
        return cohSpecStr;
    }

    // Private classes
    private static String get_kmatrix(Tgm tgm) {
        String kMatrixCall = null;

        KMatrixPanelModel kMatrix = tgm.getDat().getKMatrixPanel();

        int matrixSize = kMatrix.getJVector().getVector().size();
        if (matrixSize > 0) {
            kMatrixCall = "kmat = array(c(";
            kMatrixCall = kMatrixCall.concat(String.valueOf(kMatrix.getKMatrix().getData().get(0).getRow().get(0)));
            for (int j = 1; j < matrixSize; j++) {
                kMatrixCall = kMatrixCall.concat("," + String.valueOf(kMatrix.getKMatrix().getData().get(j).getRow().get(0)));
            }
            for (int i = 1; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    kMatrixCall = kMatrixCall.concat("," + String.valueOf(kMatrix.getKMatrix().getData().get(j).getRow().get(i)));
                }
            }
            //TODO: fix relations matrix
//            for (int i = 0; i < matrixSize; i++) {
//                for (int j = 0; j < matrixSize; j++) {
//                    kMatrixCall = kMatrixCall.concat("," + String.valueOf(kMatrix.getK2Matrix().getData().get(j).getRow().get(i)));
//                }
//            }

            kMatrixCall = kMatrixCall.concat("), dim = c(");
            kMatrixCall = kMatrixCall.concat(String.valueOf(matrixSize) + "," + String.valueOf(matrixSize) + ",2))");
            kMatrixCall = kMatrixCall.concat(", jvec = c(");
            //TODO: verify jVector
            kMatrixCall = kMatrixCall.concat(String.valueOf(kMatrix.getJVector().getVector().get(0)));
            for (int j = 1; j < matrixSize; j++) {
                kMatrixCall = kMatrixCall.concat("," + String.valueOf(kMatrix.getJVector().getVector().get(j)));
            }
            kMatrixCall = kMatrixCall.concat(")");
        }
        return kMatrixCall;
    }

    private static String get_kinpar(Tgm tgm) {
        KinparPanelModel kinparPanelModel = tgm.getDat().getKinparPanel();
        String kinpar = null;
        int size = kinparPanelModel.getKinpar().size();
        if (size > 0) {
            kinpar = "kinpar = c(";
            double k;
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    kinpar = kinpar + ",";
                }
                k = kinparPanelModel.getKinpar().get(i).getStart();
                kinpar = kinpar + Double.toString(k);
            }
            kinpar = kinpar + ")";
        }
        return kinpar;
    }

    private static String get_weightpar(Tgm tgm) {
        WeightParPanelModel weightParPanelModel = tgm.getDat().getWeightParPanel();
        String weightpar = null;
        int size = weightParPanelModel.getWeightpar().size();
        if (size > 0) {
            weightpar = "weightpar = list(";
            for (int i = 0; i < size; i++) {
                if ((i>0)&&(i<size))
                    weightpar = weightpar + ",";
                weightpar = weightpar + "c(";
                WeightPar wp = weightParPanelModel.getWeightpar().get(i);
                Double[] temparray = {wp.getMin1(), wp.getMax1(), wp.getMin2(), wp.getMax2(), wp.getWeight()};
                for (int j = 0; j < temparray.length; j++) {
                    if (temparray[j] != null && !temparray[j].isNaN()) {
                        weightpar = weightpar + String.valueOf(temparray[j]);
                    } else {
                        weightpar = weightpar + "NA";
                    }
                    if (j < temparray.length - 1) {
                        weightpar = weightpar + ",";
                    }
                }
                weightpar = weightpar + ")";
//                if (i < size - 1) {
//                    weightpar = weightpar + ",";
//                }
            }
            weightpar = weightpar + ")";
        }
        return weightpar;
    }

    private static String get_measured_irf(Tgm tgm) {
        String meaIrfString = null;
        FlimPanelModel flimPanel = tgm.getDat().getFlimPanel();
        //System.out.println("DDD"+Current.GetcurrMIRF());
        if (flimPanel.isMirf()) {
            int conv = flimPanel.getConvalg();
            meaIrfString = "measured_irf= vector( "; //+ Current.GetcurrMIRF();
            meaIrfString = meaIrfString + " ), convalg= ";
            meaIrfString = meaIrfString.concat(String.valueOf(conv));
            if (conv == 3) {
                meaIrfString = meaIrfString.concat(", reftau = ");
                meaIrfString = meaIrfString.concat(String.valueOf(flimPanel.getReftau()));
            }
        }
        return meaIrfString;
    }

    private static String get_irf(Tgm tgm) {
        String irfStr = null;
        IrfparPanelModel irfPanel = tgm.getDat().getIrfparPanel();
        int count = 0;
        if (irfPanel.getIrf().size()>0){
            irfStr = "irfpar = ";
            for (int i = 0; i < irfPanel.getIrf().size(); i++) {
                if (count > 0) {
                    irfStr = irfStr + ",";
                } else {
                    irfStr = irfStr + "c(";
                }
                irfStr = irfStr + Double.toString(irfPanel.getIrf().get(i));
                count++;
            }
            irfStr = irfStr + ")";
        
            if (irfPanel.getIrf().size()==4)
                irfStr = irfStr+", doublegaus = TRUE";
            if (irfPanel.isBacksweepEnabled()){
                irfStr = irfStr+", streak = TRUE, streakT = "+String.valueOf(irfPanel.getBacksweepPeriod());
            }
        }
        return irfStr;
    }
//
//   private static String get_dispmufun(Tgm tgm) {
//       String dispStr = null;
//       IrfparPanelModel irfPanel = tgm.getDat().getIrfparPanel();
//
//       if(irfPanel.getDispmufun().equals("poly")) {
//            dispStr = "dispmufun = \"poly\"";
//       }
//       else if (irfPanel.getDispmufun().equals("discrete")) {
//            dispStr = "dispmufun = \"discrete\"";
//       }
//
//       return dispStr;
//   }
//
//   private static String get_disptaufun(Tgm tgm) {
//       String dispStr = null;
//       IrfparPanelModel irfPanel = tgm.getDat().getIrfparPanel();
//
//       if(irfPanel.getDispmufun().equals("poly")) {
//            dispStr = "disptaufun = \"poly\"";
//       }
//       else if (irfPanel.getDispmufun().equals("discrete")) {
//            dispStr = "disptaufun = \"discrete\"";
//       }
//       return dispStr;
//   }

    private static String get_parmu(Tgm tgm) {
        String parmuStr = null;
        IrfparPanelModel irfPanel = tgm.getDat().getIrfparPanel();

        if (irfPanel.getLamda() > 0) {
            parmuStr = "lambdac = " + String.valueOf(irfPanel.getLamda());
        }

        if (irfPanel.getParmu().trim().length() != 0) {
            if (parmuStr == null) {
                parmuStr = "parmu = list(";
            } else {
                parmuStr = parmuStr.concat(", parmu = list(");
            }

            parmuStr = parmuStr + "c(" + irfPanel.getParmu() + "))";
        }

        if (parmuStr != null) {
            if (irfPanel.getDispmufun().equals("poly")) {
                parmuStr = parmuStr.concat(", dispmufun = \"poly\"");
            } else if (irfPanel.getDispmufun().equals("discrete")) {
                parmuStr = parmuStr.concat(", dispmufun = \"discrete\"");
            }
        } else {
            if (irfPanel.getDispmufun().equals("poly")) {
                parmuStr = "dispmufun = \"poly\"";
            } else if (irfPanel.getDispmufun().equals("discrete")) {
                parmuStr = "dispmufun = \"discrete\"";
            }
        }

        if (irfPanel.getPartau().trim().length() != 0) {
            if (parmuStr == null) {
                parmuStr = "partau= list(";
            } else {
                parmuStr = parmuStr.concat(", partau= list(");
            }

            parmuStr = parmuStr + "c(" + irfPanel.getPartau() + "))";
        }

        if (parmuStr != null) {
            if (irfPanel.getDisptaufun().equals("poly")) {
                parmuStr = parmuStr.concat(", disptaufun = \"poly\"");
            } else if (irfPanel.getDisptaufun().equals("discrete")) {
                parmuStr = parmuStr.concat(", disptaufun = \"discrete\"");
            }
        } else {
            if (irfPanel.getDisptaufun().equals("poly")) {
                parmuStr = "disptaufun = \"poly\"";
            } else if (irfPanel.getDisptaufun().equals("discrete")) {
                parmuStr = "disptaufun = \"discrete\"";
            }
        }
        return parmuStr;
    }

    private static String get_fixed(Tgm tgm) {
        String fixedStr = null;
        KinparPanelModel kinparPanelModel = tgm.getDat().getKinparPanel();
        int count = 0;
        for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
            if (kinparPanelModel.getKinpar().get(i).isFixed()) {
                if (count > 0) {
                    fixedStr = fixedStr + ",";
                } else {
                    fixedStr = "fixed = list(kinpar=c(";
                }
                fixedStr = fixedStr + String.valueOf(i + 1);
                count++;
            }
        }
        if (count > 0) {
            fixedStr = fixedStr + ")";
        }
        count = 0;
        IrfparPanelModel irfPanel = tgm.getDat().getIrfparPanel();
        for (int i = 0; i < irfPanel.getFixed().size(); i++) {
            if (irfPanel.getFixed().get(i)) {
                if (count > 0) {
                    fixedStr = fixedStr + ",";
                } else {
                    if (fixedStr!= null)
                        fixedStr = fixedStr + ", irfpar=c(";
                    else
                        fixedStr = "fixed = list(irfpar=c(";
                }
                fixedStr = fixedStr + String.valueOf(i + 1);
                count++;
            }
        }
        if (count > 0) {
            fixedStr = fixedStr + ")";
        }

        count = 0;
        KMatrixPanelModel kMatrix = tgm.getDat().getKMatrixPanel();
        for (int i = 0; i < kMatrix.getJVector().getFixed().size(); i++) {
            if (kMatrix.getJVector().getFixed().get(i)) {
                if (count > 0) {
                    fixedStr = fixedStr + ",";
                } else {
                    if (fixedStr!= null)
                        fixedStr = fixedStr + ", jvec=c(";
                    else
                        fixedStr = "fixed = list(jvec=c(";
                }
                fixedStr = fixedStr + String.valueOf(i + 1);
                count++;
            }
        }
        if (count > 0) {
            fixedStr = fixedStr + ")";
        }
        // TODO: add additional paramters for fixed here:

        // This closes the "fixed" argument
        if (fixedStr!= null)
            fixedStr = fixedStr + ")";

        return fixedStr;
    }

//   private static String get_constrained(Tgm tgm) {
//       Dat dat = tgm.getDat();
//       KinparPanelModel kinparPanelModel = dat.getKinparPanel();
//       String constrained = "constrained = list(";
//       double cc;
//       int count=0;
//       for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
//           if(kinparPanelModel.getKinpar().get(i).isConstrained()){
//               if(kinparPanelModel.getKinpar().get(i).getMin() != null) {
//                   if(count > 0)
//                       constrained = constrained + ",";
//                   cc = kinparPanelModel.getKinpar().get(i).getMin();
//                   constrained = constrained + "list(what=\"kinpar\", ind = "+
//                                  Integer.toString(i+1) +", low=" + cc + ")";
//                   count++;
//               }
//               else if(kinparPanelModel.getKinpar().get(i).getMax() != null) {
//                    if(count > 0)
//                       constrained = constrained + ",";
//                    cc = kinparPanelModel.getKinpar().get(i).getMax();
//                    constrained = constrained + "list(what=\"kinpar\", ind = "+
//                                  Integer.toString(i+1) +", high=" + cc + ")";
//                    count++;
//               }
//           }
//       }
//       constrained = constrained + ")";
//        // need to fill in other parameters here, once we have panels for them
//       return constrained;
//   }
    private static String get_seqmod(Tgm tgm) {
        KinparPanelModel kinparPanelModel = tgm.getDat().getKinparPanel();
        if (kinparPanelModel.isSeqmod()) {
            return "seqmod = TRUE";
        } else {
            return "seqmod = FALSE";
        }
    }

    private static String get_positivepar(Tgm tgm) {
        KinparPanelModel kinparPanelModel = tgm.getDat().getKinparPanel();
        int count = 0;
        String positivepar = null;
        if (kinparPanelModel.isPositivepar()) {
            count++;
            positivepar = "positivepar = c(\"kinpar\"";
        }
        // need to fill in other parameters here, once we have panels for them
        //if(count>0)
        //        positivepar = positivepar + ",";
        if (count < 1) {
            positivepar = "positivepar=vector()";
        } else {
            positivepar = positivepar + ")";
        }

        return positivepar;
    }

    private static String get_clp0(Tgm tgm) {
        String clp0Call = null;
        KMatrixPanelModel kMatrix = tgm.getDat().getKMatrixPanel();
        int size = kMatrix.getSpectralContraints().getMax().size();
        if (size > 0) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                Double min = kMatrix.getSpectralContraints().getMin().get(i);
                Double max = kMatrix.getSpectralContraints().getMax().get(i);
                if (min!=null && max!=null) {
                    if (count==0)
                        clp0Call = "clp0 = list(";
                    else
                        clp0Call=clp0Call+",";

                    clp0Call = clp0Call + "list(" + Double.valueOf(min) + "," +
                            Double.valueOf(max) + "," + (i+1) + ")";
                    count++;
                }
            }

            if (tgm.getDat().getCohspecPanel().isClp0Enabled()){
                if (clp0Call!=null)
                    clp0Call = clp0Call+",";
                else
                    clp0Call = "clp0 = list(";

                clp0Call = clp0Call + "list(" + tgm.getDat().getCohspecPanel().getClp0Min() + "," +
                             tgm.getDat().getCohspecPanel().getClp0Min() + "," +
                             (tgm.getDat().getKinparPanel().getKinpar().size()+1) + ")";
            }
            if (clp0Call!=null)
                clp0Call = clp0Call + ")";
        }
        return clp0Call;
    }

    private static String get_mod_type(Tgm tgm) {
        String mod_type = "mod_type = \"" + tgm.getDat().getModType() + "\"";
        return mod_type;
    }

    private static String get_clpequspec(Tgm tgm) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
