package iplm.gui.panel.item_list_panel.items;

import iplm.gui.combobox.RowListInput;
import iplm.gui.panel.item_list_panel.IItem;
import iplm.gui.panel.item_list_panel.IItemListener;

import javax.swing.*;

public class RowListInputItem extends RowListInput implements IItem {
    private IItemListener m_item_listener;

    public RowListInputItem(int width_name) {
        super(width_name);
        m_delete_btn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> m_item_listener.onDelete(this));
        });
    }

    @Override
    public void addItemListener(IItemListener listener) { m_item_listener = listener; }

    @Override
    public JComponent getComponent() { return this; }

    @Override
    public void toWriteMode() { setEditable(true); }

    @Override
    public void toReadMode() { setEditable(false); }

    @Override
    public void rememberLast() {
        m_last_name = m_name.getText().trim();
        m_last_value = m_value.getText();
    }

    @Override
    public void fillLast() {
        m_name.setValue(m_last_name);
        m_value.setText(m_last_value);
    }
}
