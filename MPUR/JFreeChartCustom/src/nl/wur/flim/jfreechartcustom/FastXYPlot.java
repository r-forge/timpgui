/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.flim.jfreechartcustom;

/**
 *
 * @author jsg210
 */

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.general.*;
import org.jfree.data.xy.*;

public class FastXYPlot extends XYPlot {

    public FastXYPlot() {
        this(null, null, null, null);
    }

    public FastXYPlot(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
        super(dataset, domainAxis, rangeAxis, renderer);
    }

    public void renderFast(ValueAxis xAxis, ValueAxis yAxis, XYDataset dataset, int series, XYItemRenderer renderer,
                           XYItemRendererState state, int pass, Graphics2D g2, Rectangle2D dataArea,
                           PlotRenderingInfo info, CrosshairState crosshairState) {

        ArrayList<Double> xValues = new ArrayList<Double> ();
        int count = dataset.getItemCount(series);
        for (int item = 0; item < count; item++) {
            xValues.add(dataset.getXValue(series, item));
        }

        int firstItem = findItemIndex(xValues, xAxis.getLowerBound(), count);
        int lastItem = findItemIndex(xValues, xAxis.getUpperBound(), count);

        long lastRendered = -1;
        for (int item = firstItem; item < lastItem; item++) {

            double x = dataset.getXValue(series, item);
            double xx = xAxis.valueToJava2D(x, dataArea, getDomainAxisEdge());

            long candidateToRender = (long) ( (xx / 10) * 100);
            if (candidateToRender != lastRendered) {
                renderer.drawItem(g2, state, dataArea, info, this, xAxis, yAxis, dataset, series, item, crosshairState,
                                  pass);
                lastRendered = candidateToRender;
            }
        }
    }


    public boolean render(Graphics2D g2, Rectangle2D dataArea, int index, PlotRenderingInfo info,
                          CrosshairState crosshairState) {

        boolean foundData = false;
        XYDataset dataset = getDataset(index);
        if (!DatasetUtilities.isEmptyOrNull(dataset)) {
            foundData = true;
            ValueAxis xAxis = getDomainAxisForDataset(index);
            ValueAxis yAxis = getRangeAxisForDataset(index);
            XYItemRenderer renderer = getRenderer(index);
            if (renderer == null) {
                renderer = getRenderer();
            }

            XYItemRendererState state = renderer.initialise(g2, dataArea, this, dataset, info);
            int passCount = renderer.getPassCount();

            SeriesRenderingOrder seriesOrder = getSeriesRenderingOrder();

            if (seriesOrder == SeriesRenderingOrder.REVERSE) {
                //render series in reverse order
                for (int pass = 0; pass < passCount; pass++) {
                    int seriesCount = dataset.getSeriesCount();
                    for (int series = seriesCount - 1; series >= 0; series--) {
                        renderFast(xAxis, yAxis, dataset, series, renderer, state, pass, g2, dataArea, info,
                                   crosshairState);
                    }
                }
            } else {
                //render series in forward order
                for (int pass = 0; pass < passCount; pass++) {
                    int seriesCount = dataset.getSeriesCount();
                    for (int series = 0; series < seriesCount; series++) {
                        renderFast(xAxis, yAxis, dataset, series, renderer, state, pass, g2, dataArea, info,
                                   crosshairState);
                    }
                }
            }
        }
        return foundData;
    }

    private int findItemIndex(ArrayList<Double> values, double value, int maxCount) {
        int itemIndex = Collections.binarySearch(values, value);
        if (itemIndex < 0) {
            itemIndex = -itemIndex - 1;
        }
        itemIndex = Math.max(0, Math.min(itemIndex, maxCount - 1));
        return itemIndex;
    }
}  
