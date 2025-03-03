package iplm.gui.label;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

// https://www.formdev.com/flatlaf/typography/

public class DefaultLabel extends JLabel {
    public DefaultLabel(String text) {
        setText(text);
    }

    public DefaultLabel(ImageIcon icon) {
        setIcon(icon);
    }

    public DefaultLabel(String size) {
        putClientProperty(FlatClientProperties.STYLE, "font: " + size);
    }
}
