package iplm.gui.combobox;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class StringComboBox extends JComboBox<String> {
    private ArrayList<String> data;
    private Color disabled_text = new Color(0, 0, 0, 255);
    private JTextField text_field;
    private ArrayList<Runnable> tap_actions;

    public StringComboBox() {
        putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(Color.white) + "; disabledForeground: " + ColorUtility.colourToString(disabled_text));
        setEditable(false);
        tap_actions = new ArrayList<>();
        data = new ArrayList<>();

        text_field = (JTextField) this.getEditor().getEditorComponent();
        text_field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                for (Runnable r : tap_actions) {
                    r.run();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                for (Runnable r : tap_actions) {
                    r.run();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) { }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
                e.consume();
            }
        });

//        addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                setSelectedItem(null);
//            }
//        });

//        if (e.getStateChange() == ItemEvent.SELECTED) {
//            JComboBox cb = (JComboBox) e.getSource();
//            String selection = (String) cb.getSelectedItem();
//            JOptionPane.showMessageDialog(parent, "The selected item is: " + selection, "Information",
//                    JOptionPane.INFORMATION_MESSAGE);
//        }

    }

    public void addTapAction(Runnable action) {
        tap_actions.add(action);
    }

    public void addFilterAction() {

        tap_actions.add(() -> {
            SwingUtilities.invokeLater(() -> {
                String filter_text = text_field.getText().trim();
//                if (filter_text.isEmpty()) refreshItems();
//                else {
                    removeAllItems();
                    for (String d : data) {
                        if (d.toLowerCase().contains(filter_text.toLowerCase())) {
                            SwingUtilities.invokeLater(() -> {
                                addItem(d);
                                setSelectedItem(null);
                            });
                        }
                    }
                });
        });
    }

    public void updateData(ArrayList<String> data) {
        this.data = data;
        refreshItems();
    }

    public void addData(String data) { this.data.add(data); }

    public void setValue(String value) {
        if (value.trim().isEmpty());

        boolean found = false;
        for (String d : data) {
            if (value.equalsIgnoreCase(d)) {
                found = true;
                setSelectedItem(d);
                break;
            }
        }
        if (!found) {
            if (!value.trim().isEmpty()) addItem(value);
            setSelectedItem(value);
        }
    }

    public String getValue() {
        String result = getSelectedItemString();
        return result;
    }

    public void setEnable(boolean flag) { setEnabled(flag); }

    public void refreshItems() {
        boolean found = false;
        String current_value = getSelectedItemString();
        removeAllItems();
        for (String d : data) {
            if (!found && d.equalsIgnoreCase(current_value)) found = true;
            addItem(d);
        }
        if (!found) addItem(current_value);
    }

    public String getSelectedItemString() {
        Object selected_item = getSelectedItem();
        return selected_item == null ? "" : selected_item.toString();
    }
}
