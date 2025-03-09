package iplm.gui.combobox;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.utility.ColorUtility;

import javax.swing.*;
import java.awt.*;

public class DefaultComboBox extends JComboBox<String> {

    Color disabled_text = new Color(0, 0, 0, 255);

    public DefaultComboBox() {
        putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(Color.white) + "; disabledForeground: " + ColorUtility.colourToString(disabled_text));
        setEditable(false);
    }

    public void setText(String text) {
        boolean found = false;
        for (int i = 0; i < this.getComponentCount(); i++) {
            if (text.equalsIgnoreCase(getItemAt(i))) {
                found = true;
                setSelectedIndex(i);
                break;
            }
        }
        if (!found) {
            addItem(text);
            setSelectedItem(text);
        }
//        JTextField tf = (JTextField) getEditor().getEditorComponent();
//        tf.setText(text);
    }

    public String getText() { return getSelectedItem().toString(); }

    public void setEnable(boolean flag) {
        setEnabled(flag);
    }
}
