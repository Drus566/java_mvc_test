package iplm.gui.combobox;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.gui.button.DeleteButton;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComboBoxInput extends JPanel {
    protected DeleteButton m_delete_btn;
    protected JComboBox<String> m_name;
    protected JTextField m_value;

    protected String m_last_name;
    protected String m_last_value;

    protected ArrayList<Runnable> m_delete_actions;

    public String getKey() { return m_name.getSelectedItem().toString().trim(); }
    public String getValue() { return m_value.getText().trim(); }

    public void setKey(String key) {
        for (int i = 0; i < m_name.getItemCount(); i++) {
            if (m_name.getItemAt(i).equalsIgnoreCase(key)) {
                m_name.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setValue(String value) { m_value.setText(value); }

    public void setEditable(boolean flag) {
        m_name.setEnabled(flag);
        m_value.setEditable(flag);
        setVisibleDeleteButton(flag);
    }

    public ComboBoxInput(int width_name) {
        Color disabled_background = new Color(255, 255, 255, 255);
        Color disabled_text = new Color(0, 0, 0, 255);

        setLayout(new MigLayout("inset 5"));
        m_delete_actions = new ArrayList<>();

        m_delete_btn = new DeleteButton(18, 18);
        m_name = new JComboBox<>();
        m_value = new JTextField();

        m_name.setEditable(false);

        m_name.putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(disabled_background) + "; disabledForeground: " + ColorUtility.colourToString(disabled_text));
        m_value.putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));

//        m_value.setText("");

        add(m_name, "width " + width_name + "!");
        add(m_value, "width " + width_name + "pref:" + width_name*2);
        add(m_delete_btn);

        m_delete_btn.addActionListener(e -> {
            for (Runnable a : m_delete_actions) {
                SwingUtilities.invokeLater(a);
            }
        });
    }

    public void setVisibleDeleteButton(boolean flag) {
        boolean in = componentIn(m_delete_btn);
        if (flag && !in) add(m_delete_btn);
        else if (!flag && in) remove(m_delete_btn);
    }

    public void addDeleteAction(Runnable action) { m_delete_actions.add(action); }

    public boolean componentIn(JComponent component) {
        boolean result = false;
        Component[] components = getComponents();
        for (Component c : components) {
            if (c == component) {
                result = true;
                break;
            }
        }
        return result;
    }}
