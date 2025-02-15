package iplm.gui.panel.search_panel.components;

import iplm.gui.panel.search_panel.ASearchPanelStr;
import iplm.utility.FontUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AInfoStr extends ASearchPanelStr {
    protected Color hover_color = new Color(238, 238, 238);
    private List<Runnable> actions;

    public AInfoStr() {
        FontUtility.multResize(this, 1.2f);
        setMargin(new Insets(0, 10, 0, 0));

        actions = new ArrayList<>();
        addActionListener(e -> handleActionListener());
    }

    public void addAction(Runnable action) { actions.add(action); }

    private void handleActionListener() {
        for (Runnable r : actions) {
            r.run();
        }
    }
}
