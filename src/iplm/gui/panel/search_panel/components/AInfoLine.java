package iplm.gui.panel.search_panel.components;

import iplm.utility.FontUtility;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AInfoLine extends ASearchPanelLine {
    protected Color hover_color = new Color(238, 238, 238);
    private List<Runnable> actions;

    public AInfoLine() {
        FontUtility.multResize(this, 1.2f);
        setMargin(new Insets(0, 10, 0, 0));

        actions = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleActionListener();
            }
        });
    }

    public void addAction(Runnable action) { actions.add(action); }

    private void handleActionListener() {
        System.out.println("INFO CLICK");
        for (Runnable r : actions) {
            r.run();
        }
    }
}
