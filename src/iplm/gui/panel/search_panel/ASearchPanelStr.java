package iplm.gui.panel.search_panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import iplm.Resources;
import iplm.utility.ColorUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ASearchPanelStr extends JTextField {
    public SearchPanelStrType type;

    protected Color str_color = new Color(103, 103, 103);
    protected Color background_color = Color.white;

    public ASearchPanelStr() {
        putClientProperty(FlatClientProperties.STYLE, "arc: 30; borderColor: " + ColorUtility.colourToString(Color.white));
        setBackground(Color.white);
        setForeground(str_color);
        setEditable(false);
    }
}
