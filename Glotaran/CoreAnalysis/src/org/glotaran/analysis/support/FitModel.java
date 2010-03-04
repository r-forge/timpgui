/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.analysis.support;

import org.glotaran.core.main.project.TGProject;
import org.glotaran.core.models.gta.GtaChangesModel;
import org.glotaran.core.models.gta.GtaLinkCLP;
import org.glotaran.core.models.gta.GtaModelDiffContainer;
import org.glotaran.core.models.gta.GtaModelDiffDO;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.tgm.Tgm;
import org.glotaran.tgmfilesupport.TgmDataObject;
import org.openide.util.Exceptions;

/**
 *
 * @author jsg210
 */
public class FitModel {

    public static String getModelDifferences(GtaModelDifferences modelDifferences) {
        String result = "";

        //TODO: implement LinkCLP
        //modelDifferences.getLinkCLP()
        if (modelDifferences != null) {
            //Fill in the "linkcpl" parameter
            for (GtaLinkCLP linkCLP : modelDifferences.getLinkCLP()) {
                result = result + getModelDiffsLinkCLP(linkCLP);

            }

            for (GtaModelDiffContainer diffContainer : modelDifferences.getDifferences()) {
                result = result + getModelDiffsFree(diffContainer);
            }

            //Fill in the "change" parameter
            for (GtaModelDiffContainer diffContainer : modelDifferences.getDifferences()) {
                result = result + getModelDiffsChange(diffContainer);
            }
        }
        if (!result.isEmpty()) {
            result = "modeldiffs = list(" + result + ")";
        }
        return result;
    }

    private static String getModelDiffsFree(GtaModelDiffContainer diffContainer) {
        String result = "";
        //Fill in the "free" parameter
        if (diffContainer != null) {
            for (int i = 0; i < diffContainer.getFree().size(); i++) {
                GtaModelDiffDO modelDiffDO = diffContainer.getFree().get(i);
                if (modelDiffDO != null) {
                    if (i > 0 && i < diffContainer.getFree().size()) {
                        result = result + ",";
                    } else {
                        result = "free = list(";
                    }
                    result = result
                            + "list(what = \"" + modelDiffDO.getWhat() + "\","
                            + "ind = " + modelDiffDO.getIndex() + ","
                            + "dataset = " + modelDiffDO.getDataset() + ","
                            + "start = " + modelDiffDO.getStart() + ")";
                }
                result = result + ")";
            }
        }
        return result;
    }

    private static String getModelDiffsLinkCLP(GtaLinkCLP linkCLP) {
        String result = "";
        if (linkCLP != null) {
            result = result + "linkclp = list(";
            result = result + ")";
        }
        return result;
    }

    private static String getModelDiffsChange(GtaModelDiffContainer diffContainer) {
        String result = "";

         TgmDataObject tgmDO;
        Tgm model = null;
        if (diffContainer != null) {
            if (diffContainer.getChanges() != null) {
                GtaChangesModel changes = diffContainer.getChanges();
                String fileName = changes.getFilename();
                String pathName = changes.getPath();
            }

        }
        
        
        return result;
    }



}
