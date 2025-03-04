package iplm.gui.panel.item_list_panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class ItemListPanel extends JPanel implements IItemListener {
    private ArrayList<IItem> m_items;
    private ArrayList<IItem> m_prev_items;
    private boolean write_mode;

    public boolean isWriteMode() { return write_mode; }

    public ItemListPanel() {
        setLayout(new MigLayout());
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
            add(item.getComponent(), "wrap");
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
}
