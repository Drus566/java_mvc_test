package iplm.gui.combobox;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

public class DefaultComboBox extends JComboBox<String> {
    private int last_index = -1;
    private boolean edit = false;

    public DefaultComboBox() {
        putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(Color.white) + "; disabledForeground: " + ColorUtility.colourToString(Color.white));
        addItem("Бобышка");
        addItem("Гайка");
        addItem("Шайба");

        setEditable(false);

        addActionListener(e -> {
            if (!edit && last_index >= 0) setSelectedIndex(last_index);
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
        last_index = getSelectedIndex();
    }
}
