package iplm.gui.window.detail;

import iplm.gui.textfield.InputField;
import iplm.gui.panel.detail_parameter.DetailParameter;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DetailControlWindow extends AWindow {
    private String m_id;
    private JButton m_add, m_edit, m_remove, m_add_parameter;
    private InputField m_name, m_decimal_name;
    private ArrayList<DetailParameter> m_detail_parameters;

    private Runnable delete_parameter_action;

    public DetailControlWindow() {
        build();
        afterBuild();
    }

//    public DefaultTable getTable() { return m_table; }
//    public SearchBar getSearchBar() { return m_search_bar; }
//    public AddButton getAddDetailButton() { return m_add_detail_button; }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10, debug"));

        m_name = new InputField("Наименование", "", 160);
        m_decimal_name = new InputField("Децимальный номер", "", 160);
        m_detail_parameters = new ArrayList<>();
        m_detail_parameters.add(new DetailParameter(160));
        m_detail_parameters.add(new DetailParameter(160));
        m_detail_parameters.add(new DetailParameter(160));

        m_id = new String();
        m_add = new JButton("Создать");
        m_edit = new JButton("Редактировать");
        m_remove = new JButton("Удалить");
        m_add_parameter = new JButton("Добавить параметр");

        m_panel.add(m_add, "split 3");
        m_panel.add(m_edit);
        m_panel.add(m_remove, "wrap");
        m_panel.add(m_name, "al center, pushx, growx, wrap");
        m_panel.add(m_decimal_name, "al center, pushx, growx, wrap");

        for (DetailParameter d : m_detail_parameters) {
            m_panel.add(d, "pushx, growx, wrap");
            d.addDeleteAction(() -> {
                m_panel.remove(d);
                m_panel.revalidate();
                m_panel.repaint();
            });
            d.setEditable(false);
        }

        m_panel.add(m_add_parameter, "wrap");

        m_name.setEditable(false);
        m_decimal_name.setEditable(false);

        m_add_parameter.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                m_panel.remove(m_add_parameter);
                m_panel.add(new DetailParameter(160), "wrap");
                m_panel.add(m_add_parameter);
                m_panel.revalidate();
                m_panel.repaint();
                m_frame.pack();
            });
        });

        delete_parameter_action = () -> {
            m_panel.remove(d);
            m_panel.revalidate();
            m_panel.repaint();
        }
//        m_describe = new JTextArea();

//        buildTable();
//        buildSearchBar();
//        buildSearchPanel();
//        buildAddDetailButton();
//        buildDetailsFilter();
//        buildInterceptLayer();
//        arrangeComponents();
    }
}
