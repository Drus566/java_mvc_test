package iplm.gui.window.detail;

import iplm.gui.button.AddButton;
import iplm.gui.button.DeleteButton;
import iplm.gui.button.EditButton;
import iplm.gui.button.UpdateButton;
import iplm.gui.table.DefaultTable;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailNameControlWindow extends AWindow {
    public String getId() {
        String result = null;
        int sr = m_table.getTable().getSelectedRow();
        if (sr != -1) result = (String) m_table.getTableModel().getValueAt(sr, 0);
        return result;
    }

    private UpdateButton m_update_btn;
    private AddButton m_add_btn;
    private EditButton m_edit_btn;
    private DeleteButton m_delete_btn;

    private JTextField m_name_input;
    private DefaultTable m_table;

    public UpdateButton getUpdateButton() { return m_update_btn; }
    public AddButton getAddButton() { return m_add_btn; }
    public EditButton getEditButton() { return m_edit_btn; }
    public DeleteButton getDeleteButton() { return m_delete_btn; }
    public JTextField getNameInput() { return m_name_input; }
    public DefaultTable getTable() { return m_table; }

    public DetailNameControlWindow() {
        build();
        afterBuild();
    }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        setTitle("Наименования деталей");

        m_update_btn = new UpdateButton();
        m_add_btn = new AddButton();
        m_edit_btn = new EditButton();
        m_delete_btn = new DeleteButton();

        m_name_input = new JTextField();
        m_table = new DefaultTable();

        m_table.addColumns(new ArrayList<>(Arrays.asList("ID", "Наименование детали")));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(0));

        m_table.getTable().getSelectionModel().addListSelectionListener(e -> {
            int sr = m_table.getTable().getSelectedRow();
            if (sr == -1) return;
            m_name_input.setText((String) m_table.getTableModel().getValueAt(sr, 1));
        });

        arrangeComponents();
    }

    private void arrangeComponents() {
        m_panel.add(m_update_btn, "al center, split 4");
        m_panel.add(m_add_btn);
        m_panel.add(m_edit_btn);
        m_panel.add(m_delete_btn, "wrap");

        m_panel.add(m_name_input, "al center, pushx, width 50%, wrap");
        m_panel.add(m_table.getScrollPane(), "al center, push, growy, width 90%");
    }
}
