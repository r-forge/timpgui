/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.analysis.support;

import org.glotaran.core.main.structures.TimpResultDataset;

/**
 *
 * @author jsg210
 */
public class AnalysisHelper implements Runnable {

    private TimpResultDataset[] results = null;

    public AnalysisHelper() {
    }

    public TimpResultDataset[] getResults() {
        return results;
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
