package iplm.gui.panel.item_list_panel;

import javax.swing.*;

public interface IItemListener {
    void onDelete(IItem item);
    void onPress(IItem item);
}
