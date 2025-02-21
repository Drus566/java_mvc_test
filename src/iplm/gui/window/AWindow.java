package iplm.gui.window;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AWindow {
    protected JFrame m_frame;
    protected JPanel m_panel;
    protected JMenuBar m_menu_bar;
    protected JMenu m_menu;
    protected JLayer m_layer;

    protected ArrayList<Runnable> m_component_resized_callbacks;

    public AWindow() {
        m_frame = new JFrame();

        m_component_resized_callbacks = new ArrayList<>();
        m_frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Runnable r : m_component_resized_callbacks) {
                    SwingUtilities.invokeLater(r);
                }
            }
        });
    }

    public JPanel getPanel() { return m_panel; }
    public JFrame getFrame() { return m_frame; }

    public abstract void build();

    protected void afterBuild() {
        setName(this.getClass().getSimpleName());
        if (m_layer != null) m_frame.add(m_layer);
        else m_frame.setContentPane(m_panel);
        m_frame.pack();
        hide();
    }

    public String getName() { return m_frame.getName(); }
    public void setName(String name) { m_frame.setName(name); }
    public boolean isOpened() { return m_frame.isVisible() == true; }
    public boolean isClosed() {
        return m_frame.isVisible() == false;
    }
    public void hide() { SwingUtilities.invokeLater(() -> m_frame.setVisible(false)); }
    public void show() { SwingUtilities.invokeLater(() -> m_frame.setVisible(true)); }
    public void close() { m_frame.dispatchEvent(new WindowEvent(m_frame, WindowEvent.WINDOW_CLOSING)); }
    public String getTitle() { return m_frame.getTitle(); }
    public void setTitle(String title) { m_frame.setTitle(title); }
}
