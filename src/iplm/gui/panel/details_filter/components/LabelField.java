package iplm.gui.panel.details_filter.components;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class LabelField extends JLabel {
    public LabelField(String text, String tooltip) {
        setText(text);
        setToolTipText(tooltip);
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
        setBackground(Color.white);
    }
}
