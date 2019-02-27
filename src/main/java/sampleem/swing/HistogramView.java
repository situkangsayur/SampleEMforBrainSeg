/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing;

import com.widget.karisma.container.PanelWhiteBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

/**
 *
 * @author hendri
 */
public class HistogramView extends PanelWhiteBorder {

    private Graphics graph;
    private Graphics2D graph2D;
    private Paint paintglass;

    public HistogramView() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g.create();
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gd.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gd.setStroke(new BasicStroke(1.6F));
        gd.setColor(Color.WHITE);
        gd.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 50, 50);
        this.paintglass = new GradientPaint(0.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 0.08F), 0.0F, getHeight(), new Color(1.0F, 1.0F, 1.0F, 0.02F));
        gd.setPaint(this.paintglass);
        gd.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

        graph = g;
        graph2D = (Graphics2D) g;

        
//        gd.dispose();
    }
    
    

    public Graphics getGraph() {
        return graph;
    }

    public void setGraph(Graphics graph) {
        this.graph = graph;
    }

    public Graphics2D getGraph2D() {
        return graph2D;
    }

    public void setGraph2D(Graphics2D graph2D) {
        this.graph2D = graph2D;
    }
}
