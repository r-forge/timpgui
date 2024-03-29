/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.jfreechartcustom;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.chart.util.LogFormat;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * A numerical axis that uses a logarithmic scale.  The class is an
 * alternative to the {@link LogarithmicAxis} class.
 *
 * @since 1.0.7
 */
public class LinLogAxis extends ValueAxis {

    private final static long serialVersionUID = 1L;
    /** The logarithm base. */
    private double base = 10.0;
    /** The logarithm of the base value - cached for performance. */
    private double baseLog = Math.log(10.0);
    /**  The smallest value permitted on the axis. */
    private double smallestValue = 1E-100;
    /** The current tick unit. */
    private NumberTickUnit tickUnit;
    /** The override number format. */
    private NumberFormat numberFormatOverride;
    /** The number at which the axis goes from linear to logarithmic*/
    private double leftLinearBoundValue, rightLinearBoundValue, linearRangeScalingFactor;
    private final double DEFAULT_LINEAR_RANGE_SCALING_VALUE = 0.2;
    private final double DEFAULT_LEFT_LINEAR_BOUND_VALUE = Double.NEGATIVE_INFINITY;
    private final double DEFAULT_RIGHT_LINEAR_BOUND_VALUE = Double.POSITIVE_INFINITY;

    /**
     * Creates a new <code>LogAxis</code> with no label.
     */
    public LinLogAxis() {
        this(null);
    }

