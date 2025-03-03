package iplm.gui.label;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;

// https://www.formdev.com/flatlaf/typography/

public class DefaultLabel extends JLabel {
    public DefaultLabel() {}

    public DefaultLabel(String size) {
        putClientProperty(FlatClientProperties.STYLE, "font: " + size);
    }
}
