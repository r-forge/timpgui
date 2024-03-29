package org.timpgui.timpservice;

import org.timpgui.timpinterface.*;
import java.util.ArrayList;
import nl.vu.nat.tgmodels.tgm.Dat;
import nl.vu.nat.tgmodels.tgm.KinparPanelModel;
import nl.vu.nat.tgmodels.tgm.Tgm;
import nl.vu.nat.tgmodels.tgo.AlgorithmPanelElements;
import nl.vu.nat.tgmodels.tgo.IterPanelElements;
import nl.vu.nat.tgmodels.tgo.OptPanelElements;
import nl.vu.nat.tgmodels.tgo.OutputPanelElements;
import nl.vu.nat.tgmodels.tgo.Tgo;

/**
 *
 * @author kate
 */
public class FitModel {

    public static String get_opt(Tgo tgo, Tgm tgm) {
        Dat dat = tgm.getDat();
        String opt = "";
        OptPanelElements mm = tgo.getOpt();
        OutputPanelElements outputP = mm.getOutputpanel();
        if (dat.getModType().equals("kin")) {
            opt = "kinopt(";
        } else if (dat.getModType().equals("spec")) {
            opt = "specopt(";
        } else if (dat.getModType().equals("mass")) {
            opt = "massopt(";
        // in all below cases need to make plots in TIMP
        }
        if (outputP.isWritefit() || outputP.isWriteclperr() ||
                outputP.isWritecon() || outputP.isWritedata() ||
                outputP.isWritefitivo() || outputP.isWritespec() ||
                outputP.isWritenormspec() || get_output(tgo).length() > 0) {
            opt = opt + "plot = TRUE,";
        } else {
            opt = opt + "plot = FALSE,";
        }
        if (get_output(tgo).length() > 0) {
            String dd;
            opt = opt + get_output(tgo) + ",";
            //TODO: "temp" was Current.getNameOfCurrentModel()
            opt = opt + "makeps = \"" + "res" + "temp" + "\",";  // fix
            if (outputP.getOutput().getFormat().compareTo("pdf") == 0) {
                dd = "pdf";
            } else {
                dd = "postscript";
            }
            //TODO: execute this command somewhere else
           // execute("options(device=\"" + dd + "\")");
        }
        opt = opt + "sumnls=FALSE," +
                get_writefit(tgo) + "," +
                get_writeclperr(tgo) + "," +
                get_writecon(tgo) + "," +
                get_writespec(tgo) + "," +
                get_writedata(tgo) + "," +
                get_writefitivo(tgo) + "," +
                get_writenormspec(tgo) + "," +
                get_title(tgo) + "," +
                get_iter(tgo) + "," +
                get_FLIM(tgo) + "," +
                get_algorithm(tgo) + "," +
                get_nnls(tgo) + "," +
                get_stderrclp(tgo) + ")";
        return opt;
    }

//    public static String get_modeldiffs(Tgm tgm, int numDat) {
//        Dat dat = tgm.getDat();
//        KinparPanelModel kinparPanelModel = dat.getKinparPanel();
//        String free = "";
//        int count = 0;
//        for (int i = 0; i < kinparPanelModel.getKinpar().size(); i++) {
//            if (kinparPanelModel.getKinpar().get(i).isModeldiffsFree()) {
//                if (count > 0) {
//                    free = free + ",";
//                } else {
//                    free = free + "free=list(";
//                }
//                for (int j = 0; j < numDat; j++) {
//                    free = free + "list(what = \"kinpar\", ind=" + Integer.toString(i + 1) + ", dataset=" +
//                            Integer.toString(j + 1) + ",start=" +
//                            Double.toString(kinparPanelModel.getKinpar().get(i).getStart()) + ")";
//                    count++;
//                }
//            }
//        }
//        if (count > 0) {
//            free = free + ")";
//        }
//        String modeldiffs = "list(" + free + ")";
//        return modeldiffs;
//    }

    private static String get_stderrclp(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        AlgorithmPanelElements outputP = opt.getAlgorithmPanel();
        String std = "stderrclp = ";
        if (outputP.isStderrclp()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;
    }

    private static String get_nnls(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        AlgorithmPanelElements outputP = opt.getAlgorithmPanel();
        String std = "nnls = ";
        if (outputP.isNnls()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;
    }

    private static String get_algorithm(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        AlgorithmPanelElements outputP = opt.getAlgorithmPanel();
        String std = "algorithm = \"" + outputP.getAlgorithm() + "\"";
        return std;
    }

    private static String get_writeclperr(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writeclperr = ";
        if (outputP.isWriteclperr()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writecon(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writecon = ";
        if (outputP.isWritecon()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writespec(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writespec = ";
        if (outputP.isWritespec()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writefitivo(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writefitivo = ";
        if (outputP.isWritefitivo()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writedata(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writedata = ";
        if (outputP.isWritedata()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writenormspec(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writenormspec = ";
        if (outputP.isWritenormspec()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_output(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "";
        if (outputP.getOutput().isEnabled()) {
            if (outputP.getOutput().getFormat().compareTo("pdf") == 0) {
                std = "output = \"pdf\"";
            } else {
                std = "output = \"ps\"";
            }
        }
        return std;

    }

    private static String get_plot(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "plot = ";
        if (outputP.isPlot()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_writefit(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        OutputPanelElements outputP = opt.getOutputpanel();
        String std = "writefit = ";
        if (outputP.isWritefit()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_FLIM(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        IterPanelElements outputP = opt.getIterPanel();
        String std = "FLIM = ";
        if (outputP.isFLIM()) {
            std = std + "TRUE";
        } else {
            std = std + "FALSE";
        }
        return std;

    }

    private static String get_iter(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        IterPanelElements outputP = opt.getIterPanel();
        String std = "iter = " + Integer.toString(outputP.getIter());
        return std;
    }

    private static String get_title(Tgo tgo) {
        OptPanelElements opt = tgo.getOpt();
        IterPanelElements outputP = opt.getIterPanel();
        String std = "title = \"" + opt.getOptName() + "\"";
        return std;
    }


  

}








   