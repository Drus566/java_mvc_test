package iplm.gui.label;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

// https://www.formdev.com/flatlaf/typography/

public class DefaultLabel extends JLabel {
    public DefaultLabel(String text) {
        setText(text);
    }

    public DefaultLabel(String text, String size) {
        setText(text);
        putClientProperty(FlatClientProperties.STYLE, "font: " + size);
    }

    public DefaultLabel(ImageIcon icon) {
        setIcon(icon);
    }
}
