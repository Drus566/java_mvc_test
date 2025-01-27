package iplm.gui.window;

import javax.swing.*;
import java.awt.event.WindowEvent;

public abstract class AWindow {
    protected JFrame m_frame;
    protected JPanel m_panel;

    public AWindow() { m_frame = new JFrame(); }

    public JPanel getPanel() { return m_panel; }
    public JFrame getFrame() { return m_frame; }

    public abstract void build();

    public void afterBuild() {
        m_frame.setContentPane(m_panel);
        hide();
    }

    public String getName() { return m_frame.getName(); }
    public void setName(String name) { m_frame.setName(name); }
    public boolean isOpened() { return m_frame.isVisible() == true; }
    public boolean isClosed() {
        return m_frame.isVisible() == false;
    }
    public void hide() {
        m_frame.setVisible(false);
    }
    public void show() {
        m_frame.setVisible(true);
    }
    public void close() { m_frame.dispatchEvent(new WindowEvent(m_frame, WindowEvent.WINDOW_CLOSING)); }
}
