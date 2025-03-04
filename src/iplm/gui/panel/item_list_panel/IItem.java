package iplm.gui.panel.item_list_panel;

import javax.swing.*;

public interface IItem {
    boolean contain(String text);
    void addItemListener(IItemListener listener);
    String getPayload();
    JComponent getComponent();
}
