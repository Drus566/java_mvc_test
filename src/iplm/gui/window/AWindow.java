package iplm.gui.window;

import iplm.utility.ScreenUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public abstract class AWindow {
    protected JFrame m_frame;
    protected JPanel m_panel;
    protected JMenuBar m_menu_bar;
    protected JLayer m_layer;
    protected JScrollPane m_scroll_pane;

    protected ArrayList<Runnable> m_component_resized_callbacks;
    protected ArrayList<Runnable> m_startup_actions;

    public AWindow() {
        m_frame = new JFrame();

        m_component_resized_callbacks = new ArrayList<>();
        m_startup_actions = new ArrayList<>();
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
    public JLayer getLayer() { return m_layer; }
    public JFrame getFrame() { return m_frame; }
    public JScrollPane getScrollPane() { return m_scroll_pane; }

    public abstract void build();

    protected void afterBuild() {
        setName(this.getClass().getSimpleName());
        if (m_scroll_pane == null) {
            if (m_layer != null) m_frame.add(m_layer);
            else m_frame.setContentPane(m_panel);
        }
        else {
            m_scroll_pane.setViewportView(m_panel);
            m_frame.setContentPane(m_scroll_pane);
        }
        if (m_menu_bar != null) m_frame.setJMenuBar(m_menu_bar);
        m_frame.pack();
        m_frame.setMinimumSize(m_frame.getSize());
        m_frame.setSize(new Dimension(ScreenUtility.getWidth() / 2, (int) (ScreenUtility.getHeight() / 1.1f)));

        m_frame.setLocationRelativeTo(null);
        hide();
    }

    public void addVisibleAction(Runnable action) { m_startup_actions.add(action); }

    public String getName() { return m_frame.getName(); }
    public void setName(String name) { m_frame.setName(name); }
    public boolean isOpened() { return m_frame.isVisible() == true; }
    public boolean isClosed() {
        return m_frame.isVisible() == false;
    }
    public void hide() { SwingUtilities.invokeLater(() -> m_frame.setVisible(false)); }
    public void show() {
        for (Runnable a : m_startup_actions) { a.run(); }
        SwingUtilities.invokeLater(() -> m_frame.setVisible(true));
    }
    public void close() { m_frame.dispatchEvent(new WindowEvent(m_frame, WindowEvent.WINDOW_CLOSING)); }
    public String getTitle() { return m_frame.getTitle(); }
    public void setTitle(String title) { m_frame.setTitle(title); }
}
