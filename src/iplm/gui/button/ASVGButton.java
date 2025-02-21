package iplm.gui.button;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class ASVGButton extends JButton {
    private List<Runnable> actions;
    private boolean in = false;

    protected FlatSVGIcon icon;
    protected FlatSVGIcon.ColorFilter color_filter;
    protected FlatSVGIcon.ColorFilter hover_filter;
    protected FlatSVGIcon.ColorFilter press_filter;

    public ASVGButton() {}

    public void addAction(Runnable e) {
        actions.add(e);
    }

    protected void build() {
        setIcon(icon);
        setContentAreaFilled(false);
        icon.setColorFilter(color_filter);
    }

    protected void buildActions() {
        actions = new ArrayList<>();
        addActionListener(e -> {
            for (Runnable function : actions) {
                function.run();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                in = true;
                icon.setColorFilter(hover_filter);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                in = false;
                icon.setColorFilter(color_filter);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                icon.setColorFilter(press_filter);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (in) icon.setColorFilter(hover_filter);
                else icon.setColorFilter(color_filter);
                repaint();
            }
        });
    }
}
