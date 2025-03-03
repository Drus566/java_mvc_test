package iplm.mvc.controllers;

import iplm.data.db.OrientDBDriver;
import iplm.data.history.RequestHistory;
import iplm.data.history.StorageHistory;
import iplm.data.history.StorageHistoryType;
import iplm.data.types.Detail;
import iplm.data.types.DetailParameter;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.panel.search_panel.SearchPanel;
import iplm.gui.table.DefaultTable;
import iplm.gui.textfield.SearchBar;
import iplm.gui.window.detail.DetailControlWindow;
import iplm.mvc.models.DetailModel;
import iplm.mvc.views.DetailControlView;
import iplm.mvc.views.DetailsView;
import iplm.utility.DateTimeUtility;

import javax.swing.*;
import java.util.ArrayList;

public class DetailsController implements IController {
    private DetailModel m_model;
    private DetailsView m_details_view;
    private DetailControlView m_detail_control_view;

    public DetailsController(DetailModel model, DetailsView detail_view, DetailControlView detail_control_view) {
        m_model = model;
        m_details_view = detail_view;
        m_detail_control_view = detail_control_view;

        bindActions();
    }

    public void getAll() {
        /*
        m_table_btn.addAction(
            data = m_model.getALL();
            m_table.fill(data);
        )
         */
    }

