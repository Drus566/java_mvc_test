package iplm;

import iplm.controllers.TaskController;
import iplm.managers.WindowManager;
import iplm.views.TaskView;

public class Application {
    WindowManager window_manager;

    public static void main(String[] args) {
        new Application();
    }

    Application() {
        window_manager = new WindowManager();
    }
}
