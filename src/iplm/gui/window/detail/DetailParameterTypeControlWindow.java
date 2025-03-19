package iplm.gui.window.detail;

import iplm.data.types.DetailParameterType;
import iplm.gui.button.AddButton;
import iplm.gui.button.DeleteButton;
import iplm.gui.button.EditButton;
import iplm.gui.button.UpdateButton;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.InputText;
import iplm.gui.textfield.RowSelectionList;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailParameterTypeControlWindow extends AWindow {
    public String getId() {
        String result = null;
        int sr = m_table.getTable().getSelectedRow();
        if (sr != -1) result = (String) m_table.getTableModel().getValueAt(sr, 0);
        return result;
    }

    public String getTypeName() {
        String result = null;
        int sr = m_table.getTable().getSelectedRow();
        if (sr != -1) result = (String) m_table.getTableModel().getValueAt(sr, 1);
        return result;
    }

    private UpdateButton m_update_btn;
    private AddButton m_add_btn;
    private EditButton m_edit_btn;
    private DeleteButton m_delete_btn;

    private InputText m_alias_input;
    private RowSelectionList m_name_input;
    private JComboBox m_value_type;
    private DefaultTable m_table;

    public UpdateButton getUpdateButton() { return m_update_btn; }
    public AddButton getAddButton() { return m_add_btn; }
    public EditButton getEditButton() { return m_edit_btn; }
    public DeleteButton getDeleteButton() { return m_delete_btn; }
    public InputText getAliasInput() { return m_alias_input; }
    public RowSelectionList getNameInput() { return m_name_input; }

    public JComboBox getValueType() { return m_value_type; }
    public DefaultTable getTable() { return m_table; }

    // TODO: добавить тип перечисление, например (Str1, Str2) или (1,2,3) или (1-2,3-4,5-6) или (2.32,3.11) или true, false, true

    public DetailParameterTypeControlWindow() {
        build();
        afterBuild();
    }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        setTitle("Типы параметров деталей");

        m_update_btn = new UpdateButton();
        m_add_btn = new AddButton();
        m_edit_btn = new EditButton();
        m_delete_btn = new DeleteButton();

        m_name_input = new RowSelectionList();
        m_alias_input = new InputText();
        m_value_type = new JComboBox();
        m_table = new DefaultTable();

        m_table.addColumns(new ArrayList<>(Arrays.asList("ID", "Наименование", "Псевдоним", "Тип данных")));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(0));

        m_table.getTable().getSelectionModel().addListSelectionListener(e -> {
            int sr = m_table.getTable().getSelectedRow();
            if (sr == -1) return;
            m_name_input.setValue((String) m_table.getTableModel().getValueAt(sr, 1));
            m_alias_input.setText((String) m_table.getTableModel().getValueAt(sr, 2));
            m_value_type.setSelectedItem((String) m_table.getTableModel().getValueAt(sr, 3));
        });

        for (DetailParameterType.Type dpp : DetailParameterType.Type.values()) {
            m_value_type.addItem(dpp.s());
        }

        m_name_input.addCallback(() -> {
            m_table.selectRowWhere(m_name_input.getValue(), 1);
            m_table.scrollToSelectedRow();
        });

        arrangeComponents();
    }

    private void arrangeComponents() {
        m_panel.add(m_update_btn,"al center, split 4");
        m_panel.add(m_add_btn);
        m_panel.add(m_edit_btn);
        m_panel.add(m_delete_btn, "wrap");

        m_panel.add(new JLabel("Наименование"), "al center, width 160!, split 2");
        m_panel.add(m_name_input, " pushx, width 50%, wrap");

        m_panel.add(new JLabel("Псевдоним"), "al center, width 160!, split 2");
        m_panel.add(m_alias_input, " pushx, width 50%, wrap");

        m_panel.add(new JLabel("Тип данных параметра"), "al center, width 160!, split 2");
        m_panel.add(m_value_type, " pushx, width 50%, wrap");

        m_panel.add(m_table.getScrollPane(), "al center, push, growy, width 90%");
    }
}
