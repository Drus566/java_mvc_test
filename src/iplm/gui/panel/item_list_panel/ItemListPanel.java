package iplm.gui.panel.item_list_panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class ItemListPanel extends JPanel implements IItemListener {
    private ArrayList<JComponent> m_items;
    private ArrayList<JComponent> m_prev_items;
    private boolean write_mode;
    private boolean with_prev_items;

    public boolean isWriteMode() { return write_mode; }

    public ItemListPanel() {
        setLayout(new MigLayout());
        write_mode = false;
        m_items = new ArrayList<>();
        m_prev_items = new ArrayList<>();
    }

    public void addParameter(JComponent component) {
        m_items.add(component);
    }

    public void removeParamater(JComponent component) {
        m_items.remove(component);
    }

    public void updateUI() {
        removeAll();
        for (JComponent item : m_items) {
            add(item, "wrap");
        }
        revalidate();
        repaint();
    }

    @Override
    public void onDelete(IItem item) {
        JComponent i = item.getComponent();
        for (JComponent c : m_items) {
            if (c == i) {
                remove(i);
                break;
            }
        }
        m_items.remove(i);
    }
}
