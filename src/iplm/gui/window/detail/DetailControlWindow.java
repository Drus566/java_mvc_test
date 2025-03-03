package iplm.gui.window.detail;

import iplm.Resources;
import iplm.gui.button.*;
import iplm.gui.label.DefaultLabel;
import iplm.gui.textarea.InputTextArea;
import iplm.gui.textfield.InputField;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.textfield.InputText;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailControlWindow extends AWindow {
    public String m_detail_id;

    /* NEW */
    // TOP
    private JPanel top_panel;
    // Обновить данные детали
    private UpdateButton m_update_btn;
    // Иконка детали
    private JLabel detail_icon;
    // Панель кнопок
    private JPanel mode_btn_panel;
    // Создать деталь
    private AddButton m_add_detail_btn;
    // Редактировать деталь
    private EditButton m_edit_detail_btn;
    // Удалить деталь
    private DeleteButton m_delete_detail_btn;
    // Скачать деталь
    private DownloadButton m_download_detail_btn;
    // TODO: Confirm button
    // TODO: Close button

    // BODY
    // Метка имени детали
    private DefaultLabel detail_name_label;
    // Метка децимального номера детали
    private DefaultLabel detail_decimal_number_label;
    // Ввод децимального номера детали
    private InputText decimal_number_input;
    // Метка описания
    private DefaultLabel describe_label;
    // Панель описание детали
    private InputTextArea description_input_panel;
    // Метка параметров
    private DefaultLabel parameters_label;
    // Создать параметр детали
    private AddButton m_add_detail_parameter_btn;
    // Окно контроля параметров детали
    private EditButton m_edit_detail_parameter_btn;

    private boolean edit_mode = false;
    private boolean create_mode = false;

    // TOP
    public UpdateButton getUpdateButton() { return m_update_btn; }
    public JLabel getDetailIcon() { return detail_icon; }
    public AddButton getAddDetailBtn() { return m_add_detail_btn; }
    public EditButton getEditDetailBtn() { return m_edit_detail_btn; }
    public DeleteButton getDeleteDetailBtn() { return  m_delete_detail_btn; };
    public DownloadButton getDownloadDetailBtn() { return m_download_detail_btn; }

    // BODY
    public InputText decimalNumberInput() { return decimal_number_input; }
    public InputTextArea descriptionInputPanel() { return description_input_panel; }
    public AddButton addDetailParameterBtn() { return m_add_detail_btn; }
    public EditButton editDetailParameterBtn() { return m_edit_detail_parameter_btn; }


//    public boolean isCreateMode() { return create_mode; }
//    public boolean isEditMode() { return edit_mode; }


    public void buildTop() {
        top_panel = new JPanel(new MigLayout("inset 10"));
        m_update_btn = new UpdateButton();
        detail_icon = new DefaultLabel(Resources.getSVGIcon("detail.svg"));
        mode_btn_panel = new JPanel(new CardLayout());
        m_add_detail_btn = new AddButton();
        m_edit_detail_btn = new EditButton();
        m_delete_detail_btn = new DeleteButton();
        m_download_detail_btn = new DownloadButton();
    }

    public void buildBody() {
        detail_name_label = new DefaultLabel("");
        detail_decimal_number_label = new DefaultLabel("");
        decimal_number_input = new InputText();
        describe_label = new DefaultLabel("");
        description_input_panel = new InputTextArea();
        parameters_label = new DefaultLabel("");
        m_add_detail_parameter_btn = new AddButton();
        m_edit_detail_parameter_btn = new EditButton();

    }

    /* OLD */
    private JButton m_add, m_edit, m_remove, m_add_parameter;
    public InputField m_name, m_decimal_name, m_description;
    public ArrayList<DetailParameterPanel> m_detail_parameters;

    public JButton getCreateButton() { return m_add; }
    public JButton getRemoveButton() { return m_remove; }
    public JButton getEditButton() { return m_edit; }

    public void buildTopPanel() {
        m_update_btn = new UpdateButton();
        detail_icon = new JLabel();
    }

    public DetailControlWindow() {
        build();
        afterBuild();
    }

    public void clearParametersPanel() {
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
    public boolean isEditMode() { return edit; }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10, debug"));

        m_name = new InputField("Наименование", "", 160);
        m_decimal_name = new InputField("Децимальный номер", "", 160);
        m_description = new InputField("Описание", "", 160);
        m_detail_parameters = new ArrayList<>();
        m_detail_parameters.add(new DetailParameterPanel(160));

        m_detail_id = new String();
        m_add = new JButton("Создать");
        m_edit = new JButton("Редактировать");
        m_remove = new JButton("Удалить");
        m_add_parameter = new JButton("Добавить параметр");

        m_panel.add(m_add, "split 3");
        m_panel.add(m_edit);
        m_panel.add(m_remove, "wrap");
        m_panel.add(m_name, "al center, wrap");
        m_panel.add(m_decimal_name, "al center, wrap");
        m_panel.add(m_description, "al center, wrap");

        for (DetailParameterPanel d : m_detail_parameters) {
            m_panel.add(d, "al center, pushx, wrap");
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
        m_description.setEditable(false);

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
            if (m_detail_id == null || m_detail_id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Чтобы редактировать, выберите деталь или создайте деталь");
                return;
            }
            editMode();
        });
        m_add.addActionListener(e -> createMode());
//        m_remove.addActionListener(e -> {
////            int result = JOptionPane.showConfirmDialog(null, "Вы уверены?", "Удалить", JOptionPane.YES_NO_OPTION);
////            if (result == 0) {
////                // TODO action
////                JOptionPane.showMessageDialog(null, "Успешно");
////                m_id = "";
////            }
//        });

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

    public void doNormalMode() { normalMode(); }

    private void normalMode() {
        toViewMode();
        m_add.setVisible(true);
        m_edit.setVisible(true);
        m_remove.setVisible(true);
        m_add.setText("Создать");
        m_edit.setText("Редактировать");
        create = false;
        edit = false;
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
//            JOptionPane.showMessageDialog(null, "Успешно");
        }
    }


    private void toViewMode() {
        m_add_parameter.setVisible(false);

        m_name.setEditable(false);
        m_decimal_name.setEditable(false);
        m_description.setEditable(false);

        for (DetailParameterPanel dp : m_detail_parameters) {
            dp.setEditable(false);
            dp.setVisibleDeleteButton(false);
        }
    }

    private void toChangeMode() {
        m_add_parameter.setVisible(true);

        m_name.setEditable(true);
        m_decimal_name.setEditable(true);
        m_description.setEditable(true);

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
