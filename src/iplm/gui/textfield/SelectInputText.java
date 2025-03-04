package iplm.gui.textfield;

import iplm.gui.button.AddButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class SelectInputText extends JTextField {
    private JPanel parent_panel;
    private JPanel select_panel;
    private JScrollPane scroll_pane;
    private AddButton add_button;

    private ArrayList<String> items;

    public SelectInputText() {
        parent_panel = new JPanel();
        select_panel = new JPanel(new MigLayout());
        scroll_pane = new JScrollPane();

        items = new ArrayList<>();
        add_button = new AddButton();

        scroll_pane.setViewportView(select_panel);
        parent_panel.add(scroll_pane);
    }

    public void addItem(String item) { items.add(item); }

    public boolean isExists(String item) {
        boolean result = false;
        for (String i : items) {
            if (i.equalsIgnoreCase(item)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void removeItem(String item) {
        for (String i : items) {
            if (i.equalsIgnoreCase(item));
            items.remove(i);
        }
    }

    public void updateUI() {
        revalidate();
        repaint();
    }

    public AddButton getAddButton() { return add_button; }

    public void addButtonAction(Runnable action) { add_button.addAction(action); }
}
