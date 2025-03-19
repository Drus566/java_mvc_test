package iplm.gui.panel.item_list_panel;

import com.formdev.flatlaf.FlatClientProperties;
import iplm.gui.button.DeleteButton;
import iplm.gui.textfield.RowSelectionList;
import iplm.utility.ColorUtility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Item extends JPanel implements IItem {
    private IItemListener m_item_listener;

    protected DeleteButton m_delete_btn;
    protected RowSelectionList m_list;
    protected JTextField m_value;

    protected String m_last_name;
    protected String m_last_value;

    protected ArrayList<Runnable> m_delete_actions;

    public String getKey() { return m_list.getText().trim(); }
    public String getValue() { return m_value.getText().trim(); }

    public void setKey(String key) { m_list.addData(key); }
    public void setValue(String value) { m_value.setText(value); }

    public boolean isRowExists(String row) { return m_list.isDataExists(row); }

    public void setEditable(boolean flag) {
        m_list.setEnable(flag);
        m_list.setEditable(flag);
        m_value.setEditable(flag);
        setVisibleDeleteButton(flag);
    }

    public void updateData(HashSet<String> data) { m_list.updateData(data); }
    public void updateData(ArrayList<String> data) { m_list.updateData(data); }

    public Item(int width_name) {
        Color disabled_background = new Color(255, 255, 255, 255);
        Color disabled_text = new Color(0, 0, 0, 255);

        setLayout(new MigLayout("inset 5"));
        m_delete_actions = new ArrayList<>();

        m_delete_btn = new DeleteButton(18, 18);
        m_list = new RowSelectionList();
        m_value = new JTextField();

        m_list.setEditable(false);
        m_list.setMaximumVisibleRows(4);

//        m_name.putClientProperty(FlatClientProperties.STYLE, "disabledBackground: " + ColorUtility.colourToString(disabled_background) + "; disabledForeground: " + ColorUtility.colourToString(disabled_text));
        m_value.putClientProperty(FlatClientProperties.STYLE, "inactiveBackground: " + ColorUtility.colourToString(disabled_background));

//        m_value.setText("");

        add(m_list, "width " + width_name + "!");
        add(m_value, "width " + width_name + "pref:" + width_name*2);
        add(m_delete_btn);

        m_delete_btn.addActionListener(e -> {
            for (Runnable a : m_delete_actions) {
                SwingUtilities.invokeLater(a);
            }
        });

        m_delete_btn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> m_item_listener.onDelete(this));
        });
    }

    public void setVisibleDeleteButton(boolean flag) {
        boolean in = componentIn(m_delete_btn);
        if (flag && !in) add(m_delete_btn);
        else if (!flag && in) remove(m_delete_btn);
    }

    public void addDeleteAction(Runnable action) { m_delete_actions.add(action); }

    public boolean componentIn(JComponent component) {
        boolean result = false;
        Component[] components = getComponents();
        for (Component c : components) {
            if (c == component) {
                result = true;
                break;
            }
        }
        return result;
    }
    @Override
    public void updateItem(Object data) {
        HashSet<String> set = (HashSet<String>) data;
        m_list.updateData(set);
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
        m_last_name = m_list.getText().trim();
        m_last_value = m_value.getText();
    }

    @Override
    public void fillLast() {
        m_list.setValue(m_last_name);
        m_value.setText(m_last_value);
    }
}
