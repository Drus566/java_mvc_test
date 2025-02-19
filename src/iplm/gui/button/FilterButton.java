package iplm.gui.button;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FilterButton extends JButton {
    private List<Runnable> actions;
    private FlatSVGIcon icon;
    private FlatSVGIcon.ColorFilter color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(128, 140, 145));
    private FlatSVGIcon.ColorFilter hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(182, 255, 191));
    private FlatSVGIcon.ColorFilter press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(113, 255, 122));
    private boolean in = false;

    public FilterButton() {
        actions = new ArrayList<>();

        icon = Resources.getSVGIcon("filter.svg").derive(18,18);
        setIcon(icon);
        setContentAreaFilled(false);
        icon.setColorFilter(color_filter);
        setToolTipText("Фильтры");

        addActionListener(e -> {
            for (Runnable function : actions) {
                SwingUtilities.invokeLater(function);
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

    public void addAction(Runnable function) {
        actions.add(function);
    }
}
