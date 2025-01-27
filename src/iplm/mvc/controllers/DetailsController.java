package iplm.mvc.controllers;

import iplm.mvc.models.DetailModel;
import iplm.mvc.models.IModel;
import iplm.mvc.views.DetailsView;
import iplm.mvc.views.IView;

import javax.swing.*;

public class DetailsController implements IController {
    private DetailsView m_view;
    private DetailModel m_model;

    private void initActions() {
        initTableActions();
    }

    private void initTableActions() {
        JTable table = m_view.getWindow().getTable().getTable();
    }

    @Override
    public void init(IModel model, IView view) {
        m_view = (DetailsView) view;
        m_model = (DetailModel) model;
        m_model.addObserver(m_view);
    }
}
