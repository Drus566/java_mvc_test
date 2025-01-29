package iplm.mvc.controllers;

import iplm.mvc.models.DetailModel;
import iplm.mvc.models.IModel;
import iplm.mvc.views.DetailsView;
import iplm.mvc.views.IView;

import javax.swing.*;

public class DetailsController implements IController {
    private DetailModel m_model;
    private DetailsView m_view;

    public DetailsController(DetailModel model, DetailsView view) {
        m_model = model;
        m_view = view;
    }

    private void initActions() {
        initTableActions();
    }

    private void initTableActions() {
        JTable table = m_view.getWindow().getTable().getTable();
    }

    @Override
    public void init() {
        initActions();
    }
}
