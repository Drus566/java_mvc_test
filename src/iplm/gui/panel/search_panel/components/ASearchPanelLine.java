package iplm.gui.panel.search_panel.components;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import java.awt.*;

public abstract class ASearchPanelLine extends JTextField {
    public enum Type {
        DESCRIBE,
        INFO;
    };

    public int ID;
    public ASearchPanelLine.Type type;

    protected Color foreground_color = new Color(71, 71, 71);
    protected Color background_color = Color.white;

    public ASearchPanelLine() {
        putClientProperty(FlatClientProperties.STYLE, "arc: 30; borderColor: " + ColorUtility.colourToString(Color.white));

        setBackground(Color.white);
        setForeground(foreground_color);
        setEditable(false);
        setFocusable(false);
    }
}
