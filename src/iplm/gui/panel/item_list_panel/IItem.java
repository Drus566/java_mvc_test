package iplm.gui.panel.item_list_panel;

import javax.swing.*;
import java.util.ArrayList;

public interface IItem {
    void addItemListener(IItemListener listener);
    JComponent getComponent();

    default void updateItem(Object data) {};
    default boolean contain(String text) { return false; }
    default String getPayload() { return null; }
    default void toWriteMode() {}
    default void toReadMode() {}
    default void rememberLast() {}
    default void fillLast() {}
}
