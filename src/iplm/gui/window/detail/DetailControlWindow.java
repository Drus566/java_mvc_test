package iplm.gui.window.detail;

import iplm.data.types.Detail;
import iplm.data.types.DetailParameterType;
import iplm.gui.button.*;
import iplm.gui.components.detail.DetailParameterUI;
import iplm.gui.label.DefaultLabel;
import iplm.gui.label.RoundIconLabel;
import iplm.gui.layer.intercept.InterceptLayer;
import iplm.gui.panel.SwitcherPanel;
import iplm.gui.panel.item_list_panel.IItem;
import iplm.gui.panel.item_list_panel.ItemListPanel;
import iplm.gui.textarea.InputTextArea;
import iplm.gui.textfield.InputText;
import iplm.gui.textfield.RowSelectionList;
import iplm.gui.window.AWindow;
import iplm.managers.WindowsManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DetailControlWindow extends AWindow {
    enum SwitchPanels {
        READ_MODE("read_mode"),
        WRITE_MODE("write_mode");

        private String mode;
        public String s() { return mode; }

        SwitchPanels(String mode) {
            this.mode = mode;
        }
    }

    private String m_detail_id;
    private String m_detail_id_last;
    public void setDetailId(String id) { m_detail_id = id; }
    public String getDetailId() { return m_detail_id; }

    public void setDetailIdLast(String id) { m_detail_id_last = id; }
    public String getDetailIdLast() { return m_detail_id_last; }

    private ArrayList<DetailParameterType> m_detail_parameter_type_list;
    public ArrayList<DetailParameterType> getDetailParameterTypes() { return m_detail_parameter_type_list; }
//    public void addDetailParameterType(DetailParameterType type) { m_detail_parameter_type_list.add(type); }

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
    private DirectoryButton m_directory_detail_btn;
    // Confirm button
    private ConfirmButton m_confirm_btn;
    // Cancel button
    private CancelButton m_cancel_btn;

    // BODY
    private JPanel m_body_panel;
    // Панель метки и кнопки редактирования имени детали
    private JPanel m_detail_name_panel;
    // Кнопка редактирования имени детали
    private EditButton m_detail_name_edit;
    // Метка имени детали
    private DefaultLabel m_detail_name_label;
    // Ввод имени детали
    private RowSelectionList m_detail_name_input;
    // Метка децимального номера детали
    private DefaultLabel m_detail_decimal_number_label;
    // Ввод децимального номера детали
    private InputText m_detail_decimal_number_input;
    // Метка описания
    private DefaultLabel m_detail_describe_label;
    // Описание детали
    private InputTextArea m_detail_description_input;
    // Панель управления параметарми
    private SwitcherPanel m_detail_parameter_control_panel;
    // Метка параметров
    private DefaultLabel m_parameters_label;
    // Создать параметр детали
    private AddButton m_detail_parameter_add_btn;
    // Окно контроля параметров детали
    private EditButton m_detail_parameter_edit_btn;
    // Панель параметров
    private ItemListPanel m_parameters_panel;
    private JScrollPane m_parameters_panel_scroll_pane;

    private boolean m_edit_mode = false;
    private boolean m_create_mode = false;

    private int detail_parameter_panel_width;

    // LAST FIELDS
    private String last_name, last_decimal_number, last_description;

    // TOP
    public UpdateButton getUpdateButton() { return m_update_btn; }
//    public RoundIconLabel getDetailIcon() { return m_detail_icon; }
    public AddButton getAddDetailBtn() { return m_add_detail_btn; }
    public EditButton getEditDetailBtn() { return m_edit_detail_btn; }
    public DeleteButton getDeleteDetailBtn() { return  m_delete_detail_btn; };
    public DirectoryButton getDownloadDetailBtn() { return m_directory_detail_btn; }
    public ConfirmButton getConfirmButton() { return m_confirm_btn; }
    public CancelButton getCancelButton() { return m_cancel_btn; }

    // BODY
    public RowSelectionList getNameInput() { return m_detail_name_input; }
    public InputText getDecimalNumberInput() { return m_detail_decimal_number_input; }
    public InputTextArea getDescriptionInput() { return m_detail_description_input; }
    public AddButton getAddDetailParameterBtn() { return m_add_detail_btn; }
    public EditButton getEditDetailParameterBtn() { return m_detail_parameter_edit_btn; }
    public ItemListPanel getParametersPanel() { return m_parameters_panel; }

    public boolean isCreateMode() { return m_create_mode; }
    public boolean isEditMode() { return m_edit_mode; }

    public void updateParametersPanel() { m_parameters_panel.updateItems(m_detail_parameter_type_list); }
    public ArrayList<IItem> getParameterPanelItems() { return m_parameters_panel.getItems(); }

    public void updateDetail(Detail detail) {
        updateParametersPanel();
//        m_detail_name_input
    }

    private void buildTop() {
        Color background_detail = Color.white;
        Color border_detail = new Color(217, 217, 217);
        int size_icon = 64;

        m_top_panel = new JPanel(new MigLayout("inset 4"));
        m_update_btn = new UpdateButton("Обновить деталь");
//        m_detail_icon = new RoundIconLabel("detail.svg", background_detail, border_detail, size_icon);

        buildModeBtnPanel();

        m_top_panel.add(m_update_btn, "split 2");
//        m_top_panel.add(m_detail_icon);
        m_top_panel.add(m_detail_control_btn_panel, "wrap");
    }

    private void buildModeBtnPanel() {
        m_detail_control_btn_panel = new SwitcherPanel();

        m_add_detail_btn = new AddButton("Добавить деталь");
        m_edit_detail_btn = new EditButton("Редактировать деталь");
        m_delete_detail_btn = new DeleteButton("Удалить деталь");
        m_directory_detail_btn = new DirectoryButton("Открыть деталь");

        m_confirm_btn = new ConfirmButton("Подтвердить");
        m_cancel_btn = new CancelButton("Отменить");

        JPanel read_mode_panel = new JPanel(new MigLayout());
        JPanel write_mode_panel = new JPanel(new MigLayout("al center"));

        read_mode_panel.add(m_add_detail_btn, "split 2");
        read_mode_panel.add(m_edit_detail_btn, "wrap");
        read_mode_panel.add(m_directory_detail_btn, "split 2");
        read_mode_panel.add(m_delete_detail_btn);

        write_mode_panel.add(m_confirm_btn, "split 2");
        write_mode_panel.add(m_cancel_btn);

        m_detail_control_btn_panel.addPanel(read_mode_panel, SwitchPanels.READ_MODE.s());
        m_detail_control_btn_panel.addPanel(write_mode_panel, SwitchPanels.WRITE_MODE.s());

        m_add_detail_btn.addAction(() -> doCreateMode());
        m_cancel_btn.addAction(() -> {
            m_parameters_panel.fillLastItems();
            fillLast();
            doReadMode();
            m_parameters_panel.updateGUI();
        });
    }

    private void buildBody() {
        int name_input_list_panel_max_height = 260;

        m_body_panel = new JPanel(new MigLayout("inset 10"));

        m_detail_name_panel = new JPanel(new MigLayout("inset 0, gap rel -2"));
        m_detail_name_edit = new EditButton();
        m_detail_name_label = new DefaultLabel("Наименование");
        m_detail_name_input = new RowSelectionList();
        m_detail_decimal_number_label = new DefaultLabel("Децимальный номер");
        m_detail_decimal_number_input = new InputText();
        m_detail_describe_label = new DefaultLabel("Примечание");
        m_detail_description_input = new InputTextArea();
        m_detail_parameter_control_panel = new SwitcherPanel();
        m_parameters_label = new DefaultLabel("Параметры");
        m_detail_parameter_add_btn = new AddButton();
        m_detail_parameter_edit_btn = new EditButton();
        m_parameters_panel = new ItemListPanel();
        m_parameters_panel_scroll_pane = new JScrollPane(m_parameters_panel);
        m_parameters_panel_scroll_pane.setBorder(null);

        m_detail_name_panel.add(m_detail_name_label);
        m_detail_name_panel.add(m_detail_name_edit);

        m_detail_name_edit.addAction(() -> WindowsManager.getInstance().showWindow("DetailNameControlWindow"));
        m_detail_parameter_edit_btn.addAction(() -> WindowsManager.getInstance().showWindow("DetailParameterTypeControlWindow"));

        JPanel read_mode_panel = new JPanel(new MigLayout());
        JPanel write_mode_panel = new JPanel(new MigLayout());

        read_mode_panel.add(new JLabel("Параметры"), "al center, push");

        write_mode_panel.add(m_parameters_label, "al center, split 3");
        write_mode_panel.add(m_detail_parameter_add_btn);
        write_mode_panel.add(m_detail_parameter_edit_btn);

        m_detail_parameter_control_panel.addPanel(read_mode_panel, SwitchPanels.READ_MODE.s());
        m_detail_parameter_control_panel.addPanel(write_mode_panel, SwitchPanels.WRITE_MODE.s());

        detail_parameter_panel_width = 160;

        m_detail_parameter_add_btn.addAction(() -> {
            m_parameters_panel.addParameter(new DetailParameterUI(detail_parameter_panel_width, m_detail_parameter_type_list));
            m_parameters_panel.updateGUI();
            updateGUI();
        });
    }

    private void rememberLast() {
        last_name = m_detail_name_input.getValue();
        last_decimal_number = m_detail_decimal_number_input.getText();
        last_description = m_detail_description_input.getTextArea().getText();
    }

    public void fillLast() {
        m_detail_name_input.setValue(last_name);
        m_detail_decimal_number_input.setText(last_decimal_number);
        m_detail_description_input.getTextArea().setText(last_description);
    }

    public DetailControlWindow() {
        build();
        afterBuild();
    }

    @Override
    public void build() {
        m_panel = new JPanel(new MigLayout("inset 10"));

        m_detail_parameter_type_list = new ArrayList<>();
        setTitle("Управление деталью");
        buildTop();
        buildBody();
        doReadMode();
        arrangeComponents();

        m_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                m_parameters_panel.fillLastItems();
                fillLast();
                doReadMode();
            }
        });
    }

    public void arrangeComponents() {
        int label_width = 160;
        int input_width = 280;
        int input_area_width = 440;
        int input_area_height = 80;

        m_panel.add(m_top_panel, "alignx center, aligny bottom, push, wrap");
        m_panel.add(m_detail_name_panel, "al center, width " + label_width + "!, split 2");
        m_panel.add(m_detail_name_input, "id input_name, width " +  input_width + "!, wrap");
        m_panel.add(m_detail_decimal_number_label, "al center, width " + label_width + "!, split 2");
        m_panel.add(m_detail_decimal_number_input, "width " +  input_width + "!, wrap");
        m_panel.add(m_detail_describe_label, "al center, gap top 10, wrap");
        m_panel.add(m_detail_description_input, "al center, width " + input_area_width + ", height " + input_area_height + "!, wrap");
        m_panel.add(m_detail_parameter_control_panel, "al center, wrap");
        m_panel.add(m_parameters_panel_scroll_pane, "al center, grow, push, wrap");

        m_layer = new JLayer<>(m_panel, new InterceptLayer());
    }

    public void doReadMode() {
        setTitle("Управление деталью");
        setMode(false);
        m_edit_mode = false;
        m_create_mode = false;
        m_detail_control_btn_panel.showPanel(SwitchPanels.READ_MODE.s());
        m_detail_parameter_control_panel.showPanel(SwitchPanels.READ_MODE.s());
    }

    public void doEditMode() {
        if (isCreateMode()) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме создания");
            return;
        }
        if (!isEditMode()) {
            setTitle("Управление деталью | Редактирование");
            setMode(true);
            m_edit_mode = true;
            m_create_mode = false;
            m_detail_control_btn_panel.showPanel(SwitchPanels.WRITE_MODE.s());
            m_detail_parameter_control_panel.showPanel(SwitchPanels.WRITE_MODE.s());
            m_parameters_panel.rememberItems();
            rememberLast();
        }
    }

    public void doCreateMode() {
        if (isEditMode()) {
            JOptionPane.showMessageDialog(null, "Деталь в режиме редактирования");
            return;
        }
        else if (!isCreateMode()) {
            setTitle("Управление деталью | Создание");
            setMode(true);
            m_edit_mode = false;
            m_create_mode = true;
            m_detail_control_btn_panel.showPanel(SwitchPanels.WRITE_MODE.s());
            m_detail_parameter_control_panel.showPanel(SwitchPanels.WRITE_MODE.s());
            m_parameters_panel.rememberItems();
            rememberLast();
        }
    }

    private void setMode(boolean flag) {
        m_detail_parameter_add_btn.setVisible(flag);
        m_detail_parameter_edit_btn.setVisible(flag);

        m_detail_name_edit.setVisible(flag);
        m_detail_name_input.setEnable(flag);
        m_detail_name_input.setEditable(flag);

        m_detail_decimal_number_input.setEditable(flag);
        m_detail_description_input.setEditable(flag);

        if (flag) m_parameters_panel.toWriteMode();
        else m_parameters_panel.toReadMode();

        m_detail_decimal_number_input.setCaretPosition(0);
        m_detail_description_input.getTextArea().setCaretPosition(0);
    }

    public void updateGUI() {
        m_frame.revalidate();
        m_frame.repaint();
        m_frame.setPreferredSize(new Dimension(m_frame.getWidth(), m_frame.getHeight()));
    }
}
