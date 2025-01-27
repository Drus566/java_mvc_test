package iplm.managers;

import iplm.mvc.controllers.IController;
import iplm.mvc.models.IModel;
import iplm.mvc.views.IView;

import java.util.ArrayList;
import java.util.List;

public class ControllersManager {
    private List<IController> m_controllers;

    public ControllersManager(IModel model, IView view) {
        m_controllers = new ArrayList<>();
    }

    public void addController(IModel model, IView view) {

    }
}
