package org.example.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class LeftBackgroundPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Lapisan 1
        GradientPaint baseGradient = new GradientPaint(0, 0, new Color(144, 238, 144), w, h, new Color(0, 206, 209));
        g2d.setPaint(baseGradient);
        g2d.fillRect(0, 0, w, h);

        // Tengah
        GeneralPath middleWave = new GeneralPath();
        middleWave.moveTo(0, h);
        middleWave.curveTo(w * 0.25, h * 0.5, w * 0.65, h * 0.2, w, h * 0.4);
        middleWave.lineTo(w, h);
        middleWave.closePath();

        GradientPaint middleGradient = new GradientPaint(0, h * 0.3f, new Color(72, 209, 204), w, h,
                new Color(0, 139, 139));
        g2d.setPaint(middleGradient);
        g2d.fill(middleWave);

        GeneralPath frontWave = new GeneralPath();
        frontWave.moveTo(0, h);
        frontWave.curveTo(w * 0.2, h * 0.7, w * 0.45, h * 0.45, w * 0.85, h);
        frontWave.lineTo(0, h);
        frontWave.closePath();

        GradientPaint frontGradient = new GradientPaint(0, h * 0.6f, new Color(0, 128, 128), w * 0.5f, h,
                new Color(0, 80, 80));
        g2d.setPaint(frontGradient);
        g2d.fill(frontWave);
    }
}