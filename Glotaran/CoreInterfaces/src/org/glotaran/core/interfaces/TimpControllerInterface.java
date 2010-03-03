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
    public TimpResultDataset[] runAnalysis(DatasetTimp[] datasets, Tgm[] models, int iterations);
    public ArrayList<Matrix> doSingularValueDecomposition(Matrix matrix);
    public String getFitModelCall();
    public ArrayList<String> getInitModelCalls();

}