    public void bindActions() {
        m_details_view.getDetailsWindow().getSearchBar().addTapAction(() -> {
            SearchBar sb = m_details_view.getDetailsWindow().getSearchBar();
            SearchPanel sp = m_details_view.getDetailsWindow().getSearchPanel();

            String search_text = sb.getSearchText();
            int MAX_TEXT_LENGTH = 100;
            if (search_text.length() > MAX_TEXT_LENGTH) return;

            sp.removeLines();

            /* Find in db */
            int MAX_COUNT = 5;

            if (!search_text.isEmpty()) {
                ArrayList<Detail> details = m_model.get(search_text);
                if (details != null) {
                    for (Detail d : details) {
                        if (MAX_COUNT <= 0) break;

                        sp.addActualLine(d.id, d.decimal_number + "  " + d.name + "  " + d.description, () -> {
                            DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
                            if (!dcw.isCreateMode()) {
                                Detail detail = m_model.getById(d.id);
                                sp.setVisible(false);
                                if (detail != null)  {
                                    dcw.m_id = detail.id;
                                    dcw.m_name.setText(detail.name);
                                    dcw.m_decimal_name.setText(detail.decimal_number);
                                    dcw.m_description.setText(detail.description);

                                    dcw.clearParametersPanel();

                                    for (DetailParameter dp : detail.params) {
                                        dcw.addParameter(dp.name,(String)dp.value);
                                    }
                                }
                            }
                            dcw.doNormalMode();
                            dcw.show();
                        });

                        --MAX_COUNT;
                    }
                }

                /* Find in cache */
                ArrayList<RequestHistory> request_history = StorageHistory.getInstance().search(StorageHistoryType.DETAILS, search_text);
                if (request_history != null) {
                    for (int i = 0; i < request_history.size(); i++) {
                        RequestHistory rh = request_history.get(i);
                        sp.addHistoryLine(rh.id, (String) rh.params.get("Query"), rh.type, m_details_view.getDetailsWindow());
                    }
                }
            }
            sp.updateLines();
            sp.updateSize(sb.getWidth());
        });

        m_details_view.getDetailsWindow().getSearchBar().addEnterButtonAction(() -> {
            SearchBar sb = m_details_view.getDetailsWindow().getSearchBar();
            DefaultTable t = m_details_view.getDetailsWindow().getTable();

            String search_text = sb.getSearchText();

            ArrayList<Detail> details = null;

            if (!search_text.isEmpty()) details = m_model.get(search_text);
            else details = m_model.getAll();

            if (details != null) {
                t.clear();
                for (Detail d : details) {
                    ArrayList<String> args = new ArrayList<>();
                    args.add(d.id);
                    args.add(d.decimal_number);
                    args.add(d.name);
                    args.add(d.description);
                    t.addLine(args);
                }
            }
        });

        m_details_view.getDetailsWindow().getUpdateButton().addAction(() -> {
            SearchBar sb = m_details_view.getDetailsWindow().getSearchBar();
            DefaultTable t = m_details_view.getDetailsWindow().getTable();

            String search_text = sb.getSearchText();

            ArrayList<Detail> details = null;

            if (!search_text.isEmpty()) details = m_model.get(sb.getLastRequest());
            else details = m_model.getAll();

            if (details != null) {
                t.clear();
                for (Detail d : details) {
                    ArrayList<String> args = new ArrayList<>();
                    args.add(d.id);
                    args.add(d.decimal_number);
                    args.add(d.name);
                    args.add(d.description);
                    t.addLine(args);
                }
            }
        });

        /* Открыть окно создания детали */
        m_details_view.getDetailsWindow().getAddDetailButton().addAction(() -> {
            //            WindowsManager.getInstance().showWindow(m_detail_control_view.getDetailControlWindow().getName());
            DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
            dcw.doCreateMode();
            dcw.show();
        });

        /* Создать деталь */
        DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
        JButton create_button = dcw.getCreateButton();
        create_button.addActionListener(e -> {
            if (!dcw.isCreateMode()) return;

            Detail detail = new Detail();
            detail.name = dcw.m_name.getText().trim();
            detail.decimal_number = dcw.m_decimal_name.getText().trim();
            detail.description = dcw.m_description.getText().trim();
            detail.created_at = DateTimeUtility.timestamp();
//            detail.updated_at = DateTimeUtility.timestamp();

            for (DetailParameterPanel dpp : dcw.m_detail_parameters) {
                DetailParameter dp = new DetailParameter();
                dp.busy = false;
                dp.custom_val = false;
                dp.deleted = false;
                dp.name = dpp.getKey();
                dp.enumeration = false;
                dp.type = DetailParameter.Type.STRING.s();
                dp.value = dpp.getValue();
                detail.params.add(dp);
            }
            String result = m_model.add(detail);
            if (result == null || result.isEmpty()) {
                JOptionPane.showMessageDialog(null, OrientDBDriver.getInstance().getLastError(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                dcw.m_id = result;
            }
            else JOptionPane.showMessageDialog(null, "Успешно");
        });

        dcw.getRemoveButton().addActionListener(e -> {
            if (dcw.isCreateMode() || dcw.isEditMode()) return;
            String rid = dcw.m_id;
            if (rid == null || rid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Выберите деталь для удаления");
                return;
            }

            String result = m_model.delete(rid);
            if (result != null && !result.isEmpty()) {
                dcw.m_decimal_name.setText("");
                dcw.m_name.setText("");

                JPanel dcw_panel = dcw.getPanel();
                for (DetailParameterPanel dpp : dcw.m_detail_parameters) {
                    dcw_panel.remove(dpp);
                    dcw_panel.revalidate();
                    dcw_panel.repaint();
                }
                dcw.m_detail_parameters.clear();
                JOptionPane.showMessageDialog(null, "Успешно");
                dcw.m_id = "";
            }
            else JOptionPane.showMessageDialog(null, OrientDBDriver.getInstance().getLastError(), "Ошибка", JOptionPane.ERROR_MESSAGE);

        });

        dcw.getEditButton().addActionListener(e -> {
            if (!dcw.isEditMode()) return;

            Detail detail = new Detail();
            detail.id = dcw.m_id;
            detail.name = dcw.m_name.getText().trim();
            detail.decimal_number = dcw.m_decimal_name.getText().trim();
            detail.description = dcw.m_description.getText().trim();
            detail.created_at = DateTimeUtility.timestamp();
//            detail.updated_at = DateTimeUtility.timestamp();

            for (DetailParameterPanel dpp : dcw.m_detail_parameters) {
                DetailParameter dp = new DetailParameter();
                dp.busy = false;
                dp.custom_val = false;
                dp.deleted = false;
                dp.name = dpp.getKey();
                dp.enumeration = false;
                dp.type = DetailParameter.Type.STRING.s();
                dp.value = dpp.getValue();
                detail.params.add(dp);
            }
            String result = m_model.update(detail);
            if (result == null || result.isEmpty()) {
                JOptionPane.showMessageDialog(null, OrientDBDriver.getInstance().getLastError(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                dcw.m_id = result;
            }
            else {
                JOptionPane.showMessageDialog(null, "Успешно");
                Runnable rebuild_index_action = () -> {
                    boolean result1 = m_model.rebuildIndex();
                    if (result1) JOptionPane.showMessageDialog(null, "Индекс успешно обновлен");
                    else JOptionPane.showMessageDialog(null, "Ошибка перестроения индекса");
                };
                Thread rebuild_index = new Thread(rebuild_index_action);
                rebuild_index.start();
            }
        });


        m_details_view.getDetailsWindow().getTable().addDoubleClickAction(new Runnable() {
            @Override
            public void run() {
//                System.out.println("DOUBLE CLICK");

                DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
                if (!dcw.isCreateMode()) {
                    Detail detail = m_model.getById(m_details_view.getDetailsWindow().getTable().getStringFromSelectedRowColumn(0));
                    if (detail != null)  {
                        dcw.m_id = detail.id;
                        dcw.m_name.setText(detail.name);
                        dcw.m_decimal_name.setText(detail.decimal_number);
                        dcw.m_description.setText(detail.description);

                        dcw.clearParametersPanel();

                        for (DetailParameter dp : detail.params) {
                            dcw.addParameter(dp.name,(String)dp.value);
                        }
                    }
                }
                dcw.doNormalMode();
                dcw.show();

            }
        });

    }


    public void show() {

    }

    public void create() {

    }

    @Override
    public void init() {

    }
}
