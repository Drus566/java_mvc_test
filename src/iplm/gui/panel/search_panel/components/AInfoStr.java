package iplm.gui.panel.search_panel.components;

import iplm.gui.panel.search_panel.ASearchPanelStr;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AInfoStr extends ASearchPanelStr {
    protected Color hover_color = new Color(238, 238, 238);
    private List<Runnable> actions;

    public AInfoStr() {
        actions = new ArrayList<>();
        addActionListener(e -> handleActionListener());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setBackground(hover_color); }

            @Override
            public void mouseExited(MouseEvent e) { setBackground(background_color); }
        });
    }

    public void addAction(Runnable action) { actions.add(action); }

    private void handleActionListener() {
        for (Runnable r : actions) {
            r.run();
        }
    }
}