    /**
     * Creates a new <code>LogAxis</code> with the given label.
     *
     * @param label  the axis label (<code>null</code> permitted).
     */
    public LinLogAxis(String label) {
        super(label, createLogTickUnits(Locale.getDefault()));
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0, new DecimalFormat("0.#"), 9);
        this.leftLinearBoundValue = DEFAULT_LEFT_LINEAR_BOUND_VALUE;
        this.rightLinearBoundValue = DEFAULT_RIGHT_LINEAR_BOUND_VALUE;
        this.linearRangeScalingFactor = DEFAULT_LINEAR_RANGE_SCALING_VALUE;
    }

    /**
     * Creates a new <code>LogAxis</code> with the given label.
     *
     * @param label  the axis label (<code>null</code> permitted).
     * @param linearBoundValue number at which the axis goes from linear to logarithmic
     * (<code>null</code> permitted).
     */
    public LinLogAxis(String label, double linearBoundValue) {
        super(label, createLogTickUnits(Locale.getDefault()));
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0, new DecimalFormat("0.#"), 9);
        this.leftLinearBoundValue = linearBoundValue;
        this.rightLinearBoundValue = -linearBoundValue;
        this.linearRangeScalingFactor = DEFAULT_LINEAR_RANGE_SCALING_VALUE;
    }

    /**
     * Creates a new <code>LogAxis</code> with the given label.
     *
     * @param label  the axis label (<code>null</code> permitted).
     * @param rightLinearBoundValue number at which the axis goes from linear to logarithmic
     * (<code>null</code> permitted).
     * @param leftLinearBoundValue  number at which the axis goes from logarithmic to linear
     */
    public LinLogAxis(String label, double rightLinearBoundValue, double leftLinearBoundValue) {
        super(label, createLogTickUnits(Locale.getDefault()));
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0, new DecimalFormat("0.#"), 9);
        this.leftLinearBoundValue = leftLinearBoundValue;
        this.rightLinearBoundValue = rightLinearBoundValue;
        this.linearRangeScalingFactor = DEFAULT_LINEAR_RANGE_SCALING_VALUE;
    }

    /**
     * Creates a new <code>LogAxis</code> with the given label.
     *
     * @param label  the axis label (<code>null</code> permitted).
     * @param rightLinearBoundValue number at which the axis goes from linear to logarithmic
     * (<code>null</code> permitted).
     * @param leftLinearBoundValue  number at which the axis goes from logarithmic to linear
     * @param linearRangeScalingFactor value between 0 and 1 to signify the scale of the linear part of the axis with respect to the logarithmic part, the default is 0.2 or 20% of the total axis is linear
     */
    public LinLogAxis(String label, double rightLinearBoundValue, double leftLinearBoundValue, double linearRangeScalingFactor) {
        super(label, createLogTickUnits(Locale.getDefault()));
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0, new DecimalFormat("0.#"), 9);
        this.leftLinearBoundValue = leftLinearBoundValue;
        this.rightLinearBoundValue = rightLinearBoundValue;
        if (linearRangeScalingFactor >= 0 && linearRangeScalingFactor <= 1) {
            this.linearRangeScalingFactor = linearRangeScalingFactor;
        } else {
            this.linearRangeScalingFactor = DEFAULT_LINEAR_RANGE_SCALING_VALUE;
        }
    }

    /**
     * Returns the base for the logarithm calculation.
     *
     * @return The base for the logarithm calculation.
     *
     * @see #setBase(double)
     */
    public double getBase() {
        return this.base;
    }

    /**
     * Sets the base for the logarithm calculation and sends an
     * {@link AxisChangeEvent} to all registered listeners.
     *
     * @param base  the base value (must be > 1.0).
     *
     * @see #getBase()
     */
    public void setBase(double base) {
        if (base <= 1.0) {
            throw new IllegalArgumentException("Requires 'base' > 1.0.");
        }
        this.base = base;
        this.baseLog = Math.log(base);
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Returns the smallest value represented by the axis.
     *
     * @return The smallest value represented by the axis.
     *
     * @see #setSmallestValue(double)
     */
    public double getSmallestValue() {
        return this.smallestValue;
    }

    /**
     * Sets the smallest value represented by the axis and sends an
     * {@link AxisChangeEvent} to all registered listeners.
     *
     * @param value  the value.
     *
     * @see #getSmallestValue()
     */
    public void setSmallestValue(double value) {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Requires 'value' > 0.0.");
        }
        this.smallestValue = value;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Returns the current tick unit.
     *
     * @return The current tick unit.
     *
     * @see #setTickUnit(NumberTickUnit)
     */
    public NumberTickUnit getTickUnit() {
        return this.tickUnit;
    }

    /**
     * Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to
     * all registered listeners.  A side effect of calling this method is that
     * the "auto-select" feature for tick units is switched off (you can
     * restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}
     * method).
     *
     * @param unit  the new tick unit (<code>null</code> not permitted).
     *
     * @see #getTickUnit()
     */
    public void setTickUnit(NumberTickUnit unit) {
        // defer argument checking...
        setTickUnit(unit, true, true);
    }

    /**
     * Sets the tick unit for the axis and, if requested, sends an
     * {@link AxisChangeEvent} to all registered listeners.  In addition, an
     * option is provided to turn off the "auto-select" feature for tick units
     * (you can restore it using the
     * {@link ValueAxis#setAutoTickUnitSelection(boolean)} method).
     *
     * @param unit  the new tick unit (<code>null</code> not permitted).
     * @param notify  notify listeners?
     * @param turnOffAutoSelect  turn off the auto-tick selection?
     *
     * @see #getTickUnit()
     */
    public void setTickUnit(NumberTickUnit unit, boolean notify,
            boolean turnOffAutoSelect) {

        if (unit == null) {
            throw new IllegalArgumentException("Null 'unit' argument.");
        }
        this.tickUnit = unit;
        if (turnOffAutoSelect) {
            setAutoTickUnitSelection(false, false);
        }
        if (notify) {
            notifyListeners(new AxisChangeEvent(this));
        }

    }

    /**
     * Returns the number format override.  If this is non-null, then it will
     * be used to format the numbers on the axis.
     *
     * @return The number formatter (possibly <code>null</code>).
     *
     * @see #setNumberFormatOverride(NumberFormat)
     */
    public NumberFormat getNumberFormatOverride() {
        return this.numberFormatOverride;
    }

    /**
     * Sets the number format override.  If this is non-null, then it will be
     * used to format the numbers on the axis.
     *
     * @param formatter  the number formatter (<code>null</code> permitted).
     *
     * @see #getNumberFormatOverride()
     */
    public void setNumberFormatOverride(NumberFormat formatter) {
        this.numberFormatOverride = formatter;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Calculates the log of the given value, using the current base.
     *
     * @param value  the value.
     *
     * @return The log of the given value.
     *
     * @see #calculateValue(double)
     * @see #getBase()
     */
    public double calculateLog(double value) {
        return Math.log(value) / this.baseLog;
    }

    /**
     * Calculates the value from a given log.
     *
     * @param log  the log value (must be > 0.0).
     *
     * @return The value with the given log.
     *
     * @see #calculateLog(double)
     * @see #getBase()
     */
    public double calculateValue(double log) {
        return Math.pow(this.base, log);
    }
    
    private double calculateJava2DValue(double value) {
        double result = 0;
        double leftRange = 0;
        if(value < leftLinearBoundValue) {
            result = calculateLog(value);
        } else if (value >= leftLinearBoundValue && value < rightLinearBoundValue) {
            result = leftRange + (value-leftLinearBoundValue)*linearRangeScalingFactor;
        } else {
            result = leftRange + (value-leftLinearBoundValue)*linearRangeScalingFactor + calculateLog(value - rightLinearBoundValue);            
        }
        return result;
    }

    private void calculateAxisLookup() {
        Range range = getRange();
        double lower = range.getLowerBound();
        double upper = range.getUpperBound();
        double totalRange;
        //double leftRange, rightRange,totalRange;
        if (lower < leftLinearBoundValue && upper > rightLinearBoundValue) {
            double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
            double linRange = Math.abs(rightLinearBoundValue - leftLinearBoundValue);
            double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
            totalRange = (leftRange + rightRange) * (1 - linearRangeScalingFactor) + linRange * linearRangeScalingFactor;
        } else if (lower < leftLinearBoundValue && upper < rightLinearBoundValue && upper > leftLinearBoundValue) {
            double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
            double rightRange = Math.abs(upper - rightLinearBoundValue);
            totalRange = (leftRange) * (1 - linearRangeScalingFactor) + rightRange * linearRangeScalingFactor;
        } else if (lower < leftLinearBoundValue && upper < rightLinearBoundValue) {
            double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
            double rightRange = calculateLog(Math.abs(upper - leftLinearBoundValue));
            totalRange = (leftRange - rightRange);
        } else if (lower > leftLinearBoundValue && lower < rightLinearBoundValue && upper > rightLinearBoundValue) {
            double leftRange = Math.abs(rightLinearBoundValue - lower);
            double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
            totalRange = leftRange * linearRangeScalingFactor + (rightRange) * (1 - linearRangeScalingFactor);
        } else if (lower > leftLinearBoundValue && lower < rightLinearBoundValue && upper < rightLinearBoundValue && upper > leftLinearBoundValue) {
            totalRange = upper - lower;
        } else { //lower > rightLinearBoundValue && upper > rightLinearBoundValue
            double leftRange = calculateLog(Math.abs(rightLinearBoundValue - lower));
            double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
            totalRange = (rightRange - leftRange);
        }
    }

    /**
     * Converts a value on the axis scale to a Java2D coordinate relative to
     * the given <code>area</code>, based on the axis running along the
     * specified <code>edge</code>.
     *
     * @param value  the data value.
     * @param area  the area.
     * @param edge  the edge.
     *
     * @return The Java2D coordinate corresponding to <code>value</code>.
     */
    public double valueToJava2D(double value, Rectangle2D area,
            RectangleEdge edge) {
        Range range = getRange();


        //Possible scenarios for lower and upper bound.
        //*---|-------|---*  ||
        //*---|---*---|----  ||
        //*---|-------|---*  ||
        //*-*-|-------|----  ||
        //----|---*-*-|----  ||
        //----|-------|-*-*  ||





        if (value > leftLinearBoundValue && value < rightLinearBoundValue) {
            double axisMin = Math.max(range.getLowerBound(), leftLinearBoundValue);
            double axisMax = Math.min(range.getUpperBound(), rightLinearBoundValue);

            double min = 0.0;
            double max = 0.0;
            if (RectangleEdge.isTopOrBottom(edge)) {
                min = area.getX();
                max = area.getMaxX();
            } else if (RectangleEdge.isLeftOrRight(edge)) {
                max = area.getMinY();
                min = area.getMaxY();
            }
            if (isInverted()) {
                return max
                        - ((value - axisMin) / (axisMax - axisMin)) * (max - min);
            } else {
                return min
                        + ((value - axisMin) / (axisMax - axisMin)) * (max - min);
            }
        } else {
            //plot on a logarithmic scale
            double logAxisMin = -calculateLog(Math.abs(range.getLowerBound()));
            double logAxisMax = calculateLog(range.getUpperBound());

            if (value > 0) {
                value = calculateLog(value);
            } else {
                value = -calculateLog(Math.abs(value));
            }

            double min = 0.0;
            double max = 0.0;
            if (RectangleEdge.isTopOrBottom(edge)) {
                min = area.getX();
                max = area.getMaxX();
            } else if (RectangleEdge.isLeftOrRight(edge)) {
                max = area.getMinY();
                min = area.getMaxY();
            }
            if (isInverted()) {
                return max
                        - ((value - logAxisMin) / (logAxisMax - logAxisMin)) * (max - min);
            } else {
                return min
                        + ((value - logAxisMin) / (logAxisMax - logAxisMin)) * (max - min);
            }
        }
    }

    /**
     * Converts a Java2D coordinate to an axis value, assuming that the
     * axis covers the specified <code>edge</code> of the <code>area</code>.
     *
     * @param java2DValue  the Java2D coordinate.
     * @param area  the area.
     * @param edge  the edge that the axis belongs to.
     *
     * @return A value along the axis scale.
     */
    public double java2DToValue(double java2DValue, Rectangle2D area,
            RectangleEdge edge) {

        Range range = getRange();


        if (java2DValue > leftLinearBoundValue && java2DValue < rightLinearBoundValue) {
            double axisMin = range.getLowerBound();
            double axisMax = range.getUpperBound();

            double min = 0.0;
            double max = 0.0;
            if (RectangleEdge.isTopOrBottom(edge)) {
                min = area.getX();
                max = area.getMaxX();
            } else if (RectangleEdge.isLeftOrRight(edge)) {
                min = area.getMaxY();
                max = area.getY();
            }
            if (isInverted()) {
                return axisMax
                        - (java2DValue - min) / (max - min) * (axisMax - axisMin);
            } else {
                return axisMin
                        + (java2DValue - min) / (max - min) * (axisMax - axisMin);
            }
        } else {
            double axisMin = calculateLog(range.getLowerBound());
            double axisMax = calculateLog(range.getUpperBound());

            double min = 0.0;
            double max = 0.0;
            if (RectangleEdge.isTopOrBottom(edge)) {
                min = area.getX();
                max = area.getMaxX();
            } else if (RectangleEdge.isLeftOrRight(edge)) {
                min = area.getMaxY();
                max = area.getY();
            }
            double log = 0.0;
            if (isInverted()) {
                log = axisMax - (java2DValue - min) / (max - min)
                        * (axisMax - axisMin);
            } else {
                log = axisMin + (java2DValue - min) / (max - min)
                        * (axisMax - axisMin);
            }
            return calculateValue(log);
        }
    }

    /**
     * Configures the axis.  This method is typically called when an axis
     * is assigned to a new plot.
     */
    public void configure() {
        if (isAutoRange()) {
            autoAdjustRange();
        }
    }

    /**
     * Adjusts the axis range to match the data range that the axis is
     * required to display.
     */
    protected void autoAdjustRange() {
        Plot plot = getPlot();
        if (plot == null) {
            return;  // no plot, no data
        }

        if (plot instanceof ValueAxisPlot) {
            ValueAxisPlot vap = (ValueAxisPlot) plot;

            Range r = vap.getDataRange(this);
            if (r == null) {
                r = getDefaultAutoRange();
            }

            double upper = r.getUpperBound();
            double lower = r.getLowerBound();
            double range = upper - lower;

            // ensure the autorange is at least <minRange> in size...
            double minRange = getAutoRangeMinimumSize();
            if (range < minRange) {
                double expand = (minRange - range) / 2;
                upper = upper + expand;
                lower = lower - expand;
                if (lower == upper) { // see bug report 1549218
                    double adjust = Math.abs(lower) / 10.0;
                    lower = lower - adjust;
                    upper = upper + adjust;
                }
            }
            // We need to caluclate the total range of axis,
            // composed of the linear and logarithmic parts and account for
            // different cases:
            //log(lb-l)+(rb-lb)+log(u-rb)
            //log(lb-l)+(u-lb)
            //log(lb-l)+log(u-lb)
            //(rb-l)+log(u-rb)
            //(rb-l)+(u-rb)
            //log(rb-l)+log(u-rb)
            if (lower < leftLinearBoundValue && upper > rightLinearBoundValue) {
                double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
                double linRange = Math.abs(rightLinearBoundValue - leftLinearBoundValue);
                double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
                double totalRange = (leftRange + rightRange) * (1 - linearRangeScalingFactor) + linRange * linearRangeScalingFactor;
                upper = calculateValue(calculateLog(upper) + getUpperMargin() * totalRange);
                lower = -calculateValue(calculateLog(Math.abs(lower)) + getLowerMargin() * totalRange);
            } else if (lower < leftLinearBoundValue && upper < rightLinearBoundValue && upper > leftLinearBoundValue) {
                double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
                double rightRange = Math.abs(upper - rightLinearBoundValue);
                double totalRange = (leftRange) * (1 - linearRangeScalingFactor) + rightRange * linearRangeScalingFactor;
                upper = upper + getUpperMargin() * totalRange;
                lower = lower - getLowerMargin() * totalRange;
            } else if (lower < leftLinearBoundValue && upper < rightLinearBoundValue) {
                double leftRange = calculateLog(Math.abs(leftLinearBoundValue - lower));
                double rightRange = calculateLog(Math.abs(upper - leftLinearBoundValue));
                double totalRange = (leftRange - rightRange);
                upper = upper + getUpperMargin() * totalRange;
                lower = lower - getLowerMargin() * totalRange;
            } else if (lower > leftLinearBoundValue && lower < rightLinearBoundValue && upper > rightLinearBoundValue) {
                double leftRange = Math.abs(rightLinearBoundValue - lower);
                double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
                double totalRange = leftRange * linearRangeScalingFactor + (rightRange) * (1 - linearRangeScalingFactor);
                upper = upper + getUpperMargin() * totalRange;
                lower = lower - getLowerMargin() * totalRange;
            } else if (lower > leftLinearBoundValue && lower < rightLinearBoundValue && upper < rightLinearBoundValue && upper > leftLinearBoundValue) {
                double totalRange = upper - lower;
                upper = upper + getUpperMargin() * totalRange;
                lower = lower - getLowerMargin() * totalRange;
            } else { //lower > rightLinearBoundValue && upper > rightLinearBoundValue
                double leftRange = calculateLog(Math.abs(rightLinearBoundValue - lower));
                double rightRange = calculateLog(Math.abs(upper - rightLinearBoundValue));
                double totalRange = (rightRange - leftRange);
                upper = upper + getUpperMargin() * totalRange;
                lower = lower - getLowerMargin() * totalRange;
            }

            setRange(new Range(lower, upper), false, false);
        }
    }

    /**
     * Draws the axis on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param cursor  the cursor location (determines where to draw the axis).
     * @param plotArea  the area within which the axes and plot should be drawn.
     * @param dataArea  the area within which the data should be drawn.
     * @param edge  the axis location (<code>null</code> not permitted).
     * @param plotState  collects information about the plot
     *                   (<code>null</code> permitted).
     *
     * @return The axis state (never <code>null</code>).
     */
    public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea,
            Rectangle2D dataArea, RectangleEdge edge,
            PlotRenderingInfo plotState) {

        AxisState state = null;
        // if the axis is not visible, don't draw it...


        if (!isVisible()) {
            state = new AxisState(cursor);
            // even though the axis is not visible, we need ticks for the
            // gridlines...
            List<Tick> ticks = refreshTicks(g2, state, dataArea, edge);
            state.setTicks(ticks);


            return state;


        }
        state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
        state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
        createAndAddEntity(
                cursor, state, dataArea, edge, plotState);


        return state;


    }

    /**
     * Calculates the positions of the tick labels for the axis, storing the
     * results in the tick label list (ready for drawing).
     *
     * @param g2  the graphics device.
     * @param state  the axis state.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the location of the axis.
     *
     * @return A list of ticks.
     *
     */
    public List<Tick> refreshTicks(Graphics2D g2, AxisState state,
            Rectangle2D dataArea, RectangleEdge edge) {

        List<Tick> result = new java.util.ArrayList<Tick>();


        if (RectangleEdge.isTopOrBottom(edge)) {
            result = refreshTicksHorizontal(g2, dataArea, edge);


        } else if (RectangleEdge.isLeftOrRight(edge)) {
            result = refreshTicksVertical(g2, dataArea, edge);


        }
        return result;



    }

    /**
     * Calculates the positions of the tick labels for the axis, storing the
     * results in the tick label list (ready for drawing).
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the data should be drawn.
     * @param edge  the location of the axis.
     *
     * @return A list of ticks.
     */
    protected List<Tick> refreshTicksHorizontal(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        List<Tick> result = new java.util.ArrayList<Tick>();

        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);



        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);


        }

        TickUnit tu = getTickUnit();


        double size = tu.getSize();


        int count = calculateVisibleTickCount();


        double lowestTickValue = calculateLowestVisibleTickValue();



        if (count <= ValueAxis.MAXIMUM_TICK_COUNT) {
            int minorTickSpaces = getMinorTickCount();


            if (minorTickSpaces <= 0) {
                minorTickSpaces = tu.getMinorTickCount();


            }
            for (int minorTick = 1; minorTick
                    < minorTickSpaces; minorTick++) {
                double minorTickValue = lowestTickValue
                        - size * minorTick / minorTickSpaces;


                if (getRange().contains(minorTickValue)) {
                    result.add(new NumberTick(TickType.MINOR, minorTickValue,
                            "", TextAnchor.TOP_CENTER, TextAnchor.CENTER,
                            0.0));


                }
            }
            for (int i = 0; i
                    < count; i++) {
                double currentTickValue = lowestTickValue + (i * size);
                String tickLabel;
                NumberFormat formatter = getNumberFormatOverride();


                if (formatter != null) {
                    tickLabel = formatter.format(currentTickValue);


                } else {
                    tickLabel = getTickUnit().valueToString(currentTickValue);


                }
                TextAnchor anchor = null;
                TextAnchor rotationAnchor = null;


                double angle = 0.0;


                if (isVerticalTickLabels()) {
                    anchor = TextAnchor.CENTER_RIGHT;
                    rotationAnchor = TextAnchor.CENTER_RIGHT;


                    if (edge == RectangleEdge.TOP) {
                        angle = Math.PI / 2.0;


                    } else {
                        angle = -Math.PI / 2.0;


                    }
                } else {
                    if (edge == RectangleEdge.TOP) {
                        anchor = TextAnchor.BOTTOM_CENTER;
                        rotationAnchor = TextAnchor.BOTTOM_CENTER;


                    } else {
                        anchor = TextAnchor.TOP_CENTER;
                        rotationAnchor = TextAnchor.TOP_CENTER;


                    }
                }

                Tick tick = new NumberTick(new Double(currentTickValue),
                        tickLabel, anchor, rotationAnchor, angle);
                result.add(tick);


                double nextTickValue = lowestTickValue + ((i + 1) * size);


                for (int minorTick = 1; minorTick
                        < minorTickSpaces;
                        minorTick++) {
                    double minorTickValue = currentTickValue
                            + (nextTickValue - currentTickValue)
                            * minorTick / minorTickSpaces;


                    if (getRange().contains(minorTickValue)) {
                        result.add(new NumberTick(TickType.MINOR,
                                minorTickValue, "", TextAnchor.TOP_CENTER,
                                TextAnchor.CENTER, 0.0));


                    }
                }
            }
        }
        return result;



    }

    /**
     * Calculates the number of visible ticks.
     *
     * @return The number of visible ticks on the axis.
     */
    protected int calculateVisibleTickCount() {

        double unit = getTickUnit().getSize();
        Range range = getRange();


        return (int) (Math.min(Math.floor(leftLinearBoundValue / unit), Math.floor(range.getUpperBound() / unit))
                - Math.ceil(range.getLowerBound() / unit) + 1);



    }

    /**
     * Calculates the value of the lowest visible tick on the axis.
     *
     * @return The value of the lowest visible tick on the axis.
     *
     * @see #calculateHighestVisibleTickValue()
     */
    protected double calculateLowestVisibleTickValue() {

        double unit = getTickUnit().getSize();


        double index = Math.min(Math.floor(leftLinearBoundValue / unit), Math.floor(getRange().getUpperBound() / unit));


        return index * unit;



    }

    /**
     * Returns a list of ticks for an axis at the top or bottom of the chart.
     *
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param edge  the edge.
     *
     * @return A list of ticks.
     */
    protected List<Tick> refreshLogTicksHorizontal(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {

        Range range = getRange();
        List<Tick> ticks = new ArrayList<Tick>();
        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);
        TextAnchor textAnchor;


        if (edge == RectangleEdge.TOP) {
            textAnchor = TextAnchor.BOTTOM_CENTER;


        } else {
            textAnchor = TextAnchor.TOP_CENTER;


        }

        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);


        }
        int minorTickCount = this.tickUnit.getMinorTickCount();


        double start = Math.floor(Math.max(calculateLog(getLowerBound()), calculateLog(leftLinearBoundValue)));


        double end = Math.ceil(calculateLog(getUpperBound()));


        double current = start;


        while (current <= end) {
            double v = calculateValue(current);


            if (range.contains(v)) {
                ticks.add(new NumberTick(TickType.MAJOR, v, createTickLabel(v),
                        textAnchor, TextAnchor.CENTER, 0.0));


            } // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current
                    + this.tickUnit.getSize());


            for (int i = 1; i
                    < minorTickCount; i++) {
                double minorV = v + i * ((next - v) / minorTickCount);


                if (range.contains(minorV)) {
                    ticks.add(new NumberTick(TickType.MINOR, minorV, "",
                            textAnchor, TextAnchor.CENTER, 0.0));


                }
            }
            current = current + this.tickUnit.getSize();


        }
        return ticks;


    }

    /**
     * Returns a list of ticks for an axis at the left or right of the chart.
     *
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param edge  the edge.
     *
     * @return A list of ticks.
     */
    protected List<Tick> refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {

        Range range = getRange();
        List<Tick> ticks = new ArrayList<Tick>();
        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);
        TextAnchor textAnchor;


        if (edge == RectangleEdge.RIGHT) {
            textAnchor = TextAnchor.CENTER_LEFT;


        } else {
            textAnchor = TextAnchor.CENTER_RIGHT;


        }

        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);


        }
        int minorTickCount = this.tickUnit.getMinorTickCount();


        double start = Math.floor(calculateLog(getLowerBound()));


        double end = Math.ceil(calculateLog(getUpperBound()));


        double current = start;


        while (current <= end) {
            double v = calculateValue(current);


            if (range.contains(v)) {
                ticks.add(new NumberTick(TickType.MAJOR, v, createTickLabel(v),
                        textAnchor, TextAnchor.CENTER, 0.0));


            } // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current
                    + this.tickUnit.getSize());


            for (int i = 1; i
                    < minorTickCount; i++) {
                double minorV = v + i * ((next - v) / minorTickCount);


                if (range.contains(minorV)) {
                    ticks.add(new NumberTick(TickType.MINOR, minorV, "",
                            textAnchor, TextAnchor.CENTER, 0.0));


                }
            }
            current = current + this.tickUnit.getSize();


        }
        return ticks;


    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area defined by the axes.
     * @param edge  the axis location.
     *
     * @since 1.0.7
     */
    protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {

        if (RectangleEdge.isTopOrBottom(edge)) {
            selectHorizontalAutoTickUnit(g2, dataArea, edge);


        } else if (RectangleEdge.isLeftOrRight(edge)) {
            selectVerticalAutoTickUnit(g2, dataArea, edge);


        }

    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area defined by the axes.
     * @param edge  the axis location.
     *
     * @since 1.0.7
     */
    protected void selectHorizontalAutoTickUnit(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        double tickLabelWidth = estimateMaximumTickLabelWidth(g2,
                getTickUnit());

        // start with the current tick unit...
        TickUnitSource tickUnits = getStandardTickUnits();
        TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());


        double unit1Width = exponentLengthToJava2D(unit1.getSize(), dataArea,
                edge);

        // then extrapolate...


        double guess = (tickLabelWidth / unit1Width) * unit1.getSize();

        NumberTickUnit unit2 = (NumberTickUnit) tickUnits.getCeilingTickUnit(guess);


        double unit2Width = exponentLengthToJava2D(unit2.getSize(), dataArea,
                edge);

        tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);


        if (tickLabelWidth > unit2Width) {
            unit2 = (NumberTickUnit) tickUnits.getLargerTickUnit(unit2);


        }

        setTickUnit(unit2, false, false);



    }

    /**
     * Converts a length in data coordinates into the corresponding length in
     * Java2D coordinates.
     *
     * @param length  the length.
     * @param area  the plot area.
     * @param edge  the edge along which the axis lies.
     *
     * @return The length in Java2D coordinates.
     *
     * @since 1.0.7
     */
    public double exponentLengthToJava2D(double length, Rectangle2D area,
            RectangleEdge edge) {
        double one = valueToJava2D(calculateValue(1.0), area, edge);


        double l = valueToJava2D(calculateValue(length + 1.0), area, edge);


        return Math.abs(l - one);


    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the axis location.
     *
     * @since 1.0.7
     */
    protected void selectVerticalAutoTickUnit(Graphics2D g2,
            Rectangle2D dataArea,
            RectangleEdge edge) {

        double tickLabelHeight = estimateMaximumTickLabelHeight(g2);

        // start with the current tick unit...
        TickUnitSource tickUnits = getStandardTickUnits();
        TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());


        double unitHeight = exponentLengthToJava2D(unit1.getSize(), dataArea,
                edge);

        // then extrapolate...


        double guess = (tickLabelHeight / unitHeight) * unit1.getSize();

        NumberTickUnit unit2 = (NumberTickUnit) tickUnits.getCeilingTickUnit(guess);


        double unit2Height = exponentLengthToJava2D(unit2.getSize(), dataArea,
                edge);

        tickLabelHeight = estimateMaximumTickLabelHeight(g2);


        if (tickLabelHeight > unit2Height) {
            unit2 = (NumberTickUnit) tickUnits.getLargerTickUnit(unit2);


        }

        setTickUnit(unit2, false, false);



    }

    /**
     * Estimates the maximum tick label height.
     *
     * @param g2  the graphics device.
     *
     * @return The maximum height.
     *
     * @since 1.0.7
     */
    protected double estimateMaximumTickLabelHeight(Graphics2D g2) {

        RectangleInsets tickLabelInsets = getTickLabelInsets();


        double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();

        Font tickLabelFont = getTickLabelFont();
        FontRenderContext frc = g2.getFontRenderContext();
        result += tickLabelFont.getLineMetrics("123", frc).getHeight();


        return result;



    }

    /**
     * Estimates the maximum width of the tick labels, assuming the specified
     * tick unit is used.
     * <P>
     * Rather than computing the string bounds of every tick on the axis, we
     * just look at two values: the lower bound and the upper bound for the
     * axis.  These two values will usually be representative.
     *
     * @param g2  the graphics device.
     * @param unit  the tick unit to use for calculation.
     *
     * @return The estimated maximum width of the tick labels.
     *
     * @since 1.0.7
     */
    protected double estimateMaximumTickLabelWidth(Graphics2D g2,
            TickUnit unit) {

        RectangleInsets tickLabelInsets = getTickLabelInsets();


        double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();



        if (isVerticalTickLabels()) {
            // all tick labels have the same width (equal to the height of the
            // font)...
            FontRenderContext frc = g2.getFontRenderContext();
            LineMetrics lm = getTickLabelFont().getLineMetrics("0", frc);
            result += lm.getHeight();


        } else {
            // look at lower and upper bounds...
            FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
            Range range = getRange();


            double lower = range.getLowerBound();


            double upper = range.getUpperBound();
            String lowerStr = "";
            String upperStr = "";
            NumberFormat formatter = getNumberFormatOverride();


            if (formatter != null) {
                lowerStr = formatter.format(lower);
                upperStr = formatter.format(upper);


            } else {
                lowerStr = unit.valueToString(lower);
                upperStr = unit.valueToString(upper);


            }
            double w1 = fm.stringWidth(lowerStr);


            double w2 = fm.stringWidth(upperStr);
            result += Math.max(w1, w2);


        }

        return result;



    }

    /**
     * Zooms in on the current range.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     */
    @Override
    public void zoomRange(double lowerPercent, double upperPercent) {
        Range range = getRange();


        double start = range.getLowerBound();


        double end = range.getUpperBound();


        double log1 = calculateLog(start);


        double log2 = calculateLog(end);


        double length = log2 - log1;
        Range adjusted = null;


        if (isInverted()) {
            double logA = log1 + length * (1 - upperPercent);


            double logB = log1 + length * (1 - lowerPercent);
            adjusted = new Range(calculateValue(logA), calculateValue(logB));


        } else {
            double logA = log1 + length * lowerPercent;


            double logB = log1 + length * upperPercent;
            adjusted = new Range(calculateValue(logA), calculateValue(logB));


        }
        setRange(adjusted);


    }

    /**
     * Slides the axis range by the specified percentage.
     *
     * @param percent  the percentage.
     *
     * @since 1.0.13
     */
    @Override
    public void pan(double percent) {
        Range range = getRange();


        double lower = range.getLowerBound();


        double upper = range.getUpperBound();


        double log1 = calculateLog(lower);


        double log2 = calculateLog(upper);


        double length = log2 - log1;


        double adj = length * percent;
        log1 = log1 + adj;
        log2 = log2 + adj;
        setRange(
                calculateValue(log1), calculateValue(log2));


    }

    /**
     * Creates a tick label for the specified value.  Note that this method
     * was 'private' prior to version 1.0.10.
     *
     * @param value  the value.
     *
     * @return The label.
     *
     * @since 1.0.10
     */
    protected String createTickLabel(double value) {
        if (this.numberFormatOverride != null) {
            return this.numberFormatOverride.format(value);


        } else {
            return this.tickUnit.valueToString(value);


        }
    }

    /**
     * Tests this axis for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;


        }
        if (!(obj instanceof LinLogAxis)) {
            return false;


        }
        LinLogAxis that = (LinLogAxis) obj;


        if (this.base != that.base) {
            return false;


        }
        if (this.smallestValue != that.smallestValue) {
            return false;


        }
        return super.equals(obj);


    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;


        long temp = Double.doubleToLongBits(this.base);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.smallestValue);
        result = 37 * result + (int) (temp ^ (temp >>> 32));


        if (this.numberFormatOverride != null) {
            result = 37 * result + this.numberFormatOverride.hashCode();


        }
        result = 37 * result + this.tickUnit.hashCode();


        return result;


    }

    /**
     * Returns a collection of tick units for log (base 10) values.
     * Uses a given Locale to create the DecimalFormats.
     *
     * @param locale the locale to use to represent Numbers.
     *
     * @return A collection of tick units for integer values.
     *
     * @since 1.0.7
     */
    public static TickUnitSource createLogTickUnits(Locale locale) {
        TickUnits units = new TickUnits();
        NumberFormat numberFormat = new LogFormat();
        units.add(new NumberTickUnit(0.05, numberFormat, 2));
        units.add(new NumberTickUnit(0.1, numberFormat, 10));
        units.add(new NumberTickUnit(0.2, numberFormat, 2));
        units.add(new NumberTickUnit(0.5, numberFormat, 5));
        units.add(new NumberTickUnit(1, numberFormat, 10));
        units.add(new NumberTickUnit(2, numberFormat, 10));
        units.add(new NumberTickUnit(3, numberFormat, 15));
        units.add(new NumberTickUnit(4, numberFormat, 20));
        units.add(new NumberTickUnit(5, numberFormat, 25));
        units.add(new NumberTickUnit(6, numberFormat));
        units.add(new NumberTickUnit(7, numberFormat));
        units.add(new NumberTickUnit(8, numberFormat));
        units.add(new NumberTickUnit(9, numberFormat));
        units.add(new NumberTickUnit(10, numberFormat));


        return units;


    }

    /**
     * Creates the standard tick units.
     * <P>
     * If you don't like these defaults, create your own instance of TickUnits
     * and then pass it to the setStandardTickUnits() method in the
     * NumberAxis class.
     *
     * @return The standard tick units.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     * @see #createIntegerTickUnits()
     */
    public static TickUnitSource createStandardTickUnits() {

        TickUnits units = new TickUnits();
        DecimalFormat df0 = new DecimalFormat("0.00000000");
        DecimalFormat df1 = new DecimalFormat("0.0000000");
        DecimalFormat df2 = new DecimalFormat("0.000000");
        DecimalFormat df3 = new DecimalFormat("0.00000");
        DecimalFormat df4 = new DecimalFormat("0.0000");
        DecimalFormat df5 = new DecimalFormat("0.000");
        DecimalFormat df6 = new DecimalFormat("0.00");
        DecimalFormat df7 = new DecimalFormat("0.0");
        DecimalFormat df8 = new DecimalFormat("#,##0");
        DecimalFormat df9 = new DecimalFormat("#,###,##0");
        DecimalFormat df10 = new DecimalFormat("#,###,###,##0");

        // we can add the units in any order, the TickUnits collection will
        // sort them...
        units.add(new NumberTickUnit(0.0000001, df1, 2));
        units.add(new NumberTickUnit(0.000001, df2, 2));
        units.add(new NumberTickUnit(0.00001, df3, 2));
        units.add(new NumberTickUnit(0.0001, df4, 2));
        units.add(new NumberTickUnit(0.001, df5, 2));
        units.add(new NumberTickUnit(0.01, df6, 2));
        units.add(new NumberTickUnit(0.1, df7, 2));
        units.add(new NumberTickUnit(1, df8, 2));
        units.add(new NumberTickUnit(10, df8, 2));
        units.add(new NumberTickUnit(100, df8, 2));
        units.add(new NumberTickUnit(1000, df8, 2));
        units.add(new NumberTickUnit(10000, df8, 2));
        units.add(new NumberTickUnit(100000, df8, 2));
        units.add(new NumberTickUnit(1000000, df9, 2));
        units.add(new NumberTickUnit(10000000, df9, 2));
        units.add(new NumberTickUnit(100000000, df9, 2));
        units.add(new NumberTickUnit(1000000000, df10, 2));
        units.add(new NumberTickUnit(10000000000.0, df10, 2));
        units.add(new NumberTickUnit(100000000000.0, df10, 2));

        units.add(new NumberTickUnit(0.00000025, df0, 5));
        units.add(new NumberTickUnit(0.0000025, df1, 5));
        units.add(new NumberTickUnit(0.000025, df2, 5));
        units.add(new NumberTickUnit(0.00025, df3, 5));
        units.add(new NumberTickUnit(0.0025, df4, 5));
        units.add(new NumberTickUnit(0.025, df5, 5));
        units.add(new NumberTickUnit(0.25, df6, 5));
        units.add(new NumberTickUnit(2.5, df7, 5));
        units.add(new NumberTickUnit(25, df8, 5));
        units.add(new NumberTickUnit(250, df8, 5));
        units.add(new NumberTickUnit(2500, df8, 5));
        units.add(new NumberTickUnit(25000, df8, 5));
        units.add(new NumberTickUnit(250000, df8, 5));
        units.add(new NumberTickUnit(2500000, df9, 5));
        units.add(new NumberTickUnit(25000000, df9, 5));
        units.add(new NumberTickUnit(250000000, df9, 5));
        units.add(new NumberTickUnit(2500000000.0, df10, 5));
        units.add(new NumberTickUnit(25000000000.0, df10, 5));
        units.add(new NumberTickUnit(250000000000.0, df10, 5));

        units.add(new NumberTickUnit(0.0000005, df1, 5));
        units.add(new NumberTickUnit(0.000005, df2, 5));
        units.add(new NumberTickUnit(0.00005, df3, 5));
        units.add(new NumberTickUnit(0.0005, df4, 5));
        units.add(new NumberTickUnit(0.005, df5, 5));
        units.add(new NumberTickUnit(0.05, df6, 5));
        units.add(new NumberTickUnit(0.5, df7, 5));
        units.add(new NumberTickUnit(5L, df8, 5));
        units.add(new NumberTickUnit(50L, df8, 5));
        units.add(new NumberTickUnit(500L, df8, 5));
        units.add(new NumberTickUnit(5000L, df8, 5));
        units.add(new NumberTickUnit(50000L, df8, 5));
        units.add(new NumberTickUnit(500000L, df8, 5));
        units.add(new NumberTickUnit(5000000L, df9, 5));
        units.add(new NumberTickUnit(50000000L, df9, 5));
        units.add(new NumberTickUnit(500000000L, df9, 5));
        units.add(new NumberTickUnit(5000000000L, df10, 5));
        units.add(new NumberTickUnit(50000000000L, df10, 5));
        units.add(new NumberTickUnit(500000000000L, df10, 5));



        return units;



    }

    /**
     * Returns a collection of tick units for integer values.
     *
     * @return A collection of tick units for integer values.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     * @see #createStandardTickUnits()
     */
    public static TickUnitSource createIntegerTickUnits() {
        TickUnits units = new TickUnits();
        DecimalFormat df0 = new DecimalFormat("0");
        DecimalFormat df1 = new DecimalFormat("#,##0");
        units.add(new NumberTickUnit(1, df0, 2));
        units.add(new NumberTickUnit(2, df0, 2));
        units.add(new NumberTickUnit(5, df0, 5));
        units.add(new NumberTickUnit(10, df0, 2));
        units.add(new NumberTickUnit(20, df0, 2));
        units.add(new NumberTickUnit(50, df0, 5));
        units.add(new NumberTickUnit(100, df0, 2));
        units.add(new NumberTickUnit(200, df0, 2));
        units.add(new NumberTickUnit(500, df0, 5));
        units.add(new NumberTickUnit(1000, df1, 2));
        units.add(new NumberTickUnit(2000, df1, 2));
        units.add(new NumberTickUnit(5000, df1, 5));
        units.add(new NumberTickUnit(10000, df1, 2));
        units.add(new NumberTickUnit(20000, df1, 2));
        units.add(new NumberTickUnit(50000, df1, 5));
        units.add(new NumberTickUnit(100000, df1, 2));
        units.add(new NumberTickUnit(200000, df1, 2));
        units.add(new NumberTickUnit(500000, df1, 5));
        units.add(new NumberTickUnit(1000000, df1, 2));
        units.add(new NumberTickUnit(2000000, df1, 2));
        units.add(new NumberTickUnit(5000000, df1, 5));
        units.add(new NumberTickUnit(10000000, df1, 2));
        units.add(new NumberTickUnit(20000000, df1, 2));
        units.add(new NumberTickUnit(50000000, df1, 5));
        units.add(new NumberTickUnit(100000000, df1, 2));
        units.add(new NumberTickUnit(200000000, df1, 2));
        units.add(new NumberTickUnit(500000000, df1, 5));
        units.add(new NumberTickUnit(1000000000, df1, 2));
        units.add(new NumberTickUnit(2000000000, df1, 2));
        units.add(new NumberTickUnit(5000000000.0, df1, 5));
        units.add(new NumberTickUnit(10000000000.0, df1, 2));


        return units;


    }

    /**
     * Creates a collection of standard tick units.  The supplied locale is
     * used to create the number formatter (a localised instance of
     * <code>NumberFormat</code>).
     * <P>
     * If you don't like these defaults, create your own instance of
     * {@link TickUnits} and then pass it to the
     * <code>setStandardTickUnits()</code> method.
     *
     * @param locale  the locale.
     *
     * @return A tick unit collection.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     */
    public static TickUnitSource createStandardTickUnits(Locale locale) {

        TickUnits units = new TickUnits();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        // we can add the units in any order, the TickUnits collection will
        // sort them...
        units.add(new NumberTickUnit(0.0000001, numberFormat, 2));
        units.add(new NumberTickUnit(0.000001, numberFormat, 2));
        units.add(new NumberTickUnit(0.00001, numberFormat, 2));
        units.add(new NumberTickUnit(0.0001, numberFormat, 2));
        units.add(new NumberTickUnit(0.001, numberFormat, 2));
        units.add(new NumberTickUnit(0.01, numberFormat, 2));
        units.add(new NumberTickUnit(0.1, numberFormat, 2));
        units.add(new NumberTickUnit(1, numberFormat, 2));
        units.add(new NumberTickUnit(10, numberFormat, 2));
        units.add(new NumberTickUnit(100, numberFormat, 2));
        units.add(new NumberTickUnit(1000, numberFormat, 2));
        units.add(new NumberTickUnit(10000, numberFormat, 2));
        units.add(new NumberTickUnit(100000, numberFormat, 2));
        units.add(new NumberTickUnit(1000000, numberFormat, 2));
        units.add(new NumberTickUnit(10000000, numberFormat, 2));
        units.add(new NumberTickUnit(100000000, numberFormat, 2));
        units.add(new NumberTickUnit(1000000000, numberFormat, 2));
        units.add(new NumberTickUnit(10000000000.0, numberFormat, 2));

        units.add(new NumberTickUnit(0.00000025, numberFormat, 5));
        units.add(new NumberTickUnit(0.0000025, numberFormat, 5));
        units.add(new NumberTickUnit(0.000025, numberFormat, 5));
        units.add(new NumberTickUnit(0.00025, numberFormat, 5));
        units.add(new NumberTickUnit(0.0025, numberFormat, 5));
        units.add(new NumberTickUnit(0.025, numberFormat, 5));
        units.add(new NumberTickUnit(0.25, numberFormat, 5));
        units.add(new NumberTickUnit(2.5, numberFormat, 5));
        units.add(new NumberTickUnit(25, numberFormat, 5));
        units.add(new NumberTickUnit(250, numberFormat, 5));
        units.add(new NumberTickUnit(2500, numberFormat, 5));
        units.add(new NumberTickUnit(25000, numberFormat, 5));
        units.add(new NumberTickUnit(250000, numberFormat, 5));
        units.add(new NumberTickUnit(2500000, numberFormat, 5));
        units.add(new NumberTickUnit(25000000, numberFormat, 5));
        units.add(new NumberTickUnit(250000000, numberFormat, 5));
        units.add(new NumberTickUnit(2500000000.0, numberFormat, 5));
        units.add(new NumberTickUnit(25000000000.0, numberFormat, 5));

        units.add(new NumberTickUnit(0.0000005, numberFormat, 5));
        units.add(new NumberTickUnit(0.000005, numberFormat, 5));
        units.add(new NumberTickUnit(0.00005, numberFormat, 5));
        units.add(new NumberTickUnit(0.0005, numberFormat, 5));
        units.add(new NumberTickUnit(0.005, numberFormat, 5));
        units.add(new NumberTickUnit(0.05, numberFormat, 5));
        units.add(new NumberTickUnit(0.5, numberFormat, 5));
        units.add(new NumberTickUnit(5L, numberFormat, 5));
        units.add(new NumberTickUnit(50L, numberFormat, 5));
        units.add(new NumberTickUnit(500L, numberFormat, 5));
        units.add(new NumberTickUnit(5000L, numberFormat, 5));
        units.add(new NumberTickUnit(50000L, numberFormat, 5));
        units.add(new NumberTickUnit(500000L, numberFormat, 5));
        units.add(new NumberTickUnit(5000000L, numberFormat, 5));
        units.add(new NumberTickUnit(50000000L, numberFormat, 5));
        units.add(new NumberTickUnit(500000000L, numberFormat, 5));
        units.add(new NumberTickUnit(5000000000L, numberFormat, 5));
        units.add(new NumberTickUnit(50000000000L, numberFormat, 5));



        return units;



    }

    /**
     * Returns a collection of tick units for integer values.
     * Uses a given Locale to create the DecimalFormats.
     *
     * @param locale the locale to use to represent Numbers.
     *
     * @return A collection of tick units for integer values.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     */
    public static TickUnitSource createIntegerTickUnits(Locale locale) {
        TickUnits units = new TickUnits();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        units.add(new NumberTickUnit(1, numberFormat, 2));
        units.add(new NumberTickUnit(2, numberFormat, 2));
        units.add(new NumberTickUnit(5, numberFormat, 5));
        units.add(new NumberTickUnit(10, numberFormat, 2));
        units.add(new NumberTickUnit(20, numberFormat, 2));
        units.add(new NumberTickUnit(50, numberFormat, 5));
        units.add(new NumberTickUnit(100, numberFormat, 2));
        units.add(new NumberTickUnit(200, numberFormat, 2));
        units.add(new NumberTickUnit(500, numberFormat, 5));
        units.add(new NumberTickUnit(1000, numberFormat, 2));
        units.add(new NumberTickUnit(2000, numberFormat, 2));
        units.add(new NumberTickUnit(5000, numberFormat, 5));
        units.add(new NumberTickUnit(10000, numberFormat, 2));
        units.add(new NumberTickUnit(20000, numberFormat, 2));
        units.add(new NumberTickUnit(50000, numberFormat, 5));
        units.add(new NumberTickUnit(100000, numberFormat, 2));
        units.add(new NumberTickUnit(200000, numberFormat, 2));
        units.add(new NumberTickUnit(500000, numberFormat, 5));
        units.add(new NumberTickUnit(1000000, numberFormat, 2));
        units.add(new NumberTickUnit(2000000, numberFormat, 2));
        units.add(new NumberTickUnit(5000000, numberFormat, 5));
        units.add(new NumberTickUnit(10000000, numberFormat, 2));
        units.add(new NumberTickUnit(20000000, numberFormat, 2));
        units.add(new NumberTickUnit(50000000, numberFormat, 5));
        units.add(new NumberTickUnit(100000000, numberFormat, 2));
        units.add(new NumberTickUnit(200000000, numberFormat, 2));
        units.add(new NumberTickUnit(500000000, numberFormat, 5));
        units.add(new NumberTickUnit(1000000000, numberFormat, 2));
        units.add(new NumberTickUnit(2000000000, numberFormat, 2));
        units.add(new NumberTickUnit(5000000000.0, numberFormat, 5));
        units.add(new NumberTickUnit(10000000000.0, numberFormat, 2));


        return units;

    }
}

