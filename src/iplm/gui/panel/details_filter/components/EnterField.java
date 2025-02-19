package iplm.gui.panel.details_filter.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EnterField extends JTextField {
    public EnterField(String text, String tooltip) {
        setText(text);
        setToolTipText(tooltip);
        setHorizontalAlignment(JTextField.CENTER);
        putClientProperty(FlatClientProperties.STYLE, "arc: 30");
    }
}
