package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class InputText extends JTextField {
    private ArrayList<Runnable> m_tap_actions;
    private ArrayList<Runnable> m_press_actions;

    public InputText() {
        Color disabled_background = new Color(255, 255, 255, 255);
        putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                for (Runnable r : m_tap_actions) {
                    r.run();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                for (Runnable r : m_tap_actions) {
                    r.run();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                for (Runnable r : m_tap_actions) {
                    r.run();
                }
            }
        });

        addActionListener(e -> {
            for (Runnable r : m_press_actions) {
                r.run();
            }
        });
    }

}
