package iplm.mvc.controllers;

import iplm.managers.WindowsManager;
import iplm.mvc.models.DetailModel;
import iplm.mvc.views.DetailControlView;
import iplm.mvc.views.DetailsView;

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
        m_details_view.getDetailsWindow().getAddDetailButton().addAction(() -> WindowsManager.getInstance().showWindow(m_detail_control_view.getDetailControlWindow().getName()));
    }

    public void show() {

    }

    public void create() {

    }

    @Override
    public void init() {

    }
}
