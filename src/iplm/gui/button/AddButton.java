package iplm.gui.button;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AddButton extends JButton {
    private List<Runnable> actions;
    private FlatSVGIcon icon;
    private FlatSVGIcon.ColorFilter color_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 182, 40));
    private FlatSVGIcon.ColorFilter hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(14, 229, 42));
    private FlatSVGIcon.ColorFilter press_filter = new FlatSVGIcon.ColorFilter(c -> new Color(58, 147, 65));
    private boolean in = false;

    public AddButton() {
        actions = new ArrayList<>();

        icon = Resources.getSVGIcon("add.svg").derive(24,24);
        setIcon(icon);
        setContentAreaFilled(false);
        icon.setColorFilter(color_filter);

        putClientProperty(FlatClientProperties.STYLE, "arc: 100");

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

    public void addAction(Runnable function) {
        actions.add(function);
    }
}
