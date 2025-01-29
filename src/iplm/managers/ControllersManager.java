package iplm.managers;

import iplm.mvc.builder.ControllerBuilder;
import iplm.mvc.builder.ViewBuilder;
import iplm.mvc.builder.component.ControllerComponent;
import iplm.mvc.builder.component.ViewComponent;
import iplm.mvc.controllers.IController;
import iplm.mvc.models.IModel;
import iplm.mvc.views.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllersManager {
    private Map<ControllerComponent, IController> m_controllers;

    public ControllersManager() {
        m_controllers = new HashMap<>();
    }

    public void buildControllers(ModelsManager models_manager, ViewsManager views_manager) {
        ControllerBuilder builder = new ControllerBuilder();

        for (ControllerComponent controller_component : ControllerComponent.values()) {
            if (controller_component.isActive()) {
                m_controllers.put(controller_component, builder.build(controller_component, models_manager, views_manager));
            }
        }
    }

//    private Map<ViewComponent, IView> m_views;
//
//    public ViewsManager() {
//        m_views = new HashMap<>();
//    }
//
//    public void buildViews() {
//        ViewBuilder builder = new ViewBuilder();
//        for (ViewComponent view_component : ViewComponent.values()) {
//            m_views.put(view_component, builder.build(view_component));
//        }
//    }
//
//    public void showStartWindow() {
//        for (Map.Entry<ViewComponent, IView> entry : m_views.entrySet()) {
//            ViewComponent key = entry.getKey();
//            if (key.isInit() && key.getVisible()) {
//                WindowsManager.getInstance().showWindow(key.getName());
//                break;
//            }
//        }
//    }

}
