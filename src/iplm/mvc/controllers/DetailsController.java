package iplm.mvc.controllers;

import iplm.data.db.OrientDBDriver;
import iplm.data.history.RequestHistory;
import iplm.data.history.StorageHistory;
import iplm.data.history.StorageHistoryType;
import iplm.data.types.Detail;
import iplm.data.types.DetailParameter;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.panel.search_panel.SearchPanel;
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

            sp.removeLines();

            /* Find in db */
            int MAX_COUNT = 5;

            if (!search_text.isEmpty()) {
                ArrayList<Detail> details = m_model.get(search_text);
                if (details != null) {
                    for (Detail d : details) {
                        if (MAX_COUNT <= 0) return;
                        sp.addActualLine(d.decimal_number + " " + d.name);
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
//            detail.description = "";
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
            if (m_model.add(detail).isEmpty()) JOptionPane.showMessageDialog(null, OrientDBDriver.getInstance().getLastError(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(null, "Успешно");
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
