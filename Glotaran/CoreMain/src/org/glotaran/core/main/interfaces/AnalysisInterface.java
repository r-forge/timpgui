/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.main.interfaces;

import org.glotaran.core.models.gta.GtaDatasetContainer;
import org.glotaran.core.models.gta.GtaModelDifferences;
import org.glotaran.core.models.gta.GtaModelReference;
import org.glotaran.core.models.gta.GtaOutput;

/**
 *
 * @author jsg210
 */
public interface AnalysisInterface {
    public void runAnalysis(GtaOutput gtaOutput, GtaDatasetContainer gtaDatasetContainer, GtaModelReference gtaModelReference, GtaModelDifferences gtaModelDifferences);
}
