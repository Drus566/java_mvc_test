package iplm.gui.textarea;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import java.awt.*;

public class InputTextArea extends JScrollPane {
    private JTextArea m_text_area;

    public JTextArea getTextArea() { return m_text_area; }

    public InputTextArea() {
        m_text_area = new JTextArea();
        m_text_area.setLineWrap(true);
        m_text_area.setWrapStyleWord(true);
        setViewportView(m_text_area);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Color disabled_background = new Color(255, 255, 255, 255);
        putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));
    }
}
