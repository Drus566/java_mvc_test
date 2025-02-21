package iplm.mvc.controllers;

import iplm.mvc.models.DetailModel;
import iplm.mvc.views.detail.DetailsView;

import javax.swing.*;

public class DetailsController implements IController {
    private DetailModel m_model;
    private DetailsView m_view;
    private DetailsView

    public DetailsController(DetailModel model, DetailsView view) {
        m_model = model;
        m_view = view;
    }

    public void getAll() {
        /*
        m_table_btn.addAction(
            data = m_model.getALL();
            m_table.fill(data);
        )
         */
    }

    public void show() {

    }

    public void create() {

    }

    @Override
    public void init() {

    }
}
