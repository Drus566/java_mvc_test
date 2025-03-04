package iplm.gui.window;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public abstract class AWindow {
    protected JFrame m_frame;
    protected JPanel m_panel;
    protected JMenuBar m_menu_bar;
    protected JLayer m_layer;
    protected JScrollPane m_scroll_pane;

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
            if (m_layer != null) m_scroll_pane.setViewportView(m_layer);
            else m_scroll_pane.setViewportView(m_panel);
            m_frame.setContentPane(m_scroll_pane);
        }
        if (m_menu_bar != null) m_frame.setJMenuBar(m_menu_bar);
        m_frame.pack();
        m_frame.setMinimumSize(m_frame.getPreferredSize());
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

//    public void packIf() {
//        if (m_frame.getWidth() < m_panel.getWidth() || m_frame.getHeight() < m_panel.getHeight()) {
//            m_frame.pack();
//        }
//
//        System.out.println("Frame");
//        System.out.println(m_frame.getWidth());
//        System.out.println(m_frame.getHeight());
//        System.out.println("Panel");
//        System.out.println(m_panel.getWidth());
//        System.out.println(m_panel.getHeight());
//        System.out.println("ScrollPane");
//        System.out.println(m_scroll_pane.getWidth());
//        System.out.println(m_scroll_pane.getHeight());
//     }
}
