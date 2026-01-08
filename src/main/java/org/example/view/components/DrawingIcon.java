package org.example.view.components;

import javax.swing.*;
import java.awt.*;

public class DrawingIcon implements Icon {
    private final String type;
    public DrawingIcon(String type) { this.type = type; }
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        if (type.equals("USER")) {
            g2.fillOval(x + 4, y, 10, 10);
            g2.fillArc(x, y + 10, 18, 10, 0, 180);
        } else {
            g2.fillRect(x + 2, y + 8, 14, 10);
            g2.drawArc(x + 4, y, 10, 12, 0, 180);
        }
        g2.dispose();
    }
    @Override public int getIconWidth() { return 18; }
    @Override public int getIconHeight() { return 20; }
}