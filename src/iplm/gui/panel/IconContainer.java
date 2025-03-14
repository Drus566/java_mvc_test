package iplm.gui.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class IconContainer extends JPanel {
    public IconContainer(JComponent component) {
        setLayout(new MigLayout("insets dialog, align 50% 50%"));
        setOpaque(false);
        add(component);
    }

    public IconContainer(int width, int height, JComponent component) {
        setLayout(new MigLayout("insets dialog, width " + width + "!, height " + height + "!, align 50% 50%"));
        setOpaque(false);
        add(component);
    }


    public boolean pointInner(Point p) {
        Rectangle r = getBounds();
        return !((p.getX() < r.getX() || p.getX() > r.getX() + r.getWidth()) ||
                (p.getY() < r.getY() || p.getY() > r.getY() + r.getHeight()));
    }
}
