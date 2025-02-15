package iplm.gui.panel.search_panel;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;
import iplm.utility.FontUtility;

import javax.swing.*;
import java.awt.*;

public abstract class ASearchPanelStr extends JTextField {
    public SearchPanelStrType type;

    protected Color foreground_color = new Color(71, 71, 71);
    protected Color background_color = Color.white;

    public ASearchPanelStr() {
        putClientProperty(FlatClientProperties.STYLE, "arc: 30; borderColor: " + ColorUtility.colourToString(Color.white));

        setBackground(Color.white);
        setForeground(foreground_color);
        setEditable(false);
        setFocusable(false);
    }
}
