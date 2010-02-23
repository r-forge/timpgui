/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.analysis;

import org.glotaran.core.main.interfaces.AnalysisInterface;
import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;

/**
 *
 * @author jsg210
 */
public class StartAnalysis implements AnalysisInterface {

    public StartAnalysis() {
        //TODO: implement constructor
    }

    public void runAnalysis(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences) {
        //TODO: load the model to be analyzed from disk
        //TODO: load the datasets to be used into memory
        //TODO: create the initModel call
        //TODO: parse the model differences
        //TODO:
        System.out.println("test");
    }

}
