package iplm.mvc.controllers;

import iplm.data.types.Detail;
import iplm.data.types.DetailParameter;
import iplm.gui.panel.detail_parameter.DetailParameterPanel;
import iplm.gui.window.detail.DetailControlWindow;
import iplm.mvc.models.DetailModel;
import iplm.mvc.views.DetailControlView;
import iplm.mvc.views.DetailsView;
import iplm.utility.DateTimeUtility;

import javax.swing.*;

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
        m_details_view.getDetailsWindow().getAddDetailButton().addAction(() -> {
            //            WindowsManager.getInstance().showWindow(m_detail_control_view.getDetailControlWindow().getName());
            DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
            dcw.doCreateMode();
            dcw.show();
        });

        DetailControlWindow dcw = m_detail_control_view.getDetailControlWindow();
        JButton create_button = dcw.getCreateButton();
        create_button.addActionListener(e -> {
            Detail detail = new Detail();
            detail.name = dcw.m_name.getText();
            detail.decimal_number = dcw.m_decimal_name.getText();
            detail.description = "example description";
            detail.created_at = DateTimeUtility.timestamp();
            detail.updated_at = DateTimeUtility.timestamp();

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
            if (m_model.add(detail).isEmpty()) { System.out.println("ERROR SEND REQ"); }
            else { JOptionPane.showMessageDialog(null, "Успешно"); }
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
