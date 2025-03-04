package iplm.gui.label;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.Resources;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class RoundIconLabel extends JPanel {
    private JLabel icon;

    public RoundIconLabel(String icon_path, Color background_color, Color border_color, int size) {
        icon = new JLabel();
        icon.setIcon(Resources.getSVGIcon(icon_path).derive(size,size));

        setBackground(background_color);
        setLayout(new MigLayout("inset 10"));
        putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        setOpaque(true);
        add(icon);

        setBorder(new FlatLineBorder(new Insets(0,0,0,0), border_color, 1, 100));
    }
}
