package iplm.gui.panel.item_list_panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class ItemListPanel extends JPanel implements IItemListener, IItemListPanel {
    private ArrayList<IItem> m_items;
    private ArrayList<IItem> m_prev_items;
    private boolean write_mode;

    private IItemListPanelListener m_listener;

    private ArrayList<Runnable> m_click_actions;

    private int width;

    public boolean isWriteMode() { return write_mode; }

    public ItemListPanel(int width) {
        init(width);
    }

    public ItemListPanel() {
        init(-1);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private void init(int width) {
        m_click_actions = new ArrayList<>();

        setLayout(new MigLayout("inset 0, gap rel -2, debug"));
        m_items = new ArrayList<>();
        m_prev_items = new ArrayList<>();

        write_mode = false;
    }

    public void addParameter(IItem item) {
        item.addItemListener(this);
        m_items.add(item);
    }

    public void removeParameter(IItem item) {
        m_items.remove(item);
    }

    public void rememberItems() {
        m_prev_items.clear();
        for (IItem i : m_items) {
            m_prev_items.add(i);
            i.rememberLast();
        }
    }

    public void fillLastItems() {
        m_items.clear();
        for (IItem i : m_prev_items) {
            m_items.add(i);
            i.fillLast();
        }
    }

    public void updateUI() {
        if (m_items == null) return;

        removeAll();
        for (IItem item : m_items) {
            String style_params = "al center, pushx, wrap";
            if (width > 0) style_params = "width " + width + ", wrap";
            add(item.getComponent(), style_params);
        }

        revalidate();
        repaint();
    }

    public void toWriteMode() {
        if (isWriteMode()) return;
        for (IItem i : m_items) {
            i.toWriteMode();
        }
        write_mode = true;
    }

    public void toReadMode() {
        if (!isWriteMode()) return;
        for (IItem i : m_items) {
            i.toReadMode();
        }
        write_mode = false;
    }

    public void addClickAction(Runnable action) { m_click_actions.add(action); }

    @Override
    public void onDelete(IItem item) {
        for (IItem i : m_items) {
            if (i == item) {
                remove(item.getComponent());
                break;
            }
        }
        m_items.remove(item);
        updateUI();
    }

    @Override
    public void onPress(IItem item) {
        m_listener.onPress(item.getPayload());
    }

    @Override
    public void addItemListPanelListener(IItemListPanelListener listener) {
        m_listener = listener;
    }
}
