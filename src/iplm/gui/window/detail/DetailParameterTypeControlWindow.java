package iplm.gui.window.detail;

import iplm.gui.button.AddButton;
import iplm.gui.button.DeleteButton;
import iplm.gui.button.EditButton;
import iplm.gui.button.UpdateButton;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.InputText;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailParameterTypeControlWindow extends AWindow {
    private String m_detail_type_id;

    private UpdateButton m_update_btn;
    private AddButton m_add_btn;
    private EditButton m_edit_btn;
    private DeleteButton m_delete_btn;

    private InputText m_name;
    private JComboBox m_value_type;
    private DefaultTable m_table;

    // TODO: добавить тип перечисление, например (Str1, Str2) или (1,2,3) или (1-2,3-4,5-6) или (2.32,3.11) или true, false, true

    public DetailParameterTypeControlWindow() {
        build();
        afterBuild();
    }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        setTitle("Параметры деталей");

        m_update_btn = new UpdateButton();
        m_add_btn = new AddButton();
        m_edit_btn = new EditButton();
        m_delete_btn = new DeleteButton();

        m_name = new InputText();
        m_value_type = new JComboBox();
        m_table = new DefaultTable();

        m_table.addColumns(new ArrayList<>(Arrays.asList("ID", "Наименование параметра детали", "Тип данных значения параметра детали")));
        m_table.getTable().removeColumn(m_table.getTable().getColumnModel().getColumn(0));

        arrangeComponents();
    }

    private void arrangeComponents() {
        m_panel.add(m_update_btn,"al center, split 4");
        m_panel.add(m_add_btn);
        m_panel.add(m_edit_btn);
        m_panel.add(m_delete_btn, "wrap");

        m_panel.add(m_name, "al center, pushx, width 50%, wrap");
        m_panel.add(m_value_type, "al center, pushx, width 50%, wrap");
        m_panel.add(m_table.getScrollPane(), "al center, push, growy, width 90%");
    }
}
