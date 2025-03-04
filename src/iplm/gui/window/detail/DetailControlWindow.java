package iplm.gui.window.detail;

import iplm.gui.button.*;
import iplm.gui.label.DefaultLabel;
import iplm.gui.label.RoundIconLabel;
import iplm.gui.panel.SwitcherPanel;
import iplm.gui.textarea.InputTextArea;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.textfield.InputText;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailControlWindow extends AWindow {
    enum switch_panels {
        READ_MODE("read_mode"),
        WRITE_MODE("write_mode");

        private String mode;
        public String s() { return mode; }

        switch_panels(String mode) {
            this.mode = mode;
        }
    }

    private String m_detail_id;

    // TOP
    private JPanel m_top_panel;
    // Обновить данные детали
    private UpdateButton m_update_btn;
    // Иконка детали
    private RoundIconLabel m_detail_icon;
    // Панель переключения набора кнопок
    private SwitcherPanel m_detail_control_btn_panel;
    // Создать деталь
    private AddButton m_add_detail_btn;
    // Редактировать деталь
    private EditButton m_edit_detail_btn;
    // Удалить деталь
    private DeleteButton m_delete_detail_btn;
    // Скачать деталь
    private DownloadButton m_download_detail_btn;
    // Confirm button
    private ConfirmButton m_confirm_btn;
    // Cancel button
    private CancelButton m_cancel_btn;

    // BODY
    private JPanel m_body_panel;
    // Метка имени детали
    private DefaultLabel m_detail_name_label;
    // Ввод имени детали
    private JTextField m_detail_name_input;
    // Метка децимального номера детали
    private DefaultLabel m_detail_decimal_number_label;
    // Ввод децимального номера детали
    private InputText m_detail_decimal_number_input;
    // Метка описания
    private DefaultLabel m_detail_describe_label;
    // Описание детали
    private InputTextArea m_detail_description_input;
    // Метка пути файла
    private DefaultLabel m_filepath_label;
    // Ввод пути файла
    private JTextField m_filepath_input;
    // Панель управления параметарми
    private SwitcherPanel m_detail_parameter_control_panel;
    // Метка параметров
    private DefaultLabel m_parameters_label;
    // Создать параметр детали
    private AddButton m_add_detail_parameter_btn;
    // Окно контроля параметров детали
    private EditButton m_edit_detail_parameter_btn;
    // Отображение параметров детали
    private ArrayList<DetailParameterPanel> m_detail_parameter_panels;

    private boolean m_edit_mode = false;
    private boolean m_create_mode = false;

    private int detail_parameter_panel_width;

    // TOP
    public UpdateButton getUpdateButton() { return m_update_btn; }
    public RoundIconLabel getDetailIcon() { return m_detail_icon; }
    public AddButton getAddDetailBtn() { return m_add_detail_btn; }
    public EditButton getEditDetailBtn() { return m_edit_detail_btn; }
    public DeleteButton getDeleteDetailBtn() { return  m_delete_detail_btn; };
    public DownloadButton getDownloadDetailBtn() { return m_download_detail_btn; }

    // BODY
    public InputText decimalNumberInput() { return m_detail_decimal_number_input; }
    public InputTextArea descriptionInput() { return m_detail_description_input; }
    public AddButton addDetailParameterBtn() { return m_add_detail_btn; }
    public EditButton editDetailParameterBtn() { return m_edit_detail_parameter_btn; }

    public boolean isCreateMode() { return m_create_mode; }
    public boolean isEditMode() { return m_edit_mode; }

    private void buildTop() {
        Color background_detail = Color.white;
        Color border_detail = new Color(217, 217, 217);
        int size_icon = 64;

        m_top_panel = new JPanel(new MigLayout("inset 4"));
        m_update_btn = new UpdateButton();
        m_detail_icon = new RoundIconLabel("detail.svg", background_detail, border_detail, size_icon);

        buildModeBtnPanel();

        m_top_panel.add(m_update_btn, "split 3");
        m_top_panel.add(m_detail_icon);
        m_top_panel.add(m_detail_control_btn_panel, "wrap");

        m_scroll_pane = new JScrollPane();
    }

    private void buildModeBtnPanel() {
        m_detail_control_btn_panel = new SwitcherPanel();

        m_add_detail_btn = new AddButton();
        m_edit_detail_btn = new EditButton();
        m_delete_detail_btn = new DeleteButton();
        m_download_detail_btn = new DownloadButton();

        m_confirm_btn = new ConfirmButton();
        m_cancel_btn = new CancelButton();

        JPanel read_mode_panel = new JPanel(new MigLayout());
        JPanel write_mode_panel = new JPanel(new MigLayout());

        read_mode_panel.add(m_add_detail_btn, "split 4");
        read_mode_panel.add(m_edit_detail_btn);
        read_mode_panel.add(m_download_detail_btn);
        read_mode_panel.add(m_delete_detail_btn);

        write_mode_panel.add(m_confirm_btn, "split 2");
        write_mode_panel.add(m_cancel_btn);

        m_detail_control_btn_panel.addPanel(read_mode_panel, switch_panels.READ_MODE.s());
        m_detail_control_btn_panel.addPanel(write_mode_panel, switch_panels.WRITE_MODE.s());

        m_add_detail_btn.addAction(() -> doCreateMode());
        m_edit_detail_btn.addAction(() -> doEditMode());
        m_cancel_btn.addAction(() -> doReadMode());
        m_confirm_btn.addAction(() -> doReadMode());
    }

    private void buildBody() {
        m_body_panel = new JPanel(new MigLayout("inset 10"));

        m_detail_name_label = new DefaultLabel("Наименование");
        m_detail_name_input = new JTextField();
        m_detail_decimal_number_label = new DefaultLabel("Децимальный номер");
        m_detail_decimal_number_input = new InputText();
        m_detail_describe_label = new DefaultLabel("Примечание");
        m_detail_description_input = new InputTextArea();
        m_filepath_label = new DefaultLabel("Загружаемый файл");
        m_filepath_input = new JTextField();
        m_detail_parameter_control_panel = new SwitcherPanel();
        m_parameters_label = new DefaultLabel("Параметры");
        m_add_detail_parameter_btn = new AddButton();
        m_edit_detail_parameter_btn = new EditButton();

        m_add_detail_parameter_btn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                DetailParameterPanel dpp = new DetailParameterPanel(detail_parameter_panel_width);
                m_detail_parameter_panels.add(dpp);

                dpp.setVisibleDeleteButton(false);
                dpp.setEditable(false);

                if (isEditMode() || isCreateMode()) {
                    dpp.setVisibleDeleteButton(true);
                    dpp.setEditable(true);
                }

                dpp.addDeleteAction(() -> {
                    m_panel.remove(dpp);
                    m_panel.revalidate();
                    m_panel.repaint();
                    m_detail_parameter_panels.remove(dpp);
                });
                m_panel.add(dpp, "al center, wrap");

                m_panel.revalidate();
                m_panel.repaint();
//                m_frame.pack();
            });
        });

        JPanel read_mode_panel = new JPanel(new MigLayout());
        JPanel write_mode_panel = new JPanel(new MigLayout());

        read_mode_panel.add(m_parameters_label);
        write_mode_panel.add(m_parameters_label, "split 3");
        write_mode_panel.add(m_add_detail_parameter_btn);
        write_mode_panel.add(m_edit_detail_parameter_btn);
        m_detail_parameter_control_panel.addPanel(read_mode_panel, switch_panels.READ_MODE.s());
        m_detail_parameter_control_panel.addPanel(write_mode_panel, switch_panels.WRITE_MODE.s());
    }

    public DetailControlWindow() {
        build();
        detail_parameter_panel_width = 160;
        afterBuild();
    }

    public void clearDetailParameterPanels() {
        for (DetailParameterPanel dpp : m_detail_parameter_panels) {
            m_panel.remove(dpp);
            m_panel.revalidate();
            m_panel.repaint();
        }
        m_detail_parameter_panels.clear();
    }

    public void addParameter(String key, String value) {


//        DetailParameterPanel dp = new DetailParameterPanel(160);
//        dp.setKey(key);
//        dp.setValue(value);
//        m_detail_parameters.add(dp);
//        dp.setVisibleDeleteButton(false);
//        dp.setEditable(false);
//        if (edit || create) {
//            dp.setVisibleDeleteButton(true);
//            dp.setEditable(true);
//        }
//        dp.addDeleteAction(() -> {
//            m_panel.remove(dp);
//            m_panel.revalidate();
//            m_panel.repaint();
//            m_detail_parameters.remove(dp);
//        });
//        m_panel.add(dp, "wrap");
//
//        m_panel.add(m_add_parameter);

        m_panel.revalidate();
        m_panel.repaint();
        m_frame.pack();
    }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));
        m_detail_parameter_panels = new ArrayList<>();
        buildTop();
        buildBody();
        arrangeComponents();
    }

    public void arrangeComponents() {
        int label_width = 160;
        int input_width = 280;
        int input_area_width = 440;
        int input_area_height = 120;

        m_panel.add(m_top_panel, "al center, pushx, wrap");
        m_panel.add(m_detail_name_label, "al center, width " + label_width + ", split 2");
        m_panel.add(m_detail_name_input, "width " +  input_width + ", wrap");
        m_panel.add(m_detail_decimal_number_label, "al center, width " + label_width + ", split 2");
        m_panel.add(m_detail_decimal_number_input, "width " +  input_width + ", wrap");
        m_panel.add(m_detail_describe_label, "al center, wrap");
        m_panel.add(m_detail_description_input, "al center, width " + input_area_width + ", height " + input_area_height + ", wrap");
        m_panel.add(m_filepath_label, "al center, split 2, width " + label_width);
        m_panel.add(m_filepath_input, "width " + input_width + ", wrap");
        m_panel.add(m_parameters_label, "al center, split 3");
        m_panel.add(m_add_detail_parameter_btn);
        m_panel.add(m_edit_detail_parameter_btn, "wrap");

        for (DetailParameterPanel d : m_detail_parameter_panels) {
            m_panel.add(d, "al center, pushx, wrap");
            d.addDeleteAction(() -> {
                m_panel.remove(d);
                m_panel.revalidate();
                m_panel.repaint();
                m_detail_parameter_panels.remove(d);
            });
            d.setEditable(false);
        }
    }

    public void doReadMode() {
        setMode(false);
        m_edit_mode = false;
        m_create_mode = false;
        m_detail_control_btn_panel.showPanel(switch_panels.READ_MODE.s());
    }

    public void doEditMode() {
        if (isCreateMode()) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме создания");
            return;
        }
        if (!isEditMode()) {
            setMode(true);
            m_edit_mode = true;
            m_create_mode = false;
            m_detail_control_btn_panel.showPanel(switch_panels.WRITE_MODE.s());
        }
    }

    public void doCreateMode() {
        if (isEditMode()) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме редактирования");
            return;
        }
        else if (!isCreateMode()) {
            setMode(true);
            m_edit_mode = false;
            m_create_mode = true;
            m_detail_control_btn_panel.showPanel(switch_panels.WRITE_MODE.s());
        }
    }

    private void setMode(boolean flag) {
        m_add_detail_parameter_btn.setVisible(flag);
        m_edit_detail_parameter_btn.setVisible(flag);

        m_detail_name_input.setEditable(flag);
        m_detail_decimal_number_input.setEditable(flag);
        m_detail_description_input.setEditable(flag);

        for (DetailParameterPanel dp : m_detail_parameter_panels) {
            dp.setEditable(flag);
            dp.setVisibleDeleteButton(flag);
        }
    }

    public void updateDisplay() {
        m_panel.revalidate();
        m_panel.repaint();
    }
}
