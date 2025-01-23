package iplm;

import iplm.controllers.TaskController;
import iplm.views.TaskView;

public class Application {
    public static void main(String[] args) {
        new Application();
    }

    Application() {
        TaskView view = new TaskView();
        new TaskController(view);
    }
}
