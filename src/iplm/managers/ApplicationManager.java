package iplm.managers;

import iplm.mvc.controllers.IController;
import iplm.mvc.models.IModel;
import iplm.mvc.views.IView;

import java.util.List;

public class ApplicationManager {
    private static final ApplicationManager INSTANCE = new ApplicationManager();

    private ControllersManager controllers_manager;
    private ViewsManager views_manager;
    private ModelsManager models_manager;

    public void softwareUpdate() {}
    public void start() {

    }
    public void stop() {}
    public void restart() {}
    public void exit() {}

    public static ApplicationManager getInstance() {
        return INSTANCE;
    }
}
