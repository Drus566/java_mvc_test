package iplm.gui.combobox;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import java.awt.*;

public class DefaultComboBox extends JComboBox<String> {
    private String current_item;
    private boolean edit = false;

    public DefaultComboBox() {
        putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(Color.white) + "; disabledForeground: " + ColorUtility.colourToString(Color.white));
        setEditable(false);

        addActionListener(e -> {
//            if (!edit && last_index >= 0) setSelectedIndex(last_index);
            current_item = (String) getSelectedItem();
        });
    }

    public void setText(String text) {
        JTextField tf = (JTextField) getEditor().getEditorComponent();
        tf.setText(text);
    }

    public String getText() {
        JTextField tf = (JTextField) getEditor().getEditorComponent();
        return tf.getText();
    }

    public void setEnable(boolean flag) {
//        setEditable(flag);
        edit = flag;
    }
}
