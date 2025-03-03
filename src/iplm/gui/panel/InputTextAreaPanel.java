package iplm.gui.panel;

import iplm.gui.label.DefaultLabel;
import iplm.gui.textarea.InputTextArea;

import javax.swing.*;

public class InputTextAreaPanel extends JPanel {
    private DefaultLabel label;
    private InputTextArea input_text_area;

    public InputTextAreaPanel() {
        label = new DefaultLabel();
        input_text_area = new InputTextArea();
    }
}
