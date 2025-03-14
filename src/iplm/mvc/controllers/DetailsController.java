package iplm.mvc.controllers;

import iplm.data.config.Config;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameter;
import iplm.data.types.DetailParameterType;
import iplm.gui.button.*;
import iplm.gui.components.detail.DetailParameterUI;
import iplm.gui.panel.item_list_panel.IItem;
import iplm.gui.panel.item_list_panel.ItemListPanel;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.table.DefaultTable;
import iplm.gui.textarea.InputTextArea;
import iplm.gui.textfield.InputText;
import iplm.gui.textfield.RowSelectionList;
import iplm.gui.textfield.SearchBar;
import iplm.gui.window.detail.*;
import iplm.managers.WindowsManager;
import iplm.mvc.models.DetailModel;
import iplm.mvc.views.detail.*;
import iplm.utility.DialogUtility;
import iplm.utility.FilesystemUtility;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DetailsController implements IController {
    private final DetailModel m_model;
    private final DetailsView m_details_view;
    private final DetailControlView m_detail_control_view;
    private final DetailNameControlView m_detail_name_control_view;
    private final DetailParameterTypeControlView m_detail_parameter_type_control_view;
    private final DetailSettingsView m_detail_settings_view;

    public DetailsController(DetailModel model,
                             DetailsView detail_view,
                             DetailControlView detail_control_view,
                             DetailNameControlView detail_name_control_view,
                             DetailParameterTypeControlView detail_parameter_type_control_view,
                             DetailSettingsView detail_settings_view) {
        m_model = model;
        m_details_view = detail_view;
        m_detail_control_view = detail_control_view;
        m_detail_name_control_view = detail_name_control_view;
        m_detail_parameter_type_control_view = detail_parameter_type_control_view;
        m_detail_settings_view = detail_settings_view;
    }

    // ОКНО НАСТРОЕК ДЕТАЛЕЙ
    private void bindDetailSettingsActions() {
        DetailSettingsWindow w = m_detail_settings_view.getDetailSettingsWindow();
        DirectoryButton db = w.getOpenDirButton();
        DirectoryButton sdb = w.getSelectDirButton();
        SearchButton sb = w.getSearchButton();
        InputText sdpf = w.getSelectDirPathField();
        JFileChooser fc = w.getSelectDirPanel();

        String details_path = m_model.getDetailsPath();
        if (details_path != null && !details_path.isEmpty()) sdpf.setText(details_path);

        db.addAction(() -> m_model.openDetailsDir());

        sb.addAction(() -> {
            if (m_model.scanDetailDir()) {
                sdpf.setText(m_model.getDetailsPath());
                DialogUtility.showDialog("Успешно", "Директория найдена: " + sdpf.getText(), JOptionPane.INFORMATION_MESSAGE);
                Config.getInstance().writeSVNPath(m_model.getDetailsPath());
            }
        });

        sdb.addAction(() -> {
            fc.setDialogTitle("Выбор директории");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) JOptionPane.showMessageDialog(null, fc.getSelectedFile());
        });

        sdpf.addPresAction(() -> {
            String path = sdpf.getText().trim();
            if (path.isEmpty()) {
                sdpf.setText("Путь к папке с деталями...");
                return;
            }
            m_model.setDetailsPath(path);
            DialogUtility.showDialog("Успешно", "Установлен путь к папки детали", JOptionPane.INFORMATION_MESSAGE);
            Config.getInstance().writeSVNPath(path);
        });
    }

    // ОКНО ДЕТАЛЕЙ
    private void bindDetailsWindowActions() {
        DetailsWindow w = m_details_view.getDetailsWindow();
        DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();

        SearchPanel sp = w.getSearchPanel();
        SearchBar sb = w.getSearchBar();
        DefaultTable t = w.getTable();

        RowSelectionList ni = dcw.getNameInput();
        InputText dni = dcw.getDecimalNumberInput();
        InputTextArea di = dcw.getDescriptionInput();
        ItemListPanel pp = dcw.getParametersPanel();

        AddButton adb = w.getAddDetailButton();

        adb.addAction(() -> {
            if (dcw.isEditMode() || dcw.isCreateMode()) {
                DialogUtility.showDialog("Информация", "Деталь уже в режиме создания | редакитрования", JOptionPane.INFORMATION_MESSAGE);
                WindowsManager.getInstance().showWindow("DetailControlWindow");
                return;
            }
            WindowsManager.getInstance().showWindow("DetailControlWindow");
            dcw.setDetailId("");
            ni.setValue("");
            dni.setText("");
            di.getTextArea().setText("");
            pp.removeItems();
            pp.updateGUI();
        });

        sb.addTapAction(() -> {
            // Максимальное количество для отображения из БД
            int MAX_COUNT = 5;
            // Максимальное количество символов
            int MAX_TEXT_LENGTH = 100;

            // Если слишком длинное сообщение, не обрабатываем его
            String search_text = sb.getSearchText();
            if (search_text.length() > MAX_TEXT_LENGTH) return;

            // Очищаем панель
            sp.removeLines();

            // Если строка поиска не пустая
            if (!search_text.isEmpty()) {
                // Получаем детали без зависимостей
                ArrayList<Detail> details = m_model.getDetails(search_text);
                if (details != null) {
                    for (Detail d : details) {
                        if (MAX_COUNT <= 0) break;

                        String preview = (d.decimal_number == null ? "" : d.decimal_number) + " " + d.name + " " + d.description;

                        // Добавляем строку в панель поиска
                        sp.addActualLine(d.id, preview, () -> {
                            dcw.show();
                            // Действие при клике по строке
                            if (!dcw.isCreateMode() && !dcw.isEditMode()) {
                                sp.setVisible(false);
                                Detail detail = m_model.getDetailByIDWithDepends(d.id);
                                if (detail != null)  {
                                    ni.setValue(detail.name);
                                    dni.setText(detail.decimal_number);
                                    di.getTextArea().setText(detail.description);

                                    pp.removeItems();
                                    if (detail.params != null) {
                                        for (DetailParameter dp : detail.params) {
                                            int detail_parameter_panel_width = 160;
                                            pp.addParameter(new DetailParameterUI(detail_parameter_panel_width, dp.type, dp.value, dcw.getDetailParameterTypes()));
                                        }
                                    }
                                    pp.updateGUI();
                                    dcw.updateGUI();
                                    dcw.setDetailId(detail.id);
                                    if (!dcw.isCreateMode() && !dcw.isEditMode()) dcw.doReadMode();

                                }
                                else DialogUtility.showErrorIfExists();
                            }
                            else {
                                DialogUtility.showDialog("Информация", "Деталь уже в режиме редактирования | создания", JOptionPane.INFORMATION_MESSAGE);
//                                dcw.show();
                            }
                        });

                        --MAX_COUNT;
                    }
                }

                /* Find in cache */
//                ArrayList<RequestHistory> request_history = StorageHistory.getInstance().search(StorageHistoryType.DETAILS, search_text);
//                if (request_history != null) {
//                    for (int i = 0; i < request_history.size(); i++) {
//                        RequestHistory rh = request_history.get(i);
//                        sp.addHistoryLine(rh.id, (String) rh.params.get("Query"), rh.type, m_details_view.getDetailsWindow());
//                    }
//                }
            }
            // Обновляем панель
            sp.updateLines();
            sp.updateSize(sb.getWidth());
        });

        sb.addEnterButtonAction(() -> {
            String search_text = sb.getSearchText();

            ArrayList<Detail> details;
            if (!search_text.isEmpty()) details = m_model.getDetailsWithDepends(search_text);
            else details = m_model.getAllDetailsWithDepends();

            if (details == null) {
                DialogUtility.showErrorIfExists();
                return;
            }

            t.clear();
            for (Detail d : details) {
                ArrayList<String> args = new ArrayList<>();
                args.add(d.id);
                args.add(d.decimal_number);
                args.add(d.name);
                args.add(d.description);
                if (d.params == null || d.params.isEmpty()) args.add(Boolean.TRUE.toString());
                else args.add(Boolean.FALSE.toString());
                t.addLine(args);
            }
        });

        t.addDoubleClickAction(() -> {
//            dcw.show();

            // Действие при клике по строке
            if (!dcw.isCreateMode() && !dcw.isEditMode()) {
                int sr = t.getTable().getSelectedRow();
                String id = (String) t.getTableModel().getValueAt(sr, 0);

                sp.setVisible(false);



                Detail detail = m_model.getDetailByIDWithDepends(id);
                if (detail != null)  {
                    // true open pdf
                    String detail_fullname = detail.decimal_number + " - " + detail.name;
                    boolean opened = m_model.openDetailPdf(detail_fullname);
                    if (!opened) opened = m_model.openDetailDir(detail_fullname);
                    if (opened) return;

                    ni.setValue(detail.name);
                    dni.setText(detail.decimal_number);
                    di.getTextArea().setText(detail.description);

                    pp.removeItems();
                    if (detail.params != null) {
                        for (DetailParameter dp : detail.params) {
                            int detail_parameter_panel_width = 160;
                            pp.addParameter(new DetailParameterUI(detail_parameter_panel_width, dp.type, dp.value, dcw.getDetailParameterTypes()));
                        }
                    }
                    pp.updateGUI();
                    dcw.updateGUI();
                    dcw.setDetailId(detail.id);
                    if (!dcw.isCreateMode() && !dcw.isEditMode()) dcw.doReadMode();
                    dcw.show();
                }
                else DialogUtility.showErrorIfExists();
            }
            else {
                DialogUtility.showDialog("Информация", "Деталь уже в режиме редактирования | создания", JOptionPane.INFORMATION_MESSAGE);
                dcw.show();
            }
        });
    }

    // ОКНО УПРАВЛЕНИЯ ДЕТАЛЬЮ
    private void bindDetailControlWindowActions() {
        DetailControlWindow w = m_detail_control_view.getDetailControlWindow();
        RowSelectionList ni = w.getNameInput();
        InputText dn = w.getDecimalNumberInput();
        InputTextArea di = w.getDescriptionInput();

        UpdateButton ub = w.getUpdateButton();
        AddButton ab = w.getAddDetailBtn();
        EditButton eb = w.getEditDetailBtn();
        DeleteButton db = w.getDeleteDetailBtn();
        ConfirmButton cb = w.getConfirmButton();
        CancelButton canb = w.getCancelButton();
        DirectoryButton ddb = w.getDownloadDetailBtn();

        ddb.addAction(() -> {
            if (w.getDetailId() == null || w.getDetailId().isEmpty()) {
                DialogUtility.showDialog("Информация","Выберите деталь для открытия папки детали", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String detail_dir_path = dn.getText() + " - " + ni.getValue();
            String details_dir = m_model.getDetailsPath();
            if (details_dir != null && !details_dir.isEmpty()) {
                Path details_dir_path = null;
                try { details_dir_path = Paths.get(details_dir).resolve(detail_dir_path); }
                catch (Exception e) {
                    DialogUtility.showDialog("Информация","Некорректное наименование или децимальный номер детали", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                FilesystemUtility.openDir(details_dir_path.toAbsolutePath().toString());
            }
            else DialogUtility.showDialog("Информация","Папка деталей неизестна, установите папку деталей в окне Настройки деталей", JOptionPane.INFORMATION_MESSAGE);
        });

        /* Подгрузка всех имен деталей при появлении окна */
        w.addVisibleAction(() -> {
            ArrayList<DetailName> result = m_model.getDetailNames();
            if (result != null) {
                ArrayList<String> names = (ArrayList<String>) result.stream().map(DetailName::getName).collect(Collectors.toList());
                ni.updateData(names);
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Подгрузка всех типов параметров деталей при появлении окна */
        w.addVisibleAction(() -> {
            ArrayList<DetailParameterType> result = m_model.getDetailParameterTypes();
            if (result != null) {
                ArrayList<DetailParameterType> dpt_list = w.getDetailParameterTypes();
                dpt_list.clear();
                for (DetailParameterType dpt : result) {
                    dpt_list.add(new DetailParameterType(dpt.id, dpt.name, dpt.type));
                }
                w.updateParametersPanel();
            }
            else DialogUtility.showErrorIfExists();

//            if (!w.isCreateMode() && !w.isEditMode()) w.doReadMode();
        });

        /* Подгрузка всех параметров и полей деталей */
        Runnable update_action = () -> {
            /* Подгрузка всех имен деталей */
            ArrayList<DetailName> detail_names_list = m_model.getDetailNames();
            if (detail_names_list != null) {
                ArrayList<String> names = (ArrayList<String>) detail_names_list.stream().map(DetailName::getName).collect(Collectors.toList());
                ni.updateData(names);
            }
            else DialogUtility.showErrorIfExists();

            /* Подгрузка всех типов параметров деталей */
            ArrayList<DetailParameterType> result = m_model.getDetailParameterTypes();
            if (result != null) {
                ArrayList<DetailParameterType> dpt_list = w.getDetailParameterTypes();
                dpt_list.clear();
                for (DetailParameterType dpt : result) {
                    dpt_list.add(new DetailParameterType(dpt.id, dpt.name, dpt.type));
                }
                w.updateParametersPanel();
            }
            else DialogUtility.showErrorIfExists();

            if (w.getDetailId() != null && !w.getDetailId().isEmpty()) {
                // TODO: сделать подгрузку параметров и значений детали (имя, децимальнй номер, описание)
                Detail detail = m_model.getDetailByID(w.getDetailId());
            }
        };

        /* Добавление детали */
        Runnable add_action = () -> {
            Detail detail = new Detail();
            ArrayList<DetailParameter> params = null;

            detail.name = ni.getText().trim();

            detail.decimal_number = dn.getText();
            detail.description = di.getTextArea().getText();

            if (detail.name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Наименование детали не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ni.isExistsValue(detail.name)) {
                JOptionPane.showMessageDialog(null, "Можно использовать наименование только из списка", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Проверка полей на корректность
            ArrayList<IItem> items = w.getParameterPanelItems();
            for (IItem i : items) {
                if (params == null) params = new ArrayList<>();

                DetailParameterUI dp = (DetailParameterUI) i.getComponent();
                DetailParameterType dpt = dp.getCurrentType();

                String value = dp.getValue();
                if (value.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Значение параметра не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dpt == null) {
                    JOptionPane.showMessageDialog(null, "Тип параметра не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.STRING.s())) params.add(new DetailParameter(value, dpt));
                else if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.DEC.s())) {
                    try {
                        int num = Integer.parseInt(value);
                        params.add(new DetailParameter(num, dpt));
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Некорректное значение параметра детали, должно быть целое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.FLOAT.s())) {
                    try {
                        float num = Float.parseFloat(value);
                        params.add(new DetailParameter(num, dpt));
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Некорректное значение параметра детали, должно быть число с плавающей точкой", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            detail.params = params;

            String result = m_model.addDetail(detail);
            if (result != null) {
                JOptionPane.showMessageDialog(null, "Деталь добавлена", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                w.setDetailId(result);
                ni.setValue(detail.name);
            }
            else DialogUtility.showErrorIfExists();
        };

        Runnable edit_action = () -> {
            String current_id = w.getDetailId();
            if (current_id == null || current_id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Выберите деталь для редактирования", "Информация", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Detail detail = new Detail();
            detail.id = current_id;
            ArrayList<DetailParameter> params = null;

            detail.name = ni.getText().trim();
            detail.decimal_number = dn.getText();
            detail.description = di.getTextArea().getText();

            if (detail.name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Наименование детали не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                w.fillLast();
                return;
            }

            if (!ni.isExistsValue(detail.name)) {
                JOptionPane.showMessageDialog(null, "Можно использовать наименование только из списка", "Ошибка", JOptionPane.ERROR_MESSAGE);
                w.fillLast();
                return;
            }

            // Проверка полей на корректность
            ArrayList<IItem> items = w.getParameterPanelItems();
            for (IItem i : items) {
                if (params == null) params = new ArrayList<>();

                DetailParameterUI dp = (DetailParameterUI) i.getComponent();
                DetailParameterType dpt = dp.getCurrentType();

                String value = dp.getValue();
                if (value.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Значение параметра не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    w.fillLast();
                    return;
                }

                if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.STRING.s())) params.add(new DetailParameter(value, dpt));
                else if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.DEC.s())) {
                    try {
                        int num = Integer.parseInt(value);
                        params.add(new DetailParameter(num, dpt));
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Некорректное значение параметра детали, должно быть целое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        w.fillLast();
                        return;
                    }
                }
                else if (dpt.type.equalsIgnoreCase(DetailParameterType.Type.FLOAT.s())) {
                    try {
                        float num = Float.parseFloat(value);
                        params.add(new DetailParameter(num, dpt));
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Некорректное значение параметра детали, должно быть число с плавающей точкой", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        w.fillLast();
                        return;
                    }
                }
            }
            detail.params = params;

            String result = m_model.updateDetail(detail);
            if (result != null) {
                JOptionPane.showMessageDialog(null, "Деталь обновлена", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                ni.setValue(detail.name);
            }
            else DialogUtility.showErrorIfExists();
        };

        /* Добавление | Редактирование детали в БД */
        cb.addAction(() -> {
            if (w.isCreateMode()) add_action.run();
            else if (w.isEditMode()) edit_action.run();
            w.doReadMode();
        });

        /* Подгрузка детали с БД */
        ub.addAction(() -> update_action.run());

        /* Удаление детали */
        db.addAction(() -> {
            String current_id = w.getDetailId();
            if (current_id == null || current_id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Выберите деталь для удаления", "Информация", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (w.isCreateMode() || w.isEditMode()) {
                JOptionPane.showMessageDialog(null, "Нельзя удалять в режиме редактирования | создания", "Информация", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (!DialogUtility.showConfirmDialog()) {
                return;
            }

            boolean result = m_model.deleteDetail(current_id);
            if (result) {
                w.setDetailId("");
                ni.setValue("");
                dn.setText("");
                di.getTextArea().setText("");
                w.getParametersPanel().removeItems();
                w.getParametersPanel().updateGUI();
                JOptionPane.showMessageDialog(null, "Деталь удалена", "Успешно", JOptionPane.INFORMATION_MESSAGE);
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Кнопка редактировать */
        eb.addAction(() -> {
            String current_id = w.getDetailId();
            if (current_id == null || current_id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Выберите деталь для редактирования", "Информация", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            w.doEditMode();
        });
    }

    // ОКНО ТИПА ПАРАМЕТРОВ ДЕТАЛЕЙ
    private void bindDetailParameterTypeControlActions() {
        DetailParameterTypeControlWindow w = m_detail_parameter_type_control_view.getDetailNameControlWindow();
        UpdateButton ub = w.getUpdateButton();
        AddButton ab = w.getAddButton();
        EditButton eb = w.getEditButton();
        DeleteButton db = w.getDeleteButton();
        InputText ni = w.getInputText();
        JComboBox vt = w.getValueType();
        DefaultTable t = w.getTable();

        /* Подгрузка всех типов параметров деталей при появлении окна */
        w.addVisibleAction(() -> {
            ArrayList<DetailParameterType> result = m_model.getDetailParameterTypes();
            if (result != null) {
                t.clear();
                for (DetailParameterType dpt : result) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(dpt.id);
                    row.add(dpt.name);
                    row.add(dpt.type);
                    t.addLine(row);
                }
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Обновление таблицы типов параметров */
        ub.addAction(() -> {
            ArrayList<DetailParameterType> result = m_model.getDetailParameterTypes();
            if (result != null) {
                t.clear();
                for (DetailParameterType dpt : result) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(dpt.id);
                    row.add(dpt.name);
                    row.add(dpt.type);
                    t.addLine(row);
                }
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Добавление нового типа параметра в список имен деталей */
        ab.addAction(() -> {
            String new_name = ni.getText().trim();
            if (new_name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Имя не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DetailParameterType dpt = new DetailParameterType();
            dpt.name = new_name;
            dpt.type = (String) vt.getSelectedItem();

            String id = m_model.addDetailParameterType(dpt);
            if (id == null) {
                DialogUtility.showErrorIfExists();
                return;
            }
            t.addLine(id, new_name, dpt.type);
            t.getTable().setRowSelectionInterval(0, 0);
            JOptionPane.showMessageDialog(null, "Тип параметра детали добавлен", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });

        /* Редактирование типа параметра */
        eb.addAction(() -> {
            String id = w.getId();
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Выберите тип параметра детали", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String new_name = ni.getText().trim();
            if (new_name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Имя не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            id = m_model.updateDetailParameterType(new DetailParameterType(id, new_name, (String) vt.getSelectedItem()));
            if (id == null) {
                DialogUtility.showErrorIfExists();
                return;
            }
            ni.setText(new_name);
            t.setSelectedRowText(1, new_name);
            t.setSelectedRowText(2, (String) vt.getSelectedItem());
            JOptionPane.showMessageDialog(null, "Тип параметра детали обновлен", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });

        /* Удаление типа параметра */
        db.addAction(() -> {
            String id = w.getId();
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Выберите тип параметра детали", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            boolean result = m_model.deleteDetailParameterType(id);
            if (!result) {
                DialogUtility.showErrorIfExists();
                return;
            }
            ni.setText("");
            t.getTableModel().removeRow(t.getTable().getSelectedRow());
            JOptionPane.showMessageDialog(null, "Наименование детали удалено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    // ОКНО НАИМЕНОВАНИЙ ДЕТАЛЕЙ
    private void bindDetailNameControlActions() {
        DetailNameControlWindow w = m_detail_name_control_view.getDetailNameControlWindow();
        DefaultTable t = w.getTable();
        AddButton ab = w.getAddButton();
        EditButton eb = w.getEditButton();
        DeleteButton db = w.getDeleteButton();
        UpdateButton ub = w.getUpdateButton();
        JTextField ni = w.getNameInput();

        /* Подгрузка всех имен деталей при появлении окна */
        w.addVisibleAction(() -> {
            ArrayList<DetailName> result = m_model.getDetailNames();
            if (result != null) {
                t.clear();
                for (DetailName dn : result) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(dn.id);
                    row.add(dn.name);
                    t.addLine(row);
                }
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Обновление таблицы имен */
        ub.addAction(() -> {
            ArrayList<DetailName> result = m_model.getDetailNames();
            if (result != null) {
                t.clear();
                for (DetailName dn : result) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(dn.id);
                    row.add(dn.name);
                    t.addLine(row);
                }
            }
            else DialogUtility.showErrorIfExists();
        });

        /* Добавление нового имени в список имен деталей */
        ab.addAction(() -> {
            String new_name = ni.getText().trim();
            if (new_name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Имя не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = m_model.addDetailName(new_name);
            if (id == null) {
                DialogUtility.showErrorIfExists();
                return;
            }
            t.addLine(id, new_name);
            t.getTable().setRowSelectionInterval(0, 0);
            JOptionPane.showMessageDialog(null, "Наименование детали добавлено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });

        /* Редактирование имени детали */
        eb.addAction(() -> {
            String id = w.getId();
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Выберите наименование детали", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String new_name = ni.getText().trim();
            if (new_name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Имя не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            id = m_model.updateDetailName(new DetailName(id, new_name));
            if (id == null) {
                DialogUtility.showErrorIfExists();
                return;
            }
            ni.setText(new_name);
            t.setSelectedRowText(1, new_name);
            JOptionPane.showMessageDialog(null, "Наименование детали обновлено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });

        /* Удаление имени детали */
        db.addAction(() -> {
            String id = w.getId();
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Выберите наименование детали", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            boolean result = m_model.deleteDetailName(id);
            if (!result) {
                DialogUtility.showErrorIfExists();
                return;
            }
            ni.setText("");
            t.getTableModel().removeRow(t.getTable().getSelectedRow());
            JOptionPane.showMessageDialog(null, "Наименование детали удалено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void init() {
        bindDetailsWindowActions();
        bindDetailNameControlActions();
        bindDetailControlWindowActions();
        bindDetailParameterTypeControlActions();
        bindDetailSettingsActions();
    }
}
