package iplm.gui.panel.item_list_panel.items;

import iplm.gui.combobox.ComboBoxInput;
import iplm.gui.panel.item_list_panel.IItem;
import iplm.gui.panel.item_list_panel.IItemListener;

import javax.swing.*;

public class ComboBoxInputItem extends ComboBoxInput implements IItem {
    private IItemListener m_item_listener;

    public ComboBoxInputItem(int width_name) {
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
        m_last_name = (String)m_name.getSelectedItem();
        m_last_value = m_value.getText();
    }

    @Override
    public void fillLast() {
        m_name.setSelectedItem(m_last_name);
        m_value.setText(m_last_value);
    }
}
