package iplm.gui.panel.details_filter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.gui.panel.details_filter.components.EnterField;
import iplm.gui.panel.details_filter.components.LabelField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailsFilter extends JPanel {
    LabelField l_opt_width, l_opt_depth, l_opt_height, l_opt_weight;
    EnterField e_opt_width, e_opt_depth, e_opt_height, e_opt_weight;
    private Color border_color = new Color(147, 179, 255);

    public DetailsFilter() {
        setLayout(new MigLayout("insets 15"));

        setBackground(Color.white);
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        setBorder(new FlatLineBorder(new Insets(0,0,0,0), border_color, 1, 30));
        setOpaque(false);
        setVisible(false);

        l_opt_width = new LabelField("ДШ", "Допуск ширины");
        l_opt_depth = new LabelField("ДГ", "Допуск глубины");
        l_opt_height = new LabelField("ДВ", "Допуск высоты");
        l_opt_weight = new LabelField("ДТ", "Допуск толщины");

        e_opt_width = new EnterField("0", "Допуск ширины");
        e_opt_depth = new EnterField("0", "Допуск глубины");
        e_opt_height = new EnterField("0", "Допуск высоты");
        e_opt_weight = new EnterField("0", "Допуск толщины");

        add(l_opt_width);
        add(e_opt_width, "wrap");
        add(l_opt_depth);
        add(e_opt_depth, "wrap");
        add(l_opt_height);
        add(e_opt_height, "wrap");
        add(l_opt_weight);
        add(e_opt_weight, "wrap");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                e.consume();
            }
        });
    }
}
