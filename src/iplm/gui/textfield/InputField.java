package iplm.gui.textfield;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputField extends JPanel {
    private JLabel m_label;
    private JTextField m_input_field;
    private ArrayList<Runnable> m_tap_actions;
    private ArrayList<Runnable> m_press_actions;

    public String getText() { return m_input_field.getText(); }
    public void setText(String text) { m_input_field.setText(text); }

    public void setEditable(boolean flag) {
        m_input_field.setEditable(flag);
    }

    public void addTapAction(Runnable action) { m_tap_actions.add(action); }

    public void addPressAction(Runnable action) { m_press_actions.add(action); }

    public InputField(String label_name, String input_field_name, int width_label) {
        setLayout(new MigLayout("inset 3"));

        m_tap_actions = new ArrayList<>();
        m_press_actions = new ArrayList<>();

        m_label = new JLabel(label_name);
        m_input_field = new JTextField(input_field_name);

        Color disabled_background = new Color(255, 255, 255, 255);
        m_input_field.putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));


        add(m_label, "width " + width_label + "!");
        add(m_input_field, "width " + width_label + "pref:" + width_label*2);

        m_input_field.getDocument().addDocumentListener(new DocumentListener() {
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

        m_input_field.addActionListener(e -> {
            for (Runnable r : m_press_actions) {
                r.run();
            }
        });
    }
}
