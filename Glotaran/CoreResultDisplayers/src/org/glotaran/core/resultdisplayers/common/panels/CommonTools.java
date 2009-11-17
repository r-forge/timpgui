/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.glotaran.core.resultdisplayers.common.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import static java.lang.Math.ceil;

/**
 *
 * @author slapten
 */
public class CommonTools {
    private static final int CHART_SIZE = 200;
    private static final int LAYOUT_GAP = 2;
    private static final int REPORT_PANEL_DEFAULT_WIDTH = 900;
    private static final int REPORT_PANEL_DEFAULT_HEIGHT = 810;

    public static void checkPanelSize(JPanel panelToResize, int numSelTraces) {
        int rowNum = (int)ceil(numSelTraces/4);
        if (rowNum > 4){
            panelToResize.setPreferredSize(new Dimension(REPORT_PANEL_DEFAULT_WIDTH,rowNum*CHART_SIZE+LAYOUT_GAP*(rowNum+1)));
        }
        GridLayout gl = (GridLayout)panelToResize.getLayout();
        if (numSelTraces/4>=gl.getRows()){
            panelToResize.setLayout(new GridLayout(rowNum, 4,LAYOUT_GAP,LAYOUT_GAP));
        }
    }

    public static void restorePanelSize(JPanel panelToResize) {
        panelToResize.setPreferredSize(new Dimension(REPORT_PANEL_DEFAULT_WIDTH, REPORT_PANEL_DEFAULT_HEIGHT));
        panelToResize.setLayout(new GridLayout(2, 2));
    }
}
