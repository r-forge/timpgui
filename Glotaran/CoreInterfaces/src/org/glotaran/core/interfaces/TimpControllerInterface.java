/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.interfaces;

import Jama.Matrix;
import java.util.ArrayList;
import org.glotaran.core.models.structures.DatasetTimp;
import org.glotaran.core.models.structures.TimpResultDataset;
import org.glotaran.core.models.tgm.Tgm;

/**
 *
 * @author jsg210
 */
public interface TimpControllerInterface {
    public static final String NAME_OF_RESULT_OBJECT = "gtaFitResult";
    public final static String NAME_OF_DATASET = "gtaDataset";
    public final static String NAME_OF_MODEL = "gtaModel";
    public TimpResultDataset[] runAnalysis(DatasetTimp[] datasets, Tgm[] models, int iterations);
    public TimpResultDataset[] runAnalysis(DatasetTimp[] datasets, ArrayList<String> initModelCalls, String fitModelCall);
    public ArrayList<Matrix> doSingularValueDecomposition(Matrix matrix);
}
