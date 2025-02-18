package iplm.gui.panel.search_panel.components.button;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloseButton extends JButton {
    private static FlatSVGIcon close_svg_icon = Resources.getSVGIcon("close.svg").derive(12,12);
    private FlatSVGIcon.ColorFilter hover_filter = new FlatSVGIcon.ColorFilter(c -> new Color(78, 78, 78));
    private Runnable action;

    public CloseButton() {
        setIcon(close_svg_icon);
        setOpaque(true);
        setVisible(false);
        setContentAreaFilled(false);
        setFocusable(false);
        setToolTipText("Удалить из истории");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { close_svg_icon.setColorFilter(hover_filter); }

            @Override
            public void mouseExited(MouseEvent e) { close_svg_icon.setColorFilter(null); }
        });

        addActionListener(e -> SwingUtilities.invokeLater(action));
    }

    public void addAction(Runnable r) { action = r; }
}
