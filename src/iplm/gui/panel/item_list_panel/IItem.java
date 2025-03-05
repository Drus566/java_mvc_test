package iplm.gui.panel.item_list_panel;

import javax.swing.*;

public interface IItem {
    void addItemListener(IItemListener listener);
    JComponent getComponent();

    default boolean contain(String text) { return false; }
    default String getPayload() { return null; }
    default void toWriteMode() {}
    default void toReadMode() {}
    default void rememberLast() {}
    default void fillLast() {}
}
