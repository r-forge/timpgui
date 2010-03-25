/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glotaran.jfreechartcustom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import org.jfree.chart.plot.DefaultDrawingSupplier;

/**
 *
 * @author slapten
 */
public class GlotaranDrawingSupplier extends DefaultDrawingSupplier {

    private final static long serialVersionUID = 1L;

    public GlotaranDrawingSupplier() {
        super(new Paint[]{
                    Color.BLACK,
                    Color.RED,
                    Color.BLUE,
                    Color.GREEN,
                    Color.CYAN,
                    Color.magenta,
                    Color.ORANGE,
                    Color.PINK,
                    Color.DARK_GRAY,
                    Color.gray},
                DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DEFAULT_STROKE_SEQUENCE,
                DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DEFAULT_SHAPE_SEQUENCE);
    }

    public GlotaranDrawingSupplier(boolean state) {
        super(new Paint[]{
                    Color.BLACK,
                    Color.BLACK,
                    Color.RED,
                    Color.RED,
                    Color.BLUE,
                    Color.BLUE,
                    Color.GREEN,
                    Color.GREEN,
                    Color.CYAN,
                    Color.CYAN,
                    Color.magenta,
                    Color.magenta,
                    Color.ORANGE,
                    Color.ORANGE,
                    Color.PINK,
                    Color.PINK,
                    Color.DARK_GRAY,
                    Color.DARK_GRAY},
                DEFAULT_OUTLINE_PAINT_SEQUENCE,
                new Stroke[]{
                    new BasicStroke(1.0f),
                    new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 3.0f, new float[]{10.0f, 10.0f}, 0.0f)},
                DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DEFAULT_SHAPE_SEQUENCE);
    }
}
