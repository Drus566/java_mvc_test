package iplm.gui.button;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Application;
import iplm.Resources;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AddDetail extends JButton {
    private List<Runnable> actions;

    public AddDetail() {
        actions = new ArrayList<>();
        FlatSVGIcon icon = Resources.getSVGIcon("add.svg");

//        setIcon(icon);
        setIcon(icon.derive(24,24));
        setContentAreaFilled(false);
        addActionListener(e -> {
            for (Runnable function : actions) {
                function.run();
            }
        });

        setToolTipText("Добавить деталь");
    }

    public void addAction(Runnable function) {
        actions.add(function);
    }
}
