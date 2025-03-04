package iplm.gui.panel;

import javax.swing.*;
import java.awt.*;

public class SwitcherPanel extends JPanel {
    private CardLayout m_switcher;

    public SwitcherPanel() {
        m_switcher = new CardLayout();
        setLayout(m_switcher);
    }

    public void addPanel(JComponent component, String name) {
        add(component, name);
    }

    public void showPanel(String name) {
        m_switcher.show(this, name);
    }
}
