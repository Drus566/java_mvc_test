package iplm.gui.window.detail;

import iplm.data.types.DetailParameter;
import iplm.gui.textfield.InputField;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class DetailControlWindow extends AWindow {
    private JButton m_add, m_edit, m_remove, m_add_parameter;
    public String m_id;
    public InputField m_name, m_decimal_name;
    public ArrayList<DetailParameterPanel> m_detail_parameters;

    public JButton getCreateButton() { return m_add; }

    public DetailControlWindow() {
        build();
        afterBuild();
    }

    public void clear() {
        for (DetailParameterPanel dpp : m_detail_parameters) {
            m_panel.remove(dpp);
            m_panel.revalidate();
            m_panel.repaint();
        }
        m_detail_parameters.clear();
    }

    public void addParameter(String key, String value) {
        m_panel.remove(m_add_parameter);
        DetailParameterPanel dp = new DetailParameterPanel(160);
        dp.setKey(key);
        dp.setValue(value);
        m_detail_parameters.add(dp);
        dp.setVisibleDeleteButton(false);
        dp.setEditable(false);
        if (edit || create) {
            dp.setVisibleDeleteButton(true);
            dp.setEditable(true);
        }
        dp.addDeleteAction(() -> {
            m_panel.remove(dp);
            m_panel.revalidate();
            m_panel.repaint();
            m_detail_parameters.remove(dp);
        });
        m_panel.add(dp, "wrap");

        m_panel.add(m_add_parameter);
        m_panel.revalidate();
        m_panel.repaint();
        m_frame.pack();
    }


//    public DefaultTable getTable() { return m_table; }
//    public SearchBar getSearchBar() { return m_search_bar; }
//    public AddButton getAddDetailButton() { return m_add_detail_button; }

    private boolean edit = false;
    private boolean create = false;

    public boolean isCreateMode() { return create; }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));

        m_name = new InputField("Наименование", "", 160);
        m_decimal_name = new InputField("Децимальный номер", "", 160);
        m_detail_parameters = new ArrayList<>();
        m_detail_parameters.add(new DetailParameterPanel(160));

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


        for (DetailParameterPanel d : m_detail_parameters) {
            m_panel.add(d, "pushx, growx, wrap");
            d.addDeleteAction(() -> {
                m_panel.remove(d);
                m_panel.revalidate();
                m_panel.repaint();
                m_detail_parameters.remove(d);
            });
            d.setEditable(false);
        }

        m_panel.add(m_add_parameter, "wrap");

        m_name.setEditable(false);
        m_decimal_name.setEditable(false);

        m_add_parameter.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                m_panel.remove(m_add_parameter);
                DetailParameterPanel dp = new DetailParameterPanel(160);
                m_detail_parameters.add(dp);
                dp.setVisibleDeleteButton(false);
                dp.setEditable(false);
                if (edit || create) {
                    dp.setVisibleDeleteButton(true);
                    dp.setEditable(true);
                }
                dp.addDeleteAction(() -> {
                    m_panel.remove(dp);
                    m_panel.revalidate();
                    m_panel.repaint();
                    m_detail_parameters.remove(dp);
                });
                m_panel.add(dp, "wrap");

                m_panel.add(m_add_parameter);
                m_panel.revalidate();
                m_panel.repaint();
                m_frame.pack();
            });
        });

        m_edit.addActionListener(e -> {
            if (m_id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Чтобы редактировать, выберите деталь или создайте деталь");
                return;
            }
            editMode();
        });
        m_add.addActionListener(e -> createMode());
        m_remove.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Вы уверены?", "Удалить", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                // TODO action
                JOptionPane.showMessageDialog(null, "Успешно");
                m_id = "";
            }
        });

        if (create) createMode();
        else if (edit) editMode();
        else {
            m_add_parameter.setVisible(false);
            for (DetailParameterPanel dp : m_detail_parameters) {
                dp.setVisibleDeleteButton(false);
            }
        }
    }

    public void addParamAction() {

    }
    public void doCreateMode() {
        if (edit) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме редактирования");
            return;
        }
        else if (!create) createMode();
    }

    public void doEditMode() {
        if (create) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме создания");
            return;
        }
        if (!edit) editMode();
    }

    private void createMode() {
        if (!create) {
            toChangeMode();
            m_edit.setVisible(false);
            m_remove.setVisible(false);
            m_add.setText("Готово");
            create = true;
        }
        else {
            toViewMode();
            m_edit.setVisible(true);
            m_remove.setVisible(true);
            m_add.setText("Создать");
            create = false;
        }
    }

    private void editMode() {
        if (!edit) {
            toChangeMode();
            m_add.setVisible(false);
            m_remove.setVisible(false);
            m_edit.setText("Сохранить");
            edit = true;
        }
        else {
            toViewMode();
            m_add.setVisible(true);
            m_remove.setVisible(true);
            m_edit.setText("Редактировать");
            edit = false;
            JOptionPane.showMessageDialog(null, "Успешно");
        }
    }


    private void toViewMode() {
        m_add_parameter.setVisible(false);

        m_name.setEditable(false);
        m_decimal_name.setEditable(false);

        for (DetailParameterPanel dp : m_detail_parameters) {
            dp.setEditable(false);
            dp.setVisibleDeleteButton(false);
        }
    }

    private void toChangeMode() {
        m_add_parameter.setVisible(true);

        m_name.setEditable(true);
        m_decimal_name.setEditable(true);

        for (DetailParameterPanel dp : m_detail_parameters) {
            dp.setEditable(true);
            dp.setVisibleDeleteButton(true);
        }
    }

    public void updateDisplay() {
        m_panel.revalidate();
        m_panel.repaint();
    }
}
