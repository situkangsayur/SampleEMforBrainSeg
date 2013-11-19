
/*
 *
 * Smaller helper class to draw 2-D plots on the screen
 *
 * Copyright 2007, Koen Van Leemput
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing;

import java.awt.*;
import java.util.Vector;

public class XYPlot {

    // Constructor
    XYPlot(int x, int y, int width, int height) {
        m_X = x;
        m_Y = y;
        m_Width = width;
        m_Height = height;

        m_Plots = new Vector();
        m_Colors = new Vector();
        m_Strokes = new Vector();

        m_MaximumValue = 0.0f;
    }

    // Add a plot
    public void Add(double[] plot, Color color, Stroke stroke) {
        m_Plots.add(plot);
        m_Colors.add(color);
        m_Strokes.add(stroke);

        // Update maximum value is necessary
        for (int i = 0; i < plot.length; i++) {
            if (plot[ i] > m_MaximumValue) {
                m_MaximumValue = plot[ i];
                //System.out.println( "Updated maximum value to " + m_MaximumValue );
            }

        }

    }

    // Plot
    public void Plot(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the surrounding box; clear the inside of the box
        g2.clearRect(m_X, m_Y, m_Width, m_Height);
        g2.setColor(Color.black);
        g2.drawRect(m_X, m_Y, m_Width - 1, m_Height - 1);

        // Draw the plots
        for (int plotNumber = 0; plotNumber < m_Plots.size(); plotNumber++) {
            double[] plot = (double[]) (m_Plots.get(plotNumber));
            Color color = (Color) (m_Colors.get(plotNumber));
            Stroke stroke = (Stroke) (m_Strokes.get(plotNumber));

            g2.setColor(color);
            g2.setStroke(stroke);
            int[] xPoints = new int[plot.length];
            int[] yPoints = new int[plot.length];
            for (int i = 0; i < plot.length; i++) {
                xPoints[ i] = (int) ((((double) (m_Width)) / (plot.length - 1)) * i + m_X);
                yPoints[ i] = (int) (m_Y + m_Height - (m_Height / (m_MaximumValue * 1.2f)) * plot[ i]);
            }

            g2.drawPolyline(xPoints, yPoints, plot.length);

        } // End loop over all plots


    }
    // Data members
    private int m_X;
    private int m_Y;
    private int m_Width;
    private int m_Height;
    private Vector m_Plots;
    private Vector m_Colors;
    private Vector m_Strokes;
    private double m_MaximumValue;
